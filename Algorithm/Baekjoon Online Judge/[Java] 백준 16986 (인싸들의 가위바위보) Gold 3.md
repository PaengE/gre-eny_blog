## Problem : https://www.acmicpc.net/problem/16986



## Approach

> `순열`을 이용한 `완전탐색`과 `구현`이 필요한 문제였다.



먼저 문제에서 주의할 점을 짚고 가자.

- 입력으로 주어지는 `경희`와 `민호`의 패는 라운드 별 패가 아니다. `경희`와 `민호`가 참여하는 게임에서 내는 패의 순서일 뿐이다.
- `지우`가 질 경우에도 사용한 적이 없는 패를 내야한다. (서로 다른 패로 이기는 경우만을 찾는 것이 아니다.)



문제 풀이의 주요 로직은 다음과 같다.

- `순열`을 이용하여 `지우`의 패를 결정한다.
- `지우, 경희, 민호`의 패를 이용하여, 게임을 진행하고 `지우`가 이길 수 있는지를 판단한다.

`지우`가 가지고 있는 패보다 게임을 더 많이 진행할 이유는 없다.

게임을 진행하며, 게임에 참여하는 인원과 게임의 승패 여부를 잘 구현해야 한다.



## Code

```java
import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 *  No.16986: 인싸들의 가위바위보
 *  Hint: BruteForce + 구현
 */

public class BOJ16986 {
    static int n, k;
    static int[][] a;
    static int[][] commands;
    static boolean[] used;
    static boolean flag;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        used = new boolean[n + 1];  // 순열 생성에 필요한 배열

        a = new int[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= n; j++) {
                a[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        commands = new int[4][21];
        for (int i = 2; i <= 3; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= 20; j++) {
                commands[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        permutation(1);

        bw.write(flag ? "1" : "0");
        bw.close();
        br.close();
    }

    // 1번 플레이어가 이기면 true, 그러지 못하면 false 리턴
    static boolean gameStart() {
        int[] winCnt = new int[4];
        int[] actionIndex = new int[4];
        Arrays.fill(actionIndex, 1);

        int player1 = 1, player2 = 2, nextPlayer = 3;

        while (true) {
            nextPlayer = 6 - player1 - player2;
            if (winCnt[1] == k) {
                return true;
            }

            if (winCnt[2] == k || winCnt[3] == k) {
                return false;
            }

            if (actionIndex[1] == n + 1 || actionIndex[2] == 21 || actionIndex[3] == 21) {
                return false;
            }

            int winner = whoWin(player1, player2, actionIndex);
            winCnt[winner]++;
            actionIndex[player1]++;
            actionIndex[player2]++;

            player1 = winner;
            player2 = nextPlayer;
        }
    }

    // 순열
    static void permutation(int depth) {
        if (flag) { // 정답을 찾았으면 리턴
            return;
        }

        if (depth == n + 1) {
            if (gameStart()) {
                flag = true;    // 정답 찾음 표시
            }
            return;
        }

        // 1번 플레이어의 action 구성
        for (int i = 1; i <= n; i++) {
            if (!used[i]) {
                used[i] = true;
                commands[1][depth] = i;
                permutation(depth + 1);
                used[i] = false;
            }
        }
    }

    // 이긴 사람의 번호를 리턴
    static int whoWin(int p1, int p2, int[] actionIndex) {
        int action1 = commands[p1][actionIndex[p1]];
        int action2 = commands[p2][actionIndex[p2]];

        if (a[action1][action2] == 2) {
            return p1;
        } else if (a[action1][action2] == 1) {
            return Math.max(p1, p2);
        } else {
            return p2;
        }
    }
}
```

