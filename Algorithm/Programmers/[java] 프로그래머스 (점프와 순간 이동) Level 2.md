## Problem : https://programmers.co.kr/learn/courses/30/lessons/12980

## Approach

Summer/Winter Coding(~2018) 문제이다.

처음에는 당연히 BFS/DFS 류의 탐색문제인줄 알고 그렇게 접근했다.

그런데 코드를 제출하니 정확성은 통과했지만, 효율성에서 모두 시간초과가 떠서 다른풀이가 생각나지 않아 고민 끝에 결국 다른 분들의 솔루션을 찾아보았다.

찾아보니 주어진 수를 이진수로 변환하였을 때, 1의 개수가 이 문제의 답이라는 것을 알았다.

> 1. 현재 위치에서 (현재위치+1) 칸으로 가는데 1 비용이 든다.
> 2. 현재 위치에서 (현재위치*2) 칸으로 가는데 0 비용이 든다.

위의 문제 조건이 이 풀이가 가능하게 만들었다.

10, 11, 12 를 예로 들겠다.

- 10을 이진수로 변환하면 1010이다. 처음 위치는 0이므로 1을 만드는데 1비용이 든다.(result = 1)
- 1에서 10으로 만드는데에는 0비용이 든다.(result = 1)
- 10에서 100으로 만드는데에는 0비용이 든다.(result = 1)
- 100에서 101로 만드는데에는 1비용이 든다.(result = 2)
- 101에서 1010으로 만드는데에는 0비용이 든다.(result = 2)
- 따라서 0위치에서 1010위치, 즉 0에서 10을 만드는데에 쓰이는 건전지 사용량은 2이다.

11과 12의 경우에도 똑같다. 11 -> 1011

- 1011(11) : 0 -> 1(+) -> 10 -> 100 -> 101(+) -> 1010 -> 1011(+)
- 1100(12) : 0 -> 1(+) -> 10 -> 11(+) -> 110 -> 1100

심지어 자바에서는 주어진 10진수를 2진수로 변환하였을 때의 1의 개수를 세어주는 Integer.bitCount() 메소드가 있어 한 줄로 풀이가 가능하다.

## Code

```java
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class JumpAndTeleport {
    public static void main(String[] args) {
        int n = 1000000000;
        JumpAndTeleport jt = new JumpAndTeleport();
        System.out.println(jt.solution(n));
//        System.out.println(jt.mySolution(n));

    }

    // 주어진 수를 이진수로 나타냈을 때의 1의 개수
    public int solution(int n) {
        return Integer.bitCount(n);
    }

    // 시간초과..
    public int mySolution(int n) {
        Queue<Integer> q = new LinkedList<>();
        int[] cost = new int[n + 1];
        Arrays.fill(cost, -1);

        q.offer(0);
        cost[0] = 0;
        while (!q.isEmpty()) {
            int cur = q.poll();
            if (cur == n) {
                return cost[cur];
            }

            if (cur * 2 <= n && cost[cur * 2] == -1) {
                q.offer(cur * 2);
                cost[cur * 2] = cost[cur];
            }
            if (cur + 1 <= n && cost[cur + 1] == -1) {
                q.offer(cur + 1);
                cost[cur + 1] = cost[cur] + 1;
            }
        }

        return cost[n];
    }
}

```

