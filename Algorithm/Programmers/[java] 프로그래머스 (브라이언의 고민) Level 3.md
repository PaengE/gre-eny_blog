## Problem : https://programmers.co.kr/learn/courses/30/lessons/1830

## Approach

2017 카카오코드 예선문제였다.

결과적으로 문제를 통과하진 못했다. 문제의 조건은 다음과 같다.

> - 광고글은 원래 문구에 다음 규칙을 적용하여 만들 수 있다.
>   - (규칙 1) 특정 단어를 선택하여 글자 사이마다 같은 기호를 넣는다. ex) `HELLO` -> `HaEaLaLaO`
>   - (규칙 2) 특정 단어를 선택하여 단어 앞뒤에 같은 기호를 넣는다. ex) `WORLD` -> `bWORLDb`
>   - 위의 두 가지 규칙은 한 단어에 모두 적용될 수 있지만 같은 규칙은 두 번 적용될 수 없다.
>   - 한 번 쓰인 소문자(특수기호)는 다시 쓰일 수 없다.
> - 마지막으로 원래 문구에 있던 공백을 제거한다.
> - 위의 규칙을 위반했을 때는 "invalid"를 출력한다.
>
> 광고문자열 A가 주어졌을 때, 원문 B를 구하는 문제이다.

광고문자열 A가 "HELLO"라고 하였을 때, 정답은 "HELLO", "H ELLO", "H E LLO", "HE LLO", ... , "H E L L O" 모두가 될 수 있다고 이해했다. 실제로 테스트케이스를 보면 위의 모든 케이스가 정답이 될 수 있다.

그러나 인터넷에서 찾은 많은 테케들이 통과됐음에도 코드가 통과가 안되어서 위의 예시가 맞나 싶다...

일단 나는 다음과 같은 방식으로 풀이를 하였다.

