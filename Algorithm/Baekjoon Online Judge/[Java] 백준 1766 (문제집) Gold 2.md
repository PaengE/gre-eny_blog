## Problem : https://www.acmicpc.net/problem/1766

## Approach

> 위상정렬 + PriorityQueue 를 활용한 문제이다. 위상정렬을 알고 있다면 꽤 쉬운 문제이다.

문제를 풀이하는데 있어 순서가 존재하므로 위상정렬을 떠올릴 수 있었다.

또한, 지금 풀 수 있는 문제가 여러개일 때, 번호가 작은 문제부터 풀어야 하므로 우선순위 큐를 생각할 수 있겠다.

- 입력을 모두 받을 때까지 inDegree 배열을 구성한다.
- 위상정렬을 수행한다. (inDegree 가 0인 값들을 `PriorityQueue` 에 넣는다.)
- 우선순위 큐는 문제번호가 작은 것이 우선순위가 더 높게 설정한다.

## Code

```java
import java.io.*;
import java.util.*;

/**
 *  No.1766: 문제집
 *  URL: https://www.acmicpc.net/problem/1766
 *  Description: 위상정렬 알고리즘을 변형하여 사전순으로 가장 앞선 위상정렬을 찾는 문제
 *  Hint: PriorityQueue 를 활용
 */

public class BOJ1766 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        ArrayList<PriorityQueue<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new PriorityQueue<>());
        }

        int[] inDegree = new int[n + 1];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            graph.get(a).offer(b);
            inDegree[b] += 1;
        }
        
        // 위상정렬
        StringBuilder sb = new StringBuilder();
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i = 1; i <= n; i++) {
            if (inDegree[i] == 0) {
                pq.offer(i);
            }
        }
        while (!pq.isEmpty()) {
            int cur = pq.poll();
            sb.append(cur).append(" ");

            for (Integer o : graph.get(cur)) {
                inDegree[o] += -1;
                if (inDegree[o] == 0) {
                    pq.offer(o);
                }
            }
        }
        bw.write(sb.toString());
        bw.close();
        br.close();
    }
}
```

