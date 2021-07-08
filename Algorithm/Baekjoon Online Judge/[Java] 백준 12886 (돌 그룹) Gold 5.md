## Problem : https://www.acmicpc.net/problem/12886

## Approach

> `DFS`를 사용하여 풀이한 문제이다. `BFS`로도 풀이가 가능한 것 같다.
>
> 새로 만든 숫자와 기존 숫자를 이용하여야 하기 때문에 DFS가 먼저 생각이나서 DFS로 풀이하였다.

먼저 처리할 수 있는 예외를 보자.

`(a + b + c) % 3`이 0이 아니라면 세 숫자를 어떻게 선택하더라도, 서로 같게 만들 수 없다.



문제 풀이 로직은 다음과 같다. 숫자 a, b, c가 있을 때,

- (a, b)를 활용하여 숫자 A, B를 만들고 그 숫자들과 c로 다음 단계를 진행한다.
- (b, c)를 활용하여 숫자 B, C를 만들고 그 숫자들과 a로 다음 단계를 진행한다.
- (a, c)를 활용하여 숫자 A, C를 만들고 그 숫자들과 b로 다음 단계를 진행한다.

위의 단계를 진행하면서 세 숫자 모두가 같아지는 경우가 있으면 답을 1로 바꾸고 종료한다. 

## Code

```java
import java.io.*;
import java.util.StringTokenizer;

/**
 *  No.12886: 돌 그룹
 *  URL: https://www.acmicpc.net/problem/12886
 *  Hint: DFS
 */

public class BOJ12886 {
    static int a, b, c, ans;
    static boolean[][] visited = new boolean[1501][1501];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        a = Integer.parseInt(st.nextToken());
        b = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        if ((a + b + c) % 3 != 0) { // 모두 더한 값 % 3 이 0이 아니면 세 숫자를 모두 같게 만들 수 없음
            bw.write("0");
        } else {
            dfs(a, b, c);
            bw.write(String.valueOf(ans));
        }

        bw.close();
        br.close();
    }

    static void dfs(int a, int b, int c) {
        if (a == b && b == c) { // 세 숫자 모두 같게 만들 수 있으면 리턴
            ans = 1;
            return;
        }

        calc(a, b, c);  // (a, b)를 조작하는 경우
        calc(b, c, a);  // (b, c)를 조작하는 경우
        calc(a, c, b);  // (a, c)를 조작하는 경우
    }

    static void calc(int a, int b, int origin) {
        int small = Math.min(a, b);
        int big = Math.max(a, b);

        // 만든 숫자를 이전에 만든 적이 있는지 체크
        if (!visited[small * 2][big - small]) {
            visited[small * 2][big - small] = visited[big - small][small * 2] = true;
            dfs(small * 2, big - small, origin);
        }
    }
}
```

