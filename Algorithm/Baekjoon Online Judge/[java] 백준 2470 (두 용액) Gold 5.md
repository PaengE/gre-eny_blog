## Problem : https://www.acmicpc.net/problem/2470

## Approach

> 투포인터를 활용하여 풀었다.
>
> 주어진 용액들 중 두 용액을 합쳐 특성값이 0에 가장 가까운 혼합 용액을 만드는 문제이다.
>
> 주어진 용액들의 특성값은 중복값이 없다.

따라서 나는 우선 주어진 용액들의 특성값을 오름차순으로 정렬했다.

그런후 start = 0, end = length - 1로 정한 후,범위를 좁혀나가며 용액의 특성값들을 계산했다.

1. abs(arr[start] + arr[end]) 를 계산한 뒤, nearest보다 작으면 max와 min을 갱신한다.
2. arr[start] + arr[end] 이 0보다 크면, 0에 더 가깝게 해주기위해 end--를 한다.
3. arr[start] + arr[end] 이 0보다 작으면, 0에 더 가깝게 해주기위해 start++를 한다.
4. arr[start] + arr[end] 이 0과 같으면, 더이상 탐색이 무의미하므로 break를 한다.
5. 위의 과정을 start >= end 가 될 때까지 반복한 후, min 과 max를 출력해준다.

## Code

```java
import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 *  No.2470: 두 용액
 *  hint: 투 포인터 연습문제
 */

public class BOJ2470 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(arr);
        int start = 0, end = arr.length - 1;
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        int nearest = Integer.MAX_VALUE;

        while (start < end) {
            int abs = Math.abs(arr[start] + arr[end]);
            if (abs < nearest) {
                nearest = Math.min(nearest, abs);
                min = arr[start];
                max = arr[end];
            }

            if (arr[start] + arr[end] > 0) {
                end--;
            } else if (arr[start] + arr[end] < 0) {
                start++;
            } else {
                break;
            }
        }
        bw.write(min + " " + max);
        bw.close();
        br.close();
    }
}

```

