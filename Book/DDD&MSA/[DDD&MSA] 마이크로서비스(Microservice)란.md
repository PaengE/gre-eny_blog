## 마이크로서비스(Microservice) 란?

> 유명 아키텍트 구루인 마틴 파울러(Martin Fowler)는 그동안의 마이크로서비스(microservice) 발전 흐름을 정리해 마이크로서비스의 등장 배경과 개념, 특징을 설명하였다.
>
> 해당 아티클은 본 포스팅의 가장 마지막에 연결해 두겠다.



## 모노리스(Monolith) vs 마이크로서비스(Microservice)

> 전통적인 시스템 구조인 `모노리스(monolith)` 구조는 다음 그림과 같다.

![마이크로서비스란-1](C:\Users\82102\OneDrive\티스토리\Book\DDD&MSA\image\마이크로서비스란-1.png)

서버 측 애플리케이션이 일체, 즉 논리적인 단일체로서 아무리 작은 변화에도 새로운 버전으로 전체를 빌드해서 배포해야 한다. 그리고 일체식 애플리케이션은 단일 프로세스에서 실행된다.

따라서 확장이 필요한 경우, 특정 기능만 확장할 수 없고 전체 애플리케이션을 동시에 확장해야 한다. 보통 로드밸런서를 앞에 두고 여러 인스턴스 위에 큰 덩어리를 복제해 수평으로 확장한다.

즉, 여러 개의 모노리스가 수평으로 확장된 상태에서는 여러 개의 모노리스 시스템 모두를 전부 다시 빌드하고 배포하여야 한다. 또한 확장시 애플리케이션이 병렬로 확장되어 사용량 증가에는 대응할 수 있지만, 데이터베이스는 통합되어 하나이므로 탄력적으로 대응할 수 없다. 따라서 사전에 성능을 감당하기 위한 `스케일 업`과정이 필요하다.

> `마이크로서비스(microservice)`는 서버 측이 여러개의 조각으로 구성되어 각 서비스가 별개의 인스턴스로 로딩된다. 즉, 여러 서비스 인스턴스가 모여 하나의 비즈니스 애플리케이션을 구성한다. 또한 각기 저장소가 다르므로 업무 단위로 모듈 경계가 명확하게 구분된다.
>
> 다음은 `마이크로서비스` 기반 시스템의 구조를 나타낸 그림이다.

위에 언급한 부분 때문에, 마이크로서비스는 확장 시 특정 기능별로 독립적으로 확장할 수 있고, 특정 서비스를 변경할 필요가 있다면 해당 서비스만 다시 빌드하고 배포하면 된다. 또 각 서비스가 독립적이어서 서로 다른 여러 언어로 개발하는 것도 가능하므로 각 서비스의 소유권을 분리해 서로 다른 팀이 개발 및 운영할 수 있다.

![마이크로서비스란-2](C:\Users\82102\OneDrive\티스토리\Book\DDD&MSA\image\마이크로서비스란-2.png)

위 그림처럼, 각기 서비스가 분리되어 별도의 저장소를 가지고 있고, 만약 서비스 C의 확장이 필요하다면 서비스 C만 `스케일 아웃`하면 된다.

## SOA(Service Oriented Architecture)와 마이크로서비스

> 소프트웨어 공학에서 말하는 모듈화(Modularity) 개념의 발전 흐름
>
> 1. 단순히 기능을 하향식으로 분해해서 설계해 나가는 `구조적(Structured) 방법론`
> 2. 객체 단위로 모듈화하기 위한 `객체지향(Object-Oriented) 방법론`
> 3. 모듈화의 단위가 기능별로 재사용할 수 있는 좀 더 큰 컴포넌트가 되는 `CBD(Component Based Development)`
> 4. 컴포넌트를 모아 비즈니스적으로 의미 있고 완결적인 서비스 단위로 모듈화하는 `SOA(Service Oriented Architecture)`

