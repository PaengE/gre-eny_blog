## Problem : https://www.acmicpc.net/problem/1305

## Approach

> KMP 알고리즘의 실패함수(Failure Function)를 연습할 수 있는 문제이다.
>
> KMP 알고리즘의 자세한 내용은 다음 링크를 참조하면 좋을것이다.
>
> https://bowbowbow.tistory.com/6#comment5168448
>
> 여기서 사용할 내용은 실패함수(Failure Function) pi를 활용한다.
>
> 실패함수 pi는 간단히 말하면, i길이의 문자열 p 에서 접두사와 접미사가 같은 최대길이를 저장해 놓는다.
>
> 예를들어 "ABABA"의 pi[4] 는 3(ABA)이고, "ABABAB"의 pi[5] 는 4(ABAB)이다.(각각 ABABA와 ABABAB가 아니게끔 구현해야한다. -> i를 1부터 시작하면 된다.)

문제에서는 무조건 반복되는 문자열이 입력으로 주어지므로, 간단히 실패함수(Failure Function)를 활용하여 pi배열을 구한 후, 주어진 문자열 s에 대해 s.length - pi[s.length - 1] 이 `반복되는 문자열의 길이`가 된다.

## Code

```java
import java.io.*;

/**
 *  No.1305: 광고
 *  URL: https://www.acmicpc.net/problem/1305
 *  Hint: KMP의 failure function을 활용하는 문제 2
 */

public class BOJ1305 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int L = Integer.parseInt(br.readLine());
        String str = br.readLine();
        int[] pi = getPi(str);

        bw.write(String.valueOf(L - pi[str.length() - 1]));
        bw.close();
        br.close();
    }

    static int[] getPi(String p) {
        int[] pi = new int[p.length()];

        int j = 0;
        for (int i = 1; i < p.length(); i++) {
            while (j > 0 && p.charAt(i) != p.charAt(j)) {
                j = pi[j - 1];
            }

            if (p.charAt(i) == p.charAt(j)) {
                pi[i] = ++j;
            }
        }
        return pi;
    }
}

```

