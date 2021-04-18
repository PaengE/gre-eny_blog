## Problem : https://www.acmicpc.net/problem/1562

## Approach

> Bitmasking을 이용한 DP문제였다.
>
> 그냥 계단수를 구하는 문제를 풀어본 기억이 있어 일단 DP를 떠올렸다.
>
> 그리고 보통 `사용한 숫자를 체크`할 때에는 Bitmasking을 사용했기에 이 문제도 그렇게 접근해 보았다.

i자리 계단수를 구하는 방법은 i-1자리 계단수의 끝자리에 +1 한 숫자와 -1 한 숫자를 붙인 계단수의 합이다.

하지만 끝자리가 0과 9인 경우에는 각각 +1한 숫자, -1한 숫자만 고려해야한다.

34가 계단수임을 알고 있으면 343도(-1한 숫자를 붙인 것) 계단수, 345도(+1한 숫자를 붙인 것) 계단수임을 알 수 있다.



먼저 DP 배열을 정의해보자. DP 배열은 3차원 배열이다.

DP[i][j][k] 는 자릿수가 i이고, 일의 자리수(끝 자리수)가 j이며, k를 이진수로 변환한 비트의 숫자를 사용한 계단수이다.

예를 들면 DP[2][2][4] = 2이다. 2자리 수이고 일의 자리 2이며 숫자 2를 사용한 계단수는 12와 32로 2개이다.(4 -> 000000010)

이는 코드를 보며 이해하는게 더 빠를 것이다. 글로 풀기가 좀 어렵다...

총 i자리이고 끝자리가 j이며 숫자 k를 사용한 계단수의 개수는, 총 i-1자리이고 끝자리가 +-1 이며 숫자 j를 사용하기 전까지의 숫자들을 사용해서 만든 계단수들의 총합이다. (그렇다고 숫자 j를 무조건 사용하지 않았다는 것이아니다. 이미 사용했을 수도 있다.)

최종 답안은 DP[n][0~9][(1 << 10) - 1] 의 값을 모두 더한후 MOD로 나누는 것이다. (총 n자리의 수 중 끝자리가 0 ~ 9이며 (1 << 10 == 1111111111) 0에서 9까지의 숫자가 모두 사용된 계단수의 합)

## Code

```java
import java.io.*;

/**
 *  No.1562: 계단 수
 *  URL: https://www.acmicpc.net/problem/1562
 *  Hint: Bitmasking DP
 */

public class BOJ1562 {
    static int n;
    static long[][][] dp;
    static final int MOD = 1000000000;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        n = Integer.parseInt(br.readLine());
        dp = new long[n + 1][10][1 << 10];

        bw.write(String.valueOf(calc()));
        bw.close();
        br.close();
    }

    static long calc() {
        long sum = 0;

        // 1자리 수는 모두 계단 수
        for (int i = 1; i <= 9; i++) {
            dp[1][i][1 << i] = 1;
        }

        // n자리 계단수의 개수는 n - 1자리 계단수에 숫자를 붙인 것
        for (int i = 2; i <= n; i++) {
            for (int j = 0; j <= 9; j++) {
                for (int k = 0; k < (1 << 10); k++) {
                    if (j == 0) {
                        dp[i][0][k | 1 << 0] = (dp[i][0][k | 1 << 0] + dp[i - 1][1][k]) % MOD;
                    } else if (j == 9) {
                        dp[i][9][k | 1 << 9] = (dp[i][9][k | 1 << 9] + dp[i - 1][8][k]) % MOD;
                    } else {
                        dp[i][j][k | 1 << j] = (dp[i][j][k | 1 << j] + dp[i - 1][j - 1][k] + dp[i - 1][j + 1][k]) % MOD;
                    }
                }
            }
        }

        // n자리 계단수의 개수 중 0~9가 모두 쓰인 수를 더함
        for (int i = 0; i <= 9; i++) {
            sum = (sum + dp[n][i][(1 << 10) - 1]) % MOD;
        }

        return sum;
    }
}
```

