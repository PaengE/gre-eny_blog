## Problem : https://www.acmicpc.net/problem/11286

## Approach

PriorityQueue를 이용하여 풀 수 있는 문제이다.

JAVA에서 기본적으로 PriorityQueue는 default값으로 오름차순, 즉 Integer큐일 경우 숫자가 작을수록 우선순위가 높다. 따라서 별다른 설정없이 우선순위 큐에 넣었다 뺀다면 자동적으로 가장 작은 값이 나온다.

절댓값이 가장 작은 값을 출력하려면 PriorityQueue의 Comparator를 재정의(override) 해주어야 한다.

return 값이 -1이면 우선순위를 높이고, 1이면 우선순위를 낮춘다는 뜻이다.

절댓값이 같은 경우도 고려하여 처리하면 된다.

## Code

```java
import java.io.*;
import java.util.PriorityQueue;

/**
 * no.11286 : 절댓값 힙
 * title : 새로운 기준으로 뽑는 우선순위 큐를 만드는 문제
 * hint : PriorityQueue 사용, Comparator interface 의 compare method 를 override 하여 사용.
 */

public class BOJ11286 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        PriorityQueue<Integer> q = new PriorityQueue<>((o1, o2) -> {
            if (Math.abs(o1) == Math.abs(o2)) {
                return o1 < o2 ? -1 : 1;
            }
            return Math.abs(o1) - Math.abs(o2);
        });

        int n = Integer.parseInt(br.readLine());
        int val = 0;

        for (int i = 0; i < n; i++) {
            val = Integer.parseInt(br.readLine());
            if (val != 0) {
                q.add(val);
            } else if (q.isEmpty()) {
                bw.write("0\n");
            } else {
                bw.write(q.poll() + "\n");
            }
        }
        bw.flush();
        bw.close();
        br.close();
    }
}

```

