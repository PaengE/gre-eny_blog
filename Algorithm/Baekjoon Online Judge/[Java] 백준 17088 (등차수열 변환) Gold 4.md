## Problem : https://www.acmicpc.net/problem/17088



## Approach

> 등차수열의 `공차` 개념을 이용한 `BruteForce(완전탐색)`로 문제를 풀이하였다.



수열의 각 요소들에 `-1`, `+1`연산을 `최대 1번` 수행할 수 있다고 한다. 주의할 점은 `0번` 수행해도 된다는 것이다.

수열에서 연속된 두 요소의 차를 `공차` 라고 한다. `직전 숫자`, `현재 숫자`, `공차`를 알면 등차수열인지 아닌지 판별할 수 있다.



문제 풀이의 주요 로직은 다음과 같다.

- 수열의 0번째 요소와 1번째 요소를 비교하여 `공차`를 구한다.
  - 이 때, 나올 수 있는 경우의 수는 총 9가지이다.
  - 0번째 숫자를 -1, +0, +1 한 숫자와 1번째 숫자를 -1, +0, +1 한 숫자가 존재하기 때문이다.
- 수열의 2번째 요소부터 `(직전 숫자, 현재 숫자, 공차)`를 이용하여 등차수열의 조건을 만족하는 경우에만 재귀를 이어나간다.



주의할 점으로, 전체 수열의 길이가 2 이하라면 그 수열은 무조건 등차수열이다.

따라서 길이가 1일 경우에는 예외처리를 해주었고, 길이가 2일 경우에는 위 로직대로 적용하여도 등차수열임이 판별되기 때문에 따로 처리해주지는 않았다.



## Code

```java
import java.io.*;
import java.util.StringTokenizer;

/**
 *  No.17088: 등차수열 변환
 *  Hint: BruteForce
 */

public class BOJ17088 {
    static int n, ans = Integer.MAX_VALUE;
    static int[] arr;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        n = Integer.parseInt(br.readLine());
        arr = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        solve();

        bw.write(ans == Integer.MAX_VALUE ? "-1" : String.valueOf(ans));
        bw.close();
        br.close();
    }

    static void solve() {
        if (n == 1) {
            ans = 0;
            return;
        }

        bruteForce(2, arr[1] - 1, (arr[0] - 1) - (arr[1] - 1), 2);
        bruteForce(2, arr[1], (arr[0] - 1) - arr[1], 1);
        bruteForce(2, arr[1] + 1, (arr[0] - 1) - (arr[1] + 1), 2);

        bruteForce(2, arr[1] - 1, arr[0] - (arr[1] - 1), 1);
        bruteForce(2, arr[1], arr[0] - arr[1], 0);
        bruteForce(2, arr[1] + 1, arr[0] - (arr[1] + 1), 1);

        bruteForce(2, arr[1] - 1, (arr[0] + 1) - (arr[1] - 1), 2);
        bruteForce(2, arr[1], (arr[0] + 1) - arr[1], 1);
        bruteForce(2, arr[1] + 1, (arr[0] + 1) - (arr[1] + 1), 2);
    }

    static void bruteForce(int idx, int prev, int diff, int cnt) {
        if (idx == arr.length) {
            ans = Math.min(ans, cnt);
            return;
        }

        if (prev - (arr[idx] - 1) == diff) {
            bruteForce(idx + 1, arr[idx] - 1, diff, cnt + 1);
        }

        if (prev - arr[idx] == diff) {
            bruteForce(idx + 1, arr[idx], diff, cnt);
        }

        if (prev - (arr[idx] + 1) == diff) {
            bruteForce(idx + 1, arr[idx] + 1, diff, cnt + 1);
        }
    }
}
```

