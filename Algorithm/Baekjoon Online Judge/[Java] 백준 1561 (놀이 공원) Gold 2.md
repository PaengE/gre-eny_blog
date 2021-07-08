## Problem : https://www.acmicpc.net/problem/1561



## Approach

> `이분탐색`을 이용하여 풀이가 가능한 문제이다.



먼저 n이 m이하일 경우, 0초에 모든 사람들이 놀이기구에 탑승할 수 있으므로 n번째 놀이기구가 마지막 사람이 타는 놀이기구가 될 것이다.



위 경우가 아니라면, 문제를 푸는 주요 로직은 다음과 같다.

- 먼저 `n -= m` 을 수행한다. (0초에 m명이 놀이기구를 탈 수 있기 때문이다.)

- 아래 과정처럼 이분탐색을 반복하여 `최소시간`을 구한다. 
  - mid 시간으로 탈 수 없다면, 시간을 늘린다. (left = mid + 1)
  - 탈 수 있다면, mid값을 ansTime에 저장하고 시간을 줄인다. (right = mid - 1)
  - 탈 수 있는지를 판단하는 것은 mid를 arr의 각 요소로 나눈 몫의 총합을 n과 비교하면 된다.
- `최소시간`을 구했다면, 마지막 사람이 타는 놀이기구의 번호를 찾는다.
  - `최소시간 - 1`까지 놀이기구를 타지 못한 사람의 수를 찾는다.
  - `최소시간`에 비어있는 놀이기구를 앞에서부터 채운다. (남은 사람이 0명이 될 때까지)
- 놀이기구에 사람을 태우고, 남은 사람이 0명이 되었을 때, 그 놀이기구의 번호가 정답이 된다.



## Code

```java
import java.io.*;
import java.util.StringTokenizer;

/**
 *  No.1561: 놀이 공원
 *  Hint: 이분탐색
 */

public class BOJ1561 {
    static long n, ansTime = Long.MAX_VALUE;
    static int m;
    static int[] arr;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Long.parseLong(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        arr = new int[m];
        int maxMinute = 0;
        for (int i = 0; i < m; i++) {
            int execTime = Integer.parseInt(st.nextToken());
            arr[i] = execTime;
            maxMinute = Math.max(maxMinute, execTime);
        }

        // n이 m보다 작으면 0초에 모든 사람이 놀이기구에 탈 수 있음
        if (n <= m) {
            bw.write(String.valueOf(n));
        } else {
            n -= m; // 0초에 타는 사람들을 제외한 나머지 사람 수
            binarySearch(1, n * maxMinute);
            bw.write(String.valueOf(lastNumber(ansTime)));
        }

        bw.close();
        br.close();
    }

    // 모든 사람이 놀이기구를 타는 최소시간을 구하는 이분탐색
    static void binarySearch(long left, long right) {
        while (left <= right) {
            long mid = (left + right) / 2;

            if (verifyingTime(mid)) {
                right = mid - 1;
                ansTime = Math.min(ansTime, mid);
            } else {
                left = mid + 1;
            }
        }
    }

    // time 안에 모든 사람이 놀이기구를 탈 수 있는지를 판단
    static boolean verifyingTime(long time) {
        long cnt = 0;
        for (int i = 0; i < m; i++) {
            cnt += (time / arr[i]);
        }

        return cnt >= n;
    }

    // 주어진 time에 마지막 사람이 타는 놀이기구의 번호를 찾음
    static int lastNumber(long time) {
        long num = n;
        // time - 1까지 놀이기구를 타지 않은 사람 수를 계산
        for (int i = 0; i < m; i++) {
            num -= ((time - 1) / arr[i]);
        }

        // 번호가 작은 빈 놀이기구부터 사람을 한명씩 태움
        for (int i = 0; i < m; i++) {
            if (time % arr[i] == 0) {
                num--;
            }

            if (num == 0) {
                return (i + 1);
            }
        }
        return -1;
    }
}
```

