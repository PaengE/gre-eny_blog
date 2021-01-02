## Problem : https://www.acmicpc.net/problem/1260

## Approach

BFS(Breadth-First Search), DFS(Depth-First Search) 의 기본 문제이다.

일단 BFS, DFS 가 어떤 방식으로 돌아가는지를 알아야 한다. 사전적 정의는

DFS는 한 노드를 시작으로 인접한 다른 노드를 재귀적으로 탐색해가고 끝까지 탐색하면 다시 위로 와서 다음을 탐색하여 검색한다는 방식이고,

BFS는 우선적으로 그 노드와 같은 레벨의 노드를 검색하여 점차적으로 깊은 레벨로 이행해가는 방식이다. 즉, 같은 레벨의 노드를 먼저 검색한다는 방식이다.

<img src="C:\Users\82102\OneDrive\티스토리\Algorithm\Baekjoon Online Judge\image\1260-1.png" alt="1260-1" style="zoom:50%;" />

문제에서 트리라는 가정은 없지만, 설명을 위해 위의 트리 이미지를 이용하여 결과적으로 먼저 말한다면

> DFS : 1 - 2 - 4 - 8 - 9 - 5 - 10 - 11 - 3 - 6 - 12 - 13 - 7 - 14 - 15
>
> BFS : 1 - 2 - 3 - 4 - 5 - 6 - 7 - 8 - 9 - 10 - 11 - 12 - 13 - 14 - 15

순으로 순회한다. (동시에 접근이 가능하면, 노드번호가 작은것을 먼저)

일반적으로, DFS는 스택이나 재귀 방식으로 풀이를 하고, BFS는 큐를 사용하여 풀이를 진행한다.

필자는 스택과 큐를 이용하여 이 문제를 풀이하였다.

DFS의 재귀방식은 밑의 게시글로 가면 볼 수 있다.(다른문제에 대해 DFS 재귀를 이용해 푼 것이다.)

> [2020/12/29 - [Algorithm/Baekjoon Online Judge\] - [java] 백준 2606 (바이러스) Silver 3﻿](https://gre-eny.tistory.com/64)

## Code

```java
import java.io.*;
import java.util.*;

/**
 * no.1260: DFS 와 BFS
 * description: DFS 와 BFS 를 다루는 문제
 * hint: DFS 는 stack 으로 구현함,(재귀, 인접리스트 등으로도 풀 수 있음)
 *       BFS 는 queue 으로 구현함
 */

public class BOJ1260 {
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int v = Integer.parseInt(st.nextToken());
        int[][] arr = new int[m][2];

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            arr[i][0] = Integer.parseInt(st.nextToken());
            arr[i][1] = Integer.parseInt(st.nextToken());
        }

        dfs(arr, n, m, v);
        bfs(arr, n, m, v);

        br.close();
        bw.close();
    }

    // DFS: Stack 활용
    static void dfs(int[][] arr, int n, int m, int v) throws IOException {
        Stack<Integer> stk = new Stack<>();
        int current = v;
        boolean[] visit = new boolean[n + 1];

        // 시작점
        stk.push(0);
        stk.push(current);

        while (stk.peek() != 0) {
            // 연결된 노드 중 가장 작은 번호의 노드를 탐색
            int next = Integer.MAX_VALUE;
            for (int i = 0; i < m; i++) {
                if (current == arr[i][0]) {
                    if (!visit[arr[i][1]]) {
                        next = Math.min(next, arr[i][1]);
                    }
                } else if (current == arr[i][1]) {
                    if (!visit[arr[i][0]]) {
                        next = Math.min(next, arr[i][0]);
                    }
                }
            }
            // 방문하지 않았다면, 방문 후 표시
            if (!visit[current]) {
                bw.write(current + " ");
                visit[current] = true;
            }
            // next 가 갱신이 안되었으므로 더 이상 연결된 노드가 없는 것.
            if (next == Integer.MAX_VALUE) {
                stk.pop();
                current = stk.peek();
            }
            // 연결된 노드가 더있으면 stack 에 push
            else {
                stk.push(next);
                current = next;
            }
        }
        bw.newLine();
        bw.flush();
    }
    // BFS: Queue 활용
    static void bfs(int[][] arr, int n, int m, int v) throws IOException {
        Queue<Integer> q = new LinkedList<>();
        boolean end = false;
        int current = v;
        boolean[] visit = new boolean[n + 1];
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        // 시작점
        q.offer(current);

        while (!end) {
            // 연결된 노드를 모두 우선순위 큐에 삽입(작은 번호 노드 우선)
            for (int i = 0; i < m; i++) {
                if (current == arr[i][0]) {
                    if (!visit[arr[i][1]]) {
                        pq.offer(arr[i][1]);
                    }
                } else if (current == arr[i][1]) {
                    if (!visit[arr[i][0]]) {
                        pq.offer(arr[i][0]);
                    }
                }
            }
            // 방문하지 않았다면, 방문 후 표시
            if (!visit[current]) {
                bw.write(current + " ");
                visit[current] = true;
            }
            // 현재 노드에 연결된 모든 노드를 큐에 삽입
            while (!pq.isEmpty()) {
                q.offer(pq.poll());
            }

            // 더이상 진행 할 수 없으면 end
            if (q.isEmpty()) {
                end = true;
            } else {
                current = q.poll();
            }
        }
        bw.flush();
    }
}

```

