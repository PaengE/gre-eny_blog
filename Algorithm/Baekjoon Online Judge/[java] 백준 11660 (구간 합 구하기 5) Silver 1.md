## 문제 원문 링크 : https://www.acmicpc.net/problem/11660

## Approach

DP를 활용하여 풀 수 있는 문제이다.

DP[i][j] = (0, 0)부터 (i, j) 까지의 직사각형을 이루는 요소들의 합이다. 이 때, (0, 0)과 (i, j)는 직사각형의 좌상, 우하의 꼭짓점이다.

위의 DP배열을 이용하여, 좌상 꼭짓점이 (x1, y1)이고, 우하 꼭짓점이 (x2, y2)일 때, 구간 합은 다음 식과 같이 구할 수 있다.

> answer = DP[x2][y2] - DP[x1 - 1][y2] - DP[x2][y2 - 1] + DP[x1 - 1][y1 - 1] 이다.

그림을 보면 이해가 더 빠를 것이다.

<img src="C:\Users\82102\OneDrive\티스토리\Algorithm\Baekjoon Online Judge\image\11660-1.jpg" alt="11660-1" style="zoom: 25%;" />

위 그림에서 빨간색 영역의 넓이는 전체 사각형 넓이 - 파란색 영역 넓이 - 노란색 영역 넓이 + 초록색 영역 넓이이다.

## Code

```java
import java.io.*;
import java.util.StringTokenizer;

/**
 *  No.11660: 구간 합 구하기 5
 *  Hint: DP
 */

public class BOJ11660 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] arr = new int[n + 1][n + 1];

        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= n; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        int[][] dp = new int[n + 1][n + 1];

      // 처음 DP 배열 초기화
        dp[1][1] = arr[1][1];
        for (int i = 1; i <= n; i++) {
            for (int j = 2; j <= n; j++) {
                if (i == 1) {
                    dp[i][j] = dp[i][j - 1] + arr[i][j];
                    dp[j][i] = dp[j - 1][i] + arr[j][i];
                } else {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1] - dp[i - 1][j - 1] + arr[i][j];
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int x1 = Integer.parseInt(st.nextToken());
            int y1 = Integer.parseInt(st.nextToken());
            int x2 = Integer.parseInt(st.nextToken());
            int y2 = Integer.parseInt(st.nextToken());

            int ans = dp[x2][y2] - dp[x2][y1 - 1] - dp[x1 - 1][y2] + dp[x1 - 1][y1 - 1];

            sb.append(ans + "\n");
        }
        bw.write(sb.toString());
        bw.close();
        br.close();
    }
}

```

