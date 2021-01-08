## Problem : https://www.acmicpc.net/problem/11780

## Approach

플로이드와샬 알고리즘을 사용한 최단경로비용 구하기 + 최단경로를 구하는 문제이다.

dist[i][j] = i부터 j까지 가는 최소비용이고, (i == j 일 경우거나, 도달할 수 없는 경우는 0)

next[i][j] = j도착 직전 도시를 저장해놓은 배열이다. (i == j 일 경우거나, 도달할 수 없는 경우는 -1)

입력으로 그래프를 먼저 구성하고, 플로이드 와샬 알고리즘을 적용한다. 이 때, next 배열도 갱신해준다.

예를들어, i~j의 최단경로가 i~k~j로 갱신될 때, next[i][j] 의 값은 i 에서 k(next[k][j]) 로 갱신된다.

## Code

```java
import java.io.*;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 *  no.11780: 플로이드 2
 *  url: https://www.acmicpc.net/problem/11780
 *  hint: 플로이드와샬 알고리즘 + 최단거리경로
 */
public class BOJ11780 {
    static int n;
    static long[][] dist;
    static int[][] next;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        n = Integer.parseInt(br.readLine());
        dist = new long[n + 1][n + 1];
        next = new int[n + 1][n + 1];

        // 배열 초기화
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                next[i][j] = -1;
                if (i == j) {
                    continue;
                }
                dist[i][j] = Integer.MAX_VALUE;
            }
        }

        // 그래프 구성
        int m = Integer.parseInt(br.readLine());
        StringTokenizer st;
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            dist[start][end] = Math.min(cost, dist[start][end]);
            next[start][end] = start;
        }

        floydWarshall();

        StringBuilder sb = print();

        bw.write(sb.toString());
        br.close();
        bw.close();
    }

    // 플로이드와샬 알고리즘
    static void floydWarshall() {
        for (int k = 1; k <= n; k++) {
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    // 최소비용이 갱신되면 next 배열도 갱신
                    if (dist[i][j] > dist[i][k] + dist[k][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        next[i][j] = next[k][j];
                    }
                }
            }
        }
    }

    // 출력 부분
    static StringBuilder print() {
        StringBuilder sb = new StringBuilder();

        // dist 배열 출력
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (dist[i][j] >= Integer.MAX_VALUE) {
                    sb.append("0 ");
                } else {
                    sb.append(dist[i][j] + " ");
                }
            }
            sb.append("\n");
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                // i == j 거나 도달할 수 없는 경우 0 출력
                if (next[i][j] == -1) {
                    sb.append("0\n");
                }
                // 그 외의 경우
                else {
                    var path = new Stack<Integer>();
                    int pre = j;
                    path.push(j);

                    // 경로 역추적 (스택에 push)
                    while (i != next[i][pre]) {
                        pre = next[i][pre];
                        path.push(pre);
                    }

                    // 경로상에 있는 도시 총 개수
                    sb.append(path.size() + 1 + " ");

                    // 경로상에 있는 도시 출력(스택에서 pop)
                    sb.append(i + " ");
                    while (!path.isEmpty()) {
                        sb.append(path.pop() + " ");
                    }
                    sb.append("\n");
                }
            }
        }
        return sb;
    }
}

```

