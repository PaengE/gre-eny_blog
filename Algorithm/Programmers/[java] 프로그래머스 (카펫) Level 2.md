## Problem : https://programmers.co.kr/learn/courses/30/lessons/42842

## Approach

수학적 규칙을 찾는다면 편한 문제이다.

일단 전체 사각형 넓이는 (가로) x (세로) = brown + yellow 이다.

그리고 전체 사각형의 (가로 - 2) x (세로 - 2) = yellow 이다.

이 두 성질을 이용하여 전체 사각형의 넓이를 가질 수 있는 (가로, 세로)의 쌍 중에 (가로 - 2) x (세로 - 2) = yellow 인 쌍을 찾으면 되는 문제이다.

## Code

```java
public class Carpet {
    public static void main(String[] args) {
        int brown = 12;
        int yellow = 4;

        Carpet c = new Carpet();
        for (int a : c.solution(brown, yellow)) {
            System.out.print(a + " ");
        }
    }

    public int[] solution(int brown, int yellow) {
        int[] answer = new int[2];
        int size = brown + yellow;

        for (int i = 3; i <= size / 3; i++) {
            if (size % i == 0) {
                if ((i - 2) * ((size / i) - 2) == yellow) {
                    answer[0] = size / i;
                    answer[1] = i;
                    break;
                }
            }
        }
        return answer;
    }
}
```

