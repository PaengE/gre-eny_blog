> `Amazon Linux 2` 이미지를 가지고 실행된 `AWS EC2` 에서 `Java 11` 을 설치해 보자.



`yum` 으로 설치 가능한 Java 는 버전 8까지이다.

따라서 아마존에서 제공하는 OpenJDK인 `Amazon Coretto` 를 다운받아 설치하자.

```shell
$ yum list java*jdk-devel # 설치 가능한 jdk 확인
# java-1.6.0-openjdk-devel.x86_64                                       1:1.6.0.41-1.13.13.1.77.amzn1                                       amzn-main
# java-1.7.0-openjdk-devel.x86_64                                       1:1.7.0.261-2.6.22.1.83.amzn1                                       amzn-updates
# java-1.8.0-openjdk-devel.x86_64                                       1:1.8.0.252.b09-2.51.amzn1    
```



## Java 11 설치하기

```shell
# aws coreetto 다운로드 (2가지 방법)
$ sudo curl -L https://corretto.aws/downloads/latest/amazon-corretto-11-x64-linux-jdk.rpm -o java11.rpm
# 혹은
$ sudo wget https://corretto.aws/downloads/latest/amazon-corretto-11-x64-linux-jdk.rpm -O java11.rpm

# jdk11 설치
$ sudo yum localinstall jdk11.rpm

# jdk version 선택
$ sudo /usr/sbin/alternatives --config java

# java 버전 확인
$ java --version

# 다운받은 설치파일 제거
$ rm -rf jdk11.rpm
```

- 파일을 다운로드 할 때 `-o` 혹은 `-O` 옵션을 사용하여 파일명을 지정해서 다운로드 받을 수 있다. 



## 사용하지 않는 Java 버전 지우기

```shell
$ yum list installed | grep "java" # yum 설치 리스트 확인
# java-1.8.0-openjdk-headless.x86_64    1:1.8.0.222.b10-0.47.amzn1   @amzn-updates
# java-11-amazon-corretto-devel.x86_64  1:11.0.7.10-1                installed

$ sudo yum remove java-1.8.0-openjdk-headless.x86_64 
```



## Refer to

- [AWS Corretto JDK List](https://docs.aws.amazon.com/corretto/latest/corretto-11-ug/downloads-list.html)

