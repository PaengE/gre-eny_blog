## TreeSet의 상속관계

```java
Module java.base
Package java.util
  
Class TreeSet<E>
	java.lang.Object
		java.util.AbstractCollection<E>
			java.util.AbstractSet<E>
				java.util.TreeSet<E>
```



## TreeSet의 특징

- 중복된 값을 허용하지 않는다.
- 데이터 정렬이 가능하다(기본값 오름차순).
- null 값 저장 가능한다.
- 이진탐색트리(레드-블랙 트리)를 사용하여 구현한다. 해싱보다는 느리다.



## 레드-블랙 트리(Red-Black Tree)

![img](https://blog.kakaocdn.net/dn/czb0Rs/btqEt6tVogn/KpmfrL9PfiNK9ioz0WkRq1/img.png)

**레드블랙 트리**는 자가균형이진탐색 트리(self-balancing binary search tree)로써, 대표적으로 연관배열(associative array) 등을 구현하는데 쓰이는 자료구조이다. 레드-블랙 트리는 복잡한 자료구조이지만, 실 사용에서 효율적이고, 최악의 경우에도 상당히 우수한 실행 시간을 보인다. 트리에 n개의 원소가 있을때 Θ(log n)의 시간복잡도로 삽입, 삭제, 검색을 할 수 있다.



## Set의 주요 메소드

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



## TreeSet의 주요 메소드

> ### 생성과 선언을 모두 TreeSet으로 해줘야만 사용가능하다.
>
> ### Set으로 생성한 경우, TreeSet의 메소드를 사용할 수 없다.

- NavigableSet<E> **descendingSet()**
- Iterator<E> **descendingIterator()**
- SortedSet<E> **headSet(E toElement)**
- NavigableSet<E> **headSet(E toElement, boolean inclusive)**
- SortedSet<E> **tailSet(E fromElement)**
- NavigableSet<E> **taliSet(E fromElement, boolean inclusive)**
- SortedSet<E> **subSet(E fromElement, Object toElement)**
- NavigableSet<E> **subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive)**
- 단일 객체 검색 메소드



## TreeSet생성

```java
TreeSet<Integer> set1 = new TreeSet<Integer>();	//TreeSet생성
TreeSet<Integer> set2 = new TreeSet<>();	//new에서 타입 파라미터 생략가능
TreeSet<Integer> set3 = new TreeSet<Integer>(set1);	//set1의 모든 값을 가진 TreeSet생성
TreeSet<Integer> set4 = new TreeSet<Integer>(Arrays.asList(1,2,3));	//초기값 지정
```

TreeSet은 HashSet과 다르게 선언시 크기를 지정할 수 없다.



## NavigableSet<E> **descendingSet()**

`descendingSet()` 메소드는 TreeSet에 저장된 요스를 역순으로 정렬해서 리턴한다.

기본적으로 TreeSet은 오름차순이므로, 이를 내림차순으로 재정렬하여 리턴해준다.



## Iterator<E> descendingIterator()

`descendingIterator()` 는 기존의 Iterator를 역순으로 수행하는 것이다.

```java
TreeSet<Integer> set = new TreeSet<>(Arrays.asList(1, 2, 3, 4, 5));

Iterator<Integer> iter = set.descendingIterator();
while (iter.hasNext())
	System.out.println("set: " + iter.next());
	
/* 출력 결과
	set: 5
	set: 4
	set: 3
	set: 2
	set: 1
*/
```



## SortedSet<E> **headSet(E toElement)**

`headSet()` 메소드는 지정한 객체보다 더 앞 쪽에 저장된 객체, 즉 더 작은 값의 객체 set을 리턴한다.



## NavigableSet<E> **headSet(E toElement, boolean inclusive)**

`headSet()` 메소드는 inclusive 파라미터를 생략할 수 있는데, 생략한다면 default값은 false이다.

inclusive 값이 true면 지정한 객체와 같은 값 객체도 포함하며, false라면 포함하지 않는다.



## SortedSet<E> **tailSet(E fromElement)**

`tailSet()` 메소드는 지정한 객체보다 더 뒤 쪽에 저장된 객체, 즉 더 큰 값의 객체 set을 리턴한다.



## NavigableSet<E> **taliSet(E fromElement, boolean inclusive)**

`tailSet()` 메소드는 inclusive 파라미터를 생략할 수 있는데, 생략한다면 default값은 false이다.

inclusive 값이 true면 지정한 객체와 같은 값 객체도 포함하며, false라면 포함하지 않는다.



## SortedSet<E> **subSet(E fromElement, E toElement)**

`subSet()` 메소드는 지정한 두 객체 사이에 저장된 객체, 즉 사이에 있는 값의 객체 set을 리턴한다.



## NavigableSet<E> **subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive)**

`subSet()` 메소드도 마찬가지로 inclusive 파라미터를 생략할 수 있으며, default값은 fromInclusive = true, toInclusive = false 이다. 즉, 시작범위는 포함하지만 끝범위는 포함하지 않는다.

하지만 변경 가능하며 inclusive 값이 true면 지정한 객체와 같은 값 객체도 포함하며, false라면 포함하지 않는다.

> 밑의 코드는 descendingSet(), headSet(), tailSet(), subSet() 과 관련된 예제이다.

```java
TreeSet<Integer> set = new TreeSet<Integer>(Arrays.asList(2, 3, 4, 6, 5, 7, 1));

System.out.println("set: " + set);
System.out.println("descendingSet: " + set.descendingSet());
System.out.println("headSet1: " + set.headSet(new Integer(4)));
System.out.println("headSet2: " + set.headSet(new Integer(4), true));
System.out.println("tailSet: " + set.tailSet(new Integer(4)));
System.out.println("subSet: " + set.subSet(new Integer(2), new Integer(5)));

/* 출력 결과
	set: [1, 2, 3, 4, 5, 6, 7]
	descendingSet: [7, 6, 5, 4, 3, 2, 1]
	headSet1: [1, 2, 3]
	headSet1: [1, 2, 3, 4]
	tailSet: [5, 6, 7]
	subSet: [2, 3, 4]
*/
// primitive type을 사용할 수 없으므로 Wrapper 객체를 이용하여 접근하여야 한다.
```



## 단일 객체 검색 메소드

| 리턴 타입 | 메소드       | 설명                                                         |
| --------- | ------------ | ------------------------------------------------------------ |
| E         | first()      | 제일 낮은 객체를 리턴                                        |
| E         | last()       | 제일 높은 객체를 리턴                                        |
| E         | lower(E e)   | 주어진 객체보다 바로 아래 객체를 리턴                        |
| E         | higer(E e)   | 주어진 객체보다 바로 위 객체를 리턴                          |
| E         | floor(E e)   | 주어진 객체와 동등한 객체가 있으면 리턴, 만약없다면 주어진 객체의 바로 아래의 객체를 리턴 |
| E         | ceiling(E e) | 주어진 객체와 동등한 객체가 있으면 리턴, 만약없다면 주어진 객체의 바로 위의 객체를 리턴 |
| E         | pollFirst()  | 제일 낮은 객체를 꺼내오고 컬렉션에서 제거함                  |
| E         | pollLast()   | 제일 높은 객체를 꺼내오고 컬렉션에서 제거함                  |



## REFERENCE

- [Oracle docs/TreeSet](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/TreeSet.html#)

