## Problem : https://www.acmicpc.net/problem/12970

## Approach

> `Greedy` 문제이다.
>
> 먼저 간단한 규칙을 알아보자.
>
> A의 개수 a, B의 개수 b일 때, (A, B) 쌍의 최대 개수는 a * b 개이다. (A~AB~B 형태의 문자열)
>
> 그리고 길이가 N 인 문자열에서 (A, B) 쌍의 최대 개수는 A의 개수가 N / 2개이고, B의 개수가 N - a 개일 때이다.
>
> 또한, a개의 A와 b개의 B로 만들 수 있는 쌍의 범위는 0 ~ a * b개이다.

문제 풀이의 주요 로직은 다음과 같다.

- A와 B를 각각 늘리고 줄이면서 a * b >= k 인 (a, b) 를 찾는다.
- 길이 N 만큼 B를 가득 채우고, 앞에서부터 a - 1 개만큼 A를 삽입한다. (현재 (a - 1) * b 만큼의 (A, B)쌍이 만들었다.)
- N에서 k - (a - 1) * b을 뺀 인덱스에 'A'를 삽입한다. (교체한 인덱스 뒤의 B 개수만큼 (A, B)쌍이 만들어진다.)



예를 들면 다음과 같다. N=10, K=12 인 예제를 보면, a * b >= K 인 (a, b) 는 (2, 8)이다.

(a - 1)개만큼 A를 채우고, b개만큼 B를 채운다 -> `ABBBBBBBB`

remain = 12 - 8 = 4이고, (n - 1) - remain = 5번째 인덱스에 'A'를 삽입한다. -> `ABBBBABBBB`

## Code

```java
import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 *  No.12970: AB
 *  Hint: Greedy
 */

public class BOJ12970 {
    static int n, k;
    static char[] ans;
    static boolean flag = false;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        ans = new char[n];

        if (k == 0) {   // k == 0이면 그냥 A로 다  채움
            flag = true;
            for (int i = 0; i < n; i++) {
                ans[i] = 'A';
            }
        } else {
            greedy();
        }

        StringBuilder sb = new StringBuilder();
        if (flag) {
            for (int i = 0; i < n; i++) {
                sb.append(ans[i]);
            }
            bw.write(sb.toString());
        } else {
            bw.write("-1");
        }

        bw.close();
        br.close();
    }

    static void greedy() {
        int b = n;
        int a = 0;

        while (a <= n) {
            // (A, B) 쌍을 만들 수 있는 최대 개수는 a * b개
            if (a * b < k) {
                a++;
                b--;
                continue;
            }

            // 문자열을 B로 모두 채움
            Arrays.fill(ans, 'B');

            // a * b <= k 인 a,b의 개수를 찾은 뒤 앞에서부터 a - 1개 만큼 A를 채움
            for (int i = 0; i < a - 1; i++) {
                ans[i] = 'A';
            }

            // 현재 상태에서 (A, B) 쌍의 개수는 (a - 1) * b 개 
            // 맨 오른쪽에 A를 하나 두고, 왼쪽으로 한 칸씩 이동할 때마다 만들 수 있는 (A, B) 쌍의 개수가 하나씩 늘어남.
            // 그래서 remain 만큼의 (A, B) 쌍이 필요하므로 맨 오른쪽에서 왼쪽 방향으로 remain 만큼 떨어진 곳을 A로 변경
            int res = (a - 1) * b;
            int remain = k - res;
            ans[(n - 1) - remain] = 'A';
            flag = true;
            break;
        }
    }
}
```

