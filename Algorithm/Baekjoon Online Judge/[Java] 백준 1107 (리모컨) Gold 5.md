## Problem : https://www.acmicpc.net/problem/1107

## Approach

> BruteForce 문제이다.
>
> 우선 0부터 목표 채널 * 2 까지 만들 수 있는 숫자인지를 판단하고, 만들 수 있는 숫자라면,
> `숫자를 만들 때의 클릭 수`와 `만든 숫자와 목표 채널과의 차이(+-클릭 수)`를 더한 것들 중 최솟값을 찾으면 된다.
>
> 주의할 점은, 시작 채널이 100이기 때문에 최소 100까지는 숫자를 만들 수 있는지 판단해야 한다.

isPossibleNumber() 메소드가 주어진 파라미터 숫자를 만들 수 있는지에 대한 판단을 한다.

- 만들 수 있다면, 만드는 데까지의 리모컨 클릭 수를 반환한다.
- 만들 수 없다면, 0을 반환한다.

--- 필자는 여기서 Runtime Error가 떴는데, 그 이유는 m이 0일 경우 읽어들일 다음 라인이 없는데 readLine()이 호출되면서 NullPointerException이 떴다. 따라서 m이 0일 경우를 예외처리 해주어야 한다.

## Code

```java
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *  No.1107: 리모컨
 *  URL: https://www.acmicpc.net/problem/1107
 *  Hint: BruteForce
 */

public class BOJ1107 {
    static ArrayList<Integer> list;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int channel = Integer.parseInt(br.readLine());
        int m = Integer.parseInt(br.readLine());
        list = new ArrayList<>();

        if (m != 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            while (m-- > 0) {
                list.add(Integer.parseInt(st.nextToken()));
            }
        }

        int min = Math.abs(channel - 100);  // 초기값: +- 클릭으로만 목표채널을 만들 때의 클릭 수

        for (int i = 0; i <= channel * 2 + 100; i++) {  // 최소 100까지는 돌아야함
            int click = isPossibleNumber(i);
            if (click > 0) {
                min = Math.min(min, click + Math.abs(channel - i));
            }
        }

        bw.write(String.valueOf(min));
        bw.close();
        br.close();
    }


    // 리모컨의 숫자만으로 num 채널을 만들 수 있는지 판단
    static int isPossibleNumber(int num) {
        if (num == 0) {
            return list.contains(0) ? 0 : 1;
        }

        int click = 0;
        while (num > 0) {
            if (list.contains(num % 10)) {
                return 0;
            }

            click++;
            num /= 10;
        }
        return click;
    }
}

```

