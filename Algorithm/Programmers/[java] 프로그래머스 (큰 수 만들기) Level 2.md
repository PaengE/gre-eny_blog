## Problem : https://programmers.co.kr/learn/courses/30/lessons/42883

## Approach (Wrong)

처음엔 주어진 문자열로 만들 수 있는 모든 수를 만들어 가장 큰 값을 저장해서 풀면 된다고 생각했다.

그러나 주어진 문자열의 길이가 최대 1,000,000자리 이하인 숫자이므로 당연하게 TLE, RTE를 받았다.

## Approach (Correct)

주어진 문자열에서 K개를 지워 가장 큰 수를 만들려면 앞자리 수가 클수록 좋다.

그렇다면 앞자리가 커질 수 있게, 앞의 수가 뒤의 수보다 작으면 삭제하면 된다. 

​	-> 언제까지? K개까지만 (K개만 지울 수 있으니까)

작지 않다면 그 뒤에 붙인다. 이것을 스택을 이용하여 구현 할 수 있다.

1. 주어진 숫자열을 앞에서부터 스택에 넣는다. 단 스택의 TOP은 항상 스택의 요소중 가장 작은 수가 되도록 유지한다.(내림차순이 되도록)

2. 다음 숫자가 스택의 TOP보다 크면 스택의 TOP이 다음 숫자보다 작아질 때까지 스택에서 뺀다.

   (단, 스택에서 숫자를 빼는 행위는 최대 K번이다. K번이 되면 더이상 pop 행위는 하지 않는다.)

3. 숫자를 스택에 다 넣을 때까지 pop 행위가 K번이 아니라면(스택은 내림차순으로 되어있다는 뜻이므로), pop 행위가 총 K번이 되도록 스택에서 뺀다.

4. 그런 후 역을 취하면 가장 큰 수를 이룬다.

## Code

```java
import java.util.Stack;

public class MakingBiggerNumber {
    Integer max = 0;
    public static void main(String[] args) {
        String number = "9988445566";
        int k = 4;

        MakingBiggerNumber q = new MakingBiggerNumber();

        System.out.println(q.solution(number, k));

    }

    public String solution(String number, int k) {
        char[] input = number.toCharArray();

        // 스택, 작은 수 일수록 위에 존재한다.
        Stack<Character> temp = new Stack<>();
        for(int i = 0 ; i < input.length ; i++) {
            while(!temp.empty() && k > 0 && temp.peek() < input[i]) {
                k--;
                temp.pop();
            }
            temp.push(input[i]);
        }


        StringBuilder sb = new StringBuilder();
        // k개를 삭제하지 못 한 경우 뒤에서 부터 제거.
        while(!temp.empty()) {
            if(k != 0) {
                temp.pop();
                k--;
            } else {
                sb.append(temp.pop());
            }
        }
        return sb.reverse().toString();
    }


    // 완전탐색 (시간초과, 메모리초과)
    private void wrong_solution(String number, String s, int k, int index) {
        if (s.length() == number.length() - k) {
            max = Math.max(max, Integer.parseInt(s));
            return;
        }

        if (index >= number.length()) {
            return;
        }

        wrong_solution(number, s + String.valueOf(number.charAt(index)), k, index + 1);
        wrong_solution(number, s, k, index + 1);
    }
}

```

