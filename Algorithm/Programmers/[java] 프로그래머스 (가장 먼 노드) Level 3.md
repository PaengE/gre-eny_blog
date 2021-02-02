## Problem : https://programmers.co.kr/learn/courses/30/lessons/49189

## Approach

주어진 그래프의 1번 노드에서 가장 먼 노드의 개수가 몇개인지를 찾는 문제이다.

> DFS로 풀면 같은 그래프라도 답이 달라질 수 있다. (정확한 것은 아니고 예상(?))
>
> 예를 들어, (1, 2), (1, 3), (2, 3) 라면, DFS는 1 -> 2 -> 3 순으로 진행되어 노드 3이 노드 1로부터 2거리에 위치한다고 판단한다.
>
> 그러나 BFS는 1 -> 2, 1 -> 3 순으로 진행하여 노드 3이 노드 1로부터 1거리에 위치한다고 판단한다.

입력 테스트케이스 중 노드 1이 아무 노드랑도 연결되어 있지 않을 경우가 있다. 그럴 경우, 연결되지 않은 노드들은 같은 거리에 있다고 판단하는 것 같다.

그러나 하나라도 노드 1과 연결되어 있다면, 그 연결된 노드가 가장 먼 노드이다.

나는 BFS로 노드 1에서 시작하여 탐색한 뒤, 가장 먼 노드일 때의 거리를 구하여, 그 거리를 가지고 있는 노드들이 몇개인지를 반환하게끔 구현하였다.

## Code

```java
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class TheLongestNode {
    public int solution(int n, int[][] vertex) {
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < vertex.length; i++) {
            graph.get(vertex[i][0]).add(vertex[i][1]);
            graph.get(vertex[i][1]).add(vertex[i][0]);

        }

        int[] res = bfs(1, n, graph);;
        Arrays.sort(res);

        // count 중 가장 큰 값이 몇개인지를 반환
        return res[n] == 0 ? n - 1: (int) Arrays.stream(res).filter(i -> i == res[n]).count();

    }

    // BFS 수행
    private int[] bfs(int start, int nodeCount, ArrayList<ArrayList<Integer>> graph) {
        boolean[] visited = new boolean[nodeCount + 1];
        int[] count = new int[nodeCount + 1];
        Queue<Integer> q = new LinkedList<>();
        q.offer(start);
        visited[start] = true;

        while (!q.isEmpty()) {
            int cur = q.poll();

            for (int next : graph.get(cur)) {
                if (!visited[next]) {
                    q.offer(next);
                    visited[next] = true;
                    count[next] = count[cur] + 1;
                }
            }
        }
        return count;
    }

    @Test
    public void test() {
        Assertions.assertEquals(3, solution(6, new int[][]{{3, 6}, {4, 3}, {3, 2}, {1, 3}, {1, 2}, {2, 4}, {5, 2}}));
        Assertions.assertEquals(5, solution(6, new int[][]{{2, 3}}));
        Assertions.assertEquals(1, solution(2, new int[][]{{2, 1}}));
        Assertions.assertEquals(1, solution(4, new int[][]{{1, 2}, {2, 3}, {3, 4}, {4, 1}}));
    }
}

```

