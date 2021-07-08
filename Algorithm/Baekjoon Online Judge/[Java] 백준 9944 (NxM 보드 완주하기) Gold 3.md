## Problem : https://www.acmicpc.net/problem/9944



## Approach

> `BruteForce완전탐색` + `구현`으로 문제를 풀이하였다.



문제풀이에 앞서 해당 문제는 입력의 끝이 정해져 있지 않다. 해당 내용에 관한 부분은 아래 포스팅을 참고하면 좋을 것이다.

https://gre-eny.tistory.com/307



문제를 푸는 로직은 다음과 같다. `visited[][]`배열은 해당 (x, y) 를 방문했는지 기록하는 배열이다.

`goLeft(), goRight(), goUp(), goDown()`은 XXX 방향으로 최대로 이동했음을 표시하고, 그 위치를 리턴한다.

- 입력 단계에서 빈 칸인 부분은 모두 시작점으로 사용하여야 하기 때문에 `startPoints`에 저장한다. 그리고 방문할 수 있는 모든칸의 개수를 저장한 `size`를 구한다. (빈 칸의 개수 - 1을 한다.)
- 장애물인 부분은 `visited` 배열에 `방문했다`고 기록한다. (실제로 방문하지 않았지만 동일한 기능을 수행한다.)
- `상하좌우`방향으로 방문하지 않은 곳이 있다면, 일직선 상으로 계속 방문한다. 
- 현재 Point의 `fillCount`가 `size`와 같다면 모든 빈 칸을 방문한 것이므로 그 때의 `단계 수 count`를 ans에 `Math.min()` 을 활용하여 갱신한다.

주의할 점은 visited 배열을 `copy`하여서 인자로 넘겨주어야 한다는 것이다. 자바에서는 기본적으로 메소드의 파라미터로 배열을 넘길 때 `얕은 복사 Shallow Copy`가 발생하기 때문이다.



`얕은 복사(Shallow Copy), 깊은 복사(Deep Copy)` 와 관련된 포스팅은 아래 포스팅을 참고하길 바란다.

https://gre-eny.tistory.com/195



## Code

```java
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 *  No.9944: NxM 보드 완주하기
 *  Hint: BruteForce + 구현
 */

public class BOJ9944 {
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};
    static int n, m, ans;
    static int size;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringBuilder sb = new StringBuilder();
        int tc = 1;
        String s = "";
        while ((s = br.readLine()) != null && !s.isEmpty()) {
            StringTokenizer st = new StringTokenizer(s);
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
            boolean[][] visited = new boolean[n][m];
            List<Point> startPoints = new ArrayList<>();
            ans = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                s = br.readLine();
                for (int j = 0; j < m; j++) {
                    if (s.charAt(j) == '*') {
                        visited[i][j] = true;
                    } else {
                        startPoints.add(new Point(i, j, 0));
                    }
                }
            }

            size = startPoints.size() - 1;	// size는 방문해야 할 곳의 개수 - 1(자기자신 제외)
            for (Point start : startPoints) {
                boolean[][] tmp = copyArrays(visited);
                tmp[start.x][start.y] = true;
                solve(start, tmp, 0);
            }

            ans = ans == Integer.MAX_VALUE ? -1 : ans;
            sb.append("Case " + tc++ + ": " + ans + "\n");
        }

        bw.write(sb.toString());
        bw.close();
        br.close();

    }

  // cur: 현재위치, cnt: 단계 수
    static void solve(Point cur, boolean[][] visited, int cnt) {
        if (cur.fillCount == size) {
            ans = Math.min(ans, cnt);
            return;
        }

        for (int i = 0; i < 4; i++) {
            int nx = cur.x + dx[i];
            int ny = cur.y + dy[i];

            if (inRange(nx, ny) && !visited[nx][ny]) {
                boolean[][] tmp = copyArrays(visited);
                Point nextStartPoint;
                switch (i) {
                    case 0:
                        nextStartPoint = goUp(cur, tmp);
                        solve(nextStartPoint, tmp, cnt + 1);
                        break;
                    case 1:
                        nextStartPoint = goDown(cur, tmp);
                        solve(nextStartPoint, tmp, cnt + 1);
                        break;

                    case 2:
                        nextStartPoint = goLeft(cur, tmp);
                        solve(nextStartPoint, tmp, cnt + 1);
                        break;

                    case 3:
                        nextStartPoint = goRight(cur, tmp);
                        solve(nextStartPoint, tmp, cnt + 1);
                        break;
                }
            }
        }
    }

    // 왼쪽으로 방문하지 않은 최대 위치를 반환
    static Point goLeft(Point cur, boolean[][] visited) {

        for (int i = cur.y - 1; i >= 0; i--) {
            if (visited[cur.x][i]) {
                return new Point(cur.x, i + 1, cur.fillCount + (cur.y - (i + 1)));
            }
            visited[cur.x][i] = true;
        }

        return new Point(cur.x, 0, cur.fillCount + cur.y);
    }

    // 오른쪽으로 방문하지 않은 최대 위치를 반환
    static Point goRight(Point cur, boolean[][] visited) {

        for (int i = cur.y + 1; i < m; i++) {
            if (visited[cur.x][i]) {
                return new Point(cur.x, i - 1, cur.fillCount + (i - 1) - cur.y);
            }
            visited[cur.x][i] = true;
        }

        return new Point(cur.x, m - 1, cur.fillCount + (m - 1) - cur.y);
    }

    // 위쪽으로 방문하지 않은 최대 위치를 반환
    static Point goUp(Point cur, boolean[][] visited) {

        for (int i = cur.x - 1; i >= 0; i--) {
            if (visited[i][cur.y]) {
                return new Point(i + 1, cur.y, cur.fillCount + cur.x - (i + 1));
            }
            visited[i][cur.y] = true;
        }

        return new Point(0, cur.y, cur.fillCount + cur.x);
    }

    // 아래쪽으로 방문하지 않은 최대 위치를 반환
    static Point goDown(Point cur, boolean[][] visited) {

        for (int i = cur.x + 1; i < n; i++) {
            if (visited[i][cur.y]) {
                return new Point(i - 1, cur.y, cur.fillCount + ((i - 1) - cur.x));
            }
            visited[i][cur.y] = true;
        }

        return new Point(n - 1, cur.y, cur.fillCount + (n - 1) - cur.x);
    }

    static boolean inRange(int x, int y) {
        if (x >= 0 && x < n && y >= 0 && y < m) {
            return true;
        }
        return false;
    }

    static boolean[][] copyArrays(boolean[][] arr) {
        boolean[][] copy = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                copy[i][j] = arr[i][j];
            }
        }
        return copy;
    }

    static class Point{
        int x, y, fillCount;	// 현재 위치에서 방문한 곳의 개수

        Point(int x, int y, int fillCount) {
            this.x = x;
            this.y = y;
            this.fillCount = fillCount;
        }
    }
}
```

