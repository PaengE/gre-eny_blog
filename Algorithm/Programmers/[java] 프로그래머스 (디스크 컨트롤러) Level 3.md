## Probelm : https://programmers.co.kr/learn/courses/30/lessons/42627

## Approach

> 이 문제는 간단히 비선점(Non-Preemption) CPU 스케줄링 기법 중 SJF(Shortest Job First)기법을 구현하는 문제이다.
>
> SJF에 대해서는 따로 설명하진 않겠지만, 간단하게 작업 큐 안에 있는 프로세스 중 가장 수행시간이 짧은 것을 먼저 수행하는 기법이다.
>
> 평균 대기시간의 감소 효과를 볼 수 있다.

일단 요청이 들어온 순서대로 정렬을 한다. (작업을 하지 않고 있을 경우 먼저들어온 순서대로 처리하기 때문에)

또, 작업의 처리시간(수행시간) 기준 오름차순이 되도록 PriorityQueue를 구성한다.

- 남은 작업의 시작시간이 완료된 작업의 종료시간이하 라면, 작업큐에 작업을 등록한다.
- 작업큐가 비어있다면, 가장 빨리들어온 요청을 작업큐에 넣은 후 처리 할 수 있게 완료된 작업의 종료시간을 가장 빨리 요청된 작업의 시작시간으로 갱신한다.
- 작업큐가 비어있지 않다면, 하나씩 빼서 `대기시간`과 `처리시간`을 계산하여 ans에 추가한다. (이 때, 수행시간이 짧은 순으로 뽑아진다.)

## Code

```java
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.PriorityQueue;

public class DiskController {
    public int solution(int[][] jobs) {
        // 요청시간 기준 오름차순
        Arrays.sort(jobs, (o1, o2) -> o1[0] - o2[0]);

        // 처리시간 기준 오름차순
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);

        int endTime = 0;    // 종료시간
        int jobsIdx = 0;    // 작업인덱스
        int countJobs = 0;  // 수행된 작업의 개수
        int ans = 0;

        while (countJobs < jobs.length) {
            // 시작시간이 종료시간보다 같거나 작으면 pq에 작업큐에 등록
            while (jobsIdx < jobs.length && jobs[jobsIdx][0] <= endTime) {
                pq.offer(jobs[jobsIdx++]);
            }

            // 작업큐가 비어있으면 종료시간을 다음 작업의 시작시간으로 지정(endTime 이후에 들어온 작업)
            if (pq.isEmpty()) {
                endTime = jobs[jobsIdx][0];
            }
            // 비어있지 않다면 하나씩 꺼내서 소요시간과 종료시간을 갱신
            else {
                int[] temp = pq.poll();
                ans += endTime + temp[1] - temp[0];
                endTime += temp[1];
                countJobs++;
            }
        }

        return ans / jobs.length;
    }

    @Test
    public void test() {
        Assertions.assertEquals(9, solution(new int[][]{{0, 3}, {1, 9}, {2, 6}}));
        Assertions.assertEquals(7, solution(new int[][]{{0, 3}, {1, 9}, {2, 6}, {30, 3}}));
        Assertions.assertEquals(3, solution(new int[][]{{0, 5}, {3, 2}, {7, 2}}));
        Assertions.assertEquals(6, solution(new int[][]{{0, 7}, {1, 1}, {5, 3}}));
        Assertions.assertEquals(550, solution(new int[][]{{100, 100}, {1000, 1000}}));
        Assertions.assertEquals(1, solution(new int[][]{{0, 1}}));
        Assertions.assertEquals(14, solution(new int[][]{{0, 10}, {2, 10}, {9, 10}, {15, 2}}));
        Assertions.assertEquals(25, solution(new int[][]{{0, 10}, {2, 12}, {9, 19}, {15, 17}}));
        Assertions.assertEquals(2, solution(new int[][]{{0, 1}, {0, 1}, {0, 1}}));
        Assertions.assertEquals(2, solution(new int[][]{{0, 1},{0, 1},{0, 1},{0, 1}}));
        Assertions.assertEquals(6, solution(new int[][]{{10, 10}, {30, 10}, {50, 2}, {51, 2}}));
        Assertions.assertEquals(550, solution(new int[][]{{100, 100}, {1000, 1000}}));
    }
}

```

