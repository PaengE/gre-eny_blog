# 마이크로서비스 운영과 관리를 위한 플랫폼 패턴

> MSA 시스템을 구성하는 수많은 마이크로서비스를 하나하나 수동으로 빌드하고 배포하는 것은 비효율적이다.
>
> 따라서 이러한 과정을 하나하나 통제하고 자동화하는 것이 중요해졌다.



## 개발 지원 환경: 데브옵스 인프라 구성

> `데브옵스(DevOps)`는 개발과 운영이 분리되지 않은 개발 및 운영을 병행할 수 있는 조직 또는 그 문화를 의미한다.
>
> 여기서 말하는 `데브옵스 환경`은 개발과 운영을 병행 가능하게끔 높은 품질로 소프트웨어를 빠르게 개발하도록 지원하는 빌드, 테스트, 배포를 위한 자동화 환경을 말한다.

과거의 수동 빌드 및 배포는 다음과 같은 과정을 따랐다.

1. 개발자는 개발 환경에서 애플리케이션을 완성하고(컴파일 및 수동 테스트 포함으로 인한 오류 수정 포함), 테스트 환경에서 수동 테스트한 후 발생한 오류 를 수정한 뒤 스테이징 환경에 배포한다.
2. 개발자는 운영 환경에 배포하기 전에 스테이징 환경에서 다시 수동으로 테스트한다. 그러다 오류가 발생하면 첫 환경인 개발 환경으로 돌아가 오류를 수정한 뒤 스테이징 환경에서 다시 테스트를 한다.
3. 이러한 과정이 무사히 끝나면 배포 승인을 받고 승인 완료 후 배포 담당자가 애플리케이션을 운영 환경에 배포한다.

특히 마지막의 배포 담당자는 보통 시스템 사용률이 낮은 야간에 시스템을 장시간 멈추고 배포 작업을 진행하는 경우가 많았다. 이처럼 위의 과정은 너무나 비효율적이다.

마이크로서비스는 특성 상 배포가 잦을 수 밖에 없기 때문에 이러한 과정의 자동화가 꼭 필요하다.

자동화된 빌드나 배포 작업을 보통 `CI/CD`라고 한다.

- `지속적 통합, CI(Continuous Integration)`: 빌드 과정을 자동화한다.
- `CD`: CD는 `지속적 제공(Continuous Delivery)` 및 `지속적 배포(Continuous Deployment)` 두 가지 의미를 포함하고 있다.
  - `지속적 제공(Continuous Delivery)`: 빌드된 소스코드의 실행 파일을 실행 환경에 반영하기 전 단계까지 진행하는 방식이다. 따라서 실행 환경에 반영하기 위해서는 승인 및 배포 담당자의 허가가 필요하며 배포도 수동으로 처리한다.
  - `지속적 배포(Continuous Deployment)`: 소스코드 저장소에서 빌드한 소스코드의 실행 파일을 실행 환경까지 자동으로 배포하는 방식이다. 모든 영역을 자동화하는 것에 해당한다.

다음은 지속적 통합/배포가 이루어지는 과정이다.

![플랫폼패턴-1](C:\Users\82102\OneDrive\티스토리\Book\DDD&MSA\image\플랫폼패턴-1.PNG)

1. 개발자들이 자신이 작성한 소스코드와 그것을 테스트한 테스트 코드를 형상관리 시스템에 보낸다.(Push)
2. 빌드 도구에서 매일 밤 형상관리 서버의 코드를 가져와(Pull) 통합한 다음, 자동으로 빌드하고 테스트 코드를 실행해 테스트를 수행한다.
3. 테스트 수행 결과를 리포트 문서로 기록하고, 빌드된 소스코드를 스테이징 환경에 자동으로 배포한다.
4. 다음날 테스터가 스테이징 환경에서 테스트를 수행한다. 또는 빌드 및 단위 테스트 결과를 개발자가 확인하고 문제가 있다면 즉시 소스코드를 수정한다.



## 빌드/배포 파이프라인 설계

