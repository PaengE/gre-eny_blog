## Spring Security와 OAuth2.0으로 Google Login 기능 구현

> 스프링 시큐리티(Spring Security)란 막강한 인증(Authentication)과 인가(권한부여: Authorization) 기능을 가진 프레임워크이다. 사실상 스프링 기반의 애플리케이션에서는 보안을 위한 표준이라고 보면 된다.

## 스프링 부트 1.5 vs 스프링 부트 2.0

> spring-security-oauth2-autoconfigure

위의 라이브러리를 사용할 경우 스프링 부트2.0에서도 1.5에서 쓰던 설정을 그대로 사용할 수 있다.

그러나 스프링 부트2.0 방식인 Spring Security Oauth2.0 Client 라이브러리를 사용해야 하는 이유는 다음과 같다.

- 스프링 팀에서 기존 spring-security-oauth 프로젝트는 유지상태(maintenance mode)로 결정하고 신규 기능 추가 없이 버그 수정 정도의 기능만 추가할 예정이고, 새 oauth2 라이브러리에서만 지원한다고 선언했기 때문
- 스프링 부트용 라이브러리(starter)가 출시
- 기존에 사용되던 방식은 확장포인트가 적절하게 오픈되어 있지 않아 직접 상속하거나 오버라이딩 해야 하고 신규 라이브러리의 경우 확장 포인트를 고려해서 설계된 상태이기 때문

스프링부트2.0 방식의 정보를 검색하고 싶다면 자료들 사이에서 다음 두가지를 확인하면 된다.

1. spring-security-oauth2-auoconfigure 라이브러리를 썼는지(썼다면 스프링 부트1.5 방식)

2. application.properties 혹은 application.yml 정보가 다음과 같이 차이가 있는지

   ```properties
   # Spring Boot 1.5
   google :
   	client :
   		clientId: 인증정보
   		clientSecret: 인증정보
   		accessTokenUri: https://accounts.google.com/o/oauth2/token
   		userAuthorizationUri: https://accounts.google.com/o/oauth2/auth
   		clientAuthenticationsScheme: form
   		scope: email. profile
   	resource:
   		userInfoUri: https://www.googleapis.com/oauth2/v2/userinfo
   	
   # Spring Boot 2.0
   spring:
   	security:
   		oauth2:
   			client:
   				clientId: 인증정보
   				clientSecret: 인증정보
   ```

   1.5버전에서 직접 입력했던 값들은 2.0버전으로 오면서 모두 enum으로 대체되었다. CommonOAuth2Provider 라는 enum이 새롭게 추가되어 구글, 깃허브, 페이스북, 옥타의 기본 설정값은 모두 여기서 제공한다. 

   이외의 다른 소셜 로그인(네이버, 카카오 등)을 추가한다면 직접 다 추가해 주어야 한다.

## 구글 서비스 등록

먼저 구글 서비스에 신규 서비스를 생성해야 한다. 여기서 발급된 인증정보(clientId, clientSecret)를 통해서 로그인 기능과 소셜 서비스 기능을 사용할 수 있다.

