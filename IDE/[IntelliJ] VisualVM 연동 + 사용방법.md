> 내 환경은 다음과 같다. 다른 버전에서는 차이가 있을 수 있다.
>
> - IntelliJ IDEA 2020.2.2
> - Java 11
> - Windows 10
>
> 나는 코딩테스트를 준비하며, 내가 사용한 로직이 얼만큼의 메모리를 쓰는지 확인하기 위해 설치했다.
>
> 그 외에도 많은 정보를 제공해 주는데, 그에 관해서는 마지막 단락의 내가 참고했던 블로그를 방문하면 좋을 것이다.

## VisualVM 설치하기

https://visualvm.github.io/download.html

위 링크로 가서 OS에 맞는 파일을 다운로드한 후, 압축을 푼다.

폴더의 위치는 어디든 상관 없으나, IntelliJ에서 path 설정을 해주어야 하기 때문에 기억하기 쉬운 곳으로 위치시킨다.

## IntelliJ 와 연동하기

https://plugins.jetbrains.com/plugin/7115-visualvm-launcher/

위 링크에서 우측상단 `Install to IDE`를 클릭하거나, 직접 IntelliJ의 Marketplace에서 `visualVM`을 검색하여 설치한다.

설치가 완료되면 `Settings` - `VisualVM Launcher` 설정으로 이동하여 다음 빨간색 박스와 보라색 박스에 대해 처리를 한다.

![visualVM](C:\Users\82102\OneDrive\티스토리\IDE\image\visualVM.jpg)

- 빨간색박스: `압축 풀었던 visualVM 폴더의 위치/bin/visualvm.exe` 의 경로를 넣어준다.
- 보라색박스: `사용하고 있는 java 버전의 위치` 경로를 넣어준다.

그러면 다음과 같은 icon 이 보일 것이고, 저 아이콘을 클릭하여 `Run/Debug`하면 visualVM 프로그램이 실행된다.

![visualVM-icon](C:\Users\82102\OneDrive\티스토리\IDE\image\visualVM-icon.jpg)

VisualVM 실행 화면이다. 실행시킨 내 어플리케이션 프로그램의 CPU, Heap, Classes, Threads 를 확인할 수 있다.

![visualVM-exe](C:\Users\82102\OneDrive\티스토리\IDE\image\visualVM-exe.jpg)

## VisualVM 사용하기

### Sampler vs Profiler

- `Sampler`가 수행하는 작업인 `Sampling`은 주기적(20ms ~ 10,000ms)으로 메소드 콜 정보(쓰레드 덤프 사용)와 메모리 사용 정보를 스냅샷하고 그 결과를 분석하여 메소드 별 혹은 쓰레드 별 CPU 실행 시간을 수집하는 것을 말한다. 그렇기에 정확한 분석은 아니지만, 분석 대상 어플리케이션의 성능에 큰 영향을 주지 않는다.
- `Profiler`가 수행하는 작업인 `Profiling`은 메소드 별 CPU 실행 시간을 분석하기 위해 함수의 진입과 진출부에 바이트 코드를 삽입하여 정확한 통계를 집계한다. 따라서 프로파일러를 실행해 보면 셋업 과정에서도 약간의 시간이 소요되며, 실행되는 과정에서도 분석 대상 어플리케이션의 성능에 영향을 미친다.

#### Sampler와 Profiler의 차이점을 표로 정리하였다.

|                                   | Sampler                                                      | Profiler                                     |
| --------------------------------- | ------------------------------------------------------------ | -------------------------------------------- |
| 동작 방식                         | 주기적(20 ms ~ 10000 ms) 스레드 덤프 결과 분석               | 메소드 진입부와 진출부에 통계 수집 코드 삽입 |
| 정확도                            | Approximated. 하지만, Profiler와 비슷한 양상으로 관측 됨.    | Precise                                      |
| 수집 결과                         | 각 메소드별 CPU 실행 시간, 각 스레드별 CPU 실행 시간         | 각 메소드별 CPU 실행 시간                    |
| 수집시 어플리케이션에 미치는 영향 | 적음                                                         | 큼                                           |
| 제약 사항                         | JIT 컴파일러에 의해 실행 시간에 Inline Call로 호출되는 메소드에 대해서는 정확한 수집이 되지 않음. |                                              |

### Thread Dump, Heap Dump 

`쓰레드 덤프`와 `힙 덤프`를 프로그램 내에서 지원하며 실행 시킨 후의 결과를 저장하여 보여준다.

### Perform GC

`Garbage Collector`를 실행시켜 어플리케이션의 메모리 사용을 실험하기 전에 초기화를 할 수 있다.

따라서 특정 시점에 얼마나 실제 메모리를 사용하는지 확인할 수 있다.

## Refer to

- https://bestugi.tistory.com/39
- https://bcho.tistory.com/789
- https://m.blog.naver.com/PostView.nhn?blogId=simpolor&logNo=221311749562&proxyReferer=https:%2F%2Fwww.google.com%2F

