# AWS 서버 환경 만들기 - 1 + EC2 instance 생성

> 여기서 구축하는 서버환경은 다음과 같은 개발환경에서 빌드된 프로젝트를 24시간 무중단 배포하기 위함임을 참고해주시면 좋겠다.
>
> - Windows 10
> - JDK 11
> - SpringBoot 2.4.1
> - Gradle 6.7
> - Intellij Community Edition 2020.03

## 클라우드 서비스(Cloud Service)

> 시작하기에 앞서 클라우드란,
> 쉽게 말하여 인터넷(클라우드)을 통하여 서버, 스토리지(파일 저장소), 데이터베이스, 네트워크, 소프트웨어, 모니터링 등의 컴퓨팅 서비스를 제공하는 것이다.
>
> [ 클라우드의 종류 ]
>
> - Infrastructure as a Service(IaaS)
>   - 기존 물리 장비를 미들웨어와 함께 묶어둔 추상화 서비스이다.
>   - 가상머신, 스토리지, 네트워크, 운영체제, 등의 IT 인프라를 대여해 주는 서비스이다.
>   - AWS의 EC2, S3 등
> - Platform as a Service(PaaS)
>   - IaaS에서 한 번 더 추상화한 서비스이다.
>   - 한번 더 추상화했끼 때문에 많은 기능이 자동화되어 있다.
>   - AWS의 Beanstalk, Heroku 등
> - Software as a Service(SaaS)
>   - 소프트웨어 서비스이다.
>   - 구글 드라이브, 드랍박스, 와탭 등
>
> PaaS 서비스인 AWS의 Beanstalk을 사용하면 많은 작업이 간소화되지만, 프리티어로 무중단 배포가 불가능하므로, IaaS를 이용하여 서버를 구성한다.

## EC2 instance 생성

> EC2(Elastic Compute Cloud)는 AWS에서 제공하는 성능, 용량 등을 유동적으로 사용할 수 있는 서버이다.

1. AMI(Amazon Machine Image) 선택하기.

   - AMI는 EC2 인스턴스를 시작하는 데에 필요한 정보를 `이미지로 만들어 둔 것`이다.
   - 여기서는 Amazon Linux AMI 2를 사용하겠다.

2. 인스턴스 유형 선택하기.

   - 여기서는 free-tier인 t2.micro 를 사용한다.

3. 인스턴스 세부 정보 구성하기.

   - VPC, Subnet 등의 네트워크 설정을 건드릴 수 있다. 1인 개발 시 별도로 설정할 필요는 없다.
   - 기회가 된다면 다른 포스팅에 VPC, Subnet, Gateway, Load Balancer, Auto Scaling에 대해 다뤄보겠다.

4. 스토리지 선택하기.

   - 서버의 디스크(SSD도 포함)를 선택하는 과정이다
   - 설정 기본값은 8GB이지만, 30GB까지는 free-tier로 사용가능하다.

5. 태그 추가.

   - 태그는 해당 인스턴스를 표현하는 여러 이름으로 사용될 수 있다.
   - 웹 콘솔에서 표기될 태그인 Name 태그 정도만 등록해보자.

6. 보안 그룹 추가.

   - 사실 가장 중요한 부분으로, 프로토콜과 포트번호에 대해서 접근을 허용하냐, 마냐를 설정하는 작업이다. `pem 키 관리`와 `지정된 IP에서만 SSH 접속가능`하도록 구성하면 보안을 더 챙길수 있다.
   - 여기서는 웹 서버로 사용할 것이므로, `웹 서버전용 8080포트`와 `SSH로 EC2 터미널에 접속할 22포트` , 그리고 `HTTPS가 사용되는 포트` 정도만 열어두도록 한다.

7. 인스턴스 검토.

   - 새 키페어를 생성하여 다운로드 받는다.

   > 인스턴스는 지정된 pem키(비밀키)와 매칭되는 공개키를 가지고 있어, 해당 pem키 외에는 접근을 허용하지 않는다.
   >
   > 일종의 `마스터키`이므로 절대 유출되면 안되며, 잃어버리지 않도록 주의해야한다.

