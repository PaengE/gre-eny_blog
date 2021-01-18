## Problem : https://programmers.co.kr/learn/courses/30/lessons/17684

## Approach

2018 KAKAO BLIND RECRUITMENT 문제였다.

우선 'A : 1' ~ 'Z : 26' 까지 hashmap에 put한 후, 주어진 문자열의 앞부터 한 글자씩 순회를 시작한다.

1. 현재 s가 hashmap에  존재한다면 계속하여 문자열을 하나씩 붙여나간다.
2. hashmap에 없는 문자가 생긴다면, 그 문자열을 hashmap에 index+1로 put한 후, 그 바로 전 문자열의 value값을 결과리스트에 추가한다.
3. 그런 후 바로 전 문자열부터 다시 2번 과정을 주어진 문자열의 끝까지 반복한다.
4. 마지막만 예외처리를 해주면 된다.

## Code

```java
import java.util.ArrayList;
import java.util.HashMap;

public class Compression {
    static HashMap<String, Integer> map = new HashMap<>();
    public static void main(String[] args) {
        String msg = "ABABABABABABABAB";
        Compression c = new Compression();
        int[] ans = c.solution(msg);

        for (int i = 0; i < ans.length; i++) {
            System.out.print(ans[i] + " ");
        }
        System.out.println();

    }

    public int[] solution(String msg) {
        mapInit();

        int index = 27;
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < msg.length(); i++) {
            String s = String.valueOf(msg.charAt(i));
            int j = i;
            // 현재 s가 hashmap에 존재한다면 계속하여 문자열을 붙여서 구성해나감
            while (map.containsKey(s)) {
                if (++j >= msg.length()) {
                    break;
                }
                s += String.valueOf(msg.charAt(j));
            }
            // 다음 시작은 hashmap에 없는 문자부터 시작해야하므로 
            i = j - 1;

            // 마지막 문자까지 않았을 경우
            if (j != msg.length()) {
                // hashmap에 put한 후, 출력 리스트에 저장함
                map.put(s, index++);
                list.add(map.get(s.substring(0, s.length() - 1)));
            }
            // 마지막 문자까지 갔을 경우
            else {
                // 출력리스트에 저장함
                list.add(map.get(s));
            }
        }
        return list.stream().mapToInt(i -> i).toArray();
    }

    static void mapInit() {
        for (int i = 0; i < 27; i++) {
            map.put(String.valueOf((char) ('A' + i)), i + 1);
        }
    }
}

```