> 빌드/배포 파이프라인이란 보통 빌드/배포되는 과정 동안 수행해야 할 태스크가 정의된 것을 얘기한다.
>
> 즉, 빌드/배포 파이프라인은 통합 및 배포까지 이어지는 일련의 프로세스를 하나로 연계해서 자동화하고 시각화된 절차로 구축하는 것을 말한다.

![플랫폼패턴-2](C:\Users\82102\OneDrive\티스토리\Book\DDD&MSA\image\플랫폼패턴-2.PNG)

위 그림은 배포 파이프라인 절차이다. 

클라우드 같은 가상 환경의 대중화와 인프라 구성을 프로그래밍하는 것처럼 처리하는 `IaC(Infrastructure as Code)`로 배포 파이프라인 절차를 완벽하게 자동화할 수 있으며, 대규모 인프라 관리를 수행할 수 있고, 코드이기 때문에 쉽게 공유 및 재사용이 가능하다.

마이크로서비스마다 수동으로 처리하는 부분이 있을 수 있으므로, 각 마이크로서비스는 독립적으로 빌드 및 배포가 가능하여야 하고, 이를 위해 개별로 파이프라인을 설계하여야 한다.



## 마이크로서비스 관리/운영 패턴

> `넷플릭스 OSS`는 넷플릭스가 마이크로서비스를 개발하고 운영하면서 생긴 노하우를 공유한 오픈소스이다.
>
> 이는 마이크로서비스 생태계에 크게 도움이 됐고, 특히 마이크로서비스 관리와 운영을 지원하는 전형적인 마이크로서비스 애플리케이션 패턴으로 자리잡았다.
>
> 예를 들어, API 게이트웨이, 서비스 디스커버리, 모니터링, 트레이싱 등이 그렇다.

스프링 진영에서는 넷플릭스 OSS를 더 쉽게 쓸 수 있게, 넷플릭스 OSS 모듈들을 스프링 프레임워크로 감싸서 `스프링 클라우드(Spring Cloud)`를 개발했다.

아래 내용들은 스프링 클라우드를 중심으로한 주요 관리 및 운영 플랫폼 패턴이다.



### 스프링 클라우드: 스프링 부트 + 넷플릭스 OSS

아래 그림은 스프링 클라우드를 기반으로 한 아키텍처 구성이다.

스프링 클라우드는 넷플릭스가 공개한 줄, 유레카, 히스트릭스, 리본 등의 넷플릭스 오픈소스를 스프링 부투 프레임워크 기반으로 사용하기 쉽게 통합한 것이다.

![플랫폼패턴-3](C:\Users\82102\OneDrive\티스토리\Book\DDD&MSA\image\플랫폼패턴-3.PNG)

1. 모든 마이크로서비스(스프링 클라우드 서비스를 포함한)는 인프라에 종속되지 않도록 데이터베이스, 파일 등에 저장된 환경 설정 정보를 형상관리 시스템에 연계된 `Config 서비스`에서 가져와 설정 정보를 주입한 후 클라우드 인프라의 개별 인스턴스로 로딩된다.
2. 로딩과 동시에 `서비스 레지스트리`에 자신의 서비스명과 클라우드 인프라로부터 할당받은 물리 주소를 매핑해서 등록한다.
3. 클라이언트가 `API 게이트웨이`를 통해 마이크로서비스에 접근하고, 이때 API 게이트웨이는 적절한 라우팅 및 부하 관리를 위한 로드밸런싱을 수행한다.
4. 또한 API 게이트웨이에서 클라이언트가 마이크로서비스에 접근하기 위한 주소를 알기 위해 `서비스 레지스트리` 검색을 통해 서비스의 위치를 가져온다.
5. 동시에 API 게이트웨이는 클라이언트가 각 서비스에 접근할 수 있는 권한이 있는지 `권한 서비스`와 연계해 인증/인가 처리를 수행한다.
6. 이러한 모든 마이크로서비스 간의 호출 흐름은 `모니터링 서비스`와 `추적 서비스`에 의해 모니터링 되고 추적된다.

AWS, Azure, GCP 등에서도 유사한 형태의 서비스로 각기 제공한다.



### 다양한 서비스의 등록 및 탐색을 위한 서비스 레지스트리, 서비스 디스커버리 패턴

