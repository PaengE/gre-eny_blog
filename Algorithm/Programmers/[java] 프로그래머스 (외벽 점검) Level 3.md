## Problem : https://programmers.co.kr/learn/courses/30/lessons/60062

## Approach

> 2020 KAKAO BLIND RECRUITMENT 문제였다. (해당 코딩테스트에서 가장 낮은 정답률을 기록하는... 0.6%)
>
> 순열을 이용한 탐색 유형의 문제이다.

처음 문제를 접했을 땐, dist 배열을 오름차순으로 정렬하여 무언가를 해보려고 했으나, 조금 생각한 결과 간단하게도 많은 반례가 생각이나서 고민 차에, 카카오 테크 공식 해설을 보고 코딩을 해보았다.

> https://tech.kakao.com/2019/10/02/kakao-blind-recruitment-2020-round1/
>
> 카카오 테크의 공식해설은 다음과 같다. 나의 풀이에선 비트마스크는 없다.
>
> #### 출제 의도
>
> - 원형으로 주어진 완전탐색 문제를 해결할 수 있는지 파악
> - bit mask나, permutation 등을 활용할 수 있는지 파악
>
> #### 해설
>
> dist의 길이가 최대 8로 크지 않기 때문에, 가능한 모든 방법을 탐색해서 해결할 수 있습니다. 따라서 dist에서 친구 한 명을 선택해 나열하는 방법, 친구 두 명을 선택해 나열하는 방법 … 친구 여덟 명을 선택해 나열하는 방법을 모두 고려해주면 됩니다.
>
> 이제 각각의 방법에 대해 취약지점을 모두 점검할 수 있는지 확인합니다. 먼저 점검해야 될 벽이 원형이 아니라 직선이라고 가정해 보면, 모든 취약지점을 점검하기 위해서는 시작 지점부터 순서대로 배정해야 된다는 점을 알 수 있습니다. 친구 한 명을 배정한 다음에는 아직 점검하지 않은 지점 중에서 바로 다음 지점에 친구를 순서대로 배정하면 됩니다.
>
> 배정을 마친 후에도 아직 점검하지 않은 취약지점이 남아있다면 해당 배치 방법으로는 모든 취약지점을 점검할 수 없다는 뜻입니다. 이제, 원형으로 이루어진 벽을 고려하기 위해, 다음 시작 지점을 기준으로 직선으로 펼쳐주면 됩니다.
>
> 예를 들어, N = 12, weak = [1, 5, 6, 10]인 경우 처음에 위치 1을 기준으로 직선으로 펼쳤다면, 이번에는 위치 5를 기준으로 [5, 6, 10, 13]과 같이 직선 형태로 만들어 주면 됩니다. 이때, 13은 값이 증가하는 형태로 만들어 주기 위해 1 + 12를 해준 결과입니다.
>
> 이제 각 친구들을 선택해 나열하는 방법에 대해서 모든 시작 지점에 대해 직선으로 펼친 후 취약 지점에 배정해본 다음, 그중 가장 적은 친구들을 선택하는 방법을 찾으면 됩니다.

크게 3가지 단계를 거쳐야한다.

1. 마지막 weak point + N 까지 weak point를 펼친다. (마지막 weak point부터 한바퀴를 돌아야 하므로)
2. 순열(Permutation)을 이용하여 사람을 배치할 수 있는 모든 가짓수를 구한다.(1명, 2명, ... , 최대 8명 각각)
3. 구해진 순서대로 weak point를 최대로 할당하여, 전체 weak point를 커버할 수 있는지를 검사한다.
   - 검사를 할 때, 각 weak point를 시작점으로 하여 + N 까지의 범위가 전체 weak point의 범위이다. 
     (한 바퀴를 돈 셈이므로)
   - 전체 weak point를 커버할 수 있다면, 더이상 남은 작업을 진행하지 않는다.
     (적은 사람 수부터 검사를 하였으므로 중복된 답이 있더라도 최소 사람 수는 처음 찾은 답이다.)

