## 문제 원문 링크 : https://www.acmicpc.net/problem/9663

## Approach

대표적인 BruteForce, Backtracking 알고리즘 문제이다.

첫번째 열부터 숫자를 하나씩 놓으면서 그 다음 열에 놓을 수 없는지를 검사하여 놓을 수 있는 총 개수를 구하는 문제다.

퀸을 놓으려면 가로, 세로, 대각선에 퀸을 놓아서는 안된다.

arr[] 배열은 몇 번째 열, 몇번째 행에 퀸을 놓은지를 기록한 것이다. 예를 들어, arr[i] = j 라 할 때, i번째 열에 j번째 행에 퀸이 놓아져 있다는 뜻이다.

```java
for(int i = 0; i < n; i++){
  arr[lv] = i;
  if (isPossible(lv)){
		backtracking(lv + 1);
  }
}
```

루프를 이렇게 처리함으로써 가로에 여러개의 퀸을 놓는 경우를 제할 수 있다.

```java
static boolean isPossible(int lv){
  for (int i = 0; i < lv; i++){
    if (arr[i] == arr[lv] || Math.abs(arr[i] - arr[lv]) == Math.abs(lv - i)) {
    	return false;
    }
  }
  return true;
}
```

arr[i] == arr[lv] 부분으로 같은 세로에 놓는 경우를 제하며, 

Math.abs(arr[i] - arr[lv]) == Math.abs(lv - i) 부분을 통하여 같은 대각선에 놓아져 있는 경우를 제한다.

이후 lv이 n이 되어지면 정상적으로 다 놓았을 경우가 생긴 것이므로 count++ 를 해준다.

## Code

```java
/*
    no.9663: N-Queen
    hint: 위에서부터 퀸을 놓아보고 놓을 수 있는지(일직선 상에 있거나 대각선에 있거나) 검사 후
        놓을 수 있으면 다음 줄로, 놓을 수 없으면 backtracking
 */
import java.io.*;

public class BOJ9663 {
    static int n, count = 0;
    static int[] arr;
    static BufferedReader br;

    static void backtracking(int lv) throws IOException{
        if (lv == n){
            count++;
        } else {
          // 가로
            for(int i = 0; i < n; i++){
                arr[lv] = i;
                if (isPossible(lv)){
                    backtracking(lv + 1);
                }
            }
        }
    }
    static boolean isPossible(int lv){
        for (int i = 0; i < lv; i++){
          // 세로와 대각선
            if (arr[i] == arr[lv] || Math.abs(arr[i] - arr[lv]) == Math.abs(lv - i)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        n = Integer.parseInt(s);
        arr = new int[n];

        backtracking(0);

        System.out.println(count);
        br.close();
    }
}

```

