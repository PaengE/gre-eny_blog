## Lombok 이란?

> `롬복(Lombok)`은 자바 Domain(DTO, VO) 에서 반복적으로 작성되는 getter/setter나 toString, 생성자 코드 등의 소스들을, 어노테이션(Annotation)을 사용하여 생략할 수 있도록 `컴파일 시점`에 자동으로 생성해주는 라이브러리이다.



## 1. Lombok 설치하기

> 본 게시글은 아래 설명한 환경을 기반으로 설명하겠다.
>
> - `IntelliJ IDEA Ultimate 2021.1.2`
> - `Gradle 6.7`
> - `Maven 3.6.3` 
>
> 참고로 `IntelliJ 2020.03` 이후 버전에서는 기본 Plugin으로 Lombok이 설치되어 있다고 한다.



IntelliJ에서는 Plugin으로 lombok을 지원한다.

`File`-`Settings`-`Plugins`-`Marketplace`에 `lombok`을 검색하고 설치해 준다.

![lombok-1](C:\Users\82102\OneDrive\티스토리\IDE\image\lombok-1.png)



## 2. Dependency 설정하기

lombok plugin을 사용하기 위해서는 dependency에 추가해 주어야만 사용이 가능하다.



### Gradle 6.7

```groovy
// build.gradle
...
dependencies {
    // lombok plugin
    implementation('org.projectlombok:lombok')
    annotationProcessor('org.projectlombok:lombok')	

    // test 환경
    testImplementation('org.projectlombok:lombok')
    testAnnotationProcessor('org.projectlombok:lombok')	
}
...
```



### Maven 3.6.3

```xml
// pom.xml
...
<dependencies>
  <!-- lombok -->
  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <scope>provided</scope>
	</dependency>
</dependencies>
...
```



## 3. Annotation Processing 설정하기

마지막으로 어노테이션 프로세싱 활성화를 해주어야 어노테이션 기반인 lombok을 사용할 수 있다.

`File`-`Settings`-`Build, Execution, Deployment`-`Compiler`-`Annotation Processors` 에서 `Enable annotation processing` 을 체크해준다.

![lombok-2](C:\Users\82102\OneDrive\티스토리\IDE\image\lombok-2.png)



## Lombok의 Annotation



### @Getter/@Setter

- 어노테이션을 붙인 클래스 멤버 변수들 혹은 특정 변수의 `getXXX()`, `setXXX()` 메소드를 생성한다.
- 기본적으로 `public method`로 선언된다.
- `@Setter(AccessLevel.PRIVATE)`처럼 `AccessLevel`을 통하여 접근권한을 설정할 수 있다. (PRIVATE, PROTECTED 등)
- `@Setter`같은 경우는 `무결성`이 보장되어야 하는 클래스나 변수에는 사용을 지양해야 한다.



### @ToString

- 어노테이션을 붙인 클래스의 `toString()` 메소드를 생성한다.
- `@ToString(exclude = "password")` 처럼 특정 필드(변수)를 제외할 수 있다.
- `@ToString(callSuper = true)` 를 사용하면 상속받은 클래스의 정보까지 출력된다.(default 값은 `false`)
- `@ToString`을 붙인 클래스에 `순환 참조`를 하는 `객체 타입 필드`가 있다면, `무한 루프`가 발생한다.
  - 클래스 A에 어노테이션을 붙였는데, A의 멤버 변수에 클래스 B 타입이 있고, B의 멤버 변수에 클래스 A 타입이 있을 경우 `무한 루프`가 발생한다. 
  - `@ToString(exclude = "A")`와 같이 명시적으로 해당 필드를 제외시켜줘야 한다.



### @NonNull

- 어노테이션을 붙인 변수는 반드시 값이 있어야 한다.
- Setter에 null 값이 들어오게 되면 `NullPointerException`이 발생하게 된다.
- `@NonNull`은 불필요하게 branch coverage를 증가시키므로 사용이 권장되지는 않는다.



### @NoArgsConstructor

- 어노테이션이 붙은 클래스의 `기본 생성자`를 생성한다.
- `AccessLevel`을 통하여 접근권한을 설정할 수 있다.
- 기본값은 `public`이지만, `protected`를 사용하여 안전성을 보장해주는 것을 권장한다.



### @RequiredArgsConstructor

- 어노테이션이 붙은 클래스의 `final` 혹은 `@NonNull`인 필드만을 포함한 생성자를 생성한다.
- `AccessLevel`을 통하여 접근권한을 설정할 수 있다.
- 필드에 잘못된 값이 들어가도 에러를 뱉지 않을수도 있기에 사용에 주의가 필요하다.



### @AllArgsConstructor

- 어노테이션이 붙은 클래스의 모든 필드를 포함한 생성자를 생성한다.
- `AccessLevel`을 통하여 접근권한을 설정할 수 있다.
- 필드에 잘못된 값이 들어가도 에러를 뱉지 않을수도 있기에 사용에 주의가 필요하다.



### @EqualsAndHashCode

- 어노테이션이 붙은 클래스에 `equals()`와 `hashCode()` 메소드를 생성한다.
- `exclude`를 통하여 특정 필드를 제외시킬 수 있다.
  - `equals()`: 두 객체의 내용이 같은지를 비교하는 메소드이다.
  - `hashCode()`: 해당 객체의 해시값을 반환하는 메소드이다.



### @Data

- `@Getter`, `@Setter`, `@EqualsAndHashCode`, `@RequiredArgsConstructor`, `@ToString` 을 포함한다.
- `@Data(staticConstructor = "foo")`와 같이 `foo`라는 `static factory method`를 생성할 수도 있다.



### @Value

- 어노테이션이 붙은 클래스를 `불변(Immutable) 클래스`로 선언한다.
- 모든 필드와 클래스를 기본적으로 private 및 final 로 선언하고, setter 메소드를 생성하지 않는 점을 제외하곤 `@Data`와 비슷하다.



### @Cleanup

- `close()` 메소드를 자동으로 호출해주어, `close()`관련 코드 작성을 최소화 할 수 있다.



### @Synchronized

- `synchronized` 키워드를 사용할 때 데드락(Deadlock)이 발생하는 경우를 방지하기 위해, 어노테이션이 붙은 메소드가 실행되기 전에 잠글 `$lock`이라는 개인 잠금 필드를 생성한다.
- `lock`오브젝트를 자동으로 생성하므로, syschronized를 손쉽게 사용할 수 있게 해준다.



### @Builder

- `빌더 패턴`을 사용할 수 있도록 `빌더 API`를 제공한다.
- `private`이긴 하지만 `@AllArgsConstructor`를 기본적으로 적용한다.
- 클래스보다는 `사용자 정의 생성자` 혹은 `static 객체 생성 메소드` 에 사용하는 것을 권장한다.
- `@Singular`: Collection 타입에 선언하게 되면 파라미터를 하나씩 추가할 수 있다.



### @Log, @Slf4j

- 로그를 남기기 위한 어노테이션이다.
- 어노테이션을 클래스에 선언하면, log 관련 static 메소드를 선언하지 않아도 된다.



## 마무리

> 보통 `@Getter`, `@Setter`, `@ToString`, `@Builder`, `@Log` 정도를 많이 사용한다고 한다.
>
> 그리고 `@Data`보다는 코드가 길어지더라도 필요한 어노테이션만 선언하는 것을 권장하고 있다



## Refer to

- https://ksshlee.github.io/spring/java/lombok/#here2

- https://projectlombok.org/features/index.html