## Problem : https://www.acmicpc.net/problem/21774



## Approach

> 문자열을 파싱하여 `DP`를 구성하고, `이분 탐색`의 `lowerbound`, `upperbound`를 활용해야 하는 문제이다.



문제 풀이의 로직은 다음과 같다.

- 먼저 문자열을 파싱한다. 주어진 시각 문자열이 `YYYY-MM-DD hh:mm:ss` 형식이고 순서대로 주어지므로, `-:`와 `공백문자`를 없애면 자연스럽게 정렬이 된 상태가 된다.
  (본인은 Long 타입으로 형변환을 하였지만, String 타입이어도 자연스럽게 사전순으로 정렬 상태가 된다.)
- 그리고 DP배열을 구성한다. DP[i][j] 는 2000-01-01 00:00:00 부터 i번째 로그 시각까지 j레벨 이상인 로그들의 개수이다.
- 시각이 같은 로그가 있을 수 있으므로, 시작 시간의 lowerbound, 종료 시간의 upperbound를 구한 뒤, DP[upperbound - 1][level] - DP[lowerbound - 1][level] 을 하면 된다.

주의할 점은 배열을 로그의 개수보다 1 크게 만들어야 한다는 것이다. 

왜냐하면 lowerbound, upperbound를 만족하는 숫자가 없을 경우, 제일 마지막 인덱스를 반환하기 때문이다. 

이 경우, 마지막 인덱스가 lowerbound, upperbound인 경우와 lowerbound, upperbound를 만족하는 숫자가 없는 경우가 같은 경우로 취급되기 때문에 간단하게 배열의 크기를 하나 늘려주는 것으로 해결할 수 있다.



lowerbound, upperbound에 대하여 간단히 설명하자면 다음과 같다. 구현 방법은 밑의 코드를 보면 된다.

- `lowerbound`: 정렬된 자료구조에서 찾으려는 target `이상`인 첫번째 인덱스를 찾는다.
- `upperbound`: 정렬된 자료구조에서 찾으려는 target `초과`인 첫번째 인덱스를 찾는다.

DP 에서 계산할 때 각각 -1 을 해주는 이유가 이 때문이다.



## Code

```java
import java.io.*;
import java.util.StringTokenizer;

public class BOJ21774 {
    static long[] logs;
    static int[] lvs;
    static int[][] dp;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());
        logs = new long[n + 2];
        logs[n + 1] = Integer.MAX_VALUE;    // lowerbound와 upperbound가 없을 때를 위한 인덱스
        lvs = new int[n + 1];
        dp = new int[n + 1][7];

        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine(), "#");
            logs[i] = Long.parseLong(st.nextToken().replaceAll("[- :]", ""));
            lvs[i] = Integer.parseInt(st.nextToken());
            for (int j = lvs[i]; j >= 1; j--) {
                dp[i][j]++;
            }
        }

        // i번째 로그까지 j레벨 이상인 로그의 개수
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= 6; j++) {
                dp[i][j] += dp[i - 1][j];
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine(), "#");
            long queryS = Long.parseLong(st.nextToken().replaceAll("[- :]", ""));
            long queryE = Long.parseLong(st.nextToken().replaceAll("[- :]", ""));
            int queryLv = Integer.parseInt(st.nextToken());

            int lower = lowerBound(1, n + 1, queryS);
            int upper = upperBound(1, n + 1, queryE);
            int count = dp[upper - 1][queryLv] - dp[lower - 1][queryLv];
            sb.append(count + "\n");
        }
        bw.write(sb.toString());
        bw.close();
        br.close();
    }

    // target 이상인 첫번째 수
    static int lowerBound(int left, int right, long target) {
        while (left < right) {
            int mid = (left + right) / 2;

            if (target <= logs[mid]) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return right;
    }

    // target 초과인 첫번째 수
    static int upperBound(int left, int right, long target) {
        while (left < right) {
            int mid = (left + right) / 2;

            if (target >= logs[mid]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return right;
    }
}
```



## 문제 출제자의 풀이

- https://github.com/cdog-gh/gh_coding_test/tree/main/1