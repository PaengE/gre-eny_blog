## Problem : https://www.acmicpc.net/problem/4902



## Approach

> `누적 합` 개념과 `구현`이 합쳐진 문제였다.



먼저 `누적 합 배열 preSum`을 구성한다. preSum[i][j] 는 i행의 0 ~ j 열까지의 누적합을 저장한다.

문제에서는 정삼각형을 나타내지만 배열에 저장할 때는 아래 그림처럼 직각삼각형처럼 저장된다.

![4902-1](C:\Users\82102\OneDrive\티스토리\Algorithm\Baekjoon Online Judge\image\4902-1.PNG)

이제 `파란 삼각형`과 `빨간 삼각형`처럼 모든 만들 수 있는 삼각형의 합을 구하여 그 중 최댓값을 구해야 한다.

`파란 삼각형`의 최대 크기는 `n`이다. `빨간 삼각형`의 최대 크기는 `n / 2`이다.

`파란 삼각형`의 경우, 모든 (i, j)를 윗 꼭짓점으로 하는 1 ~ n 크기 정삼각형의 합을 구하면 된다.

`빨간 삼각형`의 경우, 모든 (i, j)를 아랫 꼭짓점으로 하는 1 ~ n / 2 크기 역삼각형의 합을 구하면 된다.

위 로직으로 삼각형의 합을 구한다면, 밑의 코드처럼 간단하게 구현할 수 있다. 



`빨간 삼각형`의 경우가 구현이 어려웠던 문제였다.



## Code

```java
import java.io.*;
import java.util.StringTokenizer;

/**
 *  No.4902: 삼각형의 값
 *  Hint: 누적합 + 구현
 */

public class BOJ4902 {
    static int n;
    static int[][] arr, preSum;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        String s = "";
        int tc = 1;
        StringBuilder sb = new StringBuilder();
        while (!(s = br.readLine()).equals("0")) {
            StringTokenizer st = new StringTokenizer(s);
            n = Integer.parseInt(st.nextToken());
            arr = new int[n + 1][2 * n];
            preSum = new int[n + 1][2 * n];

            // preSum 구성
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= 2 * i - 1; j++) {
                    arr[i][j] = Integer.parseInt(st.nextToken());
                    preSum[i][j] = arr[i][j] + preSum[i][j - 1];
                }
            }
            int max = Integer.MIN_VALUE;

            // 정삼각형 부분 (i, j)를 위쪽 꼭지점으로 함
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= 2 * i - 1; j += 2) {
                    // (i, j)를 위 꼭지점으로 하여 만들 수 있는 최대 정삼각형의 크기 = n - i
                    for (int k = 0, triangleSum = 0; k < n - i + 1; k++) {    // (i + k) 크기의 삼각형 합
                        triangleSum += preSum[i + k][j + 2 * k] - preSum[i + k][j - 1];
                        max = Math.max(max, triangleSum);
                    }
                }
            }

            // 역삼각형 부분 (i, j)를 아래쪽 꼭지점으로 함
            for (int i = n; i >= 1; i--) {
                for (int j = 2; j <= 2 * i - 1; j += 2) {
                    // (i, j) 를 아래 꼭지점으로 하여 만들 수 있는 최대 삼각형의 크기 = min(j / 2, i - j / 2)
                    for (int k = 0, triangleSum = 0; k < Math.min(j / 2, i - j / 2); k++) {   //
                        triangleSum += preSum[i - k][j] - preSum[i - k][j - 2 * k - 1];
                        max = Math.max(max, triangleSum);
                    }
                }
            }

            sb.append(tc++ + ". " + max + "\n");
        }

        bw.write(sb.toString());
        bw.close();
        br.close();
    }
}
```

