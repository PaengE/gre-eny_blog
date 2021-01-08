## Problem : https://programmers.co.kr/learn/courses/30/lessons/43165

## Approach

BFS나 DFS로 탐색하여 target number에 몇번 도착하는지를 계산하면 된다.

보통 DFS/BFS 는 목적지까지의 최소비용을 구하는데에 사용하지만, 이 문제에서는 최소비용이 아니라 몇번 도착하는지를 구하면 된다.

## Code

```java
public class TargetNumber {
    static int count = 0;
    public static void main(String[] args) {
        int[] numbers = {1, 1, 1, 1, 1};
        int target = 3;

        TargetNumber t = new TargetNumber();
        System.out.println(t.solution(numbers, target));
    }

    public int solution(int[] numbers, int target) {
        dfs(0, 0, target, numbers);

        return count;
    }

    static void dfs(int x, int index, int target, int[] numbers) {
        if (index == numbers.length) {
            if (x == target) {
                count += 1;
            }
            return;
        }

        dfs(x + numbers[index], index + 1, target, numbers);
        dfs(x - numbers[index], index + 1, target, numbers);
    }
}
```

