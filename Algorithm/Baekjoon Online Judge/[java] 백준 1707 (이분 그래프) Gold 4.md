## Problem : https://www.acmicpc.net/problem/1707

## Approach

> 풀이에 시작하기 앞서 주의할 점을 소개한다. 이 문제는 은근히 반례를 찾는 유저들이 많았는데 대부분 다음과 같은 이유로 오답이 생겼을 것이다. 질문 게시판에 있는 djm03178님의 게시글을 인용하겠다.
>
> 1. 테스트 케이스마다 초기화를 해야 한다. 똑같은 입력을 주었을 때 다른 출력을 내놓는다면 초기화가 되지 않은 상태일 것이다.
> 2. 1번 정점에서만 탐색을 하면 안된다. 주어진 그래프가 모든 정점이 연결된 연결 그래프가 아닐 수 도 있기 때문이다.
> 3. 그래프 정보가 완전히 들어온 후 탐색을 시작한다.
> 4. 한 정점에서 탐색을 하여 답이 NO로 판명났다면, 다른 정점을 탐색해서 이분그래프를 만들 수 있다 하더라도 답이 YES가 되지는 않는다.

BFS를 수행하면서 현재 노드와 연결된 다음 노드의 숫자가 다르게끔 번호를 매긴다.

예를 들어, 3개 노드가 1ㅡ2ㅡ3 이런식으로 연결되어 있다면, 1(1)ㅡ2(2)ㅡ(1) 이런식으로 번호를 총 2개로 매긴다.

인접한 노드 중에 자신과 같은 번호가 있다면, 이분 그래프를 만들 수 없는 것이다.

다음 코드의 주석들을 참고하여 본다면 이해가 더 쉬울 것이다.

## Code

```java
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 *  no.1707: 이분 그래프
 *  url: https://www.acmicpc.net/problem/1707
 *  hint: BFS
 */

public class BOJ1707 {
    static int[] numbering;
    static ArrayList<ArrayList<Integer>> graph;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int k = Integer.parseInt(br.readLine());
        while (k-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int v = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            numbering = new int[v + 1];

            graph = new ArrayList<>();
            for (int i = 0; i <= v; i++) {
                graph.add(new ArrayList());
            }

            // 그래프 구성
            for (int i = 0; i < e; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                graph.get(a).add(b);
                graph.get(b).add(a);
            }

            boolean flag = true;
            // 연결그래프가 아닐 수도 있으므로 탐색하지 않은 모든 노드 탐색
            for (int i = 1; i <= v; i++) {
                if (numbering[i] == 0) {
                    // 인접한 한 덩어리의 노드들로 이분그래프를 만들지 못한다면
                    // 다른 노드들을 탐색할 필요가 없다.(어차피 만들지 못하므로)
                    if (!bfs(i, e)) {
                        flag = false;
                        break;
                    }
                }
            }
            if (flag) {
                bw.write("YES\n");
            } else {
                bw.write("NO\n");
            }
        }
        bw.close();
        br.close();
    }

    static boolean bfs(int start, int e) {
        Queue<Integer> q = new LinkedList<>();

        q.offer(start);
        numbering[start] = 1;

        while (!q.isEmpty() && e-- >= 0) {
            int cur = q.poll();

            for (int next : graph.get(cur)) {
                // 방문하지 않았던 노드라면 현재노드의 번호와 비교하여 다음 노드의 번호를 매김
                if (numbering[next] == 0) {
                    q.offer(next);
                    numbering[next] = (numbering[cur] == 1) ? 2 : 1;
                }

                // 다음노드와 현재노드의 번호가 같다면 이분그래프를 만들 수 없다
                if (numbering[cur] == numbering[next]) {
                    return false;
                }
            }
        }
        return true;
    }
}

```

