## Problem : https://programmers.co.kr/learn/courses/30/lessons/68645

## Approach

월간 코드 챌린지 시즌1 에 수록된 문제이다.

정삼각형의 위쪽 꼭짓점부터 반시계방향으로 외곽부터 순회하면서(토네이도 식으로) 번호를 매기고, 그것을 1차원 배열로 변환하여 리턴하는 문제이다.

정삼각형의 한 변의 길이가 n이고, 번호를 1부터 매긴다고 하면, 마지막 번호는 n + (n - 1) + (n - 2) + ... + 1 = n(n - 1)/2 이다. 

1 부터 n 까지의 합은 다음 코드로 간단하게 작성할 수 있다.

```java
// 1~n 합(삼각형 외곽부터 n...1번까지 순회하므로)
int max = IntStream.rangeClosed(1, n).sum();
```

한바퀴 돌리는 것을 1 cycle이라고 할 때, 1 cycle을 돌리고서도 max만큼 번호를 매기지 않았다면 cycle을 이어나간다.

배열의 요소가 0이고(번호를 매기지 않았고) 범위를 벗어나지 않는다면 번호를 계속 매겨나간다.

## Code

```java
import java.util.stream.IntStream;

public class TriangleSnail {
    public static void main(String[] args) {
        int n = 4;

        int[] a = solution(n);

        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
    }

    static int[] solution(int n) {
        int[][] tri = new int[n][n];
        int i = 0;
        int j = 0;
        int idx = 1;
        tri[0][0] = 1;

        // 1~n 합(삼각형 외곽부터 n...1번까지 순회하므로)
        int max = IntStream.rangeClosed(1, n).sum();

        while (idx < max) {
            while (i + 1 < n && idx < max && tri[i + 1][j] == 0) {
                tri[++i][j] = ++idx;
            }

            while (j + 1 < n && idx < max && tri[i][j + 1] == 0) {
                tri[i][++j] = ++idx;
            }

            while (i - 1 >= 0 && j - 1 >= 0 && idx < max && tri[i - 1][j - 1] == 0) {
                tri[--i][--j] = ++idx;
            }
        }

        idx = 0;
        int[] answer = new int[max];
        for (i = 0; i < n; i++) {
            for (j = 0; j <= i; j++) {
                answer[idx++] = tri[i][j];
            }
        }

        return answer;
    }
}

```

