## Problem : https://programmers.co.kr/learn/courses/30/lessons/12941

## Approach

두 배열 A, B를 둘다 정렬 한 후, (A의 최솟값 ) * (B의 최댓값) 들을 곱하여 더하는 방법이 문제에서 말한 최솟값이 된다.

정확히 그런 것이 맞는지도 모르겠지만 왠지 그럴 것 같아 그렇게 풀이를 했는데 테스트를 통과하였다. 

## Code

```java
import java.util.Arrays;

public class MakingMinimumNumber {
    public static void main(String[] args) {
        int[] a = {1, 4, 2};
        int[] b = {5, 4, 4};

        MakingMinimumNumber mnn = new MakingMinimumNumber();
        mnn.solution(a, b);
    }

    public int solution(int[] a, int[] b) {
        Arrays.sort(a);
        Arrays.sort(b);

        int answer = 0;
        for (int i = 0; i < a.length; i++) {
            answer += a[i] * b[b.length - i - 1];
        }

        return answer;
    }
}

```

