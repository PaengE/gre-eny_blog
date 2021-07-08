## Problem : https://www.acmicpc.net/problem/16638



## Approach

> `Backtracking` 기법과 `중위표기 -> 후위표기` 를 위한 `Priority`, `Stack` 등을 사용해야 했던 문제이다.

중첩된 괄호는 사용할 수 없고, 괄호 안에는 하나의 연산자만 존재할 수 있다는 점을 활용하면, `연산자`를 기준으로 괄호를 씌울 것인지 말 것인지를 결정하면 된다는 걸 알 수 있다.

따라서 `brackets` 배열을 두어 해당 연산자가 괄호가 씌워져 있는지를 표시한다.



문제를 푸는 주요 로직은 다음과 같다.

- `Backtracking` 기법을 이용하여 괄호를 씌우는 모든 수식을 구한다. 
  - 각 연산의 우선순위는 다음과 같다. `숫자: 0`, `괄호: 1`, `*: 2`, `+-: 3`
  - 입력을 받을 때 우선순위를 지정하여 저장하고, backtracking에서 괄호로 처리했다면 우선순위를 1로 재지정해 준다.
- 각 수식을 계산하여 최댓값 ans 를 갱신하면 된다.
  - 먼저 `중위표기`를 `후위표기`로 변환한다. 수식을 앞에서부터 순회한다.
    - 현재 문자가 `피연산자(숫자)`면 바로 `postfix `리스트에 넣는다.
    - 현재 문자가 `연산자`면 스택이 비었거나, 스택의 TOP보다 우선순위가 낮은 문자일 때까지 pop() 하면서 `postfix` 리스트에 넣는다. 그리고 그 연산자를 `스택`에 push 한다.
    - 모든 문자를 처리하고도 스택이 비어있지 않다면, 스택의 모든 요소를 `postfix` 리스트에 넣는다.
  - 이제 우선순위를 기반으로 만들어진 `후위표기` 수식을 계산한다.
    - 현재 문자가 `피연산자(숫자)`면 스택에 push 한다.
    - 현재 문자가 `연산자`인 경우 스택에서 2개를 뽑아 계산한다. 그리고 계산한 결과를 스택에 push 한다.
    - 마지막 문자까지 계산한 뒤 스택에 남은 하나의 요소가 결과가 된다.



## Code

```java
import java.io.*;
import java.util.ArrayList;
import java.util.Stack;

/**
 *  No.16638: 괄호 추가하기 2
 *  Hint: Backtracking + (중위표기 -> 후위표기) + 구현 + 우선순위
 */

public class BOJ16638 {
    static int n, ans = Integer.MIN_VALUE;
    static boolean[] brackets;  // 괄호를 표시하는 배열
    static Formula[] input; // 수식의 문자와 우선순위를 저장
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        n = Integer.parseInt(br.readLine());
        brackets = new boolean[n];
        input = new Formula[n];

        String s = br.readLine();
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c == '*') {
                input[i] = new Formula(c, 2);
            } else if (c == '+' || c == '-') {
                input[i] = new Formula(c, 3);
            } else {
                input[i] = new Formula(c, 0);
            }
        }

        makeBrackets(1);
        bw.write(String.valueOf(ans));
        bw.close();
        br.close();
    }

    // backtracking 으로 괄호를 생성함
    static void makeBrackets(int idx) {
        if (idx >= n) {
            int res = postfixToResult(infixToPostfix());
            ans = Math.max(ans, res);
            return;
        }

        if (idx == 1) { // 첫 번째 연산자일 경우
            brackets[idx] = true;
            makeBrackets(idx + 2);
            brackets[idx] = false;
        } else {
            if (!brackets[idx - 2]) {   // 이전에 괄호가 없을 경우에만
                brackets[idx] = true;
                makeBrackets(idx + 2);
                brackets[idx] = false;
            }
        }
        makeBrackets(idx + 2);
    }

    // 우선순위를 기준으로 중위표기를 후위표기로 변환
    static ArrayList<Formula> infixToPostfix() {
        ArrayList<Formula> postfix = new ArrayList<>();
        Stack<Formula> stk = new Stack<>();

        for (int i = 0; i < n; i++) {
            if (input[i].priority == 0) {   // 피연산자
                postfix.add(input[i]);
            } else {    // 연산자
                int priority = input[i].priority;
                if (brackets[i]) {  // 괄호를 씌운 연산자라면 우선순위를 변경
                    priority = 1;
                }

                while (!stk.isEmpty() && stk.peek().priority <= priority) {
                    postfix.add(stk.pop());
                }

                stk.push(new Formula(input[i].character, priority));
            }
        }

        while (!stk.isEmpty()) {
            postfix.add(stk.pop());
        }
        return postfix;
    }

    // 후위표기된 수식을 계산
    static int postfixToResult(ArrayList<Formula> postfix) {
        Stack<Integer> stk = new Stack<>();
        int res = 0;
        for (int i = 0; i < n; i++) {
            if (postfix.get(i).priority == 0) { // 피연산자
                stk.push(postfix.get(i).character - '0');
            } else {    // 연산자
                int n1 = stk.pop();
                int n2 = stk.pop();
                res = calculate(n2, n1, postfix.get(i).character);
                stk.push(res);
            }
        }
        return stk.pop();
    }

    // 연산자 계산
    static int calculate(int n1, int n2, char op) {
        int res = 0;
        switch (op) {
            case '+':
                res = n1 + n2;
                break;
            case '-':
                res = n1 - n2;
                break;
            case '*':
                res = n1 * n2;
                break;
        }
        return res;
    }

    static class Formula{
        char character;
        int priority;   // 숫자:0, +-:3, *:2, 괄호:1

        Formula(char op, int priority) {
            this.character = op;
            this.priority = priority;
        }
    }
}
```