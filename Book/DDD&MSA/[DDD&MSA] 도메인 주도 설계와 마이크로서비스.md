마이크로서비스를 만들기 위한 가장 효율적인 프로세스는 실제로 동작하는 제품 중심의 반복/점진적 애자일 개발 프로세스이다.

피드백을 통한 지속적인 개선을 추구하는 애자일 프로세스는 가장 효율적인 의사소통 구조와 협업 체계를 가진 다기능 팀을 필요로 하고, 그 다기능 팀이 만들어내는 결과물이 `마이크로서비스`이다.

애자일에는 빨리, 자주 실패를 경험해 보는 것이 중요하기 때문에 단순한 설계를 통해 우선 최소한의 실제로 동작하는 제품(MVP; Minimum Viable Product)을 만들어 자주 배포하는 것이 중요하다. 이러한 과정을 통하여 각 개발팀에 맞는 최적의 개발 프로세스를 지속적으로 향상시킬 수 있다.

애자일 문화는 어느정도 체계적인 소프트웨어 개발 문화에서 시작했다. 따라서 아직 개발 문화가 성숙하지 않은 팀에서 성숙한 개발 문화의 애자일을 적용시킨다면 오히려 더 비효율적일 수도 있다.

그래서 기민한 반복 주기에 적합한, 불필요한 군더더기를 제거하고 핵심 활동에 집중할 수 있는 마이크로서비스 설계 및 개발 방법이 필요하다.



## 도메인 주도 설계와 마이크로서비스

> `도메인 주도 설계(DDD)`는 마이크로서비스의 애플리케이션 개발 측면, 응집성이 있는 도메인 중심의 마이크로서비스를 도출하는 지침 및 마이크로서비스 내부의 비즈니스 로직 설계의 주요한 가이드로 사용되고 있다.



DDD에는 `전략적 설계(strategic design)`와 `전술적 설계(tactical design)`라는 설계 영역이 있다.

- `전략적 설계`: 도메인 전문가 및 기술팀이 함께 모여 유비쿼터스 언어(ubiquitous language)를 통해 도메인 지식을 공유 및 이해하고, 이를 기준으로 개념과 경계를 식별해 `바운디드 컨텍스트(bounded context)`로 정의하고 경계의 관계를 `컨텍스트 맵(context map)`으로 정의하는 활동이다.
- `전술적 설계`: 식별된 바운디드 컨텍스트 내의 도메인 개념인 도메인 모델을 구성하는 유용한 모델링 구성요소들을 설명한다.



## 기민한 설계/개발 프로세스

다음 그림은 DDD를 활용한 스크럼 기반의 마이크로서비스 개발 프로세스이다.

마이크로서비스를 개발하기 위한 핵심적인 설계 영역과 활동을 최소화했고 각 활동별로 최소한의 핵심 산출물을 정의했다.

![agile-1](C:\Users\82102\OneDrive\티스토리\Book\DDD&MSA\image\agile-1.PNG)



### 점진/반복적인 스크럼 생명주기

> 기본 생명주기는 `스크럼`의 `스프린트`를 활용한다.
>
> `스프린트`는 스크럼의 점진, 반복적인 생명주기이며, 보통 1~4주 동안 실행된다.
>
> 백로그(backlog)라는 일감 목록을 기반으로 각 스프린트에 일감이 배분되어 진행되며, 매 스프린트마다 실제로 동작하는 소프트웨어를 시연하고 피드백을 얻는다.



스크림의 주요 개념 및 공정을 간단히 살펴보면 다음과 같다.

- `스크럼 팀`: 스프린트가 진행되는 팀을 스크럼 팀이라고 하며, `스크럼 마스터`와 `팀 멤버`로 구성되는 다기능팀이다. 여러 직능을 가진 전문가들이 같은 팀에 모여 있으므로 의사결정이 빠르고 협업이 긴밀하다. `스크럼 마스터`는 협업을 촉진하고 팀의 장애물을 제거하는 역할을 수행한다.
- `스크럼 미팅`: 매일 각자 돌아가면서 어제 진행된 일, 오늘 진행할 일, 장애물을 주제로 삼아 간단히 업무를 공유하는 기법을 스크럼 미팅이라 한다.
- `스프린트 계획 수립`: 시스템의 모든 요구사항은 제품 백로그에 담긴다. 그다음 일정에 맞게 1~4주 기간의 스프린트를 몇 번 수행할 것인가가 결정된다. 스프린트 횟수가 결정되면 제품 백로그에 담긴 백로그를 각 스프린트에 적절히 배분한다. 스프린트가 종료되면 백로그 완료 일감을 기준으로 `속도`라고 부르는 팀의 생산성이 결정된다. 이 `속도`에 의해 팀의 다음 스프린트 계획이 세워진다. 이러한 과정을 `지속적인 계획(Continuous Planning)`이라고 하며, 기존의 고정된(Fixed) 계획이 아니라 적응적(Adaptive) 계획이라고 볼 수 있다.

