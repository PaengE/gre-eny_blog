## Problem : https://www.acmicpc.net/problem/1774

## Approach

> `MST(Minimum Spanning Tree)` 를 응용한 문제이다.
>
> 이미 사용된 간선이 존재할 때, 나머지 간선들로 MST를 구성하면 된다.

나는 크루스칼 알고리즘을 사용하였다.

이미 사용된 간선들을 union으로 묶은 뒤, 간선의 길이가 작은 것부터 PQ에서 꺼내 연결하였다.

연결할 때마다 res 변수에 간선의 길이를 저장했고, 모두 연결됐다면 break를 걸었다.

## Code

```java
import java.io.*;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * No.1774: 우주신과의 교감
 * Description: 이미 사용된 간선이 있을 때 최소비용으로 나머지를 완성하는 문제
 * URL: https://www.acmicpc.net/problem/1774
 * Hint: 이미 사용된 간선은 Union 시킨 후 Kruskal 알고리즘 적용
 */

public class BOJ1774 {
    static int n, m;
    static int[][] god;
    static int[] p;
    static PriorityQueue<Edge> pq;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        god = new int[n][2];
        pq = new PriorityQueue<>();
        p = new int[n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            god[i][0] = Integer.parseInt(st.nextToken());
            god[i][1] = Integer.parseInt(st.nextToken());

            p[i] = i;
        }

        // 한 정점에서 각 정점까지의 거리를 계산하여 PriorityQueue 에 삽입
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double distance = Math.sqrt(Math.pow(god[i][0] - god[j][0], 2) + Math.pow(god[i][1] - god[j][1], 2));

                pq.offer(new Edge(i, j, distance));
            }
        }

        // 주어진 간선은 미리 Union
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;

            union(p, x, y);
        }

        double ans = kruskal(n);

        bw.write(String.format("%.2f", ans));
        bw.flush();
        br.close();
        bw.close();
    }

    // kruskal 알고리즘
    public static double kruskal(int nodeCount) {
        int cnt = m;
        double res = 0.0;

        while (!pq.isEmpty()) {
            Edge edge = pq.poll();

            if (findSet(p, edge.v1) != findSet(p, edge.v2)) {
                res += edge.weight;

                // 모두 연결됐으면 break
                if (++cnt == nodeCount) {
                    break;
                }

                // v1 set 과 v2 set 을 합침
                union(p, edge.v1, edge.v2);
            }
        }
        return res;
    }

    //x가 속한 집합의 부모를 찾는다.
    public static int findSet(int[] p, int x) {
        if (p[x] == x)
            return x;
        else
            return p[x] = findSet(p, p[x]);
    }
    //x와 y를 같은 집합으로 합친다.(노드 번호가 작은 것이 위로 가게끔)
    public static void union(int[] p, int x, int y) {
        if (x < y){
            p[findSet(p, y)] = findSet(p, x);
        }
        else{
            p[findSet(p, x)] = findSet(p, y);
        }

    }

    static class Edge implements Comparable<Edge> {
        int v1, v2;
        double weight;

        Edge(int v1, int v2, double weight) {
            this.v1 = v1;
            this.v2 = v2;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge o) {
            return Double.compare(this.weight, o.weight);
        }
    }
}
```