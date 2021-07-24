> `Windows` 환경의 `VSCode`에서 `AWS EC2`에 원격접속을 해보자.
>
> 이 포스팅은 `EC2`와 접속에 필요한 `.pem`키는 이미 있다고 가정하고 진행한다.



## vscode extension: Remote Development 설치하기

`ctrl` + `shift` + `X` 단축키로 `MarketPlace`를 열어 `Remote Development`를 설치한다.

![ec2 원격 접속하기-1](https://user-images.githubusercontent.com/52652338/126439051-458723b3-0d91-48b9-8da2-0b297c09b491.PNG)



## SSH 연결 config 파일 수정하기

`F1` 키를 눌러 `ssh open`을 검색하여 나오는 `Remote-SSH: Open SSH Configuration File...`을 클릭한다.

![ec2 원격 접속하기-2](https://user-images.githubusercontent.com/52652338/126439053-01587648-e996-4d66-a64a-b01af114cc57.PNG)



기본적으로 저장되는 위치는 `사용자이름/.ssh/` 이므로 `config` 파일이 없다면 그냥 새로 만들면 된다.

![ec2 원격 접속하기-4](https://user-images.githubusercontent.com/52652338/126439056-fdd0c98a-046b-4e35-b2b9-97b9603ad027.png)



`aws console`에서 연결할 `ec2 instance`를 찾아 `우클릭` 후 `연결` 을 눌러 다음과 같이 `SSH 클라이언트` 정보를 참고하여 `config`를 구성하면 된다.

![ec2 원격 접속하기-3](https://user-images.githubusercontent.com/52652338/126439054-1e392488-65b5-469e-981d-085d4680d710.PNG)

```
Host ami2_jenkins_ec2
  HostName [퍼블릭 IPv4 DNS 주소]
  User ec2-user
  IdentityFile ~/.ssh/ami2-jenkins.pem
```

- `Host`: 연결할 ec2의 별칭(별명)이므로 아무렇게나 지어도 상관없다.
- `HostName`: ec2의 `퍼블릭 IPv4 DNS 주소`이다. 위 사진에서는 `노란색 박스` 부분이다.
- `User`: 접속할 사용자 이름이다. 위 사진에서는 빨간색 부분이다.
- `IdentityFile`: ec2에 접속할 때 필요한 `.pem`키의 위치를 지정한다. 본인은 `config` 파일과 같은 폴더 내에 위치한다.



## 원격 접속하기(Remote)

`F1`을 눌러 `ssh`를 검색하여 나오는 `Remote-SSH: Connect to Host...` 를 클릭한다.

그러면 위에서 저장한 `Host` 별칭을 확인할 수 있다. Host 별칭을 클릭하면 `ec2 인스턴스`에 접속함을 확인할 수 있다.

![ec2 원격 접속하기-5](https://user-images.githubusercontent.com/52652338/126439057-4ec1c8c2-10eb-49d6-a9cd-3c8752c79a58.png)



아래와 같이 `폴더 열기`를 통해 `ec2 인스턴스의 디렉토리 구조`를 파일탐색기처럼 확인할 수 있다.

`git`이 설치되어 있다면 `project clone`도 가능하다. `Terminal` 창은 `ctrl` + `~` 으로 띄울 수 있다.

좌측 하단을 보면 `SSH:[별칭]` 으로 하여 해당 `ec2 인스턴스`에 접속했음을 다시 한 번 확인할 수 있다.

![ec2 원격 접속하기-6](https://user-images.githubusercontent.com/52652338/126439058-192764c5-9d10-472c-816c-033209ecf070.png)

