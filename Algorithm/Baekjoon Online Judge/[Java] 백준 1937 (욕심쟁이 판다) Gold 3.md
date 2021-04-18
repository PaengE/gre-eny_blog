## Problem : https://www.acmicpc.net/problem/1937

## Approach

> 어느 한 지점으로부터 시작하여 최대 몇개의 지점을 방문할 수 있는지를 판단하는 `DFS`문제이다.
>
> DFS는 인접행렬의 경우 O(n^2)의 시간복잡도를 가지고 있어, n의 크기가 크다면 모든 지점을 시작지점으로 하여 DFS하기에는 시간초과가 뜰 것이다. (O(n^2 * n^2))

따라서 위와 같이 시간초과를 피하기 위하여, 모든 점을 시작지점으로 DFS를 수행하되, 이미 계산된 값이 있으면 그 값을 이용하는 DP를 같이 활용해보자.

헷갈릴 수 있겠지만 dp 메소드는 DFS이고, dp 배열은 말그대로 dp 배열이다.

DP[i][j] 는 (i, j) 를 시작점으로 하였을 때 판다가 살 수 있는 일수를 저장해놓았다.

그래서 DFS를 수행하던 중 DP배열의 값이 0이 아니라면, 그냥 바로 그 값을 쓰면 된다. 
(그 지점에서 DFS를 수행한 값이 DP배열에 저장되어 있기 때문이다.)

## Code

```java
import java.io.*;
import java.util.StringTokenizer;

/**
 *  No.1937: 욕심쟁이 판다
 *  URL: https://www.acmicpc.net/problem/1937
 *  Hint: DFS + DP
 */

public class BOJ1937 {
    static int[] dx = {0, 0, -1, 1};
    static int[] dy = {-1, 1, 0, 0};
    static int n, result;
    static int[][] arr, dp;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        n = Integer.parseInt(br.readLine());
        arr = new int[n][n];
        dp = new int[n][n];
        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result = Math.max(result, dp(i, j));
            }
        }

//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                System.out.print(dp[i][j] + " ");
//            }
//            System.out.println();
//        }

        bw.write(String.valueOf(result));
        bw.flush();
        bw.close();
        br.close();
    }

    static int dp(int x, int y) {
        if (dp[x][y] != 0) {
            return dp[x][y];
        }

        int day = 1;
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (nx >= 0 && nx < n && ny >= 0 && ny < n) {
                if (arr[x][y] < arr[nx][ny]) {
                    day = Math.max(dp(nx, ny) + 1, day);
                    dp[x][y] = day;
                }
            }
        }
        return day;

    }
}
```

