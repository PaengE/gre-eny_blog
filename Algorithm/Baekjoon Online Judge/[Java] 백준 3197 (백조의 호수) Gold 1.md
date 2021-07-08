## Problem : https://www.acmicpc.net/problem/3197



## Approach

> `BinarySearch` 를 이용한 `BFS + 구현` 문제였다.



먼저, 얼음이 며칠 뒤에 녹는지에 대한 전처리가 필요하다. (코드에서는 iceberg라고 표현했다.)

얼음에 대한 전처리는 다음 과정으로 처리하였다.

1. `findIcebergNearWater()`: 물과 인접한 얼음들을 구하였다.
   - `icebergsNearWater 큐` : 이 변수는 물과 인접한 얼음의 위치를 저장한다.
2. `findMeltingDay()`: BFS를 수행하여, 각 얼음이 녹는데 걸리는 일자를 구한다.
   - `meltingDay 배열`: (i, j) 위치의 얼음이 며칠 뒤에 녹는지 저장한다.

위 과정을 수행하면서 가장 마지막에 녹는 얼음이 언제 녹는지 `maxDay` 변수에 기록한다.



백조는 `최소 0 ~ 최대 maxDay`일에 서로 만날 수 있다.

이제 `이분탐색 BinarySearch`를 통하여, `특정 일`뒤에 백조가 서로 만날 수 있는지를 확인한다.

- 특정 일 D에 만날 수 있다면, D를 줄였을 때에도 서로 만날 수 있는지를 확인한다.
- 특정 일 D에 만날 수 없다면, D를 늘려서 그 일자에 서로 만날 수 있는지를 확인한다.

`이분탐색`을 활용하여 백조가 서로 만날 수 있는 `최소 일자`를 구할 수 있다.



## Code

```java
import java.io.*;
import java.util.*;

/**
 *  No.3197: 백조의 호수
 *  Hint: BFS + BinarySearch
 */

public class BOJ3197 {
    static int r, c, maxDay, swanR1, swanC1, swanR2, swanC2;
    static char[][] map;
    static int[][] meltingDay;    // 얼음이 며칠 뒤에 녹는지 저장해 놓은 배열
    static ArrayList<Point> icebergs = new ArrayList<>();   // 얼음 위치를 저장해 놓은 리스트
    static Queue<Point> icebergsNearWater = new ArrayDeque<>();    // 물과 인접한 얼음 위치 큐
    static boolean[][] visited;

    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        map = new char[r][c];
        visited = new boolean[r][c];
        meltingDay = new int[r][c];

        boolean flag = true;
        for (int i = 0; i < r; i++) {
            String str = br.readLine();
            for (int j = 0; j < c; j++) {
                char c = str.charAt(j);
                map[i][j] = c;

                if (c == 'X') {
                    icebergs.add(new Point(i, j));
                } else if (c == 'L') {
                    if (flag) {
                        swanR1 = i;
                        swanC1 = j;
                        flag = false;
                    } else {
                        swanR2 = i;
                        swanC2 = j;
                    }
                }
            }
        }

        bw.write(String.valueOf(solve()));
        bw.close();
        br.close();
    }

    static int solve() {
        // 얼음이 며칠 안에 녹는지 전처리
        findIcebergsNearWater();
        findMeltingDay();

        return binarySearch(0, maxDay);
    }

    // 이분 탐색을 이용하여 백조가 서로 만날 최소 소요일을 찾음
    static int binarySearch(int left, int right) {
        int res = 0;
        while (left <= right) {
            int mid = (left + right) / 2;

            if (bfs(mid)) { // mid 일 뒤에 백조가 만날 수 있으면
                right = mid - 1;
                res = mid;
            } else {    // mid 일 뒤에 백조가 만날 수 없으면
                left = mid + 1;
            }
        }
        return res;
    }

    // day 일 이후 두 백조가 만날 수 있는지 판단
    static boolean bfs(int day){
        Queue<Point> q = new ArrayDeque<>();
        initVisitedArrays();

        q.offer(new Point(swanR1, swanC1));
        visited[swanR1][swanC1] = true;
        while (!q.isEmpty()) {
            Point cur = q.poll();

            if (cur.r == swanR2 && cur.c == swanC2) {
                return true;
            }

            for (int i = 0; i < 4; i++) {
                int nr = cur.r + dr[i];
                int nc = cur.c + dc[i];

                if (isInRange(nr, nc) && !visited[nr][nc] && meltingDay[nr][nc] <= day) {
                    q.offer(new Point(nr, nc));
                    visited[nr][nc] = true;
                }
            }
        }
        
        return false;
    }

    // 물과 접해 있는 iceberg 로 모든 iceberg 가 며칠 뒤에 녹는지 탐색
    static void findMeltingDay() {
        while (!icebergsNearWater.isEmpty()) {
            Point cur = icebergsNearWater.poll();
            for (int i = 0; i < 4; i++) {
                int nr = cur.r + dr[i];
                int nc = cur.c + dc[i];

                if (isInRange(nr, nc) && map[nr][nc] == 'X' && meltingDay[nr][nc] == 0) {
                    icebergsNearWater.offer(new Point(nr, nc));
                    meltingDay[nr][nc] = meltingDay[cur.r][cur.c] + 1;
                    maxDay = Math.max(maxDay, meltingDay[nr][nc]);
                }
            }
        }
    }

    // 물과 접해 있는 iceberg 찾는 메소드
    static void findIcebergsNearWater() {
        for (Point iceberg : icebergs) {
            if (isNearWater(iceberg)) {
                icebergsNearWater.offer(iceberg);
                meltingDay[iceberg.r][iceberg.c] = 1;
            }
        }
    }

    // 해당 iceberg가 물과 인접해 있는지 판단
    static boolean isNearWater(Point iceberg) {
        for (int i = 0; i < 4; i++) {
            int nr = iceberg.r + dr[i];
            int nc = iceberg.c + dc[i];

            if (isInRange(nr, nc) && map[nr][nc] != 'X') {
                return true;
            }
        }
        return false;
    }

    // 호수 범위 체크
    static boolean isInRange(int x, int y) {
        if (x >= 0 && x < r && y >= 0 && y < c) {
            return true;
        }
        return false;
    }

    // visited 배열 초기화
    static void initVisitedArrays() {
        for (boolean[] tmp : visited) {
            Arrays.fill(tmp, false);
        }
    }

    static class Point{
        int r, c;

        Point(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }
}
```

