## HashMap

HashMap을 정의한다면, '키에 대한 해시 값을 사용하여 값을 저장하고 조회하며, 키-값 쌍의 개수에 따라 동적으로 크기가 증가하는 associate array'라고 할 수 있으며, 이 associate array(연관 배열)은 Map, Dictionary, Symbol Table 이라고도 불리운다.

간단하게 말하자면 Key-Value의 형태를 가진, Key와 Value가 1:1 매핑이 되어 하나의 쌍(Pair)으로 하여 중복된 Key를 허용하지 않는 기본적으로 순서가 없는 자료구조이다.

기본적으로 equals()를 사용하여 중복을 판단하기에 primitive data type은 걸러지지만, 객체(Object)는 객체의 값이 같더라도 equals()에서 서로 다르다고 판단하기 때문에 걸러지지 않는다. 

따라서 중복된 객체를 막으려면 equals를 override 해주어야 한다.

자세한 HashMap의 동작 방법은 다음 링크를 참고하면 좋을 것이다.

[Naver D2 - Java HashMap은 어떻게 동작하는가?](https://d2.naver.com/helloworld/831311)

## HashMap 주요 메소드

- void clear()
- boolean isEmpty()
- int size()
- boolean containsKey(Object Key)
- boolean containsValue(Object value)
- Set<Map.Entry<K, V>> entrySet()
- Set<K> keySet()
- Collection<V> values()
- V get(Object key)
- V put(K key, V value)
- V remove(Object key)
- V replace(K key, V value)
- void forEach(BiConsumer<? super K,? super V> action)
- V getOrDefault(Object key, V defaultValue)
- V putIfAbsent(K key, V value)
- V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) 
- V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction)



## HashMap Constructor

기본적으로 Data Type은 primitive data type은 불가능하다.

```java
// Java 8
HashMap<String, Integer> map8 = new HashMap<>();

// Java 11
var map11 = HashMap<String, Integer>();
```

HashMap은 데이터가 추가되면 저장공간을 약 두배로 늘린다. (이 과정에서 overhead가 크다.)
따라서 초기에 저장할 데이터 개수를 알고 있다면 초기 capacity를 지정해주는 것이 좋다.

```java
var map11 = new HashMap<String, Integer>(100, 0.8f);
```

여기서 100은 capacity이고, 0.8f는 load factor이다.
capacity의 0.8, 즉 80% 이상 해시 버킷이 차있다면, 해시 버킷의 크기를 두배로 확장시킨다.



## void clear()

- HashMap 안에 들어있던 기존에 요소들을 모두 지운다. 반환값은 없다.



## boolean isEmpty()

- HashMap 에 element가 있는지를 판단한다. 없다면 true를 있다면 false를 반환한다.



## boolean containsKey(Object Key)

- 인자로 주어진 Key가 현재 HashMap에 존재하는지를 판단하여 boolean값을 반환한다.



## boolean containsValue(Object value)

- 인자로 주어진 Value를 가진 Key가 현재 HashMap에 존재하는지를 판단하여 boolean값을 반환한다.



## Set<Map.Entry<K, V>> entrySet()

- HashMap의 모든 요소를 'Key=Value' 형태로 묶어 Set으로 반환한다.



## Set<K> keySet()

- HashMap의 모든 요소의 키만 'Key' 형태로 묶어 Set으로 반환한다.



## Collection<V> values()

- HashMap의 모든 요소의 값만 묶어 반환한다.

```java
var map = new HashMap<String,Integer>(){{//초기값 지정
  put("사과", 1);
  put("바나나", 2);
  put("포도", 3);
  put("사과", 4);
  put("복숭아", 5);
}};
// 중복된 Key가 put되면 나중에 put된 value로 바뀐다.

System.out.println("map = " + map);
System.out.println("map.entrySet() = " + map.entrySet());
System.out.println("map.keySet() = " + map.keySet());
System.out.println("map.values() = " + map.values());

/* 출력
  map = {복숭아=5, 포도=3, 사과=4, 바나나=2}
  map.entrySet() = [복숭아=5, 포도=3, 사과=4, 바나나=2]
  map.keySet() = [복숭아, 포도, 사과, 바나나]
  map.values() = [5, 3, 4, 2]
*/
```



## V get(Object key)

- 인자로 주어진 key와 매핑되는 value를 반환해준다.
- HashMap에 key가 없다면 null을 반환한다.

```java
System.out.println("map.get(\"사과\") = " + map.get("사과"));
System.out.println("map.get(\"감\") = " + map.get("감"));

/* 출력
  map.get("사과") = 4
  map.get("감") = null
*/
```



## V put(K key, V value)

- 인자로 주어진 key=value 쌍을 HashMap에 추가한다.
- 만약 이미 HashMap안에 key가 존재할 경우, 나중에 put된 value가 들어간다.

```java
var map = new HashMap<String,Integer>(){{//초기값 지정
  put("사과", 1);
  put("바나나", 2);
  put("포도", 3);
  put("사과", 4);
  put("복숭아", 5);
}};
// 중복된 Key가 put되면 나중에 put된 value로 바뀐다.

System.out.println("map = " + map);
// map = {복숭아=5, 포도=3, 사과=4, 바나나=2}
```



## V remove(Object key)

- HashMap에 주어진 key가 있으면 그 key=value 쌍을 제거하고 value를 반환한다.
- 주어진 key가 HashMap에 없다면 null을 반환한다.

