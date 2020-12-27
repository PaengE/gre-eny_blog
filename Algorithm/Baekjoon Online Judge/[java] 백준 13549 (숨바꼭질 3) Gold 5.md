## 문제 원문 링크 : https://www.acmicpc.net/problem/13549

## Approach

대표적인 BFS문제로 [백준 1679번 숨바꼭질](https://www.acmicpc.net/problem/1697) 문제와 단 한개의 조건을 제외하고 똑같은 문제이다.

1679번에 대한 나의 풀이는 아래 링크에 있다.

[2020/12/21 - \[Algorithm/Baekjoon Online Judge\] - \[java\] 백준 1697 (숨바꼭질) Silver 1](https://gre-eny.tistory.com/29)

> < 문제의 조건 >
>
> 현재 위치가 X 일 때, 1초 후에 X - 1 또는 X + 1로, 0초 후에 2 * X 위치로 갈 수 있다.

처음 큐에 N을 넣은 후 하나씩 뺀다. 뺀 수에 +1, -1, *2를 하여 범위(0 ~ 100,000)를 넘지 않는다면 큐에 넣는다.

다만, 1679번과 달리 2 * X 위치로 가는 것이 시간이 추가적으로 들지 않기 때문에 우선시 해줘야 한다.

(코드의 선후를 바꿔주면 된다.)

## Code

```java
import java.io.*;
import java.util.*;

/**
 *  No.13549: 숨바꼭질 3
 *  Hint: BFS
 */

public class BOJ13549 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int size = Math.max(n, k) * 2 + 1;
        int[] dp = new int[size];

        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{n, 0});

        dp[n] = 0;
        while (!q.isEmpty()) {
            int[] t = q.poll();
            int a = t[0];	// 숫자
            int b = t[1];	// 시간

            if (dp[a] != 0) {
                continue;
            }
            dp[a] = b;

            if (a == k) {
                break;
            }

          // *2 를 한 것을 먼저 큐에 집어넣어 처리되게끔 해준다.
            if (a * 2 < size) {
                q.offer(new int[]{a * 2, b});
            }
            if (a - 1 >= 0) {
                q.offer(new int[]{a - 1, b + 1});
            }
            if (a + 1 < size) {
                q.offer(new int[]{a + 1, b + 1});
            }
        }

        bw.write(String.valueOf(dp[k]));
        bw.close();
        br.close();
    }
}

```

