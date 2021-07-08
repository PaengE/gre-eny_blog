## AWS EC2 AMI2 빌드 에러 - Cannot allocate memory 

> AWS EC2에서 Spring Boot 프로젝트를 빌드할 때 아래와 같은 에러가 발생했다.
>
> 구글링을 참고하여 에러 해결방법을 정리해봤다.

```shell
# OpenJDK 64-Bit Server VM warning: INFO: os::commit_memory(0x00000000eaaa0000, 178978816, 0) failed; error='Cannot allocate memory' (errno=12)
#
# There is insufficient memory for the Java Runtime Environment to continue.
# Native memory allocation (mmap) failed to map 178978816 bytes for committing reserved memory.
```

원인은 JVM 구동 시 메모리가 부족하여 생기는 현상이라고 한다. 이유는 두가지다.

- 시스템의 물리적 RAM 또는 SWAP 공간의 부족
- 프로세스 크기 제한에 도달

해결방법은 여러가지가 있다.

- 시스템의 메모리 로드 줄이기.
- 물리적 메모리 또는 SWAP 공간 늘리기.
- SWAP 백킹 저장소가 가득 차 있는지 확인.
- 64 비트 OS에서 64 비트 Java 사용. (EC2 인스턴스 확인 명령어: $ getconf LONG_BIT)
- Java 힙 크기 줄이기 (-Xmx / -Xms).
- Java 스레드 수 줄이기
- Java 스레드 스택 크기 줄이기 (-Xss)
- -XX:ReservedCodeCacheSize= 를 사용하여 더 큰 코드 캐시 설정

필자는 SWAP 공간을 늘리는 방법을 사용해서 문제를 해결했다.



## SWAP 공간 생성 방법

다음 명령어로 SWAP 공간을 확인할 수 있다.

```shell
$ free -h
```

![free -h result-1](C:\Users\82102\OneDrive\티스토리\Linux\image\free -h result-1.JPG)

> 다음부터 있을 명령어들은 관리자 계정으로 실행하거나 sudo를 앞에 붙여서 사용하도록 한다.(권한 문제)

1. SWAP 파일을 생성한다. 크기는 1.5~2배 정도로 설정하는 것이 권장된다.

   ```shell
   $ touch /var/spool/swap/swapfile 
   $ dd if=/dev/zero of=/var/spool/swap/swapfile count=2048000 bs=1024
   ```

   경우에 따라서 swap 디렉토리가 없을 경우가 있다.(필자의 경우) 그럴 땐 그냥 새로 만들어주면 된다.

2. 시스템에서 접근 가능하도록 권한을 설정한다.(600)

   ```sh
   $ chmod 600 /var/spool/swap/swapfile
   ```

3. 파일 포맷을 SWAP으로 변환 후 SWAP file 등록한다.

   ```sh
   $ mkswap /var/spool/swap/swapfile
   $ swapon /var/spool/swap/swapfile
   ```

4. 파일 시스템 테이블(/etc/fstab)에 등록한다.

   ```sh
   $ vim /etc/fstab
   ```

   ```sh
   #추가
   /var/spool/swap/swapfile    none    swap    defaults    0 0
   ```

5. SWAP 파일이 정상적으로 등록되었는지 확인한다.

   ```sh
   $ free -h
   ```

![free -h result-2](C:\Users\82102\OneDrive\티스토리\Linux\image\free -h result-2.JPG)

이후 다시 빌드해보니 아까와 같은 에러는 사라졌다.

## SWAP 공간 삭제 방법

> 스왑파일은 한번 설정하면 굳이 삭제할 필요는 없지만, 추가로 삭제 방법을 포스팅하겠다.

1. SWAP을 비활성화 한다.

   ```sh
   $ swapoff -v /var/spool/swap/swapfile
   ```

2. SWAP 파일을 생성할 때 /etc/fstab에 추가한 내용을 삭제한다.

3. 생성했던 swapfile을 삭제하면 비활성화가 끝난다.

   ```sh
   rm /var/spool/swap/swapfile
   ```

4. `free -h`명령어로 다시 확인하면 swap 공간이 비어있는 것을 볼 수 있다.

![free -h result-1](C:\Users\82102\OneDrive\티스토리\Linux\image\free -h result-1.JPG)