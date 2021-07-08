## 디자인 패턴: 싱글턴 패턴(Singleton Pattern)

> 이 포스팅은 `Head First: Design Patterns` 책을 보고, 개인적으로 정리한 포스팅입니다.

## Singleton Pattern 이란?

> `싱글턴 패턴(Singleton Pattern)`은 해당 클래스의 인스턴스가 하나만 만들어지고, 어디서든지 그 인스턴스에 접근할 수 있도록 하기 위한 패턴이다.

![singleton-1](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\singleton-1.png)

중요한 것은 `정적 변수`와 `정적 메소드`이다. 천천히 살펴보겠다.

## 고전적인 싱글턴 패턴

> 싱글턴 패턴을 구현하기 위해서는 일반적으로 private 생성자를 가진 public 클래스를 이용한다.
>
> 앞서 말한 클래스의 경우에는 인스턴스를 절대 생성할 수 없으므로, 외부에서 생성하는 것이 아닌 인스턴스를 요청하도록 설계하여야 한다. 이 때 사용되는 getInstance() 같은 메소드는 정적메소드여야 한다. (정적 메소드의 경우에는 외부에서 `클래스이름.메소드이름`  으로 호출할 수 있기 때문이다.)

```java
public class Singleton{
	private static Singleton uniqueInstance;
  // 기타 인스턴스 변수
  
  private Singleton(){}
  
  public static Singleton getInstance(){
    if (uniqueInstance == null){
      uniqueInstance = new Singleton();
    }
    return uniqueInstance;
  }
  
  // 기타 메소드
}
```

하지만, 위와 같은 구조의 싱글턴은 문제가 있다.

여러 스레드가 이 싱글턴 인스턴스를 사용하려고 한다고 가정하자. (싱글턴 인스턴스가 생성되어 있지 않은 상태)

이 때, `Race Condition` 문제가 발생한다. JVM은 여러 쓰레드를 왔다갔다하며 실행시킨다. 작업을 공평하게 수행시키기 위해서이다. (Race Condition에 대한 자세한 내용은 검색해보는 것을 추천한다. 중요한 개념이다.)

만약 1번 쓰레드에서 8번 줄의 if 조건문 수행이 끝난 뒤, 2번 쓰레드에서 8번 줄의 if 조건문을 수행한다면 두 개의 쓰레드에서 모두 `싱글턴 인스턴스가 없다`라고 생각하게 된다. 그렇게 되면 두 쓰레드 모두 싱글턴 인스턴스를 생성하게 되며, 설계한대로 동작하지 않게 될 수 있다.

## 멀티 쓰레딩 문제 해결 방법

> 근본적인 해결법으로는 `두 쓰레드가 동시에 같은 코드를 접근하지 않게 하는 방법`이 있다. (동기화)
>
> 하지만 동기화를 시키지 않고도 해결하는 방법이 있다. 상황에 맞게 적절한 방법을 사용하는게 좋을 것 같다.

### getInstance() 메소드의 동기화(Synchronized)

getInstance() 메소드를 동기화시키는 방법이다. (synchronized 키워드를 사용한다.)

이렇게 되면 두 개이상의 쓰레드가 동시에 getInstance()를 사용할 수 없다. 하나가 끝나면 비로소 그 다음 것이 시작된다.

```java
public class Singleton{
	private static Singleton uniqueInstance;
  // 기타 인스턴스 변수
  
  private Singleton(){}
  
  public static synchronized Singleton getInstance(){
    if (uniqueInstance == null){
      uniqueInstance = new Singleton();
    }
    return uniqueInstance;
  }
  
  // 기타 메소드
}
```

이 방법은 큰 단점이 존재한다. 속도 문제이다. 동기화가 필요한 이유는 인스턴스가 존재하지 않을 때, 여러 개의 인스턴스 생성의 예방이다. 그러나 인스턴스를 하나 생성한 뒤에도 여러 쓰레드에서 getInstance() 메소드를 호출하면 동기화를 진행시켜 불필요한 오버헤드만 증가시킨다.

하지만 속도가 크게 상관 없다면, 이대로 사용하는 것도 좋을 것이다. 하지만 메소드를 동기화하면 성능이 100배 정도 저하된다는 것을 알아두고, 필요할 때 개선하면 될 것이다.

### 처음부터 인스턴스를 생성하자

인스턴스를 필요할 때 생성하지 말고, 클래스를 로딩할 때 JVM에서 그 즉시 생성하자는 방법이다.

JVM에서 유일한 인스턴스를 생성하기 전에는 그 어떤 쓰레드도 uniqueInstance 정적 변수에 접근할 수 없다.

```java
 public class Singleton{
   private static Singleton uniqueInstance = new Singleton();
   
   private Singleton() {}
   
   public static Singleton getInstance(){
     return uniqueInstance;
   }
   // 기타메소드
 }
```

하지만 이렇게 구성하면 getInstance() 메소드를 호출하지 않아도 인스턴스를 유지하고 있다는 단점이 있다. 
하지만 싱글턴 인스턴스를 처음부터 사용한다면, 동기화하는 방법과 크게 차이가 나지 않을 수도 있다.

### DCL(Double-Checking Locking)으로 동기화 부분을 줄이자.

DCL(Double-Checking Locking)을 사용하면, 부분적인 코드의 동기화가 가능하다.

```java
public class Singleton{
	private volatile static Singleton uniqueInstance;
  // 기타 인스턴스 변수
  
  private Singleton(){}
  
  public static synchronized Singleton getInstance(){
    if (uniqueInstance == null){
      synchronized (Singleton.class){
        if (uniqueInstance == null){
          uniqueInstance = new Singleton();
        }
      }
    }
    return uniqueInstance;
  }
  // 기타 메소드
}
```

인스턴스가 있는지 확인하고, 없으면 동기화된 블럭으로 들어간다. 블록에 들어온 후에도 다시 한번 인스턴스가 있는지 확인하고 생성한다.

- `volatile` 키워드를 사용하면 멀티쓰레딩을 쓰더라도 uniqueInstance 변수가 Singleton 인스턴스로 초기화 되는 과정이 올바르게 진행되도록 할 수 있다.
- `volatile`은 `변수를 메인 메모리에 저장하겠다.`라는 것을 명시하는 키워드이다. 매번 변수의 값을 Read할 때마다 CPU cache에 저장된 값이 아닌 메인 메모리에서 읽고, 변수의 값을 Write할 때마다 메인 메모리에까지 쓴다.
- `volatile` 키워드를 사용하고 있지 않은 멀티쓰레드 환경에선 Task를 수행하는 동안 성능 향상을 위해 메인 메모리에서 읽은 변수 값을 CPU cache에 저장한다. 만약 멀티쓰레드 환경에서 쓰레드가 변수 값을 읽어올 때 각각의 CPU cache에 저장된 값이 다르기 때문에 `변수 값 불일치 문제`가 발생한다.

위와 같이 코드를 짜면 인스턴스가 생기기 전 처음에만 동기화가 되고, 그 이후로는 동기화가 발생하지 않는다.

따라서 속도가 문제가 될 수 있다면 이런 식으로 Singleton을 구현하여 오버헤드를 극적으로 줄일 수 있다.

## 구현 및 테스트 코드

- https://github.com/PaengE/HeadFirst_DesignPatterns/tree/main/src/headfirst/designpatterns/singleton