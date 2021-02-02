## Problem : https://www.acmicpc.net/problem/20040

## Approach

> Union-Find 알고리즘을 활용하여 풀 수 있는 문제이다.
>
> 주어진 그래프가 사이클을 이루지 않는다면, m개의 간선을 처리할 동안 두 노드의 부모가 항상 달라야한다.
>
> 만약 간선을 처리하는 중, 두 노드의 부모가 같은 것이 나온다면, 두 노드는 이미 하나의 집합으로 묶여있는 상태이고, 같은 집합에 간선을 추가한다는 것은 곧 사이클을 만든다는 뜻이 된다.
>
> 따라서, 주어진 m개의 간선을 처리하면서, 두 노드는 항상 부모가 달라야하며(사이클이 없다면) 다를 경우 union을 해준다. 두 노드의 부모가 같다면, 사이클이 발생되는 것이므로 프로그램을 종료한다.

#### 예제 입력 2

```html
6 5
0 1
1 2
1 3
0 3
4 5
```

`예제 입력2`를 가지고 설명을 하겠다. 노드 수 = 6, 간선 수 = 5

- (0, 1), (1, 2), (1, 3) 을 처리했다면, (0, 1, 2, 3) 의 부모는 모두 0이 된다. 왜냐하면 (1, 3) 까지 처리하는 동안 한 줄에 주어진 두 노드는 항상 부모가 다르고, 모두 union을 해주기 때문이다.
- (0, 3)을 처리할 때에는 0과 3의 부모가 0으로 같다. 이럴 경우에 0과 3은 이미 하나의 집합에 속해져 있는 것이다. 같은 집합 내에 다른 간선을 추가한다는 것은 사이클을 형성한다는 뜻이다.

## Code

```java
import java.io.*;
import java.util.StringTokenizer;

/**
 *  No.20040: 사이클 게임
 *  Hint: Union-Find 알고리즘 활용
 */

public class BOJ20040 {
    static int[] parent;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }

        int ans = 0;
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            if (findSet(a) != findSet(b)) {
                union(a, b);
            } else {
                ans = i + 1;
                break;
            }
        }

        bw.write(String.valueOf(ans));
        bw.close();
        br.close();
    }

    static int findSet(int x) {
        if (parent[x] == x) {
            return x;
        } else {
            return parent[x] = findSet(parent[x]);
        }
    }

    static void union(int x, int y) {
        if (x < y) {
            parent[findSet(y)] = parent[findSet(x)];
        } else {
            parent[findSet(x)] = parent[findSet(y)];
        }
    }
}

```

