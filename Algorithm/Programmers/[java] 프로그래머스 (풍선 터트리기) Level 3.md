## Problem : https://programmers.co.kr/learn/courses/30/lessons/68646

## Approach

월간 코드 챌린지 시즌1 문제였다.

> 문제의 조건은 다음과 같다.
>
> 1. 임의의 **인접한** 두 풍선을 고른 뒤, 두 풍선 중 하나를 터트립니다.
> 2. 터진 풍선으로 인해 풍선들 사이에 빈 공간이 생겼다면, 빈 공간이 없도록 풍선들을 중앙으로 밀착시킵니다.
> 3. 인접한 두 풍선 중에서 **번호가 더 작은 풍선**을 터트리는 행위는 최대 1번만 할 수 있습니다. 즉, 어떤 시점에서 인접한 두 풍선 중 번호가 더 작은 풍선을 터트렸다면, 그 이후에는 인접한 두 풍선을 고른 뒤 번호가 더 큰 풍선만을 터트릴 수 있습니다.

이 문제를 풀기에 있어 알고리즘보다는 생각을 하는 것이 더 중요하다.



결론은 `주어진 배열의 양쪽 끝(left와 right)은 항상 남길 수 있다.`

왼쪽 끝을 가지고 예를 든다면, 

1. 배열의 1번째부터 length - 1번째까지의 풍선들을 모두 `번호가 더 큰 풍선`을 터트린다.
2. 그러면 마지막에 남은 두 풍선은 `0번째 풍선`과 `(1 ~ length - 1) 풍선 중 최댓값 풍선`이 남는다.
3. 따라서, 한번은 번호가 더 작은 풍선을 터트리는 것이 가능하기에, 0번째 풍선의 번호가 크든 작든 `(1 ~ length - 1) 풍선 중 최댓값 풍선`을 터트리는 것이 가능하다.
4. 그렇다면 남은건 0번째 풍선이다.



오른쪽 끝도 마찬가지이다.

그리고 i번째 요소가 left와 right중 가까온 쪽의 요소보다 작다면, i번째 요소 또한 남길 수 있다.

예를들어,

1. `-10 10 -20 40 50 -60`이 있을 때, -20이 가까운 끝쪽 -10보다 작다. 이럴 경우,
2. `-10 10`에서 큰걸 터트려서 `-10`이 남고, `-10 -20`에서 큰걸 터트려서 `-20`이 남는다.
3. `40 50 -60`중 큰거를 계속 터트리면 `-60`이 남는다.
4. `-20 -60`중 작은거를 터트리면 `-20`이 남는다.

마찬가지로 반대편도 동일하다. 



위처럼, i번째 요소가 left와 right중 가까온 쪽의 요소보다 작다면(여기서는 left), left 혹은 right를 i번째 요소로 갱신한다.(여기서는 left)

모든 a배열을 순회할 때까지, 혹은 left와 right가 같을 때까지 반복한다.

주의할 점은, left와 right가 같아진다면, 임의의 수 X가 left와 right 모두보다 작은 `left X right`형태에서  left와 right가 갱신된 것이므로 답 ans에서 하나를 뺀다.

## Code

```java
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BlastBalloon {
    public int solution(int[] a) {
        if (a.length == 1) {
            return 1;
        }

        int left = a[0];    // a의 왼쪽 끝
        int right = a[a.length - 1];    // a의 오른쪽 끝
        int ans = 2;

        for (int i = 1; i < a.length - 1; i++) {
            // 왼쪽 끝+1의 요소가 left보다 작으면 남길 수 있음
            if (a[i] < left) {
                left = a[i];
                ans++;
            }

            // 오른쪽 끝-1의 요소가 right보다 작으면 남길 수 있음
            if (a[a.length - 1 - i] < right) {
                right = a[a.length - 1 - i];
                ans++;
            }

            // left == right 면 중복된 답이 들어갔으므로 ans--
            if (left == right) {
                ans--;
                break;
            }
        }
        return ans;
    }

    @Test
    public void test() {
        Assertions.assertEquals(3, solution(new int[]{9, -1, -5}));
        Assertions.assertEquals(3, solution(new int[]{-10, 10, -20, 30, 40, -60}));
        Assertions.assertEquals(7, solution(new int[]{-16, 27, 65, -100, 58, -92, -71, -68, -61, -33}));
    }
}
```