![agile-2](C:\Users\82102\OneDrive\티스토리\Book\DDD&MSA\image\agile-2.PNG)

위의 그림을 보면 기존의 고정된 계획은 범위가 곶어돼 있고 일정, 비용, 품질이 그에 맞춰 유동적이었다면, 적응적 계획은 일정, 비용, 품질이 고정돼 있고 그에 따라 범위가 조정되는 것을 볼 수 있다.

팀의 속도에 맞춰 스프린트를 진행하다 보면 일정 내에 완료해야 할 일의 우선순위를 정하는 것이 매우 중요해지고, 이는 가장 우선순위가 높은 시스템의 핵심 기능에 집중할 수 있게 해준다. 

스프린트의 마지막 활동은 `시연`과 `회고`이다. 간단하게 알아보자.

- `시연`: 시연은 초기에 정의한 백로그가 모두 구현되고 그 요건을 만족하는지 확인하는 자리이다. 이 때 피드백을 받을 수 있고 다음 스프린트에 반영할 요건들을 확인할 수 있다.
- `회고`: 회고는 팀원들이 자기 스스로를 돌아보는 과정이다. 마이크로서비스를 설계 및 개발하는 과정에서 좋았던 방식과 안 좋았던 방식을 논의하고 개선점을 찾아 다른 스프린트에 적용할 수 있다.



### 아키텍처 정의와 마이크로서비스 도출

> 구현 스프린트를 본격적으로 진행하기 위해서 준비하고 계획하는 행동이 `아키텍처 정의`와 `마이크로서비스 도출`이다.

- `아키텍처 정의`: 마이크로서비스 외부/내부 아키텍처를 정의하는 공정이다. 마이크로서비스를 순수 비즈니스 로직이 존재하는 내부 영역과 기술 영역을 표현하는 외부 영역으로 구분해서 개발하면 외부 영역은 언제든지 교체될 수 있으므로 애플리케이션의 핵심인 내부 영역에 집중하고, 외부영역, 즉 아키텍처의 결정사항들은 천천히 결정해도 된다. 이는 애플리케이션은 유연해야 한다는 말로 연결되며, 결국 변화를 염두에 둔 유연한 구조의 아키텍처를 초기에 정의해야 한다는 뜻이 된다.
- `마이크로서비스 도출`: 본격적인 마이크로서비스 개발로 들어가기 위한 스크럼 팀이 개발할 전체 마이크로서비스들을 파악하는 작업이다. 모든 마이크로서비스를 하나의 스크럼 팀이 개발할 수 없으므로 DDD의 '전략적 설계' 기법을 활용해 마이크로서비스를 도출하고 그것들 간의 대략적인 매핑 관계를 정의한 후 마이크로서비스 개발 우선순위에 근거해 스크럼 팀에 배분해서 스프린트를 진행하게 된다. 최종 결과물은 컨텍스트 맵이다. 컨텍스트 맵은 식별된 마이크로서비스와 그것들 간의 의존관계를 보여준다.



### 스프린트 내 개발 공정

> 스프린트 내 개발 공정은 스크럼 팀 멤버인 백엔드 개발자와 프론트엔드 개발자의 역할대로 공정이 나뉘어진다. 
>
> 두 영역의 접목은 두 영역 간의 계약인 'API 설계'를 통해 진행되고, 나머지 활동은 각각 내부적으로 진행하게 된다.



#### 백엔드 설계 및 개발

> 백엔드 설계의 시작은 `API 설계`로, API 설계는 각 백엔드 마이크로서비스가 프론트엔드에 제공할 서비스 명세다. 초기에 API 설계를 진행해 프론트엔드 영역과 협의 및 조정해야 한다. 그래야만 프론트엔드 영역도 이를 기반으로 프론트엔드 자체의 설계를 진행할 수 있다.

