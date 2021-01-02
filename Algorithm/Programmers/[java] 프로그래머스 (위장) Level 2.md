## Problem : https://programmers.co.kr/learn/courses/30/lessons/42578

## Approach

조합의 수학적 규칙을 알고 있다면 쉽게 풀 수 있는 문제이다.

- N개의 옷이 있을 때, 옷을 고를 수 있는 경우의 수(입지 않는 경우도 포함)는 N+1가지다.

- 그래서 M종류의 옷이 각각 N1, N2, ... , Nm개씩 있을 때, 옷을 고를 수 있는 경우의 수(입지 않는 경우도 포함)는  (N1 + 1) * (N2 + 1) * ... * (Nm + 1) - 1(옷을 하나도 안입는 경우) 이다.

위 규칙만 안다면 각각 옷의 개수가 몇개인지만 판별하면 된다.

필자는 HashMap을 사용하여 옷의 개수를 카운팅하기가 편했다.

코드에서 사용된 putIfAbsent(key, value) 는 HashMap에 key가 없다면 (key: value)을 put한 후 null을 반환하고, key가 이미 있다면 그 key에 저장되어 있는 value 값을 반환해준다.

## Code

```java
import java.util.HashMap;

public class Concealment {
    public static void main(String[] args) {
        String[][] clothes = {{"yellow_hat", "headgear"}
                , {"blue_sunglasses", "headgear"}
                , {"green_turban", "ey"}};

        Concealment c = new Concealment();
        System.out.println(c.solution(clothes));
    }

    // (모든 종류별 옷의 개수 + 1)을 곱한 값에 -1을 한 것이 답임.
    // n개 옷 중 하나를 고를 경우의 수: n
    // n개 옷 중 아무것도 안입는 경우의 수: 1
    // 모든 종류의 옷을 하나도 입지 않는 경우의 수: 1
    public int solution(String[][] clothes) {
        var map = new HashMap<String, Integer>();
        int[] cloth = new int[30];
        Integer idx = 0;

        for (String[] s : clothes) {
            Integer index = map.putIfAbsent(s[1], idx);
            if (index == null) {
                cloth[idx] = 1;
                idx += 1;
            } else {
                cloth[index] += 1;
            }
        }

        int answer = 1;
        for (int i = 0; i < idx; i++) {
            answer *= (cloth[i] + 1);
        }
        return answer - 1;
    }
}

```

