# 어노테이션 기반으로 개선 예제

#### src/main/java/com/yneerg/admin/book/web/IndexController.java

```java
		@GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());

        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if (user != null) {
            model.addAttribute("userName", user.getName());
        }

        return "index";
    }
```

IndexController에서 `세션값을 가져오는 부분`은 index 메소드 외에 다른 컨트롤러와 메소드에서 세션값이 필요하면 그 때마다 직접 세션에서 값을 가져와야 한다. 같은 코드가 계속해서 반복되는 것은 불필요하다.

따라서 이 부분을 `메소드 인자로 세션값을 바로 받을 수 있도록` 어노테이션을 생성하여 변경해보도록 한다.

#### config.auth 패키지에 @LoginUser 어노테이션을 생성한다.

```java
package com.yneerg.admin.book.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {
}
```

- @Target(ElementType.PARAMETER)
  - 이 어노테이션이 생성될 수 있는 위치를 지정한다.
  - PARAMETER로 지정했으니 메소드의 파라미터로 선언된 객체에서만 사용할 수 있다.
  - 이 외에도 클래스 선언문에 쓸 수 있는 TYPE 등이 있다.
- @interface
  - 이 파일을 어노테이션 클래스로 지정한다.
  - '클래스이름'의 어노테이션이 생성된다. (LoginUser라는 이름을 가진 어노테이션이 생성되었다.)

> 그리고 같은 위치에 LoginUserArgumentResolver를 생성한다,
>
> LoginUserArgumentResolver는 HandlerMethodArgumentResolver 인터페이스를 구현한 클래스이다.
>
> HandlerMethodArgumentResolver는 조건에 맞는 경우 메소드가 있다면 HandlerMethodArgumentResolver의 구현체가 지정한 값으로 해당 메소드의 파라미터로 넘기는 기능을 지원한다.

#### config.auth 패키지에 LoginUserArgumentResolver 클래스를 생성한다.

```java
package com.yneerg.admin.book.config.auth;

import com.yneerg.admin.book.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;


    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());

        return isLoginUserAnnotation && isUserClass;
        
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        return httpSession.getAttribute("user");
        
    }
}
```

- supportParameter()
  - 컨트롤러 메소드의 특정 파라미터를 지원하는지 판단한다,.
  - 여기서는 파라미터에 @LoginUser 어노테이션이 붙어 있고, 파라미터 클래스 타입이 SessionUser.class인 경우 true를 반환한다.
- resolveArgument()
  - 파라미터에 전달할 객체를 생성한다.
  - 여기서는 세션에서 객체를 가져온다.

> @LoginUser를 사용하기 위한 환경은 구성되었다.
>
> 그러나 아직 스프링에서 LoginUserArgumentResolver를 인식할 수 없으므로, 스프링에서 인식될 수 있도록 WebMvcConfigurer에 추가하여야 한다.

#### config 패키지에 WebConfig 클래스를 생성한다.

```java
package com.yneerg.admin.book.config;

import com.yneerg.admin.book.config.auth.LoginUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoginUserArgumentResolver loginUserArgumentResolver;
    
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolver) {
        argumentResolver.add(loginUserArgumentResolver);
    }
    
}

```

- HandlerMethodArgumentResolver는 항상 WebMvcConfigurer의 addArgumentResolvers()를 통해 추가해야 한다.
- 다른 HandlerMethodArgumentResolver가 필요하다면 같은 방식으로 추가해주면 된다.

> 이제 모든 설정이 끝났으니 @LoginUser 어노테이션을 사용할 수 있다.

#### 새로 생성한 @LoginUser 어노테이션으로 IndexController에 적용하기

```java
		@GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postsService.findAllDesc());

        if (user != null) {
            model.addAttribute("userName", user.getName());
        }

        return "index";
    }
```

- @LoginUser SessionUser user
  - 기존에 (User) httpSession.getAttribute("user")로 가져오던 세션 정보 값이 개선되었다.
  - 이제는 어느 컨트롤러든지 @LoginUser 어노테이션만 사용하면 세션 정보를 가져올 수 있게되었다.