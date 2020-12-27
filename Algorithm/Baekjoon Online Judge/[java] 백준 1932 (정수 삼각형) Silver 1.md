## 문제 원문 링크 : https://www.acmicpc.net/problem/1932

## Approach

DP 문제이다.

문제에서는 이등변 삼각형처럼 보이지만, 실제로는 정사각형 배열의 반만 쓰면 된다.

크기가 N인 정수 삼각형이고, i는 위에서부터 층의 높이, j는 삼각형의 열일 때,

> ​	DP[i][j] = DP[i - 1][0]	(j == 0)
>
> ​	DP[i][j] = DP[i - 1][j - 1]	(j == N)
>
> ​	DP[i][j] = DP[i - 1][j - 1] + DP[i - 1][j]	(j == 1...N-1)
>

위의 식을 도출해 낼 수 있다. 그 이후 맨 아래층에 있는 배열 요소 중 최댓값을 찾으면 된다.

## Code

```java
/*
    no.1932 : 정수 삼각형
    hint : 삼각형의 밑 변의 n개 숫자 중 하나를 고를 때의 경로 최대값을 구함
        왼쪽 변이나 오른쪽 변에 있는 숫자는 부모가 하나.
 */

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ1932 {
    static int[][] arr, sumArr;
    static int[] res;

    public static void dp(int n){
        sumArr[0][0] = arr[0][0];
        for(int i = 1; i < n; i++){
            for(int j = 0; j <= i; j++){
                if(j == 0){
                    sumArr[i][j] = arr[i][j] + sumArr[i-1][j];
                } else if(i == j){
                    sumArr[i][j] = arr[i][j] + sumArr[i-1][j-1];
                } else {
                    sumArr[i][j] = arr[i][j] + Math.max(sumArr[i-1][j-1], sumArr[i-1][j]);
                }
                if(i == n-1){
                    res[j] = sumArr[i][j];
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine());
        arr = new int[n][n];
        sumArr = new int[n][n];
        res = new int[n];
        for(int i = 0; i < n; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j <= i; j++){
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dp(n);

        Arrays.sort(res);

        bw.write(res[n-1] + "");

        bw.flush();
        br.close();
        bw.close();
    }
}

```