넓게 보면 여러 개의 응집된 비즈니스 서비스의 집합으로 시스템을 개발한다는 점에서 SOA와 MSA는 개념적으로 큰 차이가 없다.

그러나 SOA는 구체적이지 않고 이론적이며, 실제 비즈니스 성공 사례가 많지 않다.

반면 MSA는 클라우드 인프라 기술의 발전과 접목되어 아마존과 넷플릭스에 의해 구체화되고 비즈니스 성공사례로 많이 알려졌다.

### 마틴 파울러가 정의한 마이크로서비스

> "마이크로서비스는 여러 개의 작은 서비스 집합으로 개발하는 접근 방법이다. 각 서비스는 개별 프로세스에서 실행되고, HTTP 자원 API 같은 가벼운 수단을 사용해서 통신한다. 또한 서비스는 비즈니스 기능 단위로 구성되고, 자동화된 배포 방식을 이용하여 독립적으로 배포된다. 또한 서비스에 대한 중앙 집중적인 관리는 최소화하고, 각 서비스는 서로 다른 언어와 데이터, 저장 기술을 사용할 수 있다."

위에서 언급된 것처럼 '특정 서비스를 구축하는 데 사용하는 언어나 저장소를 자율적으로 선택할 수 있는 방식'을 `폴리글랏(Polyglot)하다`라고 표현한다. 클라우드 등의 가상 인프라가 지닌 유연성이 이를 가능하도록 지원한다.

다음 그림은 마틴 파울러가 정의한 마이크로서비스 개념을 표현한 개념도이다.

![마이크로서비스란-3](C:\Users\82102\OneDrive\티스토리\Book\DDD&MSA\image\마이크로서비스란-3.png)

SOA의 접근법에서는 애플리케이션은 모듈별로 분리했으나 데이터 저장소까지는 분리하지 못했다. (즉, 여러 애플리케이션이 하나의 저장소를 통합해서 공유했다.)

따라서 데이터의 강한 결합으로 애플리케이션도 독립적으로 사용하기 힘들었다.

그러나 MSA에서는 SOA에는 없었던 다음 두가지 개념으로 모듈화 방식을 강화했고, 이를 진정으로 실현함으로써 SOA와의 차이를 만들었다.

1. 서비스별 저장소를 분리해서 다른 서비스가 저장소를 직접 호출하지 못하도록 캡슐화한다. 즉, 다른 서비스의 저장소에 접근하는 수단은 API 밖에 없다.
2. REST API 같은 가벼운 개방형 표준을 사용해 각 서비스가 느슨하게 연계되고 누구나 쉽게 사용할 수 있다. (SOA에 사용되는 SOAP 프로토콜과 XML 보다, HTTP 프로토콜과 JSON 데이터의 형태가 단순하고 쉽다는 의미이다.)

## 마이크로서비스를 위한 조건

> 마이크로서비스를 잘 구현하고 있는 조직들은 MSA라는 기술 이외에도 공통적으로 비슷한 개발환경, 문화, 일하는 방식을 가지고 있었다. 이는 마이크로서비스를 구현하기 위한 필요조건이 되기에 충분하다.

### 조직의 변화: 업무 기능 중심 팀

> `콘웨이 법칙(Conway's Law)`은 멜빈 콘웨이(Melvin E. Conway)가 정의한 조직과 조직이 개발한 소프트웨어의 관계를 정의한 법칙이다.
>
> 쉽게 설명하면, 시스템을 개발할 때 항상 시스템의 모양이 팀의 의사소통 구조를 반영되는 것을 말한다.

다음 그림과 같이, 예전 일하는 방식을 보면, 하나의 애플리케이션을 개발하기 위해서는 세 팀간의 의사소통이 필요하다. 따라서 시스템도 이러한 의사소통 구조를 그대로 반영하고, 이러한 팀 구조에서는 팀 간의 의사결정도 느리고 의사소통 또한 어렵다.

