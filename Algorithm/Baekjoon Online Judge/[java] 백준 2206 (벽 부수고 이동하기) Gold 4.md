## Problem : https://www.acmicpc.net/problem/2206

## Approach

조건이 달려있는 경로 최소비용 문제이다. 필자는 BFS를 사용했다.

경우의 수를 따지기 전에, 현재 위치에서 벽을 부시고 왔는지에 대한 brokenWall[x][y] 배열이 필요하다.

brokenWall[x][y] 의 값이 2이면, 해당 (x, y)를 아직 방문하지 않았다는 뜻이고,

1이라면, 해당 (x, y)를 방문했는데 방문하기전에 경로 어디선가 한번 벽을 부시고 왔다는 뜻이고,

0이라면, 해당 (x, y)를 방문했고 방문 전 경로 어디에서도 벽을 부시고 오지 않았다는 뜻이다.

> 이동할 수 있는 경우의 수는 다음과 같다.
>
> ​	1. 이동할 곳이 벽이고, 지금까지 한번도 벽을 부순적이 없는 경우 
>
> ​		- 범위 검사 후, 이동 (brokenWall 값은 +1하여 넘겨준다.)
>
> ​	2. 이동할 곳이 벽이 아니고, 지금까지 한번도 벽을 부순적이 없는 경우
>
> ​		- 범위 검사 후, 이동 (brokenWall 값은 그대로 넘겨준다.)
>
> ​	3. 이동할 곳이 벽이 아니고, 지금까지 한번 벽을 부순적이 있는 경우
>
> ​		- 범위 검사 후, 이동 (brokenWall 값은 그대로 넘겨준다.)

코드에 주석이 달려 있으므로, 같이 읽어보면 이해하기 쉬울 것이다.

## Code

```java
import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * no.2206: 벽 부수고 이동하기
 * description: "현재 상태"를 정점으로 표현하여 그래프를 만들고 최단거리를 구하는 문제
 */

public class BOJ2206 {
    static int n, m;
    static int[][] map, brokenWall, count;
    static int[] dx = new int[]{0, 0, -1, 1};
    static int[] dy = new int[]{-1, 1, 0, 0};
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        map = new int[n][m];
        brokenWall = new int[n][m];
        count = new int[n][m];

        for (int i = 0; i < n; i++) {
            String[] s = br.readLine().split("");
            for (int j = 0; j < m; j++) {
                map[i][j] = Integer.parseInt(s[j]);
                brokenWall[i][j] = 2;
            }
        }

        bw.write(String.valueOf(bfs(0, 0)));
        bw.flush();
        br.close();
        bw.close();
        
    }

    static int bfs(int x, int y) {
        Queue<int[]> q = new LinkedList<int[]>();

        q.offer(new int[]{x, y});
        brokenWall[x][y] = 0;
        count[x][y] = 1;


        while (!q.isEmpty()) {
            int cx = q.peek()[0];
            int cy = q.peek()[1];
            q.poll();

            // 제일 처음 (n, m)에 도착할 때의 count 값을 return
            if (cx == n - 1 && cy == m - 1) {
                return count[n - 1][m - 1];
            }

            for (int i = 0; i < 4; i++) {
                int nx = cx + dx[i];
                int ny = cy + dy[i];

                // brokenWall 값이 2면 해당 (x, y)를 아직 방문하지 않았다는 뜻
                // brokenWall 값이 1이면 해당 (x, y)를 방문했고 1번 부셨다는 뜻
                // brokenWall 값이 0이면 해당 (x, y)를 방문했고 0번 부셨다는 뜻
                if (nx >= 0 && ny >= 0 && nx < n && ny < m) {
                    // 이동할 곳이 벽이고, 지금까지 한번도 벽을 안 부순 경우
                    if (map[nx][ny] == 1 && brokenWall[cx][cy] == 0) {
                        q.offer(new int[]{nx, ny});
                        brokenWall[nx][ny] = 1;
                        count[nx][ny] = count[cx][cy] + 1;
                    }
                    // 현재 있는 곳의 벽을 부신 횟수는 0 혹은 1인데,
                    // 이동할 곳이 벽이 아니고 이동할 곳의
                    // 벽을 부신 횟수가 1인 경우: 현재 있는 곳의 벽을 부신 횟수가 0인 경우만 방문
                    // 벽을 부신 횟수가 2인 경우: 미방문이므로 현재 있는 곳의 벽을 부신 횟수에 상관없이 방문
                    else if (map[nx][ny] == 0 && brokenWall[nx][ny] > brokenWall[cx][cy]) {
                        q.offer(new int[]{nx, ny});
                        brokenWall[nx][ny] = brokenWall[cx][cy];
                        count[nx][ny] = count[cx][cy] + 1;
                    }
                }
            }
        }
        // (n, m)에 도달하지 못하면 -1 return
        return -1;
    }
}

```