## Code

```java
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExteriorWallInspection {
    private int n, answer;
    private int[] weak, dist, spreadWeak;
    private boolean finish;
    public int solution(int n, int[] weak, int[] dist) {
        this.n = n;
        this.weak = weak;
        this.dist = dist;
        makeSpreadWeak();
        finish = false;

        answer = Integer.MAX_VALUE;

        // i자리 순열 만들기
        for (int i = 1; i <= dist.length; i++) {
            permutation(0, i, new boolean[dist.length], new int[i]);
        }

        return answer == Integer.MAX_VALUE ? -1 : answer;
    }

    // 친구로 순열을 만듬
    private void permutation(int depth, int num, boolean[] visited, int[] res) {
        // 종료 flag
        if (finish) {
            return;
        }

        if (depth == num) {
            checkIfCanCover(res);
            return;
        }

        for (int i = 0; i < dist.length; i++) {
            if (!visited[i]) {
                res[depth] = dist[i];
                visited[i] = true;
                permutation(depth + 1, num, visited, res);
                visited[i] = false;
            }
        }
    }

    private void checkIfCanCover(int[] res) {
        // 시작점 i 기준으로 한바퀴를 돔
        for (int i = 0; i < weak.length; i++) {
            int start = i;
            boolean flag = true;

            // 친구 수 만큼
            for (int idx = 0; idx < res.length; idx++) {
                // i 위치에서 weak point 개수만큼
                for (int j = i; j < i + weak.length; j++) {
                    // 두 점 사이의 거리가 검사가능한 거리보다 크면, 커버할 수 없음 -> 다른 친구를 검사함.
                    // 현재 지점을 시작점으로 지정(이전 지점은 검사가 완료됐으므로)
                    if (spreadWeak[j] - spreadWeak[start] > res[idx]) {
                        start = j;
                        idx++;

                        // 현재 지점을 커버할 수 없는데 다음친구가 없다면, 남은 지점들을 검사할 필요가 없음.
                        if (idx == res.length) {
                            flag = false;
                            break;
                        }
                    }

                }

                // 주어진 사람으로 전체 weak point를 커버할 수 있다면
                // 더이상 검사를 진행할 필요가 없다.(작은 크기의 순열부터 만들었으므로 문제에서 요구한 최솟값은 이미 구해짐)
                if (flag) {
                    answer = idx + 1;
                    finish = true;
                    return;
                }
            }
        }
    }

    // weak point를 일자로 펼치는 과정
    private void makeSpreadWeak() {
        int size = weak.length;
        spreadWeak = new int[size * 2 - 1];

        for (int i = 0; i < size; i++) {
            spreadWeak[i] = weak[i];
        }

        for (int i = 0; i < size - 1; i++) {
            spreadWeak[i + size] = weak[i] + n;
        }
    }

    @Test
    public void test() {
        Assertions.assertEquals(2, solution(12, new int[]{1, 5, 6, 10}, new int[]{1, 2, 3, 4}));
        Assertions.assertEquals(1, solution(12, new int[]{1, 3, 4, 9, 10}, new int[]{3, 5, 7}));
        Assertions.assertEquals(2, solution(200, new int[]{0, 100}, new int[]{1, 1}));
        Assertions.assertEquals(3, solution(200, new int[]{0, 10, 50, 80, 120, 160}, new int[]{1, 10, 5, 40, 30}));
        Assertions.assertEquals(1, solution(12, new int[]{0, 10}, new int[]{1, 2}));
        Assertions.assertEquals(1, solution(12, new int[]{4}, new int[]{1, 2}));
        Assertions.assertEquals(2, solution(30, new int[]{0, 3, 11, 21}, new int[]{10, 4}));
        Assertions.assertEquals(-1, solution(200, new int[]{1, 3, 5, 7, 9, 11}, new int[]{1, 1, 1, 1, 1}));
        Assertions.assertEquals(1, solution(19, new int[]{0, 10}, new int[]{9}));
    }
}

```