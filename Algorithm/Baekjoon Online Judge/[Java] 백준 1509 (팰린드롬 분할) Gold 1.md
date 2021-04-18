## Problem : https://www.acmicpc.net/problem/1509

## Approach

> 주어진 문자열이 `최소 몇 개의 팰린드롬 문자열`로 구성되어 있는지를 구하는 문제이다.
>
> 팰린드롬 문자열 자체를 구하는 로직은 O(n^2) 의 시간복잡도를 가진다. 
>
> 팰린드롬 문자열을 구하는 방법은 `투포인터`를 사용하였다.

DP[i] 는 `0 ~ i`인덱스의 문자열이 `몇 개의 최소 팰린드롬 문자열로 구성되는지`를 저장한다.

`i ~ j`가 팰린드롬 문자열일 때, DP[j] 는 min(DP[j], DP[j - 1] + 1)이다.

`0 ~ (i - 1)` 인덱스의 문자열이 구성되는 최소 팰린드롬 문자열 개수에 1개를 더해주면 된다. (`i ~ j` 인덱스의 문자열이 팰린드롬이므로)

## Code

```java
import java.io.*;
import java.util.Arrays;

/**
 *  No.1509: 팰린드롬 분할
 *  URL: https://www.acmicpc.net/problem/1509
 *  Hint: i ~ j 문자열이 팰린드롬인지를 판단한 후, 그것을 응용
 */

public class BOJ1509 {
    static String str;
    static int size;
    static boolean[][] palindrome;
    static int[] dp;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        str = br.readLine();
        size = str.length();
        palindrome = new boolean[size + 1][size + 1];
        dp = new int[size + 1];

        checkPalindrome();
        setDP();

        bw.write(String.valueOf(dp[size]));
        bw.close();
        br.close();
    }

    // DP[i] 는 0 ~ i 까지의 문자열으로 만들 수 있는 팰린드롬 분할의 개수의 최솟값
    static void setDP() {
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        for (int b = 1; b <= size; b++) {
            for (int a = 1; a <= b; a++) {
                if (palindrome[a][b]) {
                    dp[b] = Math.min(dp[b], dp[a - 1] + 1);
                }
            }
        }
    }

    // i ~ j 길이의 문자열이 팰린드롬인지를 판단하는 메소드
    static void checkPalindrome() {
        for (int i = 1; i <= size; i++) {
            for (int j = i; j <= size; j++) {
                boolean isSame = true;

                int from = i - 1;
                int to = j - 1;
                while (from <= to) {
                    if (str.charAt(from++) != str.charAt(to--)) {
                        isSame = false;
                        break;
                    }
                }

                if (isSame) {
                    palindrome[i][j] = true;
                }
            }
        }
    }
}
```

