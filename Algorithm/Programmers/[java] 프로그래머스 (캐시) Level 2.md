## Problem : https://programmers.co.kr/learn/courses/30/lessons/17680

## Approach

2018 KAKAO BLIND RECRUITMENT 문제였다.

이 문제에서 사용되는 LRU(Least Recently Used) 교체알고리즘은 가장 최근에 사용되지 않은 캐시 내의 요소를 교체한다는 전략이다.

여기서의 캐시는 여러가지 자료구조로 구현할 수 있지만, 나는 리스트로 구현을 하였다.

리스트의 뒤쪽으로 갈수록 최근에 사용된 도시이름이다.

> 1. 도시이름이 캐시(리스트)에 있으면 cache hit이 되었으므로 실행시간+1 를 한다. 그런 후, 그 요소를 빼내 리스트에 맨 뒤쪽에 붙인다.
> 2. 도시이름이 캐시에 없고(cache miss), 캐시가 꽉차있다면, 실행시간+5 를 한 후, 캐시에서 가장 오래된요소(즉, 캐쉬의 맨 앞쪽 도시이름)을 제거한 후, 새로운 도시이름을 캐시의 맨 뒤쪽에 붙인다.
> 3. 도시이름이 캐시에 없고, 캐시가 꽉차있지 않다면, 실행시간+5 를 한 후, 새로운 도시이름을 캐시의 맨 뒤쪽에 붙인다.

위의 조건만 순서대로 처리해주면 끝난다.

## Code

```java
import java.util.*;

public class Cache {
    public static void main(String[] args) {
        int cacheSize = 3;
        String[] cities = {"Jeju", "Pangyo", "Seoul", "NewYork", "LA", "Jeju", "Pangyo", "Seoul", "NewYork", "LA"};
//        String[] cities = {"Jeju", "Pangyo", "Seoul", "Jeju", "Pangyo", "Seoul", "Jeju", "Pangyo", "Seoul"};

        Cache c = new Cache();
        System.out.println(c.solution(cacheSize, cities));

    }

    public int solution(int cacheSize, String[] cities) {
        var list = new ArrayList<String>();

        if (cacheSize == 0) {
            return cities.length * 5;
        }

        int time = 0;
        for (String s : cities) {
            s = s.toLowerCase();
            if (list.contains(s)) {
                list.remove(list.indexOf(s));
                time += 1;
            } else if (list.size() == cacheSize) {
                list.remove(0);
                time += 5;
            } else if (!list.contains(s)) {
                time += 5;
            }
            list.add(s);
        }

        return time;
    }
}

```

