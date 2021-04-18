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

> 해당 포스트에서는 `2. 스트림 중개 연산(스트림 변환, 가공)`에 관해서만 다루므로 `스트림 생성`과 `스트림 최종 연산`은 아래 다른 포스트를 연결해 놓겠다.

- [2021.03.11 - [Language/Java\] - [Java] Stream API - 1. 스트림 생성](https://gre-eny.tistory.com/191)
- [2021.03.16 - [Language/Java\] - [Java] Stream API - 3. 스트림 최종연산](https://gre-eny.tistory.com/193)

## 스트림 중개 연산(Intermediate Operation)

> `스트림 중개연산`은 생성된 스트림을 전달받아 다른 스트림으로 변환시켜 반환하는 역할을 한다.
>
> 대표적인 중개연산은 다음이 있다.

- Filtering - filter(), distinct()
- Mapping - map(), flatMap()
- Restricting - limit(), skip()
- Sorting - sorted()
- Iterating - peek()

## Filtering

`Filtering`은 스트림 내 요소들을 하나씩 평가해서 걸러내는 작업이다. 인자로 받는 Predicate는 boolean을 리턴하는 함수형 인터페이스로 평가식이 들어가게 된다.

### distinct()

> `distinct()`메소드는 해당 스트림에서 중복된 요소가 제거된 새로운 스트림을 반환하며, 내부적으로 Object 클래스의 equals() 메소드를 사용하여 요소의 중복을 비교한다.

```java
Stream<T> distinct();
```

### filter()

> `filter()`메소드는 해당 스트림에서 주어진 조건(predicate)에 맞는 요소만으로 구성된 새로운 스트림을 반환한다.

```java
Stream<T> filter(Predicate<? super T> predicate);
```

 다음 예를 살펴보자.

```java
List<Integer> list = Arrays.asList(7, 5, 5, 2, 1, 2, 3, 5, 4, 6);

Stream<Integer> stream1 = list.stream().distinct();	// [7, 5, 2, 1, 3, 4, 6]
Stream<Integer> stream2 = list.stream().filter(e -> e % 2 == 0)	// [2, 2, 4, 6]
```

스트림의 각 요소에 대해서 평가식을 실행하게 되고, 평가식에 충족한 값들만 들어간 스트림이 리턴된다.

## Mapping

`Mapping`은 스트림에 들어가 있는 값이 input이 되어 특정 로직을 거친 후 output이 되어 새로운 스트림에 담는 작업이다.

### map()

> `map()`메소드는 스트림 내 요소들을 하나씩 특정 값으로 변환해준다. 이 때 값을 변환하기 위한 람다식을 인자로 받는다. 

```java
<R> Stream<R> map(Function<? super T, ? extends R> mapper);
```

주어진 스트림을 가지고 다양하게 새로운 스트림으로 변환할 수 있다.

```java
List<String> list = Arrays.asList("Java", "Kotlin", "Scala");

Stream<Integer> stream1 = list.stream().map(String::length);	// [4, 6, 5];
Stream<String> stream2 = list.stream().map(String::upperCase);	// [JAVA, KOTLIN, SCALA]
```

밑의 예제처럼 Product 타입 객체 내의 특정 값만을 추출하여 새로운 스트림으로 만들 수도 있다.

productList는 Product 타입 객체를 담고있는 List이다.

```java
Stream<Integer> stream = productList.stream().map(Product::getAmount);	// [10, 24, 34, 64]
```

### flatMap()

> `flatMap()`메소드는 변환하려는 해당 스트림의 요소가 배열일 때 사용한다.
>
> 인자로 `mapper`를 받고, 리턴 타입은 Stream이다. 즉, 새로운 스트림을 생성해서 리턴하는 람다를 넘겨줘야한다. `flatMap`은 중첩 구조를 한 단계 제거하고 단일 컬렉션으로 만들어주는 역할을 하며, 이러한 작업을 플래트닝(flattening)이라고 한다.

```java
List<List<Integer>> list = Arrays.asList(Arrays.asList(1, 3), Arrays.asList(4, 2)); // [[1,3],[4,2]]

List<Integer> flatMapStream = list.stream().flatMap(Collection::stream)
		.collect(Collectors.toList());	// [1, 3, 4, 2]
```

위에서처럼 중첩 구조(이중 리스트)를 제거하고 작업할 수 있다.

밑의 코드처럼 객체에도 적용이 가능하다. Student객체를 담고있는 리스트를 스트림으로 생성하고, 객체가 가지고있는 각 과목 점수를 flatMap을 이용하여 접근이 가능하다.

```java
students.stream().flatMapToInt(student -> IntStream.of(student.getKor(), student.getEng(), student.getMath()))
		.average().ifPresent(System.out::println);
```

## Restricting

### limit()

> `limit()`메소드는 해당 스트림의 첫 번째 요소부터 전달된 개수만큼의 요소만으로 새로운 스트림을 구성한다. 앞 포스팅의 `스트림 생성`에서 `Stream.generate()`, `Stream.iterate()`의 예제에서도 잠깐 쓰였던 메소드이다. `skip()`메소드와 혼용이 가능하다.

```java
Stream<T> limit(long maxSize);
```

### skip()

> `skip()`메소드는 해당 스트림의 첫 번재 요소부터 전달된 개수만큼의 요소를 제외한 나머지 요소만으로 새로운 스트림을 구성한다. `limit()`메소드와 혼용이 가능하다.

```java
Stream<T> skip(long n);
```

간단한 예제를 살펴보자.

```java
IntStream stream = IntStream.range(0, 10);

Stream<Integer> stream1 = stream.limit(5);	// [0,1,2,3,4]
Stream<Integer> stream2 = stream.skip(3);	// [3,4,5,6,7,8,9]
Stream<Integer> stream3 = stream.skip(3).limit(5);	// [3,4,5,6,7]
```

## Sorting

### sorted()

> `sorted()`메소드는 해당 스트림을 주어진 비교자(Comparator)를 이용하여 정렬한다.
>
> 이 때, 비교자를 전달하지 않으면 기본적으로 사전 순으로(natural order) 정렬된다. (숫자의 경우 오름차순)

```java
Stream<T> sorted();
Stream<T> sorted(Comparator<? super T> comparator);
```

Comparator는 사용법이 동일하다. (5번째 라인은 문자열 길이 순 정렬)

```java
List<String> list = Arrays.asList("Java", "C", "Python", "Kotlin", "Scala", "Go");

list.stream().sorted()
  .collect(Collectors.toList());	// [C, Go, Java, Kotlin, Python, Scala]
list.stream().sorted(Comparator.reverseOrder())
  .collect(Collectors.toList());	// [Scala, Python, Kotlin, Java, Go, C]
list.stream().sorted(Comparator.comparingInt(String::length))
  .collect(Collectors.toList());	// [C, Go, Java, Scala, Kotlin, Python]
```

## Iterating

### peek()

> 스트림 내 요소들 각각을 대상으로 특정 연산을 수행하는 메소드로 `peek()`이 있다. 이 메소드는 원본 스트림에서 요소를 소모하지 않으므로, 주로 연산과 연산 사이에 결과를 확인하기 위해 사용된다. (디버깅 용도로 많이 사용한다.)
>
> 아무 것도 반환하지 않는 함수형 인터페이스 Consumer를 인자로 받는다.

```java
Stream<T> peek(Consumer<? super T> action);
```

다음과 같이 사용할 수 있다.

```java
IntStream stream = IntStream.range(0, 10);

stream.peek(System.out::print)	// 0123456789
	.sum();	// 45
```

## Refer to

- [http://www.tcpschool.com/java/java_stream_intermediate](http://www.tcpschool.com/java/java_stream_creation)
- https://futurecreator.github.io/2018/08/26/java-8-streams/