## 문제 원문 링크: https://www.acmicpc.net/problem/14888

## Approach

Backtracking 기법을 사용하여 모든 연산자의 모든 조합을 고려하는 BruteForce 문제이다.

연산자를 하나씩 늘려가며 값을 구해나가고 연산자를 다 사용하였을 때의 값이 최댓값/최솟값인지를 판별한다.

사용한 후 남은 연산자의 개수가 몇개 인지를 기록하여야 한다.

## Code

```java
/*
    no.14888 : 연산자 끼워넣기
    hint : 사용한 연산자는 true, 사용이 끝난 연산자는 false로 하여 backtracking
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ14888 {
    static int n, min, max;
    static int[] arr, op;
    static BufferedReader br;
    static String s;
    static StringTokenizer st;

    public static void cal(int res, int idx){
        if (idx == n - 1){
            if(res < min)
                min = res;
            if(res > max)
                max = res;
        }
        for(int j = 0; j < 4; j++){
            if(op[j] != 0){
                op[j]--;
                if (j == 0){
                    cal(res + arr[idx+1], idx+1);
                } else if (j == 1){
                    cal(res - arr[idx+1], idx+1);
                } else if (j == 2){
                    cal(res * arr[idx+1], idx+1);
                } else {
                    cal(res / arr[idx+1], idx+1);
                }
                op[j]++;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));

        s = br.readLine();
        n = Integer.parseInt(s);
        arr = new int[n];

        s = br.readLine();
        st = new StringTokenizer(s);
        for(int i = 0; i < n; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        op = new int[4];
        s = br.readLine();
        st = new StringTokenizer(s);
        for(int i = 0; i < 4; i++){
            op[i] = Integer.parseInt(st.nextToken());
        }

        min = 1000000001;
        max = -1000000001;

        cal(arr[0], 0);

        System.out.printf("%d\n%d", max, min);

        br.close();
    }
}

```

