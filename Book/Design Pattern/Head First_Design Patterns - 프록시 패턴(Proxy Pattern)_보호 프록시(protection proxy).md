## 디자인 패턴: 프록시 패턴(Proxy Pattern)

> 이 포스팅은 `Head First: Design Patterns` 책을 보고, 개인적으로 정리한 포스팅입니다.

## Proxy Pattern 이란?

> `프록시 패턴(Proxy Pattern)` 은 어떤 객체에 대한 접근을 제어하기 위한 용도로, 대리인이나 대변인에 해당하는 객체를 제공하는 패턴이다.
>
> 프록시 패턴을 사용하면 원격 객체라든가 생성하기 힘든 객체, 보안이 중요한 객체와 같은 다른 객체에 대한 접근을 제어하는 대변자 객체를 만들 수 있다.
>
> 먼저 Proxy Pattern에서 사용되는 용어와 방법들을 알아보고, 예제를 들겠다.

### 프록시에서 접근을 제어하는 방법

- `원격 프록시(remote proxy)`를 써서 원격 객체에 대한 접근을 제어할 수 있다.
- `가상 프록시(virtual proxy)`를 써서 생성하기 힘든 자원에 대한 접근을 제어할 수 있다.
- `보호 프록시(protection proxy)`를 써서 접근 권한이 필요한 자원에 대한 접근을 제어할 수 있다.

![proxy-1](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\proxy-1.png)

## 보호 프록시(Protection Proxy)

> `보호 프록시`는 접근권한을 바탕으로 객체에 대한 접근을 제어하는 프록시이다.
>
> 자바의 java.lang.reflect 패키지에 들어있는 프록시 기능을 이용하면 즉석에서 한개 이상의 인터페이스를 구현하고 메소드 호출을 지정해 준 클래스에 전달할 수 있는 프록시 클래스를 만들 수 있다.
>
> 실제 프록시 클래스는 실행 중에 생성되기 때문에 `동적 프록시(Dynamic Proxy)`라고 부른다.

![proxy-6](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\proxy-6.png)

- 프록시는 `Proxy`와 `InvocationHandlerClass` 두개의 클래스로 구성된다.
- `Proxy`클래스는 자바에 의해 생성되며 Subject 인터페이스 전체를 구현한다.
- `InvocationHandler` 클래스는 Proxy 객체에 대한 모든 메소드 호출을 전달받는  `InvocationHandler`를 제공해야한다. `InvocationHandler`에서 `RealSubject` 객체에 있는 메소드에 대한 접근을 제어한다.

Proxy 클래스는 자바가 만들어주기 때문에, Proxy 클래스에게 무슨 일을 할 지 알려주기 위한 방법이 필요하다.

Proxy를 직접 구현하지 않기 때문에 필요한 코드를 만들 수 없는데, 이 코드를 InvocationHandlerClass에 집어넣음으로써, Proxy에서 메소드 호출을 받으면 항상 InvocationHandlerClass에 진짜 작업을 부탁하게끔 만든다.

## 객체마을 결혼 정보

> 고객들이 서로 상대방을 평가할 수 있는 기능을 갖춘 결혼 정보 서비스를 만들었다고 가정하자.
>
> 이 서비스는 어떤 사람에 대한 정보를 설정하거나 가져올 수 있게 해주는 PersonBean을 중심으로 돌아간다.

```java
public interface PersonBean {
  String getName();
  String getGender();
  String getInterests();
  int getHotOrNotRating();
 
  void setName(String name);
  void setGender(String gender);
  void setInterests(String interests);
  void setHotOrNotRating(int rating); 
}
```

하지만 단지 위의 인터페이스를 구현한다면, 자기가 직접 자기 점수를 메긴다거나, 다른 고객의 정보를 수정할 수 있게 된다. (어떤 클라이언트에서든지 모든 메소드를 마음대로 호출할 수 있기 때문이다.)

이런 경우에 `보호 프록시(Protection Proxy)`를 사용해야 한다.

## PersonBean 용 동적 프록시(Dynamic Proxy) 만들기

> 자바 API의 동적 프록시 기술을 활용해서 보호 프록시를 만들어 보자.

### 1단계: InvocationHandler 만들기

`InvocationHandler`에서는 프록시의 행동을 구현한다.

프록시 클래스 및 객체를 만드는 일은 자바에서 알아서 해주기 때문에, 프록시의 메소드가 호출되었을 때 할 일을 지정해주는 핸들러만 만들면 된다.

호출 핸들러는 두개 만들어야 한다. 하나는 자기 자신을 위한 핸들러, 다른 하나는 타인을 위한 핸들러.

> `호출 핸들러(Invocation Handler)`는 다음과 같이 생각하면 된다.
>
> 프록시의 메소드가 호출되면 프록시에서는 그 호출을 호출 핸들러에게 넘긴다.
>
> 하지만 호출 핸들러는 호출된 메소드가 무엇이든 무조건 핸들러의 invoke() 메소드가 호출된다.

