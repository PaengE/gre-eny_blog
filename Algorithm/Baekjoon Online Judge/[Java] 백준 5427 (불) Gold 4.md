## Problem : https://www.acmicpc.net/problem/5427



## Approach

> `BFS` 문제이다.



나는 `사람이 지나간 곳`과 `불이 지나간 곳`을 따로 체크하여 구성하였다. 

하지만 문제를 풀고나서 생각해보니 `불`이 `사람`보다 먼저 움직이게 한다면 같은 visited 배열을 사용해도 될 것 같다. 
(사람이 먼저 방문하면 불이 나중에라도 그 곳을 방문할 필요가 없기 때문이다. 사람보다 더 늦게 방문)



문제 풀이의 주요 로직은 다음과 같이 간단하다.

- 일반적인 BFS 를 수행하되, 해당 포인트가 `불`인지 `사람`인지를 같이 저장한다.

적당히 종료 조건과 방문 조건을 구성하면 된다.



## Code

```java
import java.io.*;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 *  No.5427: 불
 *  Hint: BFS
 */

public class BOJ5427 {
    static int w, h;
    static char[][] map;
    static boolean[][] fireVisited, peopleVisited;
    static Queue<Point> q = new ArrayDeque<>();

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            w = Integer.parseInt(st.nextToken());
            h = Integer.parseInt(st.nextToken());
            map = new char[h][w];
            fireVisited = new boolean[h][w];
            peopleVisited = new boolean[h][w];
            q.clear();

            int sx = 0, sy = 0;
            for (int i = 0; i < h; i++) {
                String str = br.readLine();
                for (int j = 0; j < w; j++) {
                    char c = str.charAt(j);
                    map[i][j] = c;
                    if (c == '#') {
                        fireVisited[i][j] = true;
                        peopleVisited[i][j] = true;
                    } else if (c == '*') {
                        fireVisited[i][j] = true;
                        q.offer(new Point(i, j, 0, true));
                    } else if (c == '@') {
                        sx = i;
                        sy = j;
                        peopleVisited[i][j] = true;
                    }
                }
            }
            q.offer(new Point(sx, sy, 1, false));

            String ans = bfs();
            sb.append(ans + "\n");
        }

        bw.write(sb.toString());
        bw.close();
        br.close();
    }

    static String bfs() {
        while (!q.isEmpty()) {
            Point cur = q.poll();

            if (!cur.isFire && (cur.x == 0 || cur.x == h - 1 || cur.y == 0 || cur.y == w - 1)) {
                return String.valueOf(cur.cnt);
            }

            for (int i = 0; i < 4; i++) {
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];

                if (isInRange(nx, ny) && !fireVisited[nx][ny]) {    // 불이 방문한 곳이 아닐 경우
                    if (cur.isFire) {   // 불 Point
                        q.offer(new Point(nx, ny, 0, true));
                        fireVisited[nx][ny] = true;
                    } else if (!peopleVisited[nx][ny]){ // 사람 Point && 사람이 방문한 곳이 아닐 경우
                        q.offer(new Point(nx, ny, cur.cnt + 1, false));
                        peopleVisited[nx][ny] = true;
                    }
                }
            }
        }

        return "IMPOSSIBLE";
    }

    // 범위 체크
    static boolean isInRange(int x, int y) {
        if (x >= 0 && x < h && y >= 0 && y < w) {
            return true;
        }
        return false;
    }

    static class Point {
        int x, y, cnt;
        boolean isFire;

        Point(int x, int y, int cnt, boolean isFire) {
            this.x = x;
            this.y = y;
            this.cnt = cnt;
            this.isFire = isFire;
        }
    }
}
```

