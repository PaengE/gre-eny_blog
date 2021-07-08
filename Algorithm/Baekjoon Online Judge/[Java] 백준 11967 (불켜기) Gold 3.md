## Problem : https://www.acmicpc.net/problem/11967



## Approach

> 일반적인 `BFS`가 아닌 `재귀`와 `BFS`를 혼용하여야 했던 문제이다.



문제 풀이의 주요 로직은 다음과 같다.

- (0, 0)을 시작점으로 `BFS`를 진행하면서, 가능한 스위치들을 모두 켠다.
- 해당 `BFS`에서 새 스위치를 켰다면, (0, 0)을 시작점으로 다시 `BFS`를 진행한다. 
  (새로운 스위치를 올린 여부를 `flag`에 저장한다.)



새로운 스위치가 켜짐이 의미하는 바는, 새로이 불이 켜진 곳을 베시가 방문할 `가능성`이 생긴다는 의미이다.

현재 위치에서 새로 불이 켜진 곳을 탐색하기에는 복잡하고 번거로우니, 새로이 (0, 0)부터 BFS를 다시 수행하는 것이다.

위 과정대로 흘러가면, 더 이상 새로이 켜진 스위치가 없을 때까지 반복될 것이고, 그 이후에는 종료될 것이다.



## Code

```java
import java.io.*;
import java.util.*;

/**
 *  No.11967: 불켜기
 *  Hint: BFS + 재귀
 */

public class BOJ11967 {
    static int n, m;
    static boolean[][] visited, lightOn;
    static ArrayList<Point>[][] lists;

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        visited = new boolean[n + 1][n + 1];    // 방문함을 표시
        lightOn = new boolean[n + 1][n + 1];    // 불이 켜짐을 표시
        lightOn[1][1] = true;

        lists = new ArrayList[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                lists[i][j] = new ArrayList<>();
            }
        }

        // 그래프 구성
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            lists[x][y].add(new Point(a, b));
        }

        int ans = bfs(1, 1) + 1;
        bw.write(String.valueOf(ans));
        bw.close();
        br.close();
    }

    // (sx, sy)를 시작으로 BFS
    static int bfs(int sx, int sy) {
        Queue<Point> q = new ArrayDeque<>();
        initArrays();
        visited[sx][sy] = true;
        q.offer(new Point(sx, sy));

        boolean flag = false;   // true: 새로이 불을 켠 곳이 있음
        int cnt = 0;
        while (!q.isEmpty()) {
            Point cur = q.poll();

            // 현재 위치에서 가능한 스위치를 모두 켬
            for (Point willLightOn : lists[cur.x][cur.y]) {
                if (!lightOn[willLightOn.x][willLightOn.y]) {
                    lightOn[willLightOn.x][willLightOn.y] = true;
                    cnt++;
                    flag = true;
                }
            }

            for (int i = 0; i < 4; i++) {
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];

                if (isInRange(nx, ny) && !visited[nx][ny] && lightOn[nx][ny]) {
                    q.offer(new Point(nx, ny));
                    visited[nx][ny] = true;
                }
            }
        }

        // 새로이 불이 켜진 곳이 있으면 시작지점부터 다시 BFS
        if (flag) {
            cnt += bfs(1, 1);
        }

        return cnt;
    }

    static boolean isInRange(int x, int y) {
        if (x >= 1 && x <= n && y >= 1 && y <= n) {
            return true;
        }
        return false;
    }

    static void initArrays() {
        for (int i = 1; i <= n; i++) {
            Arrays.fill(visited[i], false);
        }
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

