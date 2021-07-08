## Problem : https://www.acmicpc.net/problem/14502

## Approach

> `완전탐색(BruteForce)`과 `BFS`를 함께 사용하는 문제이다.

문제 풀이 주요 로직은 다음과 같다.

- `0`인 곳들 중 3군데를 골라서 벽을 세운다.
- 벽을 3개 세웠으면 그 상태에서 BFS를 수행하여 바이러스를 전파시킨다.
- 전파 시킨 뒤에 `0`의 개수를 찾는다.



모든 경우를 다 탐색하여, 안전한 구역의 최대 개수를 구한다.

## Code

```java
import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 *  No.14502: 연구소
 *  URL: https://www.acmicpc.net/problem/14502
 *  Hint: DFS(BruteForce) + BFS
 */

public class BOJ14502 {
    static int n, m, ans = Integer.MIN_VALUE;
    static int[][] arr;
    static ArrayList<Point> virus = new ArrayList<>();
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        arr = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
                if (arr[i][j] == 2) {
                    virus.add(new Point(i, j));
                }
            }
        }

        buildWall(0);

        bw.write(String.valueOf(ans));
        bw.close();
        br.close();
    }

    // 벽 세우기 (BruteForce)
    static void buildWall(int wallCount) {
        if (wallCount == 3) {
            ans = Math.max(bfs(), ans);
            return;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (arr[i][j] == 0) {
                    arr[i][j] = 1;
                    buildWall(wallCount + 1);
                    arr[i][j] = 0;
                }
            }
        }
    }

    static int bfs() {
        Queue<Point> q = new ArrayDeque<>(virus);
        boolean[][] visited = new boolean[n][m];

        // 바이러스 전파
        while (!q.isEmpty()) {
            Point cur = q.poll();

            for (int i = 0; i < 4; i++) {
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];

                if (inRange(nx, ny) && arr[nx][ny] != 1 && !visited[nx][ny]) {
                    visited[nx][ny] = true;
                    q.offer(new Point(nx, ny));
                }
            }
        }

        // 안전한 구역의 개수 세기
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (arr[i][j] == 0 && !visited[i][j]) {
                    cnt++;
                }
            }
        }

        return cnt;
    }

    // 범위 체크
    static boolean inRange(int x, int y) {
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

