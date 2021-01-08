## 문제 원문 링크: https://www.acmicpc.net/problem/12852

## Approach

~~DP 문제이다.~~

~~DP[i] = i를 만들 수 있는 최소 연산 수. 라고 하였을 때,~~

~~i 를 만들 수 있는 가짓 수는 다음 3가지이다. 이 중 최소 연산 수를 DP[i]에 저장하면 된다.~~

​		~~`1. i - 1 숫자에서 +1 을 하여 i를 만드는 경우`~~

​		~~`2. i / 2 숫자에서 *2 를 하여 i를 만드는 경우 `~~

​		~~`3. i / 3 숫자에서 *3 을 하여 i를 만드는 경우`~~

~~위의 규칙으로 밑의 점화식을 도출할 수 있다.~~

> ​		~~DP[i] = min(DP[i - 1], DP[i / 2], DP[i / 3])~~

~~위의 식은 Bottom-up 방식의 점화식이다.~~ 

~~i가 2나 3으로 나누어 떨어질 경우에만 나눠야 하므로 위의 식에 조건을 추가하여야 한다.~~

~~추가로 N을 1로 만드는 방법을 출력해야 하므로~~

~~i를 최소 연산 수로 만들 때, 만들기 직전 숫자를 기록하여야 한다.~~

BOJ 사이트에서 재채점 이후 맞았습니다 -> 틀렸습니다가 되었다... 내 풀이가 아마 틀렸던 것 같다. 

BFS로 다시 풀었더니 코드가 통과되었다.

## Code

```java
import java.io.*;

/**
 * No.12852: 1로 만들기 2
 * Description: 1로 만드는 최적해를 출력하는 문제
 * URL: https://www.acmicpc.net/problem/12852
 * Hint: 최적 해로 온 길을 저장함
 */

public class BOJ12852 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        n = Integer.parseInt(br.readLine());
        visited = new boolean[n + 1];
        dp = new int[n + 1];
        pre = new int[n + 1];

        bfs(1);

        bw.write(dp[n] + "\n");

        StringBuilder sb = new StringBuilder();
        while (pre[n] != -1) {
            sb.append(n + " ");
            n = pre[n];
        }
        bw.write(sb.append(1).toString());

        bw.flush();
        bw.close();
        br.close();
    }

    // 1부터 n까지 가는 최소비용을 구함
    static void bfs(int start) {
        Queue<Integer> q = new LinkedList<>();
        q.offer(start);
        visited[start] = true;
        pre[start] = -1;
        dp[start] = 0;

        // 큐에서 하나씩 뺀 후 bfs 진행
        while (!q.isEmpty()) {
            int cur = q.poll();

            if (cur > n) {
                continue;
            }

            if (cur == n) {
                break;
            }

            // 범위를 벗어나는지, 방문했었는지를 검사후 큐에 삽입
            if (cur * 3 <= n && !visited[cur * 3]) {
                q.offer(cur * 3);
                pre[cur * 3] = cur;
                dp[cur * 3] = dp[cur] + 1;
                visited[cur * 3] = true;
            }
            if (cur * 2 <= n && !visited[cur * 2]) {
                q.offer(cur * 2);
                pre[cur * 2] = cur;
                dp[cur * 2] = dp[cur] + 1;
                visited[cur * 2] = true;
            }
            if (cur + 1 <= n && !visited[cur + 1]) {
                q.offer(cur + 1);
                pre[cur + 1] = cur;
                dp[cur + 1] = dp[cur] + 1;
                visited[cur + 1] = true;
            }
        }
    }
}

/*	재채점 이후 맞았습니다. -> 틀렸습니다. 코드

				int n = Integer.parseInt(br.readLine());
        int[][] dp = new int[n + 1][2];

        if (n <= 3) {
            bw.write("1\n");
            bw.write(String.valueOf(n) + " 1\n");
        } else {
            dp[2][0] = 1; dp[2][1] = 1;
            dp[3][0] = 1; dp[3][1] = 1;

            for (int i = 4; i <= n; i++) {
              // 일단 i - 1 에서 i를 만드는 경우의 최소연산수를 저장
                dp[i][0] = dp[i - 1][0] + 1;
                dp[i][1] = i - 1;

              // i가 3으로 나누어 떨어진다면 i / 3 에서 i를 만드는 경우의 최소연산수를 저장
                if (i % 3 == 0) {
                    if (dp[i / 3][0] + 1 < dp[i][0]) {
                        dp[i][0] = dp[i / 3][0] + 1;
                        dp[i][1] = i / 3;
                    }
                }

              // i가 2으로 나누어 떨어진다면 i / 2 에서 i를 만드는 경우의 최소연산수를 저장
                if (i % 2 == 0) {
                    if (dp[i / 2][0] + 1 < dp[i][0]) {
                        dp[i][0] = dp[i / 2][0] + 1;
                        dp[i][1] = i / 2;
                    }
                }
            }

            bw.write(String.valueOf(dp[n][0]));
            bw.newLine();
            bw.write(String.valueOf(n) + " ");

            while (dp[n][1] != 0) {
                bw.write(String.valueOf(dp[n][1] + " "));
                n = dp[n][1];
            }
        }
        
*/
```

​	