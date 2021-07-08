## Problem : https://www.acmicpc.net/problem/6087

## Approach

> `BFS + DP` 문제라고 볼 수 있다.
>
> 특정 위치 `(x, y)`를 방문하는 방법은 여러 방법이 있는데, 그 중 방향 전환을 최소로 하는 방문을 찾는 문제이다.
>
> 문제에서 `거울`이라는 개념이 나오는데, 쉽게 말하여 방향이 변하는 곳엔 거울을 놓을 수 밖에 없다.
>
> 따라서, 목표 위치 `(x, y)`를 방문하는 방법 중, 방향 전환이 최소인 방문을 찾으면 된다.

- char[][] map: W x H 크기의 지도를 나타낸다.
- int[][] cnt: `(x, y)`를 방문하는데에 필요한 최소 방향전환의 횟수를 저장한다.

시작점은 어디든 상관없다. 두 `C`중 아무 곳에서 시작해도 된다.



주의할 점은, 우선순위 큐(방향전환 횟수가 작은 순)를 사용하기 위해 현재 위치를 방문할 때 온 `방향`을 저장해야 한다는 것이다.

우선순위 큐를 이용해야만 목표 (x, y)를 처음 방문하는 것이 항상 최소 거울 개수임을 보장해 주기 때문이다.

그리고 현재 (x, y)를 방문하는데에 있어 사용한 거울의 개수보다, 더 작은 개수로 방문한 적이 있다면 더 이상 탐색은 무의미하므로 멈춘다.

## Code

```java
import java.io.*;
import java.util.*;

/**
 *  No.6087: 레이저 통신
 *  URL: https://www.acmicpc.net/problem/6087
 *  Hint: BFS
 */

public class BOJ6087 {
    static int w, h;
    static char[][] map;
    static int[][] cnt;
    static int[] dx = {-1, 1, 0, 0};    // 상하좌우
    static int[] dy = {0, 0, -1, 1};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        w = Integer.parseInt(st.nextToken());
        h = Integer.parseInt(st.nextToken());
        map = new char[h][w];
        cnt = new int[h][w];
        for (int[] t : cnt) {
            Arrays.fill(t, Integer.MAX_VALUE);
        }

        boolean flag = false;
        int cx = 0, cy = 0, ex = 0, ey = 0;

        // map 과 C 의 위치 파악
        for (int i = 0; i < h; i++) {
            String s = br.readLine();
            for (int j = 0; j < w; j++) {
                map[i][j] = s.charAt(j);
                if (map[i][j] == 'C') {
                    if (!flag) {
                        cx = i;
                        cy = j;
                        flag = true;
                    } else {
                        ex = i;
                        ey = j;
                    }
                }
            }
        }

        int ans = bfs(cx, cy, ex, ey);
        bw.write(String.valueOf(ans));
        bw.close();
        br.close();
    }

    static int bfs(int cx, int cy, int ex, int ey) {
        PriorityQueue<Point> pq = new PriorityQueue<>();    // 거울 개수 작은 순 우선
        pq.offer(new Point(cx, cy, 0, -1));

        while (!pq.isEmpty()) {
            Point cur = pq.poll();  // 거울 사용이 적은 것부터 뽑기

            // 도착했으면 리턴
            if (cur.x == ex && cur.y == ey) {
                return cur.mirror;
            }

            for (int i = 0; i < 4; i++) {
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];

                if (inRange(nx, ny) && map[nx][ny] != '*') {    // 범위 안쪽이며, 벽이 아니면
                    // 내가 여기까지 사용한 거울의 수가, 기존에 (nx, ny)에 도착하기까지 사용한 거울의 수보다 크면 더이상 탐색이 무의미
                    if (i == cur.direction || cur.direction == -1) {    // 방향이 같으면 거울개수 유지
                        if (cur.mirror <= cnt[nx][ny]) {
                            cnt[nx][ny] = cur.mirror;
                            pq.offer(new Point(nx, ny, cur.mirror, i));
                        }
                    } else {
                        if (cur.mirror + 1 <= cnt[nx][ny]) {    // 방향이 다르면 거울개수 추가
                            cnt[nx][ny] = cur.mirror + 1;
                            pq.offer(new Point(nx, ny, cur.mirror + 1, i));
                        }
                    }
                }
            }
        }
        return 0;
    }

    // 범위 체크
    static boolean inRange(int x, int y) {
        if (x >= 0 && x < h && y >= 0 && y < w) {
            return true;
        }
        return false;
    }

    static class Point implements Comparable<Point>{
        int x, y, mirror, direction;

        Point(int x, int y, int mirror, int direction) {
            this.x = x;
            this.y = y;
            this.mirror = mirror;
            this.direction = direction;
        }

        @Override
        public int compareTo(Point o) {
            return this.mirror - o.mirror;
        }
    }
}
```

