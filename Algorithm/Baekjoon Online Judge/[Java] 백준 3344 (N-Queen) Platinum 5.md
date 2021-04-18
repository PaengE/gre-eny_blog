## Problem : https://www.acmicpc.net/problem/3344

## Approach

> 일반적인 Backtracking 으로 풀면 시간초과가 난다. 입력의 크기가 최대 99999이고,
>
> 백트랙킹 기법은 시간복잡도가 O(N^2)이기 때문이다.

나도 구글링 중에 알아낸 풀이법으로, N-Queens의 규칙을 찾아 그대로 구현하는 문제였다.

솔직히 아직 완벽히 이해하진 못했다.

자세한 알고리즘은 밑의 링크에 `Existence of solutions` 탭을 확인하시면 좋을 것 같다.

https://en.wikipedia.org/wiki/Eight_queens_puzzle

위에 나와있는 풀이를 그대로 구현한 것이다.

## Code

```java
import java.io.*;

public class BOJ3344 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());
        boolean isOdd = false;
        StringBuilder sb = new StringBuilder();

        if (n % 2 != 0) {
            isOdd = true;
        }

        if ((!isOdd && n % 6 != 2) || (isOdd && (n - 1) % 6 != 2)) {
            if (isOdd) {
                n--;
            }
            for (int i = 1; i <= n / 2; i++) {
                sb.append(2 * i).append("\n");
            }
            for (int i = 1; i <= n / 2; i++) {
                sb.append(2 * i - 1).append("\n");
            }
            if (isOdd) {
                sb.append(n + 1).append("\n");
            }

        } else if ((!isOdd && n / 6 != 0) || (isOdd && (n - 1) / 6 != 2)) {
            if (isOdd) {
                n--;
            }
            for (int i = 1; i <= n / 2; i++) {
                sb.append(1 + (2 * i + n / 2 - 3) % n).append("\n");
            }
            for (int i = n / 2; i > 0; i--) {
                sb.append(n - (2 * i + n / 2 - 3) % n).append("\n");
            }
            if (isOdd) {
                sb.append(n + 1).append("\n");
            }
        }
        bw.write(sb.toString());
        bw.close();
        br.close();
    }
}

```

