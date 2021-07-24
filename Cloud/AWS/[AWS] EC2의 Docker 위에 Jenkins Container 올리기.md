> `AWS EC2 Instance (Amazon Linux2)` 에서 `Docker`로 `Jenkins` 이미지를 다운받아서 `Container`로 띄우는 것이 목표이다.
>
> ec2 인스턴스가 있다고 가정하고, Java나 Docker나 모두 설치되어 있다고 가정을 하고 시작한다.



## EC2 Instance 접속하기

먼저 `Jenkins`를 설치할 ec2 instance 에 접속한다. (putty, mobaXterm, vscode remote-ssh 등을 이용하여)

그리고 `Docker`를 실행하고, 간단한 설정을 진행한다.

```shell
# Docker 실행
$ sudo systemctl start docker
```



`루트 사용자`가 아닌 `일반 사용자`가 docker 명령어를 사용하려고 하면, `Permission Denied` 에러를 뱉는다.

따라서 `sudo`의 번거로움을 덜기 위하여 아래와 같이 `Docker group`에 `현재 사용자 이름`을 등록한다.

```shell
# 현재 사용자 이름 확인
$ echo $USER

# docker group에 사용자 추가
$ sudo usermod -aG docker $USER

# docker 재실행
$ sudo service docker restart
```



## Jenkins image 다운로드

`도커 이미지명` 같은 경우 보통 `[image name]:[image tag]` 식의 네이밍 규칙을 가지고 있다.

`컨테이너`로 올릴 이미지가 로컬에 존재하지 않는다면, `Docker Hub`에서 서치하여 `pull`해와서 실행한다. 



https://hub.docker.com/_/jenkins?tab=description&page=1&ordering=last_updated

위 사이트를 참고하여 jenkins의 도커이미지 이름을 알 수 있다.

```shell
# jenkins image download
$ docker pull jenkins/jenkins:lts

# 로컬에 존재하는 docker images 확인
$ docker images
# 출력 예시
REPOSITORY        TAG         IMAGE ID       CREATED       SIZE
jenkins/jenkins   lts         22bfa28ae34c   3 weeks ago   572MB 
```



## Docker image를 Container로 띄우기

다음 명령어를 통하여 다운로드 받은 image를 container로 띄울 수 있다.

```shell
# docker 컨테이너로 등록 후 실행
$ docker run -d -p 9090:8080 -v /jenkins:/var/jenkins_home --name jenkins -u root jenkins/jenkins:lts
```

- `-d`: detached mode 이다. `백그라운드`로 실행할 수 있게 해준다.
- `-p`: host(앞)와 container(뒤)의 포트를 연결해준다.(port forwarding) -> `로컬Port:컨테이너Port`
- `-v`: host(앞)와 container(뒤)의 디렉토리를 연결해준다.(mount) -> `로컬Directory:컨테이너Directory`
- `-u`: 실행할 사용자 이름을 지정한다.
- `--name`: 실행될 컨테이너의 이름을 지정한다.



다음 명령어를 활용하여 현재 실행되고 있는 컨테이너 및 보유하고 있는 컨테이너를 확인할 수 있다.

```shell
# 실행되고 있는 컨테이너 목록
$ docker ps

# 실행되고 있지 않는 컨테이너도 포함한 목록
$ docker ps -a

# 출력 예시
CONTAINER ID   IMAGE                 COMMAND                  CREATED          STATUS          PORTS                               NAMES
6b493370af1f   jenkins/jenkins:lts   "/sbin/tini -- /usr/…"   13 seconds ago   Up 12 seconds   50000/tcp, 0.0.0.0:9090->8080/tcp   jenkins
```

위의 경우, 로컬(ec2 인스턴스)의 `9090`포트로 들어오는 요청들을 도커 컨테이너(jenkins)의 `8080`포트로 `포트포워딩` 시켜준다.

