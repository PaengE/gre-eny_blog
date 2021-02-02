## Problem : https://programmers.co.kr/learn/courses/30/lessons/60059

## Approach

> 2020 KAKAO BLIND RECRUITMENT 문제였다.
>
> 문제풀이의 핵심은 배열을 확장시키고, 흘러가듯 밀면서 비교한다는 점이다.
>
> 완전탐색으로도 통과는 가능하다고 한다.(?) TC에서 100ms 정도 걸린다는 글을 보았다.

1. 원본 lock 배열에 홈의 크기 hole를 구한다.
2. 원본 lock 배열에 상하좌우에 (key배열 사이즈 - 1) 만큼씩 padding을 주어 확장된 extendedLock 배열을 만든다. (주황색 사각형)
3. 확장된 extendedLock 배열에서 아래의 그림의 파란색 사각형처럼 겹쳐지는 최소부분부터 오른쪽으로 흘러가듯 밀면서 열쇠의 돌기와 자물쇠의 홈이 맞는지를 검사한다.

   - 이 때, 원본 lock 배열의 인덱스 부분(빨간색 사각형)인지를 검사해서 열쇠와 자물쇠가 맞물린다면 count++를 해준다.

   - 열쇠의 돌기와 자물쇠의 돌기가 맞으면 안되므로 그것 또한 체크한다.
4. hole과 count가 같다면 열쇠로 자물쇠를 열 수 있다는 뜻이다.
   그게 아니라면, 초록색 사각형처럼 시작 인덱스를 밑으로 한칸씩 내리면서 파란색 사각형처럼 다시 검사한다.
5. key를 오른쪽으로 90도 회전시킨 후, `3, 4과정`을 반복한다.

![자물쇠와 열쇠-1](C:\Users\82102\OneDrive\티스토리\Algorithm\Programmers\image\자물쇠와 열쇠-1.jpg)

## Code

```java
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class LockAndKey {
    public boolean solution(int[][] key, int[][] lock) {
        // lock 의 홈의 개수 계산
        int hole = 0;
        for (int i = 0; i < lock.length; i++) {
            for (int j = 0; j < lock.length; j++) {
                if (lock[i][j] == 0) {
                    hole++;
                }
            }
        }

        // lock 에 패딩을 주어 확장
        int[][] extendedLock = extendLock(lock, key.length);

        // 확장된 lock 에서 key 를 흘러가듯 밀면서 비교
        if (check(extendedLock, key, hole)) {
            return true;
        }

        // key 를 90도, 180도, 270도 회전시킨 후, 확장된 lock 에서 key 를 흘러가듯 밀면서 비교
        for (int i = 0; i < 3; i++) {
            key = rotateKey(key);
            if (check(extendedLock, key, hole)) {
                return true;
            }
        }

        return false;
    }

    // 확장된 lock 에서 주어진 key 를 흘러가듯 밀면서 홈과 일치하는 곳이 홈의 개수와 같은지를 검사
    private boolean check(int[][] extendedLock, int[][] key, int hole) {
        int extendedLockSize = extendedLock.length;
        int keySize = key.length;

        for (int i = 0; i < extendedLockSize - keySize + 1; i++) {
            for (int j = 0; j < extendedLockSize - keySize + 1; j++) {
                int count = 0;
                for (int k = 0; k < keySize; k++) {
                    for (int l = 0; l < keySize; l++) {
                        // (i + k, j + l)이 원래 lock 안의 인덱스라면
                        if (rangeInOriginalLock(i + k, j + l, extendedLockSize, keySize)) {
                            // key 의 돌기와 lock 의 홈이 맞으면 count++
                            if (extendedLock[i + k][j + l] == 0 && key[k][l] == 1) {
                                count++;
                            }
                            // key 의 돌기와 lock 의 돌기가 만나면 안되므로
                            // break 를 걸어야 하나 이중포문을 탈출하기가 마땅치 않아 간단히 오답처리를 위해 count--
                            else if (extendedLock[i + k][j + l] == 1 && key[k][l] == 1) {
                                count--;
                            }
                        }
                    }
                }

                if (count == hole) {
                    return true;
                }
            }
        }
        return false;
    }

    // 확장된 lock 에서 (x, y) 가 원래 lock 안의 위치인지를 판별
    private boolean rangeInOriginalLock(int x, int y, int extendedLockSize, int keySize) {
        if (x < keySize - 1 || extendedLockSize - keySize + 1 < x) {
            return false;
        }

        if (y < keySize - 1 || extendedLockSize - keySize + 1 < y) {
            return false;
        }

        return true;
    }

    // lock 에 패딩을 주어 확장시킴
    private int[][] extendLock(int[][] lock, int keySize) {
        int[][] extendLock = new int[lock.length + (keySize - 1) * 2][lock.length + (keySize - 1) * 2];
        for (int[] t : extendLock) {
            Arrays.fill(t, -1);
        }

        for (int i = keySize - 1; i < keySize - 1 + lock.length; i++) {
            for (int j = keySize - 1; j < keySize - 1 + lock.length; j++) {
                extendLock[i][j] = lock[i - keySize + 1][j - keySize + 1];
            }
        }

        return extendLock;
    }

    // 시계방향 90도 회전
    private int[][] rotateKey(int[][] key) {
        // 2차원배열 깊은 복사
        int[][] copyKey = Arrays.stream(key).map(int[]::clone).toArray(int[][]::new);

        int size = key.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                key[j][size - i - 1] = copyKey[i][j];
            }
        }

        return key;
    }

    @Test
    public void test() {
        Assertions.assertEquals(true,
                solution(new int[][]{{0, 0, 0}, {1, 0, 0}, {0, 1, 1}}, new int[][]{{1, 1, 1}, {1, 1, 0}, {1, 0, 1}}));
    }
}

```

