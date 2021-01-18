## Problem : https://programmers.co.kr/learn/courses/30/lessons/12913

## Approach

대표적인 DP문제이다. 다음 위치의 땅으로 가는 경우의 수는 바로 위 땅을 제외한 3곳에서 오는 방법이 있다.

따라서 dp[i][j] = Math.max(dp[i - 1][(j + 1) % 4], dp[i - 1][(j + 2) % 4], dp[i - 1][(j + 3) % 4]) + land[i][j] 이다.

위의 점화식을 도출할 수 있다면 쉽게 문제가 풀릴 것이다.

## Code

```java
public class Hopscotch {
    public static void main(String[] args) {
        int[][] land = {{1, 2, 3, 5}, {5, 6, 7, 8}, {4, 3, 2, 1}};
        Hopscotch h = new Hopscotch();
        System.out.println(h.solution(land));
    }

    public int solution(int[][] land) {
        int size = land.length;
        int[][] dp = new int[size][4];

        for (int i = 0; i < size; i++) {
            if (i != 0) {
                dp[i][0] += Math.max(dp[i - 1][1], Math.max(dp[i - 1][2], dp[i - 1][3])) + land[i][0];
                dp[i][1] += Math.max(dp[i - 1][2], Math.max(dp[i - 1][3], dp[i - 1][0])) + land[i][1];
                dp[i][2] += Math.max(dp[i - 1][3], Math.max(dp[i - 1][0], dp[i - 1][1])) + land[i][2];
                dp[i][3] += Math.max(dp[i - 1][0], Math.max(dp[i - 1][1], dp[i - 1][2])) + land[i][3];
            } else {
                dp[i][0] = land[i][0];
                dp[i][1] = land[i][1];
                dp[i][2] = land[i][2];
                dp[i][3] = land[i][3];
            }
        }
        return Math.max(Math.max(Math.max(dp[size - 1][0], dp[size - 1][1]), dp[size - 1][2]), dp[size - 1][3]);
    }
}

```

