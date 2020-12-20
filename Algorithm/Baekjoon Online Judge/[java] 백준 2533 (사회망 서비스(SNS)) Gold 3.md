## 문제 원문 링크 : https://www.acmicpc.net/problem/2533

## Approach (Wrong)

> 내가 처음 생각했던 잘못된 접근법
>
> - 처음에는 아무 노드나 루트로 잡고 level을 매겨서 min(홀수레벨 총 노드개수, 짝수레벨 총 노드개수)로 구현하면 될 줄 알았다. 질문 게시판에 있는 대부분의 반례 또한 통과되어 맞는 풀이법이라고 생각했으나, 다음과 같은 반례가 있었다.
>
>   <img src="C:\Users\82102\OneDrive\티스토리\Algorithm\Baekjoon Online Judge\image\2253-1.JPG" alt="2253-1" style="zoom:50%;" />



## Approach (Correct)

자신이 얼리어답터인지, 아닌지 일때를 나누어서 생각하여야한다.

- 자신이 얼리어답터라면, 자신과 연결된 노드는 (1)얼리어답터여도 되고 (2)얼리어답터가 아니어도 된다.

- 그러나 자신이 얼리어답터가 아니라면, 자신과 연결된 노드는 모드 얼리어답터여야 한다.

위와 같은 경우를 생각하여 DFS를 진행하며, 최소 얼리어답터 수를 저장하면 된다.



## Code

```java
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *  No.2533: 사회망 서비스(SNS)
 *  URL: https://www.acmicpc.net/problem/2533
 *  Hint: 자신이 얼리어답터일때, 아닐때를 구분하여 최솟값을 구함
 */

public class BOJ2533 {
    static int n;
    static int[][] dp;
    static ArrayList<ArrayList<Integer>> list = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        n = Integer.parseInt(br.readLine());
        dp = new int[n + 1][2];
        for (int i = 0; i <= n; i++) {
            list.add(new ArrayList<>());
        }

        for (int i = 0; i < n - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            list.get(a).add(b);
            list.get(b).add(a);
        }

        dp(1, -1);

        bw.write(String.valueOf(Math.min(dp[1][0], dp[1][1])));
        bw.close();
        br.close();
    }

    static void dp(int cur, int parent) {
        dp[cur][0] = 0;
        dp[cur][1] = 1;

        for (int next : list.get(cur)) {
          // 사이클이 존재하지 않고, 부모가 유일하므로
          // next와 parent가 같으면 단말노드라고 생각할 수 있다.
            if (next != parent) {
                dp(next, cur);
                dp[cur][0] += dp[next][1];
                dp[cur][1] += Math.min(dp[next][0], dp[next][1]);
            }
        }
    }
}

```

