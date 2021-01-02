## Problem : https://programmers.co.kr/learn/courses/30/lessons/42839

## Approach

이 문제는 머릿속으로는 풀이가 생각나지만 구현이 쉽지 않았다.

크게 두개의 단계가 필요하다.

> 1: 주어진 숫자로 만들 수 있는 모든 숫자를 만들어야 한다.
>
> 2: 만들어진 숫자가 소수인지를 판별해야 한다.
>
> 
>
> 1번을 수행하려면 최대 nP1 + nP2 + ... + nPn-1 + nPn 번의 연산이 필요하다.
>
> 2번을 수행하려면 최대 n자리 수의 소수판별이 필요하다.
>
> 일반적으로는 시간이 오래걸리는 부분이지만, n이 최대 7이라 완전탐색으로 풀이가 가능하다.

1번은 순열을 이용하여 숫자를 구성하였다. 필자는 순열을 구현 해본적이 없어 다음 링크를 참고하였다.

순열, 조합, 멱집합에 대한 설명이 자세하게 되어있다.

[절차대로 생각하고 객체로 코딩하기 - 티스토리 블로그](https://codevang.tistory.com/297?category=859681)

2번은 1과 2를 예외처리 한 후, 숫자의 제곱근까지 3이상 홀수로만 나눠보고 소수를 판별하였다.

제곱근까지 나눠보기만 해도 된다는 것은 증명된 부분이므로, 따로 설명하지 않겠다. (증명 방법이 궁금한 분은 검색을 하시면 된다!)

## Code

```java
import java.util.ArrayList;
import java.util.TreeSet;

public class FindPrimeNumber {
    private TreeSet<String> set = new TreeSet<>();
    private int count = 0;

    public static void main(String[] args) {

        String numbers = "21";

        FindPrimeNumber s = new FindPrimeNumber();
        System.out.println(s.solution(numbers));
    }

    public int solution(String numbers) {
        int size = numbers.length();
        ArrayList<Character> arr = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            arr.add(numbers.charAt(i));
        }

        ArrayList<Character> res = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            makeNum(arr, res, size, i + 1);
        }

        return count;
    }

    /**
     * 소수판별
     *
     * @param number    판별될 숫자
     * @return          소수인지(ture) 아닌지(false)
     */
    private boolean isPrime(int number) {
        // 1이거나 2로 나누어 떨어지면 소수가 아님
        if (number % 2 == 0 || number == 1) {
            return false;
        }

        // 소수 판별은 제곱근까지만 따져보면 됨
        int sqrt = (int) Math.sqrt(number);
        for (int i = 3; i <= sqrt; i += 2) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }


    /**
     * 순열을 이용하여 숫자 생성
     *
     * @param arr   원본 리스트
     * @param res   만들어질 숫자가 담길 리스트(결과 리스트)
     * @param n     nPr 에서 n
     * @param r     nPr 에서 r
     */
    private void makeNum(ArrayList<Character> arr, ArrayList<Character> res, int n, int r) {

        // r이 0이면 문자열이 완성됐다는 뜻(탈출)
        if (r == 0) {
            if (res.get(0) != '0') {
                // res 는 [1, 2] 형식이므로 str 전처리
                String str = res.toString()
                        .replaceAll("\\[|\\]", "")
                        .replaceAll(", ", "");

                // set 에 없다면(중복된 수가 아니라면)
                if (!set.contains(str)) {
                    int num = Integer.parseInt(str);
                    set.add(str);

                    // 소수 판별
                    if (isPrime(num)) {
                        System.out.println("num = " + num);
                        count += 1;
                    }
                }
            }
            
            return;
        }

        // 순열이므로 기준을 계속 옮겨야 함(뭐를 먼저 고를지)
        for (int i = 0; i < n; i++) {
            // 원본리스트에서 선택한 요소를 결과리스트에 담음
            res.add(arr.remove(i));
            // 결과리스트에 담은 수(1개)를 빼고, 그중에서 (r-1)개를 골라야 하므로 재귀
            makeNum(arr, res, n - 1, r - 1);
            // 재귀가 끝나면 결과리스트에 마지막으로 담았던 걸 다시 원본리스트의 제자리에 넣음
            arr.add(i, res.remove(res.size() - 1));
        }
    }
}

```

