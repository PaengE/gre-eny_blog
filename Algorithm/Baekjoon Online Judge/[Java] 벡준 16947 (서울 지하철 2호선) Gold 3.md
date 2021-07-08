## Problem : https://www.acmicpc.net/problem/16947

## Approach

> 그래프 정보가 주어졌을 때, `사이클`을 구하고, 그 사이클로부터 얼마나 멀리 떨어져 있는지를 구하는 문제였다.

먼저 주의할 점은, 노드의 개수가 N개이고, 간선의 개수도 N개이므로, 사이클(순환선)은 무조건 1개 존재한다는 것이다.

문제 풀이의 순서는 다음과 같다.

- 먼저 사이클 찾은 뒤, 사이클에 속하는 노드들을 표시한다. (cycle 배열에)
- 사이클에 속하는 노드들을 시작점으로 BFS를 수행한다.

## Code

```java
import java.io.*;
import java.util.*;

/**
 *  No.16947: 서울 지하철 2호선
 *  URL: https://www.acmicpc.net/problem/16947
 *  Hint: 사이클 체크(DFS) + BFS
 */

public class BOJ16947 {
    static int n;
    static int[] ans;
    static boolean[] visited, cycle;
    static ArrayList<Integer>[] list;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        n = Integer.parseInt(br.readLine());
        visited = new boolean[n + 1];
        cycle = new boolean[n + 1];
        ans = new int[n + 1];
        list = new ArrayList[n + 1];
        for (int i = 0; i <= n; i++) {
            list[i] = new ArrayList<>();
        }

        // 그래프 구성
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            list[a].add(b);
            list[b].add(a);
        }

        // 하나있는 순환선 찾기 (순환선은 무조건 하나임 - 노드가 n개이고, 간선이 n개이므로)
        for (int i = 1; i <= n; i++) {
            if (isCycle(-1, i, i)) {
                break;
            } else {
                Arrays.fill(visited, false);
            }
        }

        bfs();

        for (int i = 1; i <= n; i++) {
            bw.write(ans[i] + " ");
        }
        bw.close();
        br.close();
    }

    // BFS를 이용하여 순환선과의 거리 계산
    static void bfs() {
        Queue<Integer> q = new ArrayDeque<>();
        Arrays.fill(visited, false);

        // 순환선 포함된 노드를 기준으로 BFS 수행
        for (int i = 1; i <= n; i++) {
            if (cycle[i]) {
                visited[i] = true;
                q.offer(i);
            }
        }

        while (!q.isEmpty()) {
            int cur = q.poll();

            for (int next : list[cur]) {
                if (!visited[next]) {
                    q.offer(next);
                    visited[next] = true;
                    ans[next] = ans[cur] + 1;
                }
            }
        }
    }

    // 사이클인지를 판단
    static boolean isCycle(int parent, int cur, int start) {
        visited[cur] = true;

        for (int next : list[cur]) {
            if (!visited[next]) {
                if (isCycle(cur, next, start)) {
                    cycle[next] = true;
                    return true;
                }
            } else if (parent != next && next == start) {   // 사이클의 끝
                cycle[next] = true;
                return true;
            }
        }
        return false;
    }
}
```

