## Problem : https://programmers.co.kr/learn/courses/30/lessons/42577

## Approach

처음엔 임의의 문자열 하나 A를 나머지 문자열(B, C, D, ...)에서 찾는 indexOf() 를 활용하여 반환값이 0인 것을 찾는 naive한 방법을 사용했었다. (indexOf()의 반환값이 0이면, 그 문자열로 시작한다는 뜻이므로)

그래서 Set 에 주어진 문자열을 모두 넣은 후, 하나씩 검사하는 방식을 썼었다. 이 방법 또한 문제의 테스트케이스는 모두 통과하였으나 O(n^2)라는 시간복잡도를 가지므로, 입력의 크기가 커진다면 시간이 오래걸리는 풀이방법이다.

다른 분들의 코드를 읽어보면서 O(nlogn) 시간복잡도의 풀이법을 설명해 보겠다.

1. 일단, 주어진 문자열을 정렬한다. (문자열의 정렬(사전순 정렬)은 숫자의 정렬과 다르다. 예를 들어, {"12","1234","1235","567","88"} 을 정렬하면, {"12", "1234", "1235", "567", "88"})이 된다.)
2. 그렇게 되면, (i + 1)번째 문자열의 시작이 i번째 문자열로 시작하는지를 판단하면 된다. (startsWith() 사용)
3. 단 하나라도 있으면 false를 리턴하고, 조건에 걸리는 것이 하나도 없으면 true를 리턴한다.

위의 방법으로는 정렬과 선형탐색을 하므로 O(nlogn)의 시간복잡도를 가지게 된다.

## Code

```java
import java.util.Arrays;

public class PhoneNumberList {
    public static void main(String[] args) {
        String[] phone_book = {"12","1234","1235","567","88"};


        PhoneNumberList p = new PhoneNumberList();
        boolean flag = p.solution(phone_book);

        System.out.println("flag = " + flag);
    }

    // O(nlogn)
    public boolean solution(String[] phone_book) {
        int size = phone_book.length - 1;

        Arrays.sort(phone_book);

        for (int i = 0; i < size; i++) {
            if (phone_book[i + 1].startsWith(phone_book[i])) {
                return false;
            }
        }
        return true;

        /*  naive 한 방식 O(n^2)

        TreeSet<String> set = new TreeSet<>(Arrays.asList(phone_book));

        int size = phone_book.length;
        for (int i = 0; i < size && flag; i++) {
            Iterator it = set.iterator();
            int count = 0;

            while (it.hasNext()) {
                if (String.valueOf(it.next()).indexOf(phone_book[i]) == 0) {
                    count += 1;
                }

                if (count == 2) {
                    flag = false;
                    break;
                }
            }
        }

        */
    }
}

```

