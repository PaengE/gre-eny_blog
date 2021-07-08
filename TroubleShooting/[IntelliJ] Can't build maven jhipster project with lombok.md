## Can't build maven jhipster project with lombok

> 해당 문제는 프로젝트 내에서는 `lombok`이 문제없이 동작하였으나, `build` 단계에서 getter/setter 메소드를 찾을 수 없다거나 (`Cannot find symbol`), Constructor 생성자가 생성이 안되어 에러를 내뱉으며 발생하였다.

해당 프로젝트에 lombok plugin을 설치하고, pom.xml 에 dependency를 주입하고, enable annotation proccessor 를 체크하면 프로젝트에서 lombok을 사용할 수 있다.

하지만 빌드 시에는 위와 같은 에러를 내뱉는다.

lombok 설치와 annotation에 대해서는 아래 포스팅을 참고하길 바란다.

[https://gre-eny.tistory.com/303﻿](https://gre-eny.tistory.com/303)



Jhipster 는 기본적으로 lombok 을 공식지원하지 않아 `entity` 클래스에 `getter/setter` 메소드가 작성되어 있다.

하지만 그럼에도 jhipster에서 lombok을 사용할 수는 있다.

아래 코드를 pom.xml 에 삽입하고 Sync를 수행하고 빌드를 하면 에러가 사라짐을 확인할 수 있다.

```xml
// pom.xml

...
  <annotationProcessorPaths>
    <path>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <version>1.18.18</version>
    </path>
    ...
  </annotationProcessorPaths>
...
```



## Refer to

- https://stackoverflow.com/questions/44602317/cant-build-maven-jhipster-project-with-lombok

