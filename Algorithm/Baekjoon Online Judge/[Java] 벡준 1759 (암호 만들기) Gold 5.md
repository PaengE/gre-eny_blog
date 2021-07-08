## Problem : https://www.acmicpc.net/problem/1759

## Approach

> 재귀를 이용하여 `Backtracking`을 사용한 `BruteForce` 문제이다. 

사전 순으로 결과를 얻고 싶어하기 때문에, 백트랙킹을 수행하기 전에 정렬을 선행한다.

모음 최소1개, 자음 최소2개인 암호들만 뽑고 싶기 때문에 자모음의 개수도 세어주어야 한다.

그 외에는 따로 신경쓸 부분이 없었다. 백트랙킹 연습 문제로 알맞는 문제인 것 같다.

## Code

```java
import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 *  No.1759: 암호 만들기
 *  URL: https://www.acmicpc.net/problem/1759
 *  Hint: Backtracking + 자모음 수 세기
 */

public class BOJ1759 {
    static int l, c;
    static char[] alpha, res;
    static StringBuilder sb = new StringBuilder();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        l = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        alpha = new char[c];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < c; i++) {
            alpha[i] = st.nextToken().charAt(0);
        }
        res = new char[l];
        Arrays.sort(alpha);

        backtracking(0,0,0, 0);

        bw.write(sb.toString());
        bw.close();
        br.close();
    }

    static void backtracking(int index, int depth, int jaeum, int moeum) {
        if (depth == l) {
            if (moeum >= 1 && jaeum >= 2) {
                sb.append(res).append("\n");
            }
            return;
        }

        for (int i = index; i < c; i++) {
            res[depth] = alpha[i];
            if (isAEIOU(alpha[i])) {
                backtracking(i + 1, depth + 1, jaeum, moeum + 1);
            } else {
                backtracking(i + 1, depth + 1, jaeum + 1, moeum);
            }
        }
    }

    static boolean isAEIOU(char c) {
        if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
            return true;
        }
        return false;
    }
}
```