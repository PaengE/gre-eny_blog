## Problem : https://www.acmicpc.net/problem/2667

## Approach

보통 지도 형식의 배열이 나오면 BFS로 푸는게 더 나은 것 같다...(필자 생각)

BFS, DFS 모두 풀이가 가능해도 더 좋은 풀이가 있는 문제처럼, 지도를 통한 탐색의 경우 필자는 BFS를 더 선호한다. (더 간단하기 때문 ㅎㅎ)

이 문제는 감을 익히기 위해서 두 방법 모두로 풀어봤다.

또한 이 문제는 프로그래머스에도 유사한 문제가 있다. 밑의 링크는 그 문제에 대한 나의 풀이이다.

[2020/12/27 - [Algorithm/Programmers\] - [java] 프로그래머스 (카카오프렌즈 컬러링) Level 2﻿](https://gre-eny.tistory.com/58)

![2667-1](C:\Users\82102\OneDrive\티스토리\Algorithm\Baekjoon Online Judge\image\2667-1.png)

1로 구성되고 분리된 영역을 찾으려면 일단 배열의 모든 요소에 접근해봐야 한다. 그렇지 않으면 그 부분의 요소가 1인지 아닌지 알수가 없다.

방문했을때 1이라면, 거기서부터 BFS를 적용한다. (상하좌우를 검사하여 사각형의 범위를 벗어나지 않고, 방문한 적이 없다면 탐색한다.) 탐색이 종료된다면 그것이 하나의 영역이다.

방문했을때 0이라면 넘긴다.

이것을 (1, 1)부터 (n, n)까지 진행해 나간다.

## Code

> DFS 풀이

```java
import java.io.*;
import java.util.PriorityQueue;

/**
 * no.2667: 단지번호붙이기
 * description: 2차원 배열을 그래프로 표현해 DFS 로 순회하는 문제
 * hint: DFS 를 이용해 현재 정점 기준 상하좌우를 방문하며 순회 (아직 방문하지 않았으면)
 */

public class BOJ2667_DFS {
    static boolean[][] visited;
    static int[][] map;
    static int dx[] = {0, 0, 1, -1};
    static int dy[] = {1, -1, 0, 0};
    static int n, count;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        n = Integer.parseInt(br.readLine());
        map = new int[n][n];
        String[] s;
        visited = new boolean[n][n];

        for (int i = 0; i < n; i++) {
            s = br.readLine().split("");
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(s[j]);
            }
        }

        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (map[i][j] == 1 && !visited[i][j]) {
                    count = 0;
                    dfs(i, j);
                    pq.offer(count);
                }
            }
        }

        bw.write(pq.size() + "\n");

        while (!pq.isEmpty()) {
            bw.write(pq.poll() + "\n");
        }

        bw.flush();
        br.close();
        bw.close();
    }
    static void dfs(int x, int y) {
        visited[x][y] = true;
        count++;

        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (nx >= 0 && ny >= 0 && nx < n && ny < n) {
                if (map[nx][ny] == 1 && !visited[nx][ny]) {
                    dfs(nx, ny);
                }
            }
        }
    }
}

```

> BFS 풀이

```java
import java.io.*;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
/**
 * no.2667: 단지번호붙이기
 * description: 2차원 배열을 그래프로 표현해 BFS 로 순회하는 문제
 * hint: BFS 를 이용해 현재 정점 기준 상하좌우를 방문하며 순회 (아직 방문하지 않았으면)
 */

public class BOJ2667_BFS {
    static boolean[][] visited;
    static int[][] map;
    static int dx[] = {0, 0, 1, -1};
    static int dy[] = {1, -1, 0, 0};
    static int n, count;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        n = Integer.parseInt(br.readLine());
        map = new int[n][n];
        String[] s;
        visited = new boolean[n][n];

        for (int i = 0; i < n; i++) {
            s = br.readLine().split("");
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(s[j]);
            }
        }

        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (map[i][j] == 1 && !visited[i][j]) {
                    count = 0;
                    bfs(i, j);
                    pq.offer(count);
                }
            }
        }

        bw.write(pq.size() + "\n");

        while (!pq.isEmpty()) {
            bw.write(pq.poll() + "\n");
        }

        bw.flush();
        br.close();
        bw.close();
    }

    static void bfs(int x, int y) {
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{x, y});
        visited[x][y] = true;
        count++;

        while (!q.isEmpty()) {
            int curX = q.peek()[0];
            int curY = q.peek()[1];
            q.poll();

            for (int i = 0; i < 4; i++) {
                int nx = curX + dx[i];
                int ny = curY + dy[i];

                if (nx >= 0 && ny >= 0 && nx < n && ny < n) {
                    if (map[nx][ny] == 1 && !visited[nx][ny]) {
                        q.add(new int[]{nx, ny});
                        visited[nx][ny] = true;
                        count++;
                    }
                }
            }
        }

    }
}

```

