## Problem : https://programmers.co.kr/learn/courses/30/lessons/12907

## Approach

> 백준 온라인 저지에서도 DP 카테고리의 문제를 풀어봤다면, 비슷한 문제를 보았을 것이다.
>
> 꽤나 유명한 DP문제이고, 아마 나는 KnapSack 문제로 풀었던 것 같다.
>
> 하지만 여기서는 KnapSack처럼 2차원배열을 가지고 풀면 효율성테스트에서 `시간초과`가 발생하므로, 1차원배열로 풀이하여야 한다.

DP[i] 를 i 원을 만드는 경우의 수라고 하였을 때, `DP[i] = DP[i - money[j]]` 이다.

돈의 종류 j 개 만큼 반복문을 돌리면 된다.

## Code

```java
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Change {
    public int solution(int n, int[] money) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        for (int i = 0; i <= n; i++) {
            dp[i] = (i % money[0] == 0) ? 1 : 0;
        }

        for (int i = 1; i < money.length; i++) {
            for (int j = money[i]; j <= n; j++) {
                dp[j] = (dp[j] + dp[j - money[i]]) % 1000000007;
            }
        }
        return dp[n];
    }

    @Test
    public void test(){
        Assertions.assertEquals(4, solution(5, new int[]{1, 2, 5}));
    }
}

```

