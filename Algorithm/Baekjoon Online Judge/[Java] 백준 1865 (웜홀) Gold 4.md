## Problem : https://www.acmicpc.net/problem/1865

## Approach

> 문제에서 `음수 사이클`이 있는지를 물어보기에, 당연스럽게 `벨만-포드 알고리즘`을 시도해봤다.
>
> `벨만-포드 알고리즘`은 시간복잡도가 O(VE) 인데, 간선의 개수가 V^2에 근사할 수 있으므로 최대 O(V^3)의 시간복잡도를 가진다.
>
> 이는 `다익스트라 알고리즘`의 시간복잡도인 O(V^2)보다 크지만, 음수 사이클을 판별해야 하는 문제에서는 다익스트라를 사용할 수 없으니 `벨만-포드 알고리즘`을 사용하여야 한다.

`벨만-포드 알고리즘`에 대해서는 자세히 설명하지 않겠다. 

간단히 왜 노드개수 V번 만큼 갱신을 시도하는지는 다음과 같다.

- 시작노드에서 특정노드로 도달하기 위해 거쳐가는 엣지의 개수는 최대 `V - 1`개이다.
- 음수 사이클이 만약 없다면, 최대 `V - 1`번 까지 반복이 됐을 때 더 이상 최소비용의 변경이 없어야 한다.
- 하지만 그 상태에서 1번의 반복을 더 수행 했을 때, 최소비용의 변경이 감지된다면? 음수사이클이 존재한다고 볼 수 있다.

그래서 (V - 1)번 수행하고, 마지막 1번에서 음수사이클을 체크하게끔 분리하여 코드를 작성할 수도 있으나

나와 같은 경우엔 V번 수행하며, 마지막까지(V번째에도) 변경이 있는지를 판단하는 것을 동일한 반복문 내에서 처리하였다.

## Code

```java
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 *  No.1865: 웜홀
 *  URL: https://www.acmicpc.net/problem/1865
 *  Hint: 벨만포드 알고리즘(음의 사이클 찾기)
 */

public class BOJ1865 {
    static final int INF = 500 * 10000;
    static ArrayList<ArrayList<Edge>> graph;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();

        int tc = Integer.parseInt(br.readLine());
        while (tc-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            graph = new ArrayList<>();
            for (int i = 0; i <= n; i++) {
                graph.add(new ArrayList<>());
            }

            for (int i = 0; i < m + w; i++) {
                st = new StringTokenizer(br.readLine());
                int s = Integer.parseInt(st.nextToken());
                int e = Integer.parseInt(st.nextToken());
                int t = Integer.parseInt(st.nextToken());
                if (i < m) {
                    graph.get(e).add(new Edge(s, t));
                    graph.get(s).add(new Edge(e, t));
                } else {
                    graph.get(s).add(new Edge(e, t * -1));
                }
            }

            boolean minusCycle = false;
            for (int i = 1; i <= n; i++) {
                if (bellmanFord(i, n)) {
                    minusCycle = true;
                    sb.append("YES\n");
                    break;
                }
            }
            if (!minusCycle) {
                sb.append("NO\n");
            }
        }

        bw.write(sb.toString());
        bw.close();
        br.close();
    }

    static boolean bellmanFord(int start, int nodeCount) {
        int[] dist = new int[nodeCount + 1];
        Arrays.fill(dist, INF);
        dist[start] = 0;
        boolean update = false;

        // (정점개수)번 동안 수행
        for (int i = 1; i <= nodeCount; i++) {
            update = false;

            // 모든 간선 검사
            for (int j = 1; j <= nodeCount; j++) {
                for (Edge cur : graph.get(j)) {
                    if (dist[j] != INF && dist[cur.e] > dist[j] + cur.t) {
                        dist[cur.e] = dist[j] + cur.t;
                        update = true;
                    }
                }
            }
            if (!update) {
               break;
            }
        }
        return update;
    }

    static class Edge{
        int e, t;

        Edge(int e, int t) {
            this.e = e;
            this.t = t;
        }
    }
}
```

