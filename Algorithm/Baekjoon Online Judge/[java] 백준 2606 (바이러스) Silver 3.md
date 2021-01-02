## Problem : https://www.acmicpc.net/problem/2606

## Approach

여러 노드가 있을 때, X 라는 노드와 연결된 모든 노드의 개수를 구하면 되는 문제이다.

-> 따라서 순회 문제라는 것을 빠르게 알아내어, 접근해야한다.

DFS, BFS 중 아무거나 사용해도 되지만, 필자는 인접리슽으를 이용한 DFS 재귀 방식으로 풀이하였다.

## Code

```java
import java.io.*;
import java.util.*;

/**
 * no.2606: 바이러스
 * description: BFS 나 DFS 로 그래프를 순회해서 방문할 수 있는 정점을 찾는 문제
 * hint: 인접리스트를 생성 후 DFS 사용
 */

public class BOJ2606 {
    static int count = 0;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());
        int m = Integer.parseInt(br.readLine());
        LinkedList<Integer>[] adjList = new LinkedList[n + 1];
        for (int i = 0; i <= n; i++) {
            adjList[i] = new LinkedList<Integer>();
        }
        boolean[] visited = new boolean[n + 1];

        StringTokenizer st;
      // 인접리스트 구성
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int v1 = Integer.parseInt(st.nextToken());
            int v2 = Integer.parseInt(st.nextToken());
            adjList[v1].add(v2);
            adjList[v2].add(v1);
        }
      // 모든 각각의 노드에 대해 연결된 노드들의 번호를 정렬하는 코드이다
      // (이 문제에서는 실행하지 않아도 된다.)
        for (int i = 1; i <= n; i++) {
            Collections.sort(adjList[i]);
        }

        dfs(adjList, visited, 1);

        bw.write(String.valueOf(count));
        bw.flush();
        br.close();
        bw.close();
    }

    static void dfs(LinkedList<Integer>[] adjList, boolean[] visited, int v) {
      // 방문했음을 표시하고
        visited[v] = true;

      // 현재 노드 v 에 연결된 모든 노드들에 대하여 방문되지 않았다면 재귀를 수행한다.
      // listIterator()를 사용하지 않고 for (int next : adjList[v]) { ... } 을 사용해도 된다.
        Iterator<Integer> iter = adjList[v].listIterator();
        while (iter.hasNext()) {
            int nextVisit = iter.next();
            if (!visited[nextVisit]) {
                count++;
                dfs(adjList, visited, nextVisit);
            }
        }
    }
}

```

