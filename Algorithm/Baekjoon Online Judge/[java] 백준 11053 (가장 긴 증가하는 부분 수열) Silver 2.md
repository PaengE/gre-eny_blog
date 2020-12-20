## 문제 원문 링크 : https://www.acmicpc.net/problem/11053

## Approach

LIS(Longest Increasing Subsequence)는 Backtracking을 이용한 DP 방식과 이분탐색 방식이 가능하다.

DP 방식은 O(N^2) 의 시간복잡도를 가지며, 이분 탐색은 O(NlogN)의 시간복잡도를 가진다.

이 문제에서는 입력이 1000보다 작으므로 N^2의 시간복잡도 풀이인 DP방식으로도 풀이가 가능하다.

i번째 인덱스의 값과 0~i-1번째 인덱스의 값들과 각각 비교하면서 i번째 인덱스의 값이 더 크다면

DP[i] = max(DP[i], DP[j] + 1) (j = 0 ~ i - 1) 의 점화식을 활용하면 된다.

후에 DP배열을 오름차순으로 정렬한 후 제일 뒤에 있는 값이 답이게 된다.

## Code

```java
import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;
/*
    no.11053 : 가장 긴 증가하는 수열
    hint : n번째 요소를 n-1, n-2, ... , 0번째 요소들 모두와 비교해 봐야 함
           비교하고 난 뒤 저장 할 때 DP를 이용
 */

public class BOJ11053 {
    public static void dp(int[] arr, int n) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int[] res = new int[n];
        Arrays.fill(res, 1);

        for (int i = 1; i < n; i++) {
            for (int j = i - 1; j >= 0; j--){
                if(arr[i] > arr[j]){
                    res[i] = Math.max(res[i], (res[j] + 1));
                }
            }
        }

      // sort를 하는 이유는 최대값을 구하기 위해서이다.
      // sort를 하지않고 위의 이중for문에서 최대값을 갱신해 주어도 된다.
        Arrays.sort(res);
        bw.write(res[n-1] + "");
        bw.flush();
        bw.close();
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n];

        String s = br.readLine();
        StringTokenizer st = new StringTokenizer(s);
        for(int i = 0; i < n; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        dp(arr, n);

        br.close();
    }
}

```