```java
import java.lang.reflect.*;
 
public class OwnerInvocationHandler implements InvocationHandler { 
	PersonBean person;
 
	public OwnerInvocationHandler(PersonBean person) {
		this.person = person;
	}
 
	public Object invoke(Object proxy, Method method, Object[] args) throws IllegalAccessException {
  
    try {
        if (method.getName().startsWith("get")) {
          return method.invoke(person, args);
        } else if (method.getName().equals("setHotOrNotRating")) {
          throw new IllegalAccessException();
        } else if (method.getName().startsWith("set")) {
          return method.invoke(person, args);
        } 
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } 
		return null;
	}
}
```

- `InvocationHandler`는 `java.lang.reflect` 패키지에 들어있다. 호출 핸들러는 반드시 InvocationHandler 인터페이스를 구현해야 한다.
- 생성자를 통해 전달받은 주 객체에 대한 레퍼런스를 저장한다.
- 호출하는 주 객체가 무엇인지, 호출하는 메소드가 무엇인지에 따라 주 객체의 메소드를 호출할지, 예외를 던질지 정한다.

### 2단계: 동적 프록시를 생성하는 코드 작성

Proxy 클래스를 생성하고 Proxy 객체 인스턴스를 만들어야 한다. (OwnerInvocationHandler 혹은 NonOwnerInvocationHandler한테 넘겨주는 프록시를 만든다.)

```java
PersonBean getOwnerProxy(PersonBean person) {
    return (PersonBean) Proxy.newProxyInstance( 
        person.getClass().getClassLoader(),
        person.getClass().getInterfaces(),
        new OwnerInvocationHandler(person));
}

PersonBean getNonOwnerProxy(PersonBean person) {
    return (PersonBean) Proxy.newProxyInstance(
        person.getClass().getClassLoader(),
        person.getClass().getInterfaces(),
        new NonOwnerInvocationHandler(person));
}
```

- Person 객체(실제 주 객체)를 인자로 받아오고 프록시를 리턴한다. 프록시의 인터페이는 주 객체의 인터페이스와 같기 때문에 리턴 타입은 PersonBean 이다.
- 리턴 문에서 프록시를 생성한다. 코드 설명은 다음과 같다.
  - line 2: Proxy 클래스에 있는 newProxyInstance라는 정적 메소드를 써서 프록시를 생성한다.
  - line 3: person의 클래스로더를 인자로 전달한다.
  - line 4: 프록시에서 구현해야 하는 인터페이스도 인자로 전달해야 한다..
  - line 5: 호출 핸들러의 인자로 person을 전달하며, 호출 핸들러인 OwnerInvocationHandler도 인자로 전달해야 한다. 

### 3단계: PersonBean 객체를 적절한 프록시로 감싼다.

`PersonBean` 객체를 사용하는 객체는 고객 자신의 객체거나, 다른 고객의 객체 중 하나이다.

어떤 경우든 해당 PersonBean에 따라 적절한 프록시를 생성해야 한다.

![proxy-7](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\proxy-7.png)

- `OwnerInvocationHandler`: 자기 자신 빈에 직접 접근하는 경우
- `NonOwnerInvocationHandler`: 다른 사람의 빈에 접근하는 경우

## 기타 다른 프록시들

- `방화벽 프록시(Firewall Proxy)`: 일련의 네트워크 자원에 대한 접근을 제어함으로써 주 객체를 "나쁜" 클라이언트들로부터 보호해 준다.
- `스마트 레퍼런스 프록시(Smart Reference Proxy)`: 주 객체가 참조될 때마다 추가 행동을 제공한다.
- `캐싱 프록시(Caching Proxy)`: 비용이 많이 드는 작업의 결과를 임시로 저장해 준다. 여러 클라이언트에서 결과를 공유하게 해 줌으로써 계산 시간 또는 네트워크 지연을 줄여주는 효과도 있다.
- `동기화 프록시(Synchronization Proxy)`: 여러 스레드에서 주 객체에 접근하는 경우에 안전하게 작업을 처리할 수 있게 해준다.
- `복잡도 숨김 프록시(Complexity Hiding Proxy)`: 복잡한 클래스들의 집합에 대한 접근을 제어하고, 그 복잡도를 숨겨준다. `퍼사드 프록시(Facade Proxy)`라고 부르기도 한다.
  - `퍼사드 프록시`에서는 접근을 제어하지만 `퍼사트 패턴`에서는 대체 인터페이스만 제공한다는 점이 차이점이다.
- `지연 복사 프록시(Copy-On-Write Proxy)`: 클라이언트에서 필요로 할 때까지 객체가 복사되는 것을 지연시킴으로써 객체의 복사를 제어한다. (변형된 가상 프록시라고 할 수 있다.)

## 구현 및 테스트 코드

- https://github.com/PaengE/HeadFirst_DesignPatterns/tree/main/src/headfirst/designpatterns/proxy/javaproxy

