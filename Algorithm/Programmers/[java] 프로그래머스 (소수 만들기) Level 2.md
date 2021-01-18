## Problem : https://programmers.co.kr/learn/courses/30/lessons/12977

## Approach

처음에 조합으로 n개 숫자에서 3개를 고른 후 더한 숫자가 소수인지를 판별하는 식으로 접근했다.

그런데 시간초과가 났다.



그래서 재귀를 돌리지 않고 그냥 단순히 앞에서부터 3개를 골라 더한 숫자가 소수인지를 판별하는 식으로 구현했더니 시간초과없이 테스트가 통과되었다.

소수판별은 에라토스테네스의 체를 이용하여 판별했고, 주어진 숫자가 1~1000까지의 중복없는 숫자기 때문에 최댓값은 정확히 따지면 1000+999+998 = 2997 이지만 그냥 3000까지 판단하였다.

## Code

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MakingPrimeNumber {
//    static int count = 0;
    static boolean[] prime = new boolean[3000]; // false 인 것이 소수
    public static void main(String[] args) {
        MakingPrimeNumber mpn = new MakingPrimeNumber();

        int[] nums = {1,2,7,6,4};

//        System.out.println(mpn.mysolution(nums));
        System.out.println(mpn.solution(nums));
    }

    public int solution(int[] nums) {
        int count = 0;

        prime[0] = true;
        prime[1] = true;
        for(int i = 0; i < prime.length; i++) {
            if (prime[i]) {
                continue;
            }
            for (int j = i + i; j < prime.length; j += i) {
                prime[j] = true;
            }
        }

        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    int sum = nums[i] + nums[j] + nums[k];
                    if (!prime[sum]) {
                        count += 1;
                    }
                }
            }
        }
        return count;
    }
}


// 시간 초과 (접근법은 맞는거같음...)
//    public int mysolution(int[] nums) {
//        List<Integer> t = Arrays.asList(Arrays.stream(nums).boxed().toArray(Integer[]::new));
//        ArrayList<Integer> arr = new ArrayList<>(t);
//        ArrayList<Integer> res = new ArrayList<>();
//
//        prime[0] = true;
//        prime[1] = true;
//        for(int i = 0; i < prime.length; i++) {
//            if (prime[i]) {
//                continue;
//            }
//            for (int j = i + i; j < prime.length; j += i) {
//                prime[j] = true;
//            }
//        }
//
//        makeNums(arr, res, 0, nums.length, 3);
//
//        return count;
//    }
//
//    static void makeNums(ArrayList<Integer> arr, ArrayList<Integer> res, int index, int n, int r) {
//        if (r == 0) {
//            int sum = 0;
//            for (int a : res) {
//                sum += a;
//                System.out.print(a +" ");
//            }
//            System.out.println("sum = " + sum);
//
//            if (!prime[sum]) {
//                count += 1;
//            }
//        }
//
//        for (int i = index; i < n; i++) {
//            res.add(arr.get(i));
//            makeNums(arr, res, i + 1, n, r - 1);
//            res.remove(res.size() - 1);
//        }
//    }
```

