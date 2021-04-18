## Problem : https://www.acmicpc.net/problem/1987

## Approach

> 이미 지나온 알파벳은 지날 수 없는 `DFS` 문제이다. 

이미 지나온 알파벳은 지날 수 없다는 것을 확인하기 위해 visited 배열과 더불어 alpha 배열을 따로 두었다.

풀이 방법은 다음과 같다.

- 좌측 상단(0, 0)에서 시작하여 DFS를 수행한다.
- DFS 를 수행하며 지나온 알파벳을 기록한다. 이미 지나온 알파벳이라면 그 칸으로는 더이상 탐색하지 않는다.

`재귀의 최대 깊이`가 정답이다.

## Code

```java
import java.io.*;
import java.util.StringTokenizer;

/**
 *  No.1987: 알파벳
 *  URL: https://www.acmicpc.net/problem/1987
 *  Hint: DFS
 */

public class BOJ1987 {
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};
    static boolean[] alpha = new boolean[26];
    static char[][] way;
    static boolean[][] visited;
    static int r, c, ans = Integer.MIN_VALUE;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        way = new char[r][c];
        visited = new boolean[r][c];

        for (int i = 0; i < r; i++) {
            way[i] = br.readLine().toCharArray();
        }

        visited[0][0] = true;
        alpha[way[0][0] - 'A'] = true;
        dfs(0, 0, 1);

        bw.write(String.valueOf(ans));
        bw.close();
        br.close();
    }

    static void dfs(int cx, int cy, int cnt) {
        ans = Math.max(ans, cnt);

        for (int i = 0; i < 4; i++) {
            int nx = cx + dx[i];
            int ny = cy + dy[i];

            if (isPossible(nx, ny) && !visited[nx][ny] && !alpha[way[nx][ny] - 'A']) {
                visited[nx][ny] = true;
                alpha[way[nx][ny] - 'A'] = true;
                dfs(nx, ny, cnt + 1);
                visited[nx][ny] = false;
                alpha[way[nx][ny] - 'A'] = false;
            }
        }
    }

    static boolean isPossible(int x, int y) {
        if (x >= 0 && x < r && y >= 0 && y < c) {
            return true;
        } else {
            return false;
        }
    }
}
```

