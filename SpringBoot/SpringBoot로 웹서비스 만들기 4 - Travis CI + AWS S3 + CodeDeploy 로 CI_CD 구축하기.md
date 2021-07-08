> 여기서는 배포할 프로젝트가 있다는 가정하에 설명을 하겠다.

## CI/CD 란?

- 코드 버전 관리를 하는 VCS 시스템(Git, SVN 등)에 Push가 발생하면 자동으로 테스트와 빌드가 수행되어 `안정적인 배포 파일을 만드는 과정`을 `CI(Continuous Intgration - 지속적 통합)`라고 한다.
- `이 빌드 결과를 자동으로 운영 서버에 무중단 배포까지 진행되는 과정`을 `CD(Continuous Deploy - 지속적 배포)` 라고 한다.
- 일반적으로 CI만 구축되어 있지는 않고, CD도 함께 구축한다.

> 주의할 점은, `CI 도구를 도입했다고 해서 CI를 하고 있는 것은 아니다.` 라는 것이다.

마틴 파울러는 CI에 대해 다음과 같은 4가지 규칙을 이야기한다.

- 모든 소스 코드가 살아 있고(현재 실행되고) 누구든 현재의 소스에 접근할 수 있는 단일 지점을 유지할 것
- 빌드 프로세스를 자동화해서 누구든 소스로부터 시슽메을 빌드하는 단일 명령어를 사용할 수 있게 할 것
- 테스팅을 자동화해서 단일 명령어로 언제든지 시스템에 대한 건전한 테스트 슈트를 실행할 수 있게 할 것
- 누구나 현재 실행 파일을 얻으면 지금까지 가장 완전한 실행 파일을 얻었다는 확신을 하게 할 것

특히나 중요한 것은 `테스팅 자동화`로, 지속적으로 통합하기 위해서는 무엇보다 이 프로젝트가 `완전한 상태`임을 보장하기 위한 테스트 코드가 구현되어 있어야만 한다.

## Travis CI 연동하기

> Travis CI 는 깃허브에서 제공하는 무료 CI 서비스이다.
>
> Jenkins 와 같은 CI 도구도 있지만, Jenkins 는 설치형이기 때문에 이를 위한 EC2 instance가 하나 더 필요하다.
>
> 따라서 오픈소스 웹 서비스인 Travis CI도 작은 서비스로 시작하기에는 좋다.
>
> (AWS에서도 CI 도구로 CodeBuild를 제공하지만, 빌드 시간만큼 요금이 부과되는 구조라 초기에 사용하기에는 부담스럽다.)
>
> Travis CI는 개인 계정의 public repository만 무료이다. (private repository는 유료다.)

### Travis CI 웹 서비스 설정

먼저 Travis CI 웹사이트에서의 설정 작업이 필요하다.

1. `https://travis-ci.com/`에서 Github 계정으로 로그인을 한 뒤, 우측 상단에 `계정명` - `Settings`를 클릭한다.
2. 원하는 Github Repository 를 검색해서 찾은 다음, 상태바를 활성화(Activate) 시킨다.

### 프로젝트 설정

Travis CI의 상세한 설정은, 프로젝트에 존재하는 `.travis.yml 파일`로 할 수 있다. `build.gradle`에 같은 곳에 위치시킨다.

파일 확장자 `.yml`은 `YAML(야믈)`이라고 하는데, 쉽게 말하여 **JSON에서 괄호를 제거한 것**이다.

```yaml
# .travis.yml

language: java
jdk:
	- openjdk11
	
branches:
	- only:
		- master
		
# Travis CI 서버의 Home
cache:
	directories:
		- '$HOME/.m2/repository'
		- '$HOME/.gradle'
		
script: "./gradlew clean build"

# CI 실행 완료 시 메일로 알람
notifications:
	email:
		recipients:
			- 본인 메일 주소
```

- `branches`: Travis CI를 어느 브랜치가 푸시될 때 수행할지 지정한다.
  - 여기서는 `오직 master 브랜치에 push 될 때`만 수행한다.
- `cache`: Gradle을 통해 의존성을 받게 되면, 이를 해당 디렉토리에 캐시하여, `같은 의존성은 다음 배포 때부터 다시 받지 않도록` 설정한다.
- `script`: 브랜치에 push 되었을 때 수행하는 명령어이다. 
  - 여기서는 프로젝틑 내부에 둔 gradlew을 통해 clean & build 를 수행한다.
