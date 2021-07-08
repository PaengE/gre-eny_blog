## Problem : https://www.acmicpc.net/problem/12904

## Approach

> `Greedy`한 문제이다.

주어진 문자열 S -> T를 만드는 것이 아니라, 규칙을 역으로 적용하여 T를 S길이와 같은 문자열로 만들고, 그 문자열과 S를 비교하여 같은지를 체크하면 된다.



주요 로직은 다음과 같다.

- 문자열 T의 맨 오른쪽 글자가 'A'라면 그냥 삭제한다.
- 문자열 T의 맨 오른쪽 글자가 'B'라면 삭제한 뒤, 뒤집는다.

위 과정을 문자열 S 의 길이랑 같아질 때까지 반복한다. 길이가 같아지면 서로를 비교해서 같다면 1을, 다르다면 0을 출력하면 된다.

## Code

```java
import java.io.*;

/**
 *  No.12094: A와 B
 *  Hint: Greedy
 */

public class BOJ12904 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        String s = br.readLine();
        String t = br.readLine();

        boolean flag = delete(s, t);
        if (flag) {
            bw.write("1");
        } else {
            bw.write("0");
        }

        bw.close();
        br.close();
    }

    static boolean delete(String s, String t) {
        StringBuilder sb = new StringBuilder(t);

        // 비교 문자열과 길이가 같아질 때까지
        while (s.length() < sb.length()) {
            char c = sb.charAt(sb.length() - 1);
            sb.deleteCharAt(sb.length() - 1);   // 마지막 문자를 삭제

            if (c == 'B') { // 마지막 문자가 B였다면 문자열 뒤집기
                sb.reverse();
            }
        }

        // 길이가 서로 같아졌을 때 비교
        if (s.equals(sb.toString())) {
            return true;
        } else {
            return false;
        }
    }
}
```

