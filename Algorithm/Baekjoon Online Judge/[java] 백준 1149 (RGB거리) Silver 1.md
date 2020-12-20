## 문제 원문 링크: https://www.acmicpc.net/problem/1149

## Approach

DP문제이다.

DP[i][j] = i번째 집을 j색으로 칠했을 때의 최소비용값이라 할 때,

Min(DP[N][빨강], DP[N][초록], DP[N][파랑]) 을 구하면 되는 문제이다.

연속된 집을 같은 색깔로 칠할 수는 없으므로 밑의 점화식을 적용시킬 수 있다.

> DP[i][j] = Min(DP[i - 1][(j + 1) % 3], DP[i - 1][(j + 1) % 3])  

## Code

```java
/*
    no.1149 : RGB거리
    hint : 마지막 집을 R, G, B로 칠할 때의 3가지 경우를 구한 후 그 중 최솟값을 선택
 */

import java.io.*;
import java.util.StringTokenizer;

public class BOJ1149 {
    static int[][] arr, resArr;
    static int n;

    public static void dp(){
        resArr[0][0] = arr[0][0];
        resArr[0][1] = arr[0][1];
        resArr[0][2] = arr[0][2];
        for(int i = 1; i < n; i++){
            resArr[i][0] = arr[i][0] + Math.min(resArr[i-1][1], resArr[i-1][2]);
            resArr[i][1] = arr[i][1] + Math.min(resArr[i-1][0], resArr[i-1][2]);
            resArr[i][2] = arr[i][2] + Math.min(resArr[i-1][0], resArr[i-1][1]);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        String s;

        n = Integer.parseInt(br.readLine());
        arr = new int[n][3];
        resArr = new int[n][3];

        for(int i = 0; i < n; i++){
            s = br.readLine();
            StringTokenizer st = new StringTokenizer(s);
            for(int j = 0; j < 3; j++){
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        dp();

        bw.write(Math.min(Math.min(resArr[n-1][0], resArr[n-1][1]), resArr[n-1][2]) + "");
        bw.flush();
        br.close();
        bw.close();
    }
}

```

