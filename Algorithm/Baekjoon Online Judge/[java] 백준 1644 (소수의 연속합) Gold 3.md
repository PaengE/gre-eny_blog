## Problem : https://www.acmicpc.net/problem/1644

## Approach

> 한쪽에서 시작하는 투포인터를 활용한 문제였다.
>
> 주어진 수 N을 연속된 소수의 합으로 만드는 방법의 가짓수를 찾는 문제이다.

따라서, 일단 연속된 소수 수열을 구해야한다.

주어진 수 N이 소수라면 그 또한 방법 가짓수에 포함되기 때문에, 1 ~ N까지의 수들 중에서 소수를 모두 찾아야한다.

소수를 구하는 방법은 `에라토스테네스의 체` 방법을 사용하였다. 여기서는 해당 방법을 설명하진 않는다.

start = 0, end = 0으로 시작한다.

1. 에라토스테네스의 체를 활용하여 소수 수열을 구한다. Index 처리를 위하여 list의 마지막에 0을 추가로 집어넣었다.
2. start == end 라면,
   1. sum < n 이면, end++ 후 sum에 list.get(end)를 더해준다.
   2. sum > n 이면, sum에 list.get(start)를 뺀 후, start++를 한다. 또, end++ 후, sum에 list.get(end)를 더한다. 
      - 현재의 start위치의 이전 숫자들은 필요가 없기 때문이다.
   3. sum == n 이면, answer++ 후, 종료 조건을 만족시키기 위해 start++, end++ 를 해준다.
      - start == end 인 상태에서, sum과 n이 같다면, start와 end가 n과 같은 경우밖에 없다. 이 말은 즉, 수열의 마지막까지 도달했다는 뜻이다.
3. start != end 라면, (여기서는 항상 start < end)
   1. sum < n 이면, end++ 후, sum에 list.get(end)를 더해준다.
   2. sum > n 이면, sum에 list.get(start)를 빼준 후, start++ 를 해준다.
   3. sum == n 이면, answer++ 후, sum에 list.get(start)를 빼준 후, start++ 를 해준다.
      - start와 end를 end + 1로 조정하지 않는 이유는, start+1 ~ end+1의 합이 n과 같을 수도 있기 때문이다.

start 혹은 end가 list.size() - 1보다 작을때까지만 `과정2, 3`을 반복한다.

## Code

```java
import java.io.*;
import java.util.ArrayList;

/**
 *  No.1644: 소수의 연속합
 *  Hint: 에라스토테네스의 체를 이용한 소수를 구한 후 수열을 만듦
 *        한 쪽에서 시작하는 투포인터를 사용하여 연속합 찾기
 */

public class BOJ1644 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        boolean[] prime = isPrime(n);   // false면 소수 true면 소수아님

        if (n == 1) {
            bw.write("0");
            bw.close();
            br.close();
            return;
        }

        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (!prime[i]) {
                list.add(i);
            }
        }
        list.add(0);

        int start = 0, end = 0;
        int sum = list.get(0);
        int answer = 0;
        while (true) {
            if (start == end) {
                if (sum < n) {
                    sum += list.get(++end);
                } else if (sum > n) {
                    sum -= list.get(start++);
                    sum += list.get(++end);
                } else {
                    answer++;
                    start++;
                    end++;
                }
            } else {
                if (sum < n) {
                    sum += list.get(++end);
                } else if (sum > n) {
                    sum -= list.get(start++);
                } else {
                    answer++;
                    sum -= list.get(start++);
                }
            }
            if (start == list.size() - 1 || end == list.size() - 1) {
                break;
            }
        }
        bw.write(String.valueOf(answer));
        bw.close();
        br.close();
    }

    // 소수판별: false면 소수 true면 소수아님
    static boolean[] isPrime(int n) {
        boolean[] prime = new boolean[n + 1];
        prime[1] = true;

        // 2의 배수는 소수 아님
        for (int i = 4; i <= n; i += 2) {
            prime[i] = true;
        }
        // 3의 배수는 소수 아님
        for (int i = 6; i <= n; i += 3) {
            prime[i] = true;
        }

        for (int i = 5; i <= n; i += 2) {
            if (!prime[i]) {
                for (int j = 3; j <= Math.sqrt(i); j++) {
                    if (i % j == 0) {
                        prime[i] = true;
                        break;
                    }
                }
            }
            if (prime[i]) {
                for (int j = i; j <= n; j += i) {
                    prime[j] = true;
                }
            }
        }
        return prime;
    }
}

```

