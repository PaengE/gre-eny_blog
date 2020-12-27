## 문제 원문 링크 : https://programmers.co.kr/learn/courses/30/lessons/62048

## Approach

임의의 꼭짓점 (x1, y1) -> (x2, y2) 으로 가는 최소크기로 나누어서 계산한 후, 최소크기의 개수만큼 곱해주는 식으로 풀면 쉽다.

최소크기로 나누었을 때, 선분이 거치는 사각형의 개수 =  (최소크기 사각형의 가로 + 세로 - 1) * 최소크기 사각형의 개수인 규칙을 찾을 수 있다. 여기서는 최소크기 사각형의 개수는 가로, 세로의 최대 공약수이다.

> 1. (w, h)의 최대공약수(gcd)를 구한다.
> 2. (w * h) - (((w / gcd) + (h / gcd) - 1) * gcd) 가 답이다.
>    - 주의할 점은 w와 h의 범위가 각각 1억까지 가능하므로, long타입을 사용해야 한다는 것이다.

나의 풀이는 mysolution()이고, solution()은 더 간단해 보이는 풀이를 참고한 것이다.

나는 최대공약수를 구하는 gcd() 메소드를 직접 구현하여 계산하였지만,

자바의 BigInteger 클래스에서 지원해주는 gcd를 사용하면 직접 구현하지 않고도 최대공약수를 사용할 수 있다.

## Code

```java
import java.math.BigInteger;

public class IntactSquare {
    public static void main(String[] args) {
        int w = 8;
        int h = 12;

        System.out.println(solution(w, h));
        System.out.println(mysolution(w, h));
    }

    // BigInteger 를 이용한 gcd method 사용
    static long solution(int w, int h) {
        int gcd = BigInteger.valueOf(w).gcd(BigInteger.valueOf(h)).intValue();
        return ((long) w * (long) h) - ((((long) w / gcd) + ((long) h / gcd) - 1) * gcd);
    }

    // gcd를 직접 구현하여 사용
    static long mysolution(int w, int h) {
        long gcd = gcd(w, h);
        return ((long) w * (long) h) - ((((long) w / gcd) + ((long) h / gcd) - 1) * gcd);
    }

    static long gcd(int a, int b) {
        if (b == 0) {
            return a;
        }

        return gcd(b, a % b);
    }
}

```

