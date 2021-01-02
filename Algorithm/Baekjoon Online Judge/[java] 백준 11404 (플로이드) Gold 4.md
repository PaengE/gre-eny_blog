## Problem : https://www.acmicpc.net/problem/11404

## Approach

1 대 N 최단 경로를 구할 때에는 다익스트라(Dijkstra) 알고리즘을 사용하면 된다.

N 대 N 최단 경로를 구할 때에는 플로이드 와샬(Floyd Warshall) 알고리즘을 사용하면 된다.

다익스트라 알고리즘은 가장 적은 비용을 하나씩 선택하며 최단경로를 구성해 나갔다면, 플로이드 와샬 알고리즘은 `거쳐가는 정점`을 기준으로 알고리즘을 수행한다. 다음과 같은 점화식을 이용하여 테이블을 구성한다.

distance[i][j] 는 i부터 j까지 가는 최단경로비용이다.

distance[i][j] = min(distance[i][j], distance[i][k] + distance[k][j])	(i <= k <= j) 이다.

플로이드 와샬 알고리즘에 대해 자세히 알고 싶다면 다음 링크가 도움이 될 것이다.

[안경잡이 개발자님의 네이버블로그](https://blog.naver.com/ndb796/221234427842)

## Code

```java
import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * No.11404: 플로이드
 * description: Floyd Warshall 알고리즘을 배우는 문제
 * hint: distance[i][j] = Math.min(distance[i][j], distance[i][k] + distance[k][j])
 */

public class BOJ11404 {
    static int n, m;
    static long[][] distance;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();

        n = Integer.parseInt(br.readLine());
        m = Integer.parseInt(br.readLine());
        distance = new long[n + 1][n + 1];

        for (long[] a : distance) {
            Arrays.fill(a, Integer.MAX_VALUE);
        }

        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int time = Integer.parseInt(st.nextToken());

            distance[start][end] = Math.min(distance[start][end], time);
        }

        floydWarshall();

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i == j || distance[i][j] >= Integer.MAX_VALUE) {
                    sb.append("0 ");
                } else {
                    sb.append(distance[i][j] + " ");
                }
            }
            sb.append("\n");
        }
        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();

    }
    // i ~ j 경로의 최단 거리는 i ~ k 와 k ~ j 최단 경로의 합
    static void floydWarshall() {
        for (int k = 1; k <= n; k++) {
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    distance[i][j] = Math.min(distance[i][j], distance[i][k] + distance[k][j]);
                }
            }
        }
    }
}

```