- `notifications`: Travis CI 실행 완료 시 자동으로 알람이 가도록 설정한다.
  - `on_success`, `on_failure`: build가 성공했을 때, 실패했을 때의 notification을 설정한다.
    - `always`: build가 성공하든 실패하든 notification을 보낸다.
    - `never`: build가 성공하든 실패하든 notification을 보내지 않는다.
    - `change`: build status가 바뀔 때에만 notification을 보낸다. (failed -> success | success -> failed)

## Travis CI + AWS S3 + AWS CodeDeploy 전체 구조

> `S3(Simple Storage Service)`란 AWS에서 제공하는 `일종의 파일 서버`이다.
>
> 이미지 파일을 비롯한 정적파일들을 관리하거나, 배포 파일들을 관리하는 등의 기능을 지원한다.
>
> 보통 이미지 업로드를 구현한다면 이 S3 를 이용하여 구현하는 경우가 많다.

S3를 비롯한 AWS 서비스와 Travis CI를 연동하게 되면, 전체 구조는 다음과 같다.

![travis ci + aws s3 + aws codedeploy - 1](C:\Users\82102\OneDrive\티스토리\SpringBoot\image\travis ci + aws s3 + aws codedeploy - 1.jpg)

- 실제 배포는 AWS CodeDeploy를 이용하지만, S3연동이 먼저 필요한 이유는 `Jar 파일을 전달하기 위해서`이다.
- CodeDeploy는 저장 기능이 없다. 그래서 Travis CI가 빌드한 결과물을 받아서 CodeDeploy가 가져갈 수 있도록 보관할 수 있는 공간이 필요한데, 이 때문에 S3를 사용한다.
- CodeDeploy에서는 Github 코드를 가져오는 기능을 지원하기 때문에 빌드도 하고 배포도 할 수 있지만, 빌드 없이 배포만 필요할 때 대응하기 어렵다.
- 빌드와 배포는 분리하는 것이 좋다. 빌드와 배포가 분리되어 있으면 예전에 빌드되어 만들어진 Jar를 재사용하면 되지만, CodeDeploy가 빌드와 배포를 모두 한다면 항상 빌드를 하게 되므로 확장성이 많이 떨어진다.

## Travis CI 와 AWS S3 연동하기

### AWS Key 발급

> 일반적으로 AWS 서비스에 `외부 서비스가 접근할 수 없다.` 그러므로 `접근 가능한 권한을 가진 Key`를 생성해서 사용해야한다.
>
> AWS에서는 이러한 인증과 관련된 기능을 제공하는 서비스로 `IAM(Identity and Access Management)`이 있다.

`IAM`은 AWS에서 제공하는 서비스의 접근 방식과 권한을 관리한다.

따라서 Travis CI가 AWS S3와 CodeDeploy에 접근할 수 있도록 권한 설정을 해주어야 한다.

- AWS 웹 콘솔에서 `IAM`을 검색하여 이동한다. 왼쪽 사이드바에서 `사용자` - `사용자 추가` 버튼을 차례로 클릭한다.
- 생성할 사용자의 이름과 액세스 유형을 선택한다. 액세스 유형은 `프로그래밍 방식 액세스`이다. (이름은 식별가능한 아무거나)
- 사용자 권한 설정에서는 `기존 정책 직접 연결`을 선택하고, `AmazonS3FullAccess`와 `AWSCodeDeployFullAccess`를 체크하여 권한을 추가한다.
- 태그는 Name 값을 지정하는데, 본인이 식별 가능한 정도의 이름으로 만든다.

최종 생성이 완료되면, `액세스 키 ID`와 `비밀 액세스 키`가 생성되는데, 이 두 값이 Travis CI에서 사용될 키 값이다.

### Travis CI에 키 등록

Travis CI 웹사이트의 대시보드에서 현재 프로젝트 Repository로 이동한다.

우측 상단의 `More options` - `Settings` 를 차례로 클릭하여, 설정 화면을 밑으로 내리다보면 `Environment Variables` 항목이 있다.

AWS IAM 에서 발급받은 두 키 값을 다음과 같이 등록한다.

- AWS_ACCESS_KEY: 액세스 키 ID 
- AWS_SECRET_KEY: 비밀 액세스 키

여기에 등록된 값들은 `.travis.yml`에서 `$AWS_ACCESS_KEY`, `$AWS_SECRET_KEY`란 이름으로 사용할 수 있다.

### AWS S3 버킷 생성

