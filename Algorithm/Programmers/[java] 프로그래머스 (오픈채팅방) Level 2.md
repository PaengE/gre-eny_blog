## Problem : https://programmers.co.kr/learn/courses/30/lessons/42888

## Approach

2019 KAKAO BLIND RECRUITMENT 문제였다.

일단 나는 HashMap을 이용하여 구현했고, 자바에서 HashMap을 사용하면 key가 존재해도 같은 key에 대해 put을 진행하면, 나중에 put된 value로 업데이트를 해준다.



따라서 각 문자열을 공백문자(" ")로 split한 0번째 요소가 "Leave"가 아니면, HashMap에 (key, value)를 (1번째 요소, 2번째 요소)로 지정하여 put 해준다.

그런 후, 0번째 요소가 "Leave"이거나 "Enter"이면 다른 리스트에 0번째 요소와 1번째 요소를 저장해놓는다.

모든 문자열에 대해 처리가 완료됐으면, 리스트에 있는 커맨드들을 하나씩 뽑아와 result를 구성한다.

## Code

```java
import java.util.ArrayList;
import java.util.HashMap;

public class OpenChattingRoom {
    public static void main(String[] args) {
        OpenChattingRoom ocr = new OpenChattingRoom();
        String[] record = {"Enter uid1234 Muzi", "Enter uid4567 Prodo", "Leave uid1234", "Enter uid1234 Prodo", "Change uid4567 Ryan"};
        ocr.solution(record);
    }

    public String[] solution(String[] record) {
        var hm = new HashMap<String, String>();
        ArrayList<ArrayList<String>> list = new ArrayList<>();

        for (String s : record) {
            String[] t = s.split(" ");

            if (!t[0].equals("Leave")) {
                hm.put(t[1], t[2]);
            }

            if (t[0].equals("Enter") || t[0].equals("Leave")) {
                list.add(new ArrayList<>(){{
                    add(t[0]);
                    add(t[1]);
                }});
            }
        }

        String[] answer = new String[list.size()];

        for (int i = 0; i < list.size(); i++) {
            answer[i] = hm.get(list.get(i).get(1)) + "님이 ";
            if (list.get(i).get(0).equals("Enter")) {
                answer[i] += "들어왔습니다.";
            } else {
                answer[i] += "나갔습니다.";
            }
        }

        for (int i = 0; i < answer.length; i++) {
            System.out.println(answer[i]);
        }
        return answer;
    }
}

```

