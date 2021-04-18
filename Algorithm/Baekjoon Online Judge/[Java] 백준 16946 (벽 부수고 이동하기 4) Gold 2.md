## Problem : https://www.acmicpc.net/problem/16946

## Approach

> BFS/DFS 문제이다.
>
> 하지만 각 점마다 탐색을 한다면 TLE 를 면치 못할 것이다.

각 벽에서 도달할 수 있는 칸이 몇개인지 판단하기 전에 한번의 탐색이 선행되어야 한다.

바로 각 칸이 속하는 그룹의 종류와 그 그룹의 크기를 구해놔야 한다. 따라서 나는 다음과 같은 변수를 사용했다.

- int[][] group: (i, j) 점이 무슨 그룹인지를 나타낸다.
- ArrayList<Integer> groupCount: 번호가 i인 그룹의 크기를 저장한다.

위의 과정을 선행한 후, map[][] 을 검사하면서 1(벽)인 경우, 인접한 상하좌우 칸을 검사한다.(그룹번호가 무엇인지)

인접한 칸이 같은 그룹일 수도 있으니 set을 이용하여 중복을 제거했고, 인접한 칸의 그룹의 크기들을 모두 더해주면 된다.

## Code

```java
import java.io.*;
import java.util.*;

/**
 *  No.16946: 벽 부수고 이동하기 4
 *  URL: https://www.acmicpc.net/problem/16946
 *  Hint: 모든 점마다 각각 탐색(BFS or DFS)을 하면 TLE
 *      
 *      각각의 점들이 어디 그룹에 속해있는지, 그 그룹의 요소가 총 몇개인지를 파악하고
 *      타겟 점이 무슨 그룹들과 연결되어 있는지를 판단
 */

public class BOJ16946 {
    static int n, m, idx;   // idx = 그룹의 인덱스를 저장
    static int[][] map; // (i, j) 위치의 값을 저장
    static int[][] group;   // (i, j) 위치의 그룹 인덱스를 저장
    static boolean[][] visited;
    static ArrayList<Integer> groupCount;   // i 번째 그룹의 크기를 저장
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};
    static StringBuilder sb = new StringBuilder();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        init();

        for (int i = 0; i < n; i++) {
            String s = br.readLine();
            for (int j = 0; j < m; j++) {
                map[i][j] = s.charAt(j) - '0';
            }
        }

        // 최초 Grouping 이 필요
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (map[i][j] == 0 && !visited[i][j]) {
                    groupCount.add(bfs(i, j, idx++));
                }
            }
        }

        // 각 위치 별로 갈 수 있는 곳의 개수를 찾음
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                findPossibleWay(i, j);
            }
            sb.append("\n");
        }

        bw.write(sb.toString());
        br.close();
        bw.close();
    }

    static void findPossibleWay(int x, int y) {
        // 현재 (x, y) 위치가 0이면
        if (map[x][y] == 0) {
            sb.append("0");
            return;
        }

        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            // 인접한 위치가 서로 같은 그룹일 수 있으므로 set 으로 중복을 거름
            if (inRange(nx, ny)) {
                set.add(group[nx][ny]);
            }
        }

        // (x, y) 와 인접한 그룹들의 개수의 총합 % 10
        int cnt = 1;
        for (int e : set) {
            cnt += groupCount.get(e);
        }
        cnt %= 10;

        sb.append(cnt);
    }

    // 현재 그룹의 크기를 리턴
    static int bfs(int sx, int sy, int idx) {
        Queue<P> q = new LinkedList<>();
        q.offer(new P(sx, sy));
        visited[sx][sy] = true;
        group[sx][sy] = idx;

        int cnt = 1;
        while (!q.isEmpty()) {
            P cur = q.poll();

            for (int i = 0; i < 4; i++) {
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];

                if (inRange(nx, ny) && map[nx][ny] == 0 && !visited[nx][ny]) {
                    q.offer(new P(nx, ny));
                    visited[nx][ny] = true;
                    group[nx][ny] = idx;
                    cnt++;
                }

            }
        }

        return cnt;
    }

    // 범위를 벗어나지 않는지 체크
    static boolean inRange(int x, int y) {
        if (x >= 0 && x < n && y >= 0 && y < m) {
            return true;
        }
        return false;
    }

    // 각종 변수 초기화
    static void init() {
        map = new int[n][m];
        group = new int[n][m];
        visited = new boolean[n][m];
        groupCount = new ArrayList<>();
        groupCount.add(0);
        idx = 1;
    }

    static class P{
        int x, y;

        P(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
```