> 클라이언트가 여러 개의 마이크로서비스를 호출하기 위해서는 최적 경로를 찾아주는 `라우팅` 기능과 적절한 부하 분산을 위한 `로드 밸런싱` 기능이 필요하다.
>
> 넷플릭스 OSS로 예를 들면 라우팅 기능은 `줄(Zuul)`이, 로드 밸런싱은 `리본(Ribbon)`이 담당한다.

`서비스 디스커버리(Service Discovery)` 패턴은 프론트엔드 클라이언트가 여러 개의 백엔드 마이크로서비스를 호출할 때, 또 스케일 아웃을 통해 인스턴스가 여러 개로 복제됐을때 부하를 적절히 분산시켜야 할 때 사용된다.

라우터는 최적 경로를 탐색하기 위해 서비스 명칭에 해당하는 IP 주소를 알아야 한다. 그런데 이러한 라우팅 정보를 클라이언트가 가지고 있으면 클라우드 환경에서 동적으로 변경되는 백엔드의 유동 IP정보를 매번 전송받아 갱신해야 한다. 따라서 제 3의 공간에서 이러한 정보를 관리하는 것이 좋다. 즉, 마이크로서비스의  명칭과 유동적인 IP정보를 매핑해서 보관할 저장소가 필요하다. 넷플릭스 OSS의 `유레카(Eureka)`가 그 기능을 담당하고, 이러한 패턴을 `서비스 레지스트리(Service Registry)` 패턴이라 한다.

![플랫폼패턴-4](C:\Users\82102\OneDrive\티스토리\Book\DDD&MSA\image\플랫폼패턴-4.PNG)

서비스 레지스트리에는 업무 처리를 위한 마이크로서비스뿐만 아니라 관리와 운영을 위한 기반 서비스의 주소도 함께 보관한다. 예를 들면, Config 서비스, 모니터링 서비스, 추적 서비스도 모두 이름을 가지고 있기 때문에 주소를 가지고 있어야 한다.



### 서비스 단일 진입을 위한 API 게이트웨이 패턴

> 여러 클라이언트가 여러 개의 서버 서비스를 각각 호출하게 된다면 매우 복잡한 호출 관계가 만들어지는데, 이러한 복잡성을 통제하기 위한 방법이 필요하다.
>
> 한가지 해결책은 `API 게이트웨이(Gateway)`이다.

![플랫폼패턴-5](C:\Users\82102\OneDrive\티스토리\Book\DDD&MSA\image\플랫폼패턴-5.PNG)

위 그림은 API 게이트웨이 개념도이다. 다양한 클라이언트가 다양한 서비스에 접근할 때, 게이트웨이라는 단일 진입점을 만들어 놓으면 효율적이다.

API 게이트웨이는 다른 서비스와 연계하여 다음과 같은 기능을 제공한다.

- 레지스트리 서비스와 연계한 동적 라우팅, 로드 밸런싱
- 보안: 권한 서비스와 연계한 인증/인가
- 로그 집계 서비스와 연계한 로깅. 예: API 소비자 정보, 요청/응답 데이터
- 메트릭(Metrics). 예: 에러율, 평균/최고 지연시간, 호출 빈도 등
- 트레이싱 서비스와 연계한 서비스 추적. 예: 트래킹 ID 기록
- 모니터링 서비스와 연계한 장애 격리(서킷 브레이커 패턴)

이러한 API 게이트웨이 패턴을 스프링 부트에서는 스프링 클라우드의 스프링 API 게이트웨이 서비스(Spring API Gateway Service)라는 제품으로 구현할 수 있다. 스프링 게이트웨이 서비스는 스프링 웹사이트(Spring.io)에서 내려받아 간단한 스프링 애너테이션(Annotation) 설정만으로 쉽게 적용할 수 있다.

쿠버네티스의 경우에는 자체 기능인 쿠버네티스 서비스(Kubernetes Service)와 인그레스 리소스(Ingress Resources)로 제공한다.

API 게이트웨이는 프론트엔드가 백엔드를 호출할 때 이외에도, 외부 레거시 시스템과 단일 지점에서 서로 다른 형태의 API를 연계하는 용도로도 사용된다.



### BFF 패턴

