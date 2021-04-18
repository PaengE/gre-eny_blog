## Problem : https://www.acmicpc.net/problem/1005

## Approach

> 위상정렬(Topological Sort)의 연습문제이다. 위상정렬 자체에 대해서는 간단히 설명하겠다. 알고리즘에서 꽤나 자주 나오는 개념이므로 숙지해두면 좋을 것이다.
>
> DAG(Directed Acyclic Graph: 방향성이 있고 싸이클이 없는 그래프)에 순서가 곁들여져 있는 경우, 위상정렬을 사용해 볼 수 있다.

위상정렬에서는 중요한 개념 하나가 있다. 바로 `inDegree 차수 배열`이다. 

`inDegree[x] = y`의 의미는 건물 x를 지으려면 선행 작업이 필요한 건물이 y개 라는 뜻이다.

시작은 inDegree의 값이 0인 것들을 Queue에 넣음으로써 시작한다.

그렇다면 위의 개념을 문제에 적용해보자.

#### b를 짓는데 a가 선행되어야 한다면

inDegree[b]++ 를 할 수 있겠다. 

또한 a가 선행되었을 때 inDegree[n]의 값을 하나 낮춰줘야 하므로, a 와 b의 관계를 저장할 수 있는 변수가 필요할 것이다. (여기서는 이중 ArrayList를 썼다. ArrayList 배열도 상관없다.)

그리고 각 건물을 지을 때의 시간을 저장할 `dp 배열`이 필요하겠다.

dp배열을 채우는 공식은 dp[next] = Math.max(dp[next], dp[cur] + next.time) 인데 원리는 다음과 같다.

예를 들어 a b c 각각 작업 시간이 1 2 3이고, b의 선행 작업이 a, c라고 할 때, b를 짓는 최소시간을 구하는 로직을 보자.

dp[b] = Math.max(dp[b], dp[a] + b.time) = Math.max(0, 1 + 2) = 3 

dp[b] = Math.max(dp[b], dp[c] + b.time) = Math.max(3, 3 + 2) = 5

이렇게 해야 a와 c가 동시에 시작한 것처럼 보이게 하여 늦게 끝난 작업 기준으로 b가 시작할 수 있다. 

## Code

```java
import java.io.*;
import java.util.*;

/**
 *  No.1005: ACM Craft
 *  URL: https://www.acmicpc.net/problem/1005
 *  Hint: DAG(Directed Acyclic Graph: 방향성이 있고 사이클이 없는 그래프) + DP
 */

public class BOJ1005 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < t; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());
            int[] d = new int[n + 1];
            ArrayList<ArrayList<Integer>> graph = new ArrayList<>();

            st = new StringTokenizer(br.readLine());
            graph.add(new ArrayList<>());
            for (int j = 1; j <= n; j++) {
                d[j] = Integer.parseInt(st.nextToken());
                graph.add(new ArrayList<Integer>());
            }

            int[] inDegree = new int[n + 1];
            for (int j = 0; j < k; j++) {
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());

                graph.get(x).add(y);
                inDegree[y]++;
            }

            int w = Integer.parseInt(br.readLine());

            Queue<Integer> q = new LinkedList<>();
            int[] dp = new int[n + 1];

            // inDegree 가 0인 것들을 enqueue
            for (int j = 1; j <= n; j++) {
                dp[j] = d[j];

                if (inDegree[j] == 0) {
                    q.offer(j);
                }
            }

            // 위상정렬
            while (!q.isEmpty()) {
                int node = q.poll();

                for (Integer o : graph.get(node)) {
                    dp[o] = Math.max(dp[o], dp[node] + d[o]);
                    inDegree[o]--;

                    if (inDegree[o] == 0) {
                        q.offer(o);
                    }
                }
            }
            sb.append(dp[w] + "\n");
        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();
    }
}
```

