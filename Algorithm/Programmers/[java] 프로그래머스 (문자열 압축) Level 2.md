## Problem : https://programmers.co.kr/learn/courses/30/lessons/60057

## Approach

2020 KAKAO BLIND RECRUITEMENT 문제로, 문자열을 처리하는 문제이다.

최대로 문자열을 압축하여, 그 길이를 반환하여야 한다. 압축 단위는 최대 원래 문자열 크기의 반이다.

알고리즘 자체는 쉽지만, 여러 테스트케이스들을 고민해 봐야한다. 

예를 들어, x....x처럼 x가 100개인 경우의 최소길이는 4이지만, x가 99개면 최소길이는 3이다.

## Code

```java
public class StringCompression {
    public static void main(String[] args) {
        String s = "xxxxxxxxxxyyy";

        System.out.println(solution(s));
    }

    static int solution(String s) {
        int size = s.length() / 2;
        int length = s.length();
        int answer = s.length();

        // 단위를 1 부터 s.length / 2까지 반복(s.length / 2 보다 크게 정하는건 무의미)
        for (int i = 1; i <= size; i++) {
            String pre = s.substring(0, i);
            int subLength = 0;
            int count = 1;
            int j = 0;

            // 나눌 수 있을때까지 나누면서 비교
            for (j = i; j + i <= length; j += i) {
                String cur = s.substring(j, j + i);
                if (pre.equals(cur)) {
                    count += 1;
                } else {
                    pre = cur;
                    if (count == 1) {
                        subLength += i;
                    } else {
                        subLength += i + String.valueOf(count).length();
                    }
                    count = 1;
                }
            }

            // 마지막으로 나눠진 문자열 처리
            if (count == 1) {
                subLength += i;
            } else {
                subLength += i + String.valueOf(count).length();
            }

            // 문자열의 마지막이 완벽하게 나눠지지 않은 부분 처리
            if (j != length) {
                subLength += length - j;
            }

            // 최소 길이를 저장
            answer = Math.min(answer, subLength);
        }

        return answer;
    }
}

```

