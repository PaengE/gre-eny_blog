## Problem : https://www.acmicpc.net/problem/1992

## Approach

전형적인 분할정복(Divide & Conquer)문제이다. 다음과 같은 과정을 반복하면 답을 도출할 수 있다.

> 1. 현재 영역의 제일 좌상 요소를 기억한 후, 모든 영역을 검사한다.
>    1. 기억한 요소와 모든 영역이 같다면, 기억한 요소를 반환한다.
>    2. 다르다면, 정확히 4등분한다. (나누기 전 괄호로 감싼다.)
> 2. 나눠진 영역의 크기가 1x1이 될 때까지 1번 전체를 반복한다. 
>    1. 나눠진 영역의 크기가 1x1이면 그 요소를 반환한다.

## Code

```java
import java.io.*;

/**
 * no.1992 : 쿼드트리
 * hint : 4등분 분할 정복
 */
public class BOJ1992 {
    static int[][] arr;
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void dq(int startX, int startY, int size) throws IOException {
        int element = arr[startX][startY];
        if(size == 1){
            bw.write(element + "");
        } else {
            boolean check = true;
            for (int i = startX; i < startX + size; i++) {
                for (int j = startY; j < startY + size; j++) {
                    if(arr[i][j] != element){
                        check = false;
                        break;
                    }
                }
                if(!check)
                    break;
            }
            if(check)
                bw.write(element + "");
            else{
                bw.write("(");
                dq(startX, startY, size / 2);
                dq(startX, startY + size / 2, size / 2);
                dq(startX + size / 2 , startY, size / 2);
                dq(startX + size / 2, startY + size / 2, size / 2);
                bw.write(")");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        arr = new int[n][n];
        String[] s;

        for (int i = 0; i < n; i++) {
            s = br.readLine().split("");
            for (int j = 0; j < n; j++) {
                arr[i][j] = Integer.parseInt(s[j]);
            }
        }

        dq(0, 0, n);

        bw.flush();
        br.close();
        bw.close();
    }
}
```