![마이크로서비스란-4](C:\Users\82102\OneDrive\티스토리\Book\DDD&MSA\image\마이크로서비스란-4.png)마이크로서비스를 만드는 팀은 `업무 기능 중심 팀`이어야 한다.

`업무 기능 중심 팀`은 역할 또는 기술별로 팀이 분리되는 것이 아니라, 업무 기능을 중심으로 기술과 역할이 다양한 사람들이 하나의 팀이 되어 서비스를 만드는 것을 의미한다. 이렇게 하면, 팀원들은 같은 공간과 시간을 공유하기 때문에 의사소통도 원활하고 의사결정도 빠르게 진행될 수 있다.

![마이크로서비스란-5](C:\Users\82102\OneDrive\티스토리\Book\DDD&MSA\image\마이크로서비스란-5.png)

이 팀을 `다기능 팀(Cross-Functional Team)`이라고도 하며, 이 팀은 자율적으로 담당 비즈니스에 관련된 서비스를 만들뿐만 아니라 개발 이후에 운영할 책임까지 진다. (아마존에서는 이러한 팀을 `Two pizza team`이라 한다.)

이러한 팀은 마이크로서비스를 만드는 데 필요한 기능과 기술을 팀 내부에 모두 가지고 있으므로 다른 마이크로서비스팀과는 협력할 일이 적을 수밖에 없다. 따라서 콘웨이 법칙에 의해 아래 그림처럼 서로 다른 팀의 마이크로서비스끼리는 서로 느슨하게 연계된다.

![마이크로서비스란-6](C:\Users\82102\OneDrive\티스토리\Book\DDD&MSA\image\마이크로서비스란-6.png)

### 관리체계의 변화: 자율적인 분권 거버넌스, 폴리글랏(Polyglot)

> `"you built it, you run it"`

마이크로서비스를 만드는 조직은 중앙의 강력한 거버넌스를 추구하지 않는다. 

각 마이크로서비스팀은 빠르게 서비스를 만드는 것을 최우선 목적으로 두고 스스로 효율적인 방법론과 도구, 기술을 찾아 적용한다.

이러한 과정에서, 각 서비스 팀이 자신의 팀에 맞는 개발 언어 및 저장소를 선택하는 것을 각각 `폴리글랏 프로그래밍`, `폴리글랏 저장소`라고 한다.

![마이크로서비스란-6](C:\Users\82102\OneDrive\티스토리\Book\DDD&MSA\image\마이크로서비스란-7.png)

### 개발 생명주기의 변화: 프로젝트가 아니라 제품 중심으로

> 기존에는 대부분의 애플리케이션 개발모델이 프로젝트 단위였다. 개발조직과 운영조직이 별도 운영됐으며, 초기에 모든 일정을 계획하였으므로 프로젝트 진행 중에 생긴 변경이나 피드백 등을 완벽히 포용할 수 없었다.

마이크로서비스팀의 개발은 비즈니스의 갑작스런 트렌드 변화에 유연하게 대처해야 하고, 개발뿐만 아니라 운영을 포함한 소프트웨어의 전체 생명주기를 책임져야 한다.

따라서 소프트웨어를 완성해야할 기능들의 집합으로 보는 것이 아니라, 비즈니스를 제공하는 `제품(product)`으로 바라보고, 우선 개발한 뒤에 반응을 보고 개선하는 방식으로 소프트웨어를 개발한다.

즉, 점진 반복적인 모델, 제품 중심의 `애자일(agile) 개발 방식`을 채용한다.

이 같은 방식은 약 2~3주 단위의 스프린트(Sprint)를 통해 소프트웨어를 개발 및 배포해서 바로 피드백을 받아 소프트웨어에 반영할 수 있게 해준다. 이는 소프트웨어를 한시적 프로젝트로 보는 것이 아니라 지속적으로 개선시키고 발전시킬 제품으로 바라보기 때문이다.

