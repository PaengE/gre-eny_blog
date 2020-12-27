## Problem : https://programmers.co.kr/learn/courses/30/lessons/42584

## Approach

아래는 문제의 입출력 예이다.

| prices          | return          |
| --------------- | --------------- |
| [1, 2, 3, 2, 3] | [4, 3, 1, 1, 0] |

큐를 이용한 간단한 문제이다.

Queue에 prices를 모두 넣은 후, 하나씩 빼내면서 Iterator를 이용한 순회를 한다.

큐 순회 중에 가격이 낮아진다면 바로 break를 걸고 다음으로 넘어간다.

## Code

```java
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class StockPrice {
    public static void main(String[] args) {
        int[] prices = {1, 2, 3, 2, 3};

        int[] answer = solution(prices);

        for (int i = 0; i < answer.length; i++) {
            System.out.print(answer[i] + " ");
        }
        System.out.println();
    }

    static int[] solution(int[] prices) {
        int size = prices.length;
        int[] answer = new int[size];

        Queue<Integer> q = new LinkedList<>();
        for (int num : prices) {
            q.offer(num);
        }

        for (int i = 0; i < size; i++) {
            int price = q.poll();
            Iterator it = q.iterator();

            int count = 0;
            while (it.hasNext()) {
                if (price <= (int) it.next()) {
                    count += 1;
                } else {
                    count += 1;
                    break;
                }
            }
            answer[i] = count;
        }
        return answer;
    }
}

```