Travis CI에서 생성된 Build 파일을 저장할 공간이 필요한데, 그 역할을 할 수 있는 것이 S3이다.

S3에 저장된 Build 파일은 이후 AWS의 CodeDeploy에서 배포할 파일로 가져가도록 구성할 것이다.

- AWS 웹 콘솔에서 `S3`를 검색하여 이동한 후, `버킷 만들기`를 누른다.
- 버킷 이름을 작성할 때, 이 버킷에 `어떤 파일들이 저장될 것`인지를 알 수 있도록 작명하는 것이 좋다.
- 버킷 버전 설정에서는 별다르게 건드릴 설정이 없다.
- 버킷의 보안과 권한 설정 부분에서는 `퍼블릭 액세스 차단(버킷 설정)`부분에서 `모든 퍼블릭 액세스 차단`을 체크해줘야 한다. 그러지 않으면 누구나 다 내려받을 수 있어 코드나 설정값, 주요 키값들이 탈취될 수 있다. (실제 서비스에서는 위험하다.)

### .travis.yml 추가

```yaml
...
before_deploy:
	- zip -r 깃헙Repository이름 *
	- mkdir -p deploy
	- mv 깃헙Repository이름.zip deploy/깃헙Repository이름.zip
	
deploy:
	- provider: s3
		access_key_id: $AWS_SUCCESS_KEY	# Travis repo settings에 설정된 값
		secret+access_key: $AWS_SECRET_KEY	# Travis repo settings에 설정된 값
		bucket: 설정한S3버킷이름	# S3 bucket
		region: ap-northeast-2	# AWS region
		skip_cleanup: true
		acl: private # zip 파일 접근을 private으로
...
```

- `before_deploy`:  deploy 명령어가 실행되기 전에 수행된다. CodeDeploy는 Jar 파일을 인식하지 못하므로 `Jar + 기타 설정 파일들`을 모아 압축(zip)한다
- `zip -r 깃헙Repository이름 *`: 현재 위치의 모든 파일을 '깃험Repository이름' 이름으로 압축(zip)한다. 명령어의 마지막 위치는 자신의 프로젝트 이름이어야 한다.
- `mkdir -p deploy`: deploy라는 디렉토리를 Travis CI가 실행 중인 위치에서 생성한다.
- `mv 깃헙Repository이름.zip deploy/깃헙Repository이름.zip`: 앞 파일을 뒤 파일 위치로 옮긴다.
- `deploy`: S3로 파일 업로드 혹은 CodeDeploy로 배포 등 외부 서비스와 연동될 행위들을 선언한다.
- `local_dir: deploy`: 앞에서 생성한 deploy 디렉토리를 지정한다. 해당 위치의 파일들만 S3로 전송한다.

여기까지 진행한 내용을 Github에 Commit하고 Push한다.

그리고 난 뒤 Travis CI 웹에서 자동으로 빌드가 진행되는지를 확인한다. 

```shell
Installing deploy dependencies
Logging in with Access Key: ****************ZCHQ
Version 2 of the Ruby SDK will enter maintenance mode as of November 20, 2020. To continue receiving service updates and new features, please upgrade to Version 3. More information can be found here: https://aws.amazon.com/blogs/developer/deprecation-schedule-for-aws-sdk-for-ruby-v2/
Triggered deployment "d-IH7WBO9L9".
Deployment successful.
dpl.2
Preparing deploy
dpl.3
Deploying application
Done. Your build exited with 0.
```

Travis CI 웹사이트에서 위와 같은 로그가 나온다면 성공한 것이다.

![travis ci + aws s3 + aws codedeploy - 2](C:\Users\82102\OneDrive\티스토리\SpringBoot\image\travis ci + aws s3 + aws codedeploy - 2.jpg)

그리고 S3 버킷을 가보면 업로드가 성공한 것을 확인할 수 있다.

## Travis CI와 AWS S3, CodeDeploy 연동하기

### EC2에 IAM 역할 추가하기

> 배포 대상인 EC2가 CodeDeploy를 연동 받을 수 있게 IAM 역할을 하나 더 생성해야 한다.
>
> #### IAM의 사용자 vs 역할
>
> - `역할`: AWS 서비스에만 할당할 수 있는 권한. EC2, CodeDeploy, SQS 등
> - `사용자`: AWS 서비스 외에 사용할 수 있는 권한. 로컬 PC, IDC 서버 등 
>
> 지금 필요한 건 `EC2에서 사용할 것`이기 때문에 사용자가 아닌 역할로 처리한다.

