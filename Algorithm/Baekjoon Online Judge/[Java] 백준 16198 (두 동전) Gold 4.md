## Problem : https://www.acmicpc.net/problem/16197

## Approach

> `백트랙킹(Backtracking)`을 이용한 `완전탐색(BruteForce)`문제 이다.
>
> BFS로도 풀이가 가능할 것 같다.

먼저 두개 동전의 위치를 찾는다. (입력으로 map을 구성하면서 찾을 수 있다.)

그리고 다음 작업을 반복한다.

- 두 동전을 상하좌우 방향으로 굴려본다. 굴렸을 때 3가지 상황이 발생한다.
  - 두 동전이 모두 떨어지면 더 이상 굴려보지 않는다. (돌아간다. (백트랙킹))
  - 둘 중 하나의 동전이 떨어지면, 문제가 원하는 답이므로 탐색을 멈춘다.
  - 두 동전 모두 떨어지지 않으면, 그 자리에서 다시 상하좌우 방향으로 동전을 굴린다.

하나의 동전만 떨어뜨리는 방법은 여러가지가 있을 수 있다. 

그 중 최소로 동전을 굴려서 하나만 떨구기를 원하기 때문에 현재 동전을 굴렸을 때 그 횟수가 이미 구한 최소횟수 이상이면, 거기서부터 동전을 더 굴리는 것은 의미가 없다.

또한, 문제에서 제한을 걸어둔 것처럼 굴린 횟수가 10번 이상이라면 또한 굴리는 행위를 멈춘다.

## Code

```java
import java.io.*;
import java.util.StringTokenizer;

/**
 *  No.16197: 두 동전
 *  URL: https://www.acmicpc.net/problem/16197
 *  Hint: Backtracking
 */

public class BOJ16197 {
    static int n, m, ans = Integer.MAX_VALUE;
    static char[][] map;
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        map = new char[n][m];
        boolean flag = false;
        int x1 = 0, y1 = 0, x2 = 0, y2 = 0;

        // 코인 두개의 위치 찾기
        for (int i = 0; i < n; i++) {
            String s = br.readLine();
            for (int j = 0; j < m; j++) {
                map[i][j] = s.charAt(j);
                if (map[i][j] == 'o') {
                    if (flag) {
                        x2 = i;
                        y2 = j;
                    } else {
                        x1 = i;
                        y1 = j;
                        flag = true;
                    }
                }
            }
        }

        backtracking(x1, y1, x2, y2, 0);
        if (ans == Integer.MAX_VALUE) {
            bw.write("-1");
        } else {
            bw.write(String.valueOf(ans));
        }
        bw.close();
        br.close();
    }

    static void backtracking(int x1, int y1, int x2, int y2, int cnt) {
        // ans 보다 cnt가 크거나, cnt가 10 이상이면 종료
        if (cnt >= ans || cnt >= 10) {
            return;
        }

        for (int i = 0; i < 4; i++) {
            int nx1 = x1 + dx[i];
            int ny1 = y1 + dy[i];
            int nx2 = x2 + dx[i];
            int ny2 = y2 + dy[i];
            int falling = 0;

            // 해당 코인이 떨어지는지를 판단
            if (!inRange(nx1, ny1)) {
                falling++;
            }
            if (!inRange(nx2, ny2)) {
                falling++;
            }

            // 코인이 두개 다 떨어지면 안됨
            if (falling == 2) {
                continue;
            } else if (falling == 1) {  // 하나만 떨어지면 문제에서 원하는 것
                ans = Math.min(ans, cnt + 1);
            } else {    // 하나도 떨어지지 않았다면
                if (map[nx1][ny1] == '#') {
                    nx1 = x1;
                    ny1 = y1;
                }
                if (map[nx2][ny2] == '#') {
                    nx2 = x2;
                    ny2 = y2;
                }
                // 코인의 위치를 이동시킴
                backtracking(nx1, ny1, nx2, ny2, cnt + 1);
            }
        }
    }

    // 코인이 떨어지면 false, 안떨어지면 true
    static boolean inRange(int x, int y) {
        if (x >= 0 && x < n && y >= 0 && y < m) {
            return true;
        }
        return false;
    }
}
```