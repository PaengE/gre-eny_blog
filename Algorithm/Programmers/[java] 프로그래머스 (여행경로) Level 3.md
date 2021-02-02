## Problem : https://programmers.co.kr/learn/courses/30/lessons/43164

## Approach

> DFS 를 활용하여 풀이가 가능한 문제이다.
>
> 이름의 알파벳 순서가 앞서는 경우부터 방문해야 하므로 `방문공항`기준 오름차순으로 정렬하고 시작한다.

출발지는 항상 ICN 이므로 ICN을 출발지로 하여 DFS를 수행한다. 티켓을 모두 사용해야 하므로, `시작공항을 제외한 방문공항은 티켓의 개수와 같다.`를 이용하여, 티켓을 처음 다 썼을 때의 경로를 ans에 담는다. 그런 후, 티켓을 다 쓴 다른 경로가 왔을 때는 무시한다.
(왜냐하면 처음 방문공항 기준 오름차순으로 정렬된 상태에서 DFS를 수행하였기에 처음 발생하는 경로가 알파벳 순서가 앞서는 경로이기 때문이다.)

## Code

```java
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class TravelRoute {
    static String ans;
    static boolean[] visited;
    public String[] solution(String[][] tickets) {
        visited = new boolean[tickets.length];
        ans = "";
        Arrays.sort(tickets, (o1, o2) -> o1[1].compareTo(o2[1]));

        dfs("ICN", 0, "ICN ", tickets);
        return ans.split(" ");
    }

    private void dfs(String cur, int count, String res, String[][] tickets) {
        if (count == tickets.length) {
            if (!ans.equals("")) {
                return;
            }
            ans = res.substring(0, res.length() - 1);
            return;
        }

        for (int i = 0; i < tickets.length; i++) {
            if (cur.equals(tickets[i][0]) && !visited[i]) {
                visited[i] = true;
                dfs(tickets[i][1], count + 1, res + tickets[i][1] + " ", tickets);
                visited[i] = false;
            }
        }
    }

    @Test
    public void test() {
        Assertions.assertArrayEquals(new String[]{"ICN", "A", "D", "B", "A", "C"}
                , solution(new String[][]{{"ICN", "A"}, {"A", "C"}, {"A", "D"}, {"D", "B"}, {"B", "A"}}));
        Assertions.assertArrayEquals(new String[]{"ICN", "JFK", "HND", "IAD"}
                , solution(new String[][]{{"ICN", "JFK"}, {"HND", "IAD"}, {"JFK", "HND"}}));
        Assertions.assertArrayEquals(new String[]{"ICN", "ATL", "ICN", "SFO", "ATL", "SFO"}
                , solution(new String[][]{{"ICN", "SFO"}, {"ICN", "ATL"}, {"SFO", "ATL"}, {"ATL", "ICN"}, {"ATL", "SFO"}}));

    }
}

```

