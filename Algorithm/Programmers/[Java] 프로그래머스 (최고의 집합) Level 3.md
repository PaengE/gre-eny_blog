## Problem : https://programmers.co.kr/learn/courses/30/lessons/12938

## Approach

> 약간 Greedy한 성격의 문제였다.
>
> 일단, 합이 s인 n개의 숫자 곱이 크려면 n개의 숫자가 s / n 과 가까운 숫자여야 한다.

s 를 n으로 나눈 나머지를 rest, 몫을 value 라고 할 때,

곱했을 때 최대가 되려면 rest개 만큼 value + 1인 숫자가 존재하고, 나머지 개수를 value가 차지하면 된다.



예를 들어, s가 11, n = 3이면, value = 3, rest = 2가 된다. 그럼 4, 4, 3이 곱했을때 최대가 된다.

## Code

```java
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TheBestSet {
    public int[] solution(int n, int s) {
        if (s < n) {
            return new int[]{-1};
        }

        int[] ans = new int[n];
        int value = s / n;
        int rest = s % n;

        for (int i = 0; i < n - rest; i++) {
            ans[i] = value;
        }
        for (int i = n - rest; i < n; i++) {
            ans[i] = value + 1;
        }

        return ans;
    }

    @Test
    public void test() {
        Assertions.assertArrayEquals(new int[]{4, 5}, solution(2, 9));
        Assertions.assertArrayEquals(new int[]{-1}, solution(2, 1));
        Assertions.assertArrayEquals(new int[]{4, 4}, solution(2, 8));
        Assertions.assertArrayEquals(new int[]{1}, solution(1, 1));
        Assertions.assertArrayEquals(new int[]{5}, solution(1, 5));
        Assertions.assertArrayEquals(new int[]{3,3,3}, solution(3, 9));
        Assertions.assertArrayEquals(new int[]{3,3,4}, solution(3, 10));
        Assertions.assertArrayEquals(new int[]{3,4,4}, solution(3, 11));
        Assertions.assertArrayEquals(new int[]{4,4,4}, solution(3, 12));
    }
}

```

