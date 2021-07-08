## Problem : https://www.acmicpc.net/problem/16938



## Approach

> `조합(Combination)`을 이용한 `BruteForce(완전탐색)`문제이다.
>
> 입력의 크기가 최대 15로 비교적 작으므로 가능한 풀이이다.



캠프에 사용할 문제를 고르는 전체 방법의 수는 `nC2 + nC3 + ... + nCn`이다.

이렇게 나온 결과 조합의 숫자들을 `검증`해주면 된다. 

배열 요소의 `총합`, `최댓값`, `최솟값`은 `Stream`을 사용하여 간결한 코드로 구할 수 있다.



## Code

```java
import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 *  No.16938: 캠프 준비
 *  Hint: BruteForce + 조합 + 백트랙킹
 */

public class BOJ16938 {
    static int n, l, r, x, ans;
    static int[] arr, res;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        l = Integer.parseInt(st.nextToken());
        r = Integer.parseInt(st.nextToken());
        x = Integer.parseInt(st.nextToken());
        arr = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 2; i <= n; i++) {
            res = new int[i];
            combination(n, i, 0, 0, res);
        }

        bw.write(String.valueOf(ans));
        bw.close();
        br.close();
    }

    static void combination(int n, int r, int index, int depth, int[] res) {
        if (depth == r) {
            if (areValidNumbers(res)) {
                ans++;
            }
            return;
        }

        for (int i = index; i < n; i++) {
            res[depth] = arr[i];
            combination(n, r, i + 1, depth + 1, res);
        }
    }

    static boolean areValidNumbers(int[] res) {
        int sum = Arrays.stream(res).sum();
        if (l > sum || sum > r) {
            return false;
        }

        int max = Arrays.stream(res).max().getAsInt();
        int min = Arrays.stream(res).min().getAsInt();
        if (max - min < x) {
            return false;
        }

        return true;
    }
}
```

