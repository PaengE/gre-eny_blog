> 이 포스팅의 모든 코드들은 IntelliJ에서 실행 되었으며, 그를 기반으로 한 내용입니다.
>
> `git reset`을 주로 활용하였으며, 찾던 내용이 아니라면 `git rebase`를 검색해보세요.



## git add 취소하기

> `add`는 해당 파일(디렉토리)들을 track(추적) 한다는 뜻이다. 



`Untracked files(Unstaged 상태)`을 `git add`하게 되면 `Tracked files(Staged 상태)`가 되면서 변화를 감지한다.

add 취소는 아래 명령어로 간단히 처리할 수 있다.

```bash
// 특정 파일 add 취소 (2가지 방법)
$ git reset HEAD <file>
$ git restore --staged <file>

// 전체 파일 add 취소
$ git reset HEAD
```



### Untracked files 삭제하기 (Unstaged 상태의 파일들)

> `git clean`은 Untracked files 만 삭제한다. .gitignore에 명시되어 git의 관리 밖에 있는 파일들은 제거하지 않는다.

```bash
// 디렉토리를 제외한 untracked 파일들만 삭제
$ git clean -f

// untracked 디렉토리를 포함하여 untracked 파일들을 삭제
$ git clean -f -d

// ignored 된 파일까지 삭제
$ git clean -f -d -x
```



## git commit 취소하기

> 여기서는 `git reset`에 대해서 다룬다.
>
> `git reset`은 reset한 시점 이후의 commit 들은 사라지게 되므로 주의하길 바란다.



### commit history 확인하기

먼저 지금까지의 commit history를 최신 순으로 다음 명령어를 통해 확인할 수 있다.

```bash
// commit 내용 확인
$ git log

// commit message 수정 내역까지 확인
$ git log -g

// commit 내용을 한줄로 확인
$ git reflog
```

기본적으로 모든 commit history를 보여준다. `Enter`키를 사용하여 계속 볼 수 있으며 `q`를 입력하여 그만 볼 수 있다.

최신 순으로 특정 개수만 보고 싶다면 `-n` 옵션을 주어 원하는 개수만큼만 확인할 수 있다.



### commit 취소하기

먼저 간단한 규칙을 알고가자.

- `HEAD`는 commit 내역 중 가장 최근의 것을 의미한다.
- `^`은 최근에서부터 ^의 개수까지 commit을 의미한다.
- `~n`은 최근에서부터 n까지의 commit을 의미한다.

예를 들어 `HEAD^^^`는 `HEAD~3`과 의미가 같다. (최근 commit 부터 총 3개의 commit)

commit의 취소는 아래와 같이 3가지의 경우가 있다.

```bash
// 1. commit을 취소하고, 해당 파일들은 staged 상태로 working directory에 보존
$ git reset --soft HEAD^

// 2. commit을 취소하고, 해당 파일들은 unstaged 상태로 working directory에 보존
$ git reset --mixed HEAD^
$ git reset HEAD^

// 3. commit을 취소하고, 해당 파일들은 unstaged 상태로 working directory에서 삭제
$ git reset --hard HEAD^
```

reset의 default option은 `--mixed`이므로 2번 방법을 사용할 땐 생략해도 된다.

위에서 언급했던 대로 최근 commit 부터 원하는 개수만큼의 commit을 취소하기 때문에 사용에 주의가 필요하다.

만약 `More?`이라고 묻는다면 `^`로 답변하면 된다.



### commit message 수정하기

다음 명령어로 가장 최근 commit의 commit message를 수정할 수 있다. 

```bash
$ git commit --amend
```

편집기가 새 창에 뜨면, commit message를 변경하고 편집기를 종료하면 된다.

그리고 commit history를 확인해보면 제대로 변경되어 있을 것이다.



## git push 취소하기

> push를 취소한다는 것은 remote repository의 내용을 해당 commit 상태로 돌리겠다는 의미이다.
>
> 따라서 해당 commit 이후의 모든 commit 정보들은 사라지게 되므로, 개인 프로젝트가 아니라면 정말로 신중히 사용하여야 한다.



#### 1. 돌아갈 commit 찾기

먼저 원하는 시점의 commit을 찾아야 한다.

```bash
// commit history를 보고 원하는 commit id를 찾는다.
$ git reflog 또는 $ git log

// 해당 시점으로 working directory를 되돌린다.
$ git reset HEAD@{number} 또는 $ git reset [commit id]
```



#### 2. 다시 commit 하기

```bash
$ git commit -m "~~~"
```



#### 3. remote repository에 강제로 push 하기

강제로 push 하지 않으면 에러가 뜨기 때문이다. 

```bash
$ git push origin [branch name] --force
$ git push origin [branch name] -f
$ git push origin +[branch name]
```

3개 명령어 중 아무거나 사용해도 된다.