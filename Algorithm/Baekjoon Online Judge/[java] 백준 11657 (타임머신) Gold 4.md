## Problem : https://www.acmicpc.net/problem/11657

## Approach

도시 to 도시 의 최단경로비용을 구하는 문제인데, 음수 사이클이 존재할 수도 있는 문제이다.

음수사이클이 존재할 수 있으므로, 다익스트라 알고리즘은 사용이 불가능하다.

문제에서 의도한 바는 벨만-포드(Bellman-Ford) 알고리즘이므로 필자도 그 방법을 이용했다.

벨만-포드 알고리즘은 `최단거리로 목적지에 도착하는 최소비용은 유일하다.(비용이 같은경우 경로는 여러개일 수도 있다.)`는 그래프의 성질을 이용한 알고리즘이다.

V개의 각각의 노드에서 모든 간선(E개의 간선)을 이용하여 최소비용을 갱신한다.(각각의 노드를 출발점으로 택한다는 뜻이 아님.)

음수사이클이 없다면 V개 만큼 루프를 돌기전에 종료될 것이다. 그러므로 V개 만큼 루프를 돌면서 업데이트가 된다는 말은 음수 사이클이 존재한다는 뜻이다.

(양수 사이클은 존재해도 괜찮다. 왜냐하면 최소비용을 구하는 과정에서 걸러지게 되기 때문이다.)

모든 노드 V에 대해 간선 E만큼 연산을 수행하므로 시간복잡도는 O(VE)이다.

벨만-포드(Bellman-Ford) 알고리즘에 대해 더 자세하게 알고싶은 분은 다음 링크를 참고하시면 도움이 될 것이다.

[victolee님의 티스토리](https://victorydntmd.tistory.com/104)

## Code

```java
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * no.11657: 타임머신
 * description: 벨만 포드 알고리즘을 배우는 문제 (경로 cost 가 음수일 경우 다익스트라는 사용 불가)
 * hint: 가능한 간선 개수만큼 최단경로를 update 함
 *       그 이후에도 update 가 가능하다면 음수 사이클이 존재하는 것임
 */

public class BOJ11657 {
    static int n, m;
    static ArrayList<Node11657>[] list;
    static long[] dist;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        list = new ArrayList[n + 1];
        dist = new long[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);

        for (int i = 1; i <= n; i++) {
            list[i] = new ArrayList<Node11657>();
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());

            list[a].add(new Node11657(b, t));
        }

        StringBuilder sb = new StringBuilder();
        if (n == 2) {

        }
        if (bellmanFord()) {
            sb.append("-1\n");
        } else {
            for (int i = 2; i <= n; i++) {
                if (dist[i] == Integer.MAX_VALUE) {
                    sb.append("-1\n");
                } else {
                    sb.append(dist[i] + "\n");
                }

            }
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();

    }

    static boolean bellmanFord() {
        dist[1] = 0;
        boolean update = false;

        // 노드개수만큼 update 를 진행
        for (int i = 1; i <= n; i++) {
            update = false;
            for (int j = 1; j <= n; j++) {
                for (Node11657 node : list[j]) {
                    if (dist[j] == Integer.MAX_VALUE) {
                        break;
                    }

                    // update 가 진행되면 update flag 표시
                    if (dist[node.end] > dist[j] + node.weight) {
                        dist[node.end] = dist[j] + node.weight;
                        update = true;
                    }
                }
            }
            // 더 이상 업데이트가 발생하지 않으면 break
            if (!update) {
                break;
            }
        }
        // n == 2 일땐 음수사이클이 없어도 update flag 가 true 로 설정 되어 있으니 조건을 추가
        // n - 1번째까지 업데이트를 진행했을 때 변경이 감지되면 n 번째에도 업데이트를 하므로
        // 음수 사이클이 존재하다고 생각할 수 있음음
        return update && n > 2 ? true : false;
    }
}
class Node11657 implements Comparable<Node11657> {
    int end, weight;

    public Node11657(int end, int weight) {
        this.end = end;
        this.weight = weight;
    }

    @Override
    public int compareTo(Node11657 o) {
        return weight - o.weight;
    }
}

```

