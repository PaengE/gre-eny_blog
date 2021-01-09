## Problem : https://www.acmicpc.net/problem/1915

## Approach

DP로 풀 수 있는 문제로, DP[i][j] 는 (i, j) 위치에서 좌상으로 진행했을 때 만들수 있는 가장 큰 정사각형의 한 변의 길이이다.

따라서 주어진 board[i][j] 가 1이라면 일단 최소 1x1 정사각형을 만들 수 있으므로 dp[i][j] = 1을 저장한다.

그리고 board[i][j - 1], board[i - 1][j], board[i - 1][j - 1]이 모두 1이라면 DP[i][j]에 min(DP[i][j - 1], DP[i - 1][j], DP[i - 1][j - 1]) + 1을 저장한다.

즉, DP[i][j - 1], DP[i - 1][j], DP[i - 1][j - 1]가 모두 같아야 더 큰 정사각형을 만들 수 있다.

탐색을 진행하면서 가장 큰 한 변의 길이 max 를 저장 한 후, 결과로 정사각형의 넓이(max * max)를 출력해주면 된다.

## Code

```java
import java.io.*;
import java.util.StringTokenizer;

/**
 *  No.1915: 가장 큰 정사각형
 *  URL: https://www.acmicpc.net/problem/1915
 *  Hint: DP
 */

public class BOJ1915 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] arr = new int[n][m];
        int[][] dp = new int[n][m];

        int max = 0;
        for (int i = 0; i < n; i++) {
            String[] s = br.readLine().split("");
            for (int j = 0; j < m; j++) {
                arr[i][j] = Integer.parseInt(s[j]);
                if (arr[i][j] == 1) {
                    dp[i][j] = 1;
                    max = 1;
                }
            }
        }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                if (arr[i - 1][j - 1] == 1 && arr[i - 1][j] == 1 && arr[i][j - 1] == 1) {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                    max = Math.max(max, dp[i][j]);
                }
            }
        }

//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                System.out.print(dp[i][j] + " ");
//            }
//            System.out.println();
//        }

        bw.write(String.valueOf(max * max));
        bw.flush();
        br.close();
        bw.close();
    }
}

```

