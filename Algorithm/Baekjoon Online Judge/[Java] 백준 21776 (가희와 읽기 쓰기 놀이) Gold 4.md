## Problem : https://www.acmicpc.net/problem/21776



## Approach

> `중복된 요소로 만드는 순열` 알고리즘과 `문자열 처리`가 필요한 문제이다.
>
> 주어진 카드 개수로 순열을 만들되, 특정 카드 집합의 순서가 유지되어야 한다.



만약 1번 사람이 `3,2,1` 순으로 카드를 사용하고 2번 사람이 `4,5` 순으로 카드를 사용한다면

(1번, 1번, 1번, 2번, 2번)으로 만드는 수열과 같은 개수가 된다. (1번,2번,1번,2번,1번), (2번,1번,2번,1번,1번) 과 같은 것이 나올 수 있겠다.

순서가 정해졌으면 사용자가 카드를 사용한 순서대로 숫자를 끼워 넣기만 하면 된다. 위의 순서는 (3,4,2,5,1), (4,3,5,2,1) 이 될 것이다.

그냥 숫자로만 순열을 만들 수도 있겠지만 이 경우 최대 `5!`의 경우의 수가 나온다. 이 중에서 또 숫자의 순서가 유효한지를 검사해야 할 것이다.

일부 순서가 정해진 순열에서는 이러한 숫자의 순서가 유효한 지를 검사하는 과정은 생략해도 되며, `5! / (3! * 2!)`의 경우의 수로 줄어들게 된다.

(물론 최악 입력의 경우 똑같이 `5!`이다. 하지만 순서가 유효한지 검사를 안해도 된다.)



따라서 위에서 설명한 것을 기반으로 문제를 풀자면 다음과 같다.

- 카드를 사용한 사람을 기준으로, 순열을 구한다. (순열의 요소로 중복된 요소가 있을 수 있다. ex. 1,1,1,2,2)
- 구한 수열을 사용자가 사용한 카드 순서로 치환한다. (ex. 1,1,1,2,2 -> 3,2,1,4,5)
- 치환된 수열 순서대로 연산카드의 내용을 수행한다.
  - 연산의 최종 결과를 정답 TreeSet에 추가한다.
  - 수행 중 에러를 뱉으면 "ERROR" 을 정답 TreeSet에 추가한다.
  - 결과의 문자열이 빈 문자열이면 "EMPTY"를 정답 TreeSet에 추가한다.
- TreeSet의 요소를 하나씩 출력한다. (ASCII 코드 기준 사전순인 것 같다.)



## Code

```java
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class BOJ21776 {
    static int n, c;
    static ArrayList<Integer>[] order;
    static String[] cards;
    static int[] personalCards, res, commands;
    static TreeSet<String> ans = new TreeSet<>();   // 정답 문자열의 중복을 제거 + 아스키코드 기준 사전 순
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        order = new ArrayList[n + 1];   // i번째 사람의 카드 연산 순서
        cards = new String[c + 1];  // 카드에 적혀 있는 연산
        personalCards = new int[n + 1]; // i번째 사람이 가지고 있는 카드 개수
        res = new int[c + 1];   // 사용자 순열
        commands = new int[c];  // 사용자가 사용한 카드 순열
        Arrays.fill(res, -1);

        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            int cnt = Integer.parseInt(st.nextToken());
            personalCards[i] = cnt;

            order[i] = new ArrayList<>();
            for (int j = 0; j < cnt; j++) {
                order[i].add(Integer.parseInt(st.nextToken()));
            }
        }
        for (int i = 1; i <= c; i++) {
            cards[i] = br.readLine();   // i번째 카드
        }

        samePermutation(1,0);

        bw.write(printAnswer());
        bw.close();
        br.close();
        
    }

    static String printAnswer() {
        StringBuilder sb = new StringBuilder();
        for (String s : ans) {
            sb.append(s + "\n");
        }
        return sb.toString();
    }

    // 카드번호 순열로 문자열 만드는 메소드
    static void operation() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < c; i++) {
            int cur = commands[i];

            String[] ops = cards[cur].split(",");
            for (int j = 0; j < ops.length; j++) {
                StringTokenizer token = new StringTokenizer(ops[j]);
                if (token.nextToken().equals("ADD")) {  // 문자열에 문자 추가
                    sb.append(token.nextToken());
                } else {    // Delete 가능하면 삭제, 불가능하면 Exception
                    try {
                        sb.deleteCharAt(Integer.parseInt(token.nextToken()));
                    } catch (Exception e) {
                        ans.add("ERROR");
                        return;
                    }
                }
            }
        }

        // 문자열이 비어있으면 EMPTY
        if (sb.length() == 0) {
            ans.add("EMPTY");
        } else {
            ans.add(sb.toString());
        }
    }

    static void samePermutation(int personIdx, int cardCnt) {
        if (personIdx == n + 1) {
            int[] cardIdx = new int[n + 1];

            // 사람 순열로 카드번호 순열 만들기
            for (int i = 0; i < c; i++) {
                int person = res[i];
                commands[i] = order[person].get(cardIdx[person]++);
            }

            operation();
            return;
        }

        // 현재 사람의 카드를 다 썼다면 다음 사람으로 넘어감
        if (personalCards[personIdx] == 0) {
            samePermutation(personIdx + 1, 0);
        } else {    // 현재 사람의 카드로 순열 만들기
            personalCards[personIdx]--;
            for (int i = cardCnt; i < c; i++) {
                if (res[i] < 0) {
                    res[i] = personIdx;
                    samePermutation(personIdx, i + 1);
                    res[i] = -1;
                }
            }
            personalCards[personIdx]++;
        }
    }
}
```



## 문제 출제자의 풀이

- https://github.com/cdog-gh/gh_coding_test/tree/main/1