## Problem : https://programmers.co.kr/learn/courses/30/lessons/12952

## Approach

> 대표적인 Backtracking 문제이다. 

체스판의 위부터 lv 0이라고 할 때, arr[lv] 는 lv에 몇번째 열에 퀸이 놓여져 있는지를 나타낸다.

퀸을 서로 공격하지 않게 놓으려면 다음 세가지 조건을 검사하여야 한다.

1. 한 행에는 하나의 퀸만 놓을 수 있다.
2. 한 열에는 하나의 퀸만 놓을 수 있다.
3. 한 대각선에는 하나의 퀸만 놓을 수 있다.

0부터 n까지의 수를 넣으면서 위의 조건들을 체크하고, 조건에 벗어난다면 Backtracking 하여 다음 수를 진행한다.



조건은 다음과 같이 구현하면 된다.

1번의 경우에는 1차원 배열을 사용함으로써 검사하지 않아도 된다.

2번의 경우에는 arr[lv] 과 0 <= i < lv 인 arr[i]들이 서로 같지 않다면 같은 열에 존재하지 않는 것이다.

3번의 경우 abs(lv - i)가 세로의 길이고, abs(arr[lv] - arr[i])가 가로의 길이인데, 만약 둘이 서로 같다면 같은 대각선에 위치하고 있는 것이다.

## Code

```java
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class N_Queens {
    static int n, count = 0;
    static int[] arr;
    public int solution(int n) {
        this.n = n;
        arr = new int[n];

        backtracking(0);

        return count;
    }

    static void backtracking(int lv){
        if (lv == n) {
            count++;
        } else {
            for (int i = 0; i < n; i++) {
                arr[lv] = i;
                if (isPossible(lv)) {
                    backtracking(lv + 1);
                }
            }
        }
    }

    static boolean isPossible(int lv) {
        for (int i = 0; i < lv; i++) {
            if (arr[i] == arr[lv] || Math.abs(arr[i] - arr[lv]) == Math.abs(lv - i)) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void test() {
        Assertions.assertEquals(2, solution(4));
    }
}

```