> #### boolean remove(Object key, Object value)
>
> 주어진 key=value 쌍이 HashMap에 존재하면 삭제하고 true를, 존재하지 않는다면 false를 반환한다.

```java
System.out.println("map.remove(\"사과\") = " + map.remove("사과"));
System.out.println("map.remove(\"사과\") = " + map.remove("사과"));

/* 출력
  map.remove("사과") = 4
  map.remove("사과") = null
*/

System.out.println("map.remove(\"포도\", 10) = " + map.remove("포도", 10));
System.out.println("map.remove(\"포도\", 3) = " + map.remove("포도", 3));

/* 출력
  map.remove("포도", 10) = false
  map.remove("포도", 3) = true
*/
```



## V replace(K key, V value)

- 기존에 존재하던 HashMap의 key=old_value를 새로운 key=value로 바꾼다.
- replace에 성공하면 기존에 존재하던 old_value를 반환하고, key가 존재하지않아 replace에 실패하면 null을 반환한다.



## void forEach(BiConsumer<? super K,? super V> action)

- forEach를 사용하여 HashMap의 각 key=value 쌍에 접근할 수 있다.
- lambda식도 사용가능하므로 Iterator를 통한 순회보다 더욱 더 간단하게 코드를 짤 수 있다.
- keySet()이나 entrySet(), values()를 통하여 만들어진 Set들도 forEach문으로 접근이 가능하다.

```java
map.forEach((key, value) -> {
  System.out.print("key = " + key);
  System.out.println(", value = " + value);
});

/*	출력
  key = 복숭아, value = 5
  key = 포도, value = 3
  key = 사과, value = 4
  key = 바나나, value = 2
*/
```



## V getOrDefault(Object key, V defaultValue)

- 기존의 HashMap.get(K key) 메소드는 해당 key가 존재하지 않을 경우 null을 반환했다.
- 그러나 HashMap.getOrDefault() 메소드는 key가 존재할 경우 key에 매핑되는 value를 반환하고, key가 존재하지 않는다면 defaultValue를 반환한다.



## V putIfAbsent(K key, V value)

- putIfAbsent의 성공시 반환값은 put과 동일하다. (key가 존재하지 않아서 성공하면 null 반환) 그러나 put은 key가 이미 존재하면 새로운 value로 업데이트를 해버린다.
- putIfAbsent는 key가 기존에 없을 때만 put이 진행된다. 기존에 이미 key가 존재한다면, 그 key에 매핑되는 value를 반환하고, 새로운 value를 업데이트 하지 않는다.

```java
var map = new HashMap<String,Integer>(){{//초기값 지정
  put("사과", 1);
  put("바나나", 2);
  put("사과", 4);
}};

System.out.println("map.put(\"사과\", 10) = " + map.put("사과", 10));
System.out.println("map = " + map + "\n");
System.out.println("map.putIfAbsent(\"사과\", 50) = " + map.putIfAbsent("사과", 50));
System.out.println("map = " + map);

/* 출력
  map.put("사과", 10) = 4
  map = {사과=10, 바나나=2}

  map.putIfAbsent("사과", 50) = 10
  map = {사과=10, 바나나=2}
*/
```



## V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) 

- computeIfAbsent()는 HashMap에 파라미터로 전달된 key가 없으면, mappingFunction이 호출되는 구조이다. 만약 key가 있다면, mappingFunction은 호출되지 않는다.
- key가 없으면 값을 구하기 위하여 mappingFunction을 호출하여 가져온 후, key=value 값을 HashMap에 추가한다.



## V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction)

- computeIfPresent()는 HashMap에 파라미터로 전달된 key가 있으면, remappingFunction이 호출되는 구조이다. 만약 key가 없다면, remappingFunction은 호출되지 않는다.
- key가 있으면 값을 구하기 위하여 remappingFunction을 호출하여 가져온 후, key=value 값을 HashMap에 갱신한다.

```java
var map = new HashMap<String,Integer>(){{//초기값 지정
  put("사과", 1);
  put("바나나", 2);
  put("사과", 4);
}};

System.out.println("map = " + map);
map.computeIfAbsent("감", key -> 40);
System.out.println("map = " + map);
map.computeIfPresent("감", (key, value) -> value + 2000);
System.out.println("map = " + map);

/* 출력
  map = {사과=4, 바나나=2}
  map = {감=40, 사과=4, 바나나=2}
  map = {감=2040, 사과=4, 바나나=2}
*/
```



## putIfAbsent() vs computeIfAbsent() 차이점

`computeIfAbsent()` 는 만일 키가 없으면 값을 얻기 위하여 호출하는 매핑된 함수를 가지는 반면, `putIfAbsent()` 는 value를 바로 가진다. 

```java
var theKey = "Fish";        

// key가 존재하여도 callExpensiveMethodToFindValue()가 호출된다.
productPriceMap.putIfAbsent(theKey, callExpensiveMethodToFindValue(theKey)); 

// key가 존재한다면 callExpensiveMethodToFindValue()가 결코 호출되지 않는다. 
productPriceMap.computeIfAbsent(theKey, key -> callExpensiveMethodToFindValue(key));
```



## Refer to

- [Oracle Docs](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/HashMap.html#putAll(java.util.Map))

- [Naver D2 - Java HashMap은 어떻게 동작하는가?](https://d2.naver.com/helloworld/831311)