## Problem : https://www.acmicpc.net/problem/1450

## Approach

> meet in the middle 알고리즘을 적용하여 각각을 완전탐색 후 이진탐색을 수행하는 문제이다.
>
> meet in the middle 알고리즘을 간단하게 말하면 주어진 범위를 반으로 나누어 각각 처리하는 알고리즘이다.

문제에서 주어진 N이 최대 30이므로 완전탐색을 수행하려면 2^30번의 연산이 필요하다.

2^30은 대략 10억이지만, 절반인 2^15은 약 32000이다. 따라서 반으로 나누어 완전탐색을 하더라도 2*2^15만큼의 연산만 필요하므로, 10억과 64000은 시간적인 면에서 엄청난 차이를 낸다.

문제의 접근법은 다음과 같다.

> 1. N을 반으로 나누어 START ~ N / 2, N / 2 ~ END 범위에서 각각 완전탐색을 하여 만들 수 있는(C이하) 모든 무게를 찾아 각각 배열(aSum, bSum)에 담는다.
> 2. aSum, bSum 중 하나를 기준으로 이진탐색을 수행하여야 하기에 기준이 아닌 다른 배열을 오름차순으로 정렬한다.
> 3. aSum 각각의 모든 무게에 대해 bSum의 무게중 하나를 합쳐서 C보다 작은 bSum에서의 인덱스를 찾는다.
>    - 찾았다 하더라도 start > end가 될 때까지 계속해서 이진탐색을 진행해 나간다.
>    - 그 이유는, aSum의 무게 + bSum의 무게가 C이하지만 최대로 되는 인덱스를 찾으면 bSum의 0번째 부터 찾은 인덱스까지는 모두 aSum의 무게와 더했을 때 C이하이기 때문이다.

## Code

```java
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 *  No.1450: 냅색문제
 *  hint: meet in the middle (반으로 나누어서 처리)
 */

public class BOJ1450 {
    static int n, c, index;
    static int[] arr;
    static ArrayList<Integer> aSum, bSum;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        arr = new int[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        aSum = new ArrayList<>();
        bSum = new ArrayList<>();

        aBruteForce(0, 0);
        bBruteForce(n / 2, 0);
        Collections.sort(bSum);

        int ans = 0;
        for (int i = 0; i < aSum.size(); i++) {
            index = -1;
            binarySearch(0, bSum.size() - 1, aSum.get(i));
            ans += index + 1;
        }

        bw.write(String.valueOf(ans));
        bw.close();
        br.close();
    }

    static void aBruteForce(int i, int sum) {
        if (sum > c) {
            return;
        }
        if (i == n / 2) {
            aSum.add(sum);
            return;
        }
        aBruteForce(i + 1, sum + arr[i]);
        aBruteForce(i + 1, sum);
    }

    static void bBruteForce(int i, int sum) {
        if (sum > c) {
            return;
        }
        if (i == n) {
            bSum.add(sum);
            return;
        }
        bBruteForce(i + 1, sum + arr[i]);
        bBruteForce(i + 1, sum);
    }

    static void binarySearch(int start, int end, int val) {
        if (start > end) {
            return;
        }

        int mid = (start + end) / 2;

        if (bSum.get(mid) + val <= c) {
            index = mid;
            binarySearch(mid + 1, end, val);
        } else {
            binarySearch(start, mid - 1, val);
        }
    }
}

```

