## Problem : https://www.acmicpc.net/problem/16985



## Approach

> `BruteForce(완전탐색) 순열` + `BFS`  + `구현` 개념이 사용된 문제였다.



문제 풀이에 사용된 주요 로직은 다음과 같다.

1. 주어진 판들의 순서를 `순열`로 구하여 쌓는다.
2. 쌓인 판들을 `0도, 90도, 180도, 270도`를 돌려서 나오는 모든 경우를 찾는다.
3. `2번 과정`에서 찾아진 각 경우에 대해 `(0, 0, 0) ~ (4, 4, 4)` 로 가는 `BFS`를 수행한다.
4. `3번 과정`에서 찾아진 최단 경로를 ans 에 저장한다.

(0, 0, 0) ~ (4, 4, 4)를 제외한 (0, 4, 4) ~ (4, 0, 0) 과 같은 경우가 있으나, `2번 과정`을 통하여 이 부분은 커버가 되므로 `(0, 0, 0) ~ (4, 4, 4)` 이 경로만 찾으면 된다.

`(0, 0, 0)` 과 `(4, 4, 4)` 이 막혀있다면 `BFS` 자체가 성립할 수 없으므로 예외처리를 하여 시간을 단축시킨다.



각 판을 쌓는 순서를 정하는 순열 `Ordering()`, 판을 90도 회전시키는 `rotateArray()`, 판을 회전시키는 모든 경우를 찾는 `backtracking()`, bfs를 수행할 `bfs()` 메소드, bfs 를 수행할 미로 배열을 쌓는 `buildMaze()` 를 올바르게 구현하면 된다.



여러 개념을 활용해야 문제를 풀 수 있으므로, 전체 로직을 생각하고, 각 메소드를 부분적으로 실수 없이 구현하는게 포인트인 문제였다.

 

## Code

```java
import java.io.*;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 *  No.16985: Maaaaaaaaaze
 *  Hint: BruteForce 순열 + Backtracking + BFS + 구현 + 배열 복사
 */

public class BOJ16985 {
    static final int size = 5;
    static int ans = Integer.MAX_VALUE;
    static boolean[][][] map = new boolean[size][size][size];
    static boolean[][][] copy = new boolean[size][size][size];
    static int[] dx = {-1, 1, 0, 0, 0, 0};
    static int[] dy = {0, 0, -1, 1, 0, 0};
    static int[] dz = {0, 0, 0, 0, -1, 1};
    static int[] res = new int[5];
    static boolean[] used = new boolean[5];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int k = 0; k < size; k++) {
                    int t = Integer.parseInt(st.nextToken());
                    if (t == 1) {
                        map[i][j][k] = true;
                        copy[i][j][k] = true;
                    }
                }
            }
        }

        Ordering(0);

        bw.write(ans == Integer.MAX_VALUE ? "-1" : String.valueOf(ans));
        bw.close();
        br.close();
    }

    static void backtracking(int depth) {
        if (depth == size) {
            bfs(0, 0, 0);
            return;
        }

        // 시계 방향 0도 회전 후 backtracking
        backtracking(depth + 1);

        // 시계 방향 90도 회전 후 backtracking
        rotateArray(depth);
        backtracking(depth + 1);

        // 시계 방향 180도 회전 후 backtracking
        rotateArray(depth);
        backtracking(depth + 1);

        // 시계 방향 270도 회전 후 backtracking
        rotateArray(depth);
        backtracking(depth + 1);

        // 미로 원상복구
        rotateArray(depth);
    }

    static void bfs(int x, int y, int z) {
        if (!copy[x][y][z] || !copy[size - 1][size - 1][size - 1]) {    // 시작 지점이나 끝 지점이 막혀 있으면
            return;
        }

        Queue<Point> q = new ArrayDeque<>();
        boolean[][][] visited = new boolean[size][size][size];
        q.offer(new Point(x, y, z, 0));
        visited[x][y][z] = true;

        while (!q.isEmpty()) {
            Point cur = q.poll();

            // 끝 지점에 도착 했으면 ans를 최솟값으로 갱신
            if (cur.x == size - 1 && cur.y == size - 1 && cur.z == size - 1) {
                ans = Math.min(ans, cur.cnt);
                return;
            }

            for (int i = 0; i < 6; i++) {
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];
                int nz = cur.z + dz[i];

                if (isInRange(nx, ny, nz) && copy[nx][ny][nz] && !visited[nx][ny][nz]) {
                    q.offer(new Point(nx, ny, nz, cur.cnt + 1));
                    visited[nx][ny][nz] = true;
                }
            }
        }
    }

    // maze 쌓기 순서
    static void Ordering(int depth) {
        if (depth == size) {
            buildMaze();
            backtracking(0);
            return;
        }

        for (int i = 0; i < size; i++) {
            if (!used[i]) {
                used[i] = true;
                res[depth] = i;
                Ordering(depth + 1);
                res[depth] = 0;
                used[i] = false;
            }
        }
    }

    // maze 쌓기
    static void buildMaze() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    copy[i][j][k] = map[res[i]][j][k];
                }
            }
        }
    }

    // 시계 방향 90도 회전
    static void rotateArray(int layer) {
        boolean[][] copied = new boolean[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                copied[i][j] = copy[layer][size - 1 - j][i];
            }
        }
        System.arraycopy(copied, 0, copy[layer], 0, size);
    }

    // 미로 범위 검사
    static boolean isInRange(int x, int y, int z) {
        if (x >= 0 && x < size && y >= 0 && y < size && z >= 0 && z < size) {
            return true;
        }
        return false;
    }

    static class Point {
        int x, y, z, cnt;

        Point(int x, int y, int z, int cnt) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.cnt = cnt;
        }
    }
}
```

