> Set의 서브클래스에 대해 알고 싶으면 다음 포스팅을 참고하세요.

[2020/12/12 - [Language/Java\] - [Java] HashSet, LinkedHashSet 정리](https://gre-eny.tistory.com/14)

[2020/12/12 - [Language/Java\] - [Java] TreeSet 정리](https://gre-eny.tistory.com/15)



## Set 생성

```java
HashSet<Integer> set1 = new HashSet<>();				
TreeSet<Integer> set2 = new TreeSet<>();
LinkedHashSet<Integer> set3 = new LinkedHashSet<>();
```

Set을 생성할 때에는 Set으로 생성해도 되지만, 선언 시에는 Specific한 Set을 지정하여 선언하여야 한다.



## Set.add(E e)

`add()` 메소드는 Set 에 받은 element를 저장한다.

이미 저장되어 있다면 false를, 저장되어 있지 않다면 저장 후 true를 리턴한다.



## Set.addAll(Collection<?> c)

`addAll()` 메소드는 Collection에 저장된 아이템들을 모두 Set 에 모두 저장한다.

```java
Set <Integer> set = new HashSet<Integer>();
set.add(1);
set.add(2);
set.add(3);
System.out.println("set = " + set);
// set: [1, 2, 3]

List<Integer> list = new ArrayList<Integer>();
list.add(3);
list.add(4);
list.add(5);

set.addAll(list);
System.out.println("set = " + set);
// set: [1, 2, 3, 4, 5]
```



## Set.remove(Object o)

`remove()` 메소드는 받은 Object를 Set 에서 제거한다.

인자가 Set 에 존재하여 제거에 성공하면 true를, Set 에 존재하지 않아 제거이 실패하면 false를 리턴한다.



## Set.removeAll(Collection<?> c)

`removeAll()` 메소드는 인자로 받은 Collection에 저장된 아이템들을 Set 에서 제거한다.

```java
Set <Integer> set = new HashSet<Integer>();
set.add(1);
set.add(2);
set.add(3);
System.out.println("set = " + set);
// set: [1, 2, 3]

List<Integer> list = new ArrayList<Integer>();
list.add(3);
list.add(4);
list.add(5);

set.removeAll(list);
System.out.println("set = " + set);
// set: [1, 2]
```



## Set.removeIf(Predicate<? super E> filter)

`removeIf()` 메소드는 인자로 람다식을 받을 수 있다. 함수형 인터페이스로 만들어진 객체를 받는다고 할 수 있다.

Set 의 아이템 중에 이 조건에 충족하는 객체는 제거된다.

> 다음은 HashSet의 아이템중 1 보다 큰 아이템들을 삭제하는 예제이다.

```java
Set <Integer> set = new HashSet<Integer>();
set.add(1);
set.add(2);
set.add(3);
System.out.println("set = " + set);
// set: [1, 2, 3]

set.removeIf(e -> e > 1);

System.out.println("set = " + set);
// set: [1]
```



## Set.clear()

`clear()` 메소드는 Set 의 모든 아이템들을 삭제한다.



## Set.contains(Object o)

`contains()` 메소드는 받은 인자가 Set 에 존재하는지 판별한다.

존재한다면 true를, 존재하지 않다면 false를 리턴한다.



## Set.iterator()

`iterator()` 메소드는 Iterator 객체를 리턴한다. 이 객체로 Set 의 모든 아이템을 순회할 수 있다.

> 다음은 HashSet의 모든 객체를 순회하면서 출력한다.

```java
Set <Integer> set = new HashSet<Integer>();
set.add(1);
set.add(2);
set.add(3);

Iterator<Integer> iter = set.iterator();
while (iter.hasNext())
	System.out.println("set: " + iter.next());
	
// set: 2
// set: 1
// set: 3
// HashSet은 순서를 보장하지 않으므로 출력값은 달라질 수 있다.
```

> for문을 사용하여 접근하는 방법도 가능한다.

```java
Set <Integer> set = new HashSet<Integer>();
set.add(1);
set.add(2);
set.add(3);

for (int number : set)
	System.out.println("set: " + number);
	
// set: 2
// set: 1
// set: 3
// HashSet은 순서를 보장하지 않으므로 출력값은 달라질 수 있다.
```



## Set.isEmpty()

`isEmpty()` 메소드는 Set 이 비어있는지를 판별한다.

비어있다면 true를, 비어있지 않다면 false를 리턴한다.



## Set.size()

`size()` 메소드는 Set 의 크기(저장된 아이템 개수)를 리턴한다.



## REFERENCE

- [Oracle docs/AbstractSet](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/AbstractSet.html#)
