## Problem : https://www.acmicpc.net/problem/16957



## Approach

> `Union-Find` 성격의 개념과 `DFS`를 혼용하여 풀이한 문제이다.



처음엔 모든 위치를 기준으로 `DFS`를 수행하여 정답을 구하였다. 이 방법은 `TLE(Time Limit Error)`가 발생한다.

그러므로 다른 방법을 사용해야 한다. 시간초과를 받은 코드와 정답처리를 받은 코드를 함께 게시하겠다.



`DFS`는 불가피하다. 그러므로 `DFS`의 횟수를 줄이면서 알고리즘 개선을 하여야 한다.

이 때, 필요한 개념이 `Union-Find`의 `Find`개념이다. 

p(i, j) = (i, j) 로 초기화 한 뒤, 방문하지 않은 곳들을 시작점으로 `DFS`를 진행하면서, (i, j) 위치를 기준으로 움직일 수 있는 곳(ni, nj)을 p(i, j)로 지정한다.

위 과정이 끝나고 난 뒤, p(i, j) == (i, j) 인 곳은 공이 모이는 곳이다. 

따라서 모든 위치에 대하여 p(i, j) 의 위치에 공의 개수를 +1 한다. 

그리고 공의 개수를 저장한 배열을 출력하면 된다.



## Code (TLE)

```java
/**
 *  시간초과 코드 (DFS)
 */

import java.io.*;
import java.util.StringTokenizer;

public class BOJ16957 {
    static int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
    static int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
    static int[][] arr, ans;
    static int r, c;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        arr = new int[r][c];
        ans = new int[r][c];

        for (int i = 0; i < r; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < c; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                dfs(i, j);
            }
        }

        bw.write(printAnswer().toString());
        bw.close();
        br.close();
    }

    static void dfs(int cx, int cy) {
        int nx = Integer.MAX_VALUE, ny = Integer.MAX_VALUE;
        int minVal = Integer.MAX_VALUE;

        for (int i = 0; i < 8; i++) {
            int tx = cx + dx[i];
            int ty = cy + dy[i];

            if (isInRange(tx, ty) && isLowerValueLocation(cx, cy, tx, ty, minVal)) {
                minVal = arr[tx][ty];
                nx = tx;
                ny = ty;
            }
        }

        if (nx == Integer.MAX_VALUE) {
            ans[cx][cy]++;
        } else {
            dfs(nx, ny);
        }
    }

    static boolean isLowerValueLocation(int cx, int cy, int tx, int ty, int minVal) {
        if (arr[tx][ty] < arr[cx][cy] && arr[tx][ty] < minVal) {
            return true;
        }
        return false;
    }

    static boolean isInRange(int x, int y) {
        if (x >= 0 && x < r && y >= 0 && y < c) {
            return true;
        }
        return false;
    }

    static StringBuilder printAnswer() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                sb.append(ans[i][j] + " ");
            }
            sb.append("\n");
        }
        return sb;
    }

    static class Point{
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
```



## Code(Correct)

```java
import java.io.*;
import java.util.StringTokenizer;

/**
 *  No.16957: 체스판 위의 공
 *  Hint: UnionFind + DFS
 */

public class BOJ16957 {
    static int r, c;
    static int[] parent;
    static int[][] arr;
    static int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
    static int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        arr = new int[r][c];
        parent = new int[r * c];

        for (int i = 0; i < r; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < c; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
                parent[i * c + j] = i * c + j;
            }
        }

        bw.write(solve());
        bw.close();
        br.close();
    }

    static String solve() {
        StringBuilder sb = new StringBuilder();

        // 방문하지 않은 위치를 기준으로 DFS 수행
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (parent[i * c + j] == i * c + j) {
                    dfs(i, j);
                }
            }
        }

        // parent 를 참조하여 정답 배열 ans 구성
        int maxSize = r * c;
        int[] ans = new int[maxSize];
        for (int i = 0; i < maxSize; i++) {
            ans[find(i)]++;
        }

        // 정답 String 구성
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                sb.append(ans[i * c + j] + " ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    static void dfs(int cx, int cy) {
        int minVal = arr[cx][cy];
        int minX = 0, minY = 0;

        // 8방향 검사
        for (int i = 0; i < 8; i++) {
            int nx = cx + dx[i];
            int ny = cy + dy[i];
            if (isInRange(nx, ny) && minVal > arr[nx][ny]) {
                minVal = arr[nx][ny];
                minX = nx;
                minY = ny;
            }
        }

        // 이동할 수 있으면 현재 위치의 parent 를 갱신 후 DFS
        if (minVal < arr[cx][cy]) {
            parent[cx * c + cy] = minX * c + minY;
            if (parent[minX * c + minY] == minX * c + minY) {
                dfs(minX, minY);
            }
        }
    }

    // x 의 parent 를 찾음
    static int find(int x) {
        if (parent[x] == x) {
            return x;
        }

        return parent[x] = find(parent[x]);
    }

    // 체스판의 범위 체크
    static boolean isInRange(int x, int y) {
        if (x >= 0 && x < r && y >= 0 && y < c) {
            return true;
        }
        return false;
    }
}
```

