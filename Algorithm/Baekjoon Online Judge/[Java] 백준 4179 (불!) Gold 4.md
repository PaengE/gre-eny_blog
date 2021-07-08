## Problem : https://www.acmicpc.net/problem/4179



## Approach

> `BFS`를 이욯하는 문제이다.



먼저, 입력을 받으면서 `#` 과 `F`인 부분은 `visited`배열에 `true`를 표시한다. 어차피 지훈이가 방문할 수 없는 곳이기 때문이다.

또한 지훈이가 방문한 곳을 불이 방문할 필요도 없다. 불보다 지훈이가 먼저 도착한 것이 되기 때문이다.

주의할 점은, BFS 를 시작할 때 `불`이 `지훈이`보다 먼저 시작해야 한다는 것이다. 

점 하나씩 처리하는 BFS 특성상 지훈이가 먼저 도착하는 것이 되지만, 문제의 관점에서는 `불`과 `지훈이`가 동시에 동일한 좌표에 도착하는 것이 되기 때문이다.



위에서 언급한 것들을 참고하며 일반적인 `BFS`를 구현하고 수행하면 된다.



## Code

```java
import java.io.*;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 *  No.4179: 불!
 *  Hint: BFS + OOP
 */

public class BOJ4179 {
    static int n, m;
    static char[][] map;
    static boolean[][] visited;
    static Queue<Point> q = new ArrayDeque<>();

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        map = new char[n][m];
        visited = new boolean[n][m];

        int jx = 0, jy = 0;
        for (int i = 0; i < n; i++) {
            String s = br.readLine();
            for (int j = 0; j < m; j++) {
                char c = s.charAt(j);
                // 벽이고 불인 곳은 방문했다고 표시
                if (c == 'J') {
                    jx = i;
                    jy = j;
                } else if (c == 'F') {
                    visited[i][j] = true;
                    q.offer(new Point(i, j, true));
                } else if (c == '#') {
                    visited[i][j] = true;
                }
            }
        }
        // 지훈이의 초기 위치는 모든 불의 위치를 넣고 맨 마지막에 넣음
        q.offer(new Point(jx, jy, 0, false));

        bw.write(bfs());
        bw.close();
        br.close();
    }

    static String bfs() {
        while (!q.isEmpty()) {
            Point cur = q.poll();

            if (!cur.isFire && (cur.x == 0 || cur.y == 0 || cur.x == n - 1 || cur.y == m - 1)) {
                return String.valueOf(cur.cnt + 1);
            }

            for (int i = 0; i < 4; i++) {
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];

                if (isInRange(nx, ny) && canMove(nx, ny)) {
                    if (cur.isFire) {
                        q.offer(new Point(nx, ny, true));
                    } else {
                        q.offer(new Point(nx, ny, cur.cnt + 1, false));
                    }
                    visited[nx][ny] = true;
                }
            }
        }

        return "IMPOSSIBLE";
    }

    // 이동 가능 여부 체크
    static boolean canMove(int x, int y) {
        if (!visited[x][y]) {
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

    static class Point{
        int x, y, cnt;
        boolean isFire;

        Point(int x, int y, boolean isFire) {   // J 일 때의 생성자
            this.x = x;
            this.y = y;
            this.isFire = isFire;
        }

        Point(int x, int y, int cnt, boolean isFire) {  // F 일 때의 생성자
            this.x = x;
            this.y = y;
            this.cnt = cnt;
            this.isFire = isFire;
        }
    }
}
```

