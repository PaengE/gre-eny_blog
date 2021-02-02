## Problem : https://programmers.co.kr/learn/courses/30/lessons/42628

## Approach

> 힙(Heap)이나 우선순위큐(PriorityQueue)를 사용하여 풀이가 가능한 문제이다.
>
> 나는 처음 문제를 보고 PriorityQueue와 Deque을 생각했다. 그러나 하나의 PriorityQueue에서는 최솟값과 최댓값을 한 번에 처리할 수 없었고, Deque는 정렬된 상태를 유지하는 방법을 찾지 못했다.

최소한의 자료구조를 가지고 문제를 풀고 싶었다. 내가 필요한 자료구조는 `정렬된 상태를 유지`하는 것과 `최댓값과 최솟값을 뽑을 수 있는` 자료구조였다.

그래서 생각한 것이 TreeSet이다. 간단하게 설명하자면, 기본적으로 오름차순으로 정렬되어 있는, 중복을 허용하지 않는 Set자료구조다. 물론 pollFirst(), pollLast()를 지원하여 최솟값과 최댓값을 뽑아낼 수 있다.

그래서 TreeSet을 사용하여 문제를 풀었고, 코드가 통과되었다.



그러나 다른 분들의 풀이를 보던 중, 큐 안에 중복된 숫자가 동시에 존재할 수 있다는 조건을 보았고, 내 코드는 틀림을 깨달았다.

그래서 TreeSet과 같은 기능을 하고, 단지 `중복을 허용할 수 있게` 하기위해 ArrayList를 사용하여 중복을 허용해 주었다.

## Code

```java
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class DoublePriorityQueue {
    public int[] solution(String[] operations) {
        var list = new ArrayList<Integer>();

        for (String str : operations) {
            String[] s = str.split(" ");
            if (s[0].equals("I")) {
                list.add(Integer.parseInt(s[1]));
            } else {
                if (list.isEmpty()) {
                    continue;
                }
                Collections.sort(list);
                if (s[1].equals("1")) {
                    list.remove(list.size() - 1);
                } else {
                    list.remove(0);
                }
            }
        }

        Collections.sort(list);
        return list.isEmpty() ? new int[]{0, 0} : new int[]{list.get(list.size() - 1), list.get(0)};
    }



    // 중복된 숫자는 처리 불가능
//    public int[] solution(String[] operations) {
//        TreeSet<Integer> set = new TreeSet<>();
//
//        for (String str : operations) {
//            String[] s = str.split(" ");
//            if (s[0].equals("I")) {
//                set.add(Integer.parseInt(s[1]));
//            } else {
//                if (s[1].equals("1")) {
//                    set.pollLast();
//                } else {
//                    set.pollFirst();
//                }
//            }
//        }
//        if (set.isEmpty()) {
//            return new int[]{0, 0};
//        } else {
//            return new int[]{set.last(), set.first()};
//        }
//    }

    @Test
    public void test() {
        Assertions.assertArrayEquals(new int[]{7, 5}, solution(new String[]{"I 7", "I 5", "I -5", "D -1"}));
        Assertions.assertArrayEquals(new int[]{0, 0}, solution(new String[]{"I 16", "D 1"}));
    }
}

```

