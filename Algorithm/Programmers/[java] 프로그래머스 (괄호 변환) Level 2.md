## Problem : https://programmers.co.kr/learn/courses/30/lessons/60058

## Approach

2020 KAKAO BLIND RECRUITMENT 문제이다.

딱히 접근법이 없다. 문제에 나와있는 조건들을 순서대로 구현하면 된다.

주의할 점은 `4-4 의 괄호방향을 뒤집는다` 이다. 단지 괄호방향만 뒤집을 뿐 문자열의 역순을 취하면 안된다.

> < 문제의 조건 >
>
> ```html
> 1. 입력이 빈 문자열인 경우, 빈 문자열을 반환합니다. 
> 2. 문자열 w를 두 "균형잡힌 괄호 문자열" u, v로 분리합니다. 단, u는 "균형잡힌 괄호 문자열"로 더 이상 분리할 수 없어야 하며, v는 빈 문자열이 될 수 있습니다. 
> 3. 문자열 u가 "올바른 괄호 문자열" 이라면 문자열 v에 대해 1단계부터 다시 수행합니다. 
>   3-1. 수행한 결과 문자열을 u에 이어 붙인 후 반환합니다. 
> 4. 문자열 u가 "올바른 괄호 문자열"이 아니라면 아래 과정을 수행합니다. 
>   4-1. 빈 문자열에 첫 번째 문자로 '('를 붙입니다. 
>   4-2. 문자열 v에 대해 1단계부터 재귀적으로 수행한 결과 문자열을 이어 붙입니다. 
>   4-3. ')'를 다시 붙입니다. 
>   4-4. u의 첫 번째와 마지막 문자를 제거하고, 나머지 문자열의 괄호 방향을 뒤집어서 뒤에 붙입니다. 
>   4-5. 생성된 문자열을 반환합니다.
> ```

## Code

```java
import java.util.Stack;

public class BracketConversion {
    public static void main(String[] args) {
        String s = ")()()()(";

        System.out.println("solution(s) = " + solution(s));
    }

    static String solution(String p) {
        String answer = "";

        // 1단계
        if (p.isEmpty() || checkComplete(p)) {
            return p;
        }

        // 2단계
        int sep = divide(p);
        String u = p.substring(0, sep);
        String v = p.substring(sep, p.length());

        // 3단계
        if (checkComplete(u)) {
            answer += u + solution(v);
        }

        // 4단계
        else {
            String t = "(" + solution(v) + ")";
            answer += t + replace(u.substring(1, u.length() - 1));
        }

        return answer;
    }

  // '올바른 괄호 문자열'인지 판단하여 boolean값 리턴
    static boolean checkComplete(String p) {
        Stack<Character> stack = new Stack<>();
        int size = p.length();

        for (int i = 0; i < size; i++) {
            switch (p.charAt(i)) {
                case '(':
                    stack.push('(');
                    break;
                case ')':
                    if (stack.isEmpty()) {
                        return false;
                    } else {
                        stack.pop();
                    }
                    break;
            }
        }

        if (stack.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

  // 가장 작은 '균형잡힌 괄호 문자열'의 오른쪽 인덱스를 반환
    static int divide(String p) {
        int size = p.length();
        int left_bracket = 0;
        int right_bracket = 0;

        for (int i = 0; i < size; i++) {
            if (p.charAt(i) == '(') {
                left_bracket += 1;
            } else {
                right_bracket += 1;
            }

            if (left_bracket == right_bracket) {
                break;
            }
        }

        return left_bracket + right_bracket;
    }

  // '(' 와 ')' 의 괄호방향을 바꿔서 반환
    static String replace(String p) {
        String s = "";
        for (int i = 0; i < p.length(); i++) {
            if (p.charAt(i) == '(') {
                s += ")";
            } else {
                s += "(";
            }
        }
        return s;
    }
}

```

