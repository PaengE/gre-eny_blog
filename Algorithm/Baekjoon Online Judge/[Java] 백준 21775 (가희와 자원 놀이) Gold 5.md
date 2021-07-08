## Problem : https://www.acmicpc.net/problem/21775



## Approach

> 간단한 `구현` 문제이다. (본인은 간단하지 않았다.)



먼저 사용할 자료구조들을 생각해 보았다. 구현 문제에선 어떤 자료구조를 사용할 것인가가 중요한 포인트같다.

- 턴을 수행하는 사람의 번호 -> sequence 배열
- 주어진 연산카드 -> 배열 or 큐
- 개인공간 -> 사람마다 공간 할당(메모리 초과) -> Map 사용 (key, value) == (자원카드 번호, 소지한 사람 번호)
- 공용공간 -> 불필요 (Map에 없으면 다 공용공간에 있는 것)
- 번호가 n인 사람이 현재 들고 있는 연산카드 -> people 배열



위처럼 어떤 자료구조를 사용할 지 결정했다면 구현은 쉬운 문제였다. 문제풀이 로직은 다음과 같다.

- 턴을 수행하는 사람의 순서대로 현재 들고 있는 카드가 있는지를 검사하고, 없으면 큐에서 연산카드를 새로 하나 뽑는다.
- 연산카드를 수행한다.
  - next의 경우, 들고 있던 연산카드를 그대로 버린다.
  - acquire n의 경우, map에서 key == n인 엔트리를 찾는다. 
    없다면, 자원카드 n이 공용공간에 있다는 뜻이 되므로, map 에 (n, 자신의 번호) 로 추가한다.
    있다면, 연산카드를 그대로 들고 있는다.
  - release n의 경우, map에서 key == n인 엔트리를 찾는다.
    없다면, 들고 있는 연산카드를 그대로 버린다.
    있다면, 해당 엔트리의 value를 살핀다. value값이 그 자원카드를 소지한 사람이기 때문에, value 값과 자신의 번호가 같다면 해당 엔트리를 map에서 삭제하고, 다르다면 그대로 연산카드를 버린다.
- 주어진 순서만큼 턴을 진행한다.



## Code

```java
import java.io.*;
import java.util.*;

public class BOJ21775 {
    static int n, t;
    static int[] sequence;
    static Queue<OperationCard> queue;
    static OperationCard[] people;
    static HashMap<Integer, Integer> personalSpace = new HashMap<>();   // (k, v) = (num, owner)
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        t = Integer.parseInt(st.nextToken());
        sequence = new int[t];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < t; i++) {
            sequence[i] = Integer.parseInt(st.nextToken());
        }

        queue = new ArrayDeque<>();
        for (int i = 0; i < t; i++) {
            st = new StringTokenizer(br.readLine());
            int idx = Integer.parseInt(st.nextToken());
            String command = st.nextToken();
            if (!command.equals("next")) {
                int num = Integer.parseInt(st.nextToken());
                queue.offer(new OperationCard(idx, command, num));
            } else {
                queue.offer(new OperationCard(idx, command));
            }
        }

        people = new OperationCard[n + 1];

        bw.write(gameStart());
        bw.close();
        br.close();
    }

    static String gameStart() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < t; i++) {
            int curPeople = sequence[i];

            if (people[curPeople] == null) {    // 해당 턴의 사람이 가지고 있는 연산카드가 없으면
                people[curPeople] = queue.poll();
                sb.append(people[curPeople].idx + "\n");
                if (operation(people[curPeople], curPeople)) {
                    people[curPeople] = null;
                }
            } else {    // 연산카드를 가지고 있으면(acquire 연산카드임)
                sb.append(people[curPeople].idx + "\n");
                if (!personalSpace.containsKey(people[curPeople].num)) {
                    personalSpace.put(people[curPeople].num, curPeople);
                    people[curPeople] = null;
                }
            }
        }

        return sb.toString();
    }

    // 리턴이 true 면 버림, fals 면 버리지 않음
    static boolean operation(OperationCard card, int personId) {
        if (card.command.equals("acquire")) {
            if (!personalSpace.containsKey(card.num)) { // 공용공간에 카드가 있으면
                personalSpace.put(card.num, personId);
                return true;
            } else {    // 공용공간에 카드가 없으면 카드를 버리지 않고 다음 턴으로
                return false;
            }
        } else if (card.command.equals("release")) {
            if (personalSpace.get(card.num) == personId) {  // personId 개인공간에 카드가 있으면
                personalSpace.remove(card.num); // 공용공간에 반환
            }
            return true;
        } else {    // 커맨드가 next일 경우
            return true;
        }
    }

    static class OperationCard{
        int idx, num;
        String command;

        OperationCard(int idx, String command, int num) {
            this.idx = idx;
            this.command = command;
            this.num = num;
        }

        OperationCard(int idx, String command) {
            this.idx = idx;
            this.command = command;
        }
    }
}
```



## 문제 출제자의 풀이

- https://github.com/cdog-gh/gh_coding_test/tree/main/1