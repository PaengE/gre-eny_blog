## Problem : https://www.acmicpc.net/problem/2239

## Approach

> 유명한 `스도쿠 게임`을 구현하는 문제였다.
>
> 사용되는 알고리즘은 `백트랙킹`이다.

비어있는 칸을 백트랙킹을 하며, 스도쿠에서 중요한 가로, 세로, 3x3 영역을 검사하며 놓을 수 있는지를 판단한다.

3x3 영역 부분은 3x3 영역의 좌상위치를 구한 뒤, 거기서부터 x, y 좌표 + 3까지를 검사했다.

## Code

```java
import java.io.*;
import java.util.ArrayList;

public class BOJ2239 {
    static int[][] map;
    static boolean end;
    static ArrayList<P> blankList = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        map = new int[9][9];
        blankList = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            String[] s = br.readLine().split("");
            for (int j = 0; j < 9; j++) {
                map[i][j] = Integer.parseInt(s[j]);
                if (map[i][j] == 0) {
                    blankList.add(new P(i, j));
                }
            }
        }
        sudoku(0);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                bw.write(String.valueOf(map[i][j]));
            }
            bw.newLine();
        }

        bw.close();
        br.close();
    }

    static void sudoku(int idx) {
        // 첫 번째 답이 81자리 수가 가장 작음
        if (idx == blankList.size()) {
            end = true;
            return;
        }

        P cur = blankList.get(idx);
        for (int i = 1; i <= 9; i++) {
            map[cur.x][cur.y] = i;
            // 스도쿠 성립의 3가지 조건을 검사
            if (checkSquare(cur.x, cur.y) && checkHorizontal(cur.x, cur.y) && checkVertical(cur.x, cur.y)) {
                sudoku(idx + 1);
            }
            if (end) {
                return;
            }
            map[cur.x][cur.y] = 0;
        }
    }

    // 3x3 영역 검사
    static boolean checkSquare(int x, int y) {
        int sx = (x / 3) * 3;
        int sy = (y / 3) * 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (x != sx + i || y != sy + j) {
                    if (map[sx + i][sy + j] == map[x][y]) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    // 세로 검사
    static boolean checkVertical(int x, int y) {
        for (int i = 0; i < 9; i++) {
            if (i != x) {
                if (map[i][y] == map[x][y]) {
                    return false;
                }
            }
        }

        return true;
    }

    // 가로 검사
    static boolean checkHorizontal(int x, int y) {
        for (int i = 0; i < 9; i++) {
            if (i != y) {
                if (map[x][i] == map[x][y]) {
                    return false;
                }
            }
        }

        return true;
    }

    static class P{
        int x, y;

        P(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
```

