## Problem : https://www.acmicpc.net/problem/16988



## Approach

> `BruteForce 완전탐색`과 `BFS`의 개념을 같이 사용한 문제였다.



일단 문제 풀이의 주요 로직은 다음과 같다.

- 먼저 `BFS`를 이용하여 상대편 돌을 `Grouping`한다. 그리고 그룹핑한 결과를 `groups`에 저장한다.
- 그리고 `Backtracking`을 사용하여 돌을 2개 놓는 모든 경우를 만든다.
- 그 상태에서 상대 돌을 몇개 딸 수 있는지 검사하고 `ans`를 갱신한다.



상대 돌 그룹의 모든 돌들의 상하좌우에 빈 공간이 없다면 그 그룹의 돌들은 다 죽일 수 있다.

따라서 모든 그룹에 대하여 그 그룹을 구성하는 모든 돌들의 상하좌우를 검사해보면 된다.



## Code

```java
import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 *  No.16988: Baaaaaaaaaduk2 (Easy)
 *  Hint: BruteForce + BFS + 구현
 */

public class BOJ16988 {
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    static int n, m, ans;
    static int[][] arr;
    static ArrayList<ArrayList<Point>> groups;  // 배열의 값이 2인 돌들을 그룹핑한 것을 저장
    static ArrayList<Point> emptySpace = new ArrayList<>(); // 배열의 값이 0인 공간들을 저장
    static boolean[][] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        arr = new int[n][m];
        groups = new ArrayList<>();
        visited = new boolean[n][m];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
                if (arr[i][j] == 0) {
                    emptySpace.add(new Point(i, j));
                }
            }
        }

        // group 구성
        int groupId = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (arr[i][j] == 2 && !visited[i][j]) {
                    groups.add(new ArrayList<>());
                    groupingRocks(i, j, groupId++);
                }
            }
        }

        putRocks();
        bw.write(String.valueOf(ans));
        bw.close();
        br.close();

    }

    // 빈 곳에 돌을 2개씩 놓아봄
    static void putRocks() {
        int size = emptySpace.size();
        for (int i = 0; i < size; i++) {
            for (int j = 1; j < size; j++) {
                Point first = emptySpace.get(i);
                Point second = emptySpace.get(j);

                arr[first.x][first.y] = 1;
                arr[second.x][second.y] = 1;
                ans = Math.max(ans, countDeadRocks());
                arr[first.x][first.y] = 0;
                arr[second.x][second.y] = 0;
            }
        }
    }

    // 죽인 상대 돌의 개수를 리턴
    static int countDeadRocks() {
        int deadRocks = 0;

        loop:
        for (ArrayList<Point> group : groups) {

            for (int i = 0; i < group.size(); i++) {
                Point t = group.get(i);
                // 전부 감싸져 있지 않으면 죽일 수 없음
                if (!isAllCovered(t.x, t.y)) {
                    continue loop;
                }
            }

            deadRocks += group.size();
        }

        return deadRocks;
    }

    // (x, y) 기준 상하좌우에 빈 공간이 있으면 false
    static boolean isAllCovered(int x, int y) {
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (isInRange(nx, ny) && arr[nx][ny] == 0) {
                return false;
            }
        }

        return true;
    }

    // 상대 돌을 grouping
    static void groupingRocks(int sx, int sy, int groupId) {
        Queue<Point> q = new ArrayDeque<>();

        q.offer(new Point(sx, sy));
        groups.get(groupId).add(new Point(sx, sy));
        visited[sx][sy] = true;

        while (!q.isEmpty()) {
            Point cur = q.poll();

            for (int i = 0; i < 4; i++) {
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];

                if (isInRange(nx, ny) && arr[nx][ny] == 2 && !visited[nx][ny]) {
                    q.offer(new Point(nx, ny));
                    groups.get(groupId).add(new Point(nx, ny));
                    visited[nx][ny] = true;
                }
            }
        }
    }

    static boolean isInRange(int x, int y) {
        if (x >= 0 && x < n && y >= 0 && y < m) {
            return true;
        }
        return false;
    }

    static class Point{
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
```

