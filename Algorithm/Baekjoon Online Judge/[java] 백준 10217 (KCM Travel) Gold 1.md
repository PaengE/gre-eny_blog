## Problem : https://www.acmicpc.net/problem/10217

## Approach

이 문제는 다익스트라 알고리즘과 DP를 같이 사용해서 풀어야했다.

인접리스트를 이용하여 그래프를 구성하였으며, 시간순 오름차순(시간이 같으면 비용순 오름차순)으로 구성하였다. 

그런 후, 시작점 1부터 다익스트라 알고리즘을(BFS: PriorityQueue 이용) 적용하여, 비용을 초과하지 않는 선에서 모두 갱신해주었다.

다익스트라 알고리즘은 알고 있다고 가정한 후, 접근법만 제시했으므로 알고리즘 자체에 대한 내용은 본 블로그의 태그에 `Dijkstra`를 검색하여 참고하면 좋을 것이다.

## Code

```java
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * No.10217: KCM Travel
 * description: 간선을 사용하는 비용과 예산 제약이 있을 때 다이나믹 프로그래밍을 활용하여 푸는 문제
 * hint: PriorityQueue + Dijkstra + DP 사용
 */

public class BOJ10217 {
    static int n, m, k;
    static long[][] dp;
    static ArrayList<AirPlane>[] list;
    static PriorityQueue<AirPlane> pq;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int t = Integer.parseInt(br.readLine());

        for (int i = 0; i < t; i++) {
            long answer = Integer.MAX_VALUE;
            StringTokenizer st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
            k = Integer.parseInt(st.nextToken());

            dp = new long[n + 1][m+ 1];
            list = new ArrayList[n + 1];
            for (int j = 1; j <= n; j++) {
                list[j] = new ArrayList<AirPlane>();
                Arrays.fill(dp[j], Integer.MAX_VALUE);
            }

            // 노드 추가
            for (int j = 1; j <= k; j++) {
                st = new StringTokenizer(br.readLine());
                int start = Integer.parseInt(st.nextToken());
                int end = Integer.parseInt(st.nextToken());
                int cost = Integer.parseInt(st.nextToken());
                int time = Integer.parseInt(st.nextToken());

                list[start].add(new AirPlane(end, cost, time));
            }

            pq = new PriorityQueue<AirPlane>();
            dp[1][0] = 0;
            // 시작 노드 1
            pq.add(new AirPlane(1, 0, 0));

            // BFS 시작
            while (!pq.isEmpty()) {
                AirPlane cur = pq.poll();

                // 목표노드 진입 시 종료
                if (cur.end == n) {
                    answer = Math.min(answer, cur.time);
                    break;
                }

                for (AirPlane next : list[cur.end]) {
                    int sumCost = cur.cost + next.cost;
                    // 비용 초과 시
                    if (sumCost > m) {
                        continue;
                    }

                    int sumTime = cur.time + next.time;
                    if (dp[next.end][sumCost] > sumTime) {
                        dp[next.end][sumCost] = sumTime;
                        pq.offer(new AirPlane(next.end, sumCost, sumTime));
                    }
                }
            }

            if (answer == Integer.MAX_VALUE) {
                bw.write("Poor KCM\n");
            } else {
                bw.write(String.valueOf(answer) + "\n");
            }

        }
        bw.flush();
        br.close();
        bw.close();

    }
    static class AirPlane implements Comparable<AirPlane> {
        int end;
        int cost;
        int time;

        AirPlane(int end, int cost, int time) {
            this.end = end;
            this.cost = cost;
            this.time = time;
        }

        @Override
        public int compareTo(AirPlane o) {
            // 시간 순으로(시간이 같으면 비용 순으로)
            if (this.time == o.time) {
                return cost - o.cost;
            } else {
                return this.time - o.time;
            }
        }
    }
}
```

