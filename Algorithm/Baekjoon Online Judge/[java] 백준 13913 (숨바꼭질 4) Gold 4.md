## 문제 원문 링크 : https://www.acmicpc.net/problem/13913

## Approach

대표적인 BFS문제로, 최단시간을 이루는 경로 또한 구해야 하는 문제이다.

> < 문제의 조건 >
>
> 현재 위치가 X 일 때, 1초 후에 X - 1 또는 X + 1 또는 2 * X 위치로 갈 수 있다.

처음 큐에 N을 넣은 후 하나씩 뺀다. 뺀 수에 +1, -1, *2를 하여 범위(0 ~ 100,000)를 넘지 않는다면 큐에 넣는다.

루프를 진행하면서 pre[] 배열에 현재 위치 최단시간으로 오는 직전 위치를 기록해주고, 나중에 Stack에 넣어 처리해준다. 다 넣은 후, 하나씩 빼서 출력해주면 된다.

## Code

```java
import java.io.*;
import java.util.*;

/**
 *  No.13913: 숨바꼭질 4
 *  URL: https://www.acmicpc.net/problem/13913
 *  Description: BFS 최단경로를 출력하는 문제
 *  Hint: BFS + 경로 역추적
 */

public class BOJ13913 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int[] dp = new int[Math.max(n, k) * 2 + 1];
        int[] pre = new int[Math.max(n, k) * 2 + 1];
        boolean[] visited = new boolean[Math.max(n, k) * 2 + 1];
        Arrays.fill(pre, -1);

        bfs(n, k, dp, pre, visited);

        bw.write(String.valueOf(dp[k]) + "\n");

        Stack<Integer> stack = new Stack<>();
        stack.push(k);
        while (pre[k] != -1) {
            stack.push(pre[k]);
            k = pre[k];
        }

        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop() + " ");
        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();

    }
    static void bfs(int n, int k, int[] arr, int[] pre, boolean[] visited) {
        Queue<Integer> q = new LinkedList<Integer>();
        int limit = Math.max(n, k);
        q.offer(n);
        visited[n] = true;

        while (!q.isEmpty()) {
            int cur = q.poll();

            for (int i = 0; i < 3; i++) {
                int next = 0;
                if (i == 0) {
                    next = cur - 1;
                } else if (i == 1) {
                    next = cur + 1;
                } else {
                    next = 2 * cur;
                }

                if (next >= 0 && next < 2 * limit && !visited[next]) {
                    q.offer(next);
                    visited[next] = true;
                    arr[next] = arr[cur] + 1;
                  // 직전 위치를 기록
                    pre[next] = cur;
                }
            }
        }
    }
}

```

