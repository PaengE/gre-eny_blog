## Problem : https://www.acmicpc.net/problem/2629

## Approach

주어진 추들의 무게로 구슬의 무게를 확인할 수 있는지를 판단하는 문제였다.

구슬을 목적지라고 가정한다면, 목적지를 갈 수 있는지를 판단하는 문제다.

따라서 필자는 깊이우선탐색을 이용하여 풀이를 하였다.

> 일단, 탐색 진행 방법에는 3가지 방법이 있다.
>
> 무게를 확인할 구슬은 항상 저울의 왼쪽에 놓는다고 가정하였을 때,
>
> 1. 다음 구슬을 저울의 오른쪽에 올리는 경우(w + weight[i])
> 2. 다음 구슬을 저울에 올리지 않는 경우(w)
> 3. 다음 구슬을 저울의 왼쪽에 올리는 경우(구슬과 함께)(|w - weight[i]|)
>
> 위와 같은 방법이 존재한다.

주의할 점은 아니지만 DP로 풀이할 때는 구슬의 무게가 15000 초과라면 무조건 확인할 수 없다, 추로 확인할 수 있는 최대 무게는 (각 추의 최대 무게 500) * (추의 최대 개수 30) = 15000이다. 따라서 15000이 넘는 구슬은 확인 할 수 없으므로 무조건 NO를 출력해주면 시간, 공간적 이점이 있을 것이다.

## Code

```java
import java.io.*;
import java.util.StringTokenizer;

/**
 *  no.2629: 양팔저울
 *  url: https://www.acmicpc.net/problem/2629
 *  hint: knapsack 변형 문제
 */
public class BOJ2629 {
    static boolean[][] isPossible;
    static int[] weight;
    static int n;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        n = Integer.parseInt(br.readLine());
        weight = new int[n];
        isPossible = new boolean[n + 1][15001]; // 추 무게의 최대 총합은 15000

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            weight[i] = Integer.parseInt(st.nextToken());
        }

        dfs(0, 0);

        int m = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            int bead = Integer.parseInt(st.nextToken());

            // 무게가 15000이 넘는 구슬은 무게를 확인할 수 없음 (추 무게의 최대 총합이 15000이므로)
            if (bead > 15000) {
                sb.append("N ");
            } else {
                if (isPossible[n][bead]) {
                    sb.append("Y ");
                } else {
                    sb.append("N ");
                }
            }
        }

        bw.write(sb.toString());
        bw.close();
        br.close();
    }

    static void dfs(int cnt, int w){
        if (isPossible[cnt][w]) {
            return;
        }
        isPossible[cnt][w] = true;

        if (cnt == n) {
            return;
        }

        // 한쪽에 추가로 놓은 경우
        dfs(cnt + 1, w + weight[cnt]);
        // 아무쪽에도 놓지 않은 경우
        dfs(cnt + 1, w);
        // 반대쪽에 놓은 경우
        dfs(cnt + 1, Math.abs(w - weight[cnt]));
    }
}

```

