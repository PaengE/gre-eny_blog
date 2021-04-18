## Problem : https://www.acmicpc.net/problem/1517

## Approach

> `버블 소트`를 가장한 `머지 소트` 문제이다.
>
> `Merge Sort`를 구현하면서 `Bubble Sort`를 했을 때와 Swap의 관계를 찾아야 한다.

- 4 5 1 2 를 머지소트를 한다고 가정하자. left = 0, right = 3, mid = 1, 
  [4, 5]의 인덱스 i = 0, [1, 2]의 인덱스 j = 0 이라 할 때,

[1 2 4 5] 를 만드려면 4회의 swap 이 일어난다. 
(버블 소트에선 (5,1), (5,2) swap 하여 [4 1 2 5] 그리고 (4, 1), (4, 2) swap 하여 [1 2 4 5]를 만든다.

이는 각각 mid(1) - i(0) + 1 = 2, mid(1) - i(0) + 1 = 2 의 합이다.) 

머지소트에서 한 번 swap이 일어나면, 버블소트에선 두 위치의 차이 `mid - i + 1`만큼 swap이 발생한다.

이를 이용하여 swap 횟수를 카운팅하면 된다.

## Code

```java
import java.io.*;
import java.util.StringTokenizer;

/**
 *  No.1517: 버블 소트
 *  Hint: 버블 소트로 풀면 못품..
 *        버블소트와 머지소트의 스왑횟수 규칙을 찾아서 적용
 *        ex) 머지소트에선 한번 할걸 버블소트에선 두 위치의 차이 만큼 함
 */

public class BOJ1517 {
    static int[] arr, res;
    static long count = 0;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());
        arr = new int[n];
        res = new int[n];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        merge(0, n - 1);

//        for (int i = 0; i < n; i++) {
//            System.out.print(res[i] + " ");
//        }
//        System.out.println();

        bw.write(String.valueOf(count));
        bw.close();
        br.close();

    }

    static void merge(int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            // Divide
            merge(left, mid);
            merge(mid + 1, right);
            // Conquer
            mergeSort(left, right);
        }
    }

    static void mergeSort(int left, int right) {
        int mid = (left + right) / 2;
        int i = left;
        int j = mid + 1;
        int k = left;

        // 정렬
        while (i <= mid && j <= right) {
            if (arr[i] > arr[j]) {
                res[k++] = arr[j++];
                count += (mid - i) + 1;
            } else {
                res[k++] = arr[i++];
            }
        }


        if (i > mid) {  // 오른쪽 배열이 남은 경우 뒤에 이어서 붙임
            while (j <= right) {
                res[k++] = arr[j++];
            }
        } else {    // 왼쪽 배열이 남은경우 뒤에 이어서 붙임
            while (i <= mid) {
                res[k++] = arr[i++];
            }
        }

        // 정렬된 배열 update
        for (int l = left; l <= right; l++) {
            arr[l] = res[l];
        }
    }
}
```

