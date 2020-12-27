## 문제 원문 링크 : https://www.acmicpc.net/problem/2156

## Approach

DP로 풀 수 있는 문제이다.

> < 문제의 조건 >
>
> ​	1. 포도주 잔을 선택하면 그 잔에 들어있는 포도주는 모두 마셔야 하고, 마신 후에는 원래 위치에 다시 놓아야 한다.
>
> ​	2. 연속으로 놓여 있는 3잔을 모두 마실 수는 없다.
>
> ​	3. 최대로 많이 마시게끔 포도주를 마셔야 한다.

놓여있는 마지막 잔을 마실 경우,

​	1. ~ X O O 로 마시는 경우가 있다.

​	2. ~ O X O 로 마시는 경우가 있다.

놓여있는 마지막 잔을 마시지 않는 경우,

​	3. ~ O O X 로 마시는 경우가 있다.

​		(~ X O X 로 마시는 경우를 생각하지 않아도 되는 이유는 최대한 많이 마시는 것이 문제의 목표이므로,

​		예를 들어 ~ O O X O X 일 경우 보다 ~ O O X O O 인 것이 더 이득이기 때문이다.)

> 따라서, DP[i] = max(DP[i - 3] + arr[i - 1] + arr[i], DP[i - 2] + arr[i], DP[i - 1]) 이다.
>
> 앞에서부터 1번, 2번, 3번의 경우다.
>

## Code

```java
import java.io.*;

/*
    no.2156 : 포도주 시식
    hint : 1. n-1번째와 n번째 잔을 마시는 경우
           2. n-1번째 잔을 마시지 않고 n번째 잔을 마시는 경우
           3. n-1번째 잔을 마시고 n번째 잔을 마시지 않는 경우
 */

public class BOJ2156 {
    public static void dp(int[] arr, int n) throws IOException {
        int[] sum = new int[n];
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        if(n == 1){
            bw.write(arr[n-1] + "");
        } else if(n == 2){
            int s = arr[n-2] + arr[n-1];
            bw.write(s + "");
        } else {
            sum[0] = arr[0];
            sum[1] = arr[0] + arr[1];
            sum[2] = Math.max(Math.max(arr[0] + arr[1], arr[1] + arr[2]), arr[0] + arr[2]);
            for(int i = 3; i < n; i++){
                sum[i] = Math.max(Math.max(sum[i-3] + arr[i-1] + arr[i], sum[i-2] + arr[i]), sum[i-1]);
            }
            bw.write(sum[n-1] + "");
        }
        bw.flush();
        bw.close();

    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n];
        for(int i = 0; i < n; i++){
            arr[i] = Integer.parseInt(br.readLine());
        }

        dp(arr, n);

        br.close();
    }
}

```

