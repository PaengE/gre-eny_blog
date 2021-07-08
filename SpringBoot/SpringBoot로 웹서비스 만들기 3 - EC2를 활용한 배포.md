## AWS EC2 서버에 SpringBoot 프로젝트 배포

> 이 포스팅에 사용된 EC2와 RDS instance에 관한 내용은 이전 포스팅을 참고해주시면 감사하겠다.
>
> - JDK 11
> - SpringBoot 2.4.1
> - Gradle 6.7

## EC2에 프로젝트 clone 받기

> 프로젝트는 다 개발되어 있다는 것을 전제로, git에 올려져있는 프로젝트를 git 명령어를 이용하여 clone 한다.

일단 EC2 터미널에서 git 명령어를 사용하기 위해 git을 설치한다.

```sh
$ sudo yum install git
```

적절한 곳에 디렉토리를 새로 생성하고(mkdir ~/app && mkdir ~/app/step1) 다음 명령어로 프로젝트를 복제한다.

```shell
$ git clone 깃허브 프로젝트 주소
```

clone이 완료되면, 프로젝트의 테스트 코드들이 정상동작하는지를 확인해본다.

```shell
$ cd 프로젝트명
$ ./gradlew test
```

만약, 실행 권한이 없다는 Permission denied 오류가 뜬다면 다음과 같은 명령어로 실행 권한을 준다.

```shell
$ chmod +x ./gradlew
```

> `EC2에 Gradle을 설치하지 않아도 Gradle Task(ex: test)를 수행할 수 있는 이유`
>
> - 이는 프로젝트 내부에 포함된 gradlew 파일 때문이다. gradlew 파일은 gradle이 설치되지 않은 환경 혹은 버전이 다른 상황에서도 해당 프로젝트에 한해서 gradle을 쓸 수 있도록 지원하는 Wrapper 파일이다.
> - 해당 파일을 직접 이용하기 때문에 별도로 gradle을 설치할 필요가 없다.

## 배포 스크립트 작성

> 작성한 코드를 실제 서버에 반영하는 것을 `배포(deploy)`라고 한다. 
>
> 보통은 변경된 코드를 반영한 새 버전의 프로젝트를 git과 같은 버전관리 툴에서 받은 후, 프로젝트를 테스트하고 빌드 한 뒤, EC2 서버에서 해당 프로젝트를 실행 및 재실행 하는 과정을 말한다.
>
> 앞선 과정들을 배포할 때마다 하나하나 명령어를 실행하기 어려우므로 `배포 스크립트`를 만드는 것이 좋다.
>
> 여기서는 리눅스 환경이므로 .sh 확장자인 `쉘 스크립트`를 작성하도록 하겠다. (vim ~app/step1/deploy.sh)

```sh
#!/bin/bash

REPOSITORY=/home/ec2-user/app/step1
PROJECT_NAME=SpringBoot_AWS_Book_Ex
PROJECT_PID=Spring_AWS_Book_Ex

cd $REPOSITORY/$PROJECT_NAME/

echo "> Git pull"
git pull

echo "> Project build start"
./gradlew build

echo "> Move step1 directory"
cd $REPOSITORY

echo "> Build file copy"
cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/

echo "> Check the application ID running now"
CURRENT_PID=$(pgrep -f ${PROJECT_PID}.*.jar)
echo "> The application ID running now: $CURRENT_PID"

if [ -z "$CURRENT_PID"]; then
        echo "> There are no applications currently running and will not shut down."
else
        echo "> kill -15 $CURRENT_PID"
        kill -15 $CURRENT_PID
        sleep 5
fi

echo "> Deploy new application"
JAR_NAME=$(ls -tr $REPOSITORY/ | grep jar | tail -n 1)
echo "> JAR Name: $JAR_NAME"

nohup java -jar $REPOSITORY/$JAR_NAME 2>&1 &
```

- REPOSITORY=/home/ec2-user/app/step1
  - 프로젝트 디렉토리 주소는 스크립트 내에서 자주 사용하는 값이기 때문에 이를 `변수`로 저장한다.
  - 마찬가지로 PROJECT_NAME와 PRJECT_PID도 `변수`로 저장된 것이다. PROJECT_NAME은 github repository의 이름이며, PROJECT_PID는 intellij에서의 프로젝트이름이다.
  - 쉘에서는 `타입 없이` 선언하여 사용할 수 있으며, `$변수명`으로 변수를 사용할 수 있다.
- cd $REPOSITORY/$PROJECT_NAME/
  - 위의 쉘 변수 설명을 따라 변수에 저장된 곳으로 이동한다.
- git pull
  - 디렉토리 이동 후, git master branch의 최신 내용을 받는다.
- ./gradlew build
  - 프로젝트 내부의 gradlew로 build를 수행한다.