> 일단, 광고문자열 S에 공백이 있으면 안되므로, 공백검사를 한다.
>
> 그런 후, 문자열의 앞에서부터 문자열의 끝까지 순회하면서 풀었다. 
>
> 1. 주어진 문자열의 처음이(idx가) `소문자 a`일 때, 올바른 광고문자열이라면, 한 단어로, 무조건 규칙2를 적용한 것이다. 따라서,  현재 이후 문자열에서 `a`의 개수와 `마지막 a`의 위치(lastIdx)를 찾는다. 
>
>    1. `a`가 2개일 경우, `a ... a`에서 양쪽 끝 `a`를 제외한 `...`(idx+1 ~ lastIdx-1)부분을 부분문자열 subS을 만든다. (`a`가 2개가 아닐 경우, invalid)
>    2. 그런후 subS가 `규칙 1`을 적용시킨 문자열인지, 아니면 아무런 규칙도 적용시키지 않은 문자열인지를 검사한다.
>       - `규칙 1`을 적용시킨 것인지를 검사하는 것은 다음과 같다.
>       - 주어진 문자열 subS가 빈 문자열이거나, subS의 첫문자가 소문자라면 "invalid"
>       - 주어진 문자열 subS의 길이가 2이하라면, 모두 대문자여야 한다.
>       - 주어진 문자열 subS의 길이가 3이상이면, 문자열 subS는 `대소대...`형식이거나, `대대대...`형식이어야 올바른 광고문자열이다. 따라서 위 두 경우인지를 검사한다. (아니라면 invalid)
>       - 여기서 `대소대...` 형식일 경우, 소문자는 하나의 소문자로만 구성되어야 한다.
>    3. 맞다면, 문자열 s에서 대문자만을 추출하여 정답문자열 ans에 추가한다. (공백없이)
>    4. 다 추가하였다면, 단어의 끝이므로 공백문자(" ")를 추가하고, 인덱스를 재조정한다. (idx = lastIdx+1)
>
> 2. 주어진 문자열의 처음이(idx가) `대문자 A`이라면, 이후 문자열에 소문자가 나올 때 까지(idx+1이 소문자일 때까지), 정답문자열 ans에 `대문자A + 공백문자(" ")`를 추가한다. (그리고 인덱스를 재조정한다. idx++)
>
>    - 예를 들어, `AAAAbBbA`일 경우, 정답문자열에 `A A A A`를 추가하고 남은 `bBbA`를 처리하게끔 하였다. 
>    - 이때, `BBBBB`일 경우, 소문자가 없으므로 `B B B B B`를 추가한다.
>
>    1. (앞에서 걸러지지 않고 지금까지 올바른 광고문자열 인채로) `소문자b`를 찾았다면, idx의 글자는 대문자이다. (idx+1의 글자가 소문자임을 찾았으니까)
>    2. 그러면 `대소...`(idx ~ S.length) 부분문자열에서 `소문자b`의 개수를 찾는다.
>       1. `소문자b`의 개수가 2개라면(`규칙1`. `규칙2` 둘 중 하나가 적용된 것이지만, 나는 `규칙2`를 적용한 것으로 생각하고 문제를 풀었다. `규칙1`을 적용했을 때와 `규칙2`를 적용했을 때의 정답은 다르나, 실제 테스트케이스에서도 그렇듯 두개 모두 정답으로 인정한다고 하였다), idx부분의 `대문자`를 공백문자와 함께 ans에 추가한다. 
>          - 그런 후, 인덱스를 재조정한다. (idx = idx + 1) 그런 후, 다시 while loop의 처음으로 돌아가 제일 바깥쪽 1번(첫문자가 소문자일 때)으로 돌아간다.
>       2. `소문자b`의 개수가 1 혹은 3이상이라면, `규칙1`을 적용된 것이므로 `규칙1`이 적용된 것인지 검사한다.
>          - 따라서, 먼저 부분문자열이 `대소...소대`가 되게끔 부분문자열 subS를 만든다. (부분문자열이 `대소`인 상태라면 invalid)
>          - 그런 후 `대소...소대`문자열에 대해 `규칙1`을 검사하고, invalid가 아니라면 정답문자열 ans에 추가한 후, 단어의 끝이므로 마지막에 공백문자(" ")를 추가한다.
>          - 그리고 인덱스를 재조정한다. (idx = lastIdx + 2)
>
> 위의 흐름으로 문제를 풀었는데 모든 테케는 통과했다. 그런데 코드의 통과는 되지않았다.
>
> 단, 내가 가정한 기댓값은 `규칙이 하나도 적용되지 않은 연속된 대문자`가 있을 때, 나는 `연속된 대문자를 각각의 단어들`로 생각하여 사이사이 공백을 추가시켰다. 라는 것이다.

정답자가 많지 않은 문제라 반례도 찾기 힘들었지만, 찾아본 모든 반례는 통과를 하였다. 게시판에 보면 오답이 정답이된 케이스도 있어서 문제가 잘못됐다고 믿고싶다... 하루내내 고민했지만 다른 반례를 찾을 수 없어, 질문을 남기고 나중에 다시 생각해보기로 하였다.



#### **반례에 대해 틀린 부분이 있다면 댓글로 알려주세요!**

## Code(Failed)

