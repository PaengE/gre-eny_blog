## Problem : https://www.acmicpc.net/problem/13460

## Approach

> 삼성 SW 역량테스트 문제라고 한다. 삼성에서는 이런 구현(시뮬레이션) 문제를 많이 내는 것 같다.
>
> BFS 를 사용하여 구현하였다. 이 때 visited 배열이 4차원 배열인 것을 주의하자.
>
> visited[blueX][blueY][redX][redY] 의 형태로 구성하였다.

일단 입력 데이터를 처리하면서, 초기 파란 구슬과 빨간 구슬의 위치를 저장한다.

그리고 다음 과정을 반복한다. (4방향에 대해서 각각)

- 일단 파란 구슬을 굴린다. (파란 구슬이 구멍에 빠지면 다음 방향을 탐색한다.)
- 이후 빨간 구슬을 굴린다. (빨간 구슬이 구멍에 빠지면 그 때의 굴린 횟수(count)를 리턴한다.)
  - 파란 구슬을 먼저 굴린 후 구멍에 빠지면, 빨간 구슬을 굴리기 때문에 동시에 들어가는 경우는 없다.
- 두 구슬을 모두 굴린 후, 두 구슬의 위치가 같다면, 위치를 재조정 해준다. (그리고 큐에 넣는다.)

굴리는 횟수가 10번을 넘어가면 -1을 리턴하도록 하고, 더 굴릴 필요가 없으면(큐가 비어있으면) 마찬가지로 -1을 리턴하도록 한다.



이런 구현 문제는 키보드에서 손을 떼고 로직을 생각한 뒤, 단계를 나눠서 천천히 진행하는 것이 좋은 것 같다. 

## Code

```java
import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 *  No.13460: 구슬 탈출 2
 *  URL: https://www.acmicpc.net/problem/13460
 *  Hint: 구현 + 시뮬레이션 + BFS
 */

public class BOJ13460 {
    static int n, m;
    static char[][] map;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        map = new char[n][m];

        int bx = 0, by = 0, rx = 0, ry = 0;
        for (int i = 0; i < n; i++) {
            String s = br.readLine();
            for (int j = 0; j < m; j++) {
                map[i][j] = s.charAt(j);

                if (map[i][j] == 'B') {
                    bx = i;
                    by = j;
                } else if (map[i][j] == 'R') {
                    rx = i;
                    ry = j;
                }
            }
        }

        int ans = bfs(bx, by, rx, ry);

        bw.write(String.valueOf(ans));
        bw.close();
        br.close();
    }

    static int bfs(int bx, int by, int rx, int ry) {
        int[] dx = {0, 0, -1, 1};   // 좌, 우, 상, 하
        int[] dy = {-1, 1, 0, 0};

        boolean[][][][] visited = new boolean[n][m][n][m];  // [bx][by][rx][ry]
        Queue<Ball> q = new LinkedList<>();
        visited[bx][by][rx][ry] = true;
        q.offer(new Ball(bx, by, rx, ry, 1));

        while (!q.isEmpty()) {
            Ball cur = q.poll();

            if (cur.count > 10) {
                return -1;
            }

            for (int i = 0; i < 4; i++) {
                // 파란공 굴리기
                int nextBlueX = cur.blueX + dx[i];
                int nextBlueY = cur.blueY + dy[i];
                boolean blueCanMove = true;
                while (map[nextBlueX][nextBlueY] != '#') {  // 벽을 만날 때까지
                    if (map[nextBlueX][nextBlueY] == 'O') {
                        blueCanMove = false;
                        break;
                    }
                    nextBlueX += dx[i];
                    nextBlueY += dy[i];
                }   // 최종 nextBlueX, nextBlueY 는 만난 벽의 위치
                // 따라서 바로 직전위치로 회귀
                nextBlueX -= dx[i];
                nextBlueY -= dy[i];

                // 파란공이 구멍에 빠지면 continue
                if (!blueCanMove) {
                    continue;
                }

                // 빨간공 굴리기
                int nextRedX = cur.redX + dx[i];
                int nextRedY = cur.redY + dy[i];

                while (map[nextRedX][nextRedY] != '#') {    // 벽을 만날 때까지
                    if (map[nextRedX][nextRedY] == 'O') {
                        return cur.count;
                    }
                    nextRedX += dx[i];
                    nextRedY += dy[i];
                }   // 최종 nextRedX, nextRedY 는 만난 벽의 위치
                // 따라서 바로 직전위치로 회귀
                nextRedX -= dx[i];
                nextRedY -= dy[i];

                // 두 공 모두 굴린 후, 위치가 같으면 위치 재조정
                if (nextBlueX == nextRedX && nextBlueY == nextRedY) {
                    switch (i) {
                        case 0: // 좌로 굴렸을 때
                            if (cur.blueY < cur.redY) {
                                nextRedY++;
                            } else {
                                nextBlueY++;
                            }
                            break;
                        case 1: // 우로 굴렸을 때
                            if (cur.blueY < cur.redY) {
                                nextBlueY--;
                            } else {
                                nextRedY--;
                            }
                            break;
                        case 2: // 위로 굴렸을 때
                            if (cur.blueX < cur.redX) {
                                nextRedX++;
                            } else {
                                nextBlueX++;
                            }
                            break;
                        case 3: // 아래로 굴렸을 때
                            if (cur.blueX < cur.redX) {
                                nextBlueX--;
                            } else {
                                nextRedX--;
                            }
                            break;
                    }
                }

                if (!visited[nextBlueX][nextBlueY][nextRedX][nextRedY]) {
                    q.offer(new Ball(nextBlueX, nextBlueY, nextRedX, nextRedY, cur.count + 1));
                    visited[nextBlueX][nextBlueY][nextRedX][nextRedY] = true;
                }
            }
        }

        return -1;
    }


    static class Ball {
        int blueX, blueY, redX, redY, count;

        Ball(int blueX, int blueY, int redX, int redY, int count) {
            this.blueX = blueX;
            this.blueY = blueY;
            this.redX = redX;
            this.redY = redY;
            this.count = count;
        }
    }
}
```

