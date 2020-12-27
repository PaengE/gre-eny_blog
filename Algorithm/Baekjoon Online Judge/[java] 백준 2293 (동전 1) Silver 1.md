## Problem : https://www.acmicpc.net/problem/2293

## Approach

DP 문제이다. 

입력되는 동전의 가치가 오름차순으로 주어진다는 말이 없어 동전의 가치를 저장한 배열을 오름차순으로 정렬시킨 후, 다음과 같은 규칙을 이용하여 문제를 풀어나가면 된다.

> 코인의 가치가 a, b, c 일 때,
>
> x를 만드는 방법의 수는 x - a원 에서 a원을 더하여 만드는 법(1), x - b원에서 b원을 더하여 만드는 법(2), x - c 원에서 c원을 더하여 만드는 법(3)의 합이다. 이걸 식으로 표현하면 밑과 같다
>
> DP[x] = DP[x - a] + DP[x - b] + DP[x - c] 이다.

## Code

```java
import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * title : Dynamic Programming 2
 * no.2293 : 동전 1
 * desc : 더 이상 사용되지 않는 값을 버림으로써 공간 복잡도를 향상시키는 문제
 * hint : 동전이 1, 2, 5 일 경우 10원을 만드는 경우의 수는
 *        dp[9] + 1원, dp[8] + 2원, dp[5] + 5원 세가지 경우의 가지 수의 합이다.
 */

public class BOJ2293 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int[] coin = new int[n];

        for (int i = 0; i < n; i++) {
            coin[i] = Integer.parseInt(br.readLine());
        }
        Arrays.sort(coin);

        int[] dp = new int[k + 1];
        dp[0] = 1;
        for (int i = 0; i < n; i++) {
            for (int j = coin[i]; j <= k; j++) {
                dp[j] += dp[j - coin[i]];
            }
        }

        bw.write(dp[k] + "");
        bw.flush();
        bw.close();
        br.close();
    }
}

```

