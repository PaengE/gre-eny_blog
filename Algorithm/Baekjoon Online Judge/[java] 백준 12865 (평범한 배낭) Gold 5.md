## Problem : https://www.acmicpc.net/problem/12865

## Approach

대표적인 DP 문제인 KnapSack Problem이다.

> DP[i][j] 는 j만큼의 무게를 견딜 수 있는 가방에 처음 i개의 물건들로 담을 수 있는 최대 가치를 말한다.
>
> - i번째 물건의 무게 weight 가 현재 넣을 수 있는 무게 j보다 크다면 DP[i][j] = DP[i - 1][j]이다.
>
> - 그러나 작다면, 가방에 넣을 수 있다는 얘기이므로 DP[i][j] = max(DP[i - 1][j], DP[i - 1][j - weight] + i번째 물건의 가치 value)이다. 

왜 j - weight 라고 한다면, 현재 물건을 넣으려면 weight만큼의 무게만 필요하므로 DP[i - 1][j - weight] + 현재 물건을 넣었을 때의 가치 value 인 것이다.

밑의 표는 문제의 입력데이터에 관한 DP 배열이다.

|       |  0   |  1   |  2   |  3   |  4   |  5   |  6   |  7   |
| :---: | :--: | :--: | :--: | :--: | :--: | :--: | :--: | :--: |
| **0** |  0   |  0   |  0   |  0   |  0   |  0   |  0   |  0   |
| **1** |  0   |  0   |  0   |  0   |  0   |  0   |  13  |  13  |
| **2** |  0   |  0   |  0   |  0   |  8   |  8   |  13  |  13  |
| **3** |  0   |  0   |  0   |  6   |  8   |  8   |  13  |  14  |
| **4** |  0   |  0   |  0   |  6   |  8   |  12  |  13  |  14  |

## Code

```java
import java.io.*;
import java.util.StringTokenizer;

/*
    no.12865 : 평범한 배낭(KnapSack Problem)
    hint : DP배열 구성 시, max(지금까지 넣은 보석의 최대 가치, 현재 보석 가치 + 나머지 자리에 넣을 수 있는 보석의 최대 가치)
           ans[i][j] = max(ans[i-1][j], ans[i-1][j-weight[i-1]] + profit[i])
 */

public class BOJ12865 {
    public static void dp(int n, int k, int[][] arr) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int[][] dp = new int [n+1][k+1];

        for(int i = 0; i < n; i++){
            int weight = arr[i][0];
            int value = arr[i][1];
            for (int j = 0; j <= k; j++){
                if (j < weight){
                    dp[i+1][j] = dp[i][j];
                } else {
                    dp[i+1][j] = Math.max(dp[i][j], dp[i][j-weight] + value);
                }
            }
        }

        bw.write(dp[n][k] + "");

        bw.flush();
        bw.close();
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s;
        StringTokenizer st;

        s = br.readLine();
        st = new StringTokenizer(s);
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int[][] arr = new int[n][2];
        for(int i = 0; i < n; i++){
            s = br.readLine();
            st = new StringTokenizer(s);
            arr[i][0] = Integer.parseInt(st.nextToken());
            arr[i][1] = Integer.parseInt(st.nextToken());
        }
        br.close();

        dp(n, k, arr);
    }
}

```

