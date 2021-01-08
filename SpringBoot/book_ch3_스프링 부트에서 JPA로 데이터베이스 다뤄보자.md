# 스프링 부트에서 JPA로 데이터베이스 다뤄보자

JPA는 인터페이스로서 자바 표준 명세서이다.

JPA를 사용하기 위해서는 Hibernate, Eclipse Link 등 구현체가 필요하지만, Spring에선 이 구현체들을 다루지 않고 구현체들을 좀 더 쉽게 사용하고자 추상화 시킨 Spring Data JPA라는 모듈을 사용하여 JPA 기술을 다룬다. 이들의 관계를 보면 다음과 같다.

- ### JPA <- Hibernate <- Spring Data JPA

> Hibernate를 쓰는 것과 Spring Data JPA를 쓰는 것 사이에는 큰 차이가 없음에도, Spring은 Spring Data JPA를 개발했고, 권장하고 있다. 그 이유는 크게 두가지로 다음과 같다.
>
> 1. **구현체 교체의 용이성**: Hibernate 외의 다른 구현체로 쉽게 교체하기 위함.
>
>    -> Hibernate가 언젠가 수명을 다해서 새로운 JPA 구현체가 대세로 떠오를 때, 쉽게 교체하기 위함.
>
> 2. **저장소 교체의 용이성**: 관계형 데이터베이스 외의 다른 저장소로 쉽게 교체하기 위함.
>
>    -> 트래픽이 점점 많아져 관계형 DBMS로 도저히 감당이 안될 때, MongoDB로 교체가 필요하다면 Spring Data JPA에서 Spring Data MongoDB로 의존성만 교체하면 됨.
>
>    -> 이것이 가능한 이유는 Spring Data의 하위 프로젝트들은 기본적인 CRUD의 인터페이스가 같기 때문임.

## 앞으로 만들 게시판의 요구사항

1. 게시판 기능
   - 게시글 조회
   - 게시글 등록
   - 게시글 수정
   - 게시글 삭제
2. 회원 기능
   - 구글 / 네이버 로그인
   - 로그인한 사용자 글 작성 권한
   - 본인 작성 글에 대한 권한 관리

```html
implementation('org.springframework.boot:spring-boot-starter-data-jpa')
// 스프링 부트용 Spring Data Jpa 추상화 라이브러리이다.
// 스프링 부트 버전에 맞춰 자동으로 JPA관련 라이브러리들의 버전을 관리해 줌
```

## h2

- 인메모리 관계형 데이터베이스이다.
- 별도의 설치가 필요없이 프로젝트 의존성만으로 관리할 수 있다.
- 메모리에서 실행되기 때문에 애플리케이션을 재시작할 때마다 초기화된다는 점을 이용하여 테스트 용도로 많이 사용된다.

## JPA에서 제공하는 어노테이션

1. @Entity
   - 테이블과 링크될 클래스임을 나타냄.
   - 기본값으로 클래스의 카멜케이스 이름을 언더스코어 네이밍으로 테이블 이름을 지칭함.
   - ex) SalesManager.java -> sales_manager table
2. @Id
   - 해당 테이블의 PK 필드를 나타냄.
3. @GeneratedValue
   - PK의 생성 규칙을 나타냄
   - 스프링 부트 2.0에서는 GenerationType.IDENTITY 옵션을 추가해야만 auto_increment가 된다.
4. @Column
   - 테이블의 컬럼을 나탄내며 굳이 선언하지 않더라도 해당 클래스의 필드는 모두 컬럼이다.
   - 사용하는 이유는, 기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용한다.
   - 문자열의 경우 VARCHAR(255)가 기본값인데, 사이즈를 500으로 늘리고 싶거나, 타입을 TEXT로 변경하고 싶거나 하는 등의 경우에 사용된다.

## Lombok 라이브러리의 어노테이션

1. @NoArgsConstructor
   - 기본 생성자 자동 추가, public Posts(){}와 같은 효과이다.
2. @Getter
   - 클래스 내 모든 필드의 Getter 메소드를 자동생성.