S3와 마찬가지로 IAM을 검색하고, `역할` - `역할 만들기` 버튼을 차례로 클릭한다.

- 서비스 선택에서는 `AWS 서비스` - `EC2`를 차례로 선택한다.
- 정책에선 `AmazonEC2RoleforAWS-CodeDeploy`를 선택하고, 태그는 원하는 이름으로 지어도 된다.
- 역할의 이름은 식별하기 쉬운 이름으로 짓는다.

만든 역할을 EC2 서비스에 등록하여야 한다. 

EC2 instance 목록으로 이동한 뒤, `해당 인스턴스 오른쪽 클릭` - `보안` - `IAM 역할 수정`을 차례로 클릭한다.

역할을 수정하고 해당 EC2 instance를 재부팅한다.(재부팅해야만 역할이 정상적으로 적용된다.)

### CodeDeploy 에이전트 설치

> EC2에 ssh로 접속하여 다음 명령어를 입력한다.
>
> ```shell
> aws s3 cp s3://aws-codedeploy-ap-northeast-2/latest/install . --region ap-northeast-2
> ```

install 파일에 실행 권한이 없으니 실행 권한을 추가하고, install 파일로 설치를 진행한다.

```shell
chmod +x ./install
sudo ./install auto
```

만약 설치 중에 `/usr/bin/env: ruby: No such file or directory`라는 에러가 뜬다면, `루비`라는 언어가 설치되지 않았기 때문이므로 `sudo yum install ruby`명령어로 루비를 설치해준다.

성공적으로 설치가 완료됐다면 Agent가 정상적으로 실행되고 있는지 상태 검사를 한다.

```sh
sudo service codedeploy-agent status
# 다음과 같이 running 메시지가 출력되면 정상이다.
# The AWS CodeDeploy agent is running as PID xxx
```

### CodeDeploy를 위한 권한 생성

> CodeDeploy에서 EC2에 접근하려면 마찬가지로 권한이 필요하다. AWS의 서비스이니 `IAM 역할`을 생성한다.

- 정책에서 `AWSCodeDeployRole`만 추가한 후, 식별할 수 있는 이름으로 역할을 생성한다.

### CodeDeploy 생성

> CodeDeploy는 AWS의 배포 3형제 중 하나이다. 배포 3형제에 대해 간단하게 소개하면 다음과 같다.
>
> - CodeCommit
>   - Github와 같은 코드 저장소의 역할을 한다.
>   - private 기능을 지원한다는 강점이 있지만, 현재 Github에서 무료로 private 지원을 하고 있어 거의 사용되지 않는다.
> - CodeBuild
>   - Travis CI와 마찬가지로 빌드용 서비스이다.
>   - 멀티 모듈을 배포해야 하는 경우 사용해 볼만 하지만, 규모가 있는 서비스에서는 대부분 젠킨스/팀시티 등을 이용하므로 거의 사용할 일이 없다.
> - CodeDeploy
>   - AWS의 배포 서비스이다. CodeDeploy는 딱히 대체재가 없다.
>   - 오토 스케일링 그룹 배포, 블루-그린 배포, 롤링 배포, EC2 단독 배포 등 많은 기능을 지원한다.
>
> 이 포스팅에서는 CodeCommit 역할은 Github가, CodeBuild 역할은 Travis CI가 하고 있다.

- CodeDeploy 서비스로 이동하여 `애플리케이션 생성`버튼을 클릭한다.
- 생성할 CodeDeploy의 이름과 컴퓨팅 플랫폼을 선택한다. 컴퓨팅 플랫폼은 `EC2/온프레미스`를 선택한다.
- 생성이 완료되면 `배포 그룹 생성`을 클릭하여 배포 그룹 이름과 서비스 역할을 등록한다. (배포 그룹 이름은 식별할 수 있는 이름으로, 서비스 역할은 좀 전에 생성한 CodeDeploy용 IAM 역할을 선택한다.)
- 배포 유형에서는 `현재 위치`를 선택한다. 만약 배포할 서비스가 2대 이상이라면 `블루/그린`을 선택한다.
- 환경 구성에서는 `Amazon EC2 인스턴스`를 체크한다.
- 배포 구성은 `CodeDeployDefault.AllAtOnce`로 하고, 로드 밸런싱은 체크 해제한다.
  - 배포 구성이란 한번 배포할 때 몇 대의 서버에 배포할지를 결정한다. (2대 이상이라면 1대씩 배포할지, 30% or 50%로 나눠서 배포할지 등)