> 다양한 클라이언트를 위해서는 특화된 처리를 위한 API 조합이나 처리가 필요한데, 이를 위한 해결방법으로 `BFF(Backend For Frontend)` 패턴이 있다.

BFF 패턴은 API 게이트웨이와 같은 진입점을 하나로 두지 않고 프론트엔드의 유형에 따라 각각 두는 패턴이다.

이를 통해 클라이언트 종류에 따라 최적화된 처리를 수행할 수 있게 한다. 또한 다음 그림처럼, 각 프론트엔트에 대한 처리만 수행하는 BFF를 두고 이후에 통합적인 API 게이트웨이를 둠으로써 공통적인 인증/인가, 로깅 등을 처리하는 통제하는 구조로 구성할 수도 있다.

![플랫폼패턴-6](C:\Users\82102\OneDrive\티스토리\Book\DDD&MSA\image\플랫폼패턴-6.PNG)



### 외부 구성 저장소 패턴

> 클라우드 인프라와 같이 유연한 인프라를 사용하는 상황에서 데이터베이스 연결 정보, 파일 스토리지 정보 같은 내용을 애플리케이션에 포함하면 변경 시 반드시 재배포해야 하는데, 이 경우 서비스를 중단해야 한다. 또한 여러 마이크로서비스가 동일한 구성 정보를 사용하는 경우에도 일일이 변경하기가 어렵고 변경 시점에 일부 마이크로서비스의 구성 정보가 불일치 할 수도 있다.
>
> 이를 위한 방법이 `외부 저장소 패턴`이다. 
>
> 외부 저장소는 각 마이크로서비스의 외부 환경 설정에 정보를 공동으로 저장하는 백업 저장소이다.

클라우드에서 운영되는 애플리케이션은 특정한 배포 환경에 종속된 정보를 코드에 두면 안된다. 이러한 정보를 애플리케이션에 두면 배포 환경이 변경됏을 때 애플리케이션 또한 변경해야 하기 때문이다.

분리해야 할 환경 정보로는 데이터베이스 연결 정보, 배포 시 변경해야 할 호스트명, 백엔드 서비스의 연결을 위한 리소스 정보 등이 있다. 또한 서비스가 기동되는 개발 서버, 테스트, 운영 서버의 IP 주소와 포트 정보 등을 분리해서 환경 변수로 사용한다.

![플랫폼패턴-7](C:\Users\82102\OneDrive\티스토리\Book\DDD&MSA\image\플랫폼패턴-7.PNG)

위 그림은 스프링 컨피그 서비스 사용을 위한 구성도이다. 위 그림과 같이 스프링 클라우드 컨피그(Spring Cloud Config)를 이용하여 이러한 환경 정보를 코드에서 분리하고 컨피그 서비스를 통해 런타임 시 주입되게 할 수 있다. 환경 정보는 Git 같은 별도의 형상관리 레포지토리에 보관하고 컨피그 서비스는 해당 서비스에 특정 환경에 배포될 때 적절한 환경 정보를 형상관리 레포지토리에서 가져와 해당 서비스에 주입한다.

쿠버네티스에서는 이러한 외부 구성 저장소 패턴을 쿠버네티스 컨피그맵(ConfigMap)으로 제공한다.



### 인증/인가 패턴

> 각 서비스가 모두 인증/인가를 중복으로 구현한다면 비효율적이다. 따라서 마이크로서비스 인증/인가를 처리하기 위해서는 일반적으로 다음과 같은 패턴을 활용한다.

#### 중앙 집중식 세션 관리

기존 모노리스 방식에서 가장 많이 사용했던 방식은 서버 세션에 사용자의 로그인 정보 및 권한 정보를 저장하고 이를 통해 어플리케이션에 인증/인가를 판단하는 것이다. 그렇지만 마이크로서비스는 사용량에 따라 수시로 수평 확장할 수 있고 로드 밸런싱이 되기 때문에 세션 데이터가 손실될 수 있다. 따라서 마이크로비스는 각자의 서비스에 세션을 저장하지 않고 공유 저장소에 세션을 저장하고 모든 서비스가 동일한 사용자 데이터를 얻게 한다. 보통 세션 저장소로 레디스(Redis), 맴캐시드(Memcached)를 사용한다.

