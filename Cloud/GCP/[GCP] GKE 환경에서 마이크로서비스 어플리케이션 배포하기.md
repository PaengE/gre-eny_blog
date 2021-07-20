> 이 포스팅에서는 배포할 SpringBoot 프로젝트가 있다고 가정하고 진행한다.
>
> 본인의 프로젝트 버전은 아래와 같다.
>
> - SpringBoot 2.4.4
> - Maven 3.3.9



## Docker image 빌드 및 Google Container Registry 등록

> 여기서는 `jib` 라는 오픈소스 라이브러리를 사용한다.
>
> `jib`는 2019년 구글에서 정식 출시한 자바 애플리케이션을 컨테이너화하는 오픈소스 기반 도구로서, jib 라이브러리를 사용하면 명령어 한 줄로 도커 데몬을 별도로 설치할 필요도 없고 도커 파일이 없어도 도커 이미지를 생성할 수 있다.
>
> Dependency만 추가해주면 손쉽게 사용할 수 있다. 여기서는 다루지 않겠다.
>
> - `jib`로 자바 컨테이너 빌드 방법: https://cloud.google.com/java/getting-started/jib?hl=ko



프로젝트가 있는 디렉토리로 이동하여, 아래와 같은 명령어를 사용하면 된다.

도커 이미지를 빌드 한 후 컨테이너 레지스트리에 푸시하는 과정이다.

`./mvnw: command not found` 와 같은 에러를 뱉는다면 `chmod +x mvnw` 을 선행한다.

```shell
./mvnw package -Pprod -DskipTests jib:build -Dimage=gcr.io/[PROJECT_ID]/[IMAGE_NAME]:[TAG]
```

- `Pprod`: 프로파일 정보
- `DskipTests`: 메이븐 빌드 시 테스트 생략 여부
- `Dimage`: 컨테이너(도커) 이미지 ID. 구글 컨테이너 레지스트리에 등록하려면 아래와 같은 명명 규칙을 준수해야한다.
  - `gcr.io/{프로젝트 ID}/{이미지 명}:{태그}`



아래 이미지처럼 `Container Registry`에 빌드한 도커 이미지가 등록된 것을 확인할 수 있다.

