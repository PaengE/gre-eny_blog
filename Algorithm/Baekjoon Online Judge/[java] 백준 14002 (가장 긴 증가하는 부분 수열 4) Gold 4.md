## 문제 원문 링크 : https://www.acmicpc.net/problem/14002

## Approach

이 문제는 [백준 11053번 가장 긴 증가하는 수열](https://www.acmicpc.net/problem/11053) 의 문제에서 가장 긴 부분 수열을 추가로 출력하는 문제이다.

아래 링크는 11053번에 대한 나의 풀이이다.

[2020/12/19 - \[Algorithm/Baekjoon Online Judge\] - \[java\] 백준 11053 (가장 긴 증가하는 부분 수열) Silver 2](https://gre-eny.tistory.com/21)

문제의 조건은 변함없이 입력 데이터의 개수가 1000 이하 이므로 Backtracking을 이용한 DP를 사용하여도 된다.

갱신을 할 때, 자신을 제외한 최장 부분 수열의 제일 오른쪽 값을 p[자신]에 넣은 후, 모든 루프가 종료 된 후, 최댓값인 DP배열의 인덱스부터 역추적을 한다.

나는 ArrayList를 사용하여 indexOf()를 사용하였지만 굳이 그러지 않아도 되며, PriorityQueue를 사용하였지만, 단순히 Stack으로도 구현가능하다.

## Code

```java
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * No.14002: 가장 긴 증가하는 부분 수열4
 * Description: O(N^2) LIS 를 출력하는 문제
 * URL: https://www.acmicpc.net/problem/14002
 * Hint: 최장길이배열 dp 와 바로 전의 위치를 기록
 */

public class BOJ14002 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int[] num = new int[n];

        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < n; i++) {
            num[i] = Integer.parseInt(st.nextToken());
        }

        dp(n, num);

        br.close();
    }

    static void dp(int n, int[] arr) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();

        Integer[] dp = new Integer[n];
        int[] p = new int[n];

        Arrays.fill(dp, 1);
        Arrays.fill(p, -1);

        int maxLength = 1;
        for (int i = 1; i < n; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (arr[i] > arr[j]) {
                    if (dp[i] < dp[j] + 1) {
                        dp[i] = dp[j] + 1;
                        p[i] = j;
                    } else {
                        dp[i] = dp[i];
                    }
                    maxLength = Math.max(dp[i], maxLength);
                }
            }
        }

        sb.append(maxLength + "\n");

      // 굳이 배열을 리스트로 변환하여 구할 필요는 없다.
      // 핵심은 LIS가 최대길이인 인덱스를 찾아 거기서부터 역순으로 올라간다는 접근법이다.
        ArrayList<Integer> list = new ArrayList<Integer>(Arrays.asList(dp));
        int maxLengthIndex = list.indexOf(maxLength);

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        while (maxLengthIndex != -1) {
            pq.offer(arr[maxLengthIndex]);
            maxLengthIndex = p[maxLengthIndex];
        }

        while (!pq.isEmpty()) {
            sb.append(pq.poll() + " ");
        }

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }
}

```

