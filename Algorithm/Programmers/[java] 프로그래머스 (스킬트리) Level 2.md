## 문제 원문 링크 : https://programmers.co.kr/learn/courses/30/lessons/49993

## Approach

```java
String skill = "CBD";
String[] skill_trees = {"BACDE", "CBADF", "AECB", "BDA"};
```

skill_trees 에 있는 각각의 문자들이 skill.contains() 메소드를 활용하여 skill 에 들어있는지를 검사한다.

만약 들어있지 않다면 빈 문자열("")로 replace 한다.

위의 과정을 거치면 "BCD", "CBD", "CB", "BD" 가 된다.

그리고 위의 문자열들을 skill 의 0번째에서 찾을 수 있다면 문제에서 `순서대로만 스킬을 배울 수 있다`를 만족할 수 있다.

## Code

```java
public class Skill_Tree {
    public static void main(String[] args) {
        String skill = "CBD";
        String[] skill_trees = {"BACDE", "CBADF", "AECB", "BDA"};

        System.out.println(String.valueOf(solution(skill, skill_trees)));
    }

    static int solution(String skill, String[] skill_trees){
        int answer = 0;

        for (String s : skill_trees) {
            String skilltree = s;
            for (int i = 0; i < s.length(); i++) {
                String t = String.valueOf(s.charAt(i));
                if (!skill.contains(t)) {
                    skilltree = skilltree.replace(t, "");
                }
            }

            if (skill.indexOf(skilltree) == 0) {
                answer += 1;
            }

        }
        return answer;
    }
}

```

