## Problem : https://www.acmicpc.net/problem/1248

## Approach

> `Backtracking`을 이용하여 `완전탐색(BruteForce)`를 수행하는 문제이다.
>
> 이 문제에서 주의할 점을 좀 적어보겠다.
>
> 1. 중복된 수로 수열을 만들 수 있다.
> 2. 숫자 0 과 문자 '0'은 다르다.
> 3. 수열을 완전히 만들고 검사하는 것이 아니라, 만드는 과정에서 검사가 이루어져야 한다.

먼저 주어진 문자열로 `i ~ j`까지의 합의 상태를 나타낸 `s[i][j]`를 구성한다.

그리고 백트랙킹을 수행하며 주어진 숫자가 수열에 포함될 수 있는지를 검사하며 수열을 만들어 나간다.

스페셜 저지인 문제로 여러 답중 하나만 구하면 되므로 답을 하나 찾았다면 모든 재귀를 끝내도록 하였다.

주의점 3번의 경우에는 TLE 문제를 피하기 위함이다.

## Code

```java
import java.io.*;
import java.util.ArrayList;

/**
 *  No.1248: 맞춰봐
 *  URL: https://www.acmicpc.net/problem/1248
 *  Hint: BruteForce(Backtracking) + TL 처리
 */

public class BOJ1248 {
    static int n;
    static boolean findAnswer;
    static int[] ans;
    static char[][] s;
    static ArrayList<Integer> list = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        n = Integer.parseInt(br.readLine());
        String str = br.readLine();
        init(str);

        // Backtracking
        backtracking(0);

        for (int i = 0; i < n; i++) {
            bw.write(ans[i] + " ");
        }
        bw.close();
        br.close();
    }

    static void backtracking(int depth) {
        if (depth == n) {
            findAnswer = true;
            return;
        }

        for (int i = 0; i < 21; i++) {
            if (findAnswer) {   // 정답인 걸 하나 찾으면 종료
                return;
            }

            ans[depth] = list.get(i);
            // 검사를 이 단계에서 해야만 TL을 피할 수 있다.
            if (isCorrect(depth)) {
                backtracking(depth + 1);
            }
        }
    }

    // 만든 수열이 유효한 지 주어진 문자열과 비교
    static boolean isCorrect(int idx){
        for (int i = 0; i <= idx; i++) {
            int sum = 0;
            for (int j = i; j <= idx; j++) {
                sum += ans[j];
                char op = sum == 0 ? '0' : (sum > 0 ? '+' : '-');
                if (s[i][j] != op) {
                    return false;
                }
            }
        }
        return true;
    }

    // 메모리 초기화
    static void init(String str) {
        s = new char[n][n];
        ans = new int[n];

        int idx = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                s[i][j] = str.charAt(idx++);
            }
        }

        for (int i = -10; i <= 10; i++) {
            list.add(i);
        }
    }
}
```