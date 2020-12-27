## Problem : https://www.acmicpc.net/problem/1655

## Approach

최대 힙과 최소 힙 두개를 사용하여 문제를 풀 수 있다. 

최대 힙은 최댓값이 우선으로 빠져나오는 큐이고, 최소 힙은 최솟값이 우선으로 빠져나오는 큐라고 할 때,

최대 힙과 최소 힙에 하나씩 숫자를 넣는다고 가정햇을 때, 최대 힙의 최댓값이 최소 힙의 최솟값보다 항상 작음을 유지한다면, 최대 힙의 최댓값이 항상 중간값을 가지게 된다.

> 그러기 위해선 다음 단계를 수행하여야 한다.
>
> ​	1. 최대 힙, 최소 힙에 하나씩 숫자를 넣는다.
>
> ​	2. 힙에 수를 넣을 때마다 최대 힙의 최댓값과 최소 힙의 최솟값을 비교하여 (항상 최대 힙의 최댓값) >= (최소 힙의 최솟값)을 유지 하도록 한다. (swap을 하면 된다. swap을 함으로써, 항상 최대 힙의 모든 요소는 최소 힙의 모든 요소보다 작음을 유지시킨다.)
>
> ​	3. 최대 힙의 최댓값이 전체 숫자의 중간값이 된다.(전체 수 개수가 홀수든, 짝수든 상관없다.)

최대 힙을 왼쪽에, 최소 힙을 오른쪽에 오름차순으로 일직선상에 놓아 그림을 그려 본다면 이해가 쉬울 것이다.

밑의 표는 입력데이터 (5, 2, 1, 10, 7, -99, 5) 에 대한 최대 힙과 최소 힙의 경과 표다.

|          | 최대 힙        | 최소 힙      |
| -------- | -------------- | ------------ |
| 5 삽입   | [5]            | []           |
| 2 삽입   | [5]            | [2]          |
| swap     | [2]            | [5]          |
| 1 삽입   | [1, 2]         | [5]          |
| 10 삽입  | [1, 2]         | [5, 10]      |
| 7 삽입   | [1, 2, 7]      | [5, 10]      |
| swap     | [1, 2, 5]      | [7, 10]      |
| -99 삽입 | [1, 2, 5]      | [-99, 7, 10] |
| swap     | [-99, 1, 2]    | [5, 7, 10]   |
| 5 삽입   | [-99, 1, 2, 5] | [5, 7, 10]   |

## Code

```java
import java.io.*;
import java.util.*;

/**
 * no.1655 : 가운데를 말해요
 * title : 우선순위 큐를 응용하여 중앙값을 빠르게 찾는 문제
 * hint : PriorityQueue 를 두개 사용
 */

public class BOJ1655 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());

        // MaxHeap, MinHeap 두 개의 우선순위 큐를 생성
        PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>();
        PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(Comparator.reverseOrder());

        int val = 0;
        for (int i = 0; i < n; i++) {
            val = Integer.parseInt(br.readLine());

            // maxHeap 의 크기가 minHeap 의 크기보다 작거나 같으면 maxHeap 에 삽입
            if (maxHeap.size() <= minHeap.size()) {
                maxHeap.offer(val);
            } else {    // 그렇지 않으면 minHeap 에 삽입
                minHeap.offer(val);
            }

            // maxHeap 의 가장 큰 숫자가 minHeap 의 가장 작은 숫자 보다 크면 swap
            if (!minHeap.isEmpty() && maxHeap.peek() > minHeap.peek()) {
                maxHeap.offer(minHeap.poll());
                minHeap.offer(maxHeap.poll());
            }

            bw.write(maxHeap.peek() + "\n");
        }
        bw.flush();
        bw.close();
        br.close();
    }
}
```

