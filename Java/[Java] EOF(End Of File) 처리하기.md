## EOF(End Of File)

> `EOF(End Of File)`은 데이터 소스로부터 더 이상 읽을 수 있는 데이터가 없음을 의미한다.



`Scanner`와 `BufferedReader` 두 클래스의 `EOF 처리`를 알아보자.

`Scanner` 보다 `BufferedReader` 가 성능면에서 우월하다.



### Scanner

```java
Scanner sc = new Scanner(System.in);

while(sc.hasNextLine()) {
  sc.nextLine();
}

while(sc.hasNext()) {
  sc.next();
}
```



### BufferedReader

```java
BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
String input = "";

// 1
while((input = br.readLine()) != null) { ... }

// 2
while((input = br.readLine()) != null && !input.isEmpty()) { ... }

// 3
while(!(input = br.readLine()).equals("")) { ... }
```

- `1번` 코드는 백준 사이트처럼 입력 자체가 `파일`로 들어온다면 `EOF`를 정상적으로 처리가능하다. 하지만  IntelliJ같은 IDE에서는 입력의 끝을 알 수 없다. (EOF를 찾지 못해 프로그램이 끝나지 않는다.)
- `2번` 코드는 1번 코드의 문제를 보완(?)할 수 있다. 입력의 끝에 `Enter`를 한 번 더 입력하면 그 입력을 `EOF`로 판별하여 처리한다.
- `3번` 코드는 역으로 IDE에서는 `Enter`로 `EOF`를 찾을 수 있지만, 백준 사이트처럼 입력 자체가 `파일`이라면 `RuntimeError`가 뜬다. (읽을 라인이 없는데 읽은 후, equals()를 수행하기 때문이다.)



`IDE` 터미널 창에서는 입력의 끝이 없다.입력을 계속 기다리는 상태이기 때문에, `EOF`를 명시해주지 않으면 프로그램이 끝나지 않게 된다.

반면 파일에서는, 입력의 끝이 파일 내에 저장되어 있으므로 별도로 `EOF`를 명시하지 않아도 된다.

결론은 맘 편한 `2번` 코드를 쓰자...