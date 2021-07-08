## Problem : https://www.acmicpc.net/problem/1201

## Approach

> 알고 보면 쉬운데 그걸 알기까지가 어려웠던 `Greedy` 문제이다.



먼저 유효하지 않은 n, m, k를 찾아보자.

- `(m + k) - 1 > n`: 1 ~ n 까지의 숫자가 모두 증가하는 수열 혹은 감소하는 수열에 속한다고 가정할 때, 두 수열을 더했을 때의 길이 m + k = n + 1이다. 
  (두 수열은 적어도 하나의 숫자를 공유하기 때문이다.)
  그렇기 때문에 (m + k) - 1 > n 라면 길이 m의 증가수열 + 길이 k의 감소수열은 만들 수 없다.

![1201-1](C:\Users\82102\OneDrive\티스토리\Algorithm\Baekjoon Online Judge\image\1201-1.PNG)



각 수열을 구하는 방법은 다음과 같다.

- 길이 m의 증가수열을 만들려면 `1 ~ n` 까지의 숫자를 m 개의 그룹으로 나눈 뒤, 각 그룹을 뒤집으면 된다. 뒤집힌 각 그룹에서 하나씩 숫자를 뽑아보면 길이 m의 증가수열인 것을 확인할 수 있다.
- 그리고 길이 k의 감소수열을 만들려면 그 그룹의 크기가 k인 그룹이 적어도 하나 있어야 한다. 최대 크기 그룹의 요소들을 보면 길이 k의 감소수열을 확인할 수 있다.
  (나머지 그룹의 크기는 k보다 크지만 않으면 된다.)

![1201-2](C:\Users\82102\OneDrive\티스토리\Algorithm\Baekjoon Online Judge\image\1201-2.PNG)



위 방법에서 유효하지 않은 n, m, k를 하나 더 찾을 수 있다.

- `m * k < n`그룹의 최대 크기가 k인 m개의 그룹을 만들 때, 모든 그룹 크기의 최대 합은 (m * k)이다. 
  (m개의 그룹 모두 k크기의 그룹일 때)

  그 최대 합보다 n이 더 크면 길이 m의 증가수열 + 길이 k의 감소수열은 만들 수 없다.

![1201-3](C:\Users\82102\OneDrive\티스토리\Algorithm\Baekjoon Online Judge\image\1201-3.PNG)



## Code

```java
import java.io.*;
import java.util.StringTokenizer;

/**
 *  No.1201: NMK
 *  Hint: Greedy
 */

public class BOJ1201 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        bw.write(solve(n, m, k));
        bw.close();
        br.close();
    }

    static String solve(int n, int m, int k) {
        if (m + k - 1 > n || m * k < n) {
            return "-1";
        }

        int[] groupSize = new int[m];
        groupSize[0] = k;   // 첫번째 그룹의 크기는 k
        n -= k;

        if (m - 1 > 0) {
            int div = n / (m - 1);  // 나머지 그룹의 기본 크기
            int remain = n % (m - 1);   // 균등하게 나누어지지 못한 부분을 나머지 그룹에 재분배하기 위함

            for (int i = 1; i < m; i++) {
                if (remain-- > 0) {
                    groupSize[i] = div + 1;
                } else {
                    groupSize[i] = div;
                }
            }
        }

        // 순열 구하기
        StringBuilder sb = new StringBuilder();
        int sIdx = 0, eIdx = 0;
        for (int i = 0; i < m; i++) {
            sIdx = eIdx;
            eIdx += groupSize[i];
            for (int j = eIdx - 1; j >= eIdx - groupSize[i]; j--) {
                sb.append((j + 1) + " ");
            }
        }

        return sb.toString();
    }
}
```

