## Problem : https://programmers.co.kr/learn/courses/30/lessons/12900

## Approach

가로의 길이를 1부터 하여 천천히 늘려가며 그림을 그려본다면 쉽게 규칙을 발견할 수 있다.

결론부터 말하면 DP[n]을 가로길이 n인 바닥을 타일로 채우는 방법의 수라고 할 때, 
`DP[i] = DP[i - 1] + DP[i - 2]` 이다.

i길이의 바닥에 타일의 놓는 방법의 수는 i - 1길이의 바닥에서 세로타일을 놓는 방법의 수와 i - 2길이의 바닥에서 가로타일을 놓는 방법의 수의 합과 같기 때문이다.

나머지 계산만 추가하여 문제를 풀면 간단하게 풀리는 문제이다.

## Code

```java
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Tiling2xN {
    public int solution(int n) {
        int[] dp = new int[n];
        dp[0] = 1;
        dp[1] = 2;
        for (int i = 2; i < n; i++) {
            dp[i] = (dp[i - 2] + dp[i - 1]) % 1000000007;
        }
        return dp[n - 1];
    }

    @Test
    public void test() {
        Assertions.assertEquals(5, solution(4));
    }
}
```

