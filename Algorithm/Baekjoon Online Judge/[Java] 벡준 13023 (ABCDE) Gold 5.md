## Problem : https://www.acmicpc.net/problem/13023

## Approach

> `DFS`를 활용한 문제이다.
>
> 문제에서 요구하는게 이해가 어려울 수 있으나, 쉽게 얘기하자면 어느 하나의 노드에서 시작하여 깊이가 5이상인 그래프가 존재하는지를 판단하면 된다.

그래프의 최대 지름이 5 이상이면 `1`을 아니라면 `0`을 출력하면 된다. 

그래프의 최대지름을 구하는 법은 다음과 같다.

- 임의의 노드에서 가장 먼(깊이가 가장 깊은) 노드를 찾는다. (DFS 로)
- 그 노드에서부터 다시 DFS를 진행하여 가장 먼 노드와의 길이를 측정한다.

두번의 DFS가 이루어져야 한다. 나는 첫번째 DFS를 수행하고 난 뒤 가장 먼 노드와의 거리가 5이상이라면 두번째 DFS는 수행하지 않도록 로직을 구성하였다.

## Code

```java
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 *  No.13023: ABCDE
 *  URL: https://www.acmicpc.net/problem/13023
 *  Hint: 이중 DFS
 */

public class BOJ13023 {
    static int n, m, max, start;
    static ArrayList<Integer>[] lists;
    static boolean[] visited;
    static boolean findAnswer = false;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        init();

        // 그래프 구성
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            lists[a].add(b);
            lists[b].add(a);
        }

        // 노드 0 에서 가장 먼 노드 start와 깊이 max를 찾음
        dfs(0, 0);

        // 깊이 max가 4보다 작으면 노드 start 에서 가장 먼 노드와의 깊이 max를 찾음
        if (!findAnswer) {
            Arrays.fill(visited, false);
            dfs(start, 0);
        }

        if (findAnswer) {
            bw.write("1");
        } else {
            bw.write("0");
        }

        bw.close();
        br.close();
    }

    static void dfs(int cur, int depth) {
        // 깊이가 4이거나 답을 찾았다면 중지
        if (depth == 4 || findAnswer) {
            findAnswer = true;
            return;
        }

        visited[cur] = true;
        for (int next : lists[cur]) {
            if (!visited[next]) {
                dfs(next, depth + 1);
            }
        }
        visited[cur] = false;

        // 만약 depth가 4보다 작을 경우를 대비하여 가장 먼 노드를 기억험
        if (max < depth) {
            max = depth;
            start = cur;
        }
    }

    static void init() {
        lists = new ArrayList[n];
        visited = new boolean[n];

        for (int i = 0; i < n; i++) {
            lists[i] = new ArrayList<>();
        }
    }
}
```