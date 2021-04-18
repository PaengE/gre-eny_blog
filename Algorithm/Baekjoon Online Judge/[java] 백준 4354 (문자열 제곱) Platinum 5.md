## Problem : https://www.acmicpc.net/problem/4354

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

주어진 문자열 s의 길이가 6인데, pi[s.length - 1]의 값이 4라면, 문자열 s의 앞 4글자와, 뒷 4글자가 같다는 뜻이다.
또한, 문자열 s를 오른쪽으로 (6-4 = 2)칸 밀어도 앞 4글자는 같다는 뜻이다. 

위의 말은 문자열 s의 제일 앞 (문자열 s의 길이) - (pi[s.length - 1]) 길이 만큼 문자열이 반복된다는 뜻이다.

위의 예에선 문자열 s의 제일 앞 길이 2의 문자열이 반복된다.



그러나 그 중에서도 (s.length % (s.length - pi[s.length - 1]) == 0)일 경우에만 (subString)^n꼴이 될 수 있다.

따라서 이 부분만 예외처리를 해주면 정답코드가 된다.

## Code

```java
import java.io.*;

/**
 *  No.4354: 문자열 제곱
 *  URL: https://www.acmicpc.net/problem/4354
 *  Hint: KMP 알고리즘의 Failure Function을 활용하는 문제 1
 */

public class BOJ4354 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();

        while (true) {
            String str = br.readLine();
            if (str.equals(".")) {
                break;
            }

            int[] pi = getPi(str);
            if (str.length() % (str.length() - pi[str.length() - 1]) != 0) {
                sb.append("1\n");
            } else {
                sb.append((str.length() / (str.length() - pi[str.length() - 1])) + "\n");
            }
        }

        bw.write(sb.toString());
        bw.close();
        br.close();
    }

    static int[] getPi(String p) {
        int length = p.length();
        int[] pi = new int[length];
        int j = 0;

        for (int i = 1; i < length; i++) {
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