![마이크로서비스란-8](C:\Users\82102\OneDrive\티스토리\Book\DDD&MSA\image\마이크로서비스란-8.png)

위의 그림은 프로젝트 중심의 개발 생명주기와 제품 중심의 개발 생명주기에 관한 내용이다.

### 개발 환경의 변화: 인프라 자동화

> 전체 소프트웨어 구현 과정
>
> 1. 개발 환경 준비 (인프라)
> 2. 개발 (분석/설계/개발)
> 3. 개발지원과정 (빌드/테스트/배포)

가상 인프라 발전은 마이크로서비스를 성공적으로 자리갑게 해준 일등공신이다. 위에 정리된 것처럼 `개발 환경 준비`를 클라우드 인프라를 활용하여 쉽고 빠르게 할 수 있다. 이는 팀의 개발 속도를 높여준다.

마찬가지로 `개발지원과정`의 속도를 높이려면 `자동화`가 필요하다. `개발`을 하여 빠르게 피드백을 받아야 하기 때문이다.

이같은 환경은 개발과 운영을 동시에 수행하는 데브옵스(DevOps)를 궁극적으로 가능하게 하므로 `데브옵스 개발 환경`이라 속칭하기도 한다.

또한, 이러한 개발 환경, 개발지원 환경을 자동화하는 것을 모두 통틀어 `인프라 자동화`라고 하기도 한다. 인프라 자동화는 마이크로서비스 개발 과정의 필수 조건이 되어야 한다.

다음 그림은 이러한 과정을 묘사한 빌드/배포 파이프라인이다. 아래 그림은 일반적인 빌드/배포 파이프라인이다.

`스테이징 환경`이란 운영환경과 거의 동일한 환경으로 구성해서 최종 운영환경으로 이관하기 전 여러가지 비기능 요건(성능, 가용성, 보안, 유지보수성, 확장성 등)을 점검하는 환경이다.

![마이크로서비스란-9](C:\Users\82102\OneDrive\티스토리\Book\DDD&MSA\image\마이크로서비스란-9.png)

최근에는 배포 환경이 마이크로서비스 개수에 따라 급격하게 늘어나기 때문에 이를 효율적으로 관리하기 위해 인프라 구성과 자동화를 마치 소프트웨어처럼 코드로 처리하는 방식인 `IaC(Infrastructure as Code)`가 각광받는다.

`IaC`는 코드를 이용해 인프라 구성부터 애플리케이션 빌드, 배포를 정의하는 것을 의미하는데, 이렇게 되면 수많은 하드웨어 리소스 설정을 동일하게 통제할 수 있으며, 상황에 따른 검증되고 적절한 설정을 쉽게 복제하고 누구한테나 공유할 수 있게 돼서 인프라를 매우 효율적으로 관리할 수 있다.

### 저장소의 변화: 통합 저장소가 아닌 분권 데이터 관리

> 마이크로서비스는 `폴리글랏 저장소(Polyglot persistence)` 접근법을 선택하며, 서비스별로 데이터베이스를 갖도록 설계한다. 즉, 각 저장소가 서비스별로 분산돼 있어야 하며, 다른 서비스의 저장소를 직접 호출할 수 없고 API를 통해서만 접근가능케 한다는 의미이다.

이러한 구조에서는 비즈니스 처리를 위해 일부 데이터의 복제와 중복 허용이 필요하다.

하지만 여기서 각 마이크로서비스의 저장소에 담긴 데이터의 비즈니스 정합성을 맞춰야 하는 데이터 일관성 문제가 발생한다.

데이터 일관성 처리를 위해서는 보통 2단계 커밋(two-phase commit) 같은 분산 트랜잭션 기법을 사용하는데, NoSQL 저장소처럼 2단계 커밋을 지원하지 않는 경우도 있으므로, 마이크로서비스는 비동기 이벤트 처리를 통한 협업을 강조한다.

