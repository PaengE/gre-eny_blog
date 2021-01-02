## Problem : https://www.acmicpc.net/problem/1753

## Approach

최단거리를 구하는 문제이다.

> < 문제의 조건 >
>
> ​	1. 단방향 그래프
>
> ​	2. 가중치는 항상 자연수

위의 조건으로 인해 다익스트라(O(Elog(V))를 이용하여 문제 풀이가 가능하다.

다익스트라 알고리즘은 현재 연결된 노드에서 이어진 모든 곳을 일단 방문하여 거리를 계산하여 저장한 후, 새로운 노드와 새로운 간선들이 추가되면, 도달할수 있는 최단거리를 다시 계산하여 거리를 저장한다.

이 포스팅에서는 다익스트라 알고리즘에 대한 설명보다 문제의 풀이 방법을 적었다. 다익스트라 알고리즘이 궁금하다면 다음 링크의 포스트를 보길 바란다.

[안경잡이개발자님의 네이버 블로그 - 다익스트라 알고리즘﻿](https://blog.naver.com/ndb796/221234424646)

가중치가 음수라면 벨만-포드 알고리즘(O(VE))이나 SPFA(O(VE))를 통하여 풀어야 한다.

- 방문을 표시할 boolean[] check 배열,
- 시작점부터 i까지의 최단거리를 저장할 int dist[i] 배열,
- 작은 가중치 순으로 저장하여 사용할 PriorityQueue 등을 사용하였다.

## Code

```java
import java.io.*;
import java.util.*;

/**
 * no.1753: 최단경로
 * description: 다익스트라 알고리즘을 배우는 문제
 * hint: dijkstra algorithm 사용
 */

public class BOJ1753 {
    static int v, e, k;
    static ArrayList<Node>[] list;
    static int[] dist;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        v = Integer.parseInt(st.nextToken());
        e = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(br.readLine());

        list = new ArrayList[v + 1];
        dist = new int[v + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);

        for (int i = 1; i <= v; i++) {
            list[i] = new ArrayList<Node>();
        }

        for (int i = 0; i < e; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            list[start].add(new Node(end, weight));
        }

        StringBuilder sb = new StringBuilder();

        dijkstra(k);

        for (int i = 1; i <= v; i++) {
            if (dist[i] == Integer.MAX_VALUE) {
                sb.append("INF\n");
            } else {
                sb.append(dist[i] + "\n");
            }
        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();
    }

    static void dijkstra(int start) {
        // 초기 시작노드
        PriorityQueue<Node> q = new PriorityQueue<Node>();
        boolean[] check = new boolean[v + 1];
        q.add(new Node(start, 0));
        dist[start] = 0;

        // 각 노드까지 가는 최단 경로 검사
        while (!q.isEmpty()) {
            Node curNode = q.poll();
            int cur = curNode.end;

            // 현재 노드를 방문했다면 continue
            if (check[cur])
                continue;
            check[cur] = true;

            // 현재 노드에서 연결된 노드로 가기까지의 최단경로 계산
            for (Node node : list[cur]) {
                if (dist[node.end] > dist[cur] + node.weight) {
                    dist[node.end] = dist[cur] + node.weight;
                    q.add(new Node(node.end, dist[node.end]));
                }
            }
        }
    }
}

class Node implements Comparable<Node> {
    int end, weight;

    public Node(int end, int weight) {
        this.end = end;
        this.weight = weight;
    }

  // weight 순으로 정렬
    @Override
    public int compareTo(Node o) {
        return weight - o.weight;
    }
}
```

