## Problem : https://www.acmicpc.net/problem/1927

## Approach

우선순위 큐(PriorityQueue)를 이용하여 풀 수 있는 문제이다.

JAVA에서 기본적으로 PriorityQueue는 default값으로 오름차순, 즉 Integer큐일 경우 숫자가 작을수록 우선순위가 높다. 따라서 별다른 설정없이 우선순위 큐에 넣었다 뺀다면 자동적으로 가장 작은 값이 나온다.

## Code

```java
import java.io.*;
import java.util.PriorityQueue;

/**
 * no.1927 : 최소 힙
 * title : 최솟값을 빠르게 뽑는 자료구조를 배우는 문제
 * hint : PriorityQueue 사용
 */

public class BOJ1927 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        PriorityQueue<Integer> q = new PriorityQueue<>();

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

