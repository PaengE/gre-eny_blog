## Problem : https://programmers.co.kr/learn/courses/30/lessons/17676

## Approach

2018 KAKAO BLIND RECRUITMENT 문제였다.

먼저 문자열을 다음과 같이 처리를 하였다.

> 문자열에서 시간을 먼저 추출하였다. 응답시간은 (h x 3600 + m x 60 + (s + ms)) x 1000로 계산하였고,
> 시작시간은 (응답시간 - 처리시간 + 1), 처리시간은 (ms x 1000)로 계산하였다.
>
> (최종 답안을 작성하기 전에 시간비교를 실수 상태로 했었는데 통과가 안되어서 시간비교를 자연수 상태로 하였더니 통과를 했다. 소수점 셋째자리까지 자른 후 비교해서 오차가 생기지 않을 줄 알았는데 TC에선 오차가 없었으나 다른 어떤 부분에서 오차가 생기나보다.) 
>
> 응답완료시간(종료시간)은 항상 2016-09-15 이지만 시작시간은 하루 전날인 2016-09-14일 경우도 있다.
>
> 처리시간은 최대 3.0s이므로 시작시간이 음수가 될 순 있으나 위에서처럼 timestamp로 계산한다면 문제가 생기진 않는다.

문자열에서 시간을 추출한 후, 특정 1초에서 동시에 실행되는 트래픽이 최대 몇개인지를 찾아야하는데 모든 범위를 검사한다는 것은 범위가 상당히커서(전날 23:59:57:000 ~ 다음날 23:59:59:999 까지 1초 범위이므로) 불가능하다.(시간 초과)

따라서 트래픽 개수의 변화가 이루어지는 몇개 부분만 검사해보면 된다. 다음과 같은 경우가 있다.

> 1. 시작시간 ~ 시작시간 + 1.0s : 시작시간 이후에는 트래픽의 개수가 1증가하므로
> 2. 응답시간 ~ 응답시간 + 1.0s : 응답시간 이후에는 트래픽의 개수가 1감소하므로

각각의 로그에 대해 (시작시간 ~ 시작시간 + 1.0s) 과 (응답시간 ~ 응답시간 + 1.0s)에 몇개의 트래픽이 있는지를 검사하고, 그 중 최댓값을 찾으면 된다.

## Code

```java
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ChuseokTraffic {
    public int solution(String[] lines) {
        int[] startTime = new int[lines.length];
        int[] endTime = new int[lines.length];

        for (int i = 0; i < lines.length; i++) {
            String[] s = lines[i].split(" ");
            String[] time = s[1].split(":");

            int processingTime = (int) (Double.parseDouble(s[2].substring(0, s[2].length() - 1)) * 1000);
            endTime[i] = (int) (((Double.parseDouble(time[0]) * 3600) + (Double.parseDouble(time[1]) * 60) + Double.parseDouble(time[2])) * 1000);
            startTime[i] = endTime[i] - processingTime + 1;
        }

        int length = lines.length;
        int max = 0;
        for (int i = 0; i < length; i++) {
            int startPlusOne = startTime[i] + 1000;
            int endPlusOne = endTime[i] + 1000;
            int count = 0;

            count = getCount(startTime, endTime, length, startPlusOne, 0, startTime[i], i);
            max = Math.max(count, max);

            count = getCount(startTime, endTime, length, endPlusOne, 0, endTime[i], i);
            max = Math.max(count, max);
        }

        return max;
    }

    private int getCount(int[] startTime, int[] endTime, int length, int startPlusOne, int count, int i2, int i) {
        for (int j = 0; j < length; j++) {
            if ((startTime[j] <= i2 && startPlusOne <= endTime[j])
                    || (i2 <= startTime[j] && startTime[j] < startPlusOne)
                    || (i2 <= endTime[j] && endTime[j] < startPlusOne)) {
                count++;
            }
        }
        return count;
    }

    @Test
    public void test() {
        String[] lines1 = {"2016-09-15 20:59:57.421 0.351s",
                "2016-09-15 20:59:58.233 1.181s",
                "2016-09-15 20:59:58.299 0.8s",
                "2016-09-15 20:59:58.688 1.041s",
                "2016-09-15 20:59:59.591 1.412s",
                "2016-09-15 21:00:00.464 1.466s",
                "2016-09-15 21:00:00.741 1.581s",
                "2016-09-15 21:00:00.748 2.31s",
                "2016-09-15 21:00:00.966 0.381s",
                "2016-09-15 21:00:02.066 2.62s"};
        String[] lines2 = {"2016-09-15 01:00:04.002 2.0s",
                "2016-09-15 01:00:07.000 2s"};
        String[] lines3 = {"2016-09-15 01:00:04.001 2.0s",
                "2016-09-15 01:00:07.000 2s"};
        String[] lines4 = {"2016-09-15 00:00:00.001 3s"};

        Assertions.assertEquals(7, solution(lines1));
        Assertions.assertEquals(2, solution(lines2));
        Assertions.assertEquals(1, solution(lines3));
        Assertions.assertEquals(1, solution(lines4));
    }
}

```

