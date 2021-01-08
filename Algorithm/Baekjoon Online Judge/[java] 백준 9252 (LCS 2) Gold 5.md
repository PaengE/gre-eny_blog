## Problem : https://www.acmicpc.net/problem/9252

## Approach

LCS(Longest Common Subsequence) 문제로 DP로 풀이가 가능한 문제이다.

배열은 DP[s1.length + 1][s2.length + 1] 으로 선언하여 두 문자열을 각각 한 문자씩 비교해 나간다.

> DP[i][j] 는 s1.charAt(i)와 s2.charAt(j)를 비교한 후, 그 때까지의 LCS크기를 저장한다.
>
> - 같다면, `s1.charAt(i - 1)와 s2.charAt(j - 1)까지를 비교하여 저장된 LCS 크기 + 1`을 DP[i][j]에 저장한다.
> - 다르다면,  `s1.charAt(i - 1)와 s2.charAt(j)까지를 비교하여 저장된 LCS 크기` 와 ` s1.charAt(i)와 s2.charAt(j - 1)` 중 큰 값을 DP[i][j]에 저장한다.

문제의 입력 예제 ACAYKP 와 CAPCAK 를 이용한 DP배열은 다음과 같다. 알파벳을 붙인 것은 이해를 돕기 위함이다.

|          | **0** | **1(C)** | **2(A)** | **3(P)** | **4(C)** | **5(A)** | **6(K)** |
| :------: | :---: | :------: | :------: | :------: | :------: | :------: | :------: |
|  **0**   |   0   |    0     |    0     |    0     |    0     |    0     |    0     |
| **1(A)** |   0   |    0     |    1     |    1     |    1     |    1     |    1     |
| **2(C)** |   0   |    1     |    1     |    1     |    2     |    2     |    2     |
| **3(A)** |   0   |    1     |    2     |    2     |    2     |    3     |    3     |
| **4(Y)** |   0   |    1     |    2     |    2     |    2     |    3     |    3     |
| **5(K)** |   0   |    1     |    2     |    3     |    3     |    3     |    4     |
| **6(P)** |   0   |    1     |    2     |    3     |    3     |    3     |    4     |

위의 표로 인하여 LCS의 최대 길이는 4인 것을 알 수있다. 또한, DP 배열 역추적을 통하여 가장 긴 LCS 를 찾아낼 수 있다.

예를들어, DP배열 구성에 있어 DP[5][6] 와 DP[6][6]의 값이 같다는 것은 CAPCAK와 ACAYK의 LCS가 같다는 뜻이다.

따라서, DP[i][j] 의 값과 DP[i - 1][j] 혹은 DP[i][j - 1] 값이 같다면 그 문자를 없앤다.(표에서 그 행 또는 열을 없앤다.)

DP[i][j]의 값이 위 두 값과 하나도 같지 않다면, 당연히 DP[i - 1][j - 1]값과는 1차이가 나게 될 것이고 그 때의 i값(혹은 j값(동일함))이 LCS의 구성요소이므로, 스택에 push한다. (스택에 넣는 이유는 역추적이므로, 나중에 앞에서부터 순서대로 LCS를 뽑아내기 위함이다.)

위 표에서 LCS 구성요소는 (1, 2), (3, 4), (4, 5), (5, 4) -> ACAK 이다. 이 예제에서는 다른 답이 없지만, 스페셜 저지 문제이므로 다른 테스트케이스에 대해선 여러 답이 존재할 수 있다.

## Code

```java
import java.io.*;
import java.util.Stack;

/**
 *  No.9252: LCS 2 (Least Common Subsequence)
 *  URL: https://www.acmicpc.net/problem/9252
 *  Hint: DP Backtracking
 */

public class BOJ9252 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        String s1 = br.readLine();
        String s2 = br.readLine();
        int len1 = s1.length();
        int len2 = s2.length();

        int[][] dp = new int[len1 + 1][len2 + 1];

        for(int i = 0; i < len1; i++){
            for(int j = 0; j < len2; j++){
                if(s1.charAt(i) == s2.charAt(j)){
                    dp[i+1][j+1] = dp[i][j] + 1;
                } else {
                    dp[i+1][j+1] = Math.max(dp[i+1][j], dp[i][j+1]);
                }
            }
        }

        bw.write(dp[len1][len2] + "\n");

        Stack<Character> stack = new Stack<>();
        while (len1 >= 1 && len2 >= 1) {
            // s1의 len1번째와 s2의 len2번째가 다르므로 s1의 인덱스를 하나 줄임
            if (dp[len1][len2] == dp[len1 - 1][len2]) {
                len1--;
            }
            // s1의 len1번째와 s2의 len2번째가 다르므로 s2의 인덱스를 하나 줄임
            else if (dp[len1][len2] == dp[len1][len2 - 1]) {
                len2--;
            }
            // s1의 len1번째와 s2의 len2번째가 같으므로 인덱스를 하나씩 줄이고
            // 스택에 저장
            else {
                stack.push(s1.charAt(len1 - 1));
                len1--;
                len2--;
            }
        }

        // 스택에서 하나씩 뽑아서 출력
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();
    }
}
```

