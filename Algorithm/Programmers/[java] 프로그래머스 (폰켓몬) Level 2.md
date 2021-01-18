## Problem : https://programmers.co.kr/learn/courses/30/lessons/1845

## Approach

N개의 폰켓몬 중 N / 2개의 폰켓몬을 최대 종류로 가져가는 법을 찾는 문제이다.

간단하게 N개의 포켓몬을 모두 set에 넣는다. 그런 후 set의 size가 N / 2보다 작으면 set의 size를 반환하면 되고,

아니라면 N / 2를 반환하면 된다.

간단한 문제이다

## Code

```java
import java.util.Arrays;
import java.util.HashSet;

public class Poncketmon {
    public static void main(String[] args) {
        int[] number = {3,1,2,3};

        Poncketmon p = new Poncketmon();
        System.out.println(p.solution(number));

    }

    public int solution(int[] number) {
        Integer b[] = Arrays.stream(number).boxed().toArray(Integer[]::new);
        var set = new HashSet<Integer>(Arrays.asList(b));

        if (set.size() < number.length / 2) {
            return set.size();
        } else {
            return number.length / 2;
        }
    }
}

```

