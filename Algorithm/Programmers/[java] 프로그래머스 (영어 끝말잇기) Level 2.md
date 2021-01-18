## Problem : https://programmers.co.kr/learn/courses/30/lessons/12981

## Approach

Summer/Winter Coding(~2018) 문제이다.

중복된 단어인지를 확인하기 위해 HashSet을 사용했고,

현재 단어의 첫문자가 이전 단어의 끝문자가 다른지를 체크하였다.

> 몇 번째 사람이 틀렸는지는 (현재 단어의 순서 % 전체 사람 수) + 1이며,
>
> 몇 번째 차례때 틀렸는지는 (현재 단어의 순서 / 전체 사람 수) + 1로 구할 수 있다.

## Code

```java
import java.util.HashSet;

public class EnglishShiritori {
    public static void main(String[] args) {
        EnglishShiritori es = new EnglishShiritori();
        int n = 2;
//        String[] words = {"tank", "kick", "know", "wheel", "land", "dream", "mother", "robot", "tank"};
//        String[] words = {"hello", "observe", "effect", "take", "either", "recognize", "encourage", "ensure", "establish", "hang", "gather", "refer", "reference", "estimate", "executive"};
        String[] words = {"hello", "one", "even", "never", "now", "world", "draw"};

        int[] t = es.solution(n, words);

        System.out.println(t[0] + " " + t[1]);

    }

    public int[] solution(int n, String[] words) {
        var set = new HashSet<String>();

        set.add(words[0]);
        for (int i = 1; i < words.length; i++) {
            if (words[i - 1].charAt(words[i - 1].length() - 1) != words[i].charAt(0)) {
                return new int[]{(i % n) + 1, i / n + 1};
            }

            if (!set.add(words[i])) {
                return new int[]{(i % n) + 1, i / n + 1};
            }
        }

        return new int[]{0, 0};
    }
}

```

