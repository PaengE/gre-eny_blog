## Problem : https://programmers.co.kr/learn/courses/30/lessons/12911

## Approach

자바에선 10진수로 표현된 숫자를 2진수로 변환 했을때의 1비트의 개수를 세어주는 Integer.bitCount() 메소드가 있어 편하게 문제를 풀이할 수 있다.

주어진 숫자 n을 1씩 증가시킨 숫자의 1비트의 개수가 n의 1비트의 개수와 같으면 그 숫자를 반환한다.

## Code

```java
public class NextBigNumber {
    public static void main(String[] args) {
        int n = 15;
        NextBigNumber nbn = new NextBigNumber();
        System.out.println(nbn.solution(n));
    }

    public int solution(int n) {
        int count = Integer.bitCount(n);

        for (int i = n + 1; ; i++) {
            if (count == Integer.bitCount(i)) {
                return i;
            }
        }
    }
}
```

