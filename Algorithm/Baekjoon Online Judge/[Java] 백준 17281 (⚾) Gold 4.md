## Problem : https://www.acmicpc.net/problem/17281

## Approach

> `BruteForce` 문제이다. 순열(Permutation)을 활용하여 생길 수 있는 모든 경우의 수를 생각한다.
>
> 순열의 크기가 8이라서 가능한 일이다.

1번 타자 ~ 9번 타자까지 순서를 정해야한다. 하지만 4번 타자의 경우 이미 정해져있기 때문에 8명만 줄을 세우면 된다.

전체적인 로직은 다음과 같다.

- 1번타자 ~ 9번타자를 정한다.
- 야구게임을 시작한다. 주어진 이닝 동안 아웃카운트를 세면서 게임을 진행한다.
- 주어진 타순으로 게임을 진행했을 때의 최종 득점이 최대가 되게끔 업데이트한다.

## Code

```java
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 *  No.17281: 야구⚾
 *  URL: https://www.acmicpc.net/problem/17281
 *  Hint: BruteForce(순열) + 구현시뮬레이션
 */

public class BOJ17281 {
    static int inning, ans = 0;
    static int[][] arr;
    static int[] res;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        inning = Integer.parseInt(br.readLine());
        arr = new int[inning][9];
        res = new int[9];
        List<Integer> strikers = new ArrayList<>();
        for (int i = 1; i < 9; i++) {
            strikers.add(i);
        }

        for (int i = 0; i < inning; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 9; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        makePermutation(strikers, res, 8, 0, 0);

        bw.write(String.valueOf(ans));
        bw.close();
        br.close();
    }

    // 게임 시작
    static void startGame(int[] order) {
        int score = 0;
        int strikerIdx = 0;
        int outCount = 0;
        int curInning = 0;

        boolean[] base = new boolean[3];    // 1루 2루 3루
        while (curInning < inning) {
            outCount = 0;
            Arrays.fill(base, false);   // 베이스 초기화
            while (outCount < 3) {
                // 9번 타자까지 갔으면 1번 타자로 다시
                if (strikerIdx == 9) {
                    strikerIdx = 0;
                }

                int hit = arr[curInning][order[strikerIdx++]];
                if (hit == 0) {
                    outCount++;
                } else {
                    score += hitAndRun(hit, base);
                }
            }
            curInning++;
        }

        if (ans < score) {
            ans = score;
        }
    }

    // Hit & Run 이후 얻은 점수를 리턴
    static int hitAndRun(int hit, boolean[] base) {
        int score = 0;

        if (hit == 1) { // 1루타
            if (base[2]) {  // 3루에 주자가 있으면 score++
                score++;
                base[2] = false;
            }

            // 1루, 2루의 주자를 각각 2루, 3루로 이동
            for (int i = 2; i > 0; i--) {
                base[i] = base[i - 1];
            }
            base[0] = true; // 1루에 타자가 이동
        } else if (hit == 2) {  // 2루타
            // 2, 3루 베이스에 주자가 있는지 검사
            for (int i = 1; i < 3; i++) {
                if (base[i]) {
                    score++;
                    base[i] = false;
                }
            }
            if (base[0]) {  // 1루에 주자가 있었으면 3루로 이동
                base[2] = true;
                base[0] = false;
            }
            base[1] = true; // 2루에 타자가 이동

        } else if (hit == 3) {  // 3루타
            // 모든 베이스에 주자가 있는지 검사
            for (int i = 0; i < 3; i++) {
                if (base[i]) {
                    score++;
                    base[i] = false;
                }
            }
            base[2] = true; // 3루에 타자가 이동
        } else if (hit == 4) {  // 홈런
            // 모든 베이스에 주자가 있는지 검사
            for (int i = 0; i < 3; i++) {
                if (base[i]) {
                    score++;
                    base[i] = false;
                }
            }
            score++;    // 타자까지
        }

        return score;
    }

    // 타자의 순서를 정하는 순열
    static void makePermutation(List<Integer> strikers, int[] res, int n, int depth, int cnt) {
        if (cnt == 9) {
            startGame(res);
            return;
        }

        for (int i = 0; i < n - depth; i++) {
            if (cnt == 3) {
                makePermutation(strikers, res, n, depth, cnt + 1);
                return;
            }

            res[cnt] = strikers.remove(i);
            makePermutation(strikers, res, n, depth + 1, cnt + 1);
            strikers.add(i, res[cnt]);
        }
    }
}
```

