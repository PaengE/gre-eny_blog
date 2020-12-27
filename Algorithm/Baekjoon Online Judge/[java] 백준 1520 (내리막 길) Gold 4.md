## Problem : https://www.acmicpc.net/problem/1520

## Approach

최단거리 문제가 아니므로 미로문제 처럼 갈 수 있는 모든 곳을 방문해 봐야 한다.

DP[i][j] = (i, j) 위치에서 최종 목적지까지 갈 수 있는 방법의 수라고 정의한다.

DP[i][j] == 0 은 방문했음을 표시한 것이고, -1은 도달할 수 없는 곳이다.

현재 위치를 방문 했음을 표시하고, 상하좌우로 갈 수있는지 검사하여 갈 수 있다면 재귀를 돌리면서 리턴 값을 DP[i][j]에 더해준다. 목적지에 도착했으면 1을, 더이상 나아갈 수 없으면 0을 반환한다.

## Code

```java
import java.io.*;
import java.util.StringTokenizer;

/**
 * no.1520: 내리막 길
 * description: 내리막 길로만 이동하는 경우의 수를 구하는 문제
 * hint: 방문하지 않았으면 방문하여 상하좌우 이동 가능성을 검사
 */
public class BOJ1520 {
    static int[][] way;
    static int[][] dp;
    static int m, n;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        m = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());
        way = new int[m][n];
        dp = new int[m][n];

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < n; j++) {
                way[i][j] = Integer.parseInt(st.nextToken());
                dp[i][j] = -1;
            }
        }

        bw.write(String.valueOf(solution(0, 0)));
        bw.flush();
        br.close();
        bw.close();
    }

    static int solution(int x, int y) {
        // destination
        if (x == m - 1 && y == n - 1) {
            return 1;
        }
        // 현재 위치가 방문된 적이 없으면
        if (dp[x][y] == -1){
            // 방문 표시
            dp[x][y] = 0;
            // move up
            if (x > 0 && way[x][y] > way[x - 1][y]) {
                dp[x][y] += solution(x - 1, y);
            }
            // move down
            if (x < m - 1 && way[x][y] > way[x + 1][y]) {
                dp[x][y] += solution(x + 1, y);
            }
            // move left
            if (y > 0 && way[x][y] > way[x][y - 1]) {
                dp[x][y] += solution(x, y - 1);
            }
            // move right
            if (y < n - 1 && way[x][y] > way[x][y + 1]) {
                dp[x][y] += solution(x, y + 1);
            }
        }

        // 현재 위치까지 내리막 길로 올 수 있는 경우의 수
        return dp[x][y];
    }
}

```

