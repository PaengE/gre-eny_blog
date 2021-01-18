## Problem : https://programmers.co.kr/learn/courses/30/lessons/17679

## Approach

2018 KAKAO BLIND RECRUITMENT 문제였다.

> 이 문제에서 해야할 과정은 크게 3가지이다. 더이상 square가 생기지 않을 때까지 밑의 세 과정을 반복한다.
>
> 1. 주어진 board에서 square를 찾는다.
> 2. square의 개수를 세고, 해당 위치의 블록을 삭제한다.
> 3. 남은 블록들을 아래로 내려, board를 재구성한다.

- checkBlock에선 현재 board로 square를 찾는다. DP를 이용하여 현재 위치 기준 좌상으로 square를 이루는지를 검사한 후 boolean 값으로 표시했다.

- countBlock에선 boolean 값을 검사하여 square를 이룬부분의 블록 개수를 count하고, board에서 삭제한다.("." 로 표시하였다.)

- dropBlock에서는 board의 맨 아래에서부터 위쪽 방향으로 board에서 삭제된 블록이 있으면 위에 남아있는 블록을 밑으로 내렸다.


## Code

```java
public class Friends4Block {
    static char[][] map;
    public static void main(String[] args) {
        Friends4Block fb = new Friends4Block();
//        int m = 6;
//        int n = 6;
//        String[] board = {"TTTANT", "RRFACC", "RRRFCC", "TRRRAA", "TTMMMF", "TMMTTJ"};

        int m = 4;
        int n = 5;
        String[] board = {"AAAAA", "AUUUA", "AUUAA", "AAAAA"};

        System.out.println(fb.solution(m, n, board));
    }

    public int solution(int m, int n, String[] board) {
        map = new char[m][n];
        for (int i = 0; i < m; i++) {
            map[i] = board[i].toCharArray();
        }

        int answer = 0;
        while (true) {
            int cnt = checkBlock(m, n);
            if (cnt == 0) {
                break;
            }
            answer += cnt;
            dropBlock(m, n);
        }

        return answer;
    }

  // 전체 board를 주어진 위치에서 좌상 방향으로 square를 이루는지를 판단
    public int checkBlock(int m, int n) {
        boolean[][] dp = new boolean[m][n];
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (map[i][j] != '.') {
                    char cur = map[i][j];
                    if (cur == map[i - 1][j - 1] && cur == map[i - 1][j] && cur == map[i][j - 1]) {
                        dp[i - 1][j - 1] = true;
                        dp[i - 1][j] = true;
                        dp[i][j - 1] = true;
                        dp[i][j] = true;
                    }
                }
            }
        }

        return countBlock(m, n, dp);
    }

  // square의 개수가 몇개인지 count
    public int countBlock(int m, int n, boolean[][] dp) {
        int count = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dp[i][j]) {
                    count += 1;
                    map[i][j] = '.';
                }
            }
        }

        return count;
    }

  // 빈 블록을 아래로 내림
    public void dropBlock(int m, int n) {
        for (int i = m - 1; i > 0; i--) {
            for (int j = 0; j < n; j++) {
                if (map[i][j] == '.') {
                    for (int k = i - 1; k >= 0; k--) {
                        if (map[k][j] != '.') {
                            map[i][j] = map[k][j];
                            map[k][j] = '.';
                            break;
                        }
                    }
                }
            }
        }
    }
}

```

