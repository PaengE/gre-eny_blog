## Problem : https://www.acmicpc.net/problem/2178

## Approach

(1, 1)에서 (n, n)까지의 최소 이동거리를 구하는 문제이다.

BFS를 사용하여, 범위를 벗어나지 않고, 요소가 1이며, 방문하지 않았다면, 큐에 집어넣는 식으로 진행하였다.

방문할 곳에 직전 곳에 있는 값+1 을 저장해주면 (n, n)에 최소 비용이 저장될 것이다.

BFS를 구현만 할줄 안다면, 쉬운 문제가 될 것이다.

## Code

```java
import java.io.*;
import java.util.*;

/**
 * no.2178: 미로 탐색
 * description: BFS 의 특징은 각 정점을 최단경로로 방문한다는 것
 * hint: BFS 를 사용하시오 (DFS 는 오래걸림 -> backtracking 하므로)
 */

public class BOJ2178 {
    static int n, m;
    static int[][] maze;
    static boolean[][] visited;
    static int dx[] = {0, 0, 1, -1};
    static int dy[] = {1, -1, 0, 0};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        maze = new int[n][m];
        visited = new boolean[n][m];

        for (int i = 0; i < n; i++) {
            String[] s = br.readLine().split("");
            for (int j = 0; j < m; j++) {
                maze[i][j] = Integer.parseInt(s[j]);
            }
        }

        bfs(0, 0);

//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < m; j++) {
//                System.out.print(maze[i][j] + " ");
//            }
//            System.out.println();
//        }

        bw.write(String.valueOf(maze[n - 1][m - 1]));
        bw.flush();
        br.close();
        bw.close();
    }

    static void bfs(int x, int y) {
        Queue<int[]> q = new LinkedList<int[]>();

        q.offer(new int[]{x, y});

        while (!q.isEmpty()) {
            int cx = q.peek()[0];
            int cy = q.peek()[1];
            q.poll();

            for (int i = 0; i < 4; i++) {
                int nx = cx + dx[i];
                int ny = cy + dy[i];

                if (nx >= 0 && ny >= 0 && nx < n && ny < m) {
                    if (maze[nx][ny] == 1 && !visited[nx][ny]) {
                        q.offer(new int[]{nx, ny});

                        visited[cx][cy] = true;

                        maze[nx][ny] = maze[cx][cy] + 1;
                    }
                }
            }
        }
    }
}

```

