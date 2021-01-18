## Problem : https://programmers.co.kr/learn/courses/30/lessons/12951

## Approach

처음에는 공백문자(" ") 한 칸을 기준으로 split을 하여 첫 문자만 UpperCase로 나머지는 LowerCase로 하여 반환하였다. 

그런데 공백문자가 한 칸이 아닌 두 칸이상 공백문자가 연속된 테스트케이스가 존재하여 이 방법은 잘못됐다.



그래서 일단 주어진 문자열 s를 toLowerCase()로 모두 소문자로 바꾸고, i - 1번째 문자가 공백문자면 i번째 문자를 UpperCase로 변환하여 StringBuilder에 붙이고, 아닐 경우엔 그대로 StringBuilder에 붙인다.

첫 문자가 공백이면, 다음 문자를 UpperCase로 바꿔 붙인다.

## Code

```java
public class JadenCaseString {
    public static void main(String[] args) {
        JadenCaseString jcs = new JadenCaseString();

        String s = "for  the last week";

        System.out.println(jcs.solution(s));

    }

    public String solution(String s) {
        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder();

        sb.append(String.valueOf(s.charAt(0)).toUpperCase());
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i - 1) == ' ') {
                sb.append(String.valueOf(s.charAt(i)).toUpperCase());
            } else {
                sb.append(String.valueOf(s.charAt(i)));
            }
        }
        return sb.toString();
    }


//    연속된 공백을 못잡는 코드
//    public String solution(String s) {
//        String[] str = s.toLowerCase().split("[ ]");
//        for (int i = 0; i < str.length; i++) {
//            System.out.println("str[i] = " + str[i]);
//        }
//
//        StringBuilder sb = new StringBuilder();
//        for (String t : str) {
//            String c = String.valueOf(t.charAt(0)).toUpperCase();
//            sb.append(c).append(t.substring(1, t.length()) + " ");
//        }
//
//        return sb.substring(0, sb.length() - 1).toString();
//    }
}

```

