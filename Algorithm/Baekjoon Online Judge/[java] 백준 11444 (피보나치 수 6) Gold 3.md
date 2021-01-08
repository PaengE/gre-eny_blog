## Problem : https://www.acmicpc.net/problem/11444

## Approach

유명한 피보나치 수열의 n번째 수를 구하는 문제이다. 

이 문제는 `백준 2749 피보나치 수 3` 문제와 나머지 연산 부분만 다르고 아예 똑같은 문제이다. 

~~문제풀이 개수 늘리기 좋다.~~

하지만 n이 1,000,000,000,000,000,000 이하의 숫자이므로 오랜시간이 걸릴 것이다. 

따라서 아래와 같이 피보나치의 행렬적 규칙을 이용하여 계산하여야 한다. (long 자료형을 사용하여)

아래 규칙만 알고 있다면 O(log n)의 행렬제곱 문제와 똑같다.

```html
[ Fn   ] = [ 1 1 ] * [ Fn-1 ] = [ 1 1 ] * [ 1 1 ] * [ Fn-2 ]
[ Fn-1 ]   [ 1 0 ]   [ Fn-2 ]   [ 1 0 ]   [ 1 0 ]   [ Fn-3 ]

->  [ Fn   ] = [ 1 1 ]^n-1 * [ F1 ]
    [ Fn-1 ]   [ 1 0 ]       [ F0 ]
```

행렬의 제곱 문제는 밑의 링크에 설명해 놓았다. 

[2020/12/24 - [Algorithm/Baekjoon Online Judge\] - [java] 백준 10830 (행렬 제곱) Gold 4](https://gre-eny.tistory.com/46)

## Code

```java
import java.io.*;

/**
 *  no.11444: 피보나치 수 6
 *  url: https://www.acmicpc.net/problem/11444
 *  hint: 행렬의 곱셈 성질을 이용
 */

public class BOJ11444 {
    static final int MOD = 1000000007;
    static long[][] arr = {{1, 1}, {1, 0}};
    public static long[][] pow(long n){
        long[][] tmp = new long[2][2];

        if(n == 1){
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    tmp[i][j] = arr[i][j];
                }
            }
            return tmp;
        }

        long[][] m;
        m = pow(n / 2);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    tmp[i][j] += m[i][k] * m[k][j];
                }
                tmp[i][j] %= MOD;
            }
        }
        if (n % 2 == 1){
            long[][] t = new long[2][2];
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    for (int k = 0; k < 2; k++) {
                        t[i][j] += tmp[i][k] * arr[k][j];
                    }
                    t[i][j] %= MOD;
                }
            }
            return t;
        } else {
            return tmp;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        long n = Long.parseLong(br.readLine());
        long ans;

        if(n == 1)
            ans = 1;
        else {
            long[][] res = pow(n-1);
            ans = res[0][0];
        }

        bw.write(ans + "");
        bw.flush();
        bw.close();
        br.close();

    }
}
```



