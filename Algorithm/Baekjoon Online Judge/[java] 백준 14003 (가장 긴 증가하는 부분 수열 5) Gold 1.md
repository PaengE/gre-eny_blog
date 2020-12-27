## 문제 원문 링크 : https://www.acmicpc.net/problem/14003

## Approach

이 문제는 [백준 12015번 가장 긴 증가하는 부분 수열 2](https://www.acmicpc.net/problem/12015) 과 [백준 12738번 가장 긴 증가하는 부분 수열 3](https://www.acmicpc.net/problem/12738) 의 문제에 LIS을 추가로 출력해야 하는 문제이다.

아래 링크는 12015번 문제와 12738번 문제에 대한 나의 풀이이다.

[2020/12/19 - \[Algorithm/Baekjoon Online Judge\] - \[java\] 백준 12015 (가장 긴 증가하는 부분 수열 2) Gold 2](https://gre-eny.tistory.com/23)

[2020/12/19 - \[Algorithm/Baekjoon Online Judge\] - \[java\] 백준 12738 (가장 긴 증가하는 부분 수열 3) Gold 2](https://gre-eny.tistory.com/24)

문제의 조건은 변함없이, 단지 1,000,000개 이하의 입력데이터 개수로 DP방식은 시간이 오래걸려 사용하지 못한다. 그래서 이분탐색을 사용해야 한다.

주의할 점은, 이분 탐색에의해 만들어진 리스트가 LIS를 구성하는 요소들은 아니라는 것이다.

> 1 5 10 4 가 입력으로 주어 졌을 때,
>
> 1. 1 입력 ▶ [0 1]
>
> 2. 5 입력 ▶ [0 1 5]
>
> 3. 10 입력 ▶ [0 1 5 10]
>
> 4. 4 입력 ▶ [0 1 4 10]
>
>    -> 답은 3으로, 최대 길이를 구하는 데에는 문제가 없는 풀이법이다. 그러나 이것은 LIS 를 구성하는 요소를 보장하지 않는다. 따라서 자신이 증가하는 부분 수열의 몇번째 인덱스인지를 기록하는 배열을 사용한다.
>
> 
>
> 위의 입력을 그대로 사용할 때, preIndex는 1 2 3 2가 된다.(여기서는 배열이름이 preIndex지만 이전 인덱스라는 뜻은 아니고, 필자가 적절하게 변수명을 짓지 못했다. preIndex에 저장된 값들은 자신이 증가하는 부분수열의 몇 번째 위치에 자리잡고 있는지를 의미한다.)
>
> 위 작업이 끝난 후, 최대길이는 3이라는 것을 알 수 있다. 그럼 preIndex 배열에서 3의 인덱스를 찾고, 그 인덱스의 왼쪽방향으로 인덱스 0까지 처음 만나는 2, 1의 인덱스를 찾는다. 그 값들을 Stack에 넣은 후, 마지막에 다시 pop하면 된다.

## Code

```java
import java.io.*;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * No.14003: 가장 긴 증가하는 부분 수열 5
 * Description: O(NlogN) LIS 를 출력하는 문제
 * URL: https://www.acmicpc.net/problem/14003
 * Hint: 이분탐색을 이용하면 O(NlogN)
 *       DP 를 이용하면 O(N^2)
 */

public class BOJ14003 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        ArrayList<Integer> list = new ArrayList<>();
        list.add(Integer.MIN_VALUE);

        int n = Integer.parseInt(br.readLine());
        int[] num = new int[n];
        int val = 0;
        int[] preIndex = new int[n];

        StringTokenizer st = new StringTokenizer(br.readLine());
        // 이분 탐색을 이용한 LIS 구하기
        for (int i = 0; i < n; i++) {
            val = Integer.parseInt(st.nextToken());
            num[i] = val;

            if (list.get(list.size() - 1) < val) {
                list.add(val);
                preIndex[i] = list.size() - 1;
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
              // 리스트에서 자신의 위치를 기록한다.
                preIndex[i] = right;
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(list.size() - 1 + "\n");

        int index = list.size() - 1;
        Stack<Integer> stack = new Stack<>();

        for(int i = n - 1; i >= 0; i--){
            // 찾길 원하는 인덱스와 같은 경우
            if(preIndex[i] == index){
                // 찾길 원하는 인덱스를 하나 감소시킨다.
                // 다음 인덱스의 값을 찾기 위해서
                index--;
                // stack에 경로를 추가한다.
                stack.push(num[i]);
            }
        }

        // 스택에서 꺼내며 찾는다.
        while (!stack.isEmpty()){
            sb.append(stack.pop() + " ");
        }

        bw.write(sb.toString());
        bw.flush();
        br.close();
        bw.close();

    }
}

```

