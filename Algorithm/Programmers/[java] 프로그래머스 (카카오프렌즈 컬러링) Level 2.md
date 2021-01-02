## Problem : https://programmers.co.kr/learn/courses/30/lessons/1829

## Approach

2017 카카오코드 예선문제로, 간단한 BFS 문제이다.

일단 모든 점을 검사한다. 

검사하면서 방문할 수 있으면 방문하면서 최대 영역의 넓이와 영역의 개수를 갱신하고 방문표시를 한다.

## Code

```java
import java.util.LinkedList;
import java.util.Queue;

public class KakaoFriendsColoringBook {
    public static void main(String[] args) {
        int m = 6;
        int n = 4;
        int[][] picture =   { { 1, 0, 0, 1 }, { 1, 0, 0, 1 }, { 1, 0, 0, 1 }, { 1, 0, 0, 1 }, { 1, 0, 0, 1 }, { 1, 1, 1, 1 } };

        int[] answer = solution(m, n, picture);
        System.out.println(answer[0]);
        System.out.println(answer[1]);

    }

    static int[] solution(int m, int n, int[][] picture) {
        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};

        boolean[][] visited = new boolean[m][n];
        Queue<Integer> qx = new LinkedList<>();
        Queue<Integer> qy = new LinkedList<>();

        int maxSizeOfOneArea = 0;
        int numberOfArea = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (picture[i][j] != 0 && !visited[i][j]) {
                    long num = picture[i][j];	// 자신의 영역 번호
                    qx.offer(i);
                    qy.offer(j);
                    visited[i][j] = true;

                    int count = 1;	// 영역의 넓이
                    numberOfArea += 1;	// 영역 개수 +1

                  // BFS 진행
                    while (!qx.isEmpty()) {
                        int cx = qx.poll();
                        int cy = qy.poll();

                        for (int k = 0; k < 4; k++) {
                            int nx = cx + dx[k];
                            int ny = cy + dy[k];

                            if (nx >= 0 && nx < m && ny >= 0 && ny < n) {
                                if (!visited[nx][ny] && picture[nx][ny] == num) {
                                    qx.offer(nx);
                                    qy.offer(ny);
                                    visited[nx][ny] = true;

                                    count += 1;
                                }
                            }
                        }
                    }
                    maxSizeOfOneArea = Math.max(maxSizeOfOneArea, count);
                }
            }
        }
        return new int[]{numberOfArea, maxSizeOfOneArea};
    }
}

```

