## Problem : https://programmers.co.kr/learn/courses/30/lessons/12924

## Approach

naive한 방법으로는 1부터 연속된 몇개의 숫자를 더해서 주어진 숫자 n을 만들 수 있는지를 검사하는 방법이다.

그러나 수학에서 `주어진 자연수를 연속된 자연수의 합으로 표현하는 방법의 수는 주어진 수의 홀수 약수의 개수와 같다.`는 정수론 정리가 존재한다.

따라서 위의 정리를 알았다면 문제 풀이가 더 쉬웠을 것이다.

## Code

```java
public class NumberExpression {
    public static void main(String[] args) {
        int n = 15;
        NumberExpression ne = new NumberExpression();
        System.out.println(ne.solution(n));
    }

    // 주어진 자연수를 연속된 자연수의 합으로 표현하는 방법의 수는
    // 주어진 수의 홀수 약수의 개수와 같다.(정수론 정리가 있음)
    public int solution(int n) {
        int answer = 0;
        for (int i = 1; i <= n; i += 2) {
            if (n % i == 0) {
                answer += 1;
            }
        }
        return answer;
    }

    public int mySolution(int n) {
        int answer = 0;
        int sum = 0;

        for (int i = 1; i <= n; i++) {
            sum = 0;
            for (int j = i; j <= n; j++) {
                sum += j;
                if (sum == n) {
                    answer++;
                    break;
                } else if (sum > n) {
                    break;
                }
            }
        }
        return answer;
    }
}

```