3. @Builder
   - 해당 클래스의 빌더 패턴 클래스를 생성, 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함.

## Repository

보통 MyBatis 등에서 DAO라고 불리는 DB Layer 접근자이다.

JPA에선 Repository라고 부르며, 단순히 인터페이스를 생성 후, JpaRepository<Entity 클래스, PK타입>을 상속하기만 하면 기본적인 CRUD 메소드가 자동으로 생성된다.(@Repository 어노테이션을 추가할 필요도 없다.)

- 주의할 점은, Entity클래스와 기본 Entity Repository는 함께 위치해야 한다는 점이다. 
- 아주 밀접한 관계이고, Entity클래스는 기본 Repository없이는 제대로 역할을 할 수가 없다.

JpaRepository를 상속한 인터페이스는 save(), findAll() 등의 메소드를 사용할 수 있다.

- repository.save() : 테이블에 insert/update 쿼리를 실행한다. id값이 있다면 update, 없다면 insert 쿼리가 실행된다.
- repository.findAll() : 테이블에 있는 모든 데이터를 조회해오는 메소드이다.

## @AfterEach

- Junit에서 단위 테스트가 끝날 때마다 수행되는 메소드를 지정
- 보통은 배포 전 전체 테스트를 수행할 때 테스트간 데이터 침범을 막기 위해 사용한다.
- 여러 테스트가 동시에 수행되면 테스트용 데이터베이스인 H2에 데이터가 그대로 남아 있어 다음 테스트 실행 시 테스트가 실패할 수 도 있기에, 단위테스트 이후에 clean up이 필요하다.

# 등록/수정/조회 API 만들기

> API를 만들기 위한 3개의 클래스
>
> 1. Request 데이터를 받을 DTO.
>    - Entity 클래스와 유사하더라도 꼭! DTO 클래스를 추가로 유지해야한다. (**절대로 Entity 클래스를 Request/Response 클래스로 사용하면 안 된다.**)
>    - Entity 클래스는 **데이터베이스와 맞닿은 핵심클래스**이고, 많은 서비스 클래스나 비즈니스 로직들이 Entity 클래스를 기준으로 동작하고 있다. 사소한 기능 변경을 위해 Entity 클래스를 수정하는 것은 너무 큰 변경이기 때문이다.
> 2. API 요청을 받을 Controller.
> 3. 트랜잭션, 도메인 기능 간의 순서를 보장하는 Service.

## Spring Web Layer

![Spring_web_layer](C:\Users\82102\OneDrive\티스토리\Spring\image\Spring_web_layer.png)

- Web Layer
  - 흔히 사용하는 컨트롤러(@Controller)와 JSP/Freemarkser 등의 뷰 템플릿 영역이다.
  - 이외에도 필터(@Fileter), 인터셉터, 컨트롤러 어드바이스(@ControllerAdvice) 등 **외부 요청과 응답**에 대한 전반적인 영역을 이야기한다.
- Service Layer
  - @Service에 사용되는 서비스 영역이다
  - 일반적으로 Controller와 Dao의 중간 영역에서 사용된다.
  - @Transactional이 사용되어야 하는 영역이기도 하다.
- Repository Layer
  - **Database**와 같이 데이터 저장소에 접근하는 영역이다. (DAO(Data Access Object) 영역으로 이해하면 쉽다.)
- Dtos
  - Dto(Data Transfer Object)는 **계층 간에 데이터 교환을 위한 객체**를 이야기하며 Dtos는 이들의 영역이다.
  - 예를들어, 뷰 템플릿 엔진에서 사용될 객체나 Repository Layer에서 결과로 넘겨준 객체 등이 이들을 이야기한다.
- Domain  Model
  - 도메인이라 불리는 개발 대상을 모든 사람이 동일한 관점에서 이해할 수 있고 공유할 수 있도록 단순화 시킨 것을 도메인 모델이라한다.
  - 예를들어 택시 앱이라고 하면 배차, 탑승, 요금 등이 모두 도메인이 될 수 있다.
  - @Entity가 사용된 영역 역시 도메인 모델이라고 이해하면 된다.
  - 단, 무조건 데이터베이스의 테이블과 관계가 있어야만 하는 것은 아니다. VO처럼 값 객체들도 이 영역에 해당하기 때문이다.
  - 비즈니스 로직을 담당하는 곳이 Domain이다.