![gke 애플리케이션 배포-1](https://user-images.githubusercontent.com/52652338/126061519-cbaafab3-047b-4138-bc87-132f2e4b3fca.PNG)



## JHipster로 쿠버네티스 배포 구성 파일 생성하기

> JHipster 를 사용하려면 다음 링크를 참고하여 필요한 패키지들을 설치하여야 한다.

- https://www.jhipster.tech/installation/



![gke 애플리케이션 배포-2](https://user-images.githubusercontent.com/52652338/126061520-e21815ff-7970-4fb2-9917-8df69784cb54.PNG)

- `Which *type* of application would you like to deploy?`: 애플리케이션 유형 선택
- `Enter the root directory where your gateway(s) and microservices are located`: 루트 디렉토리 설정
- `Which applications do you want to include in your configuration?`: 설정에 포함할 애플리케이션 설정
- `Do you want to setup monitoring for your applications ?`: 모니터링 설정 여부
- `Which applications do you want to use with clustered databases (only available with MongoDB and Couchbase)`: 데이터베이스 클러스터에 사용할 애플리케이션 선택
- `Enter the admin password used to secure the JHipster Registry`: 레지스트리 관리자 비밀번호 설정(기본값)
- `What should we use for the Kubernetes namespace?`: 쿠버네티스 네임스페이스 설정(기본값)
- `What should we use for the base Docker repository name?`: 도커 이미지 푸시 명령어 설정(기본값)
- `Do you want to enable Istio?`: 이스티오 설정 여부
- `Choose the Kubernetes service type for your edge services`: 쿠버네티스 서비스 유형 선택
- `Do you want to use dynamic storage provisioning for your stateful services?`: 동적 저장서 설정 여부



위 작업이 성공했다면, 아래와 같은 파일들이 생성된다.

![gke 애플리케이션 배포-3](https://user-images.githubusercontent.com/52652338/126061521-64f6df50-eb32-4185-954d-9bd4f53e2340.PNG)

- `kubectl-apply.sh`: 쿠버네티스 리소스를 생성하는 쉘 스크립트
- `{애플리케이션}-deployment.yml`: 쿠버네티스 디플로이먼트 구성 파일
- `{애플리케이션}-service.yml`: 쿠버네티스 서비스 구성 파일
- `{애플리케이션}-mariadb.yml`: MariaDB 구성 파일
- `{애플리케이션}-mongodb.yml`: MongoDB 구성 파일
- `kafka.yml`: 카프카(메시지큐) 구성 파일
- `jhipster-registry.yml`: JHipster 레지스트리 구성 파일
- `application-configmap.yml`: 쿠버네티스 컨피그맵 구성 파일



`MariaDB`를 사용한다면, MariaDB에서 한글을 지원하도록 구성 파일에 옵션값을 추가한다.

```yaml
// 예시: book-k8s/book-mariadb.yml 변경 전
...
	containers:
		- name: mariadb
			image: mariadb:10.6.1
			env:
				- name: MYSQL_ROOT_PASSWORD
					valueFrom:
						secretKeyRef:
							name: book-mariadb
							key: mariadb-root-password
				- name: MYSQL_DATABASE
					value: book
...

// book-k8s/book-mariadb.yml 변경 후
...
	containers:
		- name: mariadb
			image: mariadb:10.6.1
			env:
				- name: MYSQL_ROOT_PASSWORD
					valueFrom:
						secretKeyRef:
							name: book-mariadb
							key: mariadb-root-password
				- name: MYSQL_DATABASE
					value: book
			args:
				- --lower_case_table_names=1
				- --skip-ssl
				- --character_set_server=utf8mb4
				- --explicit_defaults_for_timestamp
...
		
```



`카프카(메시지큐)`를 사용한다면 쿠버네티스 리소스 생성을 위한 쉘 스크립트 순서를 수정한다.

마이크로서비스 애플리케이션이 구동되면서 카프카 구동 여부를 점검하기 때문에 작업 편의상 수행 순서를 조정한다.

```sh
// kubectl-apply.sh 변경 전
...
default() {
	suffix=k8s
	kubectl apply -f registry-${suffix}/
	kubectl apply -f book-${suffix}/
	kubectl apply -f bookcatalog-${suffix}/
	kubectl apply -f gateway-${suffix}/
	kubectl apply -f rental-${suffix}/
	kubectl apply -f messagebroker-${suffix}/
}

// kubectl-apply.sh 변경 후
...
default() {
	suffix=k8s
	kubectl apply -f registry-${suffix}/
	kubectl apply -f messagebroker-${suffix}/
	kubectl apply -f gateway-${suffix}/
	kubectl apply -f book-${suffix}/
	kubectl apply -f bookcatalog-${suffix}/
	kubectl apply -f rental-${suffix}/
}
```



## GCP로 애플리케이션 배포하기

먼저 `JHipster`로 생성한 쿠버네티스 구성 파일 디렉토리로 이동한 뒤

`쿠버네티스 구성 파일(xxx-deployment.yml)` 에서 `containers` - `image` 를 확인해본다.

`image: gcr.io/[프로젝트_ID]/[이미지명]`으로 설정이 되어있는지 확인하고 아니라면 제대로 수정한다.

이는 쿠버네티스 디플로이먼트 리소스를 생성할 때 컨테이너 레지스트리에 등록한 이미지를 내려받을 수 있도록 하기 위함이다. 모든 프로젝트의 파일을 각각 수정해주어야 한다.



이제 아래 명령어로 쿠버네티스 리소스를 생성하는 쉘 스크립트를 실행한다.

이 스크립트를 실행하면 각 서비스 구성 파일을 기반으로 리소스가 생성된다.

```sh
$ ./kubectl-apply.sh -f
```



그리고 아래 명령어로 쿠버네티스 파드 리소스 전체를 조회할 수 있다.

모든 파드의 `STATUS` 항목이 `Running`이면 정상적으로 가동된 것이다.

```sh
$ kubectl get pods
```

![gke 애플리케이션 배포-4](https://user-images.githubusercontent.com/52652338/126061966-d2c0acaa-4945-4797-a4bd-f3906f2337a0.PNG)



쿠버네티스 클러스터에 배포가 완료되면 아래 그림과 같이 클러스터가 구성되어 있을 것이다.

각 마이크로서비스 별 파드가 생상되고, 디플로이먼트, 서비스, 레플리카셋이 각각 생성된다.

![gke 애플리케이션 배포-5](https://user-images.githubusercontent.com/52652338/126061523-1db8e53d-c94c-4c62-9b77-8953040f517f.PNG)



## 배포 서비스 확인하기

`kubectl get svc` 혹은 `kubectl get service/gateway` 명령어를 통하여 `게이트웨이의 EXTERNEL-IP`를 알 수 있다.

브라우저에서 `http://[EXTERNEL-IP]:8080`로 접속을 시도하여 정상접속이 되는지 확인한다.

![gke 애플리케이션 배포-6](https://user-images.githubusercontent.com/52652338/126061524-8dbafadb-ad79-46c2-ab37-531781c68931.PNG)



## 오토스케일링 적용하기

위에서의 `쿠버네티스 클러스터` 그림을 보면 레지스트리를 제외한 나머지 서비스는 모두 1개의 파드만 생성되어 있다.

예를 들어 `bookcatalog` 서비스 사용량이 증가해서 인스턴스 늘리고 싶다면 아래 명령어로 간단하게 인스턴스 수를 조절할 수 있다.

```shell
$ kubectl scale deployment bookcatalog --replicas=3
```



아래 명령어를 통하여 오토스케일링 결과를 확인하면, 파드가 총 3개로 생성된 것을 확인할 수 있다.

```shell
$ kubectl get pod | grep bookcatalog
```

![gke 애플리케이션 배포-7](https://user-images.githubusercontent.com/52652338/126061525-4d1d2d6e-a922-45a3-9885-70e4a435278f.PNG)



## 서비스 삭제하기

쿠버네티스 클러스터에 생성한 리소스를 삭제하지 않으면 과금이 발생한다. 

실습 용도로 사용했다면 반드시 삭제를 하기를 추천한다.



#### 쿠버네티스 클러스터의 리소스 삭제

```shell
$ kubectl delete all --all
```



#### 쿠버네티스 클러스터 삭제

```shell
$ gcloud container clusters delete [CLUSTER_NAME]
```



## Refer to

- https://codelabs.developers.google.com/codelabs/cloud-deploy-website-on-gke#6
