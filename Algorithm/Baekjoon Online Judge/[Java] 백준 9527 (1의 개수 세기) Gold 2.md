## Problem : https://www.acmicpc.net/problem/9527

## Approach

> Java에는 `Integer.bitCount()` 이라는 내장 라이브러리 함수가 있다. 인자로 받은 숫자를 이진수로 나타냈을 때, 1의 개수를 리턴해준다.
>
> 이 문제에서는 시간초과가 나서 사용할 수 없지만, 이런 함수도 있긴 하다.

이 문제는 은근히 찾기 힘든 규칙이 존재한다. bit[i]를 `i번째 비트까지 모두0인 숫자(0)`부터 `i번째 비트까지 모두 1인 숫자(2^(i-1))`사이에 있는 숫자들의 1의 개수라고 하자. (i >= 0)

그러면 bit[i] = 2 * bit[i - 1] + (1 << i) 이 된다. (bit[i - 1] 이 나타내는 숫자들에 앞에 0, 1이 붙어야 bit[i] 이 나타내는 숫자가 되기 때문에 *2 인 것이고, 1을 붙일 때 만들 수 있는 수의 개수가 2^i개이기 때문에 +를 해준다.)

- i가 1일 때, `00, 01, 10, 11` 위에서 정의한 숫자는 총 4개이며, i가 1일때의 숫자들 앞에 0과 1을 각각 붙이면서 i가 2일 때의 숫자들을 만들 수 있다. `000, 001, 010, 011, 100, 101, 110, 111`


- 그래서 bit[2] = 2 * bit[1] + 2^2 = 2 * 4 + 4 = 12 이다.

  

##### 이제 2의 n 제곱 수가 아닌 일반적인 숫자를 보자. 26을 예로 들겠다. (26은 이진수로 11010 이다.)

4번째 비트가 1이므로 bit[4] = 2 * bit[3]이다. 하지만 `2^4 = 16 과 26` 사이의 숫자만큼(11개) 4번째 비트가 1인 숫자가 더 존재한다. 그 숫자의 개수는 이렇게 구한다. (x - 1 << i + 1) = (26 - 16 + 1 = 11)

그리고 x 를 1 << i 만큼 감소시킨다. 그러면 우리는 0000 ~ 1010 일 때의 1의 개수만 찾으면 된다.

이제 3번째 비트를 처리하자. 3번째 비트를 처리할 때는 bit[3] = 2 * bit[2] + (10 - 8 + 1 = 3), 그리고 x 를 1 << i만큼 감소시킨다. 위와 마찬가지로 이젠 00 ~ 10 일 때의 1의 개수만 찾으면 된다.

이제 마지막 1번째 비트를 처리하자. bit[1] = 2 * bit[0] + (2 - 2 + 1 = 1)



결론적으로 문제의 풀이는 "`0 ~ b` 숫자들의 1의 개수 - `0 ~ (a - 1)` 숫자들의 1의 개수"를 구하면 정답이다.

설명하는게 어려웠던 문제지만, 손으로 직접 써보고 그 사이의 규칙을 찾아본다면 이해할 수 있을 것이다.

참고로 0번째 비트가 1일 경우 bit[] 배열의 인덱스가 음수가 되기 때문에 처음에 `x & 1`을 ans에 추가해두면 된다.



PS. 제대로 잘 설명이 됐을지 모르겠다,,,

## Code

```java
import java.io.*;
import java.util.StringTokenizer;

/**
 *  No.9527: 1의 개수 세기
 *  URL: https://www.acmicpc.net/problem/9527
 *  Hint: 비트연산 + 규칙 찾기
 */

public class BOJ9527 {
    static long[] bit;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        long a = Long.parseLong(st.nextToken());
        long b = Long.parseLong(st.nextToken());
        initBitCount();

        long ans = getBitCount(b) - getBitCount(a - 1);
        bw.write(String.valueOf(ans));
        br.close();
        bw.close();
    }

    static long getBitCount(long x) {
        long ans = x & 1;

        for (int i = 54; i > 0; i--) {
            if ((x & (1L << i)) > 0L) { // 숫자 x의 i번째 비트가 1이면
                ans += bit[i - 1] + (x - (1L << i) + 1);
                x -= (1L << i);
            }
        }
        return ans;
    }

    // i번째 비트가 1일 때의 비트카운트(누적)
    static void initBitCount() {
        bit = new long[55]; // 입력의 최댓값인 10^16의 비트길이는 54이므로 (0부터)
        bit[0] = 1;
        for (int i = 1; i < 55; i++) {
            bit[i] = 2 * bit[i - 1] + (1L << i);
        }
    }
}
```