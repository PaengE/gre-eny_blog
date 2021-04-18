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

> 해당 포스트에서는 `3. 스트림 최종 연산(스트림 결과 만들기)`에 관해서만 다루므로 `스트림 생성`과 `스트림 중개 연산`은 아래 다른 포스트를 연결해 놓겠다.

- [2021.03.11 - [Language/Java\] - [Java] Stream API - 1. 스트림 생성﻿](https://gre-eny.tistory.com/191)
- [2021.03.14 - [Language/Java\] - [Java] Stream API - 2. 스트림 중개연산](https://gre-eny.tistory.com/192)

## 스트림 최종 연산(Terminal Operation)

> `스트림 최종연산`은 중개연산을 통해 변환된 스트림의 각 요소를 소모하여 결과 스트림을 생성한다.
>
> 즉, 지연(lazy)되었던 모든 중개연산들이 최종연산 시에 모두 수행된다. (최종연산이 없으면 중개연산은 수행이 안된다.)
>
> 그리고 최종연산 시에 모든 요소를 소모한 해당 스트림은 더는 사용할 수 없게 된다.(재사용 불가)
>
> 대표적인 최종연산은 다음이 있다.

- Calculating - sum(), count(), min(), max(), average()
- Reduction - reduce()
- Collecting - collect()
- Matching - anyMatch(), allMatch(), noneMatch()
- Searching - findFirst(), findAny()
- Iterating - forEach()

## Calculating

### sum(), count()

> `sum()`메소드는 스트림의 각 요소를 더한 총합을 int 타입으로 리턴한다.
>
> `count()`메소드는 스트림의 각 요소의 총 개수를 long 타입으로 리턴한다.

`sum()`과 `count()`메소드는 스트림이 비어있던, 비어있지 않던 상관없기 때문에 기본타입으로 결과를 리턴한다.

```java
int sum = IntStream.of(1, 2, 3, 4, 5).sum()	// 15
long count = DoubleStream.of(1.1, 1.2, 1.3, 1.4, 1.5).count()	// 5
```

### max(), min(), average()

> `max()`메소드는 스트림의 각 요소 중 최댓값을 각 타입으로 래핑된 Optional 객체로 리턴한다.
>
> `min()`메소드는 스트림의 각 요소 중 최솟값을 각 타입으로 래핑된 Optional 객체로 리턴한다.
>
> `average()`메소드는 스트림의 각 요소들의 평균값을 각 타입으로 래핑된 Optional 객체로 리턴한다.

위의 세 메소드 모두 스트림이 비어있을 땐, 결과를 만들 수 없으므로 기본적으로 Optional 객체로 리턴한다.

값을 얻고 싶다면 `getAsXXX()`메소드를 사용하면 된다.

아니면 `ifPresent()`메소드를 사용하여 Optional을 바로 처리할 수도 있다.

```java
IntStream stream1 = IntStream.of(1, 3, 5, 7, 9);
IntStream stream2 = IntStream.of(1, 3, 5, 7, 9);

OptionalInt min = stream1.min();	// Optional[1]
OptionalInt max = stream2.max(); // Optional[9]
int getMin = min.getAsInt();
int getMax = max.getAsInt();

DoubleStream.of(1.1, 3.3, 5.5, 7.7, 9.9)
	.average()
	.ifPresent(System.out::println);	// 5.5
```

공통된 stream1 하나로 사용하여 min(), max() 메소드를 호출하면 오류가 뜨는데 이유는 스트림이 한번 사용된 후 닫히기 때문이다.(재사용이 불가능하다.)

따라서 리스트를 사용할 때마다 스트림으로 생성하여 사용하던지, 아니면 각각의 스트림을 따로 선언해줘야한다.

`ifPresent()`메소드 말고도 사용자가 원하는대로 결과를 만들어내기 위한 `reduce()`와 `collect()`메소드가 있다.

## Reduction

### reduce()

> `reduce()`메소드는 총 세가지 파라미터를 받을 수 있다.
>
> - accmulator: 각 요소를 처리하는 계산 로직이다. 각 요소가 올 때마다 중간 결과를 생성하는 로직이다.
> - identity: 계산을 위한 초기값으로, 스트림이 비어서 계산할 내용이 없더라도 리턴되는 값이다.
> - combiner: 병렬 스트림에서 나눠 계산한 결과를 하나로 합치는 로직이다.

```java
// 1개 (accumulator)
Optional<T> reduce(BinaryOperator<T> accumulator);

// 2개 (identity)
T reduce(T identity, BinaryOperator<T> accumulator);

// 3개 (combiner)
<U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner);
```

`BinaryOperator<T>`는 같은 타입의 인자 두개를 받아 같은 타입의 결과를 반환하는 함수형 인터페이스이다.

```java
OptionalInt reduce1Parameter = IntStream.rangeClosed(1, 3)	// [1, 2, 3]
	.reduce((a, b) -> {
		return Integer.sum(a, b);	// 6
	})
```

`reduce()`메소드의 파라미터가 하나라면, 주어진 람다식을 수행한 결과값을 리턴한다. 주어진 스트림이 비어있을 수 있으므로 Optional 객체로 리턴을 한다.

```java
int reduce2Parameter = IntStream.rangeClosed(1, 3)	// [1, 2, 3]
	.reduce(10, Integer::sum);	// 16
```

`reduce()`메소드의 파라미터가 둘이라면, 앞의 파라미터는 초기값이다. 여기서 람다식은 `메소드 참조`를 하여 넘길 수 있다.

주어진 스트림이 비어있어도 초기값이 있기 때문에 Optional 객체가 아닌 기본타입으로 값을 리턴한다.

```java
// Sequential Stream
Integer reduce3ParameterSequential = IntStream.rangeClosed(1, 3)	// [1, 2, 3]
	.reduce(10, Integer::sum, (a, b) -> {
		System.out.println("combiner was called");
		return a + b;	// 16
	});
	
// Parallel Stream
Integer reduce3ParameterParallel = Arrays.asList(1, 2, 3)	// [1, 2, 3]
	.parallelStream()
	.reduce(10, Integer::sum, (a, b) -> {
		System.out.println("combiner was called");
		return a + b;	// 36
	});
// 출력문
// combiner was called
// combiner was called
// 36
```

파라미터 `combiner`는 병럴 처리 시 각자 다른 쓰레드에서 실행한 결과를 마지막에 합치는 로직이다. 따라서 병렬 스트림에만 동작하며, 위의 첫번째 Sequential Stream에서는 동작을 하지않는다.

combiner자체가 호출되지 않으므로 당연히 프린트문도 호출되지 않는다. 결과값은 16(10+1+2+3)이다.

병렬스트림(Parallel Stream)에서는 다르게 동작한다. `accumulator`는 스레드의 수만큼 호출이 된다.(여기서는 3개이므로 3번)

일단 각 쓰레드에서 결과를 만든다 (10+1=11, 10+2=12, 10+3=13)

`combiner`는 `identity`와 `accumulator`를 가지고 여러 쓰레드에서 나눠 계산한 결과를 합치는 역할이므로, 12+13=25, 25+11=36 이렇게 두번 호출된다. 결과값은 36이다.

## Collecting

### collect()

> `collect()`메소드는 `Collector`타입의 인자를 받아서 처리한다. 자주 사용하는 작업은 `Collectors`객체에서 제공한다.

다음 productList를 사용하여 예제를 살펴보자. Product객체는 수량(amount)과 이름(name)을 가지고 있다.

```java
List<Product> productList = Arrays.asList(new Product(23, "potatoes"),
                      new Product(14, "orange"),
             			   	new Product(13, "lemon"),
               			 	new Product(23, "bread"),
                			new Product(13, "sugar"));
```

### Collectors.toList()

> 스트림에서 작업한 결과를 리스트로 담아 리턴한다.

```java
List<String> toList = productList.stream()
	.map(Product::getName)
	.collect(Collectors.toList());	// [potatoes, orange, lemon, bread, sugar]
```

비슷하게 `toArray()`, `toCollection()`, `toSet()`, `toMap()`도 사용할 수 있다.

## Collectors.joining()

> 스트림에서 작업한 결과를 하나의 스트링으로 이어 붙일 수 있다.

```java
String joining = productList.stream()
	.map(Product::getName)
	.collect(Collectors.joining());	// potatoesorangelemonbreadsugar
```

`Collectors.joining()`은 세개의 파라미터를 받을 수 있다.

- delimiter: 각 요소 중간에 들어가 요소를 구분시켜주는 구분자
- prefix: 결과 맨 앞에 붙는 접두사
- suffix: 결과 맨 뒤에 붙는 접미사

```java
String joining = productList.stream()
	.map(Product::getName)
	.collect(Collectors.joining(", ", "<", ">"));	// <potatoes, orange, lemon, bread, sugar>
```

### Collectors.averagingInt(), Collectors.summingInt()

> `Collectors.averagingInt()`는 숫자 값의 평균을, `Collectors.summingInt()`는 숫자 값의 합을 계산하여 리턴한다.

```java
Double avg = productList.stream()
	.collect(Collectors.averageingInt(Product::getAmount));	// 17.2
	
Integer sum = productList.stream()
	.collect(Collectors.summingInt(Product::getAmount));	// 86

Integer summing = productList.stream()
  .mapToInt(Product::getAmount).sum();
```

IntStream으로 바꿔주는 `mapToInt()`메소드를 사용해서 좀 더 간단하게 표현할 수도 있다.

### Collectors.summarizingInt()

> 주어진 숫자 스트림의 여러 정보를 얻고 싶을 때 사용한다.
>
> 예를 들어 최대, 최소, 평균을 알고 싶다면 `Collectors.summarizingInt()`를 사용하면 된다.

```java
IntSummaryStatistics statistics = productList.stream()
	.collect(Collectors.summarizingInt(Product::getAmount));
```

이렇게 저장된 IntSummaryStatistics 객체에는 아래와 같이 정보가 담겨있다.

`개수(getCount())`, `합계(getSum())`, `평균(getAverage())`, `최소(getMin())`, `최대(getMax())`

```java
IntSummaryStatistics {count=5, sum=86, min=13, average=17.200000, max=23}
```

이를 이용하면 `collect()`전에 통계 작업을 위한 `map()`메소드를 호출할 필요가 없다. 

averaging, summing, summarizing 메소드는 각 기본타입(int, long, double)별로 제공된다.

### Collectors.groupingBy()

> `Collectors.groupingBy()`를 사용하면 스트림의 각 요소를 특정 조건으로 그룹화할 수 있다. 
>
> 여기서 받는 인자는 함수형 인터페이스 Function이며, 반환형은 Map 타입이다. 같은 수량이면, 리스트로 묶어서 리턴한다.

```java
Map<Integer, List<Product>> m = productList.stream()
	.collect(Collectors.groupingBy(Product::getAmount));

/** 결과
	{23=[Product{amount=23, name='potatoes'}, 
       Product{amount=23, name='bread'}], 
 	 13=[Product{amount=13, name='lemon'}, 
     	 Product{amount=13, name='sugar'}], 
 	 14=[Product{amount=14, name='orange'}]}
*/
```

### Collectors.partitioningBy()

> 위의 `groupingBy()`가 함수형 인터페이스 Function을 인자로 받는다면, `partitioningBy()`는 함수형 인터페이스 Predicate를 받는다. Predicate는 인자를 받아 boolean값을 리턴한다.
>
> 반환형은 Map 타입이고, 스트림 내 각 요소를 특정 조건을 적용시켜 true, false 두가지로 나눈다.

```java
Map<Boolean, List<Product>> m = productList.stream()
  .collect(Collectors.partitioningBy(e -> e.getAmount() > 15));

/** 결과
	{false=[Product{amount=14, name='orange'}, 
        	Product{amount=13, name='lemon'}, 
        	Product{amount=13, name='sugar'}], 
 	 true=[Product{amount=23, name='potatoes'}, 
       	 Product{amount=23, name='bread'}]}
*/
```

### Collectors.collectingAndThen()

> 특정 타입으로 결과를 `collect()`한 이후에 추가 작업이 필요한 경우 사용할 수 있다.
>
> `collectingAndThen()`은 인자를 두개 받는데, 첫 번째 인자를 수행한 결과에, 두 번째 인자를 적용시킨 결과를 리턴한다.

```java
Set<Product> unmodifiableSet = productList.stream()
  .collect(Collectors.collectingAndThen(Collectors.toSet(),
                                        Collections::unmodifiableSet));
```

위의 코드는 toSet()으로 결과를 Set으로 collect한 후, 수정 불가한 Set으로 변환하는 작업을 추가로 실행한다.

### Collector.of()

> 위의 Collectors의 메소드들 외에도 직접 Collector를 만들 수 있다. accumulator와 combiner는 `reduce()`와 동일하다.

```java
public static<T, R> Collector<T, R, R> of(
  Supplier<R> supplier, // new collector 생성
  BiConsumer<R, T> accumulator, // 두 값을 가지고 계산
  BinaryOperator<R> combiner, // 계산한 결과를 수집하는 함수.
  Characteristics... characteristics) { ... }
```

다음 예제는 스트림을 LinkedList()로 리턴해주는 Collector이다.

스트림의 각 요소에 대해 LinkedList를 생성하고, 요소를 추가한다. 끝으로 생성된 리스트들을 하나의 리스트로 합친다.

```java
Collector<Product, ?, LinkedList<Product>> toLinkedList = 
  Collector.of(LinkedList::new, LinkedList::add, (first, second) -> {
                 first.addAll(second);
                 return first;
               });
```

위와 같이 정의한다면, 아래와 같이 사용할 수 있다.

```java
LinkedList<Product> ll = productList.stream().collect(toLinkedList());
```

## Matching

> Matching은 람다식 Predicate를 받아 해당 조건을 만족하는 요소가 있는지 체크한 결과를 리턴한다.
>
> - 하나라도 조건을 만족하는 요소가 있는지(anyMatch())
> - 모두 조건을 만족하는지(allMatch())
> - 모두 조건을 만족하지 않는지(noneMatch())

```java
boolean anyMatch(Predicate<? super T> predicate);
boolean allMatch(Predicate<? super T> predicate);
boolean noneMatch(Predicate<? super T> predicate);
```

간단한 예제를 살펴보자.

```java
List<Integer> list = Arrays.asList(5, 6, 7, 8, 9);

boolean anyMatch = list.stream().anyMatch(n -> n > 8);	// true
boolean allMatch = list.stream().allMatch(n -> n >= 5);	// true
boolean noneMatch = list.stream().noneMatch(n -> n < 5);	// true
```

## Searching

### findFirst()

> `findFirst()`메소드는 해당 스트림에서 첫 번째 요소를 참조하여 Optional 객체를 리턴한다.
>
> 비어있는 스트림의 경우, 비어있는 Optional 객체를 리턴한다.

### findAny()

> `findAny()`메소드는 해당 스트림에서 아무 요소나 참조하여 Optional 객체를 리턴한다.
>
> 비어있는 스트림의 경우, 비어있는 Optional 객체를 리턴한다.
>
> parallel하지 않은 작업에서는 스트림의 첫번째 요소를 리턴할 가능성이 높지만, 이에 대한 보장은 없다.

```java
List<Integer> list = Arrays.asList(1, 3, 5, 7, 9);

OptionalInt res1 = list.stream().findFirst();
OptionalInt res2 = list.stream().findAny();
```

## Iterating

### forEach()

> `forEach()`는 요소를 돌면서 실행되는 최종연산이다. 
>
> 보통 System.out.println() 메소드를 넘겨서 결과를 출력할 때 사용한다.
>
> 앞의 `peek()`과는 중개연산과 최종연산이라는 차이가 있다.

```java
list.stream().forEach(System.out::println)
```

## Refer to

- [http://www.tcpschool.com/java/java_stream_terminal](http://www.tcpschool.com/java/java_stream_creation)
- https://futurecreator.github.io/2018/08/26/java-8-streams/