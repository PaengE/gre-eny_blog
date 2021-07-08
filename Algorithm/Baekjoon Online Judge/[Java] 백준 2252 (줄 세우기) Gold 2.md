## Problem : https://www.acmicpc.net/problem/2252

## Approach

> `위상정렬(Topological Sort)` 을 활용하는 기본문제이다.

위상정렬을 구현할 줄 안다면 별다른 처리는 더 필요 없는 문제였다.

일반적인 위상정렬처럼 inDegree 배열을 구성하고, inDegree가 0인 노드들을 큐에 넣고 뽑고를 반복하며 진행한다.

## Code

```java
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 *  No.2252: 줄 세우기
 *  URL: https://www.acmicpc.net/problem/2252
 *  Description: 위상정렬을 배우는 문제
 *  Hint: Topological Sort
 */

public class BOJ2252 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        int[] inDegree = new int[n + 1];

        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            graph.get(a).add(b);
            inDegree[b] += 1;
        }

        Queue<Integer> q = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            if (inDegree[i] == 0) {
                q.offer(i);
            }
        }

        StringBuilder sb = new StringBuilder();
        while (!q.isEmpty()) {
            int node = q.poll();
            sb.append(node + " ");

            for (Integer o : graph.get(node)) {
                inDegree[o] += -1;
                if (inDegree[o] == 0) {
                    q.offer(o);
                }
            }
        }
        bw.write(sb.toString());
        bw.close();
        br.close();
    }
}
```

