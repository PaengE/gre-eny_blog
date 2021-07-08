## Problem : https://www.acmicpc.net/problem/16920



## Approach

> `BFS` + `구현`이 필요한 문제이다.



먼저 사용된 변수 설명을 하겠다.

- `starts[i]` : 플레이어 i 의 BFS 시작점들.
- `s[i]` : 플레이어 i 가 한번에 이동할 수 있는 거리.
- `visited[i][j]` : (i, j) 위치를 방문했는지를 표시.
- `cnt[i]` : 플레이어 i 의 땅 개수.



위 변수들을 사용한 문제 풀이 주요 로직은 다음과 같다.

- 플레이어의 순서대로 `starts[플레이어] 시작점`들을 기반(초기 큐에 집어넣는다.)으로 `BFS`를 수행한다.
- `s[플레이어]`만큼 움직인, 이번 `BFS`에서 플레이어가 방문한 곳들 중 가장 외곽인, 부분은 다음 `BFS`의 시작점들로 쓰기 위하여 `starts[플레이어]`에 저장한다.
- 위 과정을 수행하며 `cnt[플레이어]` 를 같이 구성해 나간다.

모든 플레이어가 `BFS`를 1번 수행하는 것을 `1 cycle`로 하여, 어느 한 플레이어라도 새로운 곳을 방문했다면, 다음 cycle 도 진행한다.

누구도 새로운 곳을 방문하지 못할 때까지 위 과정이 반복된다.



## Code

```java
import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 *  No.16920: 확장 게임
 *  Hint: BFS + 구현
 */

public class BOJ16920 {
    static int n, m, p;
    static int[] s;
    static ArrayList<Point>[] starts;   // BFS를 시작할 시작점들
    static int[] cnt;
    static boolean[][] visited;

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        p = Integer.parseInt(st.nextToken());
        starts = new ArrayList[p + 1];
        for (int i = 1; i <= p; i++) {
            starts[i] = new ArrayList<>();
        }

        s = new int[p + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= p; i++) {
            s[i] = Integer.parseInt(st.nextToken());
        }

        visited = new boolean[n][m];
        cnt = new int[p + 1];
        for (int i = 0; i < n; i++) {
            String s = br.readLine();
            for (int j = 0; j < m; j++) {
                char c = s.charAt(j);
                if (c == '#') { // 벽이면 방문한 걸로 침
                    visited[i][j] = true;
                } else if (c != '.') {  // 숫자면 방문 표시를 하고 시작점 리스트에  추가
                    starts[c - '0'].add(new Point(i, j, 0));
                    visited[i][j] = true;
                    cnt[c - '0']++;
                }
            }
        }

        while (true) {
            boolean endFlag = false;    // 새로 방문한 곳이 있는지를 표시
            for (int i = 1; i <= p; i++) {
                endFlag = bfs(i) || endFlag;
            }

            // 더이상 새로 방문한 곳이 없다면 루프 탈출
            if (!endFlag) {
                break;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= p; i++) {
            sb.append(cnt[i] + " ");
        }

        bw.write(sb.toString().trim());
        bw.close();
        br.close();
    }

    // 새로운 곳을 방문했으면 true 리턴
    static boolean bfs(int idx) {
        Queue<Point> q = new ArrayDeque<>();

        // 시작점들을 모두 큐에 삽입
        for (Point start : starts[idx]) {
            q.offer(start);
        }
        starts[idx].clear();    // 시작점 리스트 비움

        while (!q.isEmpty()) {
            Point cur = q.poll();

            // 시작점으로 부터 가장 먼 곳은 다음 BFS의 시작점으로 하기 위해 리스트에 추가
            if (cur.dist == s[idx]) {
                cur.setDist();
                starts[idx].add(cur);
                continue;
            }

            for (int i = 0; i < 4; i++) {
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];

                if (isInRange(nx, ny) && canMove(nx, ny) && cur.dist < s[idx]) {
                    q.offer(new Point(nx, ny, cur.dist + 1));
                    visited[nx][ny] = true;
                    cnt[idx]++;
                }
            }
        }

        // 시작점 리스트가 비어 있음 == 새로 방문한 곳이 없음
        return !starts[idx].isEmpty();
    }

    static boolean canMove(int x, int y) {
        if (!visited[x][y]) {
            return true;
        }
        return false;
    }

    static boolean isInRange(int x, int y) {
        if (x >= 0 && x < n && y >= 0 && y < m) {
            return true;
        }
        return false;
    }

    static class Point {
        int x, y, dist;

        Point(int x, int y, int dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }

        public void setDist() {
            this.dist = 0;
        }
    }
}
```

