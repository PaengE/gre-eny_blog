## Problem : https://www.acmicpc.net/problem/1167

## Approach

> BFS 나 DFS 를 사용하여 풀 수 있는 문제이다. 상식으로 접근하여 생각하면 쉽게 풀리는 문제였다.

BFS나 DFS를 두번 수행하면 풀이가 가능한 문제이다. (나는 BFS를 사용했다.)

- 아무 정점을 시작으로 BFS를 수행하여 시작 정점으로부터 가장 먼 정점을 구한다.
- 그 정점을 기준으로 다시 BFS를 수행하여 가장 먼 정점까지의 거리가 `트리의 지름`이 된다.

잠깐 생각해보면 위에서 구한 것이 왜 `트리의 지름`이 되는지 이해가 갈 것이다.

## Code

```java
import java.io.*;
import java.util.*;

/**
 *  No.1167: 트리의 지름
 *  URL: https://www.acmicpc.net/problem/1167
 *  Description: BFS or DFS 로 트리에서 가장 먼 두 점을 찾는 문제
 *  Hint: A 에서 가장 먼 B 를 구함 -> B 에서 가장 먼 노드를 구함 = 최대 길이
 */

public class BOJ1167 {
    static int v;
    static ArrayList<ArrayList<Tree>> list;
    static int[] dist;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        v = Integer.parseInt(br.readLine());
        list = new ArrayList<>();
        for (int i = 0; i <= v; i++) {
            list.add(new ArrayList<>());
        }

        for (int i = 0; i < v; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int node = Integer.parseInt(st.nextToken());
            String s;
            while (!(s = st.nextToken()).equals("-1")) {
                int to = Integer.parseInt(s);
                int dist = Integer.parseInt(st.nextToken());
                list.get(node).add(new Tree(to, dist));
            }
        }

        int longestLengthNode = bfs(1);
        int ansIndex = bfs(longestLengthNode);

        bw.write(String.valueOf(dist[ansIndex]));
        bw.close();
        br.close();
    }

    // 가장 멀리 있는 노드의 index 를 구해서 return
    static int bfs(int start) {
        int max = 0;
        dist = new int[v + 1];
        Queue<Tree> q = new LinkedList<>();
        boolean[] visited = new boolean[v + 1];
        q.offer(new Tree(start, 0));

        // BFS
        while (!q.isEmpty()) {
            Tree cur = q.poll();

            if (visited[cur.to]) {
                continue;
            }
            visited[cur.to] = true;

            for (Tree next : list.get(cur.to)) {
                if (!visited[next.to]) {
                    q.offer(next);
                    dist[next.to] = dist[cur.to] + next.dist;
                    max = Math.max(max, dist[next.to]);
                }
            }
        }

        int maxIdx = 0;
        for (int i = 1; i <= v; i++) {
            if (dist[i] == max) {
                maxIdx = i;
                break;
            }
        }
        return maxIdx;
    }

    static class Tree {
        int to, dist;

        Tree(int to, int dist) {
            this.to = to;
            this.dist = dist;
        }
    }
}
```

