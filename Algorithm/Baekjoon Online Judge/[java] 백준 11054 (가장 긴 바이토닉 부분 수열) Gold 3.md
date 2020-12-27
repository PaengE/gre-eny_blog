## 문제 원문 링크 : https://www.acmicpc.net/problem/11054

## Approach

> 바이토닉 수열 : 수열 S가 어떤 수 Sk를 기준으로 S1 < S2 < ... Sk-1 < Sk > Sk+1 > ... SN-1 > SN을 만족하는 수열이다.

LIS(Longest Increasing Subsequence)를 응용한 문제이다.

앞에서부터 LIS의 길이를 구하여 저장하고, 뒤에서부터 LIS의 길이를 구하여 저장한 후, 같은 위치에 있는 두 저장한 값을 더하여 가장 큰 것이 가장 긴 바이토닉 수열의 길이이다.

#### LIS의 기본 문제는 밑의 링크이며, LIS로 이 블로그에 tag 검색을 하면 다른 LIS관련 문제들에 대한 나의 풀이를 볼 수 있다.

[2020/12/19 - \[Algorithm/Baekjoon Online Judge\] - \[java\] 백준 11053 (가장 긴 증가하는 부분 수열) Silver 2](https://gre-eny.tistory.com/21)

## Code

```java
import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;
/*
    no.11054 : 가장 긴 바이토닉 부분 수열
    hint : 현재 위치 요소가
        1. 왼쪽부터 시작하면 몇번째 증가하는 수열인지
        2. 오른쪽부터 시작하면 몇뻔재 증가하는 수열인지
 */

public class BOJ11054 {
    public static void dp(int[] arr, int n) throws IOException{
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int[] res1 = new int[n];
        int[] res2 = new int[n];
        Arrays.fill(res1, 1);
        Arrays.fill(res2, 1);
        for (int i = 1; i < n; i++){
            for(int j = i - 1; j >= 0; j--){
                if(arr[i] > arr[j]){
                    res1[i] = Math.max(res1[i], res1[j] + 1);
                }
            }
        }
        for (int i = n - 2; i >= 0; i--){

            for(int j = i + 1; j < n; j++){
                if (arr[i] > arr[j]) {
                    res2[i] = Math.max(res2[i], res2[j] + 1);
                }
            }
        }

        for (int i = 0; i < n; i++){
            res1[i] = res1[i] + res2[i];
        }
        Arrays.sort(res1);
        bw.write((res1[n-1] - 1) + "");
        bw.flush();
        bw.close();
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        String s = br.readLine();
        StringTokenizer st = new StringTokenizer(s);
        int[] arr = new int[n];
        for(int i = 0; i < n; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        dp(arr, n);

        br.close();
    }
}

```

