## Problem : https://programmers.co.kr/learn/courses/30/lessons/68936

## Approach

일단 이 문제는 `백준 1992번 쿼드트리`와 유사한 문제이다. 백준 문제의 내 풀이는 다음 링크로 가면 된다.

[java 백준 1992 (쿼드트리) Silver 1](https://gre-eny.tistory.com/92)

일단 풀이의 방향은 주어진 영역의 모든 곳을 탐색하여 하나의 요소로 통일되어있지 않다면 영역을 4등분 하여 다시 탐색하고, 하나의 요소로 통일되어 있거나 영역의 크기가 1 x 1 인 경우 해당 요소의 count 를 늘리고 종료하면 된다.

주석을 보면서 코드를 본다면 이해하기 쉬울 것이다.

## Code

```java
public class QuadCompression {
    static int[] answer = {0, 0};
    public static void main(String[] args) {
        int[][] arr = {{1, 1, 1, 1, 1, 1, 1, 1},
                {0, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 1, 1, 1, 1},
                {0, 1, 0, 0, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 1, 0, 0, 1},
                {0, 0, 0, 0, 1, 1, 1, 1}};

        QuadCompression qc = new QuadCompression();
        int[] a = qc.solution(arr);

        for (int t : a) {
            System.out.println(t);
        }
    }

    public int[] solution(int[][] arr) {
        dq(0, 0, arr.length, arr);

        return answer;
    }

    static void dq(int sx, int sy, int size, int[][] arr) {
      // 주어진 영역의 맨앞 요소
        int element = arr[sx][sy];

      // 영역의 size 가 1인 경우
        if (size == 1) {
            answer[element] += 1;
        } 
      // 영역의 size 가 2이상일 경우
      else {
            boolean same = true;
        // 주어진 영역의 모든 요소를 검사
            for (int i = sx; i < sx + size; i++) {
                for (int j = sy; j < sy + size; j++) {
                    if (arr[i][j] != element) {
                        same = false;
                        break;
                    }
                }

                if (!same) {
                    break;
                }
            }

        // 주어진 영역이 모두 같은 요소로 통일되어있다면
            if (same) {
                answer[element] += 1;
            } 
        // 그렇지 않다면 4등분하여 반복
        else {
                int half = size / 2;
                dq(sx, sy, half, arr);
                dq(sx, sy + half, half, arr);
                dq(sx + half, sy, half, arr);
                dq(sx + half, sy + half, half, arr);
            }
        }
    }
}
```