1. 구글 클라우드 플랫폼 주소(https://console.cloud.google.com)로 이동하여 로그인 후, [프로젝트 선택] 탭을 클릭하여 [새 프로젝트]를 생성한다.

   <img src="C:\Users\82102\OneDrive\티스토리\SpringBoot\image\구글 서비스 - 새 프로젝트 생성.jpg" alt="구글 서비스 새 프로젝트 생성"  />

   등록될 서비스의 이름을 `프로젝트 이름`에 입력한다. 원하는 이름으로 지으면 된다.

2. OAuth 클라이언트 ID이 생성되기 전에 OAuth 동의 화면 구성이 필요하므로 [OAuth 동의 화면]버튼을 눌러 다음 항목들을 작성한다

   1. OAuth 동의 화면 단계에선 다음 정보를 입력하면 된다.

      1. 앱 이름: 구글 로그인 시 사용자에게 노출될 애플리케이션 이름을 이야기한다.
      2. 사용자 지원 이메일: 사용자 동의 화면에서 노출될 이메일 주소이다. 보통은 서비스의 help 이메일 주소를 사용하지만, 여기서는 본인의 이메일 주소를 사용하면 된다.
      3. 개발자 연락처 정보: 역시 본인 이메일 주소를 사용하면 된다.

   2. 범위 단계에선 [범위 추가 또는 삭제] 버튼을 눌러 email, profile, openid 를 추가해 준다.

      ![구글 서비스 - OAuth 동의 화면 구성](C:\Users\82102\OneDrive\티스토리\SpringBoot\image\구글 서비스 - OAuth 동의 화면 구성.jpg)

3. 소셜로그인 기능을 OAuth 클라이언트 ID로 구현하믜로 [API 및 서비스] - [사용자 인증 정보] 에 들어가 해당 프로젝트를 선택한 후 [OAuth 클라이언트 ID] 를 선택 후, [OAuth 클라이언트 ID 만들기]를 클릭한다.

   애플리케이션 유형으로 `웹 애플리케이션`을 선택한 후, 다음과 같이 [승인된 리디렉션 URI] 항목만 값을 등록한다.

   ![구글 서비스 - 승인된 리디렉션 URI](C:\Users\82102\OneDrive\티스토리\SpringBoot\image\구글 서비스 - 승인된 리디렉션 URI.jpg)

   > 승인된 리디렉션 URI란?
   >
   > - 서비스에서 파라미터로 인증 정보를 주었을 때 인증이 성공하면 구글에서 리다이렉트할 URL이다.
   > - 스프링 부트2 버전의 시큐리티에서는 기본적으로 {도메인}/lgoin/oauth2/code/{소셜서비스코드}로 리다이렉트 URL을 지원하고 있따.
   > - 사용자가 별도로 리다이렉트 URL을 지원하는 Controller를 만들 필요가 없다.(시큐리티에서 이미 구현해 놓은 상태)
   > - 현재는 개발 단계이므로 http://localhost:8080/login/oauth2/code/google로 등록하지만, AWS 서버에 배포하게 되면 localhost 외에 추가로 주소를 추가해야 한다.

4. 이제 클라이언트 ID(clientID)와 클라이언트 보안 비밀(clientSecret) 코드를 프로젝트에서 설정해야 한다. 클라이언트 아이디와 클라이언트 보안 비밀은 [사용자 인증 정보]에서 그림과 같이 해당 OAuth 2.0 클라이언트 ID의 수정버튼을 누르면 볼 수 있다.

   ![구글 서비스 - 클라이언트 ID, 클라이언트 보안 비밀](C:\Users\82102\OneDrive\티스토리\SpringBoot\image\구글 서비스 - 클라이언트 ID, 클라이언트 보안 비밀.jpg)

   1. application.properties가 있는 src/main/resources/ 디렉토리에 application-oauth.properties 파일을 생성 후, 다음 코드를 등록한다.

      ```properties
      spring.security.oauth2.client.registration.google.client-id=구글클라이언트ID
      spring.security.oauth2.client.registration.google.client-secret=구글클라이언트시크릿
      spring.security.oauth2.client.registration.google.scope=profile,email
      ```

      > scope=profile,email을 별도로 등록하는 이유는?
      >
      > 많은 예제에서 이 scope를 별도로 등록하고 있지 않다.(기본값이 openid,profile,email 이기 때문.)
      >
      > 그러나 강제로 profile과 email을 등록한 이유는 openid라는 scope이 있으면 OpenId Provider로 인식하기 때문이다. 이렇게 되면 OpenId Provider인 서비스(구글)와 그렇지 않은 서비스(네이버, 카카오 등)로 나눠서 각각 OAuth2Service를 만들어야 하기 때문에, 하나의 OAuth2Service를 사용하기 위함이다.

   2. 스프링 부트에서 oauth properties의 설정들을 가져올 수 있게끔 설정한다.

      > 스프링 부트에서는 properties의 이름을 application-xxx.properties로 만들면 xxx라는 이름의 profile이 생성되어 이를 통해 관리 할 수 있다. 
      >
      > 따라서 profile=xxx라는 식으로 호출하면 해당 properties의 설정들을 가져올 수 있다.

      위의 방법을 통하여, 스프링 부트의 기본 설정 파일인 application.properties에서 application-oauth.properties를 포함하도록 다음 코드를 추가한다.

      ```properties
      spring.profile.include=oauth
      ```

   3. 구글 로그인을 위한 클라이언트 ID와 클라이언트 보안 비밀은 보안이 중요한 정보들이므로, 외부에 노출될 경우 언제든 개인정보를 가져갈 수 있는 취약점이 될 수 있으므로 git에 올린다면 반드시 ignore 처리를 해줘야 한다.

## 구글 로그인 연동하기

#### src/main/java/com/yneerg/admin/book/domain/user/User.java

```java
// 사용자 정보를 담당할 도메인 User 클래스
package com.yneerg.admin.book.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
```

- @Enumerated(EnumType.STRING)
  - JPA로 데이터베이스로 저장할 때 Enum 값을 어떤 형태로 저장할지를 결정한다.
  - 기본적으로는 int로 된 숫자가 저장된다. 그러나 숫자로 저장되면 데이터베이스로 확인할 때 그 값이 무슨 코드를 의미하는지 알 수 없으므로, 문자열(EnumType.STRING)로 저장될 수 있도록 선언한다.

#### src/main/java/com/yneerg/admin/book/domain/user/Role.java

```java
// 각 사용자의 권한을 관리할 Enum 클래스 Role
package com.yneerg.admin.book.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;
}
```

#### src/main/java/com/yneerg/admin/book/domain/user/UserRepository.java

```java
// User의 CRUD를 책임질 UserRepository(by JpaRepository 인터페이스 상속)
package com.yneerg.admin.book.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
```

- findByEmail
  - 소셜 로그인으로 반환되는 값 중 email을 통해 이미 생성된 사용자인지 처음 가입하는 사용자인지 판단하기 위한 메소드이다.
- Optional은 null을 대신하기 위해 만들어진 새로운 코어 라이브러리 데이터 타입입니다. Optional 클래스는 null이나 null이 아닌 값을 담을 수 있는 클래스이다.

### 스프링 시큐리티 설정

> 먼저 build.gradle에 스프링 시큐리티 관련 의존성 하나를 추가한다.
>
> ```java
> implementation('org.springframework.boot:spring-boot-starter-oauth2-client'
> ```
>
> - 소셜 로그인 등 클라이언트 입장에서 소셜 기능 구현 시 필요한 의존성이다.
> - spring-security-oauth2-client와 spring-security-oauth2-jose를 기본으로 관리해준다.

`시큐리티 관련 클래스를 모두 관리할` config.auth 패키지를 생성한 후, 밑의 파일들을 위치시킨다.

#### src/main/java/com/yneerg/admin/book/config/auth/SecurityConfig.java

```java
// OAuth 라이브러리를 이용한 소셜 로그인 설정 코드
package com.yneerg.admin.book.config.auth;

import com.yneerg.admin.book.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
    }
}
```

구글 사용자 정보가 업데이트 되었을 때를 대비하여 update 기능도 같이 구현되었다. 사용자의 이름(name)이나 프로필 사진(picture)이 변경되면 User 엔티티에도 반영된다.

- @EnableWebSecurity
  - Spring Security 설정들을 활성화시켜 준다.
- csrf().disable().headers().frameOptions().disable()
  - h2-console 화면을 사용하기 위해 해당 옵션들을 disable한다.
- authorizeRequests
  - URL별 권한 관리를 설정하는 옵션의 시작점이다.
  - authorizeRequests가 선언되어야만 antMatchers 옵션을 사용할 수 있다.
- antMatchers
  - 권한 관리 대상을 지정하는 옵션으로, URL, HTTP 메소드별로 관리가 가능하다.
  - "/"등 지정된 URL들은 permitAll() 옵션을 통해 전체 열람 권한을 주었다.
  - "/api/v1/**"주소를 가진 API는 USER 권한을 가진 사람만 가능하도록 설정하였다.
- anyRequest
  - 설정된 값들 이외 나머지 URL들을 나타낸다.
  - 여기서는 authenticated()를 추가하여 나머지 URL들은 모두 인증된 사용자들에게만 허용하게 한다.
  - 인증된 사용자 즉, 로그인 사용자들을 이야기한다.
- logout().logoutSuccessUrl("/")
  - 로그아웃 기능에 대한 여러 설정의 진입점이다.
  - 로그아웃 성공시 / 주소로 이동한다.
- oauth2Login
  - OAuth2 로그인 기능에 대한 여러 설정의 진입점이다.
- userInfoEndpoint
  - OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당한다.
- userService
  - 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록한다
  - 리소스 서버(즉, 소셜 서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시할 수 있다.

#### src/main/java/com/yneerg/admin/book/config/auth/CustomOAuth2UserService.java

```java
// 구글 로그인 이후 가져온 사용자의 정보(email. name, picture 등)들을 기반으로 가입 및 정보수정, 세션 저장 등의 기능을 지원
package com.yneerg.admin.book.config.auth;

import com.yneerg.admin.book.config.auth.dto.OAuthAttributes;
import com.yneerg.admin.book.config.auth.dto.SessionUser;
import com.yneerg.admin.book.domain.user.User;
import com.yneerg.admin.book.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }


    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
```

- registrationId
  - 현재 로그인 진행중인 서비스를 구분하는 코드이다.
  - 여러 개의 소셜 로그인 연동시에 필요한 부분으로 어떤 소셜 로그인인지를 구분하기 위해 사용한다.
- userNameAttributeName
  - OAuth2 로그인 진행 시 키가 되는 필드값이다. Primary Key와 같은 의미이다.
  - 구글의 경우 기본적으로 코드를 지원하지만, 네이버 카카오 등은 기본지원하지 않는다. 구글의 기본 코드는 "sub"이다. 여러개의 소셜 로그인을 동시 지원할 때 사용된다.
- OAuthAttributes
  - OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스이다.
- SessionUser
  - 세션에 사용자 정보를 저장하기 위한 Dto 클래스이다.

#### src/main/java/com/yneerg/admin/book/config/auth/dto/OAuthAttributes.java

```java
// OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스
package com.yneerg.admin.book.config.auth.dto;

import com.yneerg.admin.book.domain.user.Role;
import com.yneerg.admin.book.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if("naver".equals(registrationId)) {
            return ofNaver("id", attributes);
        }

        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();
    }
}
```

- of()
  - OAuth2User에서 반환하는 사용자 정보는 Map이기 때문에 값 하나하나를 변환해야만 한다.
- toEntity()
  - User 엔티티를 생성한다. OAuthAttributes에서 엔티티를 생성하는 시점은 처음 가입할 때이다.
  - 가입할 때의 기본 권한을 GUEST로 주기 위해 role 빌더값에는 Role.GUEST를 준다.

#### src/main/java/com/yneerg/admin/book/config/auth/dto/SessionUser.java

```java
// 인증된 사용자 정보를 저장하는 클래스
package com.yneerg.admin.book.config.auth.dto;

import com.yneerg.admin.book.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
```

- User 클래스(엔티티)를 사용하기에는 `성능이슈, 부수효과`등 운영 및 유지보수 때에 불편함이 많을 수 있다.(@OneToMany, @ManyToMany 등 자식 엔티티를 갖고있거나, 언제 다른 엔티티와 새로운 관계가 형성될 지 모르기 때문이다.)

## 로그인 테스트

#### src/main/resources/templates/index.mustache

```html
...
<h1>스프링부트로 시작하는 웹 서비스 Ver.2</h1>
    <div class="col-md-12">
        <!-- 로그인 기능 영역 -->
        <div class="row">
            <div class="col-md-6">
                <a href="/posts/save" role="button" class="btn btn-primary">글 등록</a>
                {{#userName}}
                    Logged in as: <span id="user">{{userName}}</span>
                    <a href="/logout" class="btn btn-info active" role="button">Logout</a>
                {{/userName}}
                {{^userName}}
                    <a href="/oauth2/authorization/google" class="btn btn-success active" role="button">Google Login</a>
                    <a href="/oauth2/authorization/naver" class="btn btn-secondary active" role="button">Naver Login</a>
                {{/userName}}
            </div>
        </div>
        <br>
        <!-- 목록 출력 영역 -->
...
```

#### src/main/java/com/yneerg/admin/book/web/IndexController.java

```java
// index.mustache에서 userName을 사용할 수 있게 IndexController에서 userName을 model에 저장하는 코드 추가
package com.yneerg.admin.book.web;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());

        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if (user != null) {
            model.addAttribute("userName", user.getName());
        }

        return "index";
    }
}
```

## 구글 로그인 테스트 이미지

![구글 로그인 테스트 - 1](C:\Users\82102\OneDrive\티스토리\SpringBoot\image\구글 로그인 테스트 - 1.jpg)

![구글 로그인 테스트 - 2](C:\Users\82102\OneDrive\티스토리\SpringBoot\image\구글 로그인 테스트 - 2.jpg)

![구글 로그인 테스트 - 3](C:\Users\82102\OneDrive\티스토리\SpringBoot\image\구글 로그인 테스트 - 3.jpg)