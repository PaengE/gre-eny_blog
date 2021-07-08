## Problem : https://www.acmicpc.net/problem/14442

## Approach

> 대표적인 `BFS`문제이다.
>
> 일단 visited[i][j][k] 는 (i, j) 위치에 k번 벽을 부순 채로 도달했는지를 나타내는 배열이다.

문제를 푸는 주요 BFS 로직은 다음과 같다.

- 현재 위치에서 상하좌우 방향으로 벽이 아니라면 cnt++ 한 후, 이동한다. (이 때 visited 배열에 벽을 부순 횟수는 증가시키지 않고 표시한다. visited[nx][ny][broken])
- 현재 위치에서 상하좌우 방향으로 벽인 곳이 있다면, 벽을 더 부실 수 있는지 검사하고 더 부실 수 있다면 벽을 부시고(broken++) cnt++한 후, 이동한다. (이 때는 벽을 부순 횟수를 증가시키고 배열에 표시한다. visited[nx][ny][broken + 1])

BFS를 수행하며, 목표 위치에 도달하면 cnt를 리턴한다. 도달하지 못했다면 -1을 리턴한다.

## Code

```java
import java.io.*;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 *  No.14442: 벽 부수고 이동하기 2
 *  URL: https://www.acmicpc.net/problem/14442
 *  Hint: BFS
 */

public class BOJ14442 {
    static int n, m, k;
    static int[][] map;
    static boolean[][][] visited;
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
        visited = new boolean[n][m][k + 1];

        for (int i = 0; i < n; i++) {
            String s = br.readLine();
            for (int j = 0; j < m; j++) {
                map[i][j] = s.charAt(j) - '0';
            }
        }

        int ans = bfs();
        bw.write(String.valueOf(ans));
        bw.close();
        br.close();
    }

    static int bfs() {
        Queue<Point> q = new ArrayDeque<>();
        q.offer(new Point(0, 0, 0, 1));
        visited[0][0][0] = true;

        while (!q.isEmpty()) {
            Point cur = q.poll();

            if (cur.x == n - 1 && cur.y == m - 1) {
                return cur.cnt;
            }

            for (int i = 0; i < 4; i++) {
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];

                if (inRange(nx, ny)) {
                    if (map[nx][ny] == 0 && !visited[nx][ny][cur.broken]) {
                        visited[nx][ny][cur.broken] = true;
                        q.offer(new Point(nx, ny, cur.broken, cur.cnt + 1));
                    }

                    if (cur.broken + 1 > k) {
                        continue;
                    }

                    if (map[nx][ny] == 1 && !visited[nx][ny][cur.broken + 1]) {
                        visited[nx][ny][cur.broken + 1] = true;
                        q.offer(new Point(nx, ny, cur.broken + 1, cur.cnt + 1));
                    }
                }
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

        Point(int x, int y, int broken, int cnt) {
            this.x = x;
            this.y = y;
            this.broken = broken;
            this.cnt = cnt;
        }
    }
}
```

