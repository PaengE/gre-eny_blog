## Problem : https://programmers.co.kr/learn/courses/30/lessons/12949

## Approach

행렬의 곱셈을 수행하여 그 결과 행렬을 반환하는 문제이다.

간단하게 i x k 행렬 A와 k x j 행렬 B를 곱하면 결과 행렬은 i x j 행렬 C이고,
C[x][z] += A[x][y] * B[y][z]	(x = 0...i, y = 0...k, z = 0...j) 이다.

위의 곱셈식을 이용하여 구현하면 된다.

## Code

```java
public class MatrixMultiplication {
    public static void main(String[] args) {
        int[][] arr1 = {{2, 3, 2}, {4, 2, 4}, {3, 1, 4}};
        int[][] arr2 = {{5, 4, 3}, {2, 4, 1}, {3, 1, 1}};
        MatrixMultiplication mm = new MatrixMultiplication();

        int[][] t = mm.solution(arr1, arr2);
        for (int i = 0; i < arr1.length; i++) {
            for (int j = 0; j < arr2[0].length; j++) {
                System.out.print(t[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int[][] solution(int[][] arr1, int[][] arr2) {
        int n = arr1.length;
        int k = arr1[0].length;
        int m = arr2[0].length;
        int[][] ans = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int l = 0; l < k; l++) {
                    ans[i][j] += arr1[i][l] * arr2[l][j];
                }
            }
        }
        return ans;
    }
}

```

