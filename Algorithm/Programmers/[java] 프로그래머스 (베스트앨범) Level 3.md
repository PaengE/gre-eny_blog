## Problem : https://programmers.co.kr/learn/courses/30/lessons/42579

## Approach

> 자바에선 HashMap을 이용한 완전탐색으로 풀 수 있는 문제였다.
>
> 좀 더 깔끔하게 풀어보고 싶었지만 그럴줄 몰라 아쉬웠다.

> 문제에서 요구한 출력조건은 다음과 같다.
>
> 1. 속한 노래가 많이 재생된 장르를 먼저 수록합니다.
> 2. 장르 내에서 많이 재생된 노래를 먼저 수록합니다.
> 3. 장르 내에서 재생 횟수가 같은 노래 중에서는 고유 번호가 낮은 노래를 먼저 수록합니다.

1번을 구하기 위해 장르별 총 플레이시간을 저장해놔야 한다. (playtimeHm)

2번을 구하기위해 별도의 songTime 이차원 배열을 두고, 플레이시간을 기준으로 내림차순 정렬을 수행했다.
배열에는 고유번호 i의 장르의 번호, 플레이횟수, 고유번호가 저장되어있다.

플레이시간 기준 내림차순 정렬함과 동시에, 플레이시간이 같다면, 고유번호 기준 오름차순으로 정렬을 마저 진행했다.

결과적으로 songTime 배열에서 `가장 플레이가 많이된 장르`부터 `플레이 횟수가 많고, 플레이 횟수가 같다면 고유번호가 낮은` 노래를 최대 2개씩 뽑아내면 된다.

이 과정은 완전탐색을 이용했다.(곡의 개수가 최대 10000개이고, 장르가 최대 100개여서 최악의 경우 1,000,000번 언저리 계산이 이루어져서 시간초과가 날 것 같아 시도하지 않았던 방법이지만, 다른 분의 풀이를 찾아보면서 깨달았다.)

## Code

```java
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class BestAlbum {
    public int[] solution(String[] genres, int[] plays) {
        HashMap<String, Integer> indexHm = new HashMap<>();     // 장르 - 넘버링 매핑
        HashMap<String, Integer> playtimeHm = new HashMap<>();  // 장르의 총 플레이타임 계산
        int size = genres.length;
        int[][] songTime = new int[size][3];    // 0: 장르, 1: 곡의 플레이타임, 2: 곡의 순서

        int idx = 0;
        for (int i = 0; i < size; i++) {
            String song = genres[i];
            Integer playtime = plays[i];

            // indexHm에 key로 해당 장르가 없으면 인덱스 추가
            indexHm.putIfAbsent(song, idx++);
            // playtimeHm에 같은 장르의 총 playtime을 갱신
            playtimeHm.computeIfPresent(song, (key, value) -> value + playtime);
            playtimeHm.putIfAbsent(song, playtime);

            // 해당 곡의 정보저장
            songTime[i][0] = indexHm.get(song);
            songTime[i][1] = playtime;
            songTime[i][2] = i;
        }

        // 플레이타임을 기준으로 내림차순 정렬(같으면 곡의 번호로 오름차순 정렬)
        Arrays.sort(songTime, (o1, o2) -> {
            if (o1[1] == o2[1]) {
                return o1[0] - o2[0];
            }
            return o2[1] - o1[1];
        });

        // 플레이타임 기준 장르를 내림차순 정렬
        ArrayList<String> keySetList = new ArrayList<>(playtimeHm.keySet());
        Collections.sort(keySetList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return playtimeHm.get(o2) - playtimeHm.get(o1);
            }
        });

        // 완전탐색
        ArrayList<Integer> ans = new ArrayList<>();
        for (String genre : keySetList) {
            int index = indexHm.get(genre);
            int count = 0;

            for (int i = 0; i < songTime.length; i++) {
                if (index == songTime[i][0]) {
                    ans.add(songTime[i][2]);
                    count++;
                    if (count == 2) {
                        break;
                    }
                }
            }
        }
        return ans.stream().mapToInt(i -> i).toArray();
    }

    @Test
    public void test() {
        Assertions.assertArrayEquals(new int[]{4, 1, 3, 0}
                , solution(new String[]{"classic", "pop", "classic", "classic", "pop"}, new int[]{500, 600, 150, 800, 2500}));
    }
}

```

