## 스트림(Stream) API

> Java SE 8부터 추가된 스트림(Stream) API는 데이터를 추상화하여 다루므로, 다양한 바식으로 저장된 데이터를 읽고 쓰기 위한 공통된 방법을 제공한다.
>
> 따라서 스트림 API를 이용하면 배열이나 컬렉션뿐만 아니라 파일에 저장된 데이터도 모두 같은 방법으로 다룰 수 있다.

## 스트림(Stream) API의 특징

- 스트림은 외부 반복을 통해 작업하는 컬렉션과는 달리, 내부 반복(internal iteration)을 통해 작업을 수행한다.
- 스트림은 재사용이 가능한 컬렉션과는 달리, 단 한 번만 사용할 수 있다.
- 스트림은 원본 데이터를 변경하지 않는다.
- 스트림의 연산은 필터-맵(filter-map) 기반의 API를 사용하여 지연(lazy) 연산을 통해 성능을 최적화한다.
- 스트림은 parallelStream() 메소드를 통한 손쉬운 병렬 처리를 지원한다.

## 스트림 API의 동작 흐름

> 스트림은 크게 세가지 단계에 걸쳐서 동작한다.
>
> 1. 스트림 생성: 스트림 인스턴스 생성.
> 2. 스트림 중개 연산(스트림 변환, 가공): 필터링(filtering) 및 맵핑(mapping) 등 원하는 결과를 만들어가는 중간 작업.
> 3. 스트림 최종 연산(스트림 사용, 결과): 최종적으로 결과를 만들어내는 작업.

![img_java_stream_operation_principle](C:\Users\82102\OneDrive\티스토리\Java\image\img_java_stream_operation_principle.png)

> 해당 포스트에서는 `1. 스트림 생성`에 관해서만 다루므로 `스트림 중개 연산`과 `스트림 최종 연산`은 아래 다른 포스트를 연결해 놓겠다.

