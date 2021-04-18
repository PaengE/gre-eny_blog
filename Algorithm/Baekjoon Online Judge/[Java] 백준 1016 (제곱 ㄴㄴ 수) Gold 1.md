## Problem : https://www.acmicpc.net/problem/1016

## Approach

> `에라토스테네스의 체` 라는 소수 판별 알고리즘을 알고 있으면 쉽게 풀 수 있는 문제이다.(주관적 생각)
>
> 개인적으로 나는 `에라토스테네스의 체`라는 알고리즘을 사용한다는 것을 바로 생각했고, 구현하는 데에도 큰 어려움이 있지 않았지만 Gold 1이라는 난이도가 매겨져있었다.
>
> 조건을 좀 따져야 하긴 한다.

조건을 먼저 살펴보자. (자료형은 무조건 long 으로... min의 최댓값이 int형의 범위를 벗어난다.)

min과 max를 입력으로 받는데, 그 값의 최대 값이 1,001,000,000 이다. 배열의 크기를 이만큼 선언한다면 메모리초과가 뜰 것이 당연하기에, `min <= max <= min + 1,000,000`을 이용하여 최대 1백만 + 1 크기의 배열만 선언하도록 하겠다.

1. 2 ~ max 까지의 제곱수들을 모두 구하여 `modNums`에 저장한다.
2. `modNums`에 있는 숫자들을 하나씩 꺼내어, min ~ max에 있는 숫자들이 나눠지는지를 판별해야 한다.
   - min 이상의 숫자 중, `modNum`으로 나눠지는 첫 번째 숫자를 찾아야 한다. (찾는 법은 생각하면 금방 떠오른다.)
   - `에라토스테네스의 체`를 활용하기 위함이며, 나눠지는 첫 번째 숫자를 찾았다면 그 숫자부터 `+ modNum`을 한 숫자들은 모두 나눠지므로 이를 표시한다.
3. min ~ max 를 표현한 배열에서 나눠지지 않는 숫자들의 개수를 센다.

> 2번 과정의 내용이 이해가 안 간다면 다음 예를 참고해보면 이해가 쉬울 것이다.

min = 1000,000 max = 1000100 이라면, 배열은 101크기면 된다. 1000000 이 배열[0]이 되는 것이고, 1000001 이 배열[1]이 된다. 마찬가지로 1000100 은 배열[100] 을 의미한다.

modNums에서 꺼낸 수가 9 라고 할 때, 9로 나눠지는 min(1,000,000)이상인 숫자는 1000008 이며 이는 배열[8]이다. 

배열[8], 배열[8+9], 배열[8+9+9], ... 는 1000008, 1000017, 100026, ... 이고 모두 9로 나눠진다.

따라서 이를 모두 나눠지는 수임을 표시하면 된다.

modNums에서 꺼낸 수가 4라면, 나눠지는 min이상 숫자는 1000000(배열[0])이고, 마찬가지로 1000004(배열[0+4]), 1000008(배열[0+4+4]) ... 를 나눠지는 수라고 표시한다.

## Code 

```java
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *  No.1016: 제곱 ㄴㄴ수
 *  URL: https://www.acmicpc.net/problem/1016
 *  Hint: 에라토스테네스의 체 + 자료형 조심
 */

public class BOJ1016 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        long min = Long.parseLong(st.nextToken());
        long max = Long.parseLong(st.nextToken());
        boolean[] ans = new boolean[(int) (max - min + 1)]; // false 가 나눠지지 않는 것

        ArrayList<Long> modNums = new ArrayList<>();
        for (long i = 2; i <= Math.sqrt(max); i++) {
            modNums.add(i * i);
        }

        for (long num : modNums) {
            // num 으로 나눠지는 min 이상인 첫번째 수를 찾음
            // 그 숫자가 위치하는 배열의 인덱스부터 +num 한 인덱스만 처리하면 되기때문에
            double t = (double) min / (double) num;
            long start = (long) ((num * Math.ceil(t)) - min);

            for (long i = start; i < ans.length; i += num) {
                ans[(int) i] = true;
            }
        }

        int count = 0;

        for (int i = 0; i < ans.length; i++) {
            if (!ans[i]) {
                count++;
            }
        }

        bw.write(String.valueOf(count));
        bw.close();
        br.close();
    }
}

```

