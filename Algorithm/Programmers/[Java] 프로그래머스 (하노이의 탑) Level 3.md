## Problem : https://programmers.co.kr/learn/courses/30/lessons/12946

## Approach

> 많이 알려져 있는 재귀문제, `하노이의 탑` 오리지널 문제와 크게 다르지 않은 문제이다.

하노이의 탑의 주요 알고리즘은 다음과 같다. n개의 원판을 1에서 3으로 옮길 때,

1. 출발지(1)에 있는 n - 1개 원판을 경유지(2)로 옮긴다.
2. 출발지(1)에 있는 1개 원판을 도착지(3)로 옮긴다. -> 여기서는 정답 배열에 추가한다.
3. 경유지(2)에 있는 n - 1개 원판을 출발지(1)로 옮긴다.

간단히 재귀로 처리할 수 있다.

## Code

```java
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TowerOfHanoi {
    static int[][] ans;
    static int idx;
    public int[][] solution(int n) {
        ans = new int[(1 << n) - 1][2];
        idx = 0;
        hanoi(n, 1, 2, 3);

        return ans;
    }

    private void hanoi(int n, int from, int via, int to) {
        int[] move = {from, to};

        if (n == 1) {
            ans[idx++] = move.clone();
        } else {
            // 출발지에서 경유지로 n - 1개 원판 이동
            hanoi(n - 1, from, to, via);
            ans[idx++] = move.clone();
            // 경유지에서 도착지로 n - 1개 원판 이동
            hanoi(n - 1, via, from, to);
        }
    }

    @Test
    public void test() {
        Assertions.assertArrayEquals(new int[][]{{1, 2}, {1, 3}, {2, 3}}, solution(2));
    }
}

```

