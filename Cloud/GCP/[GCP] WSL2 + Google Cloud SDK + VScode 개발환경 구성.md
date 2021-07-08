> [ 목 표 ]
>
> Windows 환경의 터미널과 vscode에서 Google Cloud Platform 에 remote 접속하여 제어하는 것이다.
>
> `GCP` 와 연동은 다른 포스트에 작성할 계획이다.



## Windows Terminal 설치하기

- `시작` - `Microsoft Store` 에서 `Windows Terminal`을 검색하여 설치할 수 있다.

![wsl2+google cloud sdk+vscode-1](C:\Users\82102\OneDrive\티스토리\Cloud\GCP\image\wsl2+google cloud sdk+vscode-1.PNG)



## WSL2 설치하기

> 이번 단락의 모든 명령어는 `Windows PowerShell`을 `관리자 권한`모드로 실행시킨 환경에서 실행한다.

[읽어보기: WSL1 WSL2 차이](https://docs.microsoft.com/ko-kr/windows/wsl/compare-versions)



- `Linux 용 Windows 하위시스템`을 사용가능하게 설정하기

```
dism.exe /online /enable-feature /featurename:Microsoft-Windows-Subsystem-Linux /all /norestart
```

- `Virtual Machine 플랫폼`을 사용가능하게 설정하기

```
dism.exe /online /enable-feature /featurename:VirtualMachinePlatform /all /norestart
```

- `Linux 커널 업데이트 패키지` 다운 및 설치
  - https://wslstorestorage.blob.core.windows.net/wslblob/wsl_update_x64.msi



### Ubuntu 설치하기

`시작` - `microsoft store` 에 `ubuntu`를 검색하여 설치한다. (본인이 포스팅에 사용한 버전은 20.04 LTS)

![wsl2+google cloud sdk+vscode-2](C:\Users\82102\OneDrive\티스토리\Cloud\GCP\image\wsl2+google cloud sdk+vscode-2.PNG)



#### WSL2를 기본 버전으로 설정하기

```shell
wsl --set-default-version 2
```



#### 기존 WSL1 을 WSL2로 바꾸기

```shell
// wsl 버전확인
wsl --list --verbose

// 출력 예시
  NAME                   STATE           VERSION
* Ubuntu-20.04           Running         1
  docker-desktop-data    Running         2
  docker-desktop         Running         2
```

```shell
// Ubuntu-20.04 를 wsl2로 변환
wsl --set-version ubuntu-20.04 2

// wsl 버전확인
wsl --list --verbose

// 출력 예시
  NAME                   STATE           VERSION
* Ubuntu-20.04           Running         2
  docker-desktop-data    Running         2
  docker-desktop         Running         2
```



## Google Cloud SDK 설치하기

> `google cloud sdk` 를 설치하기 앞서 `python3`이 필수로 설치되어 있어야 한다. [참고](https://cloud.google.com/sdk/gcloud/reference/topic/startup)
>
> 이번 단락의 모든 명령어는 앞서 설치한 `Windows Terminal` 을 실행한 뒤, `Ubuntu` 환경에서 실행되었다.

![wsl2+google cloud sdk+vscode-3](C:\Users\82102\OneDrive\티스토리\Cloud\GCP\image\wsl2+google cloud sdk+vscode-3.PNG)



### Python3 설치하기

```shell
// apt 버전 업데이트
sudo apt update

// python3 설치
apt install -y python3 python3-pip

// 설치확인
python3
```



### google cloud sdk 설치하기

```shell
curl https://sdk.cloud.google.com | bash
```

- 위 방법은 설치프로그램을 이용한 것이므로 항상 최신 버전이 설치된다.
- 설치 중 나오는 안내 메시지는 모두 `y`로 체크해도 된다. (읽어보고 결정해도 된다.)



### Shell 재시작 후 설치 확인

```shell
exec -l $SHELL
```

```shell
gcloud version

// 출력 예시
Google Cloud SDK 347.0.0
bq 2.0.69
core 2021.06.25
gsutil 4.64
```



## VScode 에서 사용하기

> `VScode`는 설치되어 있다는 가정하에 진행하겠당.



- `왼쪽 네비게이션 바` - `확장(MarketPlace)`에서 `Remote - WSL`을 검색하여 설치한다.
- `ubuntu` 환경에서 `code .` 를 입력하면 vscode 가 그 위치에서 실행된다.



## 소소한 팁

- `Windows Terminal`에서 `Ubuntu`로 접속하면, 윈도우의 디렉토리로 시작된다.
  `cd` 를 입력하면 `Ubuntu` 의 홈 디렉토리로 이동가능하다.
- 윈도우와 `파일공유`가 가능하지만, 윈도우 환경에서의 복붙으로는 우분투에서 인식하지 못한다.
  프로젝트의 clone이 필요하다면, `Git`과 같은 형상관리 프로그램을 사용하여 `clone` 하기를 추천한다.
  (`cp`명령어로 가능하나 생각보다 파일전송 시간이 오래걸린다.)



## Refer to

- https://majjangjjang.tistory.com/164
- https://cloud.google.com/sdk/gcloud/reference/topic/startup