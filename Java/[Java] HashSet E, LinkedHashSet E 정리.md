> 슈퍼클래스 Set 에 대해 알고 싶으면 다음 포스팅을 참고하세요.
> HashSet 의 주요 메소드는 대부분 Set 클래스와 동일하므로 밑의 포스팅에 정리하였습니다.

[2020/11/11 - [Language/Java\] - [Java] Set 정리](https://gre-eny.tistory.com/5)



## HashSet의 상속관계

```java
Module java.base
Package java.util
  
Class HashSet<E>
	java.lang.Object
		java.util.AbstractCollection<E>
			java.util.AbstractSet<E>
				java.util.HashSet<E>
  				java.util.LinkedHashSet<E>
```



## HashSet의 특징

- 중복된 값을 허용하지 않는다.
- 순서를 보장하지 않는다.
- null 값 저장 가능한다.
- 내부적으로는 HashMap을 사용하여 데이터를 저장한다.
- 해싱을 사용하여 구현하므로, TreeSet 보다 검색속도가 빠르다.

> 저장하려는 요소가 객체인 경우, 객체의 내용이 같더라도 set에 추가되는 경우가 있다. 이는 내용은 같지만 객체 자체는 다르기 때문에 발생한다. HashSet은 hashCode()를 사용하여 데이터의 중복을 검사하기 때문에 같은 내용의 다른 객체의 추가를 막기 위해선 hashCode() 의 @Override가 필요하다.



## LinkedHashSet의 특징

- 기존 HashSet의 특징 + 순서를 보장한다.



## HashSet의 주요 메소드

- boolean **add(E e)**
- boolean **addAll(Collection<?> c)**
- boolean **remove(Object o)**
- boolean **removeAll(Collection<?> c)**
- boolean **removeIf(Predicate<? super E> filter)**
- void **clear()**
- boolean **contains(Object o)**
- Iterator<E> **iterator()**
- boolean **isEmpty()**
- int **size()**



## HashSet 생성

```java
HashSet<Integer> set1 = new HashSet<Integer>();	//HashSet생성
HashSet<Integer> set2 = new HashSet<>();				//new에서 타입 파라미터 생략가능
HashSet<Integer> set3 = new HashSet<Integer>(set1);	//set1의 모든 값을 가진 HashSet생성
HashSet<Integer> set4 = new HashSet<Integer>(10);		//초기 용량(capacity)지정
HashSet<Integer> set5 = new HashSet<Integer>(10, 0.7f);	//초기 capacity,load factor지정
HashSet<Integer> set6 = new HashSet<Integer>(Arrays.asList(1,2,3));	//초기값 지정
```

HashSet을 기본으로 생성했을때에는 initial capacity(16), load factor(0.75)의 값을 가진 HashSet객체가 생성된다. HashSet도 저장공간보다 값이 추가로 들어오면 List처럼 저장공간을 늘리는데 Set은 한 칸씩 저장공간을 늘리지 않고 저장용량을 약 두배로 늘린다. 여기서 과부하가 많이 발생하므로, 초기에 저장할 데이터 갯수를 알고 있다면 Set의 초기용량을 지정해주는 것이 좋다.

load factor는 저장공간이 가득 차기 전에 미리 용량을 확보하기 위한 것이다. 기본값은 0.75. 75% 채워지면 용량이 두 배로 늘어난다.



## LinkedHashSet 생성

```java
LinkedHashSet<Integer> set = new LinkedHashSet<>();
```



## REFERENCE

- [Oracle docs/HashSet](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/HashSet.html#)
- [Oracle docs/LinkedHashSet](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/LinkedHashSet.html#)