```java
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

public class BriansTroubles {
    static HashSet<Character> set;
    public String solution(String sentence) {
        initSet();

        int length = sentence.length();
        int idx = 0;
        int lastIdx = 0;
        StringBuilder ans = new StringBuilder();
//        System.out.println("sentence = " + sentence);

        if (sentence.indexOf(" ") != -1) {
            return "invalid";
        }

        while (idx < length){
            if (idx == length - 1) {
                if (Character.isUpperCase(sentence.charAt(idx))) {
                    ans.append(sentence.charAt(idx));
                    return ans.toString().trim();
                } else {
                    return "invalid";
                }
            }

            // 첫문자가 소문자일 때
            if (Character.isLowerCase(sentence.charAt(idx))) {
                char c = sentence.charAt(idx);
                // 사용한 소문자이면 invalid
                if (!set.remove(c)) {
                    return "invalid";
                }

                // 규칙 2가 적용된 것이므로 무조건 2개여야함
                String s = sentence.substring(idx, length);
                if (countChar(s, c) != 2) {
                    return "invalid";
                }

                lastIdx = sentence.lastIndexOf(c);
                s = sentence.substring(idx + 1, lastIdx);
                // 주어진 단어 rule 1 check
                if (!checkRule1(s)) {
                    return "invalid";
                }

                // ans 에 붙이기
                for (int i = 0; i < s.length(); i++) {
                    if (Character.isUpperCase(s.charAt(i))) {
                        ans.append(s.charAt(i));
                    }
                }

                // 단어의 끝이므로 공백 추가 및 인덱스 재조정
                ans.append(" ");
                idx = lastIdx + 1;
            }
            // 첫문자가 대문자일 때
            else {
                // 소문자가 나올때까지 단어로 취급하고 ans 에 추가
                while (idx + 1 < length && Character.isUpperCase(sentence.charAt(idx + 1))) {
                    ans.append(sentence.charAt(idx) + " ");
                    idx++;

                    // sentence 의 마지막문자 처리
                    if (idx == length - 1) {
                        ans.append(sentence.charAt(idx));
                        return ans.toString();
                    }
                }

                // idx번째 문자는 대문자, idx+1번째 문자는 소문자인 상태
                char c = sentence.charAt(idx + 1);
                lastIdx = sentence.lastIndexOf(c);
                String s = sentence.substring(idx, length);
                int cnt = countChar(s, s.charAt(1));

                // idx+1번째 소문자가 idx~끝 문자열에서 총 2개일경우
                if (cnt == 2) {
                    // 소문자가 붙어있으면 invalid
                    if (lastIdx - idx == 2) {
                        return "invalid";
                    }
                    // idx번째 대문자를 ans에 붙이고 규칙 2를 적용하기 위해 idx 재조정
                    ans.append(s.charAt(0) + " ");
                    idx++;
                }
                // cnt가 1이거나 3이상이면 규칙 1을 검사
                else {
                    // 사용된 소문자면 invalid
                    if (!set.remove(c)) {
                        return "invalid";
                    }

                    // 문자열의 끝이라면
                    if (lastIdx + 1 == length) {
                        return "invalid";
                    }

                    // 아니라면 대소 ~ 소대 부분문자열로 나눈 후 규칙1 검사
                    s = sentence.substring(idx, lastIdx + 2);
                    if (!checkRule1(s)) {
                        return "invalid";
                    }

                    // 규칙1을 만족한다면 대문자들을 ans에 붙임
                    for (int i = 0; i < s.length(); i++) {
                        if (Character.isUpperCase(s.charAt(i))) {
                            ans.append(s.charAt(i));
                        }
                    }
                    // 단어의 끝이므로 공백 추가 및 인덱스 재조정
                    ans.append(" ");
                    idx = lastIdx + 2;
                }
            }
        }
        return ans.toString().trim();
    }

    private boolean checkRule1(String s) {
        // 주어진 문자열이 빈문자열이면 false
        if (s.length() == 0) {
            return false;
        }
        // 1번째 문자가 소문자면 false
        if (Character.isLowerCase(s.charAt(0))) {
            return false;
        }

        // 길이가 2이하이면 모두 대문자여야함.
        if (s.length() <= 2) {
            for (int i = 0; i < s.length(); i++) {
                if (Character.isLowerCase(s.charAt(i))) {
                    return false;
                }
            }
        }
        // 길이가 3이상이라면 대소대소대소대.. 형식이거나, 대대대대 형식이어야함
        else {
            // 2번째 문자가 소문자면 대소대소대 형식이어야 함 (s의 길이가 홀수여야함)
            if (Character.isLowerCase(s.charAt(1))) {
                if (s.length() % 2 != 1) {
                    return false;
                }

                for (int i = 0; i < s.length(); i++) {
                    if (i % 2 == 0 && Character.isLowerCase(s.charAt(i))) {
                        return false;
                    } else if (i % 2 == 1 && s.charAt(1) != s.charAt(i)) {
                        return false;
                    }
                }
            }
            // 2번째 문자가 대문자면 대대대대 형식이어야 함
            else {
                for (int i = 2; i < s.length(); i++) {
                    if (Character.isLowerCase(s.charAt(i))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private int countChar(String s, char c) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == c) {
                count += 1;
            }
        }
        return count;
    }

    private void initSet() {
        set = new HashSet<>();
        for (int i = 0; i < 26; i++) {
            set.add((char) ('a' + i));
        }
    }

    @Test
    public void test() {
        Assertions.assertEquals("A BBBB CC D D D", solution("aAaBbBbBbBcCCcDdDdD"));
        Assertions.assertEquals("HELLO", solution("aHELLOa"));
        Assertions.assertEquals("A A A A AA", solution("AAAAxAbAx"));
        Assertions.assertEquals("HELLO", solution("aHbEbLbLbOa"));
        Assertions.assertEquals("HELLO WORLD", solution("HaEaLaLaObWORLDb"));
        Assertions.assertEquals("SIGONG J O A", solution("SpIpGpOpNpGJqOqA"));
        Assertions.assertEquals("A", solution("A"));
        Assertions.assertEquals("H E L L O W O R L D", solution("HELLOWORLD"));
        Assertions.assertEquals("HELLO WORLD", solution("aHbEbLbLbOacWdOdRdLdDc"));
        Assertions.assertEquals("HELL O WORLD", solution("HaEaLaLObWORLDb"));
        Assertions.assertEquals("A A A", solution("AAA"));
        Assertions.assertEquals("HELLOWORLD", solution("aHELLOWORLDa"));
        Assertions.assertEquals("A A A B A BBBB C BBBB C BB GG G G G RRRRRR"
                , solution("AAAaBaAbBBBBbCcBdBdBdBcCeBfBeGgGGjGjGRvRvRvRvRvR"));
        Assertions.assertEquals("I A M", solution("aIaAM"));
        Assertions.assertEquals("AO", solution("bAaOb"));
        Assertions.assertEquals("invalid", solution("a"));
        Assertions.assertEquals("invalid", solution("Aa"));
        Assertions.assertEquals("invalid", solution("aA"));
        Assertions.assertEquals("invalid", solution("HaEaLaLaOWaOaRaLaD"));
        Assertions.assertEquals("invalid", solution("abHELLObaWORLD"));
        Assertions.assertEquals("invalid", solution("aHELLOa bWORLDb"));
        Assertions.assertEquals("invalid", solution("TxTxTxbAb"));
        Assertions.assertEquals("invalid", solution("bTxTxTaTxTbkABaCDk"));
        Assertions.assertEquals("invalid", solution("baHELLOabWORLD"));
        Assertions.assertEquals("invalid", solution("A B"));
        Assertions.assertEquals("AAAA BBBB", solution("AxAxAxABcBcBcB"));
        Assertions.assertEquals("BB A", solution("oBBoA"));
        Assertions.assertEquals("AAAA", solution("AxAxAxA"));
        Assertions.assertEquals("HELLO WORLD SIGONG J O A GWFD GWL BB A A A AAAA A"
                , solution("HaEaLaLaObWORLDbSpIpGpOpNpGJqOqAdGcWcFcDdeGfWfLeoBBoAAAAxAxAxAA"));
        Assertions.assertEquals("BA DE A E EEEE", solution("aBcAadDeEdvAvlElmEEEEm"));
        Assertions.assertEquals("A A A B B B", solution("AcAcABaBaB"));
        Assertions.assertEquals("GWFD GWL", solution("aGbWbFbDakGnWnLk"));
        Assertions.assertEquals("X XX X", solution("XcXbXcX"));
        Assertions.assertEquals("invalid", solution("aCaCa"));
        Assertions.assertEquals("invalid", solution("AxAxAxAoBoBoB"));
//        Assertions.assertEquals("A", solution("aaA"));
//        Assertions.assertEquals("A", solution("Aaa"));
        Assertions.assertEquals("invalid", solution("xAaAbAaAx"));
        Assertions.assertEquals("invalid", solution("AsCsWsQsQsEEEEEEEEeEeEe"));
        Assertions.assertEquals("A B C D E F GH", solution("ABCaDaEFGbH"));
        Assertions.assertEquals("A B B B AAA", solution("aAaBBBcAeAeAc"));
        Assertions.assertEquals("A B C DEF H I", solution("ABCbDaEaFbHI"));
        Assertions.assertEquals("invalid", solution("AacacaA"));
        Assertions.assertEquals("invalid", solution("AaBcBcBcBcB"));
        Assertions.assertEquals("invalid", solution("aAAA"));
        Assertions.assertEquals("invalid", solution("AAAa"));
        Assertions.assertEquals("invalid", solution("aAbBBbAa"));
        Assertions.assertEquals("invalid", solution("aAbBBbAa"));
        Assertions.assertEquals("invalid", solution("aAAbBbAAa"));
        Assertions.assertEquals("invalid", solution("aAcAbAbAcAcAcAa"));
        Assertions.assertEquals("invalid", solution("acAcAcAa"));
        Assertions.assertEquals("invalid", solution("aAcAcAca"));
        Assertions.assertEquals("A AAA A", solution("AdAeAeAdA"));
        Assertions.assertEquals("invalid", solution("dAAeAd"));
        Assertions.assertEquals("invalid", solution("dAeAAd"));
        Assertions.assertEquals("ABA", solution("cAbBbAc"));
        Assertions.assertEquals("invalid", solution("AbbA"));
        Assertions.assertEquals("invalid", solution("aAaaBa"));
        Assertions.assertEquals("A B", solution("aAacBc"));
        Assertions.assertEquals("A B", solution("AB"));
        Assertions.assertEquals("A B", solution("AcBc"));
        Assertions.assertEquals("A B", solution("aAaB"));
        Assertions.assertEquals("AAAA BBBB", solution("aAbAbAbAacBdBdBdBc"));
        Assertions.assertEquals("AAAA BBBB", solution("AbAbAbABdBdBdB"));
        Assertions.assertEquals("AAAA BBBB", solution("AbAbAbAcBBBBc"));
        Assertions.assertEquals("AAAA BBBB", solution("aAbAbAbAaBdBdBdB"));
        Assertions.assertEquals("AAAA BBBB", solution("aAbAbAbAacBBBBc"));
        Assertions.assertEquals("AAAA BBBB", solution("aAAAAaBdBdBdB"));
        Assertions.assertEquals("AAAA BBBB", solution("aAAAAacBBBBc"));
        Assertions.assertEquals("AAAA BBBB", solution("aAAAAaBdBdBdB"));
        Assertions.assertEquals("AAAA BBBB", solution("aAAAAacBdBdBdBc"));
        Assertions.assertEquals("AAAA BBBB", solution("aAAAAaBdBdBdB"));
        Assertions.assertEquals("AAAA BBBB", solution("AbAbAbAcBdBdBdBc"));
        Assertions.assertEquals("AAAA BBBB", solution("aAAAAaBdBdBdB"));
        Assertions.assertEquals("IM M M", solution("IaMMbMb"));
        Assertions.assertEquals("invalid", solution("AaAaAabBBb"));
        Assertions.assertEquals("A A AA", solution("AaAaAcA"));
        Assertions.assertEquals("A B", solution("aAabBb"));
        Assertions.assertEquals("B HEE", solution("bBbcHdEdEc"));
        Assertions.assertEquals("AA A", solution("AaAA"));
        Assertions.assertEquals("J OOO A", solution("JaOOOaA"));
        Assertions.assertEquals("J O O O A", solution("aJaOOOcAc"));
        Assertions.assertEquals("I AM", solution("IaAMa"));
        Assertions.assertEquals("I A M", solution("aIaAM"));
        Assertions.assertEquals("SIGONG J OOO A", solution("SpIpGpOpNpGJqOOOqA"));
        Assertions.assertEquals("invalid", solution("AxAxAxAoBoBoB"));
        Assertions.assertEquals("HELLO WORLD", solution("HaEaLaLaOWbObRbLbD"));
        Assertions.assertEquals("AAAA B B B", solution("AxAxAxABoBoB"));
        Assertions.assertEquals("B", solution("aBa"));
        Assertions.assertEquals("invalid", solution("baHELLOabWORLD"));
        Assertions.assertEquals("invalid", solution("aAbAba"));
        Assertions.assertEquals("AO", solution("bAaOb"));
        Assertions.assertEquals("A A A B B B BB", solution("AAAaBaBBBbB"));
    }
}

```

