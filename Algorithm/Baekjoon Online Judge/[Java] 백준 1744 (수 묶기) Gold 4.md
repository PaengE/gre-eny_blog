## Problem : https://www.acmicpc.net/problem/1744

## Approach

> `Greedy`한 문제이다.
>
> 아무 위치에 있는 두 수를 선택해도 되기 때문에, 먼저 정렬을 수행하는게 선택하기에 편하다.

문제 풀이에 앞서, 상식을 이용할 필요가 있다.

- 음수 두개를 곱하는 것이, 음수 두개를 더하는 것보다 값이 크다.
- 음수와 0은 곱하는 것이, 양수와 0은 더하는 것이 값이 크다.
- -1 두 개는 곱하는 것이, 1 두 개는 더하는 것이 값이 크다.

위의 상식을 이용해서 입력의 유형을 살펴보자. 각 유형별로 최댓값을 만드려면 해야할 연산을 나타낸다.

1. (음수, 음수) -> 곱하기
2. (음수, 0) -> 곱하기
3. (음수, 양수) -> 더하기
4. (0, 0) -> 상관없다.
5. (양수, 0) -> 더하기
6. (양수, 양수) -> 곱하기

여기서 예외로 `숫자 1`이 있다. 숫자 1이 두 개있더라도, (양수, 양수) 처럼 곱하기를 하면 안된다. 곱한 값 1 보다 더한 값 2 가 더 크기 때문이다. 또한, 규칙을 보면 0은 음수로 봐도 무방하다.

최댓값을 만드려면 어쨌든 부호가 같으면 곱하기, 부호가 다르면 더하기가 유리하다. 

따라서 음수의 개수, 양수의 개수를 센 다음 최대로 곱한 뒤, 남은 것들은 더해주면 된다.

## Code

```java
import java.io.*;
import java.util.Arrays;

/**
 *  No.1744: 수 묶기
 *  Hint: Greedy + sort
 */

public class BOJ1744 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n];

        int minusCount = 0;
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(br.readLine());
            if (arr[i] <= 0) {
                minusCount++;
            }
        }
        Arrays.sort(arr);

        int ans = 0;
        // 음수 부분(0포함) - 두 개씩 묶어서 더하기
        for (int i = 1; i < minusCount; i += 2) {
            ans += (arr[i - 1] * arr[i]);
        }

        // 음수(0포함)가 홀수 개일 경우 그냥 더하기
        if (minusCount % 2 == 1) {
            ans += arr[minusCount - 1];
        }

        // 양수가 홀수 개일 경우 그냥 더하기
        if ((n - minusCount) % 2 == 1) {
            ans += arr[minusCount];
        }

        // 양수 부분 - 두 개씩 묶어서 더하기
        for (int i = n - 1; i > minusCount ; i -= 2) {
            int mul = arr[i] * arr[i - 1];
            int sum = arr[i] + arr[i - 1];
            if (mul > sum) {
                ans += mul;
            } else {
                ans += sum;
            }
        }

        bw.write(String.valueOf(ans));
        bw.close();
        br.close();
    }
}
```

