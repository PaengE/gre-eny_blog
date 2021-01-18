## Problem : https://programmers.co.kr/learn/courses/30/lessons/12985

## Approach

2017 팁스타운 문제이다.

(a + 1) / 2 와 (b + 1) / 2가 같을 때까지 앞의 과정을 반복하면서 count 를 늘려간다.

전체 사람 수 n명은 문제 풀이에 필요가 없다.

예를 들어 2번 3번으로 시작한 경우

- (2 + 1) / 2 = 1, (3 + 1) / 2 = 2 -> count++
- (1 + 1) / 2 = 1, (2 + 1) / 2 = 1
- 따라서 2번째만에 2번 3번은 승부를 겨루게 된다.

1번 8번으로 시작한 경우

- (1 + 1) / 2 = 1, (8 + 1) / 2 = 4 -> count++
- (1 + 1) / 2 = 1, (4 + 1) / 2 = 2 -> count++
- (1 + 1) / 2 = 1, (2 + 1) / 2 = 1
- 따라서 3번째만에 1번 8번은 승부를 겨루게 된다.

## Code

```java
public class ExpectedList {
    public static void main(String[] args) {
        ExpectedList el = new ExpectedList();
        int n = 8;
        int a = 4;
        int b = 7;

        System.out.println(el.solution(n, a, b));

    }

    public int solution(int n, int a, int b) {
        int count = 1;

        while ((a + 1) / 2 != (b + 1) / 2) {
            a = (a + 1) / 2;
            b = (b + 1) / 2;
            count++;
        }

        return count;
    }
}

```

