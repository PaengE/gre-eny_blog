## Problem : https://programmers.co.kr/learn/courses/30/lessons/42885

## Approach

N kg의 하중을 견디는 보트를 최소로 사용하여 모든 사람을 구출하는 문제이다.

일단 사람들의 몸무게를 오름차순으로 정렬한다. 그리고 밑의 단계를 반복한다.

1. 구출하지 못한 사람들 중, 몸무게가 가장 큰 사람과 작은 사람의 합이 보트의 하중 기준치를 넘긴다면, 가장 큰 사람만 태운다.
2. 구출하지 못한 사람들 중, 몸무게가 가장 큰 사람과 작은 사람의 합이 보트의 하중 기준치를 넘기지 않는다면, 그 다음 몸무게 작은 사람 순으로 기준치를 넘기지 않을 만큼 최대한 태운다.

## Code

```java
import java.util.Arrays;

public class Lifeboat {
    public static void main(String[] args) {
//        int[] people = {70, 50, 80, 50};
        int[] people = {10, 20, 30, 40, 50};
//        int[] people = {70, 80, 50};

        int limit = 100;

        Lifeboat b = new Lifeboat();
        System.out.println(b.solution(people, limit));

    }

    public int solution(int[] people, int limit) {
        Arrays.sort(people);

        int count = 0;
        int left = 0;
        int right = people.length - 1;

        int weight = 0;
        while (right - left >= 0) {
            weight = people[left] + people[right--];

            if (weight <= limit) {
                while (weight <= limit) {
                    weight += people[++left];
                }
            }

            count += 1;
        }
        return count;
    }
}
```

