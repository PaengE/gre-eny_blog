## Problem : https://www.acmicpc.net/problem/2250

## Approach

> 트리에서의 순회 문제였다.
>
> 문제만 보고는 순회를 생각하기에 어려웠다. 짜놓고 보니 중위순회 였다는 느낌.
>
> 주의할 점은 루트 노드가 항상 1인 것은 아니라는 점이다. 따라서 루트 노드를 찾는 과정이 포함되어야 한다.

levelMin[i] 는 레벨 i에서의 가장 왼쪽 노드의 인덱스이다.

levelMax[i] 는 레벨 i에서의 가장 오른쪽 노드의 인덱스이다.

노드를 방문할 때마다 loc 값을 증가시킨 후 그 값을 이용하므로, 인덱스를 저장하는 방식은 문제의 그림처럼 저장되지는 않는다. 

주요 로직은 다음과 같다.

- 트리를 구성하면서 루트 노드를 찾는다.
- 중위 순회로 노드를 방문하면서, 방문하는 노드 순서대로 번호를 매긴다.
- 그리고 그 레벨에 있는 노드들 중 가장 작은 번호의 노드와 가장 큰 번호의 노드를 저장한다.
- 그 차이 중 가장 큰 값을 찾는다.

## Code

```java
import java.io.*;
import java.util.StringTokenizer;

/**
 *  No.2250: 트리의 높이와 너비
 *  URL: https://www.acmicpc.net/problem/2250
 *  Hint: Tree + inorder
 */

public class BOJ2250 {
    static int n, maxLevel, loc = 1;    // loc: 노드 방문 시마다 +1
    static Node[] nodes;
    static int[] levelMax, levelMin;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        n = Integer.parseInt(br.readLine());
        nodes = new Node[n + 1];
        levelMax = new int[n + 1];
        levelMin = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            nodes[i] = new Node(-1, i, -1, -1);
            levelMax[i] = 0;
            levelMin[i] = n;
        }

        // 트리 구성
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int cur = Integer.parseInt(st.nextToken());
            int left = Integer.parseInt(st.nextToken());
            int right = Integer.parseInt(st.nextToken());
            nodes[cur].left = left;
            nodes[cur].right = right;
            if (left != -1) {
                nodes[left].parent = cur;
            }
            if (right != -1) {
                nodes[right].parent = cur;
            }
        }

        // 루트 노드를 찾음
        int root = 0;
        for (int i = 1; i <= n; i++) {
            if (nodes[i].parent == -1) {
                root = i;
                break;
            }
        }

        // 중위 순회
        inorder(root, 1);

        int ans = 0;
        int ansIdx = 0;
        // 각 레벨 별로 너비를 구함
        for (int i = 1; i <= maxLevel; i++) {
            int diff = levelMax[i] - levelMin[i] + 1;
            if (ans < diff) {
                ans = diff;
                ansIdx = i;
            }
        }

        bw.write(ansIdx + " " + ans);
        bw.close();
        br.close();
    }

    // 중위 순회
    static void inorder(int root, int level) {
        Node cur = nodes[root];

        // 최대 깊이를 갱신
        maxLevel = Math.max(maxLevel, level);

        // 왼쪽 노드
        if (cur.left != -1) {
            inorder(cur.left, level + 1);
        }

        // 각 레벨에서 가장 왼쪽노드와 오른쪽 노드를 기록
        levelMin[level] = Math.min(levelMin[level], loc);
        levelMax[level] = loc++;

        // 오른쪽 노드
        if (cur.right != -1) {
            inorder(cur.right, level + 1);
        }
    }


    static class Node{
        int parent, cur, left, right;

        Node(int parent, int cur, int left, int right) {
            this.parent = parent;
            this.cur = cur;
            this.left = left;
            this.right = right;
        }
    }
}
```

