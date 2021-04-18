## Problem : https://programmers.co.kr/learn/courses/30/lessons/49994

> Summer/Winter Coding(~2018) 문제였다. 두 가지 방법이 있지만 나는 여기서의 2번째 방법을 이용했다.
>
> 1. 좌표평면 크기의 visited 배열을 선언하여 실제로 방문하였는지를 체크한다.
> 2. HashSet을 이용하여 `선분을 나타내는 무언가`를 set에 양방향으로 추가하고 size / 2한다.

2번 풀이로 푸는게 매우 간단하다.

중복을 걸러야 하므로 int[] 배열 보다는, 두 점을 String 으로 묶어 set에 양방향으로 넣은 후, size / 2를 하면 정답이 된다.

## Code

```java
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

public class VisitLength {
    public int solution(String dirs) {
        HashSet<String> set = new HashSet<>();
        int cx = 0, cy = 0;

        for (int i = 0; i < dirs.length(); i++) {
            char c = dirs.charAt(i);
            int nx = cx;
            int ny = cy;

            if (c == 'U' && cy + 1 <= 5) {
                cy++;
            } else if (c == 'D' && cy - 1 >= -5) {
                cy--;
            } else if (c == 'L' && cx + 1 <= 5) {
                cx++;
            } else if (c == 'R' && cx - 1 >= -5) {
                cx--;
            }

            if (nx == cx && ny == cy) {
                continue;
            }

            set.add(cx + "" + cy + "" + nx + "" + ny);
            set.add(nx + "" + ny + "" + cx + "" + cy);
        }

        return set.size() / 2;
    }


    @Test
    public void test() {
        Assertions.assertEquals(7, solution("ULURRDLLU"));
        Assertions.assertEquals(7, solution("LULLLLLU"));
    }
}

```