> 스프링에선 Bean을 주입받는 방식이 3가지다.
>
> - @Autowired
> - setter
> - 생성자
>
> 가장 권장하는 방식이 생성자로 주입받는 방식이다.
>
> 롬복의 @RequiredArgsConstructor를 사용하면 final이 선언된 모든 필드를 인자값으로 하는 생성자를 대신 생성해 주므로 편리하다. (해당 컨트롤러에 새로운 서비스를 추가하거나, 기존 컴포넌트를 제거하는 등의 상황이 발생해도 생성자 코드는 전혀 손대지 않아도 된다.)

## @SpringBootTest, @TestRestTemplate

- **@WebMvcTest의 경우 JPA 기능이 작동하지 않는다.** Controller와 ControllerAdvice 등 **외부 연동과 관련된 부분만 활성화** 된다.
- 따라서 JPA 기능까지 한번에 테스트할 때는 @SpringBootTest, @TestRestTemplate을 사용하면 된다.

## 더티 체킹(Dirty Checking)

- save 메소드로 변경사항을 저장하지 않았음에도 update 쿼리가 실행되는데 이유는 Dirty Checking 때문이다.

- JPA에서는 트랜잭션이 끝나는 시점에 **변화가 있는 모든 엔티티 객체를 데이터베이스에 자동으로 반영**해준다. 이 때, `변화가 있다`의 기준은 **최초 조회 상태**이다. 

- JPA에서는 엔티티를 조회하면 해당 엔티티의 조회 상태 그대로 `스냅샷`을 만들어 놓는다. 그리고 트랜잭션이 끝나는 시점에는 이 **스냅샷과 비교해서 다른점이 있다면 Update Query를 데이터베이스로 전달**한다.

- 이런 상태 변경 검사의 대상은 `영속성 컨텍스트가 관리하는 엔티티에만` 적용 된다.

  - detach된 엔티티 (준영속)
  - DB에 반영되기 전 처음 생성된 엔티티(비영속)

  등 준영속/비영속 상태의 엔티티는 Dirty Checking 대상에 포함되지 않는다. (값을 변경해도 데이터베이스에 반영되지 않는다.)

  > 영속성 컨텍스트란? 
  >
  > 엔티티를 영구저장하는 환경이다. JPA의 핵심 내용은 엔티티가 영속성 컨텍스트에 포함되어 있냐 아니냐로 갈린다. 
  >
  > JPA의 엔티티 매니저가 활성화된 상태로(Spring Data Jpa를 쓴다면 기본 옵션) **트랜잭션 안에서 데이터베이스의 데이터를 가져오면** 이 데이터는 영속성 컨텍스트가 유지된 상태이다.

## JPA Auditing

엔티티의 해당 데이터의 생성시간과 수정시간 등을 간단하게 알아서 처리해주는 기법

- `@MappedSuperclass`
  - JPA Entity 클래스들이 BaseTimeEntity를 상속할 경우 필드들(createdDate, modifiedDate)도 컬럼으로 인식하도록 한다.
- `@EntityListeners(AuditingEntityListener.class)`
  - BaseTimeEntity 클래스에 Auditing 기능을 포함시킨다.
- `@CreatedDate`
  - Entity가 생성되어 저장될 때 시간이 자동 저장된다.
- `@LastModifiedDate`
  - 조회한 Entity의 값을 변경할 때 시간이 자동 저장된다.

## Model

```java
import org.springframework.ui.Model;

@Controller
public class IndexController {
    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
        return "index";
    }
}
```

- 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장할 수 있다.
- model.addAttribute("posts", postsService.findAllDesc());
  - `postsService.findAllDesc()`로 가져온 결과를 posts로 index.mustache에 전달한다.