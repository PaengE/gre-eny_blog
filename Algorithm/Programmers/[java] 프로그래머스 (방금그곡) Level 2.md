## Problem : https://programmers.co.kr/learn/courses/30/lessons/17683

## Approach

2018 KAKAO BLIND RECRUITMENT 문제였다.

1. 일단 C#, D#, F#, G#, A# 을 각각 a, d, f, g, a로 치환했다.
2. 치환한 후 곡의 플레이시간을 계산하여 곡의 음을 플레이시간만큼 만든다.
3. 만들어진 음의 sequence에서 주어진 m이 있는지를 찾는다.
4. 3번의 결과로 찾아진 것이 여러 곡이라면 플레이시간이 가장 긴 것으로 갱신한다.

문자열 처리가 중요했던 문제이다.

## Code

```java
public class TheSongJustNow {
    public static void main(String[] args) {
        TheSongJustNow ts = new TheSongJustNow();
        String m = "ABCDEFG";
        String[] musicinfos = {"12:00,12:14,HELLO,CDEFGAB", "13:00,13:05,WORLD,ABCDEF"};

        System.out.println(ts.solution(m, musicinfos));

    }

    public String solution(String m, String[] musicinfos) {
        String answer = "(None)";
        m = removeSharp(m);
        int maxTime = 0;

        for (String s : musicinfos) {
            s = removeSharp(s);
            String[] str = s.split(",");
            String[] startTime = str[0].split(":");
            String[] endTime = str[1].split(":");

            int playHour = Integer.parseInt(endTime[0]) - Integer.parseInt(startTime[0]);
            int playMinute = Integer.parseInt(endTime[1]) - Integer.parseInt(startTime[1]) + (playHour * 60);

            String sound = makeSound(str[3], playMinute);

            if (sound.contains(m)) {
                if (playMinute > maxTime) {
                    maxTime = playMinute;
                    answer = str[2];
                }
            }
        }
        return answer;
    }

    static String makeSound(String s, int time) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < time; i++) {
            sb.append(s.charAt(i % s.length()));
        }
        return sb.toString();
    }

    static String removeSharp(String s) {
        s = s.replaceAll("A#", "a");
        s = s.replaceAll("C#", "c");
        s = s.replaceAll("D#", "d");
        s = s.replaceAll("F#", "f");
        s = s.replaceAll("G#", "g");
        return s;
    }
}

```

