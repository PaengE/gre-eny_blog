## Problem : https://www.acmicpc.net/problem/3055

## Approach

> 이동 가능한 곳이 동적으로 변하는 환경에서의 `BFS`문제이다.

문제를 푸는 주요 로직은 다음과 같다. 

다음에 물이 찰 예정인 곳은 비버가 방문할 수 없으니 물을 먼저 전파시키고 비버를 이동시킨다.

1. 물을 전파시킨다.
2. 비버를 이동할 수 있는(물이 차지 않은) 곳들을 방문한다. 

즉, `비버`따로 `물`따로 각각 BFS가 수행되어야 한다. (ps. cnt 배열을 사용하지 않고 Point 클래스에 정보를 저장하여도 된다.)

비버 큐가 빌 때까지(비버가 더 이상 움직이지 못할 때까지) 위의 단계를 반복한다.

## Code

```java
import java.io.*;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 *  No.3055: 탈출
 *  URL: https://www.acmicpc.net/problem/3055
 *  Hint: BFS
 */

public class BOJ3055 {
    static int r, c, ex, ey;
    static char[][] map;
    static int[][] cnt;
    static boolean[][] visited;
    static Queue<Point> beaver = new ArrayDeque<>();    // 움직일 수 있는 비버의 위치를 저장
    static Queue<Point> water = new ArrayDeque<>();     // 퍼질 수 있는 물의 위치를 저장
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        map = new char[r][c];
        cnt = new int[r][c];
        visited = new boolean[r][c];

        // map에서 정보 추출
        for (int i = 0; i < r; i++) {
            String t = br.readLine();
            for (int j = 0; j < c; j++) {
                map[i][j] = t.charAt(j);
                if (map[i][j] == '*') {
                    water.offer(new Point(i, j));
                } else if (map[i][j] == 'S') {
                    beaver.offer(new Point(i, j));
                    visited[i][j] = true;
                } else if (map[i][j] == 'D') {
                    ex = i;
                    ey = j;
                }
            }
        }

        // 더 이상 움직일 수 있는 비버가 없을 때까지
        while (!beaver.isEmpty()) {
            fillWater();
            bfs();
        }

        if (cnt[ex][ey] == 0) {
            bw.write("KAKTUS");
        } else {
            bw.write(String.valueOf(cnt[ex][ey]));
        }

        bw.close();
        br.close();
    }

    // 비버 이동
    static void bfs() {
        int sizeB = beaver.size();

        for (int i = 0; i < sizeB; i++) {
            Point cur = beaver.poll();

            for (int j = 0; j < 4; j++) {
                int nx = cur.x + dx[j];
                int ny = cur.y + dy[j];

                if (inRange(nx, ny) && !visited[nx][ny] && (map[nx][ny] != '*' && map[nx][ny] != 'X')) {
                    cnt[nx][ny] = cnt[cur.x][cur.y] + 1;
                    visited[nx][ny] = true;
                    beaver.offer(new Point(nx, ny));
                }
            }
        }
    }

    // 물 전이
    static void fillWater() {
        int sizeQ = water.size();

        for (int i = 0; i < sizeQ; i++) {
            Point cur = water.poll();

            for (int j = 0; j < 4; j++) {
                int nx = cur.x + dx[j];
                int ny = cur.y + dy[j];

                if (inRange(nx, ny) && map[nx][ny] == '.') {
                    map[nx][ny] = '*';
                    water.offer(new Point(nx, ny));
                }
            }
        }
    }

    static boolean inRange(int x, int y) {
        if (x >= 0 && x < r && y >= 0 && y < c) {
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

