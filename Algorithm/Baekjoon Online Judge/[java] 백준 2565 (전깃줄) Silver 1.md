## 문제 원문 링크 : https://www.acmicpc.net/problem/2565

## Approach

언뜻 보기에 어려워 보이는 문제이지만, LIS로 간단히 풀리는 문제이다.

일단 왼쪽 기둥을 기준으로 입력을 정렬시킨다. 그런 후, LIS의 길이를 DP배열에 저장한다.

그림에서의 DP배열의 요소는 {1, 1, 0, 2, 0, 3, 4, 1, 2, 5} 이다.

이 문제에서 LIS는 줄이 겹치지 않고 오른쪽기둥 기준 오름차순으로 연결돼 있는 줄의 개수이다.

## Code

```java
import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

/*
    no.2565 : 전깃줄
    hint : 왼쪽 전봇대를 정렬 후 생각해보기
           LIS(Longest Increasing Subsequence) Problem
 */

public class BOJ2565 {
    public static void dp(int[][] arr, int n) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int[] dp = new int[n];


        Arrays.fill(dp, 1);
        for(int i = 1; i < n; i++){
            for(int j = i-1; j >= 0; j--){
                if(arr[i][1] > arr[j][1]){
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        Arrays.sort(dp);

        bw.write((n-dp[n-1]) + "");

        bw.flush();
        bw.close();
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[][] arr = new int[n][2];
        String s;
        StringTokenizer st;
        for(int i = 0; i < n; i++){
            s = br.readLine();
            st = new StringTokenizer(s);
            arr[i][0] = Integer.parseInt(st.nextToken());
            arr[i][1] = Integer.parseInt(st.nextToken());
        }

      	// 왼쪽 기둥 기준 정렬
        Arrays.sort(arr, Comparator.comparingInt(o1 -> o1[0]));

        dp(arr, n);

        br.close();
    }
}

```

