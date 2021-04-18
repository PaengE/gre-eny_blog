## Problem : https://programmers.co.kr/learn/courses/30/lessons/12914

## Approach

> 매우 간단한 DP 문제이다.

DP[i] 를 i 칸에 도달할 수 있는 방법의 수라고 하였을 때,

i 칸에 도달할 수 있는 방법은 (i - 2 칸에서 2칸으로 움직였을 경우) + (i - 1 칸에서 1칸으로 움직였을 경우)이다.

위의 문자대로 `DP[i] = DP[i - 2] + DP[i - 1]` 이 된다.

## Code

```java
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TheBroadJump {
    public long solution(int n) {
        if (n <= 2) {
            return n;
        }

        long[] dp = new long[n + 1];
        dp[1] = 1;
        dp[2] = 2;

        for (int i = 3; i <= n; i++) {
            dp[i] = (dp[i - 1] + dp[i - 2]) % 1234567;
        }

        return dp[n];
    }
    @Test
    public void test() {
        Assertions.assertEquals(5, solution(4));
        Assertions.assertEquals(3, solution(3));
    }
}

```

