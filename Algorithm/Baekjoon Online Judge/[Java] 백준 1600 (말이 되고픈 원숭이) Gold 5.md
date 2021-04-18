## Problem : https://www.acmicpc.net/problem/1600

## Approach

> BFS 문제이다.
>
> 일반적인 BFS 에 `이동할 수 있는 추가적인 방향`과 `이 추가적인 방향을 사용하는데에 있어 제한을 두는 약간의 제약`이 추가되었다.

문제에서 언급하는 `말 이동`은 횟수제한이 있으므로, 해당 지점에 `말 이동`을 몇 번 사용하여 최소 이동으로 도착하였는지를 저장하여야 한다. (이 때문에 visited 배열을 3차원으로 선언하였다.)

그 이외에는 BFS의 유형에서 크게 벗어나지 않으므로 쉬울 것이다.



나는 처음 풀이 때, Queue에 들어가는 요소들의 자료형을 `int[]` 로 하였다. 하지만 이 방법은 어쩐일인지 `메모리 초과 에러`를 내뱉었고, 다른 코드들을 참고한 결과 Queue에 들어가는 요소들의 자료형을 따로 클래스로 만들어 처리하니 `AC`를 받을 수 있었다. (아래 코드의 42 line 부분이다.)

## Code

```java
import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 *  No.1600: 말이 되고픈 원숭이
 *  URL: https://www.acmicpc.net/problem/1600
 *  Hint: BFS + 큐의 데이터 타입은 int[]가 아닌 별도의 클래스로 해야 메모리초과가 안남.
 */

public class BOJ1600 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int k = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int w = Integer.parseInt(st.nextToken());
        int h = Integer.parseInt(st.nextToken());
        int[][] arr = new int[h][w];

        for (int i = 0; i < h; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < w; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int ans = bfs(k, w, h, arr);
        bw.write(String.valueOf(ans));
        bw.close();
        br.close();
    }

    static int bfs(int k, int w, int h, int[][] arr) {
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        int[] hr = {-2, -2, -1, -1, 1, 1, 2, 2};
        int[] hc = {1, -1, 2, -2, 2, -2, 1, -1};

        boolean[][][] visited = new boolean[h][w][k + 1];
        Queue<Monkey> q = new LinkedList<>();
        q.offer(new Monkey(0, 0, 0, 0));
        visited[0][0][0] = true;
        int res = -1;

        while (!q.isEmpty()) {
            Monkey cur = q.poll();
            if (cur.r == h - 1 && cur.c == w - 1) {
                return cur.cnt;
            }

            // 상하좌우 이동
            for (int i = 0; i < 4; i++) {
                int nr = cur.r + dr[i];
                int nc = cur.c + dc[i];
                if (isPossible(nr, nc, h, w) && arr[nr][nc] == 0 && !visited[nr][nc][cur.k]) {
                    q.offer(new Monkey(nr, nc, cur.k, cur.cnt + 1));
                    visited[nr][nc][cur.k] = true;
                }
            }

            // 말 이동을 k번 했으면 더이상 말 이동은 불가능
            if (cur.k == k) {
                continue;
            }

            // 말 이동
            for (int i = 0; i < 8; i++) {
                int nr = cur.r + hr[i];
                int nc = cur.c + hc[i];
                if (isPossible(nr, nc, h, w) && arr[nr][nc] == 0 && !visited[nr][nc][cur.k + 1]) {
                    q.offer(new Monkey(nr, nc, cur.k + 1, cur.cnt + 1));
                    visited[nr][nc][cur.k + 1] = true;
                }
            }
        }
        return res;
    }

    static boolean isPossible(int x, int y, int h, int w) {
        if (x >= 0 && x < h && y >= 0 && y < w) {
            return true;
        } else {
            return false;
        }
    }

    static class Monkey {
        int r, c, k, cnt;

        Monkey(int r, int c, int k, int cnt) {
            this.r = r;
            this.c = c;
            this.k = k;
            this.cnt = cnt;
        }
    }
}
```

