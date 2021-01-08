## Problem : https://www.acmicpc.net/problem/9019

## Approach

BFS + DP + Tracing 을 사용하여 풀이한 문제이다.

필자는 위의 과정보다 숫자를 만드는 작업이 더 어려웠다.(어렵다기보다 생각할 조건들이 많아서 귀찮았다.)

> 1. D: D 는 n을 두 배로 바꾼다. 결과 값이 9999 보다 큰 경우에는 10000 으로 나눈 나머지를 취한다. 그 결과 값(2n mod 10000)을 레지스터에 저장한다.
> 2. S: S 는 n에서 1 을 뺀 결과 n-1을 레지스터에 저장한다. n이 0 이라면 9999 가 대신 레지스터에 저장된다.
> 3. L: L 은 n의 각 자릿수를 왼편으로 회전시켜 그 결과를 레지스터에 저장한다. 이 연산이 끝나면 레지스터에 저장된 네 자릿수는 왼편부터 d2, d3, d4, d1이 된다.
> 4. R: R 은 n의 각 자릿수를 오른편으로 회전시켜 그 결과를 레지스터에 저장한다. 이 연산이 끝나면 레지스터에 저장된 네 자릿수는 왼편부터 d4, d1, d2, d3이 된다.

문제에서 준 조건대로 숫자를 구성한 후 BFS를 수행하면 된다. BFS를 진행하는 와중에 지금까지 입력된 커맨드들에 현재 커맨드를 추가하여 DP 배열을 구성하면 된다.

## Code

```java
import java.io.*;
import java.util.*;

/**
 *  No.9019: DSLR
 *  URL: https://www.acmicpc.net/problem/9019
 *  Hint: BFS + DP + Tracing
 */

public class BOJ9019 {
    static boolean[] visited;
    static String[] result;
    static Queue<String> q;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();

        int t = Integer.parseInt(br.readLine());

        for (int i = 0; i < t; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            String a = st.nextToken();
            String b = st.nextToken();

            bfs(a, b);

            sb.append(result[Integer.parseInt(b)] + "\n");

        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();

    }

    static void bfs(String a, String b) {
        visited = new boolean[10000];
        result = new String[10000];
        q = new LinkedList<>();
        Arrays.fill(result, "");

        q.offer(a);
        visited[Integer.parseInt(a)] = true;

        while (!visited[Integer.parseInt(b)]) {
            String s = q.poll();
            int ns = Integer.parseInt(s);

            for (int i = 0; i < 4; i++) {
              // 커맨드에 따른 숫자 구성
                String t = "";
                if (i == 0) {
                    t = String.valueOf((Integer.parseInt(s) * 2) % 10000);
                    while (t.length() < 4) {
                        t = "0" + t;
                    }
                } else if (i == 1) {
                    if (ns == 0) {
                        t = "9999";
                    } else {
                        t = String.valueOf(Integer.parseInt(s) - 1);
                        while (t.length() < 4) {
                            t = "0" + t;
                        }
                    }
                } else if (i == 2) {
                    while (s.length() < 4) {
                        s = "0" + s;
                    }
                    t = s.substring(1, s.length()) + s.charAt(0);
                } else if (i == 3) {
                    while (s.length() < 4) {
                        s = "0" + s;
                    }
                    t = s.charAt(s.length() - 1) + s.substring(0, s.length() - 1);
                }

                int nt = Integer.parseInt(t);
              // BFS 수행
                if (!visited[nt]) {
                    q.offer(t);
                    visited[nt] = true;

                  // 커맨드를 추가하여 저장
                    if (i == 0) {
                        result[nt] = result[ns] + "D";
                    } else if (i == 1) {
                        result[nt] = result[ns] + "S";
                    } else if (i == 2) {
                        result[nt] = result[ns] + "L";
                    } else if (i == 3) {
                        result[nt] = result[ns] + "R";
                    }
                }
            }
        }
    }
}
```

