## Problem : https://programmers.co.kr/learn/courses/30/lessons/42746

## Approach

처음엔 사전정렬을 생각했었다. 그런데 사전검색으로는 반례가 생긴다.

사전 순 정렬이면 공통된 부분이 같으면 짧은 것이 먼저다. 예를들어, 333과 33같은 경우 33이 333보다 우선이다.

그러나 문제의 요구대로라면 33, 332는 33+332가 되어야하고, 33, 334는 334+33가 되어야 하므로 사전정렬은 불가능하다.

이 문제의 접근법은 질문 검색에 있는 다른 분들의 질문들을 보고 참고하였다.

두 숫자 n1과 n2를 n1+n2, n2+n1로 이어붙인다.(여기서 + 는 덧셈이 아닌 이어붙인다는 뜻이다.)

> 위의 (33, 332) 와 (33, 334)로 설명하자면,
>
> 33+332 = 33332, 332+33 = 33233 을 비교하여 내림차순으로 정렬한다. 그럼 33, 332 순이 된다.
>
> 마찬가지로 33+334 = 33334, 334+33 = 33433 을 비교하여 내림차순으로 정렬하면, 334, 33 순이 된다.
>
> 또한, 모든 요소가 0이면 00...이 아니라 0으로 표시해야 하므로 그에 따른 처리도 해준다.

## Code

```java
import java.util.Arrays;

public class TheBiggestNumber {
    public static void main(String[] args) {
        int[] numbers = {121, 12};


        String s = solution(numbers);
        System.out.println("s = " + s);
    }

    static String solution(int[] numbers) {
        String answer = "";

        String[] s = new String[numbers.length];
        for (int i = 0; i < s.length; i++) {
            s[i] = String.valueOf(numbers[i]);
        }

        // 12121 과 12112를 비교해서 큰값으로 내림차순 정렬
        Arrays.sort(s, (str1, str2) -> (str2 + str1).compareTo(str1 + str2));

        return s[0].equals("0") ? s[0] : String.join("", s);
    }
}

```

