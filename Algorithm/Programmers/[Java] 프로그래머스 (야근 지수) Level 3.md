## Problem : https://programmers.co.kr/learn/courses/30/lessons/12927

## Approach

> 우선순위 큐(PriorityQueue)를 이용하여 문제를 풀었다.
>
> 자바의 PriorityQueue 는 Heap으로 내부동작이 이루어져서 시간복잡도가 O(NlogN)이다.
> N이 최대 1,000,000 이라 시간초과가 나지 않을까도 싶었지만 다행이도 시간초과는 나지 않았다.

우선순위 큐를 큰 숫자가 우선이 되게끔 Collections.reverseOrder() 를 사용했고,

큐에서 하나씩 빼서 -1을 하고 다시 큐에 넣는 작업을 n번 수행했다.

그리고 난 후, 큐에 있는 모든 요소들의 제곱한 값을 더해 반환했다.

## Code

```java
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.PriorityQueue;

public class OvertimeScore {
    public long solution(int n, int[] works) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        for (int i = 0; i < works.length; i++) {
            pq.offer(works[i]);
        }

        while (n-- > 0) {
            int t = pq.poll();
            if (t == 0) {
                return 0;
            }
            pq.offer(--t);
        }

        // 제곱 합 계산
        long ans = pq.stream().mapToLong(i -> (long) Math.pow(i, 2)).sum();
        return ans;
    }

    @Test
    public void test() {
        Assertions.assertEquals(12, solution(4, new int[]{4, 3, 3}));
        Assertions.assertEquals(6, solution(1, new int[]{2, 1, 2}));
        Assertions.assertEquals(0, solution(3, new int[]{1, 1}));
        Assertions.assertEquals(580, solution(99, new int[]{2, 15, 22, 55, 55}));
    }
}

```

