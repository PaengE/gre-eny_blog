## 문제 원문 링크 : https://www.acmicpc.net/problem/14226

## Approach

DFS 를 이용한 DP문제이다.

> 문제의 조건
>
> 1. 화면에 있는 이모티콘을 모두 복사해서 클립보드에 저장한다.
> 2. 클립보드에 있는 모든 이모티콘을 화면에 붙여넣기 한다.
> 3. 화면에 있는 이모티콘 중 하나를 삭제한다.

이 문제를 풀려면 이모티콘의 길이가 X일 때, 현재 클립보드에 저장된 이모티콘의 길이가 몇인지를 저장해 놓아야 한다. 그래야 X보다 큰 길이Y의 이모티콘을 만들 때 쓸 수 있기 때문이다.

1, 2, 3번의 조건에 맞춰 재귀를 돌린 후 제일 빨리 길이 S의 이모티콘을 만들면 출력 후 프로그램을 종료하면 된다.

코드를 보고 이해하면 더 쉬울 것이다.

## Code

```java
import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

/**
 *  No.14226: 이모티콘
 *  URL: https://www.acmicpc.net/problem/14226
 *  Hint: BFS + DP -> visited[이모티콘길이][버퍼의길이]
 */

public class BOJ14226 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());

        boolean[][] visited = new boolean[n * 2 + 1][n * 2 + 1];

        Queue<Emo> q = new LinkedList<>();

        q.offer(new Emo(1, 0, 0));

        while (!q.isEmpty()) {
            Emo cur = q.poll();

            if (cur.len == n) {
                bw.write(String.valueOf(cur.time));
                bw.close();
                br.close();
                System.exit(0);
            }

            if (visited[cur.len][cur.buf]) {
                continue;
            }
            visited[cur.len][cur.buf] = true;

            // copy
            q.offer(new Emo(cur.len, cur.len, cur.time + 1));

            // paste
            if (cur.buf != 0 && cur.len + cur.buf <= n) {
                q.offer(new Emo(cur.len + cur.buf, cur.buf, cur.time + 1));
            }

            // minus 1
            if (cur.len > 0 && cur.len - 1 <= n) {
                q.offer(new Emo(cur.len - 1, cur.buf, cur.time + 1));
            }
        }
    }

    static class Emo {
        int len, buf, time;

        // (이모티콘의 길이, 버퍼의 길이, 걸린 최소 시간
        Emo(int len, int buf, int time) {
            this.len = len;
            this.buf = buf;
            this.time = time;
        }
    }
}

```

