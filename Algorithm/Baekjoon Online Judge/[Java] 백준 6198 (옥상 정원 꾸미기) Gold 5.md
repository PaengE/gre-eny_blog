## Problem : https://www.acmicpc.net/problem/6198



## Approach

> `스택Stack` 자료구조를 잘 이용해야 하는 문제이다.



`스택`을 빌딩의 높이 기준 `내림차순` 상태를 유지시키도록 구성하는 것이 문제 풀이의 포인트이다.

주요 문제 풀이 로직은 빌딩을 순차적으로 접근함을 전제로 다음과 같다. 

- 스택이 비어있지 않고 스택의 top이 현재 빌딩의 높이 `이하`면 pop을 진행하며, 
  ans에 `(현재 빌딩 idx) - (스택 top의 빌딩 idx) - 1`을 더한다.

- 위 과정을 스택이 비거나, 스택의 top이 현재 빌딩의 높이보다 클 때까지 수행한다. 
  (이 부분이 스택의 요소가 내림차순임을 보장한다.)
- 모든 빌딩에 접근한 뒤에, 스택에 남아있는 모든 요소들에 대하여
  ans에 `(빌딩의 개수 - 1) - (스택 요소의 idx)`를 더한다.



## Code

```java
import java.io.*;
import java.util.Stack;

/**
 *  No.6198: 옥상 정원 꾸미기
 *  Hint: Stack
 */

public class BOJ6198 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(br.readLine());
        }

        bw.write(solve(n, arr));
        bw.close();
        br.close();
    }

    static String solve(int n, int[] arr) {
        long ans = 0L;
        Stack<Building> stk = new Stack<>();    // 스택은 빌딩 높이 순 내림차순을 유지해야 함.

        for (int i = 0; i < n; i++) {
            while (!stk.isEmpty() && stk.peek().height <= arr[i]) {
                Building now = stk.pop();
                ans += (i - now.idx - 1);
            }

            stk.push(new Building(i, arr[i]));
        }

        // 스택에 남아 있는 빌딩들을 처리
        while (!stk.isEmpty()) {
            ans += (n - 1) - stk.pop().idx;
        }

        return String.valueOf(ans);
    }

    static class Building{
        int idx, height;

        Building(int idx, int height) {
            this.idx = idx;
            this.height = height;
        }
    }
}
```