### Travis CI, S3, CodeDeploy 연동

먼저 S3에서 넘겨줄 zip 파일을 저장할 디렉토리를 생성한다. EC2 서버에 접속하여 다음과 같이 디렉토리를 생성한다.

```sh
mkdir ~/app/step2 && mkdir ~/app/step2/zip
```

Travis CI의 Build가 끝나면 S3에 zip파일이 전송되고, 이 zip 파일은 /home/ec2-user/app/step2/zip으로 복사되어 압축을 풀 예정이다.

- Travis CI의 설정은 `.travis.yml`로 진행하고, AWS CodeDeploy의 설정은 `appspec.yml`로 진행하겠다.

`appspec.yml`은 `.travis.yml`과 같은 곳에 위치시킨다.

```yaml
# appspec.yml
version: 0.0
os: linux
files:
  - source: /
    destination: /home/ec2-user/app/step3/zip/
    overwrite: yes
```

- `version: 0.0`:  CodeDeploy 버전을 이야기 한다. 프로젝트 버전이 아니므로 0.0외에 다른 버전을 사용하면 오류가 발생한다.
- `source`: CodeDeploy에서 전달해 준 파일 중 destination으로 이동시킬 대상을 지정한다. 루트경로(/)를 지정하면 전체 파일을 이야기한다.
- `destination`: source에서 지정된 파일을 받을 위치이다. 이후 Jar를 실행하는 파일들은 destination에서 옮긴 파일들로 진행된다.
- `overwrite`: 기존에 파일들이 있으면 덮어쓸지를 결정한다.

.travis.yml에도 CodeDeploy의 내용을 추가한다. deploy 항목에 다음 코드를 추가한다.

```yaml
# .travis.yml
...
deploy:
	- provider: s3
	...
	
	- provider: codedeploy
		access_key_id: $AWS_SUCCESS_KEY	# Travis repo settings에 설정된 값
		secret+access_key: $AWS_SECRET_KEY	# Travis repo settings에 설정된 값
		bucket: 설정한S3버킷이름	# S3 bucket
		key: 깃헙Repository이름.zip	# 빌드 파일을 압축해서 전달
		bundle_type: zip
		application: 깃헙Repository이름	# 웹 콘솔에서 등록한 CodeDeploy 애플리케이션
		deployment_group: CodeDeploy배포그룹	# 웹 콘솔에서 등록한 CodeDeploy 배포 그룹
		region: ap-northeast-2
		wait-until-deployed: true
...
```

여기까지 진행한 내용을 Github에 Commit하고 Push한다. Push가 완료되면 Travis CI가 자동으로 시작될 것이고 작업이 완료되면 웹 콘솔 CodeDeploy 화면에서 배포가 수행되는 것을 확인할 수 있따.

```shell
cd /home/ec2-user/app/step2/zip
ll
```

배포가 끝나면, 프로젝트 파일들이 정상적으로 잘 도착했는지 파일 목록을 확인해본다.

## 배포 자동화 구성

### deploy.sh 파일 추가

> 스프링부트 프로젝트의 /src와 같은 위치에 /script 디렉토리를 생성한 후 여기에 deploy.sh 스크립트를 생성한다.

```sh
#!/bin/bash

REPOSITORY=/home/ec2-user/app/step2
PROJECT_NAME=프로젝트이름

echo "> Build 파일 복사"

cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> 현재 구동중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -fl $PROJECT_NAME | grep java | awk '{print $1}')

echo "> 현재 구동 중인 애플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
    echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
    echo "> kill -15 $CURRENT_PID"
    kill -15 $CURRENT_PID
    sleep 5
fi

echo "> 새 어플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

nohup java -jar \
    -Dspring.config.location=classpath:/application.properties,classpath:/application-real.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-real-db.properties \
    -Dspring.profiles.active=real \
    $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &
```

