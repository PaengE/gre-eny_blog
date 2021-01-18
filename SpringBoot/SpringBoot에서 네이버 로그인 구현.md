# SpringBoot에서 네이버 로그인 구현

> 본 포스팅은 `Spring Security와 OAuth2.0으로 Google Login 기능 구현` 과 이어지는 내용으로 공유되는 코드가 있습니다.
>
> 따라서 본 포스팅의 코드로만은 정상동작되지 않을 수 있습니다.

## 네이버 API 등록

> https://developers.naver.com/apps/#/register?api=nvlogin

위 사이트에 접속하여 밑의 이미지처럼 각 항목을 채운 후 서비스를 등록한다.

![네이버 로그인 - 1](C:\Users\82102\OneDrive\티스토리\SpringBoot\image\네이버 로그인 - 1.jpg)

- 회원이름, 이메일, 프로필 사진은 필수이며 추가정보는 필요한 경우 선택하면 된다.
- 서비스 URL은 필수이며, 로컬로 테스트할 것이기에 localhost:8080을 적어준다.
- Callback URL은 리디렉션 URL과 같은 역할을 한다.

![네이버 로그인 - 2](C:\Users\82102\OneDrive\티스토리\SpringBoot\image\네이버 로그인 - 2.jpg)

- 해당 키값들을 application.properties가 위치한 곳과 동일한 곳에 application-oauth.properties를 생성 후 등록한다.
- 네이버에선 구글과 다르게 스프링 시큐리티를 공식 지원하지 않기 때문에 구글의 CommonOAuth2Provider가 제공해주던 값들도 전부 수동으로 입력해야 한다.

### src/main/java/프로젝트이름/resources/application-oauth.properties

```properties
# registration
spring.security.oauth2.client.registration.naver.client-id=네이버클라이언트ID
spring.security.oauth2.client.registration.naver.client-secret=네이버클라이언트시크릿
spring.security.oauth2.client.registration.naver.redirect-uri={baseUrl}/{action}/oauth2/code/{registrationId}
spring.security.oauth2.client.registration.naver.authorization-grant-type=authorization_code
spring.security.oauth2.client.registration.naver.scope=name,email,profile_image
spring.security.oauth2.client.registration.naver.client-name=Naver

# provider
spring.security.oauth2.client.provider.naver.authorization-uri=https://nid.naver.com/oauth2.0/authorize
spring.security.oauth2.client.provider.naver.token-uri=https://nid.naver.com/oauth2.0/token
spring.security.oauth2.client.provider.naver.user-info-uri=https://openapi.naver.com/v1/nid/me
spring.security.oauth2.client.provider.naver.user-name-attribute=response
```

> #### user_name_attribute=response
>
> - 기준이 되는 user_name의 이름을 네이버에서는 response로 해야한다.
> - 이유는 네이버의 회원 조회 시 반환되는 JSON 형태 때문이다.

```json
{
	"resultcode": "00",
	"message": "success",
	"response": {
		"email": "~~",
		"nickname": "~~",
		"profile_image": "~~",
		"age": "~~",
		"gender": "~~",
		"id": "~~",
		"name": "오픈 API",
		"birthday": "~~"
	}
}
```

- 네이버 오픈 API의 로그인 회원 결과 JSON 형태는 위와 같은데, 스프링 시큐리티에선 하위필드를 명시할 수 없다. 최상위 필드만 user_name으로 지정 가능하다.
- 네이버의 응답값 최상위 필드는 resultCode, message, response인데, 이중 하나를 골라야 한다.
- 따라서 본문의 response를 user_name으로 지정하고, 이후 자바 코드에서 response의 id를 user_name으로 지정해야한다.

## 스프링 시큐리티 설정 등록

> 구글 로그인을 등록하면서 대부분 코드가 확장성있게 작성되었다보니 네이버는 쉽게 등록 가능하다.
>
> OAuthAttributes에 다음과 같이 `네이버인지 판단하는 코드와 네이버 생성자`만 추가해 주면 된다.

```java
@Getter
public class OAuthAttributes {
	...
	
	public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
    if("naver".equals(registrationId)) {
    	return ofNaver("id", attributes);
    }

  	return ofGoogle(userNameAttributeName, attributes);
  }
  
  ...
  
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
  
  ...
}
```

> index.mustache에 네이버 로그인 버튼을 추가한다.

```html
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

- /oauth2/authorization/naver
  - 네이버 로그인 URL은 application-oauth.properties에 등록한 redirect-uri 값에 맞춰 자동으로 등록된다.
  - /oauth2/authorization/ 까지는 고정이고 마지막 Path만 각 소셜 로그인 코드를 사용하면 된다.