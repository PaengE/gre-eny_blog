## Problem : https://www.acmicpc.net/problem/9251

## Approach

LCS(Longest Common Subsequence) 문제로 DP로 풀이가 가능한 문제이다.

배열은 DP[s1.length + 1][s2.length + 1] 으로 선언하여 두 문자열을 각각 한 문자씩 비교해 나간다.

> DP[i][j] 는 s1.charAt(i)와 s2.charAt(j)를 비교한 후, 그 때까지의 LCS크기를 저장한다.
>
> - 같다면, `s1.charAt(i - 1)와 s2.charAt(j - 1)까지를 비교하여 저장된 LCS 크기 + 1`을 DP[i][j]에 저장한다.
>
> - 다르다면,  `s1.charAt(i - 1)와 s2.charAt(j)까지를 비교하여 저장된 LCS 크기` 와 ` s1.charAt(i)와 s2.charAt(j - 1)` 중 큰 값을 DP[i][j]에 저장한다.

문제의 입력 데이터를 예시로 들어 배열을 구성한다면 다음과 같다.

|       | **0** | **1** | **2** | **3** | **4** | **5** | **6** |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **0** |   0   |   0   |   0   |   0   |   0   |   0   |   0   |
| **1** |   0   |   0   |   1   |   1   |   1   |   1   |   1   |
| **2** |   0   |   1   |   1   |   1   |   2   |   2   |   2   |
| **3** |   0   |   1   |   2   |   2   |   2   |   3   |   3   |
| **4** |   0   |   1   |   2   |   2   |   2   |   3   |   3   |
| **5** |   0   |   1   |   2   |   3   |   3   |   3   |   4   |
| **6** |   0   |   1   |   2   |   3   |   3   |   3   |   4   |

## Code

```java
import java.io.*;
/*
    no.9251 : LCS(Longest Common Subsequence, 최장 공통 부분수열) 문제
    hint : D[i][j] = { D[i-1][j-1] + 1              if X[i] == Y[j]
                     { max(D[i][j-1], D[i-1][j]     if X[i] != Y[j]
 */

public class BOJ9251 {
    public static void dp(String s1, String s2) throws IOException{
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int[][] dp = new int[s1.length()+1][s2.length()+1];

        for(int i = 0; i < s1.length(); i++){
            for(int j = 0; j < s2.length(); j++){
                if(s1.charAt(i) == s2.charAt(j)){
                    dp[i+1][j+1] = dp[i][j] + 1;
                } else {
                    dp[i+1][j+1] = Math.max(dp[i+1][j], dp[i][j+1]);
                }
            }
        }

        bw.write(dp[s1.length()][s2.length()] + "");
        bw.flush();
        bw.close();
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s1 = br.readLine();
        String s2 = br.readLine();

        dp(s1, s2);

        br.close();
    }
}

```

