# Hello Controller 테스트 코드 작성하기

## Application Class

- 이 클래스는 프로젝트의 메인 클래스가 된다.
- `@SpringBootApplication이 있는 위치부터 설정을 읽어`가므로 이 클래스는 `항상 프로젝트의 최상단에 위치`해야한다. 
-  main 메소드에서 실행하는 SpringApplication.run으로 인해 내장 WAS를 실행한다.

## @SpringBootApplication

- @SpringBootApplication으로 인해 스프링 부트의 자동 설정, 스프링 Bean 읽기와 생성을 모두 자동으로 설정한다.

## @RestController

- 컨트롤러를 JSON을 반환하는 컨트롤러로 만들어준다.
- 예전에는 @ResponseBody를 각 메소드마다 선언했던 것을 한번에 사용할 수 있게 해준다.

## @GetMapping

- HTTP Method인 Get의 요청을 받을 수 있는 API를 만들어 줌.

## @ExtendWith(SpringExtension.class)----JUnit5

- 스프링 부트 테스트와 JUnit 사이에 연결자 역할을 한다.
- `테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자를 실행`시킨다.
- 여기서는 SpringExtension이라는 스프링 실행자를 사용한다는 뜻이다.

## @WebMvcTest

- Web(Spring MVC)에 집중할 수 있는 스프링 테스트 어노테이션이며, 컨트롤러만 사용할 때 사용한다.
- 선언할 경우 @Controller, @controllerAdvice 등을 사용할 수 있다.
- 단, @Service, @Component, @Repository 등은 사용할 수 없다.

## @Autowired

- `스프링이 관리하는 빈(Bean)을 주입 받는다.`

## @Getter

- 선언된 모든 필드의 get 메소드를 생성해 준다.

## @RequiredArgsConstructor

- 선언된 모든 final 필드가 포함된 생성자를 생성해준다. (final이 없는 필드는 생성자에 포함되지 않는다.)

## @RequestParam("name") String nameEx

- `외부에서 API로 넘긴 파라미터를 가져오는 어노테이션`이다.
- 위와 같이 사용하면, 외부에서 name이란 이름으로 넘겨준 파라미터를 메소드 파라미터 nameEx에 저장한다.

## private MockMvc mvc

- 웹 API를 테스트할 때 사용하며, 스프링 MVC 테스트의 시작점이다.
- 이 클래스를 통해 HTTP GET, POST, 등에 대한 API 테스트를 할 수 있다.

## mvc.perform(HTTP Method(test path))

- MockMvc를 통해 `test path 주소로 HTTP Method 요청`을 한다.
- 메소드체이닝이 지원되어 여러 검증 기능을 이어서 선언할 수 있다.
  - `mvc.perform(get("/path").param("name", name))`: API를 테스트할 때 사용될 요청 파라미터를 설정한다. 단, String 값만 허용된다.

## mvc.perform().andExpect()

- `mvc.perform의 결과를 검증`한다. andExpect의 파라미터로 다음을 줄 수 있다.
  - `status().isOk()` - HTTP Header의 Status를 검증한다. (200, 404, 500 등의... 여기서는 200을 check)
  - `content().string(hello)` - 응답 본문의 내용을 검증한다. (Controller에서의 리턴값... 여기서는 hello를 check)

## jsonPath("$.name", is(name))

- JSON 응답값을 필드별로 검증할 수 있는 메소드이다. $를 기준으로 필드명을 명시한다.

## Assertions.assertThat()

- assertj라는 테스트 검증 라이브러리의 검증 메소드이며, 검증하고 싶은 대상을 메소드 인자로 받는다.
- 메소드 체이닝이 지원되어 isEqualTo와 같이 메소드를 이어서 사용할 수 있다.
  - `isEqualTo()`: assertj의 동등 비교 메소드로, assertThat의 인자와 isEqualTo의 인자가 같을 때만 성공한다.