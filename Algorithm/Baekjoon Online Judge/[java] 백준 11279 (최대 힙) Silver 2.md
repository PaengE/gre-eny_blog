## Problem : https://www.acmicpc.net/problem/11279

## Approach

PriorityQueue를 이용하여 풀 수 있는 문제이다.

JAVA에서 기본적으로 PriorityQueue는 default값으로 오름차순, 즉 Integer큐일 경우 숫자가 작을수록 우선순위가 높다. 따라서 별다른 설정없이 우선순위 큐에 넣었다 뺀다면 자동적으로 가장 작은 값이 나온다.

하지만 가장 큰 값이 나와야 하므로 별도의 설정이 필요하다. 직접 Compartor를 override하는 방법도 있지만, 오름차순의 역순(내림차순)으로 정렬 기준을 잡는 것은 Collections 클래스의 reverseOrder()를 사용하면 간단하게 바꿀 수 있다.

## Code

```java
import java.io.*;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * no.11279 : 최대 힙
 * title : 최댓값을 빠르게 뽑는 자료구조를 배우는 문제
 * hint : PriorityQueue 와 Collections.reverseOrder() 사용
 */

public class BOJ11279 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        PriorityQueue<Integer> q = new PriorityQueue<>(Collections.reverseOrder());

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

