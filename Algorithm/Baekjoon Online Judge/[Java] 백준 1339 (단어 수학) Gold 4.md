## Problem : https://www.acmicpc.net/problem/1339

## Approach

> `순열(Permutation)` 을 이용한 `완전탐색(BruteForce)` 문제이다.
>
> 나는 입력에 따라 사용된 알파벳 개수를 세어, 조금이라도 시간을 단축시켜봤다. (사용된 알파벳 숫자가 10개면 말짱도루묵...)

주요 로직은 다음과 같다.

- 사용된 알파벳의 개수 A를 센다. (알파벳과 상수 값을 mapping 시킨다.)
- `9 ~ (9 - A)`까지의 숫자를 가지고 순열을 만든다.
- 만든 순열을 각 알파벳에 대입하면서 만들 수 있는 최댓값을 찾는다.

순열 구현과 약간의 숫자처리(문자와 숫자 맵핑)가 필요한 문제였다.

## Code

```java
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  No.1339: 단어 수학
 *  URL: https://www.acmicpc.net/problem/1339
 *  Hint: 순열
 */

public class BOJ1339 {
    static int ans;
    static String[] s;
    static HashMap<Character, Integer> map = new HashMap<>();
    static int[] res;
    static ArrayList<Integer> arr = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());
        AtomicInteger idx = new AtomicInteger(0);
        s = new String[n];

        // 사용된 알파벳 indexing
        for (int i = 0; i < n; i++) {
            s[i] = br.readLine();
            for (int j = 0; j < s[i].length(); j++) {
                map.computeIfAbsent(s[i].charAt(j), k -> idx.getAndIncrement());
            }
        }

        res = new int[10];
        for (int i = 0; i < map.size(); i++) {
            arr.add(9 - i);
        }

        makePermutation(0);

        bw.write(String.valueOf(ans));
        bw.close();
        br.close();
    }

    // 만든 수열로 주어진 알파벳 수 계산
    static void calculation(int[] res) {
        int sum = 0;
        for (int i = 0; i < s.length; i++) {
            int tmp = 0;
            for (int j = 0; j < s[i].length(); j++) {
                tmp *= 10;
                tmp += res[map.get(s[i].charAt(j))];
            }
            sum += tmp;
        }

        // 최댓값 갱신
        if (ans < sum) {
            ans = sum;
        }
    }

    // 순열 만들기
    static void makePermutation(int depth) {
        if (depth == map.size()) {
            calculation(res);
            return;
        }
        
        for (int i = 0; i < map.size() - depth; i++) {
            res[depth] = arr.remove(i);
            makePermutation(depth + 1);
            arr.add(i, res[depth]);
        }
    }
}
```

