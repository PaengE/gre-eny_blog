## Problem : https://programmers.co.kr/learn/courses/30/lessons/17687

## Approach

2018 KAKAO BLIND RECRUITMENT 문제였다.

일단 0부터 숫자를 1씩 늘린 숫자를 N진법으로 바꾼 후, 문자열 s에 붙인다.

> 10진수 A를 N진법으로 바꾸는 방법은 아래와 같다.
>
> 1. A를 N으로 나눈 나머지를 리스트의 끝에 저장한다.
> 2. A를 N으로 나눈 몫을 다시 A에 저장한다.
> 3. 1번 2번과정을 A가 0이 아닐 때까지 반복한 후, 리스트를 뒤집는다.

튜브가 (t * m) + p번째 문자까지 말해야하므로, 문자열의 길이가 (t * m) + p가 되기 전까지 이어서 구성해 나간다.

최종 생성된 문자열 s에서 튜브가 말해야될 인덱스의 charAt을 구하여 answer에 담은 후 반환해준다.

## Code

```java
public class N_DecimalGame {
    public static void main(String[] args) {
        N_DecimalGame ndg = new N_DecimalGame();
        System.out.println(ndg.solution(2, 4, 2, 1));
        System.out.println(ndg.solution(16, 16, 2, 1));
        System.out.println(ndg.solution(16, 16, 2, 2));
    }

    public String solution(int n, int t, int m, int p) {
        String s = "0";
        int count = 0;

        while (s.length() <= t * m + p) {
            String nthNum = "";
            int nth = count++;

            // 10진수를 n진수로 바꾸는 과정
            while (nth != 0) {
                // 10 이상은 A B C D E F
                if (nth % n >= 10) {
                    nthNum += String.valueOf((char)('A' + (nth % n) - 10));
                } else {
                    nthNum += String.valueOf(nth % n);
                }
                nth /= n;
            }
            s += new StringBuffer(nthNum).reverse().toString();
        }

        String answer = "";
        for (int i = 0; i < t; i++) {
            answer += String.valueOf(s.charAt(m * i + p - 1));
        }
        return answer;
    }
}

```

