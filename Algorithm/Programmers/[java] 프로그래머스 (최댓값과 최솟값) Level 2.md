## Problem : https://programmers.co.kr/learn/courses/30/lessons/12939

## Approach

주어진 문자열 s가 공백으로 구분되어져 있으므러 공백문자(" ")를 기준으로 split한 뒤, 각각의 요소들에 접근하여 최댓값과 최솟값을 갱신하면 되는 간단한 문제이다.

## Code

```java
public class MaxValueAndMinValue {
    public static void main(String[] args) {
        String s = "55 55 5 558";
        MaxValueAndMinValue mm = new MaxValueAndMinValue();
        System.out.println("mm.solution(s) = " + mm.solution(s));
    }

    public String solution(String s) {
        String[] str = s.split(" ");
        int min, max;
        min = max = Integer.parseInt(str[0]);
        for (int i = 1; i < str.length; i++) {
            int num = Integer.parseInt(str[i]);

            if (num < min) {
                min = num;
            }
            if (max < num) {
                max = num;
            }
        }

        return min + " " + max;
    }
}

```

