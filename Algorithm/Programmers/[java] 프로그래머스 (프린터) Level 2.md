## 문제 원문 링크 : https://programmers.co.kr/learn/courses/30/lessons/42587

## Approach

> Baekjoon Online Judge 사이트에 비슷한 문제([프린터 큐 Silver 3](https://www.acmicpc.net/problem/1966))가 있으니 같이 풀어보면 좋을 것이다.

문제의 조건은 이렇다.

```html
1. 인쇄 대기목록의 가장 앞에 있는 문서(J)를 대기목록에서 꺼냅니다.
2. 나머지 인쇄 대기목록에서 J보다 중요도가 높은 문서가 한 개라도 존재하면 J를 대기목록의 가장 마지막에 넣습니다.
3. 그렇지 않으면 J를 인쇄합니다.
```

문제의 조건을 봤을 때, Queue를 이용하여 풀면 간단하게 풀 수 있다.

같은 우선순위를 가질 수 있으므로 문서의 Id와 우선순위를 묶어 관리해야 한다. 여기서는 Docs class를 선언하여 사용하였다.

Iterator를 사용하여 우선순위가 더 높은 것이 있으면 큐에 추가한다.(큐에서는 FIFO를 사용하므로 가능하다.)

아니라면 그대로 빼낸다. 그리고 count를 하나 늘린다. 이는 큐가 비어있을 때까지 반복한다.

## Code

```java
import java.util.*;

public class Printer {
    public static void main(String[] args) {
        int[] priorities = {1,1,9,1,1,1};
        int location = 0;

        System.out.println(solution(priorities, location));
    }

    static int solution(int[] priorities, int location) {
        Queue<Docs> q = new LinkedList<>();
        for (int i = 0; i < priorities.length; i++) {
            q.offer(new Docs(i, priorities[i]));
        }

        int count = 1;
        while (!q.isEmpty()) {
            Docs front = q.poll();
            boolean check = true;

            Iterator<Docs> iter = q.iterator();
            while (iter.hasNext()) {
                if (front.priority < iter.next().priority) {
                    check = false;
                    break;
                }
            }
            if (!check) {
                q.offer(front);
            } else {
                if (front.docsNum == location) {
                    break;
                } else {
                    count += 1;
                }
            }
        }
        return count;
    }
    static class Docs{
        int docsNum;
        int priority;
        public Docs(int docsNum, int priority){
            this.docsNum = docsNum;
            this.priority = priority;
        }
    }
}

```

