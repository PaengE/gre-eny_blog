## Problem : https://www.acmicpc.net/problem/1786

## Approach

> KMP 알고리즘을 활용한 연습문제이다.
>
> KMP 알고리즘에 대한 내용은 여기서 다루지 않고 다음 링크를 참고하면 좋을 것이다. 설명이 정말 명쾌하다.
>
> https://bowbowbow.tistory.com/6#comment5168448

문제 자체는 KMP 알고리즘을 단순히 구현하는 문제이다. 

따라서 KMP 알고리즘의 내용을 이해하고 공부할 수 있는 좋은 연습문제가 될 것이다.

## Code

```java
import java.io.*;
import java.util.ArrayList;

/**
 *  No.1786: 찾기
 *  url: https://www.acmicpc.net/problem/1786
 *  hint: KMP 알고리즘 연습문제
 */

public class BOJ1786 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        String text = br.readLine();
        String pattern = br.readLine();

        ArrayList<Integer> ans = kmp(text, pattern);

        bw.write(ans.size() + "\n");
        for (int a : ans) {
            bw.write((a + 1) + " ");
        }
        bw.close();
        br.close();
    }

    static int[] getPi(String pattern) {
        int pLength = pattern.length();
        int[] pi = new int[pLength];
        int j = 0;

        // i는 접미사를 다룸, j는 접두사를 다룸룸
       for (int i = 1; i < pLength; i++) {
            while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
                j = pi[j - 1];  // 건너 뜀(재할당)
            }
            if (pattern.charAt(i) == pattern.charAt(j)) {
                pi[i] = ++j;
            }
        }
        return pi;
    }

    static ArrayList<Integer> kmp(String text, String pattern) {
        ArrayList<Integer> res = new ArrayList<>();
        int tLength = text.length();
        int pLength = pattern.length();
        int[] pi = getPi(pattern);

        int j = 0;
        for (int i = 0; i < tLength; i++) {
            while (j > 0 && text.charAt(i) != pattern.charAt(j)) {
                j = pi[j - 1];  // 건너 뜀(재할당)
            }
            if (text.charAt(i) == pattern.charAt(j)) {
                if (j == pLength - 1) { // pattern의 끝까지 같으면 정답에 추가
                    res.add(i - pLength + 1);
                    j = pi[j];
                } else {    // 탐색 인덱스를 증가시킴
                    j++;
                }
            }
        }
        return res;
    }
}

```

