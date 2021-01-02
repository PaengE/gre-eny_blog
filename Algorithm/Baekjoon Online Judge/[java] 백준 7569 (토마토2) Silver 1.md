## Problem : https://www.acmicpc.net/problem/7569

## Approach

백준 7576번 토마토 문제와 같은 문제이지만, 3차원 배열로 구성된 문제이다.

7576번 문제의 풀이는 밑의 링크에 있다.

[2020/12/30 - [Algorithm/Baekjoon Online Judge\] - [java] 백준 7576 (토마토1) Silver 1﻿](https://gre-eny.tistory.com/69)

문제에서 배열의 요소가 1인 모든 곳이 시작점이다. (따라서 요소가 1인 모든 좌표를 큐에 넣고 BFS를 수행한다.)

벽이 -1 로 막혀있지 않고, 범위를 벗어나지 않으며, 방문하지 않았다면,

방문하면서 직전 위치의 값 + 1을 방문하는 곳에 저장한다.

BFS가 완전히 모두 수행된 후, 최단경로 배열을 검사하여, 0이 존재하면 방문하지 못했단 뜻이므로 -1을, 0이 존재하지 않으면 배열의 요소중 가장 큰 값을 return 하면 된다.

하지만 3차원 배열이므로, 상하좌우뿐만 아니라 위, 아래도 검사하여야 한다.

## Code

```java
import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * no.7569: 토마토 (3차원)
 * description: BFS 로 토마토를 익히는 문제 (시작점이 여러개인 문제)
 * note: 모든 시작점을 enqueue 하고 BFS
 */

public class BOJ7569 {
    static int n, m, h;
    static int[][][] box;
    static boolean[][][] visited;
    static Queue<int[]> q = new LinkedList<int[]>();
    // 위 아래 상 하 좌 우
    static int[] dx = {0, 0, 0, 0, -1, 1};
    static int[] dy = {0, 0, 1, -1, 0, 0};
    static int[] dz = {1, -1, 0, 0, 0, 0};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        m = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());
        h = Integer.parseInt(st.nextToken());
        box = new int[n][m][h];
        visited = new boolean[n][m][h];

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < n; j++) {
                st = new StringTokenizer(br.readLine());
                for (int k = 0; k < m; k++) {
                    int t = Integer.parseInt(st.nextToken());
                    box[j][k][i] = t;

                    // 모든 시작점을 enqueue
                    if (t == 1) {
                        q.offer(new int[]{j, k, i});
                        visited[j][k][i] = true;
                    }
                }
            }
        }

        bfs();

        int answer = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < m; k++) {
                    // 배열에 0이 존재하면 익을 수 없는 토마토가 있으므로 -1 출력 후 종료
                    if (box[j][k][i] == 0) {
                        bw.write("-1\n");
                        bw.flush();
                        br.close();
                        bw.close();
                        System.exit(0);
                    }
                    // 0이 없다면 배열 중 제일 큰 수를 저장
                    else {
                        answer = Math.max(answer, box[j][k][i]);
                    }
                }
            }
        }
        // 배열에서 가장 큰 수 - 1 이 답안
        bw.write(String.valueOf(answer - 1));
        bw.flush();
        br.close();
        bw.close();
    }

    static void bfs() {
        while (!q.isEmpty()) {
            int cx = q.peek()[0];
            int cy = q.peek()[1];
            int cz = q.peek()[2];
            q.poll();

            for (int i = 0; i < 6; i++) {
                int nx = cx + dx[i];
                int ny = cy + dy[i];
                int nz = cz + dz[i];

                if (nx >= 0 && ny >= 0 && nz >= 0 && nx < n && ny < m && nz < h) {
                    if (box[nx][ny][nz] == 0 && !visited[nx][ny][nz]) {
                        q.offer(new int[]{nx, ny, nz});
                        visited[nx][ny][nz] = true;
                        box[nx][ny][nz] = box[cx][cy][cz] + 1;
                    }
                }
            }
        }
    }
}

```

