## Problem : https://www.acmicpc.net/problem/1941



## Approach

> `조합을 이용한 BruteForce`방식으로 `BFS/DFS`를 진행해야 하는 문제이다.



문제의 주요 풀이 로직은 다음과 같다.

- 25명 중 `조합 선택`을 이용하여 (Y가 3이하인) 7명을 구한다.
- 그 7명이 인접하여 있는지 판단한다.



`조합 선택`은 `Backtracking(DFS)`기법을 사용하여 조합을 구성하였다.

인접하여 있는지는 `BFS`로 판단하였다.



## Code

```java
import java.io.*;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/**
 *  No.1941: 소문난 칠공주
 *  Hint: BruteForce(조합 Backtracking) + DFS + BFS
 */

public class BOJ1941 {
    static char[] places = new char[25];
    static boolean[] selected = new boolean[25];
    static boolean[] visited = new boolean[25];
    static int ans;

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        for (int i = 0; i < 5; i++) {
            String str = br.readLine();
            for (int j = 0; j < 5; j++) {
                places[i * 5 + j] = str.charAt(j);
            }
        }

        combination(0, 0, 0);
        bw.write(String.valueOf(ans));
        bw.close();
        br.close();
    }

    // 25명 중 7명을 뽑는 조합 (DFS)
    static void combination(int depth, int idx, int yCnt) {
        if (yCnt > 3) { // Y의 개수가 3초과 하면 안됨
            return;
        }

        if (depth == 7) {   // 7명을 다 뽑았으면
            // 마지막으로 뽑힌 사람을 시작점으로 서로 연결되어 있는지를 판단 (BFS)
            if (checkTheyAreConnected(idx - 1)) {
                ans++;
            }

            return;
        }

        for (int i = idx; i < 25; i++) {
            selected[i] = true;
            if (places[i] == 'Y') {
                combination(depth + 1, i + 1, yCnt + 1);
            } else {
                combination(depth + 1, i + 1, yCnt);
            }
            selected[i] = false;
        }
    }

    // 서로 연결되어 있는지를 판단 (BFS)
    static boolean checkTheyAreConnected(int start) {
        Arrays.fill(visited, false);
        Queue<Integer> q = new ArrayDeque<>();

        q.offer(start);
        visited[start] = true;
        int cnt = 1;

        while (!q.isEmpty()) {
            int cur = q.poll();
            int cx = cur / 5;
            int cy = cur % 5;

            for (int i = 0; i < 4; i++) {
                int nx = cx + dx[i];
                int ny = cy + dy[i];

                if (inRange(nx, ny)) {
                    int next = nx * 5 + ny;
                    if (selected[next] && !visited[next]) {
                        q.offer(next);
                        visited[next] = true;
                        cnt++;
                    }
                }
            }
        }

        if (cnt == 7) {
            return true;
        }
        return false;
    }

    static boolean inRange(int r, int c) {
        if (r >= 0 && r < 5 && c >= 0 && c < 5) {
            return true;
        }
        return false;
    }
}
```

