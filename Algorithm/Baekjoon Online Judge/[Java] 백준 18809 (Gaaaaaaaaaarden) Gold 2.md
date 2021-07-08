## Problem : https://www.acmicpc.net/problem/18809



## Approach

> `조합을 이용한 BruteForce` 와 `BFS + 구현` 이 필요한 문제이다.
>
> 천천히 생각하고 필요한 부분을 캡슐화하여 메소드를 작성하면 된다.



코드에 사용된 변수 설명은 다음과 같다.

- `availableLands`: 배양액을 뿌릴 수 있는 `Land`를 저장한 리스트
- `selectedAvailableLands`: availableLands에서  `g + r`개의 `Land` 를 뽑는 `조합`에 이용할 boolean 배열
- `selectedLands`: 배양액을 뿌릴 곳으로 선택된 `g + r` 크기의 배열
- `selectedGreen`: selectedLands 중 `초록색 배양액`을 뿌릴 곳으로 선택한 boolean 배열
- `time`: 해당 영역에 배양액이 몇초에 도착했는지를 나타내는 배열



문제 풀이의 큰 흐름은 아래와 같지만, `bfs()`를 구현하는데에 있어서는 약간의 디테일이 필요하다.

- 입력을 받으면서 `배양액을 뿌릴 수 있는 곳`을 `availableLands`에 추가한다.
- `availableLands` 중 `g + r` 개를 뽑는 `모든 조합`을 구한다. -> `selectLand()`
- 각 `조합` 에 대하여, `g + r` 개수 중 `g`개 만큼을 `조합`으로 뽑는다. -> `selectGreen()`
- `초록색 배양액` 을 먼저 처리하는 방식으로 `BFS` 를 진행하면서, 피어날 꽃의 개수를 센다. -> `bfs()`



본인은 map의 각 요소에 대해 `0:물, 1:배양액X, 2:배양액O, 3:초록색배양액, 4:빨간색배양액, 5:꽃` 으로 처리하면서, 이를 `bfs()`에 이용하였다.

아래 코드의 각 메소드에 달린 코멘트를 보면 이해하기 더 쉬울 것이다.



## Code

```java
import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 *  No.18809: Gaaaaaaaaaarden
 *  Hint: BruteForce(조합) + BFS + 구현
 */

public class BOJ18809 {
    static int n, m, g, r;
    static int[][] map; // 0:물, 1:배양액X, 2:배양액O, 3:초록색배양액, 4:빨간색배양액, 5:꽃
    static ArrayList<Land> availableLands = new ArrayList<>();  // 배양액을 뿌릴 수 있는 곳
    static boolean[] selectedAvailableLands;    // 조합 생성에 필요한 check 배열
    static Land[] selectedLands;    // 배양액을 뿌릴 곳으로 선정된 g+r개의 땅 배열
    static boolean[] selectedGreen; // 고른 g+r개의 땅 배열에 무슨색 배양액을 뿌릴지 저장한 배열 (true:초록색, false:빨간색)
    static int ans = 0;

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        g = Integer.parseInt(st.nextToken());
        r = Integer.parseInt(st.nextToken());
        map = new int[n][m];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 2) {
                    availableLands.add(new Land(i, j));
                }
            }
        }
        
        selectedAvailableLands = new boolean[availableLands.size()];
        selectedLands = new Land[g + r];
        selectedGreen = new boolean[g + r];

        selectLand(0, 0);
        bw.write(String.valueOf(ans));
        bw.close();
        br.close();
    }


    // 배양액을 뿌릴 수 있는 땅 중 r + g 개를 조합 선택
    static void selectLand(int depth, int idx) {
        if (depth == g + r) {
            selectGreen(0, 0);
            return;
        }

        for (int i = idx; i < availableLands.size(); i++) {
            selectedAvailableLands[i] = true;
            selectedLands[depth] = availableLands.get(i);
            selectLand(depth + 1, i + 1);
            selectedAvailableLands[i] = false;
        }
    }

    // r + g개의 땅 중 r을 심을 땅을 조합 선택
    static void selectGreen(int depth, int idx) {
        if (depth == g) {
            ans = Math.max(ans, bfs());
            return;
        }

        for (int i = idx; i < selectedLands.length; i++) {
            selectedGreen[i] = true;
            selectGreen(depth + 1, i + 1);
            selectedGreen[i] = false;
        }
    }

    // 배양액을 퍼트려 만들 수 있는 꽃의 개수를 리턴
    static int bfs() {
        int[][] tmpMap = copyMap();     // 임시 map 배열
        int[][] time = new int[n][m];   // (i, j)를 몇초에 방문했는지 저장하는 배열
        Queue<Land> q = new ArrayDeque<>();

        // 초록색 배양액 먼저
        for (int i = 0; i < selectedLands.length; i++) {
            Land l = selectedLands[i];
            if (selectedGreen[i]) {
                q.offer(new Land(l.x, l.y, 'G'));
                tmpMap[l.x][l.y] = 3;
            }
        }

        // 빨간색 배양액 나중에
        for (int i = 0; i < selectedLands.length; i++) {
            Land l = selectedLands[i];
            if (!selectedGreen[i]) {
                q.offer(new Land(l.x, l.y, 'R'));
                tmpMap[l.x][l.y] = 4;
            }
        }

        int cnt = 0;
        while (!q.isEmpty()) {
            Land cur = q.poll();

            // 현재 위치에 꽃이 자랐으면 continue
            if (tmpMap[cur.x][cur.y] == 5) {
                continue;
            }

            for (int i = 0; i < 4; i++) {
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];

                if (isInRange(nx, ny)) {    // 범위 체크
                    if (canMove(nx, ny, tmpMap)) {  // 배양액이 퍼져 나갈 수 있으면
                        // G,R 을 구분하여 임시 map을 갱신
                        if (cur.color == 'G') {
                            tmpMap[nx][ny] = 3;
                        } else {
                            tmpMap[nx][ny] = 4;
                        }
                        q.offer(new Land(nx, ny, cur.color));
                        time[nx][ny] = time[cur.x][cur.y] + 1;
                    } else if (cur.color == 'R' && tmpMap[nx][ny] == 3 && time[nx][ny] == time[cur.x][cur.y] + 1) {
                        // 현재 배양액이 빨간색이고, 다음 지점에 초록색 배양액이 있으며, 동시에 도착했다면
                        tmpMap[nx][ny] = 5; // 임시 map에서 해당 지점을 꽃으로 갱신하고 cnt를 증가시킴
                        cnt++;
                    }
                }
            }
        }
        return cnt;
    }

    // 임시 map 배열을 복사하기 위한 메소드
    static int[][] copyMap() {
        int[][] tmp = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                tmp[i][j] = map[i][j];
            }
        }
        return tmp;
    }

    // map 범위 체크 메소드
    static boolean isInRange(int x, int y) {
        if (x >= 0 && x < n && y >= 0 && y < m) {
            return true;
        }
        return false;
    }

    // 배양액이 퍼질수 있는지를 체크하는 메소드
    static boolean canMove(int x, int y, int[][] tmp) {
        if (tmp[x][y] == 1 || tmp[x][y] == 2) {
            return true;
        }
        return false;
    }

    static class Land{
        int x, y;
        char color;

        Land(int x, int y) {
            this.x = x;
            this.y = y;
        }

        Land(int x, int y, char color) {
            this.x = x;
            this.y = y;
            this.color = color;
        }
    }
}
```