#### 클라이언트 토큰

세션은 서버의 중앙에 저장되고 토큰은 사용자의 브라우저에 저장된다. 토큰은 사용자이 신원정보를 가지고 있고 서버로 요청을 보낼 때 전송되기 때문에 서버에서 인가 처리를 할 수 있다.JWT(Json Web Token)은 토큰 형식을 정의하고 암호화 하며 다양한 언어에 라이브러리를 제공하는 공개 표준(RFC 7519)이다. 토큰을 통한 사용자 인증 흐름은 다음 다이어그램과 같다. 

![플랫폼패턴-8](C:\Users\82102\OneDrive\티스토리\Book\DDD&MSA\image\플랫폼패턴-8.PNG)

1. 브라우저가 서버에 사용자명과 패스워드로 인증을 요청한다.
2. 서버는 인증 후 토큰을 생성하고 브라우저에게 사용자 정보의 인증/인가 정보를 포함한 토큰을 전송한다.
3. 브라우저는 서버 리소스를 요청할 때 토큰을 함께 보낸다. 서버의 서비스는 토큰 정보를 확인한 후 자원 접근을 허가한다.

#### API 게이트웨이를 사용한 클라이언트 토큰

사용자 인증 프로세스는 토큰 인증 프로세스와 유사하다. 차이점은 API 게이트웨이가 외부 요청의 입구로 추가된다는 것이다. 또한 인증/인가에 처리를 위한 별도의 전담서비스를 만들어서 다른 서비스의 인증/인가 처리를 위임 시킬 수 있다. 이런 서비스를 인증 서비스(Auth service)라 하는데, API 게이트웨이와 연동해서 인증 인가를 처리한다. Auth서비스를 사용하면 각각의 리소스 서비스가 자체적으로 인증 인가를 처리하지 않고 순수 업무처리에 집중할 수 있다.

![플랫폼패턴-9](C:\Users\82102\OneDrive\티스토리\Book\DDD&MSA\image\플랫폼패턴-9.PNG)

1. 클라이언트가 리소스 서비스에 접근을 요청하면 API 게이트웨이는 인증 서비스에게 전달한다.
2. 인증 서비스는 해당 요청이 인증된 사용자가 보낸 것인지(인증), 해당 리소스에 대한 접근 권한이 있는지(인가) 확인하고, 모두 확인하고 나면 리소스에 접근 가능한 증명서인 액세스 토큰을 발급한다.
3. 클라이언트는 다시 액세스 토큰을 활용해 접근을 요청한다.
4. 그럼 각 리소스 서비스는 이러한 요청이 액세스 토큰을 포함하고 있는지 판단해서 리소스에 대한 접근을 허용한다.



### 장애 및 실패 처리를 위한 서킷 브레이커 패턴

> 여러 서비스로 구성된 시스템에서는 한 서비스에 장애가 발생했을 때 다른 서비스가 영향을 받을 수 있다. 이 때 장애가 발생한 서비스를 격리해서 유연하게 처리할 수 있는 방법이 `서킷 브레이커 패턴`이다.

![플랫폼패턴-10](C:\Users\82102\OneDrive\티스토리\Book\DDD&MSA\image\플랫폼패턴-10.PNG)

위 그림은 서킷 브레이커 패턴의 흐름도이다. 

서비스 A가 서비스 B를 호출해서 자신의 서비스를 제공하는데, B 서비스에서 장애가 발생하면 이러한 동기 요청(request)의 성격상 A는 계속 기다리게 된다. 이 경우 A 서비스도 장애가 발생한 것처럼 사용자가 느끼게 된다.

서킷 브레이커 패턴은 이 같은 경우에 B 서비스 호출에 대한 연속 실패 횟수가 임계값을 초과하면 회로 차단기가 작동해서 이후에 서비스를 호출하려는 모든 시도를 즉시 실패하게 만든다.

그리고 폴백(fallback) 메소드를 지정해 두어 장애가 발생했을 때 폴백 메소드가 자연스럽게 처리를 진행하게 된다. 그럼 사용자는 특정 서비스에 장애가 발생했는지 눈치채지 못하고 장애가 복구됐을 때 다시 호출을 정상화하면 된다.



