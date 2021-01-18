## Problem : https://programmers.co.kr/learn/courses/30/lessons/12909

## Approach

괄호체크는 간단하게 Stack을 사용하여 확인할 수 있다.

'('을 만나면 stack에 push를 하고, ')'를 만났을 땐 stack이 비어있거나 stack의 top이 '('이 아니면 false를 반환한다.
맞다면 stack에서 pop을 한다.

또, 문자열의 끝까지 위 과정을 반복해서 끝낸 후, stack이 차있다면 false를 반환한다.

위의 두 경우는 올바르지 않은 괄호이고, 위의 두 경우가 아닌경우 올바른 괄호이므로 true를 반환한다.

## Code

```java
import java.util.Stack;

public class CorrectBracket {
    public static void main(String[] args) {
        String s = "(()(";
        CorrectBracket cb = new CorrectBracket();
        System.out.println(cb.solution(s));
    }

    public boolean solution(String s) {
        var stack = new Stack<Character>();

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push('(');
            } else {
                if (stack.isEmpty()) {
                    return false;
                } else {
                    stack.pop();
                }
            }
        }

        if (stack.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}

```

