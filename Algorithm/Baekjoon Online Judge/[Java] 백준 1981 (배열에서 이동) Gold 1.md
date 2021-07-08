## Problem : https://www.acmicpc.net/problem/1981



## Approach

> `이분탐색` + `BFS` 문제이다. 꽤 어렵게 문제를 풀었다.



문제 풀이에 앞서, 입력을 받으면서 구해놔야 할 것이 있다. 

배열의 값중 최솟값 `minNum`과 최댓값 `maxNum`을 이분탐색의 `left`와 `right`에 이용하기 때문이다.

`left`는 0이다. (배열의 값이 모두 같은 경우 `최대 - 최소 = 0`이다.) `right`는 `maxNum` - `minNum`이다. 



문제 풀이의 주요 로직은 다음과 같다.

- `이분탐색`을 이용하여 `mid` 값을 구한다. (이 `mid`값은 문제에서 내건 `최대 - 최소` 값으로 쓰인다.)
- `s ~ e`의 길이가 `mid`인 minNum 이상이고 maxNum이하인 모든 구간에 대해 `BFS`를 수행한다.
  - `BFS`의 조건은 배열의 값이 `s ~ e` 사이일 경우 이동할 수 있는 조건이다.
  - `s ~ e` 인 값으로만 이동한다면 그 경로에 위치한 값들의 `최대 - 최소`는 무조건 `mid`값 이하이다.
- `BFS`를 수행하면서 최종적으로 (n, n)에 도달하는 구간이 하나라도 존재한다면, ans으로 저장하고 `right = mid - 1`을 수행한다.
- 존재하지 않다면 `left = mid + 1`로 하여 계속 진행한다. (가장 작은 ans를 원하므로)

주의할 점은 `시작지점` 또한 `s ~ e` 사이에 속하는지 검사를 한 후 `BFS`를 수행하여야 한다는 점이다.



이분탐색: `log(2N)`, 크기가 mid인 모든 구간들을 BFS: `(2N) * N^2`

그래서 총 시간 복잡도는 O(log(N) * N^3) 정도로 볼 수 있다. 



## Code

```java
import java.io.*;
import java.util.*;

/**
 *  No.1981: 배열에서 이동
 *  Hint: 이분탐색 + BFS
 */

public class BOJ1981 {
    static int n, ans, maxNum, minNum = 200;
    static int[][] arr;
    static int[] dx = {-1, 1, 0, 0,};
    static int[] dy = {0, 0, -1, 1};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        n = Integer.parseInt(br.readLine());
        arr = new int[n][n];

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
                maxNum = Math.max(maxNum, arr[i][j]);
                minNum = Math.min(minNum, arr[i][j]);
            }
        }

        // 정답이 될 수 있는 후보 중 최솟값은 arr의 모든 값이 같을 때이고, 최댓값은 arr의 최댓값 - 최솟값이다.
        binarySearch(0, maxNum - minNum);

        bw.write(String.valueOf(ans));
        bw.close();
        br.close();
    }

    // 이분탐색으로 (최대 - 최소) 값을 찾음
    static void binarySearch(int left, int right) {
        while (left <= right) {
            int mid = (left + right) / 2;

            if (whetherItCanBeReached(mid)) {
                ans = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
    }

    // diff 값으로 (n-1, n-1)에 도달할 수 있는지를 체크
    static boolean whetherItCanBeReached(int diff) {
        // (s - e) 가 diff 인 모든 구간을 시도
        for (int i = minNum; i + diff <= maxNum; i++) {
            int s = i;
            int e = s + diff;

            // 시작지점 검사
            if (s > arr[0][0] || arr[0][0] > e) {
                continue;
            }

            Queue<Point> q = new ArrayDeque<>();
            boolean[][] visited = new boolean[n][n];
            q.offer(new Point(0, 0));
            visited[0][0] = true;

            while (!q.isEmpty()) {
                Point cur = q.poll();

                if (cur.x == n - 1 && cur.y == n - 1) {
                    return true;
                }

                for (int j = 0; j < 4; j++) {
                    int nx = cur.x + dx[j];
                    int ny = cur.y + dy[j];

                    // s ~ e 사이에 arr[nx][ny] 값이 존재하면 이동할 수 있음
                    if (inRange(nx, ny) && !visited[nx][ny] && s <= arr[nx][ny] && arr[nx][ny] <= e){
                        q.offer(new Point(nx, ny));
                        visited[nx][ny] = true;
                    }
                }
            }
        }

        return false;
    }

    static boolean inRange(int x, int y) {
        if (x >= 0 && x < n && y >= 0 && y < n) {
            return true;
        }
        return false;
    }

    static class Point{
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
```

