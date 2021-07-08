## Problem : https://www.acmicpc.net/problem/1963

## Approach

> `소수`를 찾는 알고리즘과 `BFS` 개념으로 풀 수 있는 문제이다.
>
> 문제 풀이의 주요 로직은 다음과 같다.

- 먼저 간단하게 `에라토스테네스의 체` 개념을 이용하여 `1 ~ 9999` 사이의 숫자들 중 `소수`를 판별한다.
- 그리고 난 뒤 현재 숫자에서 숫자 하나만 변경하며 `BFS`를 수행한다.



숫자를 하나씩 바꾸는 부분을 구현하는게 꽤 까다로울 수 있다.

String을 사용할까도 생각해봤지만, int형으로 구현하는 것보다 시간상 불리하기 때문에 최대한 지양하려 노력했다.



그래서 현재 숫자를 `1. 숫자가 바뀔 자리의 왼쪽 부분`, `2. 숫자가 바뀔 자리`, `3. 숫자가 바뀔 자리의 오른쪽 부분` 이렇게 3부분으로 나누어 더함으로써 최종 숫자를 만들어냈다.

68 line 에 순서대로 1, 2, 3번을 나타내었다. `division`과 `modular`연산으로 만들어 낼 수 있다.

## Code

```java
import java.io.*;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 *  No.1963: 소수 경로
 *  URL: https://www.acmicpc.net/problem/1963
 *  Hint: 에라토스테네스의 체 + BFS
 */

public class BOJ1963 {
    static boolean[] prime = new boolean[10000];    // false가 소수
    static boolean[] visited;
    static int[] cnt;
    static int[] d = {1000, 100, 10, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int t = Integer.parseInt(br.readLine());
        checkPrimeNumber();

        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            visited = new boolean[10000];
            cnt = new int[10000];

            bfs(start, end);

            if (!visited[end]) {
                sb.append("Impossible\n");
            } else {
                sb.append(cnt[end] + "\n");
            }
        }

        bw.write(sb.toString());
        bw.close();
        br.close();
    }

    static void bfs(int start, int end) {
        Queue<Integer> q = new ArrayDeque<>();
        q.offer(start);
        visited[start] = true;

        while (!q.isEmpty()) {
            int cur = q.poll();

            // end에 도달하면 종료
            if (cur == end) {
                return;
            }

            for (int i = 0; i < 4; i++) {
                int val = cur / d[i] / 10;  // 바뀔 숫자 왼쪽 부분
                int mod = cur % d[i];       // 바뀔 숫자 오른쪽 부분

                for (int j = 0; j <= 9; j++) {
                    if (i == 0 && j == 0) { // 천의 자리가 0으로 시작하면 안되므로
                        continue;
                    }

                    int next = (val * d[i] * 10) + (j * d[i]) + mod;
                    if (!visited[next] && !prime[next]) {   // 다음 숫자를 방문하지 않았고, 소수이면 큐에 추가
                        q.offer(next);
                        visited[next] = true;
                        cnt[next] = cnt[cur] + 1;
                    }
                }
            }
        }
    }

    // 에라토스테네스의 체를 이용하여 1 ~ 10000 까지 소수판별
    static void checkPrimeNumber() {
        prime[0] = prime[1] = true;

        for (int i = 2; i * i < 10000; i++) {
            if (!prime[i]) {    // i가 소수라면
                for (int j = i * i; j < 10000; j += i) {
                    prime[j] = true;
                }
            }
        }
    }
}
```

