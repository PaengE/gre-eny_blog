## Problem : https://www.acmicpc.net/problem/1377



## Approach

> `정렬`을 활용한 문제이다.
>
> 문제에서 소개한 버블정렬 알고리즘은 해당 리스트를 맨 뒤부터 채우는 정렬 방법이다. 

문제에서 물어보는 것은 완전히 정렬될 때까지, 외부 for문이 몇번 돌아가는지를 물어보는 것이다.



한 번의 외부 for문을 돌릴 때, 리스트의 한 요소 a는 오른쪽으로는 최대 리스트의 끝까지 움직일 수 있고, 왼쪽으로는 1칸 움직일 수 있다.

따라서 `정렬 전 리스트`와 `정렬 후 리스트`를 비교하여, 각 요소들이 이동한 방향(-+)과 횟수를 구하고, 그 중 가장 큰 값이 답이 된다.

왜냐하면 외부 for문 한번에 왼쪽으로 최대 1번 움직일 수 있으므로, 예를 들어 왼쪽으로 A번 움직인 요소가 있다면 그 요소는 외부 for문이 A번 수행됐을 때 그 위치에 가기 때문이다.

## Code

```java
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 *  No.1377: 버블 소트
 *  Hint: 정렬 + Greedy
 */

public class BOJ1377 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        ArrayList<Number> list = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            list.add(new Number(Integer.parseInt(br.readLine()), i + 1));
        }

        Collections.sort(list, ((o1, o2) -> {
            if (o2.num == o1.num) {
                return o1.idx - o2.idx;
            }
            return o1.num - o2.num;
        }));

        int max = 0;
        for (int i = 1; i <= n; i++) {
            max = Math.max(max, list.get(i - 1).idx - i);
        }

        bw.write(String.valueOf(max + 1));
        bw.close();
        br.close();

    }
    static class Number{
        int num, idx;

        Number(int num, int idx) {
            this.num = num;
            this.idx = idx;
        }
    }
}
```

