## 1차원 배열 정렬 방법

- 오름차순으로 정렬이 된다.

```java
import java.util.Arrays;

Arrays.sort(arr);
```

## 2차원 배열 정렬 방법

> 2차원 배열을 정렬하기 위해서는 Comparator를 Override하여 정렬 기준을 제시하면 된다.

- 예를 들어, arr이 { {2, 6}, {1, 5}, {1, 3} } 일 때 밑의 코드를 사용하면, { {1, 5}, {1, 3}, {2, 6} }이 된다
- o1[0]은 각 부분 배열의 0번째 element를 가리키며 그것들을 기준으로 compare한다는 소리이다.
- 비교 기준이 같으면 입력의 순서대로 저장된다.
- 오름차순으로 정렬이 된다.

```java
Arrays.sort(arr, Comparator.comparingInt(o1 -> o1[0]));
```

- 위의 배열에서 두 번째 요소도 고려하여 정렬을 하고 싶으면 밑의 코드를 사용하면 된다.
- 위의 배열을 밑의 방법으로 정렬하면 { {1, 3}, {1, 5}, {2, 6} }이 된다.
- 0번째 element가 같으면 1번째 element로 compare하고, 같지 않으면 0번째 element로 compare한다는 소리이다.
- 오름차순으로 정렬이 된다.

```java
Arrays.sort(arr, (o1, o2) -> {
    if(o1[0] == o2[0]){
    	return Integer.compare(o1[1], o2[1]);
    } else {
    	return Integer.compare(o1[0], o2[0]);
    }
});
```