## Problem : https://www.acmicpc.net/problem/14395

## Approach

> `BFS`문제이다. 하지만 배열을 사용하여 풀이한다면, 메모리초과를 만날 것이다.
>
> Set 에 방문한 곳들만 추가하여, 메모리를 절약하여야 한다. 
>
> 이것이 가능한 이유는 `1 ~ t`범위 안에 실제로 방문할 수 있는 숫자는 전체 숫자 대비 몇개 안되기 때문이다.

문제 풀이에 앞서 입력에 따라 간단히 처리할 수 있는 부분과, 필요한 로직을 단순화 시켜보자.

- `s == t`일 경우, 0을 출력한다.
- `t == 1`일 경우, s에서 항상 `/`를 하는 것이 가장 빠르게 방문하는 방법이다.
- `s = s - s` 연산은 고려하지 않아도 된다.
  - s가 어떤 수이건 간에, 위의 연산을 취하면 0이 된다. 0에 나머지 연산을 적용하는 것도 의미가 없을 뿐더러, t의 범위가 1이상 이므로 필요없는 연산이 된다.



위에 언급한 것처럼 `*, +, /`에 대해서만 BFS를 수행해주면 된다.

방문표시는 Set을 이용하여 수행하고, 정보를 큐에 저장할 때, 현재 숫자와 현재 숫자를 방문하기까지 사용된 명령어들을 같이 저장한다.

## Code

```java
import java.io.*;
import java.util.*;

/**
 *  No.14395: 4연산
 *  URL: https://www.acmicpc.net/problem/14395
 *  Hint: BFS + Set
 */

public class BOJ14395 {
    static int s, t;
    static HashSet<Integer> visited = new HashSet<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        s = Integer.parseInt(st.nextToken());
        t = Integer.parseInt(st.nextToken());

        if (s == t) {   // s == t
            bw.write("0");
        } else if (t == 1) {    // t == 1이면 무조건 / 하는 것이 제일 빠름
            bw.write("/");
        } else {
            Number ans = bfs(s);
            if (!visited.contains(t)) { // t에 도달하지 못하면
                bw.write("-1");
            } else {    // t에 도달 했다면
                bw.write(ans.preOps);
            }
        }

        bw.close();
        br.close();
    }

    static Number bfs(int start) {
        Queue<Number> q = new ArrayDeque<>();
        q.offer(new Number(start, ""));
        visited.add(start);

        while (!q.isEmpty()) {
            Number cur = q.poll();
            if (cur.me == t) {
                return cur;
            }

            for (int i = 0; i < 3; i++) {
                // *, +, /에 대해 숫자 처리
                long tmp = cur.me;
                if (i == 0) tmp *= tmp;
                else if (i == 1) tmp += tmp;
                else if (i == 2) tmp /= tmp;

                // t보다 크면 의미가 없는 숫자가 됨
                if (tmp > t) continue;
                // 이미 방문한 숫자일 경우
                if (visited.contains((int) tmp)) continue;

                // 사용한 명령어를 추가
                if (i == 0) q.offer(new Number((int) tmp, cur.preOps + "*"));
                else if (i == 1) q.offer(new Number((int) tmp, cur.preOps + "+"));
                else if (i == 2) q.offer(new Number((int) tmp, cur.preOps + "/"));

                // 방문 표시
                visited.add((int) tmp);
            }
        }

        return null;
    }

    static class Number{
        int me;
        String preOps;

        Number(int pre, String preOps) {
            this.me = pre;
            this.preOps = preOps;
        }
    }
}
```

