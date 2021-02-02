## Problem : https://programmers.co.kr/learn/courses/30/lessons/42861

## Approach

MST(Minimum Spanning Tree) 최소신장트리 관한 문제이다.

MST 풀이 방법엔 프림(O(N^2)), 크루스칼(O(ElogE)) 알고리즘이 있다.

일반적으로 dense graph일 경우 프림을, sparse graph일 경우 크루스칼 알고리즘을 사용한다.

나는 크루스칼 알고리즘을 사용하여 문제를 풀었다. 크루스칼 알고리즘을 대략적으로 설명하자면 다음과 같다.

> - 모든 간선을 비용 오름차순으로 정렬한다. (여기서는 PrioriryQueue 사용)
> - 큐에서 하나씩 간선을 꺼내 연결된 두 노드의 부모(parent)를 검사하여, 다르다면 합쳐준다.(union)
> - 큐에 저장된 모든 간선들을 검사했을 때, 알고리즘은 종료된다.

## Code

```java
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.PriorityQueue;

public class ConnectIsland {
    static int[] parent;
    public int solution(int n, int[][] costs) {
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        // 노드 i 의 부모노드 초기화
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }

        // 모든 간선을 cost 오름차순으로 priority queue 에 삽입
        for (int i = 0; i < costs.length; i++) {
            pq.offer(new Edge(costs[i][0], costs[i][1], costs[i][2]));
        }

        // 크루스칼 알고리즘 수행
        return kruskal(pq, n);
    }

    // 크루스칼 알고리즘
    private int kruskal(PriorityQueue<Edge> pq, int nodeCount) {
        int res = 0, cnt = 0;

        while (!pq.isEmpty()) {
            Edge edge = pq.poll();

            // 두 노드의 부모가 같지 않다면 간선 cost 를 더하고 하나로 묶음(union)
            if (findSet(edge.v1) != findSet(edge.v2)) {
                res += edge.cost;

                if (++cnt == nodeCount) {
                    break;
                }
                union(edge.v1, edge.v2);
            }
        }

        // 간선 cost 의 합을 리턴
        return res;
    }

    // 노드 x의 부모를 찾고, 저장
    private int findSet(int x) {
        if (parent[x] == x) {
            return x;
        } else {
            return parent[x] = findSet(parent[x]);
        }
    }

    // 두 노드 x, y를 합침(더 작은 숫자가 부모가 되게)
    private void union(int x, int y) {
        if (x < y) {
            parent[findSet(y)] = parent[findSet(x)];
        } else {
            parent[findSet(x)] = parent[findSet(y)];
        }
    }

    // Edge 클래스 (cost 오름차순)
    private class Edge implements Comparable<Edge> {
        private int v1, v2, cost;
        Edge(int s, int e, int cost) {
            this.v1 = s;
            this.v2 = e;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.cost, o.cost);
        }
    }

    @Test
    public void test() {
        Assertions.assertEquals(4
                , solution(4, new int[][]{{0, 1, 1}, {0, 2, 2}, {1, 2, 5}, {1, 3, 1}, {2, 3, 8}}));
    }
}

```

