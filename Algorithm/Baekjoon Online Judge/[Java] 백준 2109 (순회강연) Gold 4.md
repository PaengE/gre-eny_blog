## Problem : https://www.acmicpc.net/problem/2109

## Approach

> 우선순위 큐를 사용한 `그리디(Greedy) 알고리즘` 문제이다.

먼저 pay 기준 내림차순으로 정렬된 `우선순위 큐`가 필요하다. pay가 같을 경우에는 day가 더 작은 것이 우선이다.

그리고 주어진 입력 d의 최대 크기만큼의 cost 배열을 선언한다.

cost[i] 는 i일에 얼마짜리 강의를 하는지를 저장한다. cost 배열의 총 합이 이 문제의 답이 된다. 



## Code

```java
import java.io.*;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 *  No.2109: 순회 강연
 *  URL: https://www.acmicpc.net/problem/2109
 *  Hint: Greedy
 */

public class BOJ2109 {
    static PriorityQueue<Lecture> pq = new PriorityQueue<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());

        int maxDay = 0;
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int p = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            maxDay = Math.max(maxDay, d);
            pq.offer(new Lecture(d, p));
        }

        int[] cost = new int[maxDay + 1];
        while (!pq.isEmpty()) {
            Lecture lec = pq.poll();

            // 가능한 최대 일자부터 역순으로 강의를 할 수 있는지 체크한다.
            for (int i = lec.day; i > 0; i--) {
                if (cost[i] < lec.pay) {
                    cost[i] = lec.pay;
                    break;
                }
            }
        }

        bw.write(String.valueOf(Arrays.stream(cost).sum()));
        bw.close();
        br.close();
    }

    // pay 기준 내림차순으로 하되, pay가 같은 경우에는 day 기준 오름차순으로 정렬한다.
    static class Lecture implements Comparable<Lecture>{
        int day, pay;

        Lecture(int day, int pay) {
            this.day = day;
            this.pay = pay;
        }

        @Override
        public int compareTo(Lecture o) {
            if (this.pay == o.pay) {
                return this.day - o.day;

            }

            return o.pay - this.pay;
        }
    }
}
```

