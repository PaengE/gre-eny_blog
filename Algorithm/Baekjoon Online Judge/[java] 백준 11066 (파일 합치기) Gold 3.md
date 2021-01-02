## Problem : https://www.acmicpc.net/problem/11066

## Approach

DP를 이용한 문제로, DP[i][j] = i ~ j까지의 파일을 합치는 최소 비용 이라고 정의하고 문제에 접근한다.

> 주어진 파일의 개수가 1개라면, 그 파일의 크기를 return 한다.
>
> 주어진 파일의 개수가 2개라면, 두 개의 파일 크기를 합친 값을 return 한다.
>
> 주어진 파일의 개수가 3개 이상이라면, DP[i][j] = DP[i][k] + DP[k][j] + i ~ j부터 파일의 합 이다.

의외로 간단한 문제이다.

## Code

```java
import java.io.*;
import java.util.StringTokenizer;

/**
 * no.11066: 파일 합치기
 * description: 파일을 합쳐 하나로 모으는 최소 비용을 구하는 문제
 * hint: Knuth Optimization ?
 *      1. 무조건 인접한 것끼리 합쳐야 함
 *      2. 규칙상 마지막 연산은 전체 합을 더해야 함
 * 점화식: n == 1일 때, DP[i][i] = 0
 *         n == 2일 때, DP[i][i + 1] = V[i] + V[i + 1]
 *         n >= 3일 때ㅡ DP[i][j] = MIN(DP[i][j], DP[i][k] + DP[k + 1][j] + SUM(V[i ~ j]) (단, i < k < j)
 */

public class BOJ11066 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int t = Integer.parseInt(br.readLine());
        StringTokenizer st;
        for (int i = 0; i < t; i++) {
            int k = Integer.parseInt(br.readLine());
            int[] file = new int[k];
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < k; j++) {
                file[j] = Integer.parseInt(st.nextToken());
            }

            bw.write(String.valueOf(solution(file)));
            bw.newLine();
        }
        bw.flush();
        bw.close();
        br.close();
    }

    static int solution(int[] file) {
        int size = file.length;
        int[][] dp = new int[size][size];
        int[] sum = new int[size];

        sum[0] = file[0];

        // 앞에서부터 순서대로 파일을 합친 값
        for (int i = 1; i < sum.length; i++) {
            sum[i] = sum[i - 1] + file[i];
        }

        // 앞에서부터 인접한 두 파일을 합친 값
        for (int i = 0; i < file.length - 1; i++) {
            dp[i][i + 1] = file[i] + file[i + 1];
        }

        // 파일의 개수가 >= 3일 때
        for (int gap = 2; gap < size; gap++) {
            for (int i = 0; i + gap < size; i++) {  // i index
                int j = i + gap;                    // j index
                dp[i][j] = Integer.MAX_VALUE;

                // i ~ j 사이의 k 값
                for (int k = i; k < j; k++) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k + 1][j] + sumDist(sum, i, j));
                }
            }
        }
        return dp[0][size - 1];
    }

    // start 부터 end 까지의 파일의 합을 구하는 메소드
    static int sumDist(int[] sum, int start, int end) {
        if (start == 0) {
            return sum[end];
        } else {
            return sum[end] - sum[start - 1];
        }
    }
}

```