따라서 ec2 인스턴스의 `보안 그룹`의 `인바운드 정책`을 수정할 필요가 있다.
(`9090`포트로 들어오는 요청을 허용해야한다. 이 부분은 여기서 다루진 않겠다.)



## Jenkins 접속하기

ec2 인스턴스의 `퍼블릭 IPv4 주소:9090` 혹은 `퍼블릭 IPv4 DNS:9090`로 접속이 가능하다.

주의할 점은 당연한 얘기지만 ec2 환경에서 `8080` 포트를 사용하고 있지 않아야 한다.

`8080`포트를 이미 사용하고 있어도 지금까지 위에서 진행된 내용들은 정상적으로 진행이 된다.

하지만 `9090`포트로 접속하려고 시도하면 `8080`포트로 포트포워딩이 정상적으로 진행되지 않는다.

`netstat -ntlp | grep :80` 명령어를 활용하여 `8080`포트가 사용되고 있는지를 확인한다.



정상적으로 접속이 된다면 아래와 같은 화면을 브라우저에서 볼 수 있을 것이다.

![docker+jenkins-1](https://user-images.githubusercontent.com/52652338/126866296-4ff3f4a2-20dc-49bd-a537-d263eab191ac.PNG)

위에서 요구하는 패스워드는 `도커 컨테이너` 안에 특정파일 안에 존재한다.

아래 명령어를 활용하여 패스워드를 알아낼 수 있다.

```shell
# docker의 jenkins 컨테이너로 접속하여 /var/jenkins_home/secrets/initialAdminPassword 읽기
$ docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword
```

- `docker exec` 명령어는 컨테이너에 접속하는 명령어이다.



참고로 `도커 컨테이너 내부로 접속`하고 싶다면 다음 명령어를 사용하면 된다.

```shell
$ docker exec -it [컨테이너 ID 혹은 컨테이너 Name] /bin/bash
```

- `exit`을 활용해 접속을 종료하고 외부로 빠져나올 수 있다.
- 도커 컨테이너 내부에서 `cat /var/jenkins_home/secrets/initialAdminPassword`을 활용하여 패스워드를 알아낼 수도 있다.



![docker+jenkins-2](https://user-images.githubusercontent.com/52652338/126866298-6beb5719-4afd-48cf-a592-dbf06df6c850.PNG)

위 사진 처럼`Install suggested plugins`를 선택하여 플러그인을 설치하고 나면 아래 사진과 같이 `Admin` 계정을 만드는 화면을 볼 수 있다.

![docker+jenkins-3](https://user-images.githubusercontent.com/52652338/126866299-4b10e1e9-8070-4f2b-873e-30144c53479b.PNG)

`Admin` 계정을 만들고 나면 `Instance Configuration`이 나올텐데 `Jenkins URL`이 ec2 인스턴스의 `퍼블릭 IPv4 DNS:port number`와 동일한지 확인 한 뒤 `Save and Finish`를 선택한다.



아래 사진처럼 Jenkins에 접속했다면 성공한 것이다.

![docker+jenkins-4](https://user-images.githubusercontent.com/52652338/126866301-26bcf19b-daf3-4a70-aa9a-13a6847b3732.PNG)



## 도커 컨테이너 시작/중단/삭제하기

컨테이너가 이미 존재한다고 가정했을 때, `docker ps -a` 명령어로 보유하고 있는 `도커 컨테이너 목록`을 확인할 수 있다.

* 참고: `docker images`로는 보유하고 있는 `도커 이미지 목록`을 확인할 수 있다.



#### 컨테이너 시작하기

```shell
$ docker start [컨테이너ID or 컨테이너Name]
```



#### 컨테이너 중지하기

```shell
$ docker stop [컨테이너ID or 컨테이너Name]
```



#### 컨테이너 삭제하기

```shell
$ docker rm [컨테이너ID or 컨테이너Name]
```



## Refer to

- https://subicura.com/2017/01/19/docker-guide-for-beginners-2.html

