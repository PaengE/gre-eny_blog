## Problem : https://www.acmicpc.net/problem/2143

## Approach

> `이분탐색` 문제이다.
>
> 먼저 `A 배열` 과 `B 배열`의 요소들로 부 배열의 합들을 구하여 각각 `aSum`, `bSum` 에 저장한다.
>
> 그런 후, `aSum`에 있는 각 요소들에 대하여 더해서 T가 되는 요소를 `bSum`에서 구할 것이다. 

그러기 위해 일단 `bSum`을 정렬한다. 

`aSum`의 각 요소들에 대해 `bSum`에서 합이 T가 되는 요소가 여러 개일 수 있으므로, `lower_bound` 와 `upper_bound` 를 구하여 그 사이 범위만큼 ans에 더해주면 된다.

정확하게 T를 만들지 못한다면 무시해도 된다.

C++ 에서는 lower_bound 와 upper_bound 가 STL 라이브러리로 지원된다고 하나, 자바에서는 아직 지원하고 있지 않기때문에, 직접 구현해줘야 한다.

## Code

```java
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 *  No.2143: 두 배열의 합
 *  URL: https://www.acmicpc.net/problem/2143
 *  Hint: 이분탐색 or 투포인터
 */

public class BOJ2143 {
    static long t, ans;
    static int n, m;
    static ArrayList<Long> aSum, bSum;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        t = Long.parseLong(br.readLine());

        n = Integer.parseInt(br.readLine());
        int[] a = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(st.nextToken());
        }

        m = Integer.parseInt(br.readLine());
        int[] b = new int[m];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) {
            b[i] = Integer.parseInt(st.nextToken());
        }

        initSum(a, b);

        // aSum의 각각 요소로 합이 t가 되는 요소를 bSum에서 찾는다.
        for (long ae : aSum) {
            binarySearch(0, bSum.size() - 1, ae);
        }

        bw.write(String.valueOf(ans));
        bw.close();
        br.close();
    }

    // 이분 탐색
    static void binarySearch(int left, int right, long x) {
        if (left > right) {
            return;
        }

        int mid = (left + right) / 2;
        long sum = x + bSum.get(mid);

        // sum 을 t와 같게 만드는 bSum의 요소가 여러개일 수 있으니 upperBound, lowerBound를 구해야함
        if (sum == t) {
            ans += (upperBound(0, bSum.size() - 1, bSum.get(mid)) - lowerBound(0, bSum.size() - 1, bSum.get(mid)));
        } else if (sum < t) {
            binarySearch(mid + 1, right, x);
        } else {
            binarySearch(left, mid - 1, x);
        }
    }

    // x 보다 큰값이 나타나는 첫번째 인덱스
    static long upperBound(int left, int right, long x) {
        right++;
        while (left < right) {
            int mid = (left + right) / 2;
            if (bSum.get(mid) <= x) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }

    // x 값이 나오는 첫번째 인덱스
    static long lowerBound(int left, int right, long x) {
        right++;
        while (left < right) {
            int mid = (left + right) / 2;
            if (bSum.get(mid) < x) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return right;
    }


    // a와 b의 부분 합을 각각 aSum, bSum에 저장.
    static void initSum(int[] a, int[] b) {
        aSum = new ArrayList<>();
        bSum = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            long tmp = a[i];
            aSum.add(tmp);
            for (int j = i + 1; j < n; j++) {
                tmp += a[j];
                aSum.add(tmp);
            }
        }

        for (int i = 0; i < m; i++) {
            long tmp = b[i];
            bSum.add(tmp);
            for (int j = i + 1; j < m; j++) {
                tmp += b[j];
                bSum.add(tmp);
            }
        }

        // bSum을 기준으로 이분탐색을 할 것이므로 bSum은 정렬되어 있어야 한다.
        Collections.sort(bSum);
    }
}
```