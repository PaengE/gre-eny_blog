## Lambda Expression(람다 표현식)

> 람다표현식은 간단히 말해 메소드를 하나의 식으로 표현한 것이다.

다음 메소드와 람다표현식은 같은 기능을 한다.

```java
// 메소드
int min(int x, int y){
	return x < y ? x : y;
}

// 람다식
(x, y) -> x < y ? x : y;

// 익명 클래스
new Object(){
  int min(int x, int y){
    return x < y ? x : y;
  }
}
```

위에서처럼 람다식은, 클래스를 작성하고 객체를 생성하지 않아도 메소드를 사용할 수 있다.

자바의 익명 클래스(Anonymous class)와 같다고 볼 수 있다.

## 람다 표현식 작성

> 자바에서는 화살표(->) 기호를 사용하여 람다식을 작성한다. 문법은 다음과 같다.
>
> ```java
> (매개변수 목록) -> {함수 몸체}
> ```

자바에서 람다식을 작성할 때 유의할 사항으로는 밑의 내용 정도가 있다.

- 매개변수의 타입을 추론할 수 있는 경우에는 타입을 생략할 수 있다.
- 매개변수가 하나인 경우에는 괄호(())를 생략할 수 있다.
- 함수의 몸체가 하나의 명령문만으로 이루어진 경우에는 중괄호({})를 생략할 수 있다. (세미콜론 x)
- 함수의 몸체가 하나의 return 문으로만 이루어진 경우에는 중괄호({})를 생략할 수 있다. 
- return 문 대신 표현식을 사용할 수 있으며, 이 때 반환값은 표현식의 결과값이 된다.(세미콜론 x)

람다식은 불필요한 코드를 줄일 수 있으며, 가독성을 높여준다.

```java
new Thread(new Runnable() {
  public void run() {
	  System.out.println("전통적인 방식의 일회용 스레드 생성");
  }
}).start();

new Thread(()->{
  System.out.println("람다 표현식을 사용한 일회용 스레드 생성");
}).start();
```

## 함수형 인터페이스(Functional Interface)

> 람다 표현식을 사용할 때는 람다 표현식을 저장하기 위한 참조 변수의 타입을 결정해야 한다.
>
> `함수형 인터페이스`는 람다 표현식을 하나의 변수에 대입할 때 사용하는 참조 변수의 타입이다.
>
> 함수형 인터페이스는 추상 클래스와는 달리 단 하나의 추상 메소드만을 가져야 한다. 또한, 다음과 같은 어노테이션(annotation)을 사용하여 함수형 인터페이스임을 명시할 수 있다. (인터페이스에 추상메소드가 하나만 있다면 함수형 인터페이스라 볼 수 있다.)
>
> - @FunctionalInterface
>
> 위와 같은 어노테이션을 인터페이스의 선언 앞에 붙이면, 컴파일러는 해당 인터페이스를 함수형 인터페이스로 인식하며, 하나의 함수형 인터페이스에 두 개 이상의 메소드가 있다면 컴파일러는 오류를 내뱉는다.

## 대표적인 기본 함수형 인터페이스

- 더 많은 함수형 인터페이스는 [여기](https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html)를 클릭하면 된다. (Oracle Docs)

#### Supplier<T> : 인자를 받지 않고 T타입의 값을 제공하는 함수형 인터페이스

```java
@FunctionalInterface
public interface Supplier<T> {
    T get();
}
```

`get()`메소드를 호출하여 다음과 같이 사용할 수 있다.

```java
Supplier<String> getString = () -> "Supplier!";
String str = getString.get();
System.out.println(str);
// Supplier!
```

#### Consumer<T> : T타입을 받아 아무값도 반환하지 않는 함수형 인터페이스

```java
@FunctionalInterface
public interface Consumer<T> {
    void accept(T t);
    
    default Consumer<T> andThen(Consumer<? super T> after) {
        Objects.requireNonNull(after);
        return (T t) -> { accept(t); after.accept(t); };
    }
}
```

`accept()`메소드를 사용하여 다음과 같이 사용할 수 있다.

```java
Consumer<String> printString = text -> System.out.println("Good " + text + "!");
printString.accept("bye");
// Good bye!
```

`andThen()`을 사용하면 두개 이상의 Consumer를 연속적으로 실행할 수 있다.

이 때 실행 순서는 앞에서부터 뒤로 간다.

```java
Consumer<String> printString = text -> System.out.println("Good " + text + "!");
Consumer<String> printString2 = text -> System.out.println("--> See you!");
printString.andThen(printString2).accept("bye");
// Good bye!
// --> See you!
```

#### Function<T, R> : T타입을 받아 R타입을 반환하는 함수형 인터페이스

```java
@FunctionalInterface
public interface Function<T, R> {
    R apply(T t);
    
    default <V> Function<V, R> compose(Function<? super V, ? extends T> before) {
        Objects.requireNonNull(before);
        return (V v) -> apply(before.apply(v));
    }
    
    default <V> Function<T, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.apply(apply(t));
    }

    static <T> Function<T, T> identity() {
        return t -> t;
    }
}

```

