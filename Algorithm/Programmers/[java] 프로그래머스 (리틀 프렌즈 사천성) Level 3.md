## Problem : https://programmers.co.kr/learn/courses/30/lessons/1836

## Approach

> `2017 카카오코드 본선`문제이다.
>
> 구현에 필요한 별다른 테크닉은 필요없고, 여러 경우에 대해 조건 검사를 많이 해야되는 문제였다.

두 타일 사이가 최대 한번의 꺾임만으로 서로 도달할 수 있는지에 대한 문제이다.

동시에 없앨 수 있는 타일이 여러개이면, 알파벳이 작은 순부터 처리해야하므로, `정렬`은 사용해야 되겠다는 점만 알고가자. (여기서는 알파벳 오름차순, 알파벳이 같으면 왼쪽->오른쪽 순으로 정렬했다.)

일단 지울 수 있는 두 타일이라면, 4가지 경우가 존재한다.

1. 두 타일이 같은 행에 있는 경우: 두 타일 사이 일직선 상의 경로에 장애물이 없음을 검사한다.
2. 두 타일이 같은 열에 있는 경우: 두 타일 사이 일직선 상의 경로에 장애물이 없음을 검사한다.
3. 두 타일이 각각 좌상, 우하에 있는 경우: 좌상에 있는 타일 기준으로 우->하, 하->우 경로에 장애물이 없음을 검사한다.
4. 두 타일이 각각 좌하, 우상에 있는 경우: 좌하에 있는 타일 기준으로 우->상, 상->우 경로에 장애물이 없음을 검사한다.

3, 4번에서 기준으로 삼는 타일은 중요하지 않다. 두 타일을 직각으로 이루는 경로를 검사한다는게 중요하다.
밑의 코드에서는 항상 왼쪽에 있는 타일을 기준으로 검사하였다.

한 종류의 타일을 삭제할 때에는 board에서 빈칸으로 바꾼다. 그리고 다시 알파벳이 작은 타일부터 검사한다.



if 문을 작성할 때, 처음부터 천천히 생각해서 조건을 만드는 것이 헷갈리지 않고 좋은 것 같다.

## Code

```java
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class LittleFriendsSacheonseong {
    static ArrayList<Tile> list;
    static int m, n;
    static char[][] b;
    public String solution(int m, int n, String[] board) {
        this.m = m;
        this.n = n;
        b = new char[m][n];
        for (int i = 0; i < board.length; i++) {
            b[i] = board[i].toCharArray();
        }

        initArrayList(b);

        StringBuilder sb = new StringBuilder();
        int i = 0;
        loop:
        for (i = 0; i < list.size(); i += 2) {
            Tile t1 = list.get(i);
            Tile t2 = list.get(i + 1);
            if (canRemove(t1, t2)) {
                sb.append(t1.c);
                list.remove(i);
                list.remove(i);
                b[t1.x][t1.y] = '.';
                b[t2.x][t2.y] = '.';
                i = -2;
                continue loop;
            }
        }
        return i == 0 ? sb.toString() : "IMPOSSIBLE";
    }

    private boolean canRemove(Tile t1, Tile t2) {
        // 두 타일의 y좌표가 같지 않은 이상, t1이 왼쪽
        // y좌표가 같으면 t1이 위쪽
        if (t1.x == t2.x) {   // 가로 일직선 상에 있는 경우
            // 직선 상에 장애물이 없는지 검사
            for (int i = t1.y + 1; i <= t2.y - 1; i++) {
                if (b[t1.x][i] != '.') {
                    return false;
                }
            }
            return true;
        } else if (t1.y == t2.y) {    // 세로 일직선 상에 있는 경우
            // 직선 상에 장애물이 없는지 검사
            for (int i = t1.x + 1; i <= t2.x - 1; i++) {
                if (b[i][t1.y] != '.') {
                    return false;
                }
            }
            return true;
        } else {    // 위의 두 경우가 아닌경우 (좌상우하 와 우상좌하의 2가지 경우를 나눠야함
            boolean check1 = true, check2 = true;

            if (t1.x < t2.x) {  // 두 타일이 좌상, 우하에 있는 경우
                // 우->하 직선경로 검사
                for (int i = t1.y + 1; i <= t2.y; i++) {
                    if (b[t1.x][i] != '.') {
                        check1 = false;
                        break;
                    }
                }
                for (int i = t1.x + 1; i <= t2.x - 1; i++) {
                    if (!check1) {
                        break;
                    }
                    if (b[i][t2.y] != '.') {
                        check1 = false;
                        break;
                    }
                }

                // 하->우 직선경로 검사
                for (int i = t1.x + 1; i <= t2.x; i++) {
                    if (b[i][t1.y] != '.') {
                        check2 = false;
                        break;
                    }
                }
                for (int i = t1.y + 1; i <= t2.y - 1; i++) {
                    if (!check2) {
                        break;
                    }
                    if (b[t2.x][i] != '.') {
                        check2 = false;
                        break;
                    }
                }

            } else {    // 두 타일이 좌하, 우상에 위치해있는 경우
                // 우->상 직선경로 검사
                for (int i = t1.y + 1; i <= t2.y; i++) {
                    if (b[t1.x][i] != '.') {
                        check1 = false;
                        break;
                    }
                }
                for (int i = t1.x; i >= t2.x + 1; i--) {
                    if (!check1) {
                        break;
                    }
                    if (b[i][t2.y] != '.') {
                        check1 = false;
                        break;
                    }
                }
                // 상->우 직선경로 검사
                for (int i = t1.x - 1; i >= t2.x; i--) {
                    if (b[i][t1.y] != '.') {
                        check2 = false;
                        break;
                    }
                }
                for (int i = t1.y + 1; i <= t2.y - 1; i++) {
                    if (!check2) {
                        break;
                    }
                    if (b[t2.x][i] != '.') {
                        check2 = false;
                        break;
                    }
                }
            }

            // 두 경로중 하나라도 가능한 경로라면
            if (check1 || check2) {
                return true;
            } else {
                return false;
            }
        }
    }

    private void initArrayList(char[][] b) {
        list = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Character c = b[i][j];
                if (Character.isUpperCase(c)) {
                    list.add(new Tile(c, i, j));
                }
            }
        }

        // 알파벳 작은순으로 정렬
        // 알파벳이 같으면 왼쪽에 있는 타일 먼저
        Collections.sort(list, ((o1, o2) -> {
            if (o1.c == o2.c) {
                if (o1.x == o2.x) {
                    return Integer.compare(o1.y, o2.y);
                } else if (o1.y == o2.y) {
                    return Integer.compare(o1.x, o2.x);
                } else {
                    return Integer.compare(o1.y, o2.y);
                }
            }
            return Character.compare(o1.c, o2.c);
        }));
    }

    private class Tile {
        char c;
        int x, y;

        Tile(char c, int x, int y) {
            this.c = c;
            this.x = x;
            this.y = y;
        }
    }

    @Test
    public void test() {
        Assertions.assertEquals("ABCD", solution(3, 3, new String[]{"DBA", "C*A", "CDB"}));
        Assertions.assertEquals("RYAN", solution(2, 4, new String[]{"NRYN", "ARYA"}));
        Assertions.assertEquals("MUZI", solution(4, 4, new String[]{".ZI.", "M.**", "MZU.", ".IU."}));
        Assertions.assertEquals("IMPOSSIBLE", solution(2, 2, new String[]{"AB", "BA"}));
    }
}

```