### 모니터링과 추적 패턴

> 서킷 브레이커 패턴을 가능케 하려면 각 마이크로서비스의 장애를 시릿간으로 감지해야 하고, 서비스 간의 호출이 어떤지 알아야 한다. 즉, 모니터링하고 추적하는 패턴이 필요하다.

스프링 클라우드에서는 `히스트릭스`라는 라이브러리를 제공하고, 히스트릭스 라이브러리가 배포된 서비스를 모니터링할 수 있는 히스트릭스 대시보드를 제공한다.

또한 모니터링과 함께 각 서비스 트랜잭션의 호출을 추적하는 분산 트레이싱 서비스를 사용하면 마이크로서비스 운영에 매우 유용하다. 이를 이용하면 분산된 서비스 간의 호출이나 지연 구간별 장애 포인트를 확인할 수 있다.



### 중앙화된 로그 집계 패턴

> 마이크로서비스에서는 로그를 이벤트 스트림(Event stream)으로 처리하는 것이 좋다. 로그는 시작과 끝이 고정된 것이 아니라 서비스가 실행되는 동안 계속 흐르는 흐름이라는 뜻이다. 그리고 서비스는 스트림의 전달이나 저장에 절대 관여하지 않아야 한다.
>
> 로그를 저장하는 메커니즘 자체가 특정 기술이나 인프라에 의존할 수밖에 없고 이러한 메커니즘을 직접 마이크로서비스에서 구현한다면 유연성이 떨어지기 때문이다.
>
> 이럴 때 필요한 것이 `중앙화된 로그 집계 패턴`이다.

대표적으로 많이 쓰이는 기술이 `ELK 스택`이다. ELK 스택은 `엘라스틱서치(ElasticSearch)`, `로그스태시(Logstash)`, `키바나(Kibana)`라는 세가지 오픈소스 프로젝트를 기반으로 구성된 데이터 분석 환경이다.

여기서 엘라스틱서치는 분석엔진이고, 로그스태시는 서버 측 로그 집합기다. 키바나는 시각적으로 로그내역을 보여주는 대시보드이다.

![플랫폼패턴-11](C:\Users\82102\OneDrive\티스토리\Book\DDD&MSA\image\플랫폼패턴-11.PNG)

ELK스택을 사용하면 각 서비스의 인스턴스 로그를 집계해서 중앙에서 집중 관리할 수 있으며 쉽게 특정 로그를 검색, 분석할 수 있다. 또한 특정 메시지가 로그에 나타나거나 특정 예외가 날 때 운영자나 개발자에게 직접 통보하게 할 수도 있다.

![플랫폼패턴-12](C:\Users\82102\OneDrive\티스토리\Book\DDD&MSA\image\플랫폼패턴-12.PNG)

위 그림은 마이크로서비스의 로그 수집을 위한 ELK 스택 구축 아키텍처이다. 각각의 서비스에 Logstash가 설치되어 각 로그를 수집해서 레디스 저장소에 보내고 있다. 그 다음 하나의 서비스에서는 Elasticsearch와 Kibana로 로그 중앙 관리 저장소와 대시보드 서비스를 각각 구축한다. 

마이크로서비스에서 보낸 로그가 중앙 레디스에 쌓이면 레디스에서 중앙 관리 저장소에 로그를 보내게 되고 이 로그 저장소에 엘라스틱서치(Elasticsearch)엔진이 로그를 인덱싱하고 그 로그 정보가 키바나(Kibana) 대시보드를 통해 보여진다. 중간에 레디스 DB를 둔 까닭은 마이크로서비스의 Logstash가 바로 로그 저장소에 로그를 보낼 수 있지만 로그 스트림이 너무 몰리면 로그 저장소 서비스 역시 성능에 문제가 생기기 때문에 중간에 임시 저장소를 추가한 것이다.



### 서비스 메시 패턴

