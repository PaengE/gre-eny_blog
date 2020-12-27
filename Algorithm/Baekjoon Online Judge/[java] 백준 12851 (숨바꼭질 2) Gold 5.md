## 문제 원문 링크 : https://www.acmicpc.net/problem/12851

## Approach

대표적인 BFS 문제로, N to K의 최단시간과 최단시간으로 가는 방법을 구하는 문제이다.

> < 문제의 조건 >
>
> 현재 위치가 X 일 때, 1초 후에 X - 1 또는 X + 1 또는 2 * X 위치로 갈 수 있다.

처음 큐에 N을 넣은 후 하나씩 뺀다. 뺀 수에 +1, -1, *2를 하여 범위(0 ~ 100,000)를 넘지 않는다면 큐에 넣는다.

뺀 수가 K와 같고, 최단거리일 경우에 count를 1씩 증가시킨다.

루프의 빠른 종료를 위하여 최단시간보다 더 길다면, 큐에 추가하지 않도록 처리하였다.

## Code

```java
import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 *  No.12851: 숨바꼭질 2
 *  Hint: BFS
 */

public class BOJ12851 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int size = Math.max(n, k) * 2 + 1;
        int[] dp = new int[size];
        Arrays.fill(dp, -1);

        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{n, 0});

        int count = 0;
        dp[n] = 0;
        while (!q.isEmpty()) {
            int[] t = q.poll();
            int a = t[0];
            int b = t[1];

          // 빼낸 수가 k라면
            if (a == k) {
              // 처음 k에 도달했을 때,
                if (dp[a] == -1) {
                    count += 1;
                } 
              // 최단시간으로 k에 도착했을 때,
              else if (dp[a] == b) {
                    count += 1;
                }
            }

          // 기존에 저장된 최단시간보다 긴 시간으로 도착한다면 
            if (dp[a] != -1 && dp[a] < b) {
                continue;
            }
            dp[a] = b;

            if (a - 1 >= 0) {
                q.offer(new int[]{a - 1, b + 1});
            }
            if (a + 1 < size) {
                q.offer(new int[]{a + 1, b + 1});
            }
            if (a * 2 < size) {
                q.offer(new int[]{a * 2, b + 1});
            }

        }

        bw.write(String.valueOf(dp[k]) + "\n" + String.valueOf(count));
        bw.close();
        br.close();
    }
}

```

