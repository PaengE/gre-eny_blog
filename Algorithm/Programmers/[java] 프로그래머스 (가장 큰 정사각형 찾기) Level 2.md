## Problem : https://programmers.co.kr/learn/courses/30/lessons/12905

## Approach

이 문제는 [백준 1915번 가장 큰 정사각형](https://www.acmicpc.net/problem/1915) 문제와 유사하다.

아래는 내 풀이에 대한 포스팅이다.

[2021/01/13 - [Algorithm/Baekjoon Online Judge\] - [java] 백준 1915 (가장 큰 정사각형) Gold 5](https://gre-eny.tistory.com/103)

DP로 풀 수 있는 문제로, DP[i][j] 는 (i, j) 위치에서 좌상으로 진행했을 때 만들수 있는 가장 큰 정사각형의 한 변의 길이이다.

따라서 주어진 board[i][j] 가 1이라면 일단 최소 1x1 정사각형을 만들 수 있으므로 dp[i][j] = 1을 저장한다.

그리고 board[i][j - 1], board[i - 1][j], board[i - 1][j - 1]이 모두 1이라면 DP[i][j]에 min(DP[i][j - 1], DP[i - 1][j], DP[i - 1][j - 1]) + 1을 저장한다.

탐색을 진행하면서 가장 큰 한 변의 길이 max 를 저장 한 후, 결과로 정사각형의 넓이(max * max)를 반환하면 된다.

## Code

```java
public class TheBiggestSquare {
    public static void main(String[] args) {
        int[][] board = {{0, 1, 1, 1},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {0, 0, 1, 0}};

//        int[][] board = {{0, 0, 1, 1}, {1, 1, 1, 1}};

        TheBiggestSquare tbs = new TheBiggestSquare();
        System.out.println(tbs.solution(board));

    }

    public int solution(int[][] board) {
        int row = board.length;
        int col = board[0].length;
        int max = 0;
        int[][] dp = new int[row][col];

        for (int i = 0; i < row; i++) {
            if (board[i][0] == 1) {
                dp[i][0] = 1;
                max = 1;
            }
        }
        for (int i = 0; i < col; i++) {
            if (board[0][i] == 1) {
                dp[0][i] = 1;
                max = 1;
            }
        }

        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (board[i][j] == 1) {
                    dp[i][j] = 1;
                    if (board[i - 1][j - 1] == 1 && board[i - 1][j] == 1 && board[i][j - 1] == 1) {
                        dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                    }
                    max = Math.max(max, dp[i][j]);
                }
            }
        }
        return (max * max);
    }
}
```