> `서비스 메시 패턴`은 MSA 문제 영역 해결을 위한 기능(서비스 탐색, 서킷 브레이크, 추적, 로드 밸런싱 등)을 비즈니스 로직과 분리해서 네트워크 인프라 계층에서 수행하게 하는 패턴이다.
>
> 이는 인프라 레이어로서 서비스 간의 통신을 처리하며 지금까지 언급된 여러 문제 해결 패턴을 포괄한다.
>
> 구글의 `이스티오(Istio)`는 서비스 메시 패턴의 대표적인 구현체이며, 애플리케이션이 배포되는 컨테이너에 완전히 격리되어 별도의 컨테이너로 배포되는 사이드카(Sidecar) 패턴을 적용해서 서비스 디스커버리, 라우팅, 로드 밸런싱, 로깅, 모니터링, 보안, 트레이싱 등의 기능을 제공한다.
>
> `사이드카 패턴`은 모든 서비스 컨테이너에 추가로 사이드카 컨테이너가 배포되는 패턴으로, 각 서비스를 연계할 때 한 서비스가 다른 서비스를 직접 호출하지 않고 사이드카인 프록시를 통해 연계해서 개발자가 별도의 작업 없이 관리 및 운영에 대핸 서비스 등을 적용할 수 있다.

![플랫폼패턴-13](C:\Users\82102\OneDrive\티스토리\Book\DDD&MSA\image\플랫폼패턴-13.PNG)

위의 그림을 보면 왼쪽의 스피링 클라우드,넷플릭스 OSS를 이용한 경우에는 스프링 클라우드로 기반 서비스를 먼저 구축하고 마이크로서비스 어플리케이션 자체도 내부 코드에 이런 관리/운영 기능을 제공하는 클라이언트 코드가 탑재 되어야 한다.

그렇지만 서비스 메시를 적용하게 되는 경우에는 마이크로서비스는 순수 비지니스 로직에 집중하고 컨테이너에 관리/운영 기능을 제공하는 별도의 사이드카 프록시를 같이 배포하면 된다.

![플랫폼패턴-14](C:\Users\82102\OneDrive\티스토리\Book\DDD&MSA\image\플랫폼패턴-14.PNG)

위 그림은 서비스 메시의 통신을 나타낸 그림이다.

이는 컨트롤 플레인(Control Plane) 기능에 의해 중앙에서 통제되며, 사이드카끼리  통신해서 관련 운영 관리 기능을 제공한다. 이를 통해 마이크로서비스의 비즈니스 로직과는 완벽하게 독립적으로 운영된다. 쿠버네티스의 컨테이너 단위인 파드(pod)에서 서비스 컨테이너와 사이드카 구현체인 엔보이(envoy) 컨테이너가 함께 배포되는 걸 볼 수 있다.

#### 이스티오의 주요 기능

- 트래픽 관리(Traffic Management): 동적 라우팅, 로드 밸런싱
- 보안: 보안 통신 채널(TLS), 인증/인가/암호화
- 관측성(Observability): 메트릭, 분산 트레이싱, 로깅

#### 이스티오가 스프링 클라우드 및 넷플릭스 OSS와의 차별점

- 애플리케이션 코드의 변경이 거의 없다. 스프링 클라우드나 넷플릭스 OSS 기반은 비즈니스 로직과 함께 코드로 표현돼야 하지만 이스티오는 완전히 사이드카로 격리되며 야믈(yaml)파일과 같은 설정 파일에 의해 정의된다.
- 폴리글랏(Polyglot) 애플리케이션도 지원한다. 스프링 클라우드나 넷플릭스 기반은 자바 언어만 지원하나 이스티오는 각 마이크로서비스를 다른 언어(Java, Node.js, C#)로 작성한 경우에도 지원 가능하다.
- 이스티오는 쿠버네티스와 완벽하게 통합된 환경을 지원한다.



## 정리

여러 MSA 문제 영역을 해결하기 위해서는 넷플릭스 OSS, 스프링 클라욷, 쿠버네티스, 이스티오 등 여러 기술이 선택적으로 사용될 수 있다. 쿠버네티스와 이스티오가 모든 상황에 항상 적합하고 옳은 것은 아니므로, 각 문제 영역을 정확히 이해하고 시스템 상황에 맞게 적절한 기술을 적용하는 것이 중요하다.