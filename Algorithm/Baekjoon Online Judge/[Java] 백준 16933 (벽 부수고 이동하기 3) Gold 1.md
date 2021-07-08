## Problem : https://www.acmicpc.net/problem/16933

## Approach

> `BFS`문제이다. 백준 14442 (벽 부수고 이동하기 2) 문제에 조건이 하나 더 달린 문제이다.
>
> 여기서의 visited배열은 4차원 배열이다.
>
> visited[n][m][k][a] 는 (n, m) 위치를 k번 벽을 부신 채로 낮(a = 0) 혹은 밤(a = 1)에 방문했다는 뜻이다.

문제 풀이의 주요 로직은 다음과 같다.

- 낮이라면, 현재 위치에서 벽이 아닌 곳으로 이동하며 + 벽인 곳은 벽을 더 부실 수 있는지 확인한 후 이동한다.
- 밤이라면, 현재 위치에서 벽이 아닌 곳으로만 이동이 가능하다.
- 낮, 밤을 따지지 않고 제자리에 가만히 있는다. (이 때, 낮 밤은 서로 뒤바뀐다.)

마찬가지로 목표 위치에 도달하면 cnt를, 도달하지 못했으면 -1을 리턴한다.

## Code

```java
import java.io.*;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 *  No.16933: 벽 부수고 이동하기 3
 *  URL: https://www.acmicpc.net/problem/16933
 *  Hint: BFS
 */

public class BOJ16933 {
    static int n, m, k;
    static int[][] map;
    static boolean[][][][] visited;
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        map = new int[n][m];
        visited = new boolean[n][m][k + 1][2];  // 0: 낮, 1: 밤

        for (int i = 0; i < n; i++) {
            String s = br.readLine();
            for (int j = 0; j < m; j++) {
                map[i][j] = s.charAt(j) - '0';
            }
        }

        bw.write(String.valueOf(bfs()));
        bw.close();
        br.close();
    }

    static int bfs() {
        Queue<Point> q = new ArrayDeque<>();
        q.offer(new Point(0, 0, 0, 1, true));
        visited[0][0][0][0] = true;

        while (!q.isEmpty()) {
            Point cur = q.poll();

            if (cur.x == n - 1 && cur.y == m - 1) {
                return cur.cnt;
            }

            for (int i = 0; i < 4; i++) {
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];

                if (!inRange(nx, ny)) {
                    continue;
                }

                //  낮이고 벽을 더 부술 수 있으면
                if (map[nx][ny] == 1 && cur.daytime && cur.broken + 1 <= k && !visited[nx][ny][cur.broken + 1][1]) {
                    visited[nx][ny][cur.broken + 1][1] = true;
                    q.offer(new Point(nx, ny, cur.broken + 1, cur.cnt + 1, false));
                }

                if (map[nx][ny] == 0 && !visited[nx][ny][cur.broken][cur.daytime ? 1 : 0]) {
                    visited[nx][ny][cur.broken][cur.daytime ? 1 : 0] = true;
                    q.offer(new Point(nx, ny, cur.broken, cur.cnt + 1, !cur.daytime));
                }
            }

            if (!visited[cur.x][cur.y][cur.broken][cur.daytime ? 1 : 0]) {
                visited[cur.x][cur.y][cur.broken][cur.daytime ? 1 : 0] = true;
                q.offer(new Point(cur.x, cur.y, cur.broken, cur.cnt + 1, !cur.daytime));
            }

        }
        return -1;
    }

    static boolean inRange(int x, int y) {
        if (x >= 0 && x < n && y >= 0 && y < m) {
            return true;
        }
        return false;
    }

    static class Point{
        int x, y, broken, cnt;
        boolean daytime;

        Point(int x, int y, int broken, int cnt, boolean daytime) {
            this.x = x;
            this.y = y;
            this.broken = broken;
            this.cnt = cnt;
            this.daytime = daytime;
        }
    }
}
```

