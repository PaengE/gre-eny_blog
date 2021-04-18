## Problem : https://www.acmicpc.net/problem/1197

## Approach

> MST(Minimum Spanning Tree) 를 구하는 데에는 크게 `Prim`과 `Kruskal`알고리즘이 있다.
>
> 이 문제는 간단하게 위 두 알고리즘 중 하나를 택하여 구현만 하면 되는 문제였다.

간단한 로직만 설명하고, 자세한 내용은 다루지 않겠다.

- `Prim`알고리즘은 `간선` 중심 알고리즘이다. 간선을 오름차순으로 정렬한 후, 하나씩 빼서 모든 정점이 연결될 때까지 반복하는 것이다.

- `Kruskal`알고리즘은 `정점` 중심 알고리즘이다. 연결된 정점들으로 닿을 수 있는 간선들 중 가장 작은 간선을 선택하여 두 정점을 연결한다. 연결된 정점으로부터 닿을 수 있는 간선이 있다면, 이 또한 간선들 집합에 추가하여 반복한다.

노드의 개수를 V, 간선의 개수를 E라고 할 때,

간선의 개수가 많을 때는 간선 중심인 `프림`을, 간선이 적은 경우에는 `크루스칼`을 사용하는 것이 효율적이다.

두 알고리즘 모두 사용한 풀이를 아래에 첨부하겠다.

## Code

```java
import java.io.*;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * No.1197: 최소 스패닝 트리(MST: Minimum Spanning Tree)
 * URL: https://www.acmicpc.net/problem/1197
 * Hint: Prim Algorithm 이용
 * --- 간선의 개수가 적으면 크루스칼이 빠르고, 정점의 개수가 적다면 프림이 빠르다 ---
 *
 * - 트리에 연결되지 않은 정점들은 큐에 add되어 있다.
 *
 * - 각 정점들은인접한 정점 중 최소 비용으로 이동가능한 정점을 선택하여 추가한다.
 *
 * 1. 한 정점에서 시작하여 인접한 정점을 잇는 간선 중 가중치가 가장 낮은 간선을 선택한다.
 *  - 선택하여 연결 후 간선을 다시 탐색한다.
 * 2. 정점들이 이어져 있는 간선들을 반복해서 비교하며 가중치가 가장 낮은 간선을 추가한다.
 *
 * 3. 모든 요소가 이어졌다면 총 dis를 반환한다.
 *
 */

public class BOJ1197_Prim {
    static ArrayList<ArrayList<Edge>> graph;
    static boolean[] visited;
    static PriorityQueue<Edge> pq;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int v = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());

        pq = new PriorityQueue<>();
        visited = new boolean[v + 1];
        graph = new ArrayList<>();

        for (int i = 0; i <= v; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < e; i++) {
            st = new StringTokenizer(br.readLine());
            int v1 = Integer.parseInt(st.nextToken());
            int v2 = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            graph.get(v1).add(new Edge(v2, weight));
            graph.get(v2).add(new Edge(v1, weight));
        }

        long answer = prim(1, v);

        bw.write(String.valueOf(answer));
        bw.flush();
        br.close();
        bw.close();
    }

    static long prim(int start, int nodeCount) {
        long res = 0, cnt = 0;

        pq.offer(new Edge(start, 0));

        while (!pq.isEmpty()) {
            // 우선순위 큐를 이용하여 최소 가중치 간선을 꺼냄
            Edge edge = pq.poll();

            // 방문했다면(연결이 돼있다면) continue
            if (visited[edge.node]) {
                continue;
            }

            // 방문표시를 하고(연결을 하고) 연결된 weight 를 총 비용에 더함
            visited[edge.node] = true;
            res += edge.weight;

            // 새로이 연결된 노드에 연결된 노드들 중 방문하지 않은(연결하지 않은) 노드들을 모두 추가
            for (Edge next : graph.get(edge.node)) {
                if (!visited[next.node]) {
                    pq.offer(next);
                }
            }

            // 모두 연결됐으면 break
            if (++cnt == nodeCount) {
                break;
            }
        }
        return res;
    }

    static class Edge implements Comparable<Edge> {
        int node, weight;

        Edge(int node, int weight) {
            this.node = node;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.weight, o.weight);
        }
    }
}

```

```java
import java.io.*;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * No.1197: 최소 스패닝 트리(MST: Minimum Spanning Tree)
 * URL: https://www.acmicpc.net/problem/1197
 * Hint: Kruskal Algorithm 이용
 * --- 간선의 개수가 적으면 크루스칼이 빠르고, 정점의 개수가 적다면 프림이 빠르다 ---
 *
 * - 탐욕적인 방법(greedy method) 을 이용하여 그래프의 모든
 *   정점을 최소 비용으로 연결하는 최소 비용을 구하는 것이다.
 *
 * 1. 그래프의 간선들을 가중치의 오름차순으로 정렬한다.
 *
 * 2. 정렬된 간선 리스트에서 순서대로 사이클을 형성하지 않는 간선을 선택한다.
 *
 * 3. 해당 간선을 현재의 MST(최소 비용 신장 트리)의 집합에 추가한다.
 *
 *  << UnionFind 를 이용하여 서로 다른 집합이면서 사이클이 형성되지 않는다면
 *  간선을 추가하고 같은 집합으로 만든다. >>
 *
 */

public class BOJ1197_Kruskal {
    static int[] p;
    static PriorityQueue<Edge> pq;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int v = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());

        p = new int[v + 1];
        pq = new PriorityQueue<>();

        for (int i = 1; i <= v; i++) {
            p[i] = i;
        }

        for (int i = 0; i < e; i++) {
            st = new StringTokenizer(br.readLine());
            int v1 = Integer.parseInt(st.nextToken());
            int v2 = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            pq.offer(new Edge(v1, v2, weight));
        }

        long answer = kruskal(v);

        bw.write(String.valueOf(answer));
        bw.flush();
        br.close();
        bw.close();
    }

    static long kruskal(int nodeCount) {
        long res = 0, cnt = 0;

        while (!pq.isEmpty()) {
            // 우선순위 큐를 이용하여 최소 가중치 간선을 꺼냄
            Edge edge = pq.poll();


            if (findSet(p, edge.v1) != findSet(p, edge.v2)) {
                // 연결된 weight 를 총 비용에 더함
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
        int v1, v2, weight;

        Edge(int v1, int v2, int weight) {
            this.v1 = v1;
            this.v2 = v2;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.weight, o.weight);
        }
    }
}

```

