## Problem : https://www.acmicpc.net/problem/10830

## Approach

같은 행렬 요소를 여러번 곱하는 문제이므로, 분할 정복을 이용하여 풀 수 있다.

행렬 제곱은 다음과 같이 분할 할 수 있다. 행렬 A의 9제곱을 구한다고 가정했을 때,

A^9 = (A^4 * A^4) * A = ((A^2 * A^2) * (A^2 * A^2)) * A 이 된다.

알고리즘 자체는 O(logN)이지만, 행렬 곱셈 자체는 O(N^3)이다.

구현부는 코드와 코드 주석을 같이 보면 이해가 쉬울 것이다.

## Code

```java
import java.io.*;

/**
 * no.10830 : 행렬 제곱
 * title : 분할 정복으로 행렬의 거듭젝보을 빠르게 계산하는 문제
 * hint : M^9 = (M^4 * M^4) * M = ((M^2 * M^2) * (M^2 * M^2)) * M
 * 알고리즘 자체는 O(logN), long 타입으로 할 것.
 */

public class BOJ10830 {
    static long[][] matrix;
    static int n;
    public static long[][] pow(long b){
        long[][] tmp = new long[n][n];
        long[][] m;
      
      // b 가 1이라면 tmp에 행렬을 담아서 return
        if(b == 1){
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tmp[i][j] = matrix[i][j] % 1000;
                }
            }
            return tmp;
        }
      // b == 1가 아니면 b를 2로 나눠서 재귀를 돌린다. (분할)
        m = pow(b / 2);
      
      // 재귀를 돌려서 받은 m으로 행렬의 곱셈을 수행 (병합)
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    tmp[i][j] += m[i][k] * m[k][j];
                }
                tmp[i][j] %= 1000;
            }
        }
      // b를 나누어서 나머지가 1이면 행렬을 한번 더 곱해서 return
      // 아니라면 그냥 return
        if(b % 2 == 1){
            long[][] odd = new long[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    for (int k = 0; k < n; k++) {
                        odd[i][j] += tmp[i][k] * matrix[k][j];
                    }
                    odd[i][j] %= 1000;
                }
            }
            return odd;
        } else {
            return tmp;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        String[] s = br.readLine().split(" ");
        n = Integer.parseInt(s[0]);
        long b = Long.parseLong(s[1]);
        matrix = new long[n][n];

        for (int i = 0; i < n; i++) {
            s = br.readLine().split(" ");
            for (int j = 0; j < n; j++) {
                matrix[i][j] = Integer.parseInt(s[j]);
            }
        }

        long[][] ans = pow(b);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                bw.write(ans[i][j] + " ");
            }
            bw.write("\n");
        }
        bw.flush();
        bw.close();
        br.close();
    }
}

```

