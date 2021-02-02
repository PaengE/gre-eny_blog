## Problem : https://programmers.co.kr/learn/courses/30/lessons/42898

## Approach

> 간단한 DP문제이다.
>
> 주의할 점은 문제에서 주어진 예시그림과 주어진 데이터가 불일치한다는 점을 알고 가야한다.
>
> 문제의 그림에서는 m = 4, n = 3이지만, 3 x 4 지도가 나와있어 n이 행의 개수,  m이 열의 개수라고 생각할 수 있겠지만, 그렇게 푼다면 `런타임에러`가 계속 뜰 것이다.
>
> `m을 행의 개수, n을 열의 개수` 라고 생각하고 `m x n 지도`라고 생각하고 문제를 풀어야 한다.

시작하기 전에, puddles에 있는 좌표를 -1로 초기화한다.

그런 후 DP배열을 처음부터 탐색해 나간다.

dp[i][j] == -1이면 dp[i][j] 를 0으로 갱신하고, -1이 아니라면 (dp[i - 1][j] + dp[i][j - 1]) % MOD을 계산하여 저장해준다.

그리고 최종 목적지인 dp[m][n] 을 반환해준다.

## Code

```java
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WayToSchool {
    static final int MOD = 1000000007;
    public int solution(int m, int n, int[][] puddles) {
        int[][] dp = new int[m + 1][n + 1];
        for (int[] t : puddles) {
            dp[t[0]][t[1]] = -1;
        }

        dp[0][1] = 1;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (dp[i][j] == -1) {
                    dp[i][j] = 0;
                } else {
                    dp[i][j] = (dp[i - 1][j] + dp[i][j - 1]) % MOD;
                }
            }
        }
        return dp[m][n];
    }

    @Test
    public void test() {
        Assertions.assertEquals(4, solution(4, 3, new int[][]{{2, 2}}));
        Assertions.assertEquals(690285631, solution(100, 100, new int[][]{}));
    }
}

```

