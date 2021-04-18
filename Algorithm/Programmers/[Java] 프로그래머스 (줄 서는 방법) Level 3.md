## Problem : https://programmers.co.kr/learn/courses/30/lessons/12936

## Approach

> 각 자리를 정하는 나름의 규칙을 찾고 그에 맞게 구현을 해야했던 문제이다.
>
> 참고로 순열을 이용한 풀이는 정확성 테스트는 통과되나, 효율성 테스트에서 통과되지 못한다.

문제에서 주어진 n = 3, k = 5인 케이스를 예로 들겠다. 3명이 줄을 설 수 있는 방법은 다음과 같이 6가지이다.

- [1, 2, 3]
- [1, 3, 2]
- [2, 1, 3]
- [2, 3, 1]
- [3, 1, 2]
- [3, 2, 1]

다음과 같은 과정을 거칠 것이다.

1. 사람배열은 0부터 시작하니 일단 k-- 를 하고 fn = n!를 구한 상태에서 시작한다.
2. 일단 fn을 fn(6) / n(3) = 2 로 갱신하고 k(4) / fn(2) = 2 를 하여 첫자리를 구한다.
3. 여기서 2는 사람배열의 2번째 인덱스를 말한다. 정답배열에 사람배열의 2번째를 넣고 사람배열에선 삭제한다.
4. 그런 후, k 를 k(4) % fn(2) = 0으로 갱신한다. 그리고 n-- 을 한다.
5. 위의 작업을 n이 0보다 클 때동안 반복한다.

일단 3의 과정이 끝나면 앞자리는 3이다. 그럼 [3, 1, 2] 와 [3, 2, 1]이 후보가 될 수 있는데, 다음 과정에서 k가 0이된다. 그렇다는 말은 [3, 1, 2] 와 [3, 2, 1] 중 0번째 방법을 골라야한다는 말이다.

다시 돌아와서, 현재 fn = 2, k = 0, n = 2이다. 위의 1, 2, 3, 4, 5과정을 한번 더 돌면

fn(2) / n(2) = 1, k(0) / fn(1) = 0, k = k(0) % fn(1) = 0, n-- : 1이 정답배열에 삽입된다.

마찬가지로 다음번엔 2가 정답배열에 삽입된다.

## Code

```java
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class MethodToLineUp {
    public int[] solution(int n, long k) {
        ArrayList<Integer> arr = new ArrayList<>();
        int[] ans = new int[n];
        long fn = 1;
        for (int i = 1; i <= n; i++) {
            arr.add(i);
            fn *= i;
        }
        k--;

        int idx = 0;
        while (n > 0) {
            fn /= n;
            ans[idx++] = arr.get((int) (k / fn));
            arr.remove((int) (k / fn));
            k %= fn;
            n--;
        }

        return ans;
    }

    // 순열을 이용한 풀이는 효율성 테스트에서 시간초과
//    public void makePermutation(ArrayList<Integer> arr, ArrayList<Integer> res, int n, int r, long idx) {
//        if (r == 0) {
//            cnt += 1;
//            if (idx == cnt) {
//                ans = res.stream().mapToInt(i -> i).toArray();
//            }
//            return;
//        }
//
//        for (int i = 0; i < n; i++) {
//            res.add(arr.remove(i));
//            makePermutation(arr, res, n - 1, r - 1, idx);
//            arr.add(i, res.remove(res.size() - 1));
//        }
//    }

    @Test
    public void test() {
        Assertions.assertArrayEquals(new int[]{3, 1, 2}, solution(3, 5));
    }
}

```

