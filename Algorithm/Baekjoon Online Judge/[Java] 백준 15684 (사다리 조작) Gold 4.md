## Problem : https://www.acmicpc.net/problem/15684



## Approach

> `백트랙킹 Backtracking`을 활용하여 `BruteForce 완전탐색`으로 풀이하였다.



가로선을 놓을 수 있는 개수는 `0, 1, 2, 3`개 이다. 이를 이용하여 문제를 푸는 주요 로직은 다음과 같다.

- 가로선을 놓을 수 있는 개수만큼 가로선을 놓아본다.
- 이 때, A 번째 사다리가 A 에 도착하는지를 검사한다.
- 모든 경우의 수에 대하여 위 과정을 수행한다.

생각하기엔 쉬웠지만 구현이 빡셌던 문제였다. `두번째 과정`을 구현하는 데에 시간이 많이 소요되었다.



## Code

```java
import java.io.*;
import java.util.StringTokenizer;

/**
 *  No.15684: 사다리 조작
 *  Hint: BruteForce + Backtracking
 */

public class BOJ15684 {
    static int n, m, h, ans;
    static boolean flag;
    static int[][] arr; // 1이면 오른쪽으로 이동, 2면 왼쪽으로 이동
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        h = Integer.parseInt(st.nextToken());
        arr = new int[h + 1][n + 1];

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            arr[a][b] = 1;
            arr[a][b + 1] = 2;
        }

        // 추가할 가로선을 0 ~ 3 개 하여 BruteForce
        for (int i = 0; i <= 3; i++) {
            if (!flag) {
                dfs(0, 0, i);
            }
        }

        bw.write(flag ? String.valueOf(ans) : "-1");
        bw.close();
        br.close();
    }

    static void dfs(int depth, int ladder, int limit) {
        if (flag) {
            return;
        }

        if (ladder == limit) {
            if (check()) {
                ans = limit;
                flag = true;
            }
            return;
        }

        // 모든 구간에 가로선을 놓아봄
        for (int i = depth; i <= h; i++) {
            for (int j = 0; j < n; j++) {
                if (arr[i][j] == 0 && arr[i][j + 1] == 0) {
                    arr[i][j] = 1;
                    arr[i][j + 1] = 2;
                    dfs(depth, ladder + 1, limit);
                    arr[i][j] = arr[i][j + 1] = 0;
                }
            }
        }
    }

    // 사다리가 i번째로 시작하여 i번째로 끝나는지 체크
    static boolean check() {
        for (int i = 1; i <= n; i++) {  // i = 시작 사다리
            int x = 1, y = i;   // x = 현재 사다리에서의 높이. y = 현재 사다리의 번호
            for (int j = 0; j < h; j++) {
                if (arr[x][y] == 1) {
                    y++;
                } else if (arr[x][y] == 2) {
                    y--;
                }
                x++;
            }

            if (y != i){
                return false;
            }
        } return true;

    }
}
```

