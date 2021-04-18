## Problem : https://www.acmicpc.net/problem/17143

## Approach

> 삼성 역량 테스트 문제이다. 역시 구현 시뮬레이션 문제.
>
> 크게 아래 두가지를 구현하여야 한다. 구현하는게 좀 어렵다.
>
> 1. 상어 잡기
> 2. 상어 이동
>
> 1번 과정인 `상어 잡기`는 쉽다. 그냥 j 열에서 가장 가까운 i 행에 있는 상어를 잡으면 된다.
>
> 까다로운 부분은 2번 과정인 `상어 이동`이다.

나는 다음과 같은 변수를 사용하였다.

- int[][] map: 상어를 잡을 때 사용할 상어 위치 배열
- int[][] tmp: 상어를 움직인 후 상어의 위치를 저장할 배열.
  - 상어를 순차적으로 움직이기 때문에, 움직인 상어와 움직이지 않은 상어가 겹치는 것을 방지하기 위함이다.
- Shark[] sharks: 크기가 i인 상어들의 정보를 저장할 배열.
  - 같은 크기의 상어는 없기에 크기가 주 식별자가 될 수 있기 때문이다.
- Queue<Shark> sharkQ: 이동시킬 상어들이 담겨있는 큐 
  - 풀고나니까 큐여야할 필요는 없는 것 같다. List여도 상관없을 듯 싶다.
  - 상어를 이동시키고 바로 큐에 넣으려 했는데, 이럴 경우 상어가 겹칠 때 삭제하는 부분에서 불필요한 인덱싱이 필요할 것 같았다.

일단 상어가 이동할 때 speed가 (움직일 행 또는 열 - 1) * 2의 배수이면 이동한다 하여도 제자리이다. 이 부분을 활용했다.

그리고 난 뒤, 벽을 만났을 때만 처리해줬다. 속도와 위치에 따라 벽을 0, 1, 2 번 만날 수 있다.

상어의 위치를 갱신하는 부분은 손가락을 총동원했다... 더 간단한 코드로 표현할 수 도 있겠으나 풀렸으니 됐다...

## Code

```java
import java.io.*;
import java.util.*;

/**
 *  No.17143: 낚시왕
 *  URL: https://www.acmicpc.net/problem/17143
 *  Hint: 구현 시뮬레이션 문제
 */

public class BOJ17143 {
    static int r, c, m;
    static int[][] map; // 상어를 잡을 때 사용할 배열
    static int[][] tmp; // 상어를 움직인 후 임시로 저장할 배열
    static Shark[] sharks;  // 크기가 i인 상어들의 정보를 저장할 배열
    static Queue<Shark> sharksQ = new LinkedList<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        map = new int[r][c];
        tmp = new int[r][c];
        sharks = new Shark[10001];  // 상어의 최대 마리 수 만큼

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());   // 1:위, 2:아래, 3:오른, 4:왼
            int z = Integer.parseInt(st.nextToken());
            Shark shark = new Shark(r - 1, c - 1, s, d, z);
            sharks[z] = shark;
            map[r - 1][c - 1] = z;
        }

        int ans = 0;
        for (int i = 0; i < c; i++) {
            ans += getShark(i);
        }

        bw.write(String.valueOf(ans));
        bw.close();
        br.close();
    }

    // 상어를 잡는 일련의 과정 (잡은 상어의 크기를 리턴)
    static int getShark(int curC) {
        // 상어를 잡음(못 잡을 수도 있음)
        int sharkSize = 0;
        for (int i = 0; i < r; i++) {
            if (map[i][curC] != 0) {
                sharkSize = map[i][curC];
                map[i][curC] = 0;
                break;
            }
        }

        // 남은 상어들을 큐에 집어넣음
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (map[i][j] != 0) {
                    sharksQ.offer(sharks[map[i][j]]);
                }
            }
        }

        // 상어들을 움직임
        initTmpArray();
        while(!sharksQ.isEmpty()) {
            move(sharksQ.poll());
        }

        // 움직임이 반영된 tmp 를 map 에 복사
        copyTmpToMap();
        return sharkSize;
    }

    // 상어 이동
    static void move(Shark cur) {
        if (cur.direction == 1) {   // 위
            cur.speed %= ((r - 1) * 2);
            cur.r -= cur.speed;

            if (cur.r < 0) {
                cur.r = Math.abs(cur.r);
                cur.direction = 2;
            }
            if (cur.r >= r) {
                cur.r = r - (cur.r - (r - 1) + 1);
                cur.direction = 1;
            }
        } else if (cur.direction == 2) {    // 아래
            cur.speed %= ((r - 1) * 2);
            cur.r += cur.speed;

            if (cur.r >= r) {
                cur.r = r - (cur.r - (r - 1) + 1);
                cur.direction = 1;
            }
            if (cur.r < 0) {
                cur.r = Math.abs(cur.r);
                cur.direction = 2;
            }
        } else if (cur.direction == 3) {    // 오른
            cur.speed %= ((c - 1) * 2);
            cur.c += cur.speed;

            if (cur.c >= c) {
                cur.c = c - (cur.c - (c - 1) + 1);
                cur.direction = 4;
            }
            if (cur.c < 0) {
                cur.c = Math.abs(cur.c);
                cur.direction = 3;
            }
        } else if (cur.direction == 4) {    // 왼
            cur.speed %= ((c - 1) * 2);
            cur.c -= cur.speed;

            if (cur.c < 0) {
                cur.c = Math.abs(cur.c);
                cur.direction = 3;
            }
            if (cur.c >= c) {
                cur.c = c - (cur.c - (c - 1) + 1);
                cur.direction = 4;
            }
        }

        // 이동한 상어의 위치를 tmp 에 기록 (크기가 큰 상어만)
        if (cur.z > tmp[cur.r][cur.c]) {
            tmp[cur.r][cur.c] = cur.z;
            sharks[cur.z] = new Shark(cur.r, cur.c, cur.speed, cur.direction, cur.z);
        }
    }

    // tmp 배열을 map 배열에 복사
    static void copyTmpToMap() {
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                map[i][j] = tmp[i][j];
            }
        }
    }

    // tmp 배열 0으로 초기화
    static void initTmpArray() {
        for (int[] t : tmp) {
            Arrays.fill(t, 0);
        }
    }

    static class Shark{
        int r, c, speed, direction, z;

        Shark(int r, int c, int speed, int direction, int z) {
            this.r = r;
            this.c = c;
            this.speed = speed;
            this.direction = direction;
            this.z = z;
        }
    }
}
```

