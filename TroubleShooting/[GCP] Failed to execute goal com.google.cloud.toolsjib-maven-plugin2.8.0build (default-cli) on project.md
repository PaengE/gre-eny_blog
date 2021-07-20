## 도커 이미지 빌드 시 오류

SpringBoot 프로젝트로 `도커 이미지`를 빌드하는 도중 다음과 같은 오류를 만났다.

> [ERROR] Failed to execute goal com.google.cloud.tools:jib-maven-plugin:2.8.0:build (default-cli) on project book: Build image failed, perhaps you should make sure your credentials for 'gcr.io/paeng2-brs/book' are set up correctly. See https://github.com/GoogleContainerTools/jib/blob/master/docs/faq.md#what-should-i-do-when-the-registry-responds-with-unauthorized for help: Unauthorized for gcr.io/paeng2-brs/book: 401 Unauthorized

![perhaps you should make sure your credentials](https://user-images.githubusercontent.com/52652338/126038430-4369ca7d-b8d4-44cd-b48c-5c0ce17d5f45.PNG)



## 해결방법

나의 경우에는 `Docker desktop`이 동시에 켜져있어서 생긴 오류였다.

따라서 `도커 데스크탑`을 종료하고 이미지 빌드를 시도하니 문제가 해결되었다.



## Refer to

- https://docs.docker.com/engine/reference/commandline/login/#credentials-store