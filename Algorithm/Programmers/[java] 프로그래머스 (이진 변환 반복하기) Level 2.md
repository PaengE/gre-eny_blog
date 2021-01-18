## Problem : https://programmers.co.kr/learn/courses/30/lessons/70129

## Approach

월간 코드 챌린지 시즌1의 문제이며, 문자열을 처리하는 문제이다.

> 1. x의 모든 0을 제거합니다.
> 2. x의 길이를 c라고 하면, x를 c를 2진법으로 표현한 문자열로 바꿉니다.

위의 두가지를 반복하면서 변환 횟수, 제거된 0의 개수를 세어서 반환하면 된다.

따로 설명이 필요없고 구현 문제기 때문에 코드를 보면서 문자열을 처리하는 방법을 익히는 것이 좋을 것 같다.

## Code

```java
public class BinaryConversion {
    public static void main(String[] args) {
        String s = "1111111";
        BinaryConversion bc = new BinaryConversion();
        bc.solution(s);
    }

    public int[] solution(String s) {
        int count = 0;
        int deleted_zero = 0;

        while (!s.equals("1")) {
            count++;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '0') {
                    deleted_zero += 1;
                }
            }

            s = s.replaceAll("0", "");
            if (s.equals("1")) {
                break;
            }

            int length = s.length();
            s = Integer.toBinaryString(length);
        }

        System.out.println("count = " + count);
        System.out.println("deleted_zero = " + deleted_zero);
        return (new int[]{count, deleted_zero});
    }
}

```

