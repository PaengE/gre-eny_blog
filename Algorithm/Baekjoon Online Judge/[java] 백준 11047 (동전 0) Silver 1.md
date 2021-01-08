## Problem : https://www.acmicpc.net/problem/11047

## Approach

주어지는 동전의 가치가 항상 배수관계이므로, 동전의 개수가 최소가 되게끔 동전을 사용하려면 동전의 가치가 큰 것부터 동전을 구성해나가면 된다. 

예를들어, 500원 2개보단 1000원 1개 사용하는게 개수가 더 적다.(주어지는 동전의 가치들이 항상 배수관계이므로 가능함)

다른 테크닉은 필요하지 않는 간단한 문제이다

## Code

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/*
    no.11047 : 동전 0
    hint : 큰 숫자부터 나누기
 */

public class BOJ11047 {
    private static void greedy(int n, int k, int[] coin){
        int count = 0;
        for(int i = n-1; i >= 0; i--){
            if(k == 0){
                break;
            }else if(k / coin[i] != 0){
                count += k / coin[i];
                k %= coin[i];
            }
        }
        System.out.println(count);
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int[] coin = new int[n];
        for(int i = 0; i < n; i++){
            coin[i] = Integer.parseInt(br.readLine());
        }
        br.close();

        greedy(n, k, coin);
    }
}
```

