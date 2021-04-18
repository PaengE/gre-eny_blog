## Problem : https://www.acmicpc.net/problem/1043

## Approach

> `union-find`라는 알고리즘을 사용하는 문제였다. 같은 집합으로 묶는 개념이며 알고리즘에서 자주 사용하는 방법이므로 숙지해두면 좋다.

다른 `union-find`에서와 마찬가지로 parent[] 배열을 사용한다. 로직은 다음과 같다.

- 진실을 아는 사람들은 parent[x] = x로  초기화를 하고, 진실을 모르는 사람들은 parent[x] = -1로 초기화를 한다.
- 각 파티마다 참석한 사람들을 하나의 집합으로 묶는다. 
- 다시 각 파티를 돌면서 거짓을 말할 수 있는지를 판단한다.

같은 집합으로 묶을 때 그 파티에 진실을 아는 사람이 있다면, 진실을 모르는 사람의 부모가 진실을 아는 사람이 되게끔 해주는 점만 주의하면 될 것이다.

## Code

```java
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 *  No.1043: 거짓말
 *  URL: https://www.acmicpc.net/problem/1043
 *  Hint: Union-find를 사용한 문제
 */

public class BOJ1043 {
    static ArrayList<ArrayList<Integer>> list = new ArrayList<>();
    static int[] p;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        p = new int[n + 1]; // 진실을 알고 있는 사람은 p[tmp] = tmp 인 상태임
        Arrays.fill(p, -1);

        st = new StringTokenizer(br.readLine());
        int t = Integer.parseInt(st.nextToken());
        for (int i = 0; i < t; i++) {
            int temp = Integer.parseInt(st.nextToken());
            p[temp] = temp;
        }

        // 파티 참석 인원들의 접점을 집합으로 묶음
        for (int i = 0; i < m; i++) {
            list.add(new ArrayList<>());
            st = new StringTokenizer(br.readLine());
            int k = Integer.parseInt(st.nextToken());
            for (int j = 0; j < k; j++) {
                list.get(i).add(Integer.parseInt(st.nextToken()));
            }

            if (list.get(i).size() >= 2) {
                unionize(i);
            }
        }

        // 파티에서 거짓말을 할 수있는지 체크
        int answer = 0;
        for (int i = 0; i < m; i++) {
            if (falseIsPossible(i)) {
                answer++;
            }
        }

        bw.write(String.valueOf(answer));
        bw.close();
        br.close();
    }

    // 현재 파티에서 거짓말을 할 수있는지 체크
    static boolean falseIsPossible(int i) {
        for (int j = 0; j < list.get(i).size(); j++) {
            int tmp = find(list.get(i).get(j));
            if (p[tmp] == tmp) {    // 파티원 중 진실을 알고 있는사람이 있으면 거짓말을 칠 수 없음
                return false;
            }
        }
        return true;
    }

    // 파티인원을 조사하여 union
    static void unionize(int i) {
        int first = list.get(i).get(0);

        for (int j = 1; j < list.get(i).size(); j++) {
            union(first, list.get(i).get(j));
        }
    }

    // 같은 집합으로 묶는 메소드
    static void union(int a, int b) {
        int aRoot = find(a);
        int bRoot = find(b);

        if (aRoot != bRoot) {
            if (aRoot == p[aRoot]) {
                p[bRoot] = aRoot;
            } else {
                p[aRoot] = bRoot;
            }
        }
    }

    // 숫자 a의 부모가 누군지를 찾는 메소드
    static int find(int a) {
        if (p[a] < 0 || p[a] == a) {
            return a;
        } else {
            return p[a] = find(p[a]);
        }
    }
}
```

