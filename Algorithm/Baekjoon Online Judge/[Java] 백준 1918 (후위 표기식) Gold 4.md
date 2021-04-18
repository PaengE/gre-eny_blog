## Problem : https://www.acmicpc.net/problem/1918

## Approach

> Stack을 이용하여 풀이하는 문제이다.
>
> 입력으로 주어진 문자열을 하나씩 읽으면서 각 문자에 대해 우선순위를 매겨서 하나씩 뽑는 형태이다.

다음과 같이 4개의 경우가 발생한다. 참고로 스택엔 `여는괄호`와 `연산자`만 들어가게끔 구현한다.

스택의 문자들은 각각 우선순위가 매겨져 있으며, `(`가 0, `+-`가 1, `*/`가 2로 숫자가 높을수록 우선이 된다.

stack의 top을 검사하는 것은 stack이 비어있지 않을 때에만 수행한다.

1. 현재 문자가 `알파벳`인 경우
   - 알파벳인 경우에는 그대로 정답문자열에 추가한다.
2. 현재 문자가 `여는괄호(`인 경우
   - 여는괄호의 경우 stack에 push를 한다.
3. 현재 문자가 `닫는괄호)`인 경우
   - stack의 top이 `여는괄호`가 될 때까지 stack에서 pop을 하여 정답문자열에 추가한다.
   - stack의 top이 여는괄호가 되면 pop하여 여는괄호를 없앤다.
4. 현재 문자가 `연산자`인 경우
   - stack의 top을 검사하여, 현재 연산자의 우선순위보다 stack의 top에 있는 문자의 우선순위가 크거나 같다면, pop하여 정답문자열에 추가한다.
   - 위의 경우가 아니라면, 그냥 stack에 현재 연산자를 push한다.

마지막으로 stack에 요소가 남아있다면 모두 정답문자열의 뒤쪽에 붙인다.



문제의 예제인 `A*(B+C)` 를 살펴보자. 스택은 [] 로 표시하고 top은 왼쪽이다.

- `A` -> ans에 추가 `[], ans = A`
- `*` -> stack이 비어있으므로 stack에 추가 `[*], ans = A`
- `(` -> stack에 추가 `[(*], ans = A`
- `B` -> ans에 추가 `[(*], ans = AB`
- `+` -> stack의 top `(`의 우선순위가 `+`의 우선순위보다 작으므로 stack에 추가 `[+(*], ans = AB`
- `C` -> ans에 추가 `[+(*], ans = ABC`
- `)` -> stack의 top이 `(` 될 때까지 pop하여 정답문자열에 추가한 후, `(`을 스택에서 뺌 `[*], ans = ABC+`
- 스택이 비어있지 않으므로 모든 요소를 정답문자열의 뒤에 추가함 `[], ans = ABC+*`

## Code

```java
import java.io.*;
import java.util.Stack;

/**
 *  No.1918: 후위 표기식
 *  URL: https://www.acmicpc.net/problem/1918
 *  Hint: Stack
 */

public class BOJ1918 {
    static StringBuilder sb = new StringBuilder();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        String s = br.readLine();
        Stack<Character> stk = new Stack<>();

        for (Character c : s.toCharArray()) {
            if (c >= 'A' && c <= 'Z') { // 알파벳이면 그대로 출력
                sb.append(c);
            } else if (c == '(') {  // 여는 괄호이면
                stk.push(c);
            } else if (c == ')') {  // 닫는 괄호이면
                while (!stk.isEmpty()) {    // 여는 괄호가 나올 때까지 수행ㄱ
                    if (stk.peek() == '(') {
                        stk.pop();
                        break;
                    }
                    sb.append(stk.pop());
                }
            } else {	// 연산자이면
                while (!stk.isEmpty() && priority(stk.peek()) >= priority(c)) {
                    sb.append(stk.pop());
                }
                stk.push(c);
            }
        }

        while (!stk.isEmpty()) {
            sb.append(stk.pop());
        }

        bw.write(sb.toString());
        bw.close();
        br.close();
    }

    // 연산자 우선순위
    static int priority(char c) {
        if (c == '(') {
            return 0;
        } else if (c == '+' || c == '-') {
            return 1;
        } else {
            return 2;
        }
    }
}
```

