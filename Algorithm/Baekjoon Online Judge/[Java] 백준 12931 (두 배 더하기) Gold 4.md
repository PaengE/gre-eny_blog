## Problem : https://www.acmicpc.net/problem/12931



## Approach

> `Greedy` 한 방법으로 숫자의 `2진수 비트`를 활용하여 문제를 풀이하였다.



먼저 문제 풀이에 사용한 자바 메소드를 살펴보자.

- `Integer.toBinaryString(int n)`: 숫자 n을 받아서 문자열 이진수로 바꾼 뒤 리턴한다.
- `Integer.bintCount(int n)`: 숫자 n의 이진수에서 1의 개수를 리턴한다.



숫자를 만드는 데에 `+1`과 `*2` 하는 방법이 있다.

이진수와 연관하여 생각하면, `+1` 연산은 이진수 첫번째 자리의 비트를 하나 올리는 것이며, `*2`연산은 이진수 자체를 왼쪽으로 한칸씩 밀고 첫번째 자리 비트를 0으로 만드는 것이다.

따라서 숫자 n의 이진수 b가 있을 때, b의 1의 개수가 `+1` 연산의 횟수이고, b의 길이 - 1이 `*2` 연산 횟수이다.



숫자 15, 16, 17 를 예로 들어보면 각각 이진수는 1111, 10000, 1001 이다.

- 16(10000) 의 `+1` 횟수는 1번(1의 개수가 1개), `*2` 횟수는 4번(길이5 - 1)이다. `(1 - 2 - 4 - 8 - 16)`
- 17(10001) 의 `+1` 횟수는 2번, `*2` 횟수는 4번이다.  `(1 - 2 - 4 - 8 - 16 - 17)`
- 15(1111)의 `+1` 횟수는 4번, `*2` 횟수는 3번이다. `(1 - 2 - 3 - 6 - 7 - 14 - 15)`



그리고 잠시 생각해보면 전체를 `*2`하는 연산의 순서를 잘 끼워 맞추면,

문제에서 요하는 정답은 `각 숫자의 +1 연산 횟수의 합` + `각 숫자의 *2 연산 횟수 중 최댓값` 인 것을 알 수 있다.

15 와 17을 예로 들어 위 방법대로 정답을 구하면 (2 + 4) + 4 = 10 이다.

- (0, 0) -> (1, 0) : 1번 숫자에 +1
- (1, 0) -> (10, 0) : 각 숫자에 *2
- (10, 0) -> (10, 1) : 2번 숫자에 +1
- (10, 1) -> (100, 10) : 각 숫자에 *2
- (100, 10) -> (100, 11) : 2번 숫자에 +1
- (100, 11) -> (1000, 110) : 각 숫자에 *2
- (1000, 110) -> (1000, 111) : 2번 숫자에 +1
- (1000, 111) -> (10000, 1110) : 각 숫자에 *2
- (10000, 1110) -> (10001, 1111) : 각 숫자에 +1

위와 같이 총 10번의 횟수가 사용된다.



## Code

```java
import java.io.*;
import java.util.StringTokenizer;

/**
 *  No.12931: 두 배 더하기
 *  Hint: Greedy + bitCount
 */

public class BOJ12931 {
    static int ans, maxMultiply;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            findAnswer(Integer.parseInt(st.nextToken()));
        }
        ans += maxMultiply;

        bw.write(String.valueOf(ans));
        bw.close();
        br.close();
    }

    static void findAnswer(int num) {
        String binaryString = Integer.toBinaryString(num);
        int bitCount = Integer.bitCount(num);
        maxMultiply = Math.max(maxMultiply, binaryString.length() - 1);
        ans += bitCount;
    }
}
```