8. 인스턴스 실행.

   - 이제 서버 구성은 끝이났고, 인스턴스를 실행해 주면 된다.

   > 인스턴스 실행 시, 항상 새 IP를 할당한다. 따라서 인스턴스를 중지 후 재시작한다면 기존에 사용하던 IP는 사용하지 못하게 되고, 새로운 IP를 받는다.
   >
   > 위의 문제가 상당히 귀찮고 반복되는 작업을 요구하므로, 고정 IP를 할당해보자.
   >
   > AWS에서는 고정 IP를 탄력적 IP(EIP: Elastic IP)라고 한다. EC2 인스턴스 페이지의 왼쪽 카테고리에서 설정할 수 있다.
   >
   > 새 EIP를 할당을 요청하여 발급받고, 생성된 EC2와 연결해주면 끝난다.
   >
   > ####주의할 점은, `EIP는 생성하고 EC2 서버와 연결하지 않으면 비용이 발생한다`는 점이다. 어차피 EC2 인스턴스를 하나만 생성하여 사용한다면, 750시간 free 안쪽이므로 EC2를 중지하지 않아도 되므로, 억울한 비용이 발생하지 않도록 하는 것이 좋다.(EC2를 중지해도 비용이 청구된다.)

9. SSH로 EC2 인스턴스 터미널로 접속하는 방법.

   - putty로 접속하기: puttygen을 통해 pem 키를 ppk 키로 변환하여야 한다.
   - MobaXterm으로 접속하기: pem 키를 사용하여 바로 접속이 가능하다.

   위의 두 방법 모두 Host Name은 ec2-user로 해야한다.

## Amazon Linux AMI 2 서버 설정

- Java 11 설치

  - https://docs.aws.amazon.com/ko_kr/corretto/latest/corretto-11-ug/amazon-linux-install.html

  - 위의 링크에서 JDK 11 version을 설치하는 방법을 참고하면 된다.

  - Java 8 version을 설치하려면 다음 명령어를 입력한다.

  - ```shell
    sudo yum install -y java-1.8.0-openjdk-devel.x86_64	// jdk 8 설치
    sudo /usr/sbin/alternatives --config java	// default java version 선택
    sudo yum remove 삭제할 자바 버전	// 사용하지 않는 자바 버전 삭제
    java -version	// 자바 버전확인
    ```

- 타임존 변경

  - EC2 서버의 기본 타임존은 UTC 이므로, `한국시간과 9시간 차이`가 발생한다. `한국시간(KST)`로 변경하는 명령어는 다음과 같다.

  - ```shell
    sudo rm /etc/localtime
    sudo ln -s /usr/share/zoneinfo/Asia/Seoul /etc/localtime
    ```

  - `date`명령어로 현재 서버 시간을 확인할 수 있다.

- Hostname 변경

  - 여러 서버를 관리 중일 경우 IP만으로는 어떤 서비스의 서버인지 확인이 어려우므로, 각 서버가 어느 서비스인지를 표현하기 위해 hostname을 변경해두는 것이 좋다.

  - ```shell
    sudo vim /etc/sysconfig/network
    ```

    위의 명령어로 파일을 열어 `HOSTNAME=원하는 이름`으로 변경할 수 있다.

  - 변경한 후에 `sudo reboot`명령어를 통해 서버를 재부팅한다.

  - 새로운 Hostname이 등록되었다면, 호스트 주소를 찾을 때 가장 먼저 검색해보는 /etc/hosts에 변경한 hostname을 등록한다. 다음 명령어로 /etc/hosts 파일을 열어 수정한다.

  - ```shell
    sudo vim /etc/hosts
    
    127.0.0.1 ~~~~~
    ::1				~~~~~
    127.0.0.1 등록한 HOSTNAME
    ```

  - `curl 등록한 호스트 이름`으로 정상적으로 등록되었는지 확인해 볼 수 있다.
    정상적으로 등록되었다면, 80포트로 접근이 안된다는 에러가 발생할 것이고,
    등록에 실패했다면, 찾을수 없는 주소라는 에러가 발생할 것이다.

  - 정상적으로 등록되었더라도 오류가 발생하는 이유는 아직 80포트로 실행된 서비스가 없기 때문이다.