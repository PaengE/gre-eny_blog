## Problem : https://www.acmicpc.net/problem/1285

## Approach

> `Greedy` + `Bitmask` 문제이다.

주요 로직은 다음과 같다. N x N 행렬이고, sum은 뒷면 동전의 총 개수이다.

- 행을 기준으로 뒤집을 행을 선택한다. (이 부분은 2^n 개수 만큼 존재한다.)
  - 101 이라면 0, 2번째 행을 뒤집는다.
- 이제 선택한 행들을 뒤집을 건데, 열을 기준으로 먼저 뒤집는다.
  - 101 이라면, 0,1,2 열 순서대로 0번째 행 요소와 2번째 행 요소를 뒤집는다.
- 선택한 행들을 기준으로 하나의 열을 모두 뒤집었으면, 그 열에 동전 뒷면이 몇개인지를 센다.
  - 앞면 동전과 뒷면 동전 중 작은 것을 sum에 더한다.
  - 왜냐하면 뒷면 동전이 많을 경우, 해당 열을 뒤집어야 뒷면 동전 개수가 최소가 되기 때문이고, 앞면 동전이 많을 경우, 해당 열을 뒤집지 않는 것이 뒷면 동전 개수가 최소가 되기 때문이다.

이렇게 하면, 모든 행과 열을 뒤집어보는 결과와 같은 결과를 낸다. 이것이 가능한 이유는 행을 뒤집는 것과 열을 뒤집는 것이 각각 독립적이기 때문이다. (서로에게 영향을 끼치지 않는다는 뜻이다.)

## Code

```java
import java.io.*;

/**
 *  No.1285: 동전 뒤집기
 *  Hint: Bitmask + Greedy
 */

public class BOJ1285 {
    static int n;
    static char[][] map;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        n = Integer.parseInt(br.readLine());
        map = new char[n][n];
        for (int i = 0; i < n; i++) {
            map[i] = br.readLine().toCharArray();
        }

        int ans = Integer.MAX_VALUE;
        // 길이가 n인 2진수 비트로 어떤 행들을 뒤집을지 선택(모든 경우의 수를 다 수행함)
        for (int bit = 0; bit < (1 << n); bit++) {
            int sum = 0;
            // 뒤집기를 선택한 행들을 뒤집는다.
            for (int b = 0; b < n; b++) {
                int back = 0;
                // 단 열을 기준으로 뒤집는다.
                for (int a = 0; a < n; a++) {
                    char cur = map[a][b];

                    // 뒤집기로 선택한 행의 열이라면 뒤집는다.
                    if ((bit & (1 << a)) != 0) {
                        cur = reverse(a, b);
                    }

                    if (cur == 'T') {   // 동전이 뒷면이면 카운트한다.
                        back++;
                    }
                }
                // b열을 뒤집었을 때, 뒤집지 않았을 때 중 동전의 개수가 더 적은 것을 누적합한다.
                sum += Math.min(back, n - back);
            }
            // 선택된 행들을 뒤집었을 때, 뒷면 동전의 최소 개수를 구한다.
            ans = Math.min(ans, sum);
        }
        // 위의 로직이 가능한 이유는, 행끼리의 뒤집기는 서로 영향을 주지 않고
        // 열끼리의 뒤집기 또한 서로 영향을 주지 않기 때문이다.

        bw.write(String.valueOf(ans));
        bw.close();
        br.close();
    }

    static char reverse(int y, int x) {
        if (map[y][x] == 'T') {
            return 'H';
        } else {
            return 'T';
        }
    }
}
```

