## Problem : https://www.acmicpc.net/problem/1261

## Approach

> 이 문제의 기본 접근법은 `갈 수 있는 곳 중 벽이 아닌 곳이 있으면 우선 그 쪽으로 가고, 갈 수 있는 곳이 벽인 곳밖에 없다면 벽을 뚫는다.`이다.
>
> 이를 구현하는 데에는 여러 방법이 있겠지만, 나는 Deque+BFS, PriorityQueue+BFS 두 가지 방법으로 풀이를 해봤다. 두 방법 모두 풀이가 가능하다.

주의할 점은, 큐에 동일한 우선순위를 가진 요소가 있다면, 큐에 나중에 들어온 것을 먼저 처리해야 한다는 것이다.

PriorityQueue 를 사용할 때에는 우선순위가 같을 때 (별다른 기준이 없다면) 큐에 들어온 순서대로 처리된다. 그래서 PQ에서는 `벽을 부순 횟수`가 지점을 방문하는데에 있어 고려요소가 된다.

하지만 Deque 풀이에서는 벽이 아닐 때는 나중에 들어온 것이 제일 먼저 처리되게끔 offerFirst() 로 큐에 삽입했기 때문에 `벽을 부순 횟수`가 다음 지점을 방문하는데에 있어 고려요소가 아니다. 

(정답을 구하려면 `벽을 부순 횟수`가 필요하긴 하지만, Deque에서 어떤 요소를 빼낼 것인가에 대한 기준의 요소가 아니라는 뜻이다.)

## Code

```java
import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ1261 {
    static int n, m;
    static int[][] map, dp;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        map = new int[m][n];
        dp = new int[m][n];

        for (int i = 0; i < m; i++) {
            String[] str = br.readLine().split("");
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(str[j]);
            }
        }

        bfs(0, 0);

        bw.write(String.valueOf(dp[m-1][n-1]));
        bw.close();
        br.close();

    }

    // Deque + BFS
    static void bfs(int sx, int sy) {
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        Deque<P> deq = new ArrayDeque<>();
        boolean[][] visited = new boolean[m][n];
        deq.offerFirst(new P(sx, sy, 0));
        visited[sx][sy] = true;

        while (!deq.isEmpty()) {
            P cur = deq.poll();
            if (cur.x == m - 1 && cur.y == n - 1) {
                return;
            }

            for (int i = 0; i < 4; i++) {
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];

                if (nx >= 0 && nx < m && ny >= 0 && ny < n) {
                    if (!visited[nx][ny]) {
                        visited[nx][ny] = true;
                        if (map[nx][ny] == 0) {
                            dp[nx][ny] = dp[cur.x][cur.y];
                            deq.offerFirst(new P(nx, ny, 0));
                        } else {
                            dp[nx][ny] = dp[cur.x][cur.y] + 1;
                            deq.offerLast(new P(nx, ny, 1));
                        }
                    }
                }
            }
        }
    }

    /**
     * PriorityQueue + BFS
     */
//    static void bfs(int sx, int sy) {
//        int[] dx = {-1, 1, 0, 0};
//        int[] dy = {0, 0, -1, 1};
//
//        PriorityQueue<P> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.flag, o2.flag));
//        boolean[][] visited = new boolean[m][n];
//        pq.offer(new P(sx, sy, 0));
//        visited[sx][sy] = true;
//
//        while (!pq.isEmpty()) {
//            P cur = pq.poll();
//            if (cur.x == m - 1 && cur.y == n - 1) {
//                return;
//            }
//
//            for (int i = 0; i < 4; i++) {
//                int nx = cur.x + dx[i];
//                int ny = cur.y + dy[i];
//
//                if (nx >= 0 && nx < m && ny >= 0 && ny < n) {
//                    if (!visited[nx][ny]) {
//                        if (map[nx][ny] == 0) {
//                            dp[nx][ny] = dp[cur.x][cur.y];
//                        } else {
//                            dp[nx][ny] = dp[cur.x][cur.y] + 1;
//                        }
//                        pq.offer(new P(nx, ny, dp[nx][ny]));
//                        visited[nx][ny] = true;
//
//                    }
//                }
//            }
//        }
//    }

    static class P {
        int x, y, flag;
        P(int x, int y, int flag) {
            this.x = x;
            this.y = y;
            this.flag = flag;
        }
    }
}
```

