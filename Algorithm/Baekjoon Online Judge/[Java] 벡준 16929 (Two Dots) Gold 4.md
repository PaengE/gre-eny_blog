## Problem : https://www.acmicpc.net/problem/16929

## Approach

> 행렬 정보가 주어졌을 때, `사이클`이 존재하는지를 판단하는 문제였다.
>
> 사이클을 판별하는 방법은 여러가지가 있지만, 여기서는 `DFS`를 이용하였다.

모든 정점을 방문할 수 있도록, 방문하지 않은 모든 곳에서 DFS를 수행한다.

그 중에 사이클이 있다면, 더이상 진행할 필요가 없으므로 종료시켰다.

사이클을 판별하는 로직은 다음과 같다.

- 임의의 시작점에서 DFS를 수행한다.(시작점 직전에 방문한 지점과 바로 이후에 방문할 지점이 같지 않은 곳만을 방문한다.)
- 다음 지점이  방문했던 곳이라면, 사이클이 존재한다는 뜻이다.

## Code

```java
import java.io.*;
import java.util.StringTokenizer;

/**
 *  No.16929: Two Dots
 *  URL: https://www.acmicpc.net/problem/16929
 *  Hint: DFS + 사이클 체크
 */

public class BOJ16929 {
    static int n, m;
    static char[][] map;
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};
    static boolean[][] visited;
    static String answer = "No";
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        map = new char[n][m];
        visited = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            map[i] = br.readLine().toCharArray();
        }

        // 모든 점을 탐색
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (!visited[i][j]) {
                    visited[i][j] = true;
                    dfs(-1, -1, i, j);
                }
            }
        }

        bw.write(answer);
        bw.close();
        br.close();
    }

    // DFS
    static void dfs(int px, int py, int cx, int cy) {
        if (answer.equals("Yes")) {
            return;
        }

        for (int i = 0; i < 4; i++) {
            int nx = cx + dx[i];
            int ny = cy + dy[i];

            // 범위 안에 있고, 왔던 길이 아니고, 같은 문자인 경우
            if (inRange(nx, ny) && (px != nx || py != ny) && map[cx][cy] == map[nx][ny]) {
                if (visited[nx][ny]) {  // 방문한 적이 있다면 cycle이 있다는 소리
                    answer = "Yes";
                    return;
                } else {
                    visited[nx][ny] = true; // 방문한 적이 없다면 계속 탐색
                    dfs(cx, cy, nx, ny);
                }
            }
        }
    }

    // 주어진 범위를 벗어나는지 판단
    static boolean inRange(int x, int y) {
        if (x >= 0 && x < n && y >= 0 && y < m) {
            return true;
        }
        return false;
    }
}
```