- `CURRENT_PID`: 현재 수행 중인 스프링부트 애플리케이션의 프로세스 ID를 찾는다. (실행 중이면 종료하기 위함.)
- `chmod +x $JAR_NAME`: Jar 파일은 실행 권한이 없는 상태이므로, nohup으로 실행할 수 있게 실행 권한을 부여한다.
- `$JAR_NAME > $REPOSITORY/nohup.out 2>&1 &`: nohup 실행 시 CodeDeploy는 `무한 대기`한다.
  - 이 이슈를 해결하기 위해 nohup.out 파일을 표준 입출력용으로 별도로 사용한다.
  - 이렇게 하지 않으면 nohup.out 파일이 생기지 않고, CodeDeploy 로그에 표준 입출력이 출력된다.
  - nohup이 끝나기 전까지 CodeDeploy도 끝나지 않으니 꼭 이렇개 해야만 한다.

### .travis.yml 파일 수정

현재는 프로젝트의 모든 파일을 zip파일로 만드는데, 실제로 필요한 파일들은 `Jar, appspec.yml, 배포를 위한 스크립트들`이다.

이 외 나머지는 배포에 필요하지 않으니 `before_deploy` 부분을 수정한다.

```yaml
#	.travis.yml
...
before_deploy:
  - mkdir -p before-deploy # zip에 포함시킬 파일들을 담을 디렉토리 생성
  - cp scripts/*.sh before-deploy/
  - cp appspec.yml before-deploy/
  - cp build/libs/*.jar before-deploy/
  - cd before-deploy && zip -r before-deploy * # before-deploy로 이동후 전체 압축
  - cd ../ && mkdir -p deploy # 상위 디렉토리로 이동후 deploy 디렉토리 생성
  - mv before-deploy/before-deploy.zip deploy/빌드파일이름.zip # deploy로 zip파일(앞에선 '깃헙Repository이름'으로 빌드 파일 이름을 지정했음) 이동
```

- Travis CI는 S3로 특정 파일만 업로드가 불가능하다. 디렉토리 단위로만 업로드할 수 있기 때문에 before-deploy 디렉토리는 항상 생성한다.
- before-deploy에는 zip 파일에 포함시킬 파일들을 저장한다.
- zip -r 명령어를 통해 before-deploy 디렉토리 전체 파일을 압축한다.

### appspec.yml 파일 수정

> appspec.yml 파일에 다음 코드를 추가한다. 이 때 들여쓰기를 주의하여 작성하여야 한다.

```yaml
#	appspec.yml
...
permissions:
	- object:	/
		pattern: "**"
		owner: ec2-user
		group: ec2-user

hooks:
	ApplicationStart:
		- location: deploy.sh
			timeout: 60
			runas: ec2-user
```

- `permissions`: CodeDeploy에서 EC2 서버로 넘겨준 파일들을 모두 ec2-user 권한을 갖도록 한다.
- `hooks`: CodeDeploy배포 단계에서 실행할 명령어를 지정한다.
  - ApplicationStart라는 단계에서 deploy.sh를 ec2-user 권한으로 실행하게 한다.
  - timeout: 60으로 스크립트 실행 60초 이상 수행되면 실패가 된다.(무한정 기다릴 수 없으니 시간제한을 둔 것)

지금까지 변경한 점들을 Github에 Commit하고 Push하면, Travis CI에서 빌드가 성공하고 CodeDeploy에서도 배포가 성송한 것을 확인할 수 있다.

### CodeDeploy 로그 확인

> CodeDeploy에 관한 대부분 내용은 `/opt/codedeploy-agent/deployment-root`에 있다.

`/opt/codedeploy-agent/deployment-root`로 이동하여 `ll`명령어를 쳐서 목록을 확인해 보자.

- `영문과 숫자, 하이픈(-)으로 이루어진 디렉토리 이름`은 CodeDeploy ID이다.
  - 사용자마다 고유한 ID가 생성되어 각자 다른 ID로 발급된다.
  - 해당 디렉토리로 들어가보면 `배포한 단위별로 배포 파일들`이 있다.
  - 본인의 배포파일이 정상적으로 왔는지 확인해 볼 수있다.
- `/opt/codedeploy-agent/deployment-root/deployment-logs/codedeploy-agent-deployments.log`
  - CodeDeploy 로그 파일로, CodeDeploy로 이루어지는 배포 내용 중 표준 입출력 내용은 모두 여기에 담겨있다. (작성한 echo 내용도 모두 표기된다.)

지금까지 과정을 따라왔다는 가정 하에, 이제는 작업이 끝난 내용을 Github의 해당 Repo의 Master 브랜치에 Push만 하면, 자동으로 빌드/배포가 이루어진다.

#### 하지만 배포하는 동안 스프링부트 프로젝트는 종료 상태가 되어 서비스를 이용할 수 없다는 문제점이 있긴 하다.