## Problem : https://www.acmicpc.net/problem/1956

## Approach

최단경로 사이클을 찾는 문제이다.

따라서 플로이드 와샬(Floyd Warshall) 알고리즘을 사용하여야 한다.

참고로 1:N 최단경로 문제는 다익스트라 알고리즘을, 음수사이클을 찾을 때엔 벨만-포드 알고리즘을, N:N 최단경로 문제는 플로이드 와샬 알고리즘으로 접근하면 용이하다.

이 문제에서는 각 노드 A에 대하여 A에서 시작하여 A로 돌아오는 최단경로를 구하면 된다. 만약 그런 사이클이 존재하지 않으면 -1을 출력한다.

distance[i][j] 가 i에서 j로 가는 최단경로비용이므로, 모든 노드에 대하여 distance[i][i] 중 최솟값을 취하면 된다.

## Code

```java
import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * no.1956: 운동
 * description: 최단거리 알고리즘을 응용하여 최단 사이클을 찾는 문제
 * hint: FloydWarshall 알고리즘을 적용 후 최단 사이클 구하기
 */

public class BOJ1956 {
    static int v, e;
    static long[][] distance;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        v = Integer.parseInt(st.nextToken());
        e = Integer.parseInt(st.nextToken());
        distance = new long[v + 1][v + 1];

        for (long[] a : distance) {
            Arrays.fill(a, Integer.MAX_VALUE);
        }

        for (int i = 0; i < e; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            distance[a][b] = Math.min(distance[a][b], c);
        }

        floydWarshall();

        long answer = Integer.MAX_VALUE;
        for (int i = 1; i <= v; i++) {
            answer = Math.min(answer, distance[i][i]);
        }

        if (answer == Integer.MAX_VALUE) {
            bw.write("-1");
        } else {
            bw.write(String.valueOf(answer));
        }

        bw.flush();
        br.close();
        bw.close();

    }

    static void floydWarshall() {
        for (int i = 1; i <= v; i++) {
            for (int j = 1; j <= v; j++) {
                for (int k = 1; k <= v; k++) {
                    distance[i][j] = Math.min(distance[i][j], distance[i][k] + distance[k][j]);
                }
            }
        }
    }
}

```

