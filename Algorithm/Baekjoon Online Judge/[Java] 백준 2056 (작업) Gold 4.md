## Problem : https://www.acmicpc.net/problem/2042

## Approach

> 기본적인 `위상정렬 (Topological Sort)` 문제였다.
>
> 위상 정렬에서는 선행되어야 하는 것들의 개수를 저장하는 inDegree 배열이 필요하다.

먼저 inDegree[a] 를 a작업이 수행되기까지 선행되어야 하는 작업의 수라고 정의한다.

그렇게 inDegree를 구성하고, inDegree가 0인 것부터 큐에 넣어 순차적으로 위상정렬을 수행한다.

- 현재 작업의 후행 작업들의 inDegree를 1씩 낮추면서, inDegree가 0인 것들을 다시 큐에 넣는다.
- 2개 이상의 선행작업이 동시에 수행될 수 있으므로, (먼저 끝난 또다른 선행작업의 종료시간 + 후행작업의 종료시간) 과 (지금 수행되고 있는 선행작업의 종료시간 + 후행작업의 종료시간)을 비교하여 더 큰 것을 저장한다.

마지막으로 모든 작업의 종료시간을 검사하여, 그 중 가장 큰 수가 `모든 작업을 완료할 최소 시간`이 된다.

나는 Stream으로 간단히 코드를 작성했다. Stream말고 배열을 순회하면서 최댓값을 찾아도 된다.

## Code

```java
import java.io.*;
import java.util.*;
/**
 * No.2056: 작업
 * URL: https://www.acmicpc.net/problem/2056
 * Hint: 위상정렬
 */
public class BOJ2056 {
    static int n;
    static int[] inDegree, timeCost, finishTime;
    static ArrayList<Integer>[] list;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        n = Integer.parseInt(br.readLine());
        inDegree = new int[n + 1];
        list = new ArrayList[n + 1];
        timeCost = new int[n + 1];
        finishTime = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            list[i] = new ArrayList<>();
            StringTokenizer st = new StringTokenizer(br.readLine());
            timeCost[i] = Integer.parseInt(st.nextToken());
            int count = Integer.parseInt(st.nextToken());

            for (int j = 0; j < count; j++) {
                int num = Integer.parseInt(st.nextToken());
                inDegree[i]++;
                list[num].add(i);
            }
        }

        int answer = topologicalSort();

        bw.write(String.valueOf(answer));
        bw.close();
        br.close();
    }

    // 위상 정렬
    static int topologicalSort() {
        Queue<Integer> q = new LinkedList<>();
        // 차수가 0인 모든 작업을 큐에 넣음
        for (int i = 1; i <= n; i++) {
            if (inDegree[i] == 0) {
                q.offer(i);
                finishTime[i] = timeCost[i];
            }
        }

        while (!q.isEmpty()) {
            int cur = q.poll();

            // 각 작업이 끝나는 시간을 계산하며
            // 현재 작업이 완료 되었으면, 후행 작업들의 차수를 하나씩 내림
            // 후행 작업의 차수가 0이면(실행할 수 있으면)
            for (int next : list[cur]) {
                if (finishTime[next] < finishTime[cur] + timeCost[next]) {
                    finishTime[next] = finishTime[cur] + timeCost[next];
                }
                if (--inDegree[next] == 0) {
                    q.offer(next);
                }
            }
        }

        return Arrays.stream(finishTime).max().getAsInt();
    }
}
```