- cp ./build/libs/*.jar $REPOSITORY/
  - build의 결과물인 jar 파일을 복사하여 jar 파일이 모아둔 위치로 복사한다.
- CURRENT_PID=$(pgrep -f ${PROJECT_PID}.*.jar)
  - 기존에 수행 중이던 스프링 부트 애플리케이션을 종료한다.
  - pgrep은 process id만 추출하는 명령어이고, -f 옵션은 프로세스 이름으로 찾는다.
- if ~ else ~ fi
  - 현재 구동 중인 프로세스가 있는지 없는지를 판단해서 기능을 수행한다.
  - process id 값을 보고 프로세스가 있으면 해당 프로세스를 종료한다. (이 부분이 실행이 제대로 되지 않으면, 8080 포트가 사용중이라는 에러를 nohup.out에서 볼 수 있다.)
    해결법은 `netstat -ntlp | grep :8080`으로 사용중인 pid를 kill 명령어로 죽이면 되지만, 이건 일시적인 방편이며, deploy.sh 를 실행할 때마다 같은 에러를 볼 수 있을 것이다. 따라서 배포스크립트에 경로 등을 작성을 잘하여 자동화 하는 것이 중요하다.
- JAR_NAME=$(ls -tr $REPOSITORY/ | grep jar | tail -n 1)
  - 새로 실행할 jar 파일명을 찾는다. 여러 jar 파일이 생기기 때문에 tail -n 1로 가장 나중의 jar파일(최신 파일)을 변수에 저장한다.
- nohup java -jar $REPOSITORY/$JAR_NAME 2>&1 &
  - 찾은 jar 파일명으로 해당 jar 파일을 nohup으로 실행한다. 스프링부트의 장점으로 특별히 외장 톰캣을 설치할 필요가 없다. 내장 톰캣을 사용하여 jar파일만 있으면 바로 웹 어플리케이션 서버를 실행할 수 있다.
  - 일반적으로 자바를 실행할 때는 java -jar 라는 명령어를 사용하지만, 이렇게 하면 사용자가 터미널 접속을 끊을 때 어플리케이션도 같이 종료되므로, 어플리케이션 실행자가 터미널을 종료해도 어플리케이션은 계속 구동될 수 있도록 nohup 명령어를 사용한다.

```sh
$ sudo chmod +x ./deploy.sh
$ ./deploy.sh
```

실행권한을 주고, deploy.sh를 실행시킨다.

## 외부 Security 파일 등록

> 보통 보안과 밀접한 파일들은 git에 올리지 않고 gitignore에 등록하여 따로 관리를 한다.
>
> EC2 서버에 git에 있는 내용만을 그대로 내려받았기 때문에 gitignore에 등록된 것들 중 필요한 것을 서버에서 갖고 있게 만들어야 한다.
>
> 배포하려는 프로젝트에는 구글로그인, 네이버로그인 같은 기능들이 있어서, 다음과 같은 에러가 발생하여 어플리케이션 실행에 실패하게 된다.

```shell
***************************
APPLICATION FAILED TO START
***************************

Description:
Method springSecurityFilterChain in org.springframework.security.config.annotation.web.configuration.
WebSecurityConfiguration required a bean of type 'org.springframework.security.oauth2.client.registration.ClientRegistrationRepository' that could not be fount.

The following candidates were found but could not be injected:
	- Bean method 'clientRegistrationRepository' in 'OAuth2ClientRegistrationRepositoryConfiguration' not loaded because OAuth2 Clients Configured Condition registered clients is not available
	
Action:

Consider revisiting the entries above or defining a bean of type 'org.springframework.security.oauth2.client.registration.ClientRegistrationRepository' in your configuration.
```

- ClientRegistrationRepository를 생성하려면 clientId와 clientSecret이 필수이지만, gitignore에 등록된 application-oauth.properties가 없어 문제가 생겼다.

- step2, step3에도 쓰기 위해서 다음 경로에 application-oauth.properties를 만들고, 로컬에 있는 application-oauth.properties의 내용을 똑같이 붙여넣는다.

  ```shell
  vim /home/ec2-user/app/application-oauth.properties
  ```

- 그리고 deploy.sh 파일을 다음과 같이 수정한다.

  ```shell
  ...
  nohup java -jar \
  	-Dspring.config.location=classpath:/application.properties,/home/ec2-user/app/application-oauth.properties \
  	$REPOSITORY/$JAR_NAME 2>&1 &
  ```

  - -Dspring.config.location
    - 스프링 설정 파일 위치를 지정한다. 쓸데없는 스페이스와 탭을 주면 다르게 동자갛ㄴ다.
    - 기본 옵션들을 담고 있는 application.properties과 OAuth 설정들을 담고 있는 application-oauth.properties의 위치를 지정한다.
    - classpath가 붙으면 jar 안에 있는 resources 디렉토리를 기준으로 경로가 생성된다.
    - application-oauth.properties는 외부에 파일이 있기 때문에 절대경로를 사용한다.

```shell
o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
o.s.b.a.w.s.WelcomePageHandlerMapping    : Adding welcome page template: index
o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
s.a.ScheduledAnnotationBeanPostProcessor : No TaskScheduler/ScheduledExecutorService bean found for scheduled processing
com.yneerg.admin.book.Application        : Started Application in 12.179 seconds (JVM running for 13.586)
```

deploy.sh를 다시 실행하면 nohup.out에 저런 내용을 볼 수 있을 것이다.

## 스프링부트 프로젝트로 RDS 접근

> RDS는 MariaDB를 사용중이다. 이 MariaDB에서 스프링부트 프로젝트를 실행하기 위해선 다음 작업들이 필요하다.
>
> 1. 테이블생성: H2에서 자동 생성해주던 테이블들을 MariaDB에선 직접 쿼리를 이용해 생성한다.
> 2. 프로젝트설정: 자바 프로젝트가 MariaDB에 접근하려면 DB 드라이버가 필요하다. MariaDB에서 사용가능한 드라이버를 프로젝트에 추가한다.
> 3. EC2(리눅스 서버) 설정: DB의 접속정보는 중요하게 보호해야 할 정보이므로, EC2 서버 내부에서 접속 정보를 관리하도록 설정한다.

### RDS 테이블 생성

> 여기선 `JPA가 사용될 엔티티 테이블`과 `스프링 세션이 사용될 테이블` 두가지 종류를 생성한다.

1. JPA가 사용할 테이블은 `테스트 코드 수행 시 로그로 생성되는 쿼리를 사용하면 된다.`
   - 테스트 코드를 수행될 때 발생하는 로그에서 create table 부분부터 복사하여 RDS에 반영한다.
2. 스프링 세션 테이블은 `schema-mysql.sql`파일에서 확인할 수 있다. Intellij에서 검색하면 찾을 수 있다.
   - 해당 파일에 있는 세션테이블 또한 RDS에 반영한다.

### 프로젝트 설정

> 먼저 MariaDB 드라이버를 build.gradle에 등록한다.
>
> ```java
> implementation("org.mariadb.jdbc:mariadb-java-client")
> ```

그리고 서버에서 구동될 환경을 하나 구성한다.(src/main/resources/에 application-real.properties)

application-real.properties로 파일을 만들면 profile=real인 환경이 구성된다. 

```properties
spring.profiles.include=oauth,real-db
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL57Dialect
spring.jpa.properties.hibernate.dialect.storage_engine=innodb
spring.session.store-type=jdbc
```

### EC2 설정

> OAuth와 마찬가지로 RDS 접속 정보도 보호해야할 정보이니 EC2 서버에 직접 설정 파일을 둔다.
>
> ```shell
> vim /home/ec2-user/app/application-real-db.properties
> ```

다음과 같은 내용을 작성한다.

```shell
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show_sql=false

spring.datasource.hikari.jdbc-url=jdbc:mariadb://RDS엔드포인트주소:포트번호(기본값은 3306)/RDS DB 인스턴스 이름
spring.datasource.hikari.username=RDS사용자정보
spring.datasource.hikari.password=RDS비밀번호
spring.datasource.hikari.driver-class-name=org.mariadb.jdbc.Driver
```

- spring.jpa.hibernate.ddl-auto=none
  - JPA로 테이블이 자동 생성되는 옵션을 None(생성하지 않음)으로 지정한다.
  - RDS에는 실제 운영으로 사용될 테이블이니 절대 스프링부트에서 새로 만들지 않도록 해야한다.
  - 이 옵연을 하지 않으면 자칫 테이블이 모두 새로 생성될 수 있으므로 주의가 필요하다.

마지막으로 deploy.sh가 real profile을 쓸 수 있도록 다음과 같이 수정한다.

```shell
...
nohup java -jar \
 -Dspring.config.location=classpath:/application.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-real-db.properties,classpath:/application-real.properties \
 -Dspring.profiles.active=real \
 $REPOSITORY/$JAR_NAME 2>&1 &
```

- -Dspring.profiles.active=real
  - application-real.properties를 활성화시킨다.
  - application-real.properties의 spring.profiles.include=oauth,real-db 옵션 때문에 real-db역시 함께 활성화 대상에 포함된다.

이제 다시 deploy.sh를 실행한 후, nohup.out에서 다음과 같은 로그가 보이고, `curl localhost:8080` 명령어 입력 시에 html 코드가 보인다면 성공적으로 수행된 것이다.

```sh
Tomcat started on port(s): 8080 (http) with context path ''
Started Application in ~~ seconds (JVM running for ~~~)
```