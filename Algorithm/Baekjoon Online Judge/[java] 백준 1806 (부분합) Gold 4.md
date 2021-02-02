## Problem : https://www.acmicpc.net/problem/1806

## Approach

> 한쪽에서 시작하는 투포인터 문제이다.
>
> 연속된 수의 합이 S이상이 되는 것 중, 가장 짧은 길이를 찾는 문제이다.

start와 end를 모두 배열의 처음부터 시작한다.(start = 0, end = 0)

나는 Index 처리 때문에 배열의 크기를 n+1로 지정해주었다.

1. start와 end가 같다면,
   1. sum이 S이상이라면, 최소 길이는 1이다. 1보다 짧은 길이는 찾을 수 없으므로 break한다.
   2. sum이 s미만이라면, end++ 후 sum에 arr[end]를 더한다.
2. start와 end가 다르다면, (여기서는 항상 start < end)
   1. sum이 S이상이라면, answer을 갱신한다.(end - start + 1과 answer중 최솟값으로)
      1. sum에서 arr[start]를 빼고, sum과 s를 비교한다.
      2. 그래도 sum이 S이상이라면, `2-1`을 반복한다. (반복 후엔 sum이 S미만이 될 것이 된다.)
   2. end++ 후 sum에 arr[end]를 더한다.

위 과정을 start나 end가 `배열의 길이 - 1`가 되기 전까지 반복한 후, 종료한다.

## Code

```java
import java.io.*;
import java.util.StringTokenizer;

/**
 *  No.1806: 부분합
 *  hint: 한쪽에서 시작하는 투 포인터 연습문제
 */

public class BOJ1806 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());
        int[] arr = new int[n + 1];
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        int start = 0, end = 0;
        int answer = 0;
        int sum = arr[0];
        while (true) {
            if (start == end) {
                if (sum >= s) {
                    answer = 1;
                    break;
                } else {
                    sum += arr[++end];
                }
            } else {
                if (sum >= s) {
                    answer = answer == 0 ? end - start + 1 : Math.min(answer, end - start + 1);
                    while (sum - arr[start] >= s && start <= end) {
                        sum -= arr[start];
                        start++;
                        answer = Math.min(answer, end - start + 1);
                    }
                }
                sum += arr[++end];
            }

            if (start == arr.length - 1 || end == arr.length - 1) {
                break;
            }
        }

        bw.write(String.valueOf(answer));
        bw.close();
        br.close();
    }
}

```

