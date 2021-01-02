## Problem : https://programmers.co.kr/learn/courses/30/lessons/42747

## Approach

이 문제에 있어서 주의할 점은 답이 citations에 없는 요소가 될 수 있다는 점이다.

예를 들어 citations = {6, 6, 6, 6} 이라면 답은 4가 된다.

필자는 citations 배열을 오름차순 정렬 한 후, 배열안에 i보다 크거나 같은 수가 i개 있는지를 검사하여, 가장 큰 i를 리턴하는 식으로 풀이했다.

시간복잡도는 O(n^2)이지만, citations의 최대길이가 1,000 이라 가능한 풀이인 것 같다.

## Code

```java
import java.util.Arrays;

public class H_Index {
    public static void main(String[] args) {
        int[] citations = {10,9,4,1,1};

        System.out.println(new H_Index().solution(citations));
    }

    public int solution(int[] citations) {
        Arrays.sort(citations);
        int size = citations.length;
        int h_idx = 0;
        int count = 0;

        for (int i = 0; i <= size; i++) {
            count = 0;
            for (int j = 0; j < size; j++) {
                if (i <= citations[j]) {
                    count += 1;
                }
                if (count >= i) {
                    h_idx = i;
                    break;
                }
            }
            if (count < i) {
                break;
            }
        }
        return h_idx;
    }

    /*      테스트케이스와 기댓값

            [12, 11, 10, 9, 8, 1]       5
            [6, 6, 6, 6, 6, 1]          5
            [4, 4, 4]                   3
            [4, 4, 4, 5, 0, 1, 2, 3]    4
            [10, 11, 12, 13]            4
            [3, 0, 6, 1, 5]             3
            [0, 0, 1, 1]                1
            [0, 1]                      1
            [10, 9, 4, 1, 1]            3
     */
}

```

