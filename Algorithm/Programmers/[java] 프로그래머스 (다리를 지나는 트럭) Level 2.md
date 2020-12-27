## Problem : https://programmers.co.kr/learn/courses/30/lessons/42583

## Approach

다음은 길이가 2이고, 10kg 무게를 견디는 다리, 무게가 [7, 4, 5, 6]kg인 트럭일 때의 과정이다.

| 경과 시간 | 다리를 지난 트럭 | 다리를 건너는 트럭 | 대기 트럭 |
| --------- | ---------------- | ------------------ | --------- |
| 0         | []               | []                 | [7,4,5,6] |
| 1~2       | []               | [7]                | [4,5,6]   |
| 3         | [7]              | [4]                | [5,6]     |
| 4         | [7]              | [4,5]              | [6]       |
| 5         | [7,4]            | [5]                | [6]       |
| 6~7       | [7,4,5]          | [6]                | []        |
| 8         | [7,4,5,6]        | []                 | []        |

트럭을 다리에 올리기 전에 (다리에 올라가 있는 트럭 + 현재 올릴 트럭)의 무게가 다리가 감당할 수 있는지를 검사해야한다. 큐의 크기는 다리의 길이이다.

>   1 . 만약 감당 가능하다면 큐에 트럭의 무게를 넣고, 감당 불가능 하다면 큐에 0을 넣는다.
>
>   2 . (큐가 꽉차면 맨 앞 요소를 빼는데 그 때까지 0을 넣는다.)
>
>   3 . 무엇을 넣든 간에 시간을 1 증가시킨다. 
>
>   4 . 모든 트럭을 큐에 넣을 때까지 반복한다.
>
>   5 . 모든 트럭을 다 넣었을 때, 답은 지금까지의 시간 + 다리의 길이이다.
>
> ​	(트럭이 큐에 들어가 큐에서 빠져나오면 다리가 건너간 것이다. 그러나 마지막 트럭은 큐에 집어넣자마자 루프를 종료하게 했으므로 다리의 길이를 더해줘야한다.)

0을 넣는 이유는 일종의 시간을 계산하기 위함이다. 밑의 표는 내 풀이의 큐 진행 상태이다.

| 경과시간 |   큐   |
| :------: | :----: |
|    0     |   []   |
|    1     |  [7]   |
|    2     | [7, 0] |
|    3     | [0, 4] |
|    4     | [4, 5] |
|    5     | [5, 0] |
|    6     | [0, 6] |

## Code

```java
import java.util.LinkedList;
import java.util.Queue;

public class TruckPassingByBridge {
    public static void main(String[] args) {
        int bridge_length = 2;
        int weight = 10;
        int[] truck_weights = {7, 4, 5, 6};

        System.out.println(solution(bridge_length, weight, truck_weights));
    }

    static int solution(int bridge_length, int weight, int[] truck_weights) {
        Queue<Integer> q = new LinkedList<>();
        int answer = 0;
        int total_weight = 0;

        for (int cur_weight : truck_weights) {
            while (true) {
                if (q.isEmpty()) {
                    q.offer(cur_weight);
                    total_weight += cur_weight;
                    answer += 1;
                    break;
                } 
                // 큐가 꽉 찼으면 맨 앞 요소를 뺀다.
                else if (q.size() == bridge_length) {
                    total_weight -= q.poll();
                } else {
                  	// 다리가 감당할 수 없으면
                    if (total_weight + cur_weight > weight) {
                        answer += 1;
                        // 0을 넣는 이유는 시간 계산을 위해서임.
                        q.offer(0);
                    } 
                    // 다리가 감당할 수 있으면
                    else {
                        q.offer(cur_weight);
                        total_weight += cur_weight;
                        answer += 1;
                        break;
                    }
                }
            }
        }

        // 마지막 트럭은 큐에 넣은 후 바로 종료되기 때문에 + bridge_length 를 해줌
        return (answer + bridge_length);
    }
}

```

