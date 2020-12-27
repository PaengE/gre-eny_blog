## Problem : https://www.acmicpc.net/problem/7579

## Approach

DP 문제로 냅색(KnapSack) 문제와 유사하다. 

N cost로 최대 몇 M size를 비활성화 할 수 있는지를 고민해서 풀어야 한다.

DP[i][j] = i개의 앱이 있을 때, j cost로 비활성화 할 수 있는 최대 사이즈이다.

그런 후, X개 앱이 있을 때, Y size를 처음으로 넘기는 cost를 찾으면 된다.

## Code

```java
import java.io.*;
import java.util.StringTokenizer;

/**
 * no.7579: 앱
 * description: 발상의 전환이 필요한 냅색 문제
 * hint: N cost 로 최대 몇 M size 를 비활성화 할 수 있는지 고민
 */

public class BOJ7579 {
    static int n, m, maxCost;
    static int[][] arr;
    static long[][] dp;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        arr = new int[n][2];

        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 0; i < n; i++) {
            arr[i][0] = Integer.parseInt(st.nextToken());
        }
        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 0; i < n; i++) {
            arr[i][1] = Integer.parseInt(st.nextToken());
            maxCost += arr[i][1];
        }

        bw.write(String.valueOf(solution()));
        br.close();
        bw.close();
    }

    static long solution() {
        dp = new long[n][maxCost + 1];

        for (int i = 0; i < n; i++) {
            int appSize = arr[i][0];
            int appCost = arr[i][1];

            for (int j = 0; j <= maxCost; j++) {
                // 앱이 하나일 때 dp[][] 초기화
                if (i == 0) {
                    if (appCost <= j) {
                        dp[i][j] = appSize;
                    }
                } else {
                    if (appCost <= j) {
                        dp[i][j] = Math.max(dp[i - 1][j - appCost] + appSize, dp[i - 1][j]);
                    } else {
                        dp[i][j] = dp[i - 1][j];
                    }
                }
            }
        }

        int answer = Integer.MAX_VALUE;

        // M 보다 크거나 같은 사이즈를 비활성화 할 수 있는 minimum cost 를 검색
        for (int i = 0; i <= maxCost; i++) {
            if (dp[n - 1][i] >= m) {
                answer = i;
                break;
            }
        }
        return answer;
    }
}

```

