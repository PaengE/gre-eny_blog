## Problem : https://www.acmicpc.net/problem/21773



## Approach

> `우선순위 큐(PriorityQueue)` 를 이용한 문제이다.

문제의 로직은 다음과 같다.

- 레디 큐에서 가장 우선순위가 높은 프로세스를 꺼내 1초동안 실행시킨다.
- 꺼낸 프로세스를 제외한 레디 큐에 있는 모든 프로세스의 우선순위는 1증가한다.



위 로직을 구현하려면, 먼저 우선순위 큐의 정렬 기준을 확립해야 한다. 프로세스의 우선순위 기준 내림차순이 필요할 것이다.

이제 우선순위큐에서 프로세스를 하나 뽑는다. 그리고 그 프로세스의 남은 실행시간을 1 낮춘다.

문제의 로직 중 2번째 부분은 뽑은 프로세스의 우선순위를 1 낮춤으로써 조건을 만족시킬 수 있다. 이렇게 하면 우선순위는 상대적이므로, 결과적으로 레디 큐에 있는 모든 프로세스들의 우선순위를 1 증가시킨 것과 같은 효과를 볼 수 있다. 
(우선순위 큐에 있는 모든 프로세스들의 우선순위를 1 늘리는 것은 시간복잡도 면으로 효율적이지 않다.)

마지막으로 뽑은 프로세스를 다시 레디 큐에 집어넣는다. (남은 실행시간이 존재할 경우)



## Code

```java
import java.io.*;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ21773 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int t = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        PriorityQueue<Job> pq = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int idx = Integer.parseInt(st.nextToken());
            int time = Integer.parseInt(st.nextToken());
            int prior = Integer.parseInt(st.nextToken());
            pq.offer(new Job(idx, time, prior));
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < t; i++) {
            if (pq.isEmpty()) {
                break;
            }

            Job cur = pq.poll();
            sb.append(cur.idx + "\n");

            if (cur.execTime == 1) {
                continue;
            }

            pq.offer(new Job(cur.idx, cur.execTime - 1, cur.priority - 1));
        }

        bw.write(sb.toString());
        br.close();
        bw.close();
    }

    static class Job implements Comparable<Job>{
        int idx, execTime, priority;

        Job(int idx, int execTime, int priority) {
            this.idx = idx;
            this.execTime = execTime;
            this.priority = priority;
        }

        @Override
        public int compareTo(Job o) {
            if (this.priority == o.priority) {
                return this.idx - o.idx;
            }
            return o.priority - this.priority;
        }
    }
}
```



## 문제 출제자의 풀이

- https://github.com/cdog-gh/gh_coding_test/tree/main/1