## Problem : https://www.acmicpc.net/problem/17069



## Approach

> `DP` 로 문제를 풀이했다.

[백준 17070 (파이프 옮기기 1)](https://gre-eny.tistory.com/13)와 똑같은 문제지만, 문제의 입력 부분에 차이가 있다.



먼저 `DP[i][j][k]` 를 (i, j) 위치에 k 방향으로 파이프가 도착할 수 있는 방법의 개수로 정의하겠다.

k의 값이 각각 0일 땐 가로방향, 1일 땐 세로방향, 2일 땐 대각선 방향으로 정의한다.

그러면 아래의 점화식이 성립이 된다. 문제에서 나온 파이프를 놓는 7가지 방법에 대해 모두 정리가 된다.

- dp[i][j][0] = dp[i][j - 1][0] + dp[i][j - 1][2]
- dp[i][j][1] = dp[i - 1][j][1] + dp[i - 1][j][2]
- dp[i][j][2] = dp[i - 1][j - 1][0] + dp[i - 1][j - 1][1] + dp[i - 1][j - 1][2]

이제 파이프를 놓을 수 있는지를 체크하며, dp 배열을 구성해 나가면 될 것이다.

최종적으로 답은 dp[n-1][n-1][0] + dp[n-1][n-1][1] + dp[n-1][n-1][2] 이 된다.



## Code

```java
import java.io.*;
import java.util.StringTokenizer;

/**
 *  No.17069: 파이프 옮기기 2
 *  Hint: DP + 자료형
 */

public class BOJ17069 {
    static int n;
    static long[][][] dp;
    static int[][] arr;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        n = Integer.parseInt(br.readLine());
        arr = new int[n][n];
        dp = new long[n][n][3];   // 0:가로, 1:세로, 2:대각선

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dp[0][1][0] = 1;

        bw.write(String.valueOf(dp()));
        bw.close();
        br.close();
    }

    static long dp() {
        for (int i = 0; i < n; i++) {
            for (int j = 2; j < n; j++) {
                if (arr[i][j] == 1) {   // 막혀 있으면 continue
                    continue;
                }

                dp[i][j][0] = dp[i][j - 1][0] + dp[i][j - 1][2];    // 가로방향

                if (i == 0) {   // 맨 윗줄이면 continue
                    continue;
                }

                dp[i][j][1] = dp[i - 1][j][1] + dp[i - 1][j][2];    // 세로방향

                if (arr[i - 1][j] == 1 || arr[i][j - 1] == 1) {
                    continue;
                }

                dp[i][j][2] = dp[i - 1][j - 1][0] + dp[i - 1][j - 1][1] + dp[i - 1][j - 1][2];  // 대각선 방향
            }
        }

        return dp[n - 1][n - 1][0] + dp[n - 1][n - 1][1] + dp[n - 1][n - 1][2];
    }
}
```

