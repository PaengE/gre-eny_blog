## Problem : https://www.acmicpc.net/problem/2873

## Approach

>  `Greedy` 문제지만 구현이 좀 필요하다.

일단 그리디한 생각은 다음과 같다.

1. r행 c열 표가 존재할 때, r, c 중 하나라도 홀수라면 표의 모든 곳을 방문할 수 있다.
   - `ㄹ 모양` 혹은 `ㄹ 을 y=x 대칭시킨 모양`으로 방문한다면 모든 곳을 지날 수 있다.
2. r, c 모두 짝수라면, 표 중 단 한 곳을 제외하고 모든 곳을 방문할 수 있다.
   - 시작점이 흰색인 체스판으로 생각하면, 도착점도 흰색이다. 최대한 모든 곳을 들린다고 해도 검은판 중 한 곳은 무조건적으로 방문할 수 없다.
   - 왜냐하면 흰 -> 검 -> 흰 -> 검 -> 흰 -> ... 식으로 방문하여야 하는데 결과적으로 흰색부분을 검은색 부분보다 한 곳 더 방문해야 한다. r, c가 모두 짝수이면 검은색 부분 중 한 곳을 방문할 수 없게 된다.
   - 문제 특성상 최대한 많은 곳을 방문하고, 방문하지 않을 한 곳은 가장 값이 작아야 한다.



첫번째 경우는 다음 그림처럼 쉽게 구현이 가능하다.

![2873-2](C:\Users\82102\OneDrive\티스토리\Algorithm\Baekjoon Online Judge\image\2873-2.PNG)

두번째 경우는 다음과 같은 방법을 사용해야 한다. 먼저 체스판을 생각하며 검은색 부분 중 가장 작은 곳M을 찾는다.

![2873-3](C:\Users\82102\OneDrive\티스토리\Algorithm\Baekjoon Online Judge\image\2873-3.PNG)

- 시작점과 도착점을 기준으로 각각 현재 행과 바로 인접한 행에 M이 있는지 체크한 뒤, 없다면 각각 StringBuilder에 차곡차곡 쌓는다.
  - 위의 과정이 끝나면, 방문하지 않은 크기 2의 행이 남을 것이다.
  - 그러면 크기 2의 행의 좌상단을 시작점, 우하단을 도착점이 될 것이다.
- 마찬가지로 시작점과 도착점을 기준으로 각각 현재 열과 바로 인접한 열에 M이 있는지 체크한 뒤, 없다면 각각 StringBuilder에 쌓는다.
  - 위의 과정이 끝나면, 아래 그림처럼 2 x 2의 방문하지 않은 곳이 존재하게 된다.
  - 그러면 M의 위치에 따라 `우 -> 하`를 할 지, `하 -> 우`를 할 지 결정하면 된다.

![2873-4](C:\Users\82102\OneDrive\티스토리\Algorithm\Baekjoon Online Judge\image\2873-4.PNG)

- 순서를 맞추기 위해, 도착점부터 시작한 StringBuilder는 reverse 연산과 같은 것을 취하여 경로를 확정한다.



## Code

```java
import java.io.*;
import java.util.StringTokenizer;

/**
 *  No.2873: 롤러코스터
 *  Hint: Greedy
 */

public class BOJ2873 {
    static int r, c, minR, minC;
    static int[][] arr;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        arr = new int[r][c];
        int min = 1001;
        for (int i = 0; i < r; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < c; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
                // 시작지점이 흰색인 체스판이라 가정했을 때,
                // 검은 지점 중 가장 작은 숫자가 위치한 곳을 찾음
                if ((i + j) % 2 == 1 && arr[i][j] < min) {
                    min = arr[i][j];
                    minR = i;
                    minC = j;
                }
            }
        }

        String ans;
        // r 과 c 중 하나라도 홀수라면 전체 탐색이 가능
        if (r % 2 == 1 || c % 2 == 1) {
            ans = notEven();
        } else {
            ans = even();
        }

        bw.write(ans);
        bw.close();
        br.close();
    }

    static String even() {
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();

        int sr = 0, sc = 0;
        int er = r - 1, ec = c - 1;
        
        // er 과 sr 사이에 행 2개만 남을 때까지
        while (er - sr > 1) {
            // sr 기준 현재 행과 다음 행에 최소값 검은 지점이 없다면 ㄹ 모양으로 방문
            if (sr / 2 < minR / 2) {    
                for (int i = 0; i < c - 1; i++) {
                    sb1.append("R");
                }
                sb1.append("D");

                for (int i = 0; i < c - 1; i++) { 
                    sb1.append("L");
                }
                sb1.append("D");
                sr += 2;
            }

            // er 기준 현재 행과 이전 행에 최소값 검은 지점이 없다면 ㄹ 모양으로 방문
            if (er / 2 > minR / 2) {    
                for (int i = 0; i < c - 1; i++) {
                    sb2.append("R");
                }
                sb2.append("D");

                for (int i = 0; i < c - 1; i++) {
                    sb2.append("L");
                }
                sb2.append("D");
                er -= 2;
            }
        }

        // ec 와 sc 사이에 열 2개만 남을 때까지
        while (ec - sc > 1) {
            // sc 기준 현재 열과 다음 열에 최소값 검은 지점이 없다면 ㄹ 을 y=x 대칭 시킨 모양으로 방문
            if (sc / 2 < minC / 2) {
                sb1.append("DRUR");
                sc += 2;
            }

            // ec 기준 현재 열과 다음 열에 최소값 검은 지점이 없다면 ㄹ 을 y=x 대칭 시킨 모양으로 방문
            if (ec / 2 > minC / 2) {
                sb2.append("DRUR");
                ec -= 2;
            }
        }

        // 위 과정을 거치면 2 x 2 부분만 남음
        if (sc == minC) {
            sb1.append("RD");
        } else {
            sb1.append("DR");
        }

        return sb1.append(sb2.reverse()).toString();
    }

    static String notEven() {
        StringBuilder sb = new StringBuilder();

        if (r % 2 == 1) {  // 행이 홀수라면 ㄹ 모양으로 방문
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c - 1; j++) {
                    if (i % 2 == 0) {
                        sb.append("R");
                    } else if (i % 2 == 1) {
                        sb.append("L");
                    }
                }
                if (i != r - 1) {
                    sb.append("D");
                }
            }
        } else if (c % 2 == 1) {  // 열이 홀수라면 ㄹ y=x 기준 대칭 시킨 모양으로 방문
            for (int i = 0; i < c; i++) {
                for (int j = 0; j < r - 1; j++) {
                    if (i % 2 == 0) {
                        sb.append("D");
                    } else if (i % 2 == 1) {
                        sb.append("U");
                    }
                }
                if (i != c - 1) {
                    sb.append("R");
                }
            }
        }

        return sb.toString();
    }
}
```