## Problem : https://www.acmicpc.net/problem/1891



## Approach

> `분할 정복(Divide & Conquer)`을 구현한 문제이다.



아래와 같이 두 가지 기능을 구현하여야 한다. 그리고 순서대로 수행하면서 답을 도출한다.

1. 주어진 사분면 숫자의 위치 (x, y)를 찾는다. 그리고 그 위치에 주어진 이동 횟수를 더한 위치를 찾는다.
2. 구한 위치의 사분면 숫자를 구한다.



`1번 기능`의 경우, 주어진 사분면 숫자를 앞에서부터 한 자리씩 읽으면서 대략의 위치를 가늠한다. (사분면 숫자를 보고 (a, b) ~ (a', b') 범위를 찾는다.)

주어진 좌표평면의 한 변의 길이는 (1 << d) 인 정사각형이다. 그러므로 findLoc() 메소드를 d번 호출하면 된다.



`2번 기능`의 경우, 좌표를 기준으로 한 변의 길이가 (1 << d)인 정사각형에서의 대략의 위치를 가늠한다. (몇 사분면인지)

d의 숫자를 하나씩 줄이며(한변의 길이를 계속 반으로 줄이면서) 0이 될 때까지 반복한다. (이 때 한 변의 길이는 1이 된다.)



사분면 숫자와 좌표를 구하는 자세한 구현 방법은 아래 코드를 참조하면 좋을 것이다.

## Code

```java
import java.io.*;
import java.util.StringTokenizer;

/**
 *  No.1891: 사분면
 *  Hint: Divide & Conquer + 구현
 */

public class BOJ1891 {
    static int d;
    static long tx, ty;
    static int[] arr;
    static StringBuilder sb = new StringBuilder();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        d = Integer.parseInt(st.nextToken());
        arr = new int[d];
        String num = st.nextToken();
        for (int i = 0; i < d; i++) {
            arr[i] = num.charAt(i) - '0';
        }
        st = new StringTokenizer(br.readLine());
        long moveH = Long.parseLong(st.nextToken());    // 가로 이동
        long moveV = Long.parseLong(st.nextToken());    // 세로 이동

        long n = 1L << d;
        findLoc(0,0,0, n, n);
        tx -= moveV;
        ty += moveH;

        if (tx >= 0 && tx < n && ty >= 0 && ty < n) {
            findNum(d, tx, ty);
        } else {
            bw.write("-1");
        }

        bw.write(sb.toString());
        bw.close();
        br.close();
    }

    // 주어진 위치로 사분면 찾기
    static void findNum(int depth, long tx, long ty) {
        if (depth == 0) {
            return;
        }

        long half = 1L << (depth - 1);

        if (tx < half && ty >= half) {
            sb.append("1");
            findNum(depth - 1, tx, ty - half);
        } else if (tx < half && ty < half) {
            sb.append("2");
            findNum(depth - 1, tx, ty);
        } else if (tx >= half && ty < half) {
            sb.append("3");
            findNum(depth - 1, tx - half, ty);
        } else if (tx >= half && ty >= half) {
            sb.append("4");
            findNum(depth - 1, tx - half, ty - half);
        }
    }


    // 주어진 사분면 숫자의 위치 찾기
    static void findLoc(int numIdx, long lx, long ly, long rx, long ry) {
        if (numIdx == d) {
            tx = lx;
            ty = ly;
            return;
        }

        int num = arr[numIdx];
        if (num == 1) { // 1사분면
            findLoc(numIdx + 1, lx, (ly + ry) / 2, (lx + rx) / 2, ry);
        } else if (num == 2) {  // 2사분면
            findLoc(numIdx + 1, lx, ly, (lx + rx) / 2, (ly + ry) / 2);
        } else if (num == 3) {  // 3사분면
            findLoc(numIdx + 1, (lx + rx) / 2, ly, rx, (ly + ry) / 2);
        } else if (num == 4) {  // 4사분면
            findLoc(numIdx + 1, (lx + rx) / 2, (ly + ry) / 2, rx, ry);
        }
    }
}
```

