## Problem : https://programmers.co.kr/learn/courses/30/lessons/42890

## Approach

2019 KAKAO BLIND RECRUITMENT 문제였다.

> 데이터베이스에서 후보키는 유일성과 최소성을 만족시키는 키를 말한다.
>
> - 유일성(uniqueness) : 릴레이션에 있는 모든 튜플에 대해 유일하게 식별되어야 한다.
> - 최소성(minimality) : 유일성을 가진 키를 구성하는 속성(Attribute) 중 하나라도 제외하는 경우 유일성이 깨지는 것을 의미한다. 즉, 릴레이션의 모든 튜플을 유일하게 식별하는 데 꼭 필요한 속성들로만 구성되어야 한다.
>
> 여기서 데이터베이스에 대해 지식이 없다면 최소성의 개념에 대해 좀 헷갈릴 수 있을텐데, 쉽게 말하면 유일성을 충족시킨 키가 포함된 키는 최소성을 충족시키지 못한다는 소리이다.
>
> 예를 들어, 문제에서 `학번`은 유일성을 만족시키는 키이다. 따라서 `학번`이 들어간 (`학번`, `이름`), (`학번`, `전공`)과 같은 키들은 최소성을 만족시킬 수 없다.

따라서 컬럼의 조합으로 만들 수 있는 모든 조합의 키가 각각 유일성과 최소성을 둘다 만족시키는지를 확인하면 되는 문제이다.

- 구성된 컬럼 조합으로 각 튜플을 돌면서 문자열을 구성한다. 구성한 문자열을 set에 집어넣어 중복을 제거한다. set의 개수가 relation의 행 개수와 같다면, `유일성`을 만족시킨다.
- `후보키`를 사용하여 최소성을 검사한다. `후보키`로 등록된 키들은 모두 유일성을 만족시킨 상태이므로, 새로운 조합으로 만들어진 키가 `후보키`키를 포함하는지를 판별하여, `최소성`을 검사한다.

## Code

```java
import java.util.ArrayList;
import java.util.HashSet;

public class CandidateKey {
    static HashSet<ArrayList<Integer>> candidates;
    static int answer;
    public static void main(String[] args) {

//        String[][] relation = {{"100", "ryan", "music", "2"}
//                , {"200", "apeach", "math", "2"}
//                , {"300", "tube", "computer", "3"}
//                , {"400", "con", "computer", "4"}
//                , {"500", "muzi", "music", "3"}
//                , {"600", "apeach", "music", "2"}};
//        String[][] relation = {{"a", "b", "c"}, {"1", "b", "c"}, {"a", "b", "4"}, {"a", "5", "c"}};
        String[][] relation = {{"a", "1", "4"}, {"2", "1", "5"}, {"a", "2", "4"}};

        CandidateKey ck = new CandidateKey();
        System.out.println("answer : " + ck.solution(relation));

        candidates.forEach(list -> System.out.println(list));
    }

    public int solution(String[][] relation) {
        candidates = new HashSet<>();
        answer = 0;
        ArrayList<Integer> arr = new ArrayList<>();
        ArrayList<Integer> res = new ArrayList<>();
        int n = relation[0].length;

        for (int i = 0; i < n; i++) {
            arr.add(i);
        }

        for (int i = 1; i <= n; i++) {
            makeKey(arr, res, 0, n, i, relation);
        }

        return answer;
    }

    static void makeKey(ArrayList<Integer> arr, ArrayList<Integer> res, int idx, int n, int r, String[][] relation) {
        if (r == 0) {
            // 최소성을 검사
            for (ArrayList tmpList : candidates) {
                boolean check = true;
                // 주어진 컬럼 조합이 후보키를 포함하고 있으면 최소성을 충족시키지 못함
                for (int i = 0; i < tmpList.size(); i++) {
                    if (res.indexOf(tmpList.get(i)) == -1) {
                        check = false;
                        break;
                    }
                }
                if (check) {
                    return;
                }
            }

            // 유일성을 검사(구성된 컬럼으로)
            HashSet<String> tmpSet = new HashSet<>();
            for (int i = 0; i < relation.length; i++) {
                String t = "";
                for (int x : res) {
                    t += relation[i][x] + " ";
                }
                tmpSet.add(t);
            }
            // 구성된 컬럼으로 구성된 set 이 relation 의 행 개수와 같으면 유일성이 충족됨
            if (tmpSet.size() == relation.length) {
                candidates.add(new ArrayList<>(res));
                answer += 1;
            }
        }

        // 컬럼의 조합을 생성
        for (int i = idx; i < n; i++) {
            res.add(arr.get(i));
            makeKey(arr, res, i + 1, n, r - 1, relation);
            res.remove(res.size() - 1);
        }
    }
}

```