이를 `결과적 일관성(Eventual Consistency)`이라는 개념으로 표현하기도 하는데, 두 서비스의 데이터가 일시적으로 불일치하는 시점에 있고 일관성이 없는 상태일지라도 결국에는 두 데이터가 같아진다는 개념이다. 즉, 여러 트랜잭션을 하나로 묶지 않고 별도의 로컬 트랜잭션을 각각 수행하고 일관성이 달라진 부분은 체크해서 보상 트랜잭션으로 일관성을 맞추는 개념이다.

마이크로서비스가 추구하는 방법(2단계 커밋을 제외하고)은 각 트랜잭션을 분리하고 큐(queue) 메커니즘을 이용하여 보상 트랜잭션을 활용하는 방법이다.

![마이크로서비스란-10](C:\Users\82102\OneDrive\티스토리\Book\DDD&MSA\image\마이크로서비스란-10.png)

위의 경우 다음과 같이 처리하여 비즈니스 일관성을 맞출 수 있다.

1. 주문 서비스가 주문처리 트랜잭션을 수행한다.
   - a. 동시에 주문 이벤트를 발행한다.
   - b. 주문 이벤트가 메시지 큐로 전송된다.
   - c. 배송 서비스가 주문 이벤트를 인식한다.
2. 배송 서비스가 주문 처리에 맞는 배송처리 트랜잭션을 수행한다. (비즈니스 일관성 만족)
3. 배송 서비스 처리 트랜잭션 중 오류로 트랜잭션을 실패한다.
   - a. 배송처리 실패 이벤트를 발행한다.
   - b. 배송처리 실패 이벤트가 메시지 큐로 전송된다.
   - c. 주문 서비스가 배송처리 실패 이벤트를 인식한다.
4. 주문 서비스는 주문취소(보상트랜잭션)를 수행한다. (비즈니스 일관성 만족)

### 위기 대응 방식의 변화: 실패를 고려한 설계

> `'소프트웨어는 모두 실패한다' - 아마존 부사장 버너 보겔스(Werner Vogels)`
>
> 시스템은 언제든 실패할 수 있으며, 실패해서 더는 진행할 수 없을 때도 자연스럽게 대응할 수 있도록 설계해야 한다. 이러한 성격을 `내결함성(Fault tolerance)`이라 한다.

예전의 시스템 아키텍처에서는 결함이나 실패 무결성을 추구했다. 하지만 버너 보겔스의 말처럼 어떤 시스템이라도 실패하지 않을 수없다. 실패하지 않는 시스템을 만드는 것보다 실패에 빠르게 대응할 수 있는 시스템을 만드는 것이 더 쉽고 효율적이다.

서킷 브레이커(circuit breaker) 패턴처럼 각 서비스를 모니터링 하고 있다가 한 서비스가 다운되거나 실패하면 이를 호출하는 서비스의 연계를 차단하고 적절하게 대응하여야 한다.

## Summary

`MSA(MicroService Architecture)`를 용어 그대로 아키텍처나 기술로만 이해하는건 바람직하지 못하다.

`마이크로서비스`라는 기술적인 내용을 포함하고 있으나, 업무 방식, 조직문화 등의 내용도 중요한 부분이다.

MSA를 지향하려면 클라우드 환경에서 비즈니스 민첩성을 강화하기 위한 다음 3가지를 필요하다.

- `기술`: 자동화된 개발 환경 기반의 마이크로서비스 아키텍처
- `업무 방식`: 점진 반복적인 개발 프로세스
- `조직문화`: 자율적인 업무 기능 중심 팀과 자율적인 개발문화

![마이크로서비스란-11](C:\Users\82102\OneDrive\티스토리\Book\DDD&MSA\image\마이크로서비스란-11.png)

## Refer to

- https://martinfowler.com/articles/microservices.html
- https://engineering-skcc.github.io/microservice%20%EA%B0%9C%EB%85%90/whatis-microservice/
- https://engineering-skcc.github.io/microservice%20%EA%B0%9C%EB%85%90/microservice-condition/