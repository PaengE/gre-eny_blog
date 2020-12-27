## Problem : https://programmers.co.kr/learn/courses/30/lessons/12899

## Approach

> < 문제 조건 >
>
> ​	1. 124 나라에는 자연수만 존재합니다.
>
> ​	2. 124 나라에는 모든 수를 표현할 때 1, 2, 4만 사용합니다.
>
> ​	3. n은 500,000,000이하의 자연수이다.

| 10진법 | 124 나라 | 10진법 | 124 나라 |
| :----- | :------- | ------ | -------- |
| 1      | 1        | 6      | 14       |
| 2      | 2        | 7      | 21       |
| 3      | 4        | 8      | 22       |
| 4      | 11       | 9      | 24       |
| 5      | 12       | 10     | 41       |

위의 표를 보아 예상하기를 11:42, 12:44, 13:111, 14:112, 15:114, 16:121, 17:122, 18:124 ~ 일 것이다.

그럼 3으로 나눴을 때, 나머지가 1이면 1의 자리수가 1, 2이면 2, 0이면 4인 것이 보일 것이다.

그럼 계속 3으로 나누면서 나머지를 계산하면 되겠으나, 6, 9, 12의 경우를 보면 앞의 규칙을 따르면 표와 다르다.

(위의 규칙을 따르면 6:24, 9:44, 12:114 -> 표와 달라서 새로운 규칙을 적용시켜야 함.)

따라서 6, 9, 12와 같이 3으로 나눈 나머지가 0일 경우에는 n--를 해준다. 그럼 표와 동일한 숫자가 나온다.

> 정리하면,
>
> ​	1. n을 나눈 나머지에 따라 문자열의 마지막에 붙인다.
>
> ​	2. n 을 3으로 나눈다.
>
> ​	3. 나머지가 0이면 n에 -1을 해준다.

## Code

```java
public class NumberOfCountry124 {
    public static void main(String[] args) {
        int n = 13;

        String s = solution(n);

        System.out.println("s = " + s);
    }

    static String solution(int n) {
        StringBuilder sb = new StringBuilder();
        while (n > 0) {
            int remainder = n % 3;
            n /= 3;

            if (remainder == 0) {
                n--;
            }

            if (remainder == 0) {
                sb.insert(0, 4);
            } else if (remainder == 1) {
                sb.insert(0, 1);
            } else {
                sb.insert(0, 2);
            }
        }

        String answer = sb.toString();
        return answer;
    }
}

```

