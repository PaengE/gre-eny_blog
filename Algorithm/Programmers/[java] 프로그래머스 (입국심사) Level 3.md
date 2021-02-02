## Problem : https://programmers.co.kr/learn/courses/30/lessons/43238

## Approach

> 이분탐색(이진탐색)을 이용하는 문제였다. 주의할 점은 모든 자료형을 long으로 해줘야 `런타임에러`가 발생하지 않는다.

일단 이진탐색을 가능하게 하기 위해 정렬을 한다.

그리고 시간을 이분하면서 주어진 시간으로 몇명의 사람을 처리할 수 있는지를 체크한다.

주어진 사람 수 n미만 이면 시간이 더 필요하므로 반으로 나눠진 시간의 오른쪽+1부터 다시 탐색한다.

주어진 사람 수 n이상 이면 시간을 더 줄여도 되므로 반으로 나눠진 시간의 왼쪽을 다시 탐색한다. n과 같더라도 탐색을 계속 진행하는 이유는, n명을 처리할 수 있는 시간 중 가장 짧은 시간을 구하기 위해서이다.



테스트 코드의 예제를 보면, 29초라도 6명을 처리할 수 있고, 28초여도 6명을 처리할 수 있다. 문제에서 요구하는 답은 6명을 처리하는 시간 중 최소값이기에, start와 end가 같아질 때까지 (터미널 노드가 될 때까지) 탐색을 계속한다.

## Code

```java
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class Immigration {
    static long ans;
    public long solution(int n, int[] times) {
        Arrays.sort(times);

        long start = 1;
        long end = (long) times[times.length - 1] * n;
        ans = end;

        binarySearch(start, end, n, times);
        return ans;
    }

    private void binarySearch(long start, long end, int n, int[] times) {
        long mid = (start + end) / 2;
        if (start >= end) {
            ans = Math.min(ans, mid);
            return;
        }

        long countedN = countFinishedPeople(mid, times);

        if (countedN < n) {
            binarySearch(mid + 1, end, n, times);
        } else {
            ans = Math.min(ans, mid);
            binarySearch(start, mid, n, times);
        }
    }

    private long countFinishedPeople(long time, int[] times) {
        long total = 0;
        for (long t : times) {
            total += time / t;
        }
        return total;
    }

    @Test
    public void test(){
        Assertions.assertEquals(28, solution(6, new int[]{7, 10}));
    }
}

```

