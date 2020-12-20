## 문제 원문 링크 : https://www.acmicpc.net/problem/11779

## Approach

대표적인 Dijkstra 알고리즘을 활용한 문제이다.

문제에서 요구했듯이 최단거리 cost뿐만 아니라 경로 또한 구해야 하므로 최단거리 cost를 업데이트 할 때, 이전 도시가 무엇인지만 별도로 저장해두면 간단하게 구현된다. 이전 노드가 0이라면 그 때의 마을은 시작 마을이 된다.

경로 출력은 Stack 자료형을 이용하면 편하다. (end부터 넣고 start부터 출력하므로)

## Code

```java
import java.io.*;
import java.util.*;

/**
 *  No.11779: 최소비용 구하기 2
 *  URL: https://www.acmicpc.net/problem/11779
 *  Description: 간선에 가중치가 있을 때 최단경로를 출력하는 문제
 *  Hint: Dijkstra + Graph
 */

public class BOJ11779 {
    static ArrayList<Node>[] graph;
    static int n, m, count;
    static int[] dist, preCity;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        m = Integer.parseInt(br.readLine());
        graph = new ArrayList[n + 1];
        preCity = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            graph[start].add(new Node(end, cost));
        }

        st = new StringTokenizer(br.readLine());
        int s = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());

        dijkstra(s);

        StringBuilder sb = new StringBuilder();
        sb.append(dist[e] + "\n");

      // 경로 역추적
        Stack<Integer> stk = new Stack<>();
        stk.push(e);
        while (preCity[e] != 0) {
            count += 1;
            stk.push(preCity[e]);
            e = preCity[e];
        }

        sb.append((count + 1) + "\n");
        while (!stk.isEmpty()) {
            sb.append(stk.pop() + " ");
        }

        bw.write(sb.toString());
        bw.close();
        br.close();
    }
  
      // 다익스트라 알고리즘
    static void dijkstra(int start) {
        Queue<Node> q = new LinkedList<>();
        dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);

        dist[start] = 0;
        q.offer(new Node(start, 0));

        while (!q.isEmpty()) {
            Node curNode = q.poll();
            int cur = curNode.to;

            for (Node next : graph[cur]) {
              // 최단거리 cost를 업데이트
                if (dist[next.to] > dist[cur] + next.cost) {
                    dist[next.to] = dist[cur] + next.cost;
                  // 이전 마을을 기록
                    preCity[next.to] = cur;
                    q.offer(new Node(next.to, dist[next.to]));
                }
            }
        }
    }

    static class Node implements Comparable<Node> {
        public int to, cost;

        Node(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node o) {
            return cost - o.cost;
        }
    }
}

```

