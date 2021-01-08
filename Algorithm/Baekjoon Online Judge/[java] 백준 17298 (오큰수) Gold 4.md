## Problem : https://www.acmicpc.net/problem/17298

## Approach

배열에서 자신 기준 오른쪽에 있는 큰 값들 중 가장 가까운 값을 찾는 문제이다.

간단하게 생각할 수 있는 naive한 방법은 자신보다 큰 값이 나올 때까지 자신 기준 오른쪽 요소들을 모두 탐색하는 것이다. 

하지만 이런 접근법은 최대 O(n^2)의 시간복잡도를 가지므로 효율성이 떨어진다.

다른 풀이법으로는 스택을 이용한 풀이법이 있다.

> 1. 처음 시작점을 스택에 push 한다.
> 2. 스택의 top과 배열의 다음요소를 비교한다.
>    1. 스택의 top이 배열의 다음요소보다 작다면, 스택의 top의 오큰수는 배열의 다음요소이므로, 스택에서 pop을 한 후, `오큰수`를 저장한다. 이 과정을 스택의 top이 배열의 다음요소보다 클거나 같을 때까지 반복한다.(이 과정이 끝나면 2 - 2 과정을 수행한다.)
>    2. 스택의 top이 배열의 다음요소보다 크거나 같다면, 배열의 다음요소를 스택에 push한다.
> 3. 배열의 모든 요소를 스택에 넣을 때까지 2번 전체과정을 반복한다.
>
> 위의 과정으로 인해 스택은 top이 가장 큰 값이되도록 유지된다. 따라서 루프가 종료되었을 때 스택에 남아있는 값들은 `오큰수`가 없는 것이다.

스택을 이용한 풀이법은 O(n)으로 선형 시간복잡도이므로 naive한 방법보다 효율적이다.

## Code

```java
import java.io.*;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 *  no.17298: 오큰수
 *  url: https://www.acmicpc.net/problem/17298
 *  description: 스택으로 풀 수 있는 꽤 어려운 문제
 *  hint: 스택의 top 과 입력배열의 top 의 오른쪽 부분을 비교하여 풀어야 함.
 */

public class BOJ17298 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n];     // 입력 배열
        int[] answer = new int[n];  // 정답 배열
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        StringBuilder sb = new StringBuilder();
        Stack<Integer> stack = new Stack<>();   // 스택엔 인덱스를 삽입함

        // 초기 인덱스 삽입
        stack.push(0);
        for (int i = 1; i < n; i++) {
            // 스택의 top 에 있는 인덱스의 값이 i번째 인덱스의 값보다 작으면 정답배열에 저장
            while (!stack.isEmpty() && arr[stack.peek()] < arr[i]) {
                answer[stack.pop()] = arr[i];
            }

            // 다음번에 비교할 숫자 삽입
            stack.push(i);
        }

        // 스택에 남아있는 인덱스들은 오큰수가 없는 것
        while (!stack.isEmpty()) {
            answer[stack.pop()] = -1;
        }

        for (int nge : answer) {
            sb.append(nge + " ");
        }
        bw.write(sb.toString());
        bw.close();
        br.close();
    }
}


/* 시간초과 코드(O^2)

for (int i = 0; i < n; i++) {
    for (int j = i + 1; j < n; j++) {
        if (arr[i] < arr[j]) {
            sb.append(arr[j] + " ");
            break;
        }

        if (j == n - 1) {
            sb.append("-1 ");
        }
    }
}
sb.append("-1 ");
 */
```

