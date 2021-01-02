## Problem : https://programmers.co.kr/learn/courses/30/lessons/42626

## Approach

'이렇게 풀어도 되나?' 싶었지만 어쨌든 모든 테케를 다 통과했다.

- 우선순위 큐를 사용하여 큐에 요소가 2개 미만이라면 -1을 리턴한다.
- 큐에 요소가 2개 이상이라면, 가장 작은 두개 요소를 빼내 섞은 후, 다시 큐에 집어넣었다.
- 큐의 모든요소가 K 이상일 때까지 혹은 큐의 요소개수가 2개 미만이 될 때까지 반복했다.

## Code

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Hotter {
    public static void main(String[] args) {
        int[] s = {0,0,0,0};
        int k = 7;

        Hotter h = new Hotter();
        System.out.println(h.solution(s, k));
    }

    public int solution(int[] scoville, int K) {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(Arrays.stream(scoville).boxed().toArray(Integer[]::new)));
        PriorityQueue<Integer> pq = new PriorityQueue<>(list);

//        pq.forEach(item -> System.out.println(item));
        int answer = 0;

        while (pq.peek() < K) {
            // 우선순위 큐 앞 두개를 조합하여 다시 큐에 삽입
            if (pq.size() >= 2) {
                pq.offer(pq.poll() + pq.poll() * 2);
                answer += 1;
            }
            // 큐 안의 요소가 2개 미만이면 더이상 조합할 수 없으므로 -1
            else {
                answer = -1;
                break;
            }
        }
        return answer;
    }
}

```

