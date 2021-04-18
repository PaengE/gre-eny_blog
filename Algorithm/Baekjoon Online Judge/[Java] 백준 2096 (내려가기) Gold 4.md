## Problem : https://www.acmicpc.net/problem/2096

## Approach

> 간단한 DP 문제로 생각하고 풀었다.

DP의 점화식은 문제에서 알려주고 있기 때문에 따로 언급은 하지 않겠다.

- 최솟값 배열과 최댓값 배열을 따로 두어, 한 번 순회하며 두 값을 각각 갱신하였다.

마지막 줄을 검사하여 최종최댓값과 최종최솟값을 구하면 된다.

## Code

```java
import java.io.*;
import java.util.StringTokenizer;

/**
 *  No.2096: 내려가기
 *  URL: https://www.acmicpc.net/problem/2096
 *  Hint: DP
 */

public class BOJ2096 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());
        int[][] arr = new int[n][3];

        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int[][] dpMin = new int[n][3];
        int[][] dpMax = new int[n][3];
        dpMin[0][0] = dpMax[0][0] = arr[0][0];
        dpMin[0][1] = dpMax[0][1] = arr[0][1];
        dpMin[0][2] = dpMax[0][2] = arr[0][2];

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < 3; j++) {
                if (j == 0) {
                    dpMin[i][j] = Math.min(dpMin[i - 1][0], dpMin[i - 1][1]) + arr[i][j];
                    dpMax[i][j] = Math.max(dpMax[i - 1][0], dpMax[i - 1][1]) + arr[i][j];
                } else if (j == 2) {
                    dpMin[i][j] = Math.min(dpMin[i - 1][1], dpMin[i - 1][2]) + arr[i][j];
                    dpMax[i][j] = Math.max(dpMax[i - 1][1], dpMax[i - 1][2]) + arr[i][j];
                } else {
                    dpMin[i][j] = Math.min(Math.min(dpMin[i - 1][0], dpMin[i - 1][1]), dpMin[i - 1][2]) + arr[i][j];
                    dpMax[i][j] = Math.max(Math.max(dpMax[i - 1][0], dpMax[i - 1][1]), dpMax[i - 1][2]) + arr[i][j];
                }
            }
        }

        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < 3; i++) {
            max = Math.max(dpMax[n - 1][i], max);
            min = Math.min(dpMin[n - 1][i], min);
        }
        bw.write(String.valueOf(max) + " " + String.valueOf(min));
        bw.flush();
        bw.close();
        br.close();
    }
}
```

