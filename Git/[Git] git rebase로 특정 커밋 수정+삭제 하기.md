## git rebase

> `git rebase`의 용도는 주로 `merge`에 이용되긴 하지만, 커밋 내역을 수정/삭제 할 때에도 많이 쓰인다.



먼저 `git rebase`를 사용하기 위한 커밋 내역은 다음과 같다. 아래쪽으로 갈수록 최근 커밋이다.

1. `test-1` 생성 후 커밋
2. `test-1` 수정 후 커밋
3. `test-1` 삭제 후 커밋
4. `test-2` 생성 후 커밋

![rebase-1](C:\Users\82102\OneDrive\티스토리\Git\image\rebase-1.PNG)



### Commit message 수정하기

아래 명령어로 최근 n개의 커밋을 확인해 볼 수 있다. `-i` 옵션은 `interactive` 모드를 의미하며 새 에디터가 열린다. (최근 3개의 커밋 내역을 상호작용 모드로)

```bash
$ git rebase -i HEAD~3
```

![rebase-2](C:\Users\82102\OneDrive\티스토리\Git\image\rebase-2.PNG)

여기서 2번째 커밋 메시지`"delete test-1"`를 수정하려면  `pick`키워드를 `reword`키워드로 고쳐준다.

![rebase-3](C:\Users\82102\OneDrive\티스토리\Git\image\rebase-3.PNG)

그러면 아래와 같이 새 에디터가 뜨게 된다. 그 곳에서 커밋 메시지를 변경할 수 있다.

![rebase-4](C:\Users\82102\OneDrive\티스토리\Git\image\rebase-4.PNG)

`delete test-1` 에서 `will be removed`로 커밋 메시지를 수정했다.

![rebase-5](C:\Users\82102\OneDrive\티스토리\Git\image\rebase-5.PNG)

이후 `git log`로 확인해 보면 실제로 커밋 메시지가 변경된 것을 볼 수 있다.



### 특정 commit 삭제하기

특정 커밋을 삭제하기 위해선 커밋 메시지를 수정할 때와 마찬가지로 `git rebase` 명령어를 통해 커밋 내역들을 확인한다.

![rebase-6](C:\Users\82102\OneDrive\티스토리\Git\image\rebase-6.PNG)

그리고 위와 같이 `drop` 키워드를 사용하면 된다.

![rebase-7](C:\Users\82102\OneDrive\티스토리\Git\image\rebase-7.PNG)

다시 한 번 `git log`로 커밋 히스토리를 확인해 보면 없애려던 `will be removed` 커밋이 아예 삭제된 것을 볼 수 있다.

실제 디렉토리에 가보더라도 `test-1을 삭제 후 커밋`한 행동이 사라졌기 때문에 `test-1`파일이 남아있음을 확인할 수 있다.

![rebase-8](C:\Users\82102\OneDrive\티스토리\Git\image\rebase-8.PNG)