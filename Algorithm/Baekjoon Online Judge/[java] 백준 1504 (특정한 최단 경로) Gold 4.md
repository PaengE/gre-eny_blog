## Problem : https://www.acmicpc.net/problem/1504

## Approach

가중치가 음수가 아니므로 다익스트라 알고리즘을 이용하여 문제 풀이가 가능하다.

하지만 특정 루트를 반드시 지나가면서 최단거리를 구해야한다.

A 부터 B까지 가는 최단경로를 구해야 할 때, V1-V2 경로를 무조건 지나야 한다면,

1. A - V1 - V2 - B
2. A - V2 - V1 - B

위의 두 경로가 존재한다. 따라서 위 두 경로의 최소비용을 각각 구하여 더 작은 값을 취하면 된다.

## Code

```java
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * no.1504: 특정한 최단 경로
 * description: 규칙을 만족하는 최단 거리를 구하는 문제
 * hint: v1, v2 를 지나야 한다면 1 - v1(v2)- v2(v1) - n 경로의 최단 거리를 구하면 됨 
 */

public class BOJ1504 {
    static int n, e;
    static ArrayList<Node1504>[] list;
    static int[] dist;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        e = Integer.parseInt(st.nextToken());

        list = new ArrayList[n + 1];
        dist = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            list[i] = new ArrayList<Node1504>();
        }

        for (int i = 0; i < e; i++) {
            st = new StringTokenizer(br.readLine());
            int v1 = Integer.parseInt(st.nextToken());
            int v2 = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            list[v1].add(new Node1504(v2, weight));
            list[v2].add(new Node1504(v1, weight));
        }

        st = new StringTokenizer(br.readLine());
        int v1 = Integer.parseInt(st.nextToken());
        int v2 = Integer.parseInt(st.nextToken());

        long res1 = dijkstra1504(1, v1) + dijkstra1504(v1, v2) + dijkstra1504(v2, n);
        long res2 = dijkstra1504(1, v2) + dijkstra1504(v2, v1) + dijkstra1504(v1, n);

        if (Math.min(res1, res2) >= Integer.MAX_VALUE) {
            bw.write("-1\n");
        } else {
            bw.write(String.valueOf(Math.min(res1, res2)));
        }
        bw.flush();
        br.close();
        bw.close();
    }

    static long dijkstra1504(int start, int end) {
        boolean[] check = new boolean[n + 1];
        PriorityQueue<Node1504> q = new PriorityQueue<Node1504>();
        q.offer(new Node1504(start, 0));

        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;

        while (!q.isEmpty()) {
            Node1504 curNode = q.poll();
            int cur = curNode.end;

            if (check[cur])
                continue;
            check[cur] = true;

            for (Node1504 node : list[cur]) {
                if (dist[node.end] > dist[cur] + node.weight) {
                    dist[node.end] = dist[cur] + node.weight;
                    q.offer(new Node1504(node.end, dist[node.end]));
                }
            }
        }
        return dist[end];
    }
}

class Node1504 implements Comparable<Node1504> {
    int end, weight;

    public Node1504(int end, int weight) {
        this.end = end;
        this.weight = weight;
    }

    @Override
    public int compareTo(Node1504 o) {
        return weight - o.weight;
    }
}

```

