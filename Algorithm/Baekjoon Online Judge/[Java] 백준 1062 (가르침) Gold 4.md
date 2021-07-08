## Problem : https://www.acmicpc.net/problem/1062

## Approach

> `조합(Combination)`을 이용한 `완전탐색(BruteForce)`문제이다.
>
> 처음에는 조합을 list로 구현하였으나, 시간초과를 받았다.
>
> 그래서 총 알파벳의 개수인 26 크기의 배열을 가지고 사용 여부를 체크하며 조합을 구성하였다.

조합을 구현하기 전에 입력에 따라 간단히 처리할 수 있는 부분들을 짚어보자.

N은 주어진 단어의 개수, K개 만큼 문자를 배울 수 있다. 모든 단어들은 "antic" 5개 문자를 포함한다.
SIZE 는 "antic"을 제외한 나머지 문자들 단어의 사용된 문자들의 개수이다.

- K < 5 라면, "antic" 조차 표현할 수 없기 때문에 0개의 단어를 배울 수 있다.
- K == 26 이라면, 모든 문자를 표현할 수 있기 때문에 N개의 단어를 배울 수 있다.
- SIZE <= K - 5 라면, 모든 문자를 표현할 수 있기 때문에 N개의 단어를 배울 수 있다.



나머지의 경우에는 (SIZE) 개수 중 (K - 5)개를 뽑아서 조합을 만든 후, 그 조합으로 단어를 최대 몇개 표현할 수 있는지를 찾으면 된다.
(이 부분에서 리스트를 썼을 때 시간초과가 났다.)

DFS처럼 사용된 문자들을 체크했다 해제하며, 모든 조합을 고려하면 된다. (완전탐색)

## Code

```java
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

/**
 *  No.1062: 가르침
 *  URL: https://www.acmicpc.net/problem/1062
 *  Hint: 크기 26 배열 + 조합
 */

public class BOJ1062 {
    static int n, k, ans;
    static int size;
    static ArrayList<Character> usedAlphabet;
    static String[] str;
    static HashSet<Character> set;
    static boolean[] used = new boolean[26];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        set = new HashSet<>();  // "antic"을 제외한 알파벳 중 사용된 알파벳들을 저장
        str = new String[n];    // 앞4글자 뒤4글자를 지운 문자열
        for (int i = 0; i < n; i++) {
            String s = br.readLine();
            str[i] = s.substring(4, s.length() - 4);
            for (int j = 0; j < str[i].length(); j++) {
                set.add(str[i].charAt(j));
            }
        }

        init();

        // k가 5보다 작으면 "antic"을 표현할 수없음
        // k == 26 이면 모든 글자를 배울 수 있으므로 모든 글자를 표현 가능
        // size는 "antic"을 제외하고 사용한 알파벳의 개수인데 그게 k-5 보다 작거나 같으면 모든 글자 표현 가능
        if (k < 5 || k == 26 || size <= k - 5) {
            bw.write(k == 26 || size <= k - 5 ? String.valueOf(n) : "0");
            bw.close();
            br.close();
            return;
        }

        combination(5, 0);
        bw.write(String.valueOf(ans));
        bw.close();
        br.close();
    }

    static void combination(int t, int idx) {
        if (t == k) {
            int cnt = 0;
            // 해당 문자열이 알고 있는 문자들로 표현 가능한지를 체크
            for (int i = 0; i < str.length; i++) {
                boolean flag = true;
                for (int j = 0; j < str[i].length(); j++) {
                    if (!used[str[i].charAt(j) - 'a']) {
                        flag = false;
                        break;
                    }
                }

                if (flag) {
                    cnt++;
                }
            }
            ans = Math.max(ans, cnt);
            return;
        }

        for (int i = idx; i < size; i++) {
            used[usedAlphabet.get(i) - 'a'] = true;
            combination(t + 1, i + 1);
            used[usedAlphabet.get(i) - 'a'] = false;
        }
    }

    // "antic"을 제외하고 사용한 알파벳의 집합인 usedAlphabet 리스트를 구성, "antic"을 사용했다고 used배열에 표시
    static void init() {
        set.remove('a');
        set.remove('n');
        set.remove('t');
        set.remove('i');
        set.remove('c');

        usedAlphabet = new ArrayList<>(set);
        size = usedAlphabet.size();

        used[0] = true;
        used['n' - 'a'] = true;
        used['t' - 'a'] = true;
        used['i' - 'a'] = true;
        used['c' - 'a'] = true;
    }
}
```

