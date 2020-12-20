## 자바 int[] 배열을 Integer[] 배열로 변환

```java
int a[] = {1,2,3,4};

Integer b[] = Arrays.stream(a).boxed().toArray(Integer[]::new); 
```



## 자바 Integer[] 배열을 int[] 배열로 변환

```java
Integer a[] = {1,2,3,4};

int b[] = Arrays.stream(a).mapToInt(Integer::intValue).toArray();

or
  
int b[] = Arrays.stream(a).mapToInt(i->i).toArray();
```



## Refer to

- ### http://www.techiedelight.com/convert-int-array-list-integer/