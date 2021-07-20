## GCP 설정



### 컨테이너 레지스트리 사용 설정

> `Google Container Registry API` 활성화가 필요하다.
>
> 빌드를 통해 생성된 도커 컨테이너를 보관할 컨테이너 저장소를 사용하기 위함이다.

GCP console 에서 `[좌측메뉴]` - `[CI/CD]` - `[Container Registry]` 에서 설정이 가능하다.



### config 설정 및 환경변수 등록

터미널 환경에서 아래 명령어들을 활용하여 사용할 config 를 설정한다.

```shell
$ gcloud config set project [PROJECT_ID]	// 프로젝트 정보의 프로젝트를 ID로 설정
$ gcloud config set compute/znoe asia-northeast3-a
$ gcloud config list 	// 설정 확인
```



project 아이디를 환경변수로 등록한다.

```shell
$ export PROJECT_ID=$(gcloud config get-value core/project)
$ echo $PROJECT_ID	// 잘 등록되었는지 확인
```



## GKE(Google Kubernetes Engine) 생성

> `GKE`는 구글에서 제공하는 관리형 쿠버네티스 서비스로, 손쉽게 쿠버네티스 클러스터를 생성 및 관리할 수 있다.

```shell
$ gcloud container clusters create [CLUSTER_NAME] \
			--zone asia-northeast3-a --machine-type n1-standard-2 --num-nodes 3 \
			--enable-autoscaling --min-nodes 1 --max-nodes 5
```

- `[CLUSTER_NAME]`:  사용할 쿠버네티스 클러스터 이름
- `zone`: 사용할 서비스 존
- `machine-type`: 인스턴스 머신 유형
- `num-nodes`: 최초 생성 노드 수
- `enable-autoscaling`: 노드의 오토스케일링 사용 옵션
- `min-nodes`: 최소 노드 수
- `max-nodes`: 최대 노드 수

![gcp 배포 환경 구성-1](https://user-images.githubusercontent.com/52652338/126029598-ecb59315-3e32-4de4-828e-0ece9aeb3b7f.PNG)



앞에서 생성한 클러스터 인증 과정이 필요하다.

```sh
$ gcloud container clusters get-credentials [CLUSTER_NAME]
```



어플리케이션을 배포할 GCP 및 GKE 설정은 끝났다.