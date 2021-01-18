## Problem : https://programmers.co.kr/learn/courses/30/lessons/12973

## Approach

2017 팀스타운 문제이다.

주어진 문자열을 처음부터 Stack에 넣고 빼기를 반복한다.

넣기 전에 stack의 top과 비교하여 만약 같다면 pop을 하고, 다르다면 push를 한다.

문자열의 끝까지 다 완료한 후, 스택이 비어있으면 문자열을 모두 제거할 수 있는 것이고,
스택이 비어있지 않다면 문자열을 모두 제거할 수 없는 것이다.

## Code

```java
import java.util.Stack;

public class PairRemove {
    public static void main(String[] args) {
        PairRemove pr = new PairRemove();
        String s = "baabaa";
        System.out.println(pr.solution(s));

    }

    public int solution(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (stack.isEmpty() || stack.peek() != s.charAt(i)) {
                stack.push(s.charAt(i));
            } else if (stack.peek() == s.charAt(i)) {
                stack.pop();
            }
        }
        return stack.isEmpty() ? 1 : 0;
    }
}

```

