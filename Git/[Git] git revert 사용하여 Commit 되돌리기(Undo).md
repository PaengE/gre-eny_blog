## git revert

> 원하는 특정 commit 상태를 되돌린다.(Undo 개념) 이 때, 나머지 commit들을 그대로 유지시킨다.
>
> 그렇기 때문에 remote repository 충돌이 적게 난다.
>
> 같은 기능을 수행하지만, 되돌린 이후의 commit 을 모두 제거하는 `git reset`과는 차이가 있다.



먼저 `git revert` 실습을 하기위하여 아래 순서와 같은 커밋들을 수행했다.

1. `test-1` 생성 후 커밋
2. `test-2` 생성 후 커밋
3. `test-2` 삭제 후 커밋
4. `test-3` 생성 후 커밋
5. `test-4` 생성 후 커밋
6. `test-4` 내용에 "hello world"를 추가 후 커밋
7. `test-5` 생성 후 커밋

![revert-1](C:\Users\82102\OneDrive\티스토리\Git\image\revert-1.PNG)

현재 상태에서는 `test-1`, `test-3`, `test-4`, `test-5` 파일만 존재한다.

3번 과정의 커밋(`HEAD@{4}`)을 revert 한다면, `삭제` 한 내용이 Undo 되기 때문에 `test-2` 파일이 다시 생겨난다.

이 때 다음 그림처럼 revert 한 내용도 commit history에서 볼 수 있고, 나머지 커밋들도 그대로 유지되어 있는 것을 확인할 수 있다.

![revert-2](C:\Users\82102\OneDrive\티스토리\Git\image\revert-2.PNG)



여러 커밋을 한꺼번에 revert 하려면 `git revert dc168cb..3a34149`와 같이 사용할 수 있다.

`dc168cb`이후 커밋부터 `3a34149`까지의 커밋을 revert한다는 의미이다.

이 경우 revert 커밋은 본디 커밋한 순서 반대로 각각 생성된다.