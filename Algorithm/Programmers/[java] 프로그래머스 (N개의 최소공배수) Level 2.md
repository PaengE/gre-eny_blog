## Problem : https://programmers.co.kr/learn/courses/30/lessons/12953

## Approach

A * B = (A와 B의 최대공약수) * (A와 B의 최소공배수) 를 이용하여 문제를 해결할 수 있다.

주어진 배열의 앞 두 요소로 최소공배수를 구한 후, 그 최소공배수와 배열의 다음 요소와의 최소공배수를 계속해서 구해나간다.

그렇게 마지막으로 구해진 숫자가 그 배열의 최소공배수이다.

## Code

```java
public class N_LCM {
    public static void main(String[] args) {
        N_LCM lcm = new N_LCM();
        int[] arr = {2, 6, 8, 14};

        System.out.println(lcm.solution(arr));
    }

    public int solution(int[] arr) {
        int size = arr.length;
        if (size == 1) {
            return arr[0];
        } else {
            int lcm = (arr[0] * arr[1]) / gcd(arr[0], arr[1]);
            for (int i = 2; i < arr.length; i++) {
                lcm = (lcm * arr[i]) / gcd(lcm, arr[i]);
            }
            return lcm;
        }
    }

    public int gcd(int a, int b) {
        if (b == 0) {
            return a;
        } else {
            return gcd(b, a % b);
        }
    }
}

```

