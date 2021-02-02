## Problem : https://programmers.co.kr/learn/courses/30/lessons/42895

## Approach

DP문제로 분류되어있긴 하지만 문제를 풀이하면서 딱히 DP문제인 것 같진 않았다.

만들려는 숫자가 32000이하이므로 일단 n으로 n, nn, nnn, nnnn, nnnnn 인 숫자를 만든다. (당연하게 각각 n이 사용된 횟수가 1, 2, 3, 4, 5 임을 기억한다.)

그런 후, 사칙연산 +, -, *, / 에 대해서 DFS를 수행한다.(n이 사용된 횟수와 계산값을 넘겨준다.)

n이 사용된 횟수가 8이 넘으면 -1을 반환하고, 8이 넘지 않으면서 계산값이 number와 같다면 result 최소가 되게 계속 갱신한다.

## Code

```java
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExpressByN {
    static int[] nth = new int[8];
    static int result;
    public int solution(int n, int number) {
        result = -1;
        String s = "";
        for (int i = 0; i < 8; i++) {
            s += n;
            nth[i] = Integer.parseInt(s);
        }

        dfs(0, 0, number);
        return result;
    }

    public void dfs(int count, int num, int number) {
        if (count > 8) {
            return;
        }

        if (num == number) {
            if (count < result || result == -1) {
                result = count;
            }
            return;
        }

        for (int i = 0; i < 8; i++) {
            int nn = nth[i];
            int cntN = i + 1;

            dfs(count + cntN, num + nn, number);
            dfs(count + cntN, num - nn, number);
            dfs(count + cntN, num * nn, number);
            dfs(count + cntN, num / nn, number);
        }
    }

    @Test
    public void test() {
        Assertions.assertEquals(4, solution(5, 12));
        Assertions.assertEquals(3, solution(2, 11));
        Assertions.assertEquals(-1, solution(5, 31168));
    }
}

```

