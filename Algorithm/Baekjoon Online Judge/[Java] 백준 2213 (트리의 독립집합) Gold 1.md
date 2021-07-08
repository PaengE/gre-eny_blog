## Problem : https://www.acmicpc.net/problem/2213

## Approach

> `트리 DP` 문제이다. 문제를 풀기 위해서는 다음 개념들을 알고 있어야 한다.
>
> 먼저 `독립집합` 이란, 그래프 이론에서, 어떤 두 꼭짓점도 서로 인접하지 않는 그래프에 있는 꼭짓점들의 집합을 이르는 말. 이라고 한다.
>
> 다시 말해 `독립집합`에 속해 있는 임의의 노드는 자신과 연결된 모든 노드와 인접해있지 않다.(연결되어 있지 않다는 뜻이다.)
>
> 여기서는 이러한 `독립집합`인 트리 중에 크기가 최대인 `최대독립집합`을 구해야 한다.

먼저 받은 입력들로 노드 1을 루트로 한 트리를 구성한다.

그리고 루트 노드를 포함한 최대독립집합과 루트 노드를 포함하지 않은 최대독립집합을 구한 뒤에 더 큰 최대독립집합을 판단한다. (여기서는 `t1`과 `t2`)

루트 노드를 포함하였는지 안하였는지를 selected[1] 에 표시한다.

그런 뒤 루트노드부터 다시 트리를 탐색하면서, 현재 노드가 독립집합에 포함되는지를 검사하고, 포함된다면 ? 오름차순 우선순위큐에 넣는다.

## Code

```java
import java.io.*;
import java.util.*;

/**
 *  No.2213: 트리의 독립집합
 *  URL: https://www.acmicpc.net/problem/2213
 *  Description: 트리의 최대 독립 집합을 구하는 문제.
 *               일반적인 그래프에서는 NP-hard 문제로, 효율적인 알고리즘이 알려지지 않음
 *  Hint: Tree + DP + Tracing
 */

public class BOJ2213 {
    static int n;
    static int[] arr, dp, selected;
    static ArrayList<ArrayList<Integer>> list = new ArrayList<>();
    static ArrayList<ArrayList<Integer>> tree = new ArrayList<>();
    static PriorityQueue<Integer> pq = new PriorityQueue<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        n = Integer.parseInt(br.readLine());
        arr = new int[n + 1];
        dp = new int[n + 1];
        selected = new int[n + 1];

        for (int i = 0; i <= n; i++) {
            list.add(new ArrayList<>());
            tree.add(new ArrayList<>());
        }

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            list.get(a).add(b);
            list.get(b).add(a);
        }

        buildTree(1, -1);

        int t1 = dp(1, 0);
        int t2 = dp(1, 1);
        if (t1 < t2) {
            selected[1] = 1;
        } else {
            selected[1] = 0;
        }

        bw.write(String.valueOf(Math.max(t1, t2)));
        bw.newLine();

        trace(1, selected[1]);

        StringBuilder sb = new StringBuilder();
        while (!pq.isEmpty()) {
            sb.append(pq.poll()).append(" ");
        }

        bw.write(sb.toString());
        bw.close();
        br.close();


    }

    // 트리 구성
    static void buildTree(int cur, int parent) {
        for (int child : list.get(cur)) {
            if (child != parent) {
                tree.get(cur).add(child);
                buildTree(child, cur);
            }
        }
    }

    // 최대 독립 집합 구하기
    static int dp(int cur, int include) {
        int ans = 0;
        // 현재 노드를 포함한다면
        if (include == 1) {
            for (int next : tree.get(cur)) {
                ans += dp(next, 0); // 다음 노드를 포함 안함
            }
            return ans + arr[cur];
        }
        // 현재 노드를 포함하지 않는다면
        else {
            for (int next : tree.get(cur)) {
                int t1 = dp(next, 0);
                int t2 = dp(next, 1);

                // 다음 노드를 포함할지 안할지를 비교
                if (t1 < t2) {  // 포함한 것이 더 크면
                    selected[next] = 1;
                } else {        // 포함하지 않은 것이 더 크면
                    selected[next] = 0;
                }
                ans += Math.max(t1, t2);
            }
            return ans;
        }
    }

    // 최대 독립 집합의 노드 찾기
    static void trace(int cur, int include) {
        if (include == 1) {
            // 노드가 최대 독립 집합에 포함되면 우선순위 큐에 삽입
            pq.offer(cur);
            for (int next : tree.get(cur)) {
                trace(next, 0);
            }
        } else if (include == 0) {
            for (int next : tree.get(cur)) {
                trace(next, selected[next]);
            }
        }
    }
}
```