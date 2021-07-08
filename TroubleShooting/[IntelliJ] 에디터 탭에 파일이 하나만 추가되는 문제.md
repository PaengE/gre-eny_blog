## IntelliJ 에디터 탭에 파일 추가가 안되는 경우

> intelliJ 를 사용하면서 다른 파일을 에디터 탭에 띄워 작업하는 경우가 비일비재하다.
>
> 그런데 갑자기 다른 파일을 더블클릭하여 열려고 하니, 기존에 에디터 탭에 있는 파일이 직전에 누른 파일로 교체되기만 할 뿐 추가가 되지않았다. (에디터 탭에 하나의 파일밖에 올라가지 않았다.)
>
> 아래와 같은 방법으로 해결은 하였으나, 이런 현상이 벌어진 이유는 정확히 모르겠다... 설정을 전혀 건드리지 않았는데 갑자기 발생한 문제이다.
>
>
> 이 프로젝트는 알고리즘 문제풀이를 위한 프로젝트로 각각의 자바파일이 독립된(한 파일에서 다른 파일을 참조하지 않는다.) 파일들이며, 다른 프로젝트에서도 똑같은 문제가 발생했다. (물론 IDE 설정 문제때문이니까...)

![intellij-editor-tab-2](C:\Users\82102\OneDrive\티스토리\IDE\image\intellij-editor-tab-2.png)

- 여기서 A 라는 파일을 더블클릭하면, 평소엔 에디터 탭에 표시한 오른쪽 빨간 박스 안에 들어왔다.
- 하지만 다음과 같이 BOJ1003.java가 에디터 탭에서 사라지고, A.java 가 대체되는 문제가 생겼다.

![intellij-editor-tab-3](C:\Users\82102\OneDrive\티스토리\IDE\image\intellij-editor-tab-3.png)

## 해결 방법

![intellij-editor-tab-1](C:\Users\82102\OneDrive\티스토리\IDE\image\intellij-editor-tab-1.png)

> 결론적으로 체크되어 있던 빨간색 체크박스를 해제하면서 문제가 해결되었다.
>
> 한글로 번역하면 `메소드, 클래스 또는 변수로 이동할 때 해당 선언이 포함된 소스파일이 변경사항이 없는 경우 현재 탭을 대체한다.` 라고 적혀있다.

저 설정이 체크가 되어있어도 나와 같은 현상을 겪지 않는 분도 봤다. (MacOS이고 정확한 설정과 코드는 모르지만...)

그래서 정확한 이유를 모르겠다라는 것이다. 어쨌든 문제를 해결하긴 해서 다행이다.

## Official Document

- https://www.jetbrains.com/help/idea/settings-editor-tabs.html#opening-policy