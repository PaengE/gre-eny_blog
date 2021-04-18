## Problem : https://www.acmicpc.net/problem/1647

## Approach

> MST(Minimum Spanning Tree) 의 개념을 응용한 문제이다.
>
> MST를 적용한 그래프는 `임의의 두 점 사이를 연결하는 경로는 유일`하다.
>
> 따라서, 그 중 하나의 간선을 제거하면 MST 가 2개로 분리된다. (분리된 각 그래프도 MST이다.)

- 먼저 MST를 적용한다. (Prim or Kruskal 을 사용하면 된다. 나는 Kruskal 을 사용했다.)
- 그리고 가장 마지막에 추가된 간선을 제거한다. (이 간선이 MST를 구성하는 간선 중 cost가 가장 크다.)
  (간선의 cost가 작은 것부터 연결하기 때문이다.)

## Code

```java
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 *  No.1647: 도시 분할 계획
 *  URL: https://www.acmicpc.net/problem/1647
 *  Hint: MST(Minimum Spanning Tree) kruskal or prim
 *
 *  일단 MST로 모든 집을 연결한 후, 가장 큰 유지비가 드는 경로를 없애면 두 그룹으로 나뉘어짐
 *  가장 큰 유지비가 드는 경로는 가장 마지막에 연결된 경로
 */

public class BOJ1647 {
    static int n, m;
    static int[] parent;
    static ArrayList<Edge> edgeList = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        parent = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            parent[i] = i;
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int v1 = Integer.parseInt(st.nextToken());
            int v2 = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            edgeList.add(new Edge(v1, v2, cost));
            edgeList.add(new Edge(v2, v1, cost));
        }
        Collections.sort(edgeList);

        int ans = kruskal();

        bw.write(String.valueOf(ans));
        bw.close();
        br.close();
    }

    static int kruskal() {
        int res = 0;
        int biggestCost = 0;

        for (int i = 0; i < edgeList.size(); i++) {
            Edge e = edgeList.get(i);

            // 두 집이 연결되어 있지 않다면
            if (find(e.src) != find(e.dst)) {
                union(e.src, e.dst);    // 연결
                res += e.cost;          // cost 를 갱신
                biggestCost = e.cost;   // 가장 마지막으로 연결된 집 사이의 유지비가 가장 큼
            }
        }

        return res - biggestCost;
    }

    // union-find
    static int find(int x) {
        if (x == parent[x]) {
            return x;
        }

        return parent[x] = find(parent[x]);
    }
    static void union(int x, int y) {
        x = find(x);
        y = find(y);

        if (x < y) {
            parent[y] = x;
        } else {
            parent[x] = y;
        }
    }

    // cost 오름차순
    static class Edge implements Comparable<Edge>{
        int src, dst, cost;

        Edge(int src, int dst, int cost) {
            this.src = src;
            this.dst = dst;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge o) {
            return cost - o.cost;
        }
    }
}
```

