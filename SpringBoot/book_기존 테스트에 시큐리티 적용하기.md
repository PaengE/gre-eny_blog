## 기존 테스트에 시큐리티 적용하기

> 기존에는 바로 API를 호출할 수 있어 테스트 코드 역시 바로 API를 호출하도록 구성했다. 하지만, 시큐리티 옵션이 활성화되면 인증된 사용자만 API를 호출할 수 있다.
>
> 기존의 테스트 코드에선 인증이 수행되지 않았기 때문에 성공적인 테스트 코드도 실패하게 된다.
>
> 따라서 테스트 코드마다 인증한 사용자가 호출한 것처럼 작동하도록 해보겠다.

## 문제1. CustomOAuth2UserService을 찾을 수 없음

이는 CustomOAuth2UserService를 생성하는데 필요한 `소셜 로그인 관련 설정값들이 없기 때문에` 발생한다.

application-oauth.properties에 설정값들을 추가했더라도 이 오류는 발생할 것이다.

> 오류내용
>
> "No qualifying bean of type '~~~.config.auth.CustomOAuth2UserService'"



왜냐하면  src/main 환경과 src/test 환경의 차이 때문이다. 둘은 본인만의 환경 구성을 가지지만, src/main/resources/application.properties가 테스트 코드를 수행할 때도 적용되는 이유는 `test에 application.properties가 없으면 main의 설정을 그대로 가져오기 때문이다`



다만, 자동으로 가져오는 옵션의 범위는 application.properties 파일까지이며, 다른 application-oauth.properties 같은 파일은 test에 파일이 없다고해서 가져오지 않는다.



이 문제를 해결하기 위해 테스트 환경에서의 application.properties를 만들어 가짜 설정값을 지정해 주겠다.

```properties
# Test OAuth
spring.security.oauth2.client.registration.google.client-id=test
spring.security.oauth2.client.registration.google.client-secret=test
spring.security.ouath2.client.registration.google.scope=profile,email
```

## 문제2. 302 Status Code

Status Code 302(리다이렉션 응답)의 원인은, 스프링 시큐리티 설정때문에 `인증되지 않는 사용자의 요청은 이동`시키기 때문이다.

그래서 이런 API 요청은 임의로 인증된 사용자를 추가하여 API만 테스트해 볼 수 있도록 한다. `스프링 시큐리티 테스트를 위한 여러 도구를 지원`하는 spring-security-test를 build.gradle에 추가한다.

```java
testImplementation("org.springframework.security:spring-security-test")
```

그리고 테스트 메소드에 다음과 같이 `임의 사용자 인증을 추가`한다.

```java
@WithMockUser(roles="USER")
```

- @WithMockUser(roles="USER")
  - 인증된 모의(가짜) 사용자를 만들어서 사용한다. (roles에 권한을 추가할 수 있다.)
  - 이 어노테이션으로 인해 ROLE_USER 권한을 가진 사용자가 API를 요청하는 것과 동일한 효과를 가진다.

그러나 `@WithMockUser는 MockMvc에서만 작동하기 때문에` MockMvc를 사용하지 않는 테스트코드는 사용할 수 없다.

그래서 @SpringBootTest에서 MockMvc를 사용하는 방법은 다음과 같다.

```java
// 새로 추가할 import
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

...
	// @BeforeEach
  @Autowired
  private WebApplicationContext context;

  private MockMvc mvc;

  @BeforeEach
  public void setup() {
  	mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
  }
  
  ...
 	// mvc.perform
  mvc.perform(post(url)
      .contentType(MediaType.APPLICATION_JSON)
      .content(new ObjectMapper().writeValueAsString(requestDto)))
      .andExpect(status().isOk());
```

- 생성된 MockMvc를 통해 API를 테스트한다.
- 본문(Body)영역은 문자열로 표현하기 위해 ObjectMapper를 통해 문자열 JSON으로 변환한다.
- 기존 코드의 http method에 따라 mvc.perform(xxx(url)...) 로 사용한다.

## 문제3. @WebMvcTest에서 CustomOAuth2UserService을 찾을 수 없음

문제1과 같이 "No qualifying bean of type '~~~.config.auth.CustomOAuth2UserService'"한 오류를 내는 것중에, @WebMvcTest를 사용한 테스트코드에서는 CustomOAuth2UserService를 스캔하지 않기 때문에 문제1을 수정하여도 같은 에러로 성공하지 못하는 경우가 있다.



@WebMvcTest는 WebSecurityConfigurerAdapter, WebMvcConfigurer를 비롯한 @ControllerAdvice, @Controller를 읽는다.(@Repository, @Service, @Component는 스캔 대상이 아니다.)

그러니 SecurityConfig는 읽었지만, SecurityConfig를 생성하기 위해 필요한 CustomOAuth2UserService는 읽을수가 없어 발생한다.



따라서 이 문제를 해결하기 위해서는 `스캔 대상에서 SecurityConfig를 제거`하면 된다.

```java
@WebMvcTest(controllers = HelloController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
})
```

참고로 @WebMvcTest의 secure 옵션은 2.1부터 Depreciated 되었으므로 사용하지 않는 것이 좋다.

그리고 여기서도 문제1과 마찬가지로 @WithMockUser를 사용해서 가짜로 인증된 사용자를 생성한다.

```java
@WithMockUser(roles="USER")
```

위 어노테이션을 테스트 메소드 위에 붙여준다. 그런 후 테스트를 다시 돌리면 새로운 에러가 발생할 것이다

```
java.lang.IllegalArgumentException: At least one JPA metamodel must be present!
```

그런데 필자는 위의 에러는 뜨지않고 아래의 에러가 떴다. 하지만 위의 에러 해결법을 적용하니 아래의 에러도 사라졌으니 참고하길 바란다.

```java
java.lang.IllegalStateException: Failed to load ApplicationContext
```

`java.lang.IllegalArgumentException: At least one JPA metamodel must be present!`에러는 @EnableJpaAuditing으로 인해 발생한다. @EnableJpaAuditing을 사용하기 위해선 최소 하나의 @Entity 클래스가 필요한데, @WebMvcTest이다 보니 당연히 없다.

@EnableJpaAuditing가 @SpringBootApplication과 함께 있다보니 @WebMvcTest에서도 스캔하게 되었기 때문에, @EnableJpaAuditing과 @SpringBootApplication 둘을 분리해주면 된다.



따라서, Application.java에서 @EnableJpaAuditing을 제거하고, config 패키지에 JpaConfig 클래스를 생성하여 @EnableJpaAuditing을 추가한다.

#### src/main/java/프로젝트이름/config/JpaConfig.java

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

그리고 다시 전체 테스트를 수행하면 문제없이 통과할 것이다.

앞의 과정을 토대로 스프링 시큐리티 적용으로 깨진 테스트를 적절하게 수정할 수 있다.