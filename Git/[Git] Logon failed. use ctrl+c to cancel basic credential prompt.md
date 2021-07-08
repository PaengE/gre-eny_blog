## 문제 발생

> Logon failed. use ctrl+c to cancel basic credential prompt



Intellij IDEA 에서 Github와 연동하여 push를 하려하면 어느 순간부터 아래와 같은 로그인 창이 떴었다. Git Bash에서도 마찬가지로 두 번의 로그인이 필요했다.

![logon failed-1](C:\Users\82102\OneDrive\티스토리\Git\image\logon failed-1.PNG)

원래 안뜨던 로그인 창이 떠서 id와 pwd를 타이핑하는 번거로움이 생겼는데, 문제는 명백히 오타가 없음에도 불구하고 이마저 아래 사진처럼 실패한다는 것이다.

![logon failed-2](C:\Users\82102\OneDrive\티스토리\Git\image\logon failed-2.PNG)

위 사진에서 id와 pwd를 타이핑하면, 그제서야 push가 성공한다. (매우 불편...)



## 해결법

> Git 의 버전을 최신 버전으로 업데이트하기



검색 결과 Git의 버전을 최신으로 업데이트 해보라는 답변이 있었다.

본인의 경우 Git for Windows 를 사용하며 버전은 `2.28.1` 버전이었다. 버전 확인은 아래 커맨드를 사용한다.

```
git --version
```

확인해보니 현재 최신 버전은 `2.31.1` 버전이다.



아래 명령어로 git을 최신 버전으로 업데이트 할 수 있다.

```
git update-git-for-windows
```

그러면 정말 업데이트 할 것인지 확인차 물어보는 문구가 출력되고 `y`로 대답한다.

그리고 Git을 처음 설치했을 때 처럼 여러가지 옵션을 묻는다. 적당한 옵션을 선택해 주면 될 것이다.



## 결과

![logon failed-3](C:\Users\82102\OneDrive\티스토리\Git\image\logon failed-3.PNG)

로그인 창 없이 명령어만으로 push가 성공된다.