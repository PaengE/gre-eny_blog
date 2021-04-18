## java.util.Optional<T> Class

> `Optional<T> 클래스`는 'T'타입의 객체를 포장해주는 래퍼클래스(Wrapper Class)이다.
>
> `Optional<T> 클래스`는 null이 올 수 있는 값을 감싸는 클래스로,  만약 null이 저장되어 있더라도 `NullPointerException`이 발생하지 않는다.

```java
public final class Optional<T> {
  // If non-null, the value; if null, indicates no value is present
  private final T value;
  ...
}
```

`value`에 값을 저장하기 때문에 값이 null이라도 바로 참조 시에 NPE가 발생하지 않고, 또한 클래스이기 때문에 각종 메소드를 제공해준다.

> 자바에서는 기본 타입 스트림을 위한 별도의 Optional 클래스를 제공해준다.
>
> - OptionalInt 클래스
> - OptionalLong 클래스
> - OptionalDouble 클래스
>
> 위의 클래스로 생성된 객체는 각각 `getAsXXX()`메소드로 객체에 저장된 값에 접근할 수있다.

## Optional 객체 생성

> Optional 클래스는 객체 생성을 위한 3가지 정적 팩토리 메소드를 제공해준다.

### Optional.empty()

> null을 담고 있는, 비어있는 Optional 객체를 생성한다. 이 비어있는 객체는 Optional 내부적으로 미리 생성해놓은 싱글톤 인스턴스이다.

```java
Optional<User> emptyOptional = Optional.empty();
```

### Optional.of(value)

> null이 아닌 객체를 담고 있는 Optional 객체를 생성한다. null이 넘어올 경우 NPE가 발생하기 때문에 null이 올 수 있을 경우엔 `ofNullable(value)`를 써야한다.

```jav
Optional<User> ofOptional = Optional.of(user);
```

### Optional.ofNullable(value)

> null인지 아닌지 확신할 수 없는 객체를 담고 있는 Optional 객체를 생성한다.
>
> `Optional.empty()`와 `Optional.of(value)`를 합쳐놓은 메소드라고 생각하면 된다.
>
> null이 넘어오면, `Optional.empty()`와 동일하게 동작하고, null이 아닌 값이 넘어오면, `Optional.of(value)`와 동일하게 동작한다.

```java
Optional<User> nullableOptional1 = Optional.ofNullable(user);
Optional<User> nullableOptional2 = Optional.ofNullable(null);
```

## Optional 객체 접근

> Optional 클래스는 담고 있는 객체에 접근하기 위해 다양한 인스턴스 메소드를 제공한다.
>
> 아래 메소드들은 Optional이 담고 있는 값이 null 이 아닌 경우에는 동일하게 해당 값을 리턴한다.
>
> 하지만 Optional이 비어있는 경우(null을 담고 있는 경우), 각각 다르게 동작한다. 그 차이를 알아보자.

### get()

- Optional 객체에 저장된 값이 존재하면, 그 값을 리턴한다.

- 비어있는 Optional 객체에 대하여 `NoSuchElementException`을 던진다. 따라서, `ifPresent()`를 통하여 값이 존재하는지를 먼저 체크한 뒤에 사용하여야 한다.

### orElse(T other)

- Optional 객체에 저장된 값이 존재하면, 그 값을 리턴한다.
- 비어있는 Optional 객체에 대하여, 넘어온 인자를 리턴한다.

### orElseGet(Supplier<? extends T> other)

- Optional 객체에 저장된 값이 존재하면, 그 값을 리턴한다.

- 비어있는 Optional 객체에 대하여, 넘어온 함수형 인자를 통해 생성된 객체를 리턴한다.
- Optional 객체가 비어있는 경우에만 함수가 호출되기 때문에 `orElse(T other)`과 비교하여 성능상의 이점이 있을 수 있다.

### orElseThrow(Supplier<? extends X> exceptionSupplier)

- Optional 객체에 저장된 값이 존재하면, 그 값을 리턴한다.

- 비어있는 Optional 객체에 대하여, 넘어온 함수형 인자를 통해 생성된 예외를 리턴한다.

## Optional 사용

> Optional<T> 클래스를 사용하는 `null handling을 직접 하지 않고, Optional 클래스에 위임`하기 위해서 이다. 따라서, Optional 클래스를 사용함에도, null을 처리한다면 Optional을 제대로 사용하고 있지 못하다고 볼 수 있다.

```java
// Optional 사용 X
String text = getText();
int length;
if (text != null) {
	length = maybeText.get().length();
} else {
	length = 0;
}

// Optional 사용 O
String text = getText();
Optional<String> maybeText = Optional.ofNullable(text);
int length;
if (maybeText.isPresent()) {
	length = maybeText.get().length();
} else {
	length = 0;
}
```

Optional을 적절하게 사용한다 함은 아래와 같이 사용하는 것이다.

```java
int length = Optional.ofNullable(getText()).map(String::length).orElse(0);
```

마찬가지로 다음 예제도 Optional을 어떻게 사용하냐에 따라 지저분하게 보일지, 아닐지가 결정된다.

```java
// Optional 사용 X
User user = getUser();
if (user != null) {
  Address address = user.getAddress();
  if (address != null) {
    String street = address.getStreet();
    if (street != null) {
      return street;
    }
  }
}
return "주소 없음";

// Optional 사용 O
Optional<User> user = Optional.ofNullable(getUser());
Optional<Address> address = user.map(User::getAddress);
Optional<String> street = address.map(Address::getStreet);
String result = street.orElse("주소 없음");

// 더 간단하게
Optional<User> user = Optional.ofNullable(getUser())
  .map(User::getAddress)
  .map(Address::getStreet)
  .orElse("주소 없음");
```

NullPointerException을 핸들링할 때에도, Optional의 elseThrow() 를 활용하여 간단하게 작성할 수 있다.

```java
// Optional 사용 X
String value = null;
String result = "";
try {
  result = value.toUpperCase();
} catch (NullPointerException e) {
  throw new CustomExcpetion();
}

Optional<String> opt = Optional.ofNullable(null)
  .orElseThrow(CustomException::new)
  .toUpperCase();
```

## Refer to

- http://www.tcpschool.com/java/java_stream_optional
- https://futurecreator.github.io/2018/08/14/java-8-optional/
- https://www.daleseo.com/java8-optional-after/