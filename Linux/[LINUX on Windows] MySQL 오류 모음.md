> 윈도우 WSL의 Ubuntu에서 발생하는 MySQL 에러를 모아봤다.
>
> +++내가 겪은 오류들을 추가할 예정
>
> [실행환경]
> Windows 10
> Ubuntu 18.04 LTS
> mysql ver14.14 Distrib 5.7.31

## 1. MySQL데몬 실행 에러

> ubuntu에서 mysql을 실행 시킬 때 
> /etc/init.d/mysql start 혹은 mysql -u root -p 등을 사용할 것이다.
> 만약 Windows에서도 mysql을 사용하고 있다면 이러한 에러를 발견할 수 있다.

```mysql
ERROR 2002 (HY000): Can't connect to local MySQL server through socket 
    '/var/run/mysqld/mysqld.sock' (2)
```

저 에러는 

  \1. /var/run/mysqld/mysqld.sock 소켓파일이 없거나(찾을 수 없거나)

  \2. port number가 같은 경우 충돌이 일어날 때 발생 할 수 있다.

```
$ cat /var/log/mysql/error.log
```

위 명령어로 mysql error log를 본 후 어떤 문제인지 판별 할 수 있다.

1의 경우에는 내가 겪은 경우가 아니라 자세히 알진 못하지만 재설치를 하면 해결된다는 것 같다.

2의 경우가 내가 겪은 상황인데 로그파일에 아래 에러 로그가 적혀있었다.

```null
[ERROR] Do you already have another mysqld server running on port: 3306 ?
```

이미 3306 port number를 사용하고 있다는 말이다. port number를 바꿔주면 된다.
윈도우에서 우분투를 사용할 때 mysql 설정파일 경로는 /etc/mysql/mysql.conf.d/mysqld.cnf 이며,
[mysqld] 아래에 있는 port 부분을 안쓰는 포트넘버로 바꿔주면 된다.

```
$ /etc/init.d/mysql restart
```

위의 명령어로 mysql을 재시작하고 다시 실행시키면 제대로 동작하는 것을 볼 수있다.



### PS

위의 방법은 나에게 임시적인 방편(?)인 것 같다. 당장 에러가 날 때 포트넘버를 바꾸면 제대로 동작하지만 어찌된 일인지 며칠이 지나면 다시 포트넘버 충돌이 일어나서 포트넘버를 다른 넘버로 바꿔야한다. 여기에 대한 내용은 찾을 수 없어서 귀찮지만... 어쩔 수 없이 이렇게 쓰고 있다...