그 다음으로는 정의된 마이크로서비스 내부 구조에 따라 `도메인 모델`과 `데이터 모델`을 설계하여야 한다.



도메인 모델을 작성하는 활동을 `도메인 모델링`이라고 한다. 이전의 OOAD(Object Oriented Analysis Design) 방식에서는 UML 등을 활용해 설계 모델을 작성하고 이를 소스코드로 변환하는 작업 등을 진행했으나, DDD를 적용하면 별도의 정형화된 모델을 만들지 않고, 간략히 도메인 모델 등을 화이트보드나 포스트잇 등의 단순한 도구로 작성해서 공유한 후 곧바로 소스코드로 도메인 모델을 개발한다는 것이다.

즉, 아래 그림을 보면 예전의 설계 모델의 개념은 MDD(Model Driven Development)의 사례와 같이 추상적인 모델을 완벽히 만들어 놓고 특정 기술 프로파일이나 프레임워크를 적용해 구체적인 코드를 생성해서 모델과 코드가 단절되는 구조였다면, DDD의 모델링은 코드 자체가 모델의 기본 표현 형식을 그대로 반영해서 코드로 표현된다.

![agile-3](C:\Users\82102\OneDrive\티스토리\Book\DDD&MSA\image\agile-3.PNG)



#### 프론트엔드 영역 설계와 개발

> 프론트엔드 영역 설계는 UI 레이아웃을 정의하고 백엔드의 API를 호출해서 API가 보내준 데이터를 기반으로 UI에 어떻게 표현할 것인가를 정의하는 활동이다.



프론트엔드 영역 설계와 개발의 기본적인 활동은 다음과 같다.

- `UI 흐름 정의`: 비즈니스 흐름에 따른 UI 흐름을 정의한다. UI 흐름을 설계한 산출물이 `UI 스토리보드`이다.
- `UI 레이아웃 정의`: 사용자 접점인 UI를 정의하는 활동이다. 디자인을 고려하지 않은 사용자 경험을 고려해서 설계한다.
- `UI 이벤트 및 액션 정의`: UI 레이아웃의 구성요소인 컨트롤을 클릭하거나 터치하는 등의 행위를 했을 때 발생하는 이벤트 및 액션을 정의하는 활동이다. 미리 정의된 백엔드 API와의 연계를 정의할 수도 있다.
- `UI 개발`: UI 레이아웃 및 이벤트의 의도에 맞춰 프론트엔드 애플리케이션을 개발하는 활동이다. 보통 프론트엔드 아키텍처에서 정의한 UI 프레임워크나 도구를 사용할 수 있다.



#### 빌드 및 배포

> 기민한 개발을 위해서는 백엔드와 프론트엔드 개발이 진행되는 과정에서 지속적으로 빌드되고 자동으로 배포되도록 빌드 및 배포 환경을 자동화해야 한다.
>
> 스크럼 팀의 구성원은 언제라도 현재 진행된 만큼의 실제로 돌아가는 소프트웨어를 확인할 수 있어야 한다.



아키텍처를 정의할 때, 빌드 및 배포 파이프라인이 구성된 상태의 개발자 입장에서 수행해야 할 빌드 및 배포를 위한 활동은 다음과 같다.

- `소스코드 레포지토리 구성`: 프론트엔드, 백엔드 코드를 위한 소스코드 레포지토리를 구성한다. (SVN, Git, Github 등을 이용한다.)
- `통합 빌드 잡(Build Job) 구성`: 레포지토리에 존재하는 소스코드를 통합한 후 컴파일 및 테스트해서 바이너리를 만드는 활동을 자동화한다. (Jenkins, Travis CI 등을 이용한다.)
- `컨테이너 생성 파일 작성`: 배포 환경을 컨테이너 환경으로 구성할 경우 운영체제와 WAS와 빌드된 애플리케이션을 묶어서 컨테이너 이미지를 생성하는 스크립트를 작성할 수 있다. (도커 컨테이너인 경우 Dockerfile로 작성할 수 있다.)
- `배포 스크립트 작성`: 자동으로 배포하는 스크립트를 작성하는 활동이다. 배포 타겟에 맞춰 스크립트를 작성한다. (Jenkins에도 배포 기능이 있으며, 온프레미스 및 클라우드 서비스 등 대부분 환경에서 자동화해서 배포할 수 있다.)