## 문제 원문 링크 : https://programmers.co.kr/learn/courses/30/lessons/42586

## Approach

> < 문제의 조건 >
>
> 각 기능은 진도가 100%일 때 서비스에 반영할 수 있습니다.
>
> 또, 각 기능의 개발속도는 모두 다르기 때문에 뒤에 있는 기능이 앞에 있는 기능보다 먼저 개발될 수 있고, 이때 뒤에 있는 기능은 앞에 있는 기능이 배포될 때 함께 배포됩니다.

| progresses               | speeds             | return    |
| ------------------------ | ------------------ | --------- |
| [93, 30, 55]             | [1, 30, 5]         | [2, 1]    |
| [95, 90, 99, 99, 80, 99] | [1, 1, 1, 1, 1, 1] | [1, 3, 2] |

문제 자체는 쉽다. 

1. progresses의 각 요소에 speeds의 각 요소를 동시에 더해주기를 반복하며, 더해주기 전에 progresses의 요소들을 앞에서부터 검색하여 100보다 같거나 크면 index와 count를 1증가시키고 다음 요소를 검사한다.

2. 더이상 100보다 같거나 큰 요소가 나오지 않을때까지 반복한다. 그리고 count를 0으로 초기화한다.

3. 다 배포될 때까지 위의 과정들을 반복한다.

> Programmers 사이트 특성상 solution 함수의 반환형이 정해져있다. 여기서는 int[]형인데 풀이에선 처음부터 int[]로 하기엔 불편하다고 생각하여, ArrayList로 구현하여 return하기 전에 List to Array로 형변환을 해주었다.

## Code

```java
import java.util.ArrayList;

public class FunctionalDevelopment {
    public static void main(String[] args) {
        int[] progresses = {93, 30, 55};
        int[] speeds = {1, 30, 5};

        int[] a = solution(progresses, speeds);

        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] +" ");
        }
    }

    static int[] solution(int[] progresses, int[] speeds) {
        ArrayList<Integer> list = new ArrayList<>();

        int idx = 0;
        while (idx < speeds.length) {
            int count = 0;

            for (int i = idx; i < speeds.length; i++) {
                progresses[i] += speeds[i];
            }

            while (progresses[idx] >= 100) {
                idx += 1;
                count += 1;
                if (idx == speeds.length) {
                    break;
                }
            }

            if (count != 0) {
                list.add(count);
            }
        }

      // List to Array
        int[] answer = list.stream().mapToInt(i -> i).toArray();

        return answer;
    }
}

```