- [2021.03.14 - [Language/Java\] - [Java] Stream API - 2. 스트림 중개연산﻿](https://gre-eny.tistory.com/192)
- [2021.03.16 - [Language/Java\] - [Java] Stream API - 3. 스트림 최종연산﻿](https://gre-eny.tistory.com/193)

## 스트림 생성

> 생성과 관련하여는 다음과 같은 방법이 있다.

- 배열 / 컬렉션 / 빈 스트림 / 가변 매개변수
- Stream.builder() / Stream.generate() / Stream.iterate()
- 기본 타입형 / 문자열 스트림 / 파일 스트림
- 병렬 스트림 / 스트림 연결하기

### 배열 스트림

배열 스트림은 `Arrays.stream()`메소드를 활용하여 생성이 가능하다.

```java
String[] str = new String[]{"1", "2", "3", "4", "5"};
Stream<String> stream = Arrays.stream(str);
Stream<String> subStream = Arrays.stream(str, 3, 5);	// 3 ~ (5-1) 요소 [4, 5]
```

### 컬렉션 스트림

컬렉션 타입(Collection, List, Set)의 경우, 인터페이스에 추가된 default method인 `stream()`을 활용하여 생성한다.

```java
List<String> list = Arrays.asList("1", "2", "3", "4", "5");
Stream<String> stream = list.stream();
Stream<String> parallelStream = list.parallelStream();	// 병렬 처리 스트림
```

### 빈 스트림

빈 스트림은 `empty()`메소드를 활용하여 생성할 수 있고, 요소가 없을 때 `null` 대신 사용할 수 있다.

```java
Stream<String> emptyStream = Stream.empty();	// 요소의 개수가 0인 빈 스트림
```

### 가변 매개변수

`of()`메소드를 사용하여 직접적으로 값을 넣으며 가변적인 크기의 스트림을 생성할 수 있다.

```java
Stream<Double> doubleStream = Stream.of(5.1, 2.4, 3.9, 1.2);	// 크기가 4인 스트림 생성
```

### Stream.builder()

빌더(Builder)를 사용하면 스트림에 직접적으로 값을 넣을 수 있다. 마지막에 `build()`메소드로 스트림을 반환한다.

```java
Stream<String> builderStream = Stream.<String>builder()
		.add("3").add("2").add("1")
		.build();			// [3, 2, 1]
```

### Stream.generate()

`generate()` 메소드를 이용하면 `Supplier<T>`에 해당하는 람다로 값을 넣을 수 있다.

`Supplier<T>`는 아무 인자도 받지않고 T타입 값을 반환하는 함수형 인터페이스이다. 람다식에서 리턴하는 값이 들어간다.

이 때, 생성되는 스트림은 크기가 정해져있지 않고 무한하기 때문에 특정 사이즈로 최대 크기를 제한해야 한다. (`limit()` 사용 -> limit으로 지정해준 크기만큼의 스트림을 반환한다.)

```java
Stream<String> generateStream = Stream.generate(() -> "gs").limit(5);	// [gs, gs, gs, gs, gs]
```

### Stream.iterate()

`iterate()`메소드를 이용하면 초기값과 해당값을 다루는 람다식을 이용하여 스트림에 들어갈 요소를 만든다.

현재 인풋으로 만들어진 아웃풋이, 다음 요소의 인풋으로 들어간다. `iterate()`도 스트림의 사이즈가 무한하기 때문에 `limit()`을 이용하여 특정 사이즈로 제한해줘야 한다.

```java
Stream<Integer> iterateStream = Stream.iterate(10, n -> n * 2).limit(5);	// [10, 20, 40, 80, 160]
```

### 기본 타입형 스트림

제네릭(Generic)을 사요하면 리스트나 배열을 이용하여 기본타입 스트림을 생성할 수 있지만, 제네릭을 사용하지 않고 직접적으로 기본타입 스트림을 다룰 수도 있다.

`range`는 끝 범위를 포함하지 않고, `rangeClosed`는 범위를 포함한다.

```java
IntStream intStream = IntStream.range(1, 5)	// [1, 2, 3, 4]
LongStream longStream = LongStream.rangeClosed(1, 5)	// [1, 2, 3, 4, 5]
```

제네릭을 사용하지 않기 때문에 불필요한 오토박싱(auto-boxing)이 일어나지 않는다.

필요한 경우 `boxed()`메소드를 이용하여 박싱(boxing)할 수 있다.

```java
Stream<Integer> boxedIntStream = IntStream.range(1, 5).boxed();
```

Java 8 이상의 `Random` 클래스는 난수를 3가지 타입의 스트림(IntStream, LongStream, DoubleStream)을 만들어 낼 수 있다. 쉽게 난수 스트림을 생성하여 여러가지 후속 작업을 할 수 있어 유용하다.

```java
DoubleStream doubleStream = new Random().doubles(3);	// double형 난수 3개 생성
IntStream intStream = new Random().ints(5)	// int형 난수 5개 생성
```

### 문자열 스트림

String을 이용하여 스트림을 생성할 수도 있다.

```java
// 스트링의 각 문자(char)를 IntStream으로 변환(아스키코드 숫자로)
IntStream charStream = "Stream".chars();	// [83, 116, 114, 101, 97, 109]

// 정규표현식(Regular Expression)을 이용하여 문자열을 자르고, 각 요소들로 스트림을 생성
Stream<String> stringStream = Pattern.compile(", ").splitAsStream("A, B, C");	// [A, B, C]
```

### 파일 스트림

`Files`클래스의 `lines()`메소드는 해당 파일의 각 라인을 String 타입의 스트림으로 만들어준다.

```java
Stream<String> lineStream = Files.lines(Paths.get("example.txt"), Charset.forName("UTF-8"));
```

### 병렬 스트림 Parallel Stream

스트림 생성 시 사용하는 `stream()`대신 `parallelStream()`메소드를 사용해서 병렬 스트림을 생성할 수 있다.

내부적으로는 쓰레드를 처리하기 위해 Java 7부터 도입된 Fork/Join framework를 사용한다고 한다.

```java
Stream<Product> parallelStream = productList.parallelStream();	// 컬렉션 병렬 스트림 생성
boolean isParallel = parallelStream.isParallel();	// 병렬 여부 확인
```

위와 같이 선언함으로써, 다음 코드는 각 작업을 쓰레드를 이용해 병렬 처리한다.

```java
boolean isExist = parallelStream.map(product -> product.getAmount * 2)
		.anyMatch(amount -> amount > 200);
```

배열을 이용한 병렬 스트림 생성은 다음과 같다.

```java
Arrays.stream(arr).parallel()	// 배열 병렬 스트림 생성
```

컬렉션과 배열이 아닌 경우에는 `parallel()` 메소드를 이용한다.

```java
IntStream intStream = IntStream.range(1, 100).parallel();
boolean isParallel = intStream.isParallel();
```

다시 시퀀셜(sequential) 모드로 돌리고 싶다면 `sequential()`메소드를 사용하면 된다.

```java
IntStream intStream = intStream.sequential();
boolean isParallel = intStream.isParallel();
```

### 스트림 연결하기

`Stream.concat()`메소드를 이용하여 두개의 스트림을 연결해서 새로운 스트림을 만들어낼 수 있다.

```java
Stream<String> stream1 = Stream.of("C", "C++", "C#");
Stream<String> stream2 = Stream.of("Java", "Kotlin");
Stream<String> concat = Stream.concat(stream1, stream2);	// [C, C++, C#, Java, Kotlin]
```

## Refer to

- http://www.tcpschool.com/java/java_stream_creation
- https://futurecreator.github.io/2018/08/26/java-8-streams/