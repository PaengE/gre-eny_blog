## Problem : https://www.acmicpc.net/problem/2568

## Approach

> 겉으로는 그렇게 보이지 않을지도 모르지만 LIS (Longest Increase Subsequence) 문제이다.
>
> 한 쪽 전봇대를 오름차순으로 정렬한 뒤, 최대 LIS의 요소가 아닌 전깃줄들을 제거하면 된다.
>
> LIS 는 시간제한에 따라(입력데이터의 범위에 따라) DP 혹은 이분탐색으로 풀이가 가능한데, 여기서는 이분탐색으로 풀이를 해야한다.
>
> - 이분 탐색을 이용하여 LIS를 구한다.
> - 전깃줄이 LIS의 몇번째 요소인지를 저장하는 배열을 따로 두어 Tracing을 한다.

우선 A 전봇대를 오름차순으로 정렬한다. 그리고 전깃줄의 번호가 작은 것부터 꺼내어 이분탐색을 시작한다.

(list 의 크기 - 1) 이 최대 LIS의 길이이다. 그러나 list의 각요소가 항상 LIS를 이루지는 않는다. LIS 를 최대크기로 구하기 위하여 list 의 각 요소를 가능한 작은 값으로 유지하기 때문이다.

따라서 현재 전깃줄이 자기가 속한 LIS의 몇번째 요소인지를 저장한다. 

마지막에 전깃줄부터 역순으로 검사하며 Tracing을 진행한다. 번호가 작은 전깃줄부터 출력해야하므로 Stack을 이용한다.

## Code

```java
import java.io.*;
import java.util.*;

/**
 *  No.2568: 전깃줄 - 2
 *  URL: https://www.acmicpc.net/problem/2568
 *  Hint: LIS + BinarySearch + Tracing
 */

public class BOJ2568 {
    static int m;
    static int[][] pole;
    static int[] preIndex;  // i번째 전깃줄이 LIS의 몇번째 요소인지를 저장
    static ArrayList<Integer> list; // list.size() - 1 크기가 LIS의 최대크기 (list의 요소가 항상 LIS는 아님)
    static StringBuilder sb = new StringBuilder();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        m = Integer.parseInt(br.readLine());
        pole = new int[m][2];
        preIndex = new int[m];
        list = new ArrayList<>();
        list.add(0);    // 비교를 위해 0을 삽입

        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            pole[i][0] = Integer.parseInt(st.nextToken());
            pole[i][1] = Integer.parseInt(st.nextToken());
        }

        // A 기둥 기준 오름차순 정렬
        Arrays.sort(pole, Comparator.comparingInt(o -> o[0]));
        sb.append(binarySearch() + "\n");
        willBeRemovedPole();

        bw.write(sb.toString());
        bw.close();
        br.close();
    }

    static int binarySearch() {
        for (int i = 0; i < m; i++) {
            if (list.get(list.size() - 1) < pole[i][1]) {   // LIS 의 마지막 요소보다 크면 LIS의 마지막 요소를 갱신
                list.add(pole[i][1]);
                preIndex[i] = list.size() - 1;
            } else {    // 아니라면 이분탐색을 통하여 현재값이 LIS의 몇번째 요소인지를 탐색
                int left = 1;
                int right = list.size() - 1;

                while (left < right) {
                    int mid = (left + right) / 2;

                    if (list.get(mid) < pole[i][1]) {
                        left = mid + 1;
                    } else {
                        right = mid;
                    }
                }

                // list의 위치 중 자신보다 처음 큰 값이 나오는 곳에 저장
                // list의 요소들의 값을 작게 유지해야 LIS의 크기를 늘릴 수 있기 때문
                list.set(right, pole[i][1]);
                preIndex[i] = right;
            }
        }

        return m - (list.size() - 1);
    }

    static void willBeRemovedPole() {
        int idx = list.size() - 1;
        Stack<Integer> stk = new Stack<>();
        // list.size() - 1이 LIS의 최대크기이므로, 전깃줄이 최대 크기 LIS의 요소인지를 판단하고,
        // 최대 크기 LIS의 요소가 아니라면 제거해야한다.
        for (int i = m - 1; i >= 0; i--) {
            if (preIndex[i] == idx) {
                idx--;
            } else {
                stk.push(pole[i][0]);
            }
        }

        // A 전봇대의 위에서부터 전깃줄을 제거해야하므로 Stack을 사용했다.
        while (!stk.isEmpty()) {
            sb.append(stk.pop() + "\n");
        }
    }
}
```

