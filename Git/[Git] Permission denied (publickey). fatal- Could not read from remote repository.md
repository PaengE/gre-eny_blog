## 문제 발생

> 디렉토리 주소 >git push -u origin main
> Warning: Permanently added the RSA host key for IP address 'xxx.xxx.xxx.xxx' to the list of known hosts.
> git@github.com: Permission denied (publickey).
> fatal: Could not read from remote repository.
>
> Please make sure you have the correct access rights
> and the repository exists.



IntelliJ에서 새 프로젝트를 Github에 push를 시도하였다. 

- 참고로 Window IntelliJ에서는 `ctrl`+`shift`+`a`을 사용해 `Action` 검색창을 열어 `share project on github`으로 간단하게 repository 생성과 동기화 및 커밋 푸쉬가 가능하다.

본인은 새로운 기기도 아니고, IntelliJ에는 계정 동기화가 이미 수행되었는데도 불구하고 다음과 같은 에러를 뱉었다.

![permission denied-1](C:\Users\82102\OneDrive\티스토리\Git\image\permission denied-1.png)

에러 내용은 publickey가 어쩌구 permission denied 되어, 어쨌든 remote repository로부터 읽을 수 없다는 내용이다. 



## 해결법

여러 해결방법이 있다고는 하는데 본인은 다음 방법으로 문제를 해결했다.



> #### ssh key 새로 생성

- Git이 설치되어 있다는 가정 하에, Git Bash를 실행한다. 그리고 아래 명령어를 실행한다.

- ```bash
  ssh-keygen -t rsa -b 4096 -C "email@email.com"
  ```

- 아래 그림의 빨간 줄 쳐져있는 부분이 출력될 것이다. 처음 수행하는 것이라면, 키가 이미 존재하여 overwrite 할 것인가는 나오지 않을 것이다.

![permission denied-2](C:\Users\82102\OneDrive\티스토리\Git\image\permission denied-2.png)

- public key가 저장된 디렉토리로 가서 `id_rsa.pub` 파일을 열어 안의 내용을 복사한다.
- Github 자신의 계정에 로그인하여 우측 상단 아이콘의 `Settings` - `SSH and GPG keys` 에 들어가 `New SSH key`를 클릭한다.
- 적당한 title을 주고, Key에 위에서 복사한 내용을 붙여 넣고 `Add SSH key`를 클릭한다.

![permission denied-3](C:\Users\82102\OneDrive\티스토리\Git\image\permission denied-3.png)

- 그런 후 push 를 수행하면 위와 같은 에러는 다시 뜨지 않을 것이다.
