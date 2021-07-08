## Problem : https://www.acmicpc.net/problem/16639



## Approach

> `괄호 추가하기 2`와 같은 BruteForce 문제가 아닌 `DP` 문제로, 아예 다른 유형의 문제였다.



중첩된 괄호가 있을 수도 있고, 괄호 안에 여러 연산자가 존재할 수도 있기에, 연산자들의 우선순위는 고려할 사항이 아니다.

따라서 수식의 `i ~ j`까지의 결과는 `i ~ k`의 결과 + `k ~ j`의 결과 임을 이용하면 된다.



주의할 점은 `(음수) * (음수)`의 결과가 최댓값이 될 수도 있기에, `max[i][j]`와 `min[i][j]`를 모두 구하여 정보를 유지시켜야 한다.

- calculate(max, max)
- calculate(max, min)
- calculate(min, max)
- calculate(min, min)

위 4가지의 경우를 모두 계산하여 가장 큰값을 `max[i][j]`에 가장 작은 값을 `min[i][j]`에 저장하며 진행하면 된다.



## Code

```java
import java.io.*;
import java.util.Arrays;

/**
 *  No.16639: 괄호 추가하기 3
 *  Hint: DP
 */

public class BOJ16639 {
    static int n;
    static int[][] min, max;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        n = Integer.parseInt(br.readLine());
        min = new int[n][n];
        max = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(min[i], Integer.MAX_VALUE);
            Arrays.fill(max[i], Integer.MIN_VALUE);
        }

        char[] input = br.readLine().toCharArray();
        for (int i = 0; i < n; i += 2) {
            min[i][i] = input[i] - '0';
            max[i][i] = input[i] - '0';
        }

        // (i ~ ㅓ) 까지의 합은 (i ~ k) 의 합 + (k ~ j) 합을 이용
        for (int j = 2; j < n; j += 2) {
            for (int i = 0; i < n - j; i += 2) {
                for (int k = 2; k <= j; k += 2) {
                    char op = input[i + k - 1];
                    int[] tmp = new int[4];
                    tmp[0] = calculate(max[i][i + k - 2], max[i + k][i + j], op);
                    tmp[1] = calculate(min[i][i + k - 2], max[i + k][i + j], op);
                    tmp[2] = calculate(max[i][i + k - 2], min[i + k][i + j], op);
                    tmp[3] = calculate(min[i][i + k - 2], min[i + k][i + j], op);
                    Arrays.sort(tmp);
                    max[i][i + j] = Math.max(max[i][i + j], tmp[3]);
                    min[i][i + j] = Math.min(min[i][i + j], tmp[0]);
                }
            }
        }

        bw.write(String.valueOf(max[0][n - 1]));
        bw.close();
        br.close();
    }

    // 연산자 계산
    static int calculate(int n1, int n2, char op) {
        int res = 0;
        switch (op) {
            case '+':
                res = n1 + n2;
                break;
            case '-':
                res = n1 - n2;
                break;
            case '*':
                res = n1 * n2;
                break;
        }
        return res;
    }
}
```

