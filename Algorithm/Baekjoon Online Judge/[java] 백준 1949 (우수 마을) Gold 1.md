## 문제 원문 링크 : https://www.acmicpc.net/problem/1949

## Approach(Wrong: TLE)

트리에서의 DP문제로, 2213번 트리의 독립집합과 유사한 문제이다.

DFS를 이용하여 트리 끝까지 내려가면 함수가 차례차례 종료되는 것을 이용하여 값을 갱신했지만 시간초과(TLE)가 떴다.

질문 글을 올려 답변을 받자면 내 잘못된 코드는 DP를 수행하지 않고 그냥 탐색만 하는 것이므로 각 호출에서 구한 답을 메모이제이션 해보라는 답변을 받았다.

<u>답변해 주신 djm03178 분께 감사말을 드린다!</u>

## Approach(Correct)

문제의 조건이다.

1. '우수 마을'로 선정된 마을 주민 수의 총 합을 최대로 해야 한다.
2. 마을 사이의 충돌을 방지하기 위해서, 만일 두 마을이 인접해 있으면 두 마을을 모두 '우수 마을'로 선정할 수는 없다. 즉 '우수 마을'끼리는 서로 인접해 있을 수 없다.
3. 선정되지 못한 마을에 경각심을 불러일으키기 위해서, '우수 마을'로 선정되지 못한 마을은 적어도 하나의 '우수 마을'과는 인접해 있어야 한다.

> 여기서 3번 조건은 고려하지 않고 문제를 풀어도 정답을 받을수 있다
>
> - 왜?
>
>   N번째 마을이 우수마을이라면 N+1번째 마을은 우수마을이면 안되지만,
>
>   N번째 마을이 우수마을이 아니라면 N+1번째 마을은 우수마을이 될수도, 안될수도 있다.
>
>   마찬가지로 N+1번째 마을이 우수마을이 아니라면 N+2번째 마을은 우수마을이 될수도 안될수도 있다.
>
>   하지만 우수마을의 주민합을 최대로 하는 것이 목표이기 때문에 N+1번째 마을 또는 N+2번째 마을을 무조건 우수마을로 선정하는 것이 이득이다.
>
>   그렇게 되면 2번 조건을 만족할 수 있으므로, 따로 3번 조건을 고려하지 않고 최대 합을 구해도 무관하다.

TLE를 받았던 코드에서 메모이제이션을 해보았다.

현재마을이 우수마을이면, 다음마을은 우수마을이면 안된다.

현재마을이 우수마을이 아니라면, 다음마을은  max(우수마을일 때, 아닐 때)로 구한다.

## Code(Wrong: TLE)

```java
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BOJ1949 {
    static int n;
    static int[] arr;
    static ArrayList<ArrayList<Integer>> list = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        n = Integer.parseInt(br.readLine());
        arr = new int[n + 1];

        for (int i = 0; i <= n; i++) {
            list.add(new ArrayList<>());
        }

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            list.get(a).add(b);
            list.get(b).add(a);
        }

        int result = Math.max(dp(1, -1, true) + arr[1], dp(1, -1, false));

        bw.write(String.valueOf(result));
        bw.close();
        br.close();
    }

    static int dp(int cur, int parent, boolean flag) {
        int ans = 0;

        for (int child : list.get(cur)) {
            if (child != parent) {
                if (flag) {
                    ans += dp(child, cur, false);
                } else {
                    ans += Math.max(dp(child, cur, true) + arr[child], dp(child, cur, false));
                }
            }
        }
        return ans;
    }
}
```

## Code(Correct)

```java
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BOJ1949 {
    static int n;
    static int[] arr;
    static int[][] dp;
    static ArrayList<ArrayList<Integer>> list = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        n = Integer.parseInt(br.readLine());
        arr = new int[n + 1];
        dp = new int[n + 1][2];

        for (int i = 0; i <= n; i++) {
            list.add(new ArrayList<>());
        }

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            list.get(a).add(b);
            list.get(b).add(a);
        }

        dfs(1, -1);

        bw.write(String.valueOf(Math.max(dp[1][0], dp[1][1])));
        bw.close();
        br.close();
    }

    static void dfs(int cur, int parent) {
        for (int child : list.get(cur)) {
            if (child != parent) {
                dfs(child, cur);
                dp[cur][1] += dp[child][0];
                dp[cur][0] += Math.max(dp[child][0], dp[child][1]);
            }
        }
        dp[cur][1] += arr[cur];
    }
}

```

