## 문제 원문 링크 : https://www.acmicpc.net/problem/12738

## Approach

LIS(Longest Increasing Subsequence)는 Backtracking을 이용한 DP 방식과 이분탐색 방식이 가능하다.

DP 방식은 O(N^2) 의 시간복잡도를 가지며, 이분 탐색은 O(NlogN)의 시간복잡도를 가진다.

이 문제에서는 입력의 크기가 최대 1,000,000 이므로 DP를 이용한 풀이는 시간이 너무 오래걸리므로 이분탐색을 이용한 풀이를 사용하여야 한다.

> 이 문제는 [가장 긴 증가하는 부분 수열 2](https://www.acmicpc.net/problem/12015) 문제와 거의 동일하다.
>
> 수열의 구성요소가 음수가 될 수 있다는 차이가 있지만, 이분 탐색을 이용한다면 같은 코드로 테스트 통과가 가능하다.

- 10 20 1 2 3 4 가 입력으로 주어 졌을 때,

  1. 10 입력 ▶ [0 10] 

  2. 20 입력 ▶ [0 10 20]

  3. 1 입력 ▶ [0 1 20]

  4. 2 입력 ▶ [0 1 2] → 현재까지 입력받은 결과로는 [10 20] , [1 2] 두 개의 조합이 존재한다.

  ​                **[10 20]을 덮어 써도 되는 이유는 [1 2] 의 경우는 앞으로 [10 20] 뒤에 나올 경우를 포함하고 있기 때문이다.**

  5. 3 입력 ▶ [0 1 2 3]

  6. 4 입력 ▶ [0 1 2 3 4]

     -> 답은 list.size() - 1이 답이 된다.

- 1 5 10 4 가 입력으로 주어 졌을 때,

  1. 1 입력 ▶ [0 1]

  2. 5 입력 ▶ [0 1 5]

  3. 10 입력 ▶ [0 1 5 10]

  4. 4 입력 ▶ [0 1 4 10]

     -> 답은 list.size() - 1이다. 그러나 이것은 LIS 의 최대 길이이지 LIS를 구성하는 요소는 아니다.

## Code

```java
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * no.12738 : 가장 긴 증가하는 부분 수열 3 (LIS)
 * hint : 아무 자료구조 사용하여도 무방 -> ArrayList 사용
 *        1. 리스트의 마지막 보다 크면 삽입.
 *        2. 리스트의 마지막 보다 작으면 리스트 이진탐색으로 적절한 위치에 삽입.
 */

public class BOJ12738 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        ArrayList<Integer> list = new ArrayList<>();
        list.add(0);

        int n = Integer.parseInt(br.readLine());
        int val = 0;

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            val = Integer.parseInt(st.nextToken());

            if (list.get(list.size() - 1) < val) {
                list.add(val);
            } else {
                int left = 1;
                int right = list.size() - 1;

                while (left < right) {
                    int mid = (left + right) / 2;
                    if (list.get(mid) < val){
                        left = mid + 1;
                    } else {
                        right = mid;
                    }
                }
                list.set(right, val);
            }
        }

        bw.write((list.size() - 1) + "");
        bw.flush();
        br.close();
        bw.close();
    }
}
```

