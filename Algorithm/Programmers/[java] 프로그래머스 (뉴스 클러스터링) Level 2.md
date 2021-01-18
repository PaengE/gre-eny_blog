## Problem : https://programmers.co.kr/learn/courses/30/lessons/17677

## Approach

2018 KAKAO BLIND RECRUITMENT 문제였다.

주어진 문자열 str1, str2 을 각각 두글자씩 잘라 list1, list2를 구성한다.

list1의 각 요소들을 list2에도 존재하는지를 검사한다.

존재한다면 그 요소는 str1, str2의 교집합이 될 것이다. (그런 후, list2에서 그 요소를 제거한다. 제거하지 않으면 중복된 요소도 교집합에 추가되기 때문이다.)

그리고 집합의 성질을 이용하여 합집합을 구해준다. 집합 A, B에 대하여

(A와 B의 합집합 원소 개수) = (A의 원소 개수) + (B의 원소 개수) - (A와 B의 교집합 원소 개수) 이 성질을 이용한다.

## Code

```java
import java.util.ArrayList;

public class NewsClustering {
    public static void main(String[] args) {
        String str1 = "aaaaa";
        String str2 = "aaa";

        NewsClustering nc = new NewsClustering();
        System.out.println(nc.solution(str1, str2));
    }

    public int solution(String str1, String str2) {
        str1 = str1.toLowerCase();
        str2 = str2.toLowerCase();
        var list1 = new ArrayList<String>();
        var list2 = new ArrayList<String>();

        for (int i = 1; i < str1.length(); i++) {
            Character c1 = str1.charAt(i - 1);
            Character c2 = str1.charAt(i);
            if (Character.isLetter(c1) && Character.isLetter(c2)) {
                String s = String.valueOf(c1) + String.valueOf(c2);
                list1.add(s);
            }
        }
        for (int i = 1; i < str2.length(); i++) {
            Character c1 = str2.charAt(i - 1);
            Character c2 = str2.charAt(i);
            if (Character.isLetter(c1) && Character.isLetter(c2)) {
                String s = String.valueOf(c1) + String.valueOf(c2);
                list2.add(s);
            }
        }

        int intersection = 0;
        int sizeA = list1.size();
        int sizeB = list2.size();

        if (sizeA == 0 && sizeB == 0) {
            return 65536;
        }

        for (int i = 0; i < sizeA; i++) {
            if (list2.indexOf(list1.get(i)) != -1) {
                intersection++;
                list2.remove(list1.get(i));
            }
        }
        int union = sizeA + sizeB - intersection;

        return (int)(((float)intersection / (float) union) * 65536);
    }
}

```

