## Problem : https://www.acmicpc.net/problem/1629

## Approach

수학적 지식이 있어야 하는 분할정복(Divide & Conquer)문제이다.

> A^4 % C = ((A^2 % C) * (A^2 % C)) % C
>
> A^5 % C = ((((A^2 % C) * (A^2 % C)) % C) * A) % C

위의 규칙을 이용하여 구현하면 된다.

## Code

```java
import java.io.*;

/**
 * no.1629 : 곱셈
 * hint : A^2 % C = ((A % C) * (A % C)) % C
 */

public class BOJ1629 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        String[] s = br.readLine().split(" ");
        int a = Integer.parseInt(s[0]);
        int b = Integer.parseInt(s[1]);
        int c = Integer.parseInt(s[2]);

        long ans = 1;
        long t = a % c;
        while (b > 0) {
            if (b % 2 == 1){
                ans *= t;
                ans %= c;
            }
            t = ((t % c) * (t % c)) % c;
            b /= 2;
        }

        bw.write(ans + "");
        bw.flush();
        bw.close();
        br.close();
    }
}
```

