## Problem : https://programmers.co.kr/learn/courses/30/lessons/43105

## Approach

> 대표적인 DP 문제이다.

문제에서 보이기는 정삼각형이지만, 배열로 구현하게 되면 왼쪽 하단이 직각을 이루는 직각삼각형이 된다.

따라서 점화식도 간단하게 유추가 가능하다.

점화식은 DP[i][j] = max(DP[i - 1][j - 1], DP[i - 1][j]) + triangle[i][j] 이다.

주의할 점은 삼각형의 변에 해당하는 경우에는 오는 경로가 하나 뿐이므로 예외적으로 처리해 주어야한다.

삼각형의 가장 밑에 있는 배열을 검사하여 그 중 최댓값이 문제의 답이 된다.

## Code

```java
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class IntegerTriangle {
    public int solution(int[][] triangle) {
        int size = triangle.length;
        int[][] dp = new int[size][size];
        dp[0][0] = triangle[0][0];

        for (int i = 1; i < size; i++) {
            for (int j = 0; j < triangle[i].length; j++) {
                if (j == 0) {
                    dp[i][j] = dp[i - 1][j] + triangle[i][j];
                } else if (i == j) {
                    dp[i][j] = dp[i - 1][j - 1] + triangle[i][j];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - 1]) + triangle[i][j];
                }
            }
        }

        Arrays.sort(dp[size - 1]);
        return dp[size - 1][size - 1];
    }

    @Test
    public void test() {
        Assertions.assertEquals(30, solution(new int[][]{{7}, {3, 8}, {8, 1, 0}, {2, 7, 4, 4}, {4, 5, 2, 6, 5}}));
    }
}

```

