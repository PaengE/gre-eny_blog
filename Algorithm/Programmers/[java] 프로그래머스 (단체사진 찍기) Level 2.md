## Problem : https://programmers.co.kr/learn/courses/30/lessons/1835

## Approach

2017 카카오코드 본선 문제이다. 생각보다 어려웠다... Level 2의 다른문제와는 난이도가 다른것 같다...

{A, C, F, J, M, N, R, T} 로 줄을 세우는 방법 중 주어진 조건을 만족하는 방법의 개수를 구하는 문제이다.

줄을 세운다는 것 자체가 순열이므로 순열을 구하여, 특정 문자의 위치의 거리를 계산하여 조건을 충족시키는지를 확인한다.

순열을 구현하는 코드에서, 모든 경우의 위치를 검사해야 하므로  for loop를 돌렸고, 골랐던 문자는 다음 문자를 고를 때 포함되면 안되므로 arr.remove(i) 한 것을 res에 추가해준다. 
그런 후, 줄을 다 세웠으면 원래 위치에 res에 마지막에 추가된 요소를 remove하고 원래 arr의 자리에 되돌려준다.(arr.add(i, res.remove(res.size() - 1)))

순열로 문자열이 만들어졌으면 주어진 조건을 만족하는지(두 문자의 위치차이) 계산하여 count를 늘려간다.
첫 조건부터 만족하지 못하면 그대로 return을 시켜 불필요한 연산을 막는다.

순열을 만드는 연산의 수 자체가 8! = 40,320이고, 문제에서 조건의 개수가 최대 100이므로 최악의 경우 100 x 40320 = 4032000 만큼의 연산이 필요하므로, 시간이 오래걸릴 수 밖에 없다고 생각한다(?)

## Code

```java
import java.util.ArrayList;
import java.util.Arrays;

public class TakingAGroupPhoto {
    static int count;
    public static void main(String[] args) {
        int n = 2;
        String[] data = {"N~F=0", "R~T>2"};
//        String[] data = {"M~C<2", "C~M>1"};
        TakingAGroupPhoto tagp = new TakingAGroupPhoto();
        tagp.solution(n, data);

        System.out.println("count = " + count);
    }

    public int solution(int n, String[] data) {
        Character[] s = {'A', 'C', 'F', 'J', 'M', 'N', 'R', 'T'};
        count = 0;

        ArrayList<Character> arr = new ArrayList<>(Arrays.asList(s));
        ArrayList<Character> res = new ArrayList<>();

        makeString(arr, res, 8, 8, data);

        return count;
    }

    static void makeString(ArrayList<Character> arr, ArrayList<Character> res, int n, int r, String[] data) {
        if (r == 0) {
            int size = data.length;

            for (int i = 0; i < size; i++) {
                char c1 = data[i].charAt(0);
                char c2 = data[i].charAt(2);
                char op = data[i].charAt(3);
                int num = data[i].charAt(4) - '0';

                int diff = Math.abs(res.indexOf(c1) - res.indexOf(c2)) - 1;
                if (op == '<') {
                    if (diff >= num) {
                        return;
                    }
                } else if (op == '>'){
                    if (diff <= num) {
                        return;
                    }
                } else if (op == '='){
                    if (diff != num) {
                        return;
                    }
                }
            }
            count += 1;
            return;
        }
        for (int i = 0; i < n; i++) {
            res.add(arr.remove(i));
            makeString(arr, res, n - 1, r - 1, data);
            arr.add(i, res.remove(res.size() - 1));
        }
    }
}

```

