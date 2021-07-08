## Problem : https://www.acmicpc.net/problem/5014



## Approach

> 간단한 `BFS` 문제이다.



빌딩의 최고층이 F인데, `F층보다 큰 층에서 내려오는 부분`과 `1층보다 낮은 층에서 올라오는 부분`을 제외해주면 되기에 배열의 크기를 정하기 용이하다.



기본적인 `BFS`문제로, 풀이하기에 어렵지 않다.



## Code

```java
import java.io.*;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 *  No.5014: 스타트링크
 *  Hint: BFS
 */

public class BOJ5014 {
    static int f, s, g;
    static int[] move = new int[2];
    static int[] res;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        f = Integer.parseInt(st.nextToken());
        s = Integer.parseInt(st.nextToken());
        g = Integer.parseInt(st.nextToken());
        move[0] = Integer.parseInt(st.nextToken());
        move[1] = -Integer.parseInt(st.nextToken());
        res = new int[f + 1];
        Arrays.fill(res, -1);

        bfs();

        if (res[g] == -1) {
            bw.write("use the stairs");
        } else {
            bw.write(String.valueOf(res[g]));
        }

        bw.close();
        br.close();
    }

    static void bfs() {
        Queue<Integer> q = new ArrayDeque<>();
        q.offer(s);
        res[s] = 0;

        while (!q.isEmpty()) {
            int cur = q.poll();
            for (int i = 0; i < 2; i++) {
                int next = cur + move[i];
                if (isInRange(next) && res[next] == -1) {
                    q.offer(next);
                    res[next] = res[cur] + 1;
                }
            }
        }
    }

    // 범위 체크
    static boolean isInRange(int x) {
        if (x >= 1 && x <= f) {
            return true;
        }
        return false;
    }
}
```

