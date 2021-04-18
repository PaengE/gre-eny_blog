## Problem : https://programmers.co.kr/learn/courses/30/lessons/1832

## Approach

> 2017 카카오코드 예선 문제이다. DP를 이용하되, 두개의 DP를 유지하여야 하는 문제이다.

어느 한 지점 (x, y)에 대하여 그 점에 세로방향 길로 도착했는지, 가로방향 길로 도착했는지, 두개의 DP배열을 유지해야한다.

왜냐하면, 현재 위치 (x, y)에 회전금지 표지판이 있으면, 직진으로만 다음 위치로 갈 수 있기 때문이다.



따라서, 일반적으로 DP를 수행하되, 세로방향 길인지, 가로방향 길인지에 따라 다르게 처리를 해준 후,
목적지 (ex, ey) 를 세로방향 길로 도착할 수 있는 경우의 수와 가로방향 길로 도착할 수 있는 경우의 수를 더한다음 문제에서 요구한것처럼 MOD 연산을 취해주면된다.

## Code

```java
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PedestrianParadise {
    int MOD = 20170805;
    public int solution(int m, int n, int[][] city_map) {
        // 1 x 1 이면 1 반환
        if (m == 1 && n == 1) {
            return 1;
        }

        int[][][] dp = new int[m][n][2];    // 0: 세로방향, 1: 가로방향

        // 첫번째 열 초기화
        for (int i = 1; i < m; i++) {
            if (city_map[i][0] == 1) {
                break;
            }
            dp[i][0][0] = 1;
        }
        // 첫번째 행 초기화
        for (int i = 1; i < n; i++) {
            if (city_map[0][i] == 1) {
                break;
            }
            dp[0][i][1] = 1;
        }


        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n ; j++) {
                if (city_map[i][j] == 1) {
                    continue;
                }

                // 세로방향 도로 검사
                if (city_map[i - 1][j] == 0) {
                    dp[i][j][0] = (dp[i][j][0] + dp[i - 1][j][0] + dp[i - 1][j][1]) % MOD;
                } else if (city_map[i - 1][j] == 1) {
                    // nothing to do
                } else {
                    dp[i][j][0] = (dp[i][j][0] + dp[i - 1][j][0]) % MOD;
                }

                // 가로방향 도로 검사
                if (city_map[i][j - 1] == 0) {
                    dp[i][j][1] = (dp[i][j][1] + dp[i][j - 1][0] + dp[i][j - 1][1]) % MOD;
                } else if (city_map[i][j - 1] == 1) {
                    // nothing to do
                } else {
                    dp[i][j][1] = (dp[i][j][1] + dp[i][j - 1][1]) % MOD;
                }
            }
        }

        return (dp[m - 1][n - 1][0] + dp[m - 1][n - 1][1]) % MOD;
    }

    @Test
    public void test() {
        Assertions.assertEquals(6, solution(3, 3, new int[][]{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}));
        Assertions.assertEquals(2, solution(3, 6, new int[][]{{0, 2, 0, 0, 0, 2}, {0, 0, 2, 0, 1, 0}, {1, 0, 0, 2, 2, 0}}));
    }
}

```

