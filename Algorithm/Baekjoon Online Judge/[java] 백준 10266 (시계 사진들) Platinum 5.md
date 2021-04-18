## Problem : https://www.acmicpc.net/problem/10266

## Approach

> KMP 알고리즘의 실패함수(Failure Function)를 연습할 수 있는 문제이다.
>
> KMP 알고리즘의 자세한 내용은 다음 링크를 참조하면 좋을것이다.
>
> https://bowbowbow.tistory.com/6#comment5168448

문자열 길이가 360,000인 배열을 구성하고, 입력으로 주어진 곳의 인덱스만 1로 표시를 한 후, 일자로 펴본다고 생각하면 대략 `000010...` 이러한 순서를 가진 배열이 될 것이다.

그렇다면 입력이 각각 c1, c2 라고 하였을 때, c1을 두개 이어붙인 배열에서 c2를 찾는 방식으로 문제를 풀 수 있다. 주어진 입력이 문자열이 아니더라도, 문자열과 유사한 특성을 가진 입력이므로 kmp 알고리즘을 사용하여 문제를 풀 수 있다.

1. c2를 pattern 문자열로 간주하고, 실패함수(Failure Function) pi를 구성한다.
2. c1을 두개 이어 붙인 target 배열에서, pattern 배열인 c2를 찾는다. (kmp 알고리즘을 활용하여)

## Code

```java
import java.io.*;
import java.util.StringTokenizer;

/**
 *  No.10266: 시계 사진들
 *  URL: https://www.acmicpc.net/problem/10266
 *  Hint: KMP 알고리즘을 응용한 문제
 */

public class BOJ10266 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        int[] c1 = new int[720000];
        int[] c2 = new int[360000];

        StringTokenizer st1 = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            int t = Integer.parseInt(st1.nextToken());
            c1[t] = 1;
            c1[t + 360000] = 1;
        }
        StringTokenizer st2 = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            c2[Integer.parseInt(st2.nextToken())] = 1;
        }

        bw.write(kmp(c1, c2));

        bw.close();
        br.close();
    }

    static String kmp(int[] target, int[] pattern) {
        int[] pi = getPi(pattern);

        int j = 0;
        for (int i = 0; i < target.length; i++) {
            while (j > 0 && target[i] != pattern[j]) {
                j = pi[j - 1];
            }
            if (target[i] == pattern[j]) {
                if (j == pattern.length - 1) {
                    return "possible";
                } else {
                    ++j;
                }
            }
        }
        return "impossible";
    }

    static int[] getPi(int[] pattern) {
        int[] pi = new int[pattern.length];

        int j = 0;
        for (int i = 1; i < pattern.length; i++) {
            while (j > 0 && pattern[i] != pattern[j]) {
                j = pi[j - 1];
            }
            if (pattern[i] == pattern[j]) {
                pi[i] = ++j;
            }
        }
        return pi;
    }
}

```