`apply()` 메소드를 사용하여 다음과 같이 사용할 수 있다.

```java
Function<Integer, Integer> multiply = (value) -> value * 2;
Integer result = multiply.apply(3);
System.out.println(result);
// 6
```

`compose()`는 두개의 Function을 조합하여 새로운 Function 객체를 만들어주는 메소드이다.

`andThen()`과의 차이점은 실행 순서이다. `compose()`는 인자로 전달되는 Function이 먼저 수행되고 그 이후에 호출하는 객체의 Function이 수행된다.

아래 코드에서처럼 multiply 와 add를 합쳐 addThenMultiply 이름의 Function을 만들 수 있다. 이 때, add가 먼저 수행되고, multiply가 나중에 수행된다.

```java
Function<Integer, Integer> multiply = (value) -> value * 2;
Function<Integer, Integer> add = (value) -> value + 3;
Function<Integer, Integer> addThenMultiply = multiply.compose(add);
Integer result1 = addThenMultiply.apply(3);
System.out.println(result1);
// 12
```

#### Predicate<T> : T타입을 받아서 boolean을 반환하는 함수형 인터페이스

```java
@FunctionalInterface
public interface Predicate<T> {
    boolean test(T t);

    default Predicate<T> and(Predicate<? super T> other) {
        Objects.requireNonNull(other);
        return (t) -> test(t) && other.test(t);
    }

    default Predicate<T> negate() {
        return (t) -> !test(t);
    }

    default Predicate<T> or(Predicate<? super T> other) {
        Objects.requireNonNull(other);
        return (t) -> test(t) || other.test(t);
    }

    static <T> Predicate<T> isEqual(Object targetRef) {
        return (null == targetRef)
                ? Objects::isNull
                : object -> targetRef.equals(object);
    }

    @SuppressWarnings("unchecked")
    static <T> Predicate<T> not(Predicate<? super T> target) {
        Objects.requireNonNull(target);
        return (Predicate<T>)target.negate();
    }
}

```

`test()` 메소드를 사용하여 다음과 같이 쓸 수 있다.

```java
Predicate<Integer> isBiggerThanFive = num -> num > 5;
System.out.println("10 is bigger than 5? -> " + isBiggerThanFive.test(10));
// 10 is bigger than 5? -> true
```

`and()`와 `or()`은 다른 Predicate와 함께 사용된다. `and()`는 두 개 모두 true일 때 true를, `or()`는 둘 중 하나라도 true이면 true를 반환한다.

`negate()`는 반환될 boolean값의 역을 반환한다.

`isEqual()`은 static 메소드로, 인자로 전달되는 객체와 같은지를 체크한다.

```java
Predicate<Integer> isBiggerThanFive = num -> num > 5;
Predicate<Integer> isLowerThanSix = num -> num < 6;
System.out.println(isBiggerThanFive.and(isLowerThanSix).test(10));
System.out.println(isBiggerThanFive.or(isLowerThanSix).test(10));
// false
// true

Predicate<String> isEquals = Predicate.isEqual("Google");
isEquals.test("Google");
// true
```

## 메소드 참조

> `메소드 참조(Method Reference)`는 람다 표현식이 단 하나의 메소드만을 호출하는 경우에 해당 람다 표현식에서 불필요한 매개변수를 제거하고 사용할 수 있도록 해준다.

`::`기호를 사용하며, 다음과 같이 쓸 수 있다.

> `클래스 이름::메소드 이름` 혹은 `참조변수이름::메소드 이름` 과 같이 사용한다.

다음 예를 보자. 아래 두 줄은 같은 역할을 수행한다.

```java
(base, exponent) -> Math.pow(base, exponent);	// 람다 표현식
Math::pow;	// 메소드 참조
```

또, 특정 인스턴스의 메소드를 참조할 때에도 참조변수의 이름을 통해 메소드 참조를 사용할 수 있다.

```java
MyClass obj = new MyClass;
Function<String, Boolean> func = (a) -> obj.equals(a)	// 람다 표현식
Function<String, Boolean> func = obj::equals(a)	// 메소드 참조
```

## 생성자 참조

> 생성자를 호출하는 람다 표현식도 위의 메소드 참조를 이용할 수 있다.
>
> 즉, 단순히 객체를 생성하고 반환하는 람다 표현식은 생성자 참조로 변환할 수 있다.

밑의 1번째 줄은 단순히 객체를 생성하고 반환하는 람다식이다. 2번째 줄은 메소드 참조를 이용한 코드이다.

```java
(a) -> { return new Object(a); }	// 람다식
Object::new;	// 메소드 참조
```

이 때 해당 생성자가 존재하지 않으면 컴파일 시 오류가 발생한다.

배열을 생성할 때에도 생성자 참조 사용이 가능하다.

```java
Function<Integer, double[]> func1 = a -> new double[a];	// 람다식
Function<Integer, double[]> func2 = double[]::new;	// 생성자 참조
```

## Refer to

- http://www.tcpschool.com/java/java_lambda_concept
- http://www.tcpschool.com/java/java_lambda_reference