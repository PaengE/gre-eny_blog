## Problem : https://www.acmicpc.net/problem/15683



## Approach

> `BruteForce(완전탐색)` + `구현` 문제이다.
>
> `1 ~ 5번` 연산을 구현해야 하고, 연산에 필요한 `CCTV가 감시하고 있는 영역`을 구현해야 한다.



먼저 `1 ~ 5번 연산`에 필요한 공통적인 부분을 구현해야 한다. (fiilLeft(), fillRight(), fillUp(), fillDown())

위 4개 메소드는 현재 위치 (x, y)에서 XXX 방향으로 `벽(6)`을 만나기 전까지의 위치들을 `#`으로 표시한다.



이제 `1 ~ 5번 연산`을 구현하면 된다. 위의 4개 매소드를 구현하였다면 연산 부분은 4개 메소드를 적절히 조합하여 구현하면 된다.

주의할 점으로, 자바에서는 메소드 파라미터로 넘어오는 배열의 경우 `얕은 복사(Shallow copy)`가 적용된다.

메소드에 배열을 복사하여 넘기는 것이 아닌, 넘겨주는 배열의 레퍼런스를 넘기기 때문에 메소드 내에서 수정이 일어날 경우, 원본 배열이 변경된다.

따라서 파라미터로 배열을 넘겨줄 때, 임시 배열 tmp에 원본 배열 arr을 복사하여 넘겨주어야 한다. 그래야 원본 배열 arr의 수정이 이루어지지 않는다.



## Code

```java
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 *  No.15683: 감시
 *  Hint: backtracking + 구현
 */

public class BOJ15683 {
    static int n, m, ans = Integer.MAX_VALUE;
    static List<Point> points = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        char[][] arr = new char[n][m];
        for (int i = 0; i < n; i++) {
            String s = br.readLine();
            for (int j = 0; j < m; j++) {
                arr[i][j] = s.charAt(2 * j);
                if (arr[i][j] != '0' && arr[i][j] != '6') {
                    points.add(new Point(i, j, arr[i][j]));
                }
            }
        }

        backtracking(0, arr);

        bw.write(String.valueOf(ans));
        bw.close();
        br.close();
    }

    static void backtracking(int idx, char[][] arr) {
        if (idx == points.size()) {
            ans = Math.min(ans, findAnswer(arr));
            return;
        }

        Point tmp = points.get(idx);
        switch (tmp.val) {
            case '1':
                no1(tmp.x, tmp.y, idx, arr);
                break;

            case '2':
                no2(tmp.x, tmp.y, idx, arr);
                break;

            case '3':
                no3(tmp.x, tmp.y, idx, arr);
                break;

            case '4':
                no4(tmp.x, tmp.y, idx, arr);
                break;

            case '5':
                no5(tmp.x, tmp.y, idx, arr);
                break;
        }
    }

    static void no1(int x, int y, int idx, char[][] arr) {
        char[][] tmp1 = copyArrays(arr);
        backtracking(idx + 1, fillLeft(x, y, tmp1));

        char[][] tmp2 = copyArrays(arr);
        backtracking(idx + 1, fillRight(x, y, tmp2));

        char[][] tmp3 = copyArrays(arr);
        backtracking(idx + 1, fillUp(x, y, tmp3));

        char[][] tmp4 = copyArrays(arr);
        backtracking(idx + 1, fillDown(x, y, tmp4));
    }

    static void no2(int x, int y, int idx, char[][] arr) {
        char[][] tmp1 = copyArrays(arr);
        fillLeft(x, y, tmp1);
        fillRight(x, y, tmp1);
        backtracking(idx + 1, tmp1);

        char[][] tmp2 = copyArrays(arr);
        fillUp(x, y, tmp2);
        fillDown(x, y, tmp2);
        backtracking(idx + 1, tmp2);
    }

    static void no3(int x, int y, int idx, char[][] arr) {
        char[][] tmp1 = copyArrays(arr);
        fillLeft(x, y, tmp1);
        fillUp(x, y, tmp1);
        backtracking(idx + 1, tmp1);

        char[][] tmp2 = copyArrays(arr);
        fillUp(x, y, tmp2);
        fillRight(x, y, tmp2);
        backtracking(idx + 1, tmp2);

        char[][] tmp3 = copyArrays(arr);
        fillRight(x, y, tmp3);
        fillDown(x, y, tmp3);
        backtracking(idx + 1, tmp3);

        char[][] tmp4 = copyArrays(arr);
        fillDown(x, y, tmp4);
        fillLeft(x, y, tmp4);
        backtracking(idx + 1, tmp4);
    }

    static void no4(int x, int y, int idx, char[][] arr) {
        char[][] tmp1 = copyArrays(arr);
        fillLeft(x, y, tmp1);
        fillUp(x, y, tmp1);
        fillRight(x, y, tmp1);
        backtracking(idx + 1, tmp1);

        char[][] tmp2 = copyArrays(arr);
        fillUp(x, y, tmp2);
        fillRight(x, y, tmp2);
        fillDown(x, y, tmp2);
        backtracking(idx + 1, tmp2);

        char[][] tmp3 = copyArrays(arr);
        fillRight(x, y, tmp3);
        fillDown(x, y, tmp3);
        fillLeft(x, y, tmp3);
        backtracking(idx + 1, tmp3);

        char[][] tmp4 = copyArrays(arr);
        fillDown(x, y, tmp4);
        fillLeft(x, y, tmp4);
        fillUp(x, y, tmp4);
        backtracking(idx + 1, tmp4);
    }

    static void no5(int x, int y, int idx, char[][] arr) {
        char[][] tmp = copyArrays(arr);
        fillLeft(x, y, tmp);
        fillRight(x, y, tmp);
        fillUp(x, y, tmp);
        fillDown(x, y, tmp);
        backtracking(idx + 1, tmp);
    }

    static char[][] fillLeft(int x, int y, char[][] arr) {
        for (int i = y - 1; i >= 0; i--) {
            if (arr[x][i] == '6') {
                return arr;
            }
            arr[x][i] = '#';
        }
        return arr;
    }

    static char[][] fillRight(int x, int y, char[][] arr) {
        for (int i = y + 1; i < m; i++) {
            if (arr[x][i] == '6') {
                return arr;
            }
            arr[x][i] = '#';
        }
        return arr;
    }

    static char[][] fillUp(int x, int y, char[][] arr) {
        for (int i = x - 1; i >= 0; i--) {
            if (arr[i][y] == '6') {
                return arr;
            }
            arr[i][y] = '#';
        }
        return arr;
    }

    static char[][] fillDown(int x, int y, char[][] arr) {
        for (int i = x + 1; i < n; i++) {
            if (arr[i][y] == '6') {
                return arr;
            }
            arr[i][y] = '#';
        }
        return arr;
    }

    static int findAnswer(char[][] arr){
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (arr[i][j] == '0') {
                    cnt++;
                }
            }
        }
        return cnt;
    }

    static char[][] copyArrays(char[][] original) {
        char[][] copy = new char[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                copy[i][j] = original[i][j];
            }
        }
        return copy;
    }

    static class Point {
        int x, y;
        char val;

        Point(int x, int y, char val) {
            this.x = x;
            this.y = y;
            this.val = val;
        }
    }
}
```

