## Problem : https://www.acmicpc.net/problem/17071



## Approach

> 숨바꼭질 시리즈에서 `동생이 움직인 버전` 이다.
>
> 일반적인 `BFS`로는 시간초과(TLE)가 발생한다.
>
> `규칙`을 찾아 시간을 단축시켜야 한다.

처음엔 일반적인 BFS를 하여 시간초과를 받았다. 시간초과 코드 또한 아래에 기록했다.



`규칙`은 다음과 같다. 

- 수빈이가 A초에 X지점을 도착했다고 한다면, A+2초에도 X지점을 도착할 수 있다.
  (2초 동안 -1, +1 을 반복하면 제자리로 올 수 있다.)

이 규칙이 필요한 경우는 수빈이가 X지점에서 -1, +1을 반복하고 있고, 동생이 나중에 X지점에 도착할 경우이다.

일반적인 BFS 라면 -1, +1 할 때에도 Queue에 집어 넣어야 한다.



기존 시간초과 코드에서는 방문했던 곳이라도 다시 Queue에 넣어 불필요한 탐색을 진행했다.
(이 부분에서 시간초과가 발생했을 것이다.)

위 규칙을 이용하면, 도착했던 곳은 다시 Queue에 넣지 않아도 된다.



주의할 점은 `홀수 시간`과 `짝수 시간`을 별개로 도착처리 해야 한다는 점이다. 

A초에 X지점에 도착했다면, A+1초에는 X지점에 도착할 수 없기 때문이다.



## Code (71% TLE)

```java
/**
 *  시간초과 코드 71% TLE
 */
public class BOJ17071 {
    static int n, k, ans = -1;
    static boolean[] visited;
    static boolean flag;
    static Queue<Integer> startPoints = new ArrayDeque<>();
    static HashSet<Integer> set = new HashSet<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        visited = new boolean[Math.max(n, k) + 1];

        int time = 0;
        startPoints.offer(n);
        while (!flag && k <= 500000) {
            bfs(time++);
            k += time;
        }

        bw.write(String.valueOf(ans));
        bw.close();
        br.close();
    }

    static void bfs(int time) {
        startPoints.addAll(set);
        set.clear();

        while (!startPoints.isEmpty()) {
            int cur = startPoints.poll();
            if (cur == k) {
                ans = time;
                flag = true;
                return;
            }

            for (int i = 0; i < 3; i++) {
                int nx = cur;
                switch (i) {
                    case 0:
                        nx += -1;
                        break;
                    case 1:
                        nx += 1;
                        break;
                    case 2:
                        nx *= 2;
                        break;
                }

                if (isInRange(nx)) {
                    set.add(nx);
                }
            }
        }
    }

    static boolean isInRange(int x) {
        if (x >= 0 && x <= 500000) {
            return true;
        }
        return false;
    }
}
```



## Code (Correct)

```java
import java.io.*;
import java.util.*;

/**
 *  No.17071: 숨바꼭질 5
 *  Hint: 규칙이 있는 BFS
 *      동생이 k 를 홀수(짝수) 초에 방문했을 때, 수빈이가 k를 홀수(짝수) 초에 방문한 적이 있었다면,
 *      둘은 서로 만날 수 있다.
 */

public class BOJ17071 {
    static int n, k;
    static boolean[][] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        visited = new boolean[2][500001];

        bw.write(n == k ? "0" : String.valueOf(bfs()));
        bw.close();
        br.close();
    }

    static int bfs() {
        Queue<Integer> q = new ArrayDeque<>();
        q.offer(n);
        visited[0][n] = true;   // visited[0][] == 짝수 초, visited[1][] == 홀수 초

        int size, mod, time = 0;
        while (!q.isEmpty()) {
            size = q.size();
            time++;
            mod = time % 2;

            k += time;
            if (k > 500000) {
                return -1;
            }

            for (int i = 0; i < size; i++) {
                int cur = q.poll();

                if (cur - 1 >= 0 && !visited[mod][cur - 1]) {
                    q.offer(cur - 1);
                    visited[mod][cur - 1] = true;
                }

                if (cur + 1 <= 500000 && !visited[mod][cur + 1]) {
                    q.offer(cur + 1);
                    visited[mod][cur + 1] = true;
                }

                if (cur * 2 <= 500000 && !visited[mod][cur * 2]) {
                    q.offer(cur * 2);
                    visited[mod][cur * 2] = true;
                }
            }

            if (visited[mod][k]) {
                return time;
            }
        }
        return -1;
    }
}
```

