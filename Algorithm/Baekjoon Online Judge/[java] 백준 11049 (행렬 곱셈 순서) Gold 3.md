## Problem : https://www.acmicpc.net/problem/11049

## Approach

DP 문제로 행렬의 곱셈규칙을 알아야 한다.

> A x B 행렬, B X C 행렬을 곱한다고 하였을 떄, 필요한 곱셈연산 수는 A x B x C번이며, 곱셈으로 만들어진 행렬은 A x C 크기의 행렬이다. 
>
> 그리고 이 행렬과 C x D 행렬을 곱한다고 하였을 때, 필요한 곱셈연산 수는 A x C x D 이다. 그리고 만들어진 행렬의 크기는 A x D 이다.

mat[i][0] = i번째 행렬의 행 크기, mat[i][1] = i번째 행렬의 열 크기,

DP[i][j] = i번째 행렬부터 j번째 행렬까지의 곱셈연산의 최소 횟수. 라고 정의한 후 풀이에 들어간다.

그렇다면 DP[i][j] = DP[i][k] + DP[k + 1][j] + (i ~ k까지를 곱한 행렬) 과 (k + 1 ~ j까지를 곱한 행렬)의 곱셈연산 횟수이다.



이 때, 위에서 설명한 행렬  곱셈 규칙을 써먹을 수 있다.

i ~ k까지를 곱한 행렬의 크기는 (i번째 행렬의 행의 개수) x  (k번째 행렬의 열의 개수) 이다.

마찬가지로, k + 1 ~ j까지를 곱한 행렬의 크기는 (k + 1번째 행렬의 행의 개수) x (j번째 행렬의 열의 개수) 이다.

위 두 행렬을 곱하는 곱셈연산 횟수는 (i번째 행렬의 행의 개수) x (k + 1번째 행렬의 행의 개수) x (j번째 행렬의 열의 개수) 이다. (k번째 행렬의 열의 개수와 k + 1번째 행렬의 행의 개수는 항상 같으므로 어느걸 써도 상관없다.)

## Code

```java
import java.io.*;
import java.util.StringTokenizer;

/**
 * no.11049: 행렬 곱셈 순서
 * description: 행렬을 곱하는 최소비용을 구하는 문제
 * hint: dp[i][j] = Min(dp[i][j], dp[i][k] + dp[k+1][j] + (mat[i][0] * mat[k][1] * mat[j][1]))
 */

public class BOJ11049 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());
        int[][] mat = new int[n][2];
        StringTokenizer st;

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            mat[i][0] = Integer.parseInt(st.nextToken());
            mat[i][1] = Integer.parseInt(st.nextToken());
        }

        bw.write(String.valueOf(solution(mat)));
        bw.flush();

        bw.close();
        br.close();
    }

    static int solution(int[][] mat) {
        int size = mat.length;
        int[][] dp = new int[size][size];

        for (int i = 0; i < size - 1; i++) {
            dp[i][i + 1] = mat[i][0] * mat[i][1] * mat[i + 1][1];
        }

        for (int gap = 2; gap < size; gap++) {
            for (int i = 0; i + gap < size; i++) {
                int j = i + gap;
                dp[i][j] = Integer.MAX_VALUE;

                for (int k = i; k < j; k++) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k + 1][j] + (mat[i][0] * mat[k][1] * mat[j][1]));
                }
            }
        }
        return dp[0][size - 1];
    }
}

```

