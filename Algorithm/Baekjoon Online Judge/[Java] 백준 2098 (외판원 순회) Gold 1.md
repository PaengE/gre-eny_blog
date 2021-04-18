## Problem : https://www.acmicpc.net/problem/2098

## Approach

> 대표적인 `외판원 문제 (TSP: Traveling Salesman Problem)` 이다.
>
> 어떤 지점 A에서 모든 지점을 돌아 다시 A로 오는 최소 비용 경로를 찾으면 된다. (완전탐색)

시작 지점은 아무 곳에서 시작해도 된다. 어디서 시작하든 최소 비용 사이클은 동일하기 때문이다.

코드의 주석처럼 도시가 4개일 때, 1001이 나타내는 의미는 1번 도시와 4번 도시를 방문했다는 뜻이다.

visit 과 도시 i 와의 AND연산을 하여 방문을 했는지 안했는지 체크를 한다. 완전탐색이 필요하다.

## Code

```java
import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * No.2098: 외판원 순회 Traveling Salesman Problem (TSP) ---- 어려움
 * description: 비트마스크 DP를 이용하여 최소 비용으로 모든 도시를 순회하는 문제
 * hint: If n == 4, 방문한 도시가 없는경우: 0000, 2, 4번 도시를 방문한 경우: 1010
 *      순회하는 최단거리가 있을 때, 어느 노드에서 출발하던 최단거리 사이클은 동일함
 */

public class BOJ2098 {
    static int n;
    static long[][] weight, dp;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        n = Integer.parseInt(br.readLine());
        weight = new long[n][n];
        dp = new long[n][1 << n];   // 비트마스킹을 사용하므로 2^n개 만큼 사용

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                weight[i][j] = Integer.parseInt(st.nextToken());
            }
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }

        bw.write(String.valueOf(tsp(0, 1))); // 1부터 시작
        bw.flush();
        br.close();
        bw.close();
    }

    /* cur 위치에서 visit 에 기록되지 않은 방문하지 않은 나머지를 방문하는 최소값.
     * ex) 1(cur) -> { 2, 3, 4 } -> 1으로 가는 {2, 3, 4}를 순회하는 최소값을 탐색 */
    static long tsp(int cur, int visit) {
        //모든 정점을 다 돌았으면 현재 위치에서 출발점(0)으로 돌아감
        if (visit == (1 << n) - 1) {
            if (weight[cur][0] == 0) {	// 현재 위치에서 0번째 위치로 돌아갈 수 없다면
                return Integer.MAX_VALUE;
            }
            return weight[cur][0];
        }

        //현 위치에서의 거리값이 MAX_VALUE가 아니면 이미 계산된 것이므로 값을 리턴
        if (dp[cur][visit] != Integer.MAX_VALUE) {
            return dp[cur][visit];
        }

        for (int i = 0; i < n; i++) {
            //방문 안했으면서 길이 있으면 방문
            if ((visit & (1 << i)) == 0 && weight[cur][i] != 0) {
                dp[cur][visit] = Math.min(dp[cur][visit], weight[cur][i] + tsp(i, visit | (1 << i)));
            }
        }

        return dp[cur][visit];
    }
}
```

