## Problem : https://programmers.co.kr/learn/courses/30/lessons/64065

## Approach

2019 카카오 개발자 겨울 인턴십 문제이다. Level2에 위치한 카카오문제들은 가끔보면 Level2가 아닌것 같은데 이 문제는 주어진 문자열만 잘 처리하면 꽤 쉬운 문제였다.

mySolution은 내가 테스트를 통과한 코드이고, simpleSolution은 다른 분의 풀이를 발췌해 온 것이다.
둘다 Set을 사용한 접근법은 비슷하다. 문자열을 처리하는 과정이 더 간단하여 도움이 될 것 같아 첨부한다.



내 풀이는 {}를 "로 치환한 후, "를 기준으로 split하여 String 배열을 구성한다. 그리고 String 배열을 요소의 길이 순으로 오름차순으로 정렬한다.

그리고 각 문자열의 요소를 공백을 제거한 후, 콤마를 기준으로 split한다. 그러면 공백으로만 이루어진 요소를 제외한 모든 요소를 List에 추가한다. (오름차순으로 정렬된 String배열을 추가하므로 리스트에서도 0번째요소는 길이가 제일 짧다.)

리스트의 첫 요소는 answer의 첫 자리로 담고(요소의 크기가 1이니까) set에 추가한다. 나머지 list의 요소를 순회하면서 Set에 없는 숫자는 answer의 다음 자리가 된다.(리스트 각 요소의 길이는 자연스럽게 1, 2, 3, ... 이다.)

위 과정을 반복하여 answer을 반환한다.

> 생각해보면 코드의 길이를 줄이려면 굳이 contains()를 사용하지 않고 add() 를 사용하는게 더 짧아 보일 순 있을 것 같다.
>
> add() 메소드의 구현체를 보면 어차피 contains()를 사용하므로 성능상으로는 별차이가 없을 것 같고, add() 메소드의 반환값이 어차피 boolean 이니까 가능하다.(set 추가에 성공하면 true, 중복되어 추가에 실패하면 false)

## Code

```java
import java.util.*;

public class Tuple {
    public static void main(String[] args) {

        String s = "{{1,2,3},{2,1},{1,2,4,3},{2}}";

        Tuple t = new Tuple();
//        t.mySolution(s);
        t.simpleSolution(s);
    }

    public int[] simpleSolution(String s) {
        var set = new HashSet<String>();
        String[] str = s.replaceAll("[\\{\\}]", " ")
                .trim().split(" , ");

        Arrays.sort(str, Comparator.comparingInt(o -> o.length()));

        int[] answer = new int[str.length];
        int idx = 0;
        for (String s1 : str) {
            for (String s2 : s1.split(",")) {
                if (set.add(s2)) {
                    answer[idx++] = Integer.parseInt(s2);
                }
            }
        }

        return answer;
    }

    public int[] mySolution(String s) {

        s = s.substring(1, s.length() - 1)
                .replaceAll("[\\{\\}]", "\"");

        String[] str = s.split("\"");

        Arrays.sort(str, Comparator.comparingInt(o -> o.length()));

        var list = new ArrayList<String[]>();
        for (int i = 1; i < str.length; i++) {
            String[] z = str[i].replace(" ", "").split(",");

            if (z.length != 0) {
                list.add(z);
            }
        }

        var set = new TreeSet<String>();
        int[] answer = new int[list.size()];
        set.add(list.get(0)[0]);
        answer[0] = Integer.parseInt(list.get(0)[0]);

        for (int i = 1; i < list.size(); i++) {
            String[] t = list.get(i);

            for (int j = 0; j < t.length; j++) {
                if (!set.contains(t[j])) {
                    set.add(t[j]);
                    answer[i] = Integer.parseInt(t[j]);
                }
            }
        }

//        for (int a : answer) {
//            System.out.print(a + " ");
//        }
//        System.out.println();

        return answer;
    }
}

```

