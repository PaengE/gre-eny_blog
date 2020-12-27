## 문제 원문 링크 : https://www.acmicpc.net/problem/10844

## Approach

DP로 풀 수 있는 문제이다.

> 문제 풀이에 앞서, 밑의 조건을 알고 가면 편하다.
>
> ​	1. 0으로 시작하는 N자리 수는 없다.
>
> ​	2. 1자리 수는 모두 계단수이다.
>
> ​	3. DP에 저장할 때에 1,000,000,000으로 나눈 나머지를 저장해야 한다.

N자리 수를 봤을 때, 10의 자리 수가 0이면 1의 자리 수가 1이어야 한다.

10의 자리 수가 1~8이면 1의 자리 수는 10의 자리 수의 +1한 수 이거나 -1한 수 여야만 한다.

10의 자리수가 9이면 1의 자리 수는 8이어야만 한다.

위의 규칙을 가지고 점화식을 세우면 아래와 같다.

> ​	DP[i][j] = DP[i - 1][j +1]	(j == 0)
>
> ​	DP[i][j] = DP[i - 1][j - 1] + DP[i - 1][j + 1]	(j == 1 ~ 8)
>
> ​	DP[i][j] = DP[i - 1][j - 1]	(j == 9)
>

## Code

```java
/*
    no.10844 : 쉬운 계단 수
    hint : n번째 자리에 숫자 k를 놓을 수 있는지를 따져 볼 것.
 */

import java.io.*;

public class BOJ10844 {

    public static void dp(int n) throws IOException{
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int[][] arr = new int[n+1][10];

        for(int i = 1; i <= 9; i++){
            arr[1][i] = 1;
        }
        for(int i = 2; i <= n; i++){
            for(int j = 0; j <= 9; j++){
                if (j == 0){
                    arr[i][j] = arr[i-1][1];
                } else if (j == 9){
                    arr[i][j] = arr[i-1][8];
                } else {
                    arr[i][j] = (arr[i-1][j-1] + arr[i-1][j+1]) % 1000000000;
                }
            }
        }
        long sum = 0;
        for(int i = 0; i <= 9; i++){
            sum += arr[n][i];
        }

        bw.write(sum % 1000000000 + "");
        bw.flush();
        bw.close();
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        dp(n);

        br.close();
    }
}

```

