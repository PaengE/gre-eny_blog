## Problem : https://programmers.co.kr/learn/courses/30/lessons/12904

## Approach

> 처음 생각한 풀이는 문자열의 왼쪽 끝과 오른쪽 끝을 각각 left와 right로 정해놓고, 범위를 점점 줄여나가는 식으로 풀이를 생각했지만, 결과적으로 시간초과가 나는 코드가 됐다.

문자열의 모든 위치를 기준으로 시작하여, 팰린드롬이면 계속해서 범위를 늘려 팰린드롬 문자열인지를 체크한다. 팰린드롬 문자열이 아닐 때까지(혹은 문자열의 범위를 벗어날 때까지) 반복하여 팰린드롬의 길이를 구하는 방식으로 문제를 풀 수있다.

여기서 주의할 점은, 가장 긴 팰린드롬 문자열의 길이가 홀수인지, 짝수인지 모르기 때문에 두 개의 경우 모두를 검사하여야한다.
(홀수일 경우 abcba 이런 식인데, 짝수의 경우 abccba 이므로 검사 조건이 달라지기 때문이다.)

## Code

```java
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LongestPalindrome {
    public int solution(String s) {
        int answer = 1;
        for (int i = 0; i < s.length() - 1; i++) {
            // 팰린드롬 부분문자열의 길이가 홀수일 경우
            answer = Math.max(answer, findLongestPalindrome(i, i, s));
            // 팰린드롬 부분문자열의 길이가 짝수일 경우
            answer = Math.max(answer, findLongestPalindrome(i, i + 1, s));
        }
        return answer;
    }

    // 어느 한 지점부터 늘려가면서 팰린드롬의 길이 구하는 메소드
    private int findLongestPalindrome(int left, int right, String s) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }

    @Test
    public void test(){
        Assertions.assertEquals(7, solution("abcdcba"));
        Assertions.assertEquals(3, solution("abacde"));
    }
}

```

