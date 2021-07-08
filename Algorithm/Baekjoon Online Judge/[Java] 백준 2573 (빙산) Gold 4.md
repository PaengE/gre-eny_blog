## Problem : https://www.acmicpc.net/problem/2573



## Approach

> `BFS`를 이용한 `구현`이 필요한 문제였다.



나의 문제 풀이 주요 로직은 다음과 같다.

- 빙산을 Grouping 하여 `빙산 그룹의 개수`를 찾는다. 이 때, `빙산 그룹의 개수`가 `0`이거나 `2`이상 이면 그만둔다.
- 모든 지점 (x, y)에 대하여 바다와 얼마나 접하는지를 `oceanCnt`배열에 저장한다.
- `oceanCnt`배열을 참고하여 빙산의 높이를 줄인다.

위의 과정을 종료조건이 나오기 전까지 반복한다.



주의할 점으로는 빙산과 닿아있는 부분의 개수를 배열에 저장한 뒤, 한꺼번에 빙산을 녹여야 한다는 것이다.

순차적으로 녹인다면, 빙산을 녹인 위치가 다음 위치에 영향을 끼칠 수도 있다.

또한, 빙산을 녹일 때 빙산의 높이가 `음수`가 될 수 있으므로, 별도의 처리를 하지 않았다면 `== 0`연산자가 아닌 `<= 0`연산자를 사용하여야 한다.



## Code

```java
import java.io.*;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 *  No.2573: 빙산
 *  Hint: BFS + 구현
 */

public class BOJ2573 {
    static int n, m;
    static int[][] map, oceanCnt;
    static boolean[][] visited;

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        map = new int[n][m];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int ans = 0;
        while (true) {
            int icebergCnt = countGroup();
            if (icebergCnt >= 2) {
                break;
            }
            if (icebergCnt == 0) {
                ans = 0;
                break;
            }
            ans++;
        }
        bw.write(String.valueOf(ans));
        bw.close();
        br.close();
    }

    // 빙산 그룹핑
    static int countGroup() {
        int cnt = 0;
        visited = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (map[i][j] > 0 && !visited[i][j]) {
                    if (++cnt >= 2) {   // 그룹 개수가 2이상 이면 return
                        return cnt;
                    }
                    int[][] oceanCnt = bfs(i, j);   // (i, j) 가 접하는 바다의 개수를 저장하는 배열
                    meltingIcebergs(oceanCnt);    // oceanCnt를 기준으로 빙산 녹이기
                }
            }
        }
        return cnt;
    }

    // BFS
    static int[][] bfs(int sx, int sy) {
        Queue<Point> q = new ArrayDeque<>();
        oceanCnt = new int[n][m];

        q.offer(new Point(sx, sy));
        visited[sx][sy] = true;
        oceanCnt[sx][sy] = countNearOcean(sx, sy);
        while (!q.isEmpty()) {
            Point cur = q.poll();
            for (int i = 0; i < 4; i++) {
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];

                if (isInRange(nx, ny) && canMove(nx, ny)) {
                    q.offer(new Point(nx, ny));
                    visited[nx][ny] = true;
                    oceanCnt[nx][ny] = countNearOcean(nx, ny);
                }
            }
        }
        return oceanCnt;
    }

    // 빙산 녹이기
    static void meltingIcebergs(int[][] oceanCnt) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                map[i][j] -= oceanCnt[i][j];
            }
        }
    }

    // (x, y) 기준 4방향 바다의 개수 세기
    static int countNearOcean(int x, int y) {
        int cnt = 0;
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (isInRange(nx, ny) && map[nx][ny] <= 0) {
                cnt++;
            }
        }
        return cnt;
    }

    // 이동할 수 있는지 체크
    static boolean canMove(int x, int y) {
        if (map[x][y] > 0 && !visited[x][y]) {
            return true;
        }
        return false;
    }

    // 범위 체크
    static boolean isInRange(int x, int y) {
        if (x >= 0 && x < n && y >= 0 && y < m) {
            return true;
        }
        return false;
    }

    static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
```

