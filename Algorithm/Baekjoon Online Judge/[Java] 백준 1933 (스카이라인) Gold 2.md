## Problem : https://www.acmicpc.net/problem/1933



## Approach

> `우선순위 큐(PriorityQueue)`와 `TreeMap` 자료구조를 활용하는 문제이다.

문제에서 원하는 것은 `높이가 변하는 부분의 x좌표`와 `그 때의 최대높이`이다.

이를 위해 `우선순위 큐`와 `TreeMap`을 사용한다. 

우선순위 큐는 x좌표 기준 오름차순(같을 경우, 높이 기준 내림차순)으로, 트리맵은 높이 기준 내림차순의 정렬 기준을 규정한다. (높이, 해당 높이의 건물 개수)





문제 풀이의 주요 로직은 다음과 같다.

- 건물 정보를 이용하여 `(건물의 왼쪽 좌표, 높이)`, `(건물의 오른쪽 좌표, -높이)`를 우선순위 큐에 넣는다.
- 우선순위 큐에서 하나씩 poll 하며 아래 작업을 수행한다.
  - 뽑은 게 `건물의 왼쪽 좌표`라면, Map에 추가/갱신한다. (높이, 해당 높이의 건물 개수)
  - 뽑은 게 `건물의 오른쪽 좌표`라면, Map에 삭제/갱신한다. 
- TreeMap에서 첫 번째 Key(가장 높은 건물의 높이)를 뽑는다. 뽑은 좌표x가 처음이고, x 이전의 최대 높이maxH와 비교했을 때 key가 다르다면, 정답 StringBuilder에 추가하고 maxH를 갱신한다.

위 과정을 우선순위 큐가 빌 때까지 반복한다.



## Code

```java
import java.io.*;
import java.util.*;

/**
 *  No.1933: 스카이라인
 *  Hint: PriorityQueue
 */

public class BOJ1933 {
    static int n;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        n = Integer.parseInt(br.readLine());
        // 좌표 기준 오름차순 (같으면 높이 기준 내림차순)
        PriorityQueue<Building> pq = new PriorityQueue<>((o1, o2) -> {
            if (o1.x == o2.x) {
                return o2.h - o1.h;
            }
            return o1.x - o2.x;
        });

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int l = Integer.parseInt(st.nextToken());
            int h = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());
            pq.offer(new Building(l, h));
            pq.offer(new Building(r, -h));
        }

        StringBuilder sb = new StringBuilder();

        // key 기준 내림 차순
        TreeMap<Integer, Integer> map = new TreeMap<>(Collections.reverseOrder());
        int maxX = 0, maxH = 0;
        map.put(0, 1);
        while (!pq.isEmpty()) {
            Building b = pq.poll();

            if (b.h > 0) {  // 시작점이면
                map.put(b.h, map.getOrDefault(b.h, 0) + 1);
            } else {    // 끝점이면
                int val = map.get(-b.h);
                if (val == 1) {
                    map.remove(-b.h);
                } else {
                    map.replace(-b.h, val - 1);
                }
            }

            // maxX ~ b.x 중 가장 높은 H를 뽑음
            int height = map.firstKey();
            if (maxX != b.x && maxH != height) {
                sb.append(b.x + " " + height + " ");
                maxX = b.x;
                maxH = height;
            }
        }

        bw.write(sb.toString().trim());
        bw.close();
        br.close();
    }

    static class Building{
        int x, h;

        Building(int x, int h) {
            this.x = x;
            this.h = h;
        }
    }
}
```

