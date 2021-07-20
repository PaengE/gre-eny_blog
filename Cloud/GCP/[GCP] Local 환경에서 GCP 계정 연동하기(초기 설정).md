> [ 목 표 ]
>
> Windows 환경의 터미널과 vscode의 로컬 환경에서 Google Cloud Platform 에 remote 접속하여 제어하는 것. 
>
> GCP 계정과 Remote 접속 환경은 구성되어 있다고 가정한다.



## GCP console cloudshell 을 사용하지 않는 이유

GCP console cloudshell 에서도 동일한 작업을 할 수 있지만 불편하다. 이유는 다음과 같다.



### 타이핑에 딜레이가 존재함

- 별 것 아닐 수 있지만 매우 거슬린다. 그리고 어느정도 길게 명령을 쓰면 줄이 바뀌어 지는데 기존에 있던 글자를 덮어버린다.



### configuration이 초기화됨

- `google cloud sdk` 를 사용하여 remote 접속하는 것이 마음 편한 이유이다.

![gcp 계정 연동-00](https://user-images.githubusercontent.com/52652338/125251791-a6214400-e332-11eb-91ab-9e4e785b4a45.png)

- cloudshell에 재접속을 하면 기존 세션이 끊기고 새로운 세션을 할당받는데 이렇게 되면 세션간 설정을 공유하지 않기 때문에 초기화가 되버린다.
- 근데 다른 작업 몇분하다 다시 돌아오면 걍 연결이 끊겨져있다. (네트워크 문제아님) -> 재접속 불가피 -> 설정초기화



![gcp 계정 연동-01](https://user-images.githubusercontent.com/52652338/125251951-cf41d480-e332-11eb-8d52-c52461e0b505.png)

- 실제로 configuration이 저장되는 곳이 임시 저장소인 `/tmp` 이하이다.
- 저 path를 변경해봐도 재접속을 하면 path 변경 조차도 초기화가 된다.



## GCP 계정 연동하기

Terminal 에서 `gcloud init` 명령어를 실행한다. 

![gcp 계정 연동-1](https://user-images.githubusercontent.com/52652338/125251952-cfda6b00-e332-11eb-9659-aebd00c4446b.png)

- 먼저 `configuration`을 새로 만들지, 기존에 있던걸 Re-initialize 할 것인지 선택한다. 새로 만들자.
  `configuration` 은 일종의 환경설정이다.
- 본인은 이미 계정이 연동되어 있어서 위와 같이 뜨지만, 새 계정 등록을 선택하면 된다.



![gcp 계정 연동-2](https://user-images.githubusercontent.com/52652338/125251955-d0730180-e332-11eb-9028-1febab2347b3.png)

- 위 링크를 `ctrl + 클릭`하면 브라우저가 뜨고, 연동시킬 구글 계정을 로그인한 뒤, 나오는 verfiication code 를 아래에 복사하여 붙여넣으면 된다.



![gcp 계정 연동-3](https://user-images.githubusercontent.com/52652338/125251959-d0730180-e332-11eb-9bdd-667585ecb26e.png)

- `configuration`을 적용시킬 Project를 선택한다.



![gcp 계정 연동-4](https://user-images.githubusercontent.com/52652338/125251962-d10b9800-e332-11eb-9b24-07ed0736d9e6.png)

- ` default Compute Region and Zone` 을 선택한다. 서울 region을 선택한다. (50번)

이제 로컬환경에 GCP 계정이 연동되었다.



![gcp 계정 연동-5](https://user-images.githubusercontent.com/52652338/125251968-d1a42e80-e332-11eb-9bed-72a620a04f11.png)

```shell
gcloud config configurations list
```

- 위 명령어로 새롭게 만들어진 configuration 과 그에 대한 정보를 간략히 볼수 있다.



## Configuration 생성/삭제, 속성 수정

아래의 공식문서에 설명이 잘 되어있으니 참고하면 쉽게 따라할 수 있을 것이다.

- https://cloud.google.com/sdk/docs/configurations

