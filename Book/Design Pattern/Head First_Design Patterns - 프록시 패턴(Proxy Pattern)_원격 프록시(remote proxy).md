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

## 원격 프록시(Remote Proxy)

### 원격 프록시의 역할

- `원격 프록시`: 원격 객체에 대한 로컬 대변자 역할을 한다. 프록시의 메소드를 호출하면 그 호출이 네트워크를 통하여 전달되어 결국 원격 객체의 메소드가 호출된다. 그리고 그 결과는 다시 프록시를 거쳐 클라이언트에게 전달된다.
- `원격 객체(remote object)`: 다른 JVM의 Heap에서 살고 있는 객체(일반적으로 얘기하자면 다른 주소 공간에서 돌아가고 있는 원격 객체)이다.
- `로컬 대변자(local representative)`: 로컬 대변자의 어떤 메소드를 호출하면, 다른 원격 객체한테 그 메소드 호출을 전달해주는 역할을 맡고 있는 객체이다.

### 원격 메소드의 기초

![proxy-2](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\proxy-2.png)

- `클라이언트 객체(Client Object)`는 진짜 서비스의 메소드를 호출한다고 생각한다. 클라이언트 보조 객체에서 실제로 자신이 원하는 작업을 처리한다고 생각한다.
- `클라이언트 보조 객체(Client Helper)`는 진짜 서비스인 척 하지만, 실제로는 진짜 객체에 대한 `프록시(proxy)`이다.
- `서비스 보조 객체(Service Helper)`는 클라이언트 보조 객체로부터 요청을 받아 그 내용을 해석하여 진짜 서비스에 있는 메소드를 호출하낟.
- `서비스 객체(Service Object)`가 진짜 서비스로, 실제로 작업을 처리하는 메소드가 들어있다.

## 자바 RMI(Remote Method Invocation, 원격 메소드 호출)

> 기본적으로, 다른 힙에 위치한 객체에 대한 레퍼런스를 가져올 수는 없다. 어떤 변수가 어떤 객체를 참조하려면, 그 객체는 그 선언문이 들어있는 코드와 같은 힙 공간에 위치해야만 한다.
>
> 하지만 `RMI`를 이용하면 원격 JVM에 있는 객체를 찾아서 그 메소드를 호출할 수 있다.
>
> `RMI`은 `원격 메소드 기초`에 나와있는 그림과 같은 로직을 가진다.
>
> 하지만 `클라이언트 보조 객체`를 `스텁(Stub)`, `서비스 보조 객체`를 `스켈레톤(Skeleton)`이라고 부른다.

- RMI에서는 클라이언트 보조객체와 서비스 보조객체를 만들어 준다. 보조 객체에는 원격 서비스와 똑같은 메소드가 들어 있다.
- 또한 클라이언트에서 원격 객체를 찾아서 그 원격 객체에 접근하기 위해 쓸 수 있는 룩업(lookup) 서비스도 제공한다.

- 클라이언트 입장에서는 로컬 메소드 호출과 같은 방식으로 메소드를 호출하지만, 실제로는 클라이언트 보조객체에서 네트워크를 통해 호출을 전송할 수 있다.
- 따라서 네트워킹 및 입출력 기능이 필수적으로 사용되며, 이에 대한 주의가 필요하다.

## 원격 서비스 만들기

> 여기서는 아래 5단계를 통하여 일반 객체를 원격 클라이언트로부터 들어온 메소드 호출을 받아서 처리할 수 있는 원격 서비스로 개조한다.

### 1단계: 원격 인터페이스 만들기

원격 인터페이스에서는 클라이언트에서 원격으로 호출할 수 있는 메소드를 정의한다.

클라이언트에서 이 인터페이스를 서비스의 클래스 형식으로 사용하며, 스텁과 실제 서비스에서는 모두 이 인터페이스를 구현해야 한다.

ex ) `MyService.java`

1. `java.rmi.Remote`를 확장한다.

   - `Remote`는 아무 메소드도 없는 '표식용(marker)' 인터페이스이다. 하지만 RMI에서 특별한 의미를 가지기 때문에 반드시 이 규칙을 따라야 한다.

   ```java
   public interface MyRemote extends Remote {...}
   ```

2. 모든 메소드를 `RemoteException`을 던지는 메소드로 선언한다.

   - 클라이언트에서는 서비스의 형식을 원격 인터페이스의 형식으로 선언해서 사용한다. 즉, 클라이언트에서는 원격 인터페이스를 구현하는 `스텁`에 대해서 메소드를 호출하게 된다.
   - 하지만 스텁에서는 네트워킹 및 각종 입출력 작업을 처리해야 하므로 반드시 예외처리를 해주어야 한다. 인터페이스를 정의할 때 모든 메소드에서 예외를 선언하면, 그 인터페이스 형식의 레퍼런스에 대해서 메소드를 호출하는 코드에서는 반드시 그 예외를 처리하거나 선언해야 한다.

   ```java
   import java.rmi.*;
   
   public interface MyRemote extends Remote {
     public String sayHello() throws RemoteException;
   }
   ```

3. 인자와 리턴값은 반드시 primitive type이거나 Serializable 형식으로 선언한다.

   - 원격 메소드의 인자와 리턴값은 모두 네트워크를 통해 전달되어야 하며, 직렬화를 통해 포장된다.
   - 만약 custom type을 전달한다면, 클래스를 만들 때 Serializable 인터페이스를 구현해야 한다.

### 2단계: 서비스 구현 클래스 만들기

원격 인터페이스에서 정의한 원격 메소드를 실제로 구현한 코득다 들어있는, 실제 작업을 처리하는 클래스이다.

클라이언트에서 이 객체에 있는 메소드를 호출하게 된다.

ex ) `MyServiceImpl.java` -> 이 포스팅에서는 GumballMachine 클래스 같은

1. 원격 인터페이스를 구현한다.

   - 서비스 클래스에서는 반드시 원격 인터페이스를 구현해야한다.

   ```java
   public class MyRemoteImpl implements MyRemote{
     public String sayHello(){
       return "Server says, 'Hey'";
     }
     // ...
   }
   ```

2. UnicastRemoteObject를 확장한다.

   - 원격 서비스 객체 역할을 하려면 객체에 '원격 객체가 되기 위한' 기능을 추가해야 한다.
   - 가장 간단한 방법은 (java.rmi.server 패키지에 들어있는) UnicastRemoteObject를 확장해서 그 슈퍼클래스에서 제공하는 기능을 활용하는 방법이다.

   ```java
   public class MyRemoteImpl extends UnicastRemoteObject implements MyRemote{
     public String sayHello(){
       return "Server says, 'Hey'";
     }
     // ...
   }
   ```

3. RemoteException을 선언하는 인자가 없는 생성자 만들기

   - 슈퍼클래스인 UnicastRemoteObject는 생성자에서 RemoteException 을 던진다는 문제가 있다.
   - 이 문제를 해결하려면 서비스를 구현하는 클래스에서도 RemoteException 을 선언하는 생성자를 만들어야 한다.
   - 어떤 클래스가 생성될 때 그 슈퍼클래스의 생성자도 반드시 호출되기 때문에, 슈퍼클래스 생성자에서 어떤 예외를 던진다면, 서브클래스의 생성자에서도 그 예외를 선언해야 된다.

   ```java
   public MyRemoteImpl() throws RemoteException {}
   ```

4. 서비스를 RMI 레지스트리에 등록

   - 원격 서비스가 완성되고 나면 원격 클라이언트에서 쓸 수 있게 해줘야 한다.
   - 인스턴스를 만든 다음 RMI 레지스트리에 등록해주면 된다. (이 클래스가 실행될 때 RMI 레지스트리가 작동 중에 있어야 한다.)
   - 서비스를 구현한 객체를 등록하면 RMI 시스템에서는 클라이언트가 필요한 스텁만 레지스트리에 등록한다.
   - 서비스를 등록할 때는 java.rmi.Naming 클래스에 있는 rebind() 정적메소드를 사용하면 된다.

   ```java
   try {
   	MyRemote service = new MyRemoteImpl();
     Naming.rebind("RemoteHello", service);
   } catch (Exception e) { ... }
   ```

### 3단계: rmic를 이용하여 스텁과 스켈레톤 만들기

클라이언트 및 서비스 '보조객체'를 생성한다.

1. 서비스를 구현한 클래스에 대해서 rmic를 작동시킨다.

   - JDK에 포함되어 있는 rmic 툴은 서비스를 구현한 클래스를 받아서 `스텁`과 `스켈레톤` 두개의 새로운 클래스를 만들어 준다. `스텁`과 `스켈레톤` 클래스에는 원래 클래스의 이름에 각각 `_Stub`과 `_Skel`이 추가된 이름이 붙는다.
   - 새로 만들어지는 클래스는 현재 디렉토리에 저장된다. (패키지 디렉토리 구조와 전체 이름을 고려해야 한다.)

   ```shell
   rmic MyRemoteImpl
   ```

### 4단계: RMI 레지스트리(rmiregistry) 실행

클라이언트에서 rmiregistry 명령어를 실행함으로 인해, 이 레지스트리로부터 프록시(클라이언트 스텁)를 받아갈 수 있다.

1. 터미널을 새로 띄워서 rmiregistry 명령어를 실행시킨다.

   - 클래스에 접근할 수 있는 디렉토리에서 실행시켜야 한다.

   ```shell
   rmiregistry
   ```

### 5단계: 원격 서비스 시작

서비스 객체를 가동시킨다. (java MyServiceImpl)

서비스를 구현한 클래스에서 서비스의 인스턴스를 만들고, 그 인스턴스를 RMI 레지스트리에 등록한다.

1. 다른 터미널을 열고 서비스를 시작한다.

   - 이 작업은 원격 서비스를 구현한 클래스의 main() 메소드를 통해서 실행시킬 수도 있지만, 별도의 클래스로부터 할 수 도 있다.

   ```shell
   java MyRemoteImpl
   ```

### 서버 쪽에서 필요한 코드

- 원격 인터페이스

  ```java
  import java.rmi.*;
  
  public interface MyRemote extends Remote {
    public String sayHello() throws RemoteException;
  }
  ```

- 원격 서비스를 구현한 클래스

  ```java
  import java.rmi.*;
  import java.rmi.server.*;
  
  public class MyRemoteImpl extends UnicastRemoteObject implements MyRemote{
    public String sayHello(){
      return "Server says, 'Hey'";
    }
    
    public MyRemoteImpl() throws RemoteException{}
    
    public static void main (String[] args){
      try {
        MyRemote service = new MyRemoteImpl();
        Naming.rebind("RemoteHello", service);
      } catch (Exception e) { ... }
    }
  }
  ```


> 클라이언트에서는 `스텁 객체(프록시)`를 가져와야 한다. (그 곳에 있는 메소드를 호출하기 위함)
>
> 이 때 RMI 레지스트리를 사용하여, 클라이언트에서는 룩업(lookup)을 통해 스텁 객체를 요청한다.
>
> 작동 방식은 다음과 같다.

![proxy-3](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\proxy-3.png)

1. 클라이언트에서 RMI 레지스트리를 룩업한다.
   - Naming.lookup("rmi://127.0.0.1/RemoteHello");
2. RMI 레지스트리에서 스텁 객체를 리턴한다.
   - 스텁 객체는 lookup() 메소드의 리턴값으로 전달되며, RMI에서는 그 스텁을 자동으로 역직렬화한다.
   - 이 때 (rmic에서 생성해 준) 스텁 클래스가 반드시 클라이언트 쪽에 있어야만 한다.
3. 클라이언트에서 스텁에 대해 메소드를 호출한다.

### 클라이언트 코드

```java
import java.rmi.*;

public class MyRemoteClient{
  public static void main(String[] args){
    new MyRemoteClient().go();
  }
  
  public void go(){
    try{
      MyRemote service = (MyRemote) Naming.lookup("rmi://127.0.0.1/RemoteHello");
      
      String s = service.sayHello();
      System.out.println(s);
    } catch(Exception e){
      e.printStackTrace();
    }
  }
}
```

- 레지스트리에서 리턴된 객체는 Object 타입이기 때문에 반드시 캐스팅을 해야만 한다.
- IP 주소 또는 호스트 이름이 필요하며, 서비스를 결합/재결합할 때 지정해준 이름도 필요하다.

#### *RMI 사용 시 주의할 점

1. 원격 서비스를 작동시킨 후, rmiregistry를 실행시켜야 한다. (Naming.rebind()를 호출해서 서비스를 등록하는 시점에서 RMI 레지스트리가 돌아가고 있어야 한다.)
2. 인자와 리턴 형식이 직렬화 가능하여야 한다. (컴파일러에서는 캐치할 수 없다.)
3. 클라이언트에 스텁 클래스를 건네줘야 한다.

## 뽑기 기계의 원격 모니터링

> 이전 포스팅인 `스테이트(상태) 패턴`에서 사용된 뽑기 기계에 원격 모니터링 기능을 추가하고 싶다.

뽑기 기계가 일종의 서비스 역하을 맡고, 네트워크를 통해 들어오는 요청을 수용할 수 있도록 만들자.

그리고 모니터링용 클래스에 프록시 객체에 대한 레퍼런스를 받아 올 수 있는 기능을 추가하여 데이터를 주고받자.

`자바 RMI`를 활용하여 원격 서비스와 데이터를 주고 받을 수 있는 프록시를 만들 것이다.

### GumballMachine을 원격서비스로 개조하는 방법

> 원격 프록시를 쓸 수 있도록 코드를 개조할 때 가장 먼저 할 일은, 해당 프로그램이 클라이언트로부터 전달된 원격 요청에 대한 서비스를 제공할 수 있는 방식으로 수정되어야 한다.
>
> 즉, 서비스를 구현한 클래스로 만들어야 한다. 방법은 다음과 같다.

1. GumballMachine용 원격 인터페이스를 만든다. 이 인터페이스에서는 원격 클라이언트에서 호출할 수 있는 메소드들을 정의해야 한다.

   ```java
   import java.rmi.*;
    
   public interface GumballMachineRemote extends Remote {
   	public int getCount() throws RemoteException;
   	public String getLocation() throws RemoteException;
   	public State getState() throws RemoteException;
   }
   ```

   - 모든 리턴 타입은 primitive 혹은 Serializable 하여야 한다.
   - 지원해야 하는 메소드 모두 RemoteException을 던질 수 있다.

2. 인터페이스의 모든 리턴 타입이 직렬화할 수 있는 형식인지 확인한다.

   ```java
   import java.io.*;
     
   public interface State extends Serializable {
   	public void insertQuarter();
   	public void ejectQuarter();
   	public void turnCrank();
   	public void dispense();
   }
   ```

   - (아무 메소드도 없는) Serializable 인터페이스를 확장함으로써, State의 서브클래스를 직렬화할 수 있다.(즉, 네트워크를 통해 전송할 수 있다.)
   - 부분적으로 직렬화하고 싶지 않은 필드는 `transient` 키워드를 추가해주면 된다.

   ```java
   public class NoQuarterState implements State {
     transient GumballMachine gumballMachine;
     
     //...
   }
   ```

3. 구상 클래스에서 인터페이스를 구현한다.

   ```java
   import java.rmi.*;
   import java.rmi.server.*;
    
   public class GumballMachine extends UnicastRemoteObject implements GumballMachineRemote {
   	public GumballMachine(String location, int numberGumballs) throws RemoteException {
       // ...
     }
   }
   ```

   - 원격 서비스 역할을 할 수 있도록 UnicastRemoteObject의 서브클래스로 만들고, 원격 인터페이스를 구현한다.
   - 슈퍼클래스에서 RemoteException을 던질 수 있기 때문에 이 생성자에서도 RemoteException을 던질 수 있도록 한다.

### RMI 레지스트리 등록

> 클라이언트에서 찾을 수 있도록 RMI 레지스트리에 등록해야 한다.

```java
try {
	count = Integer.parseInt(args[1]);

	gumballMachine = new GumballMachine(args[0], count);
	Naming.rebind("//" + args[0] + "/gumballmachine", gumballMachine);
} catch (Exception e) {
	e.printStackTrace();
}
```

- GumballMachine의 인스턴스를 만드는 부분은 생성자에서 예외를 던질 수 도 있으므로 try/catch로 감싸야한다.
- Naming.rebind() 메소드를 통하여 GumballMachine 스텁을 gumballmachine이라는 이름으로 등록한다.

### GumballMonitor 클라이언트 수정하기

```java
import java.rmi.*;
 
public class GumballMonitor {
	GumballMachineRemote machine;
 
	public GumballMonitor(GumballMachineRemote machine) {
		this.machine = machine;
	}
 
	public void report() {
		try {
			System.out.println("Gumball Machine: " + machine.getLocation());
			System.out.println("Current inventory: " + machine.getCount() + " gumballs");
			System.out.println("Current state: " + machine.getState());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
```

- GumballMachine 구상 클래스 대신 원격 인터페이스를 사용한다.
- 메소드를 네트워크를 통해서 호출해야 하므로, RemoteException이 던져지는 경우를 대비한다.

### 모니터링 클래스용 테스트 코드

```java
import java.rmi.*;
 
public class GumballMonitorTestDrive {
	public static void main(String[] args) {
    // 모니터링할 위치를 배열 형태로 저장
		String[] location = {"rmi://santafe.mightygumball.com/gumballmachine",
		                     "rmi://boulder.mightygumball.com/gumballmachine",
		                     "rmi://seattle.mightygumball.com/gumballmachine"}; 
		GumballMonitor[] monitor = new GumballMonitor[location.length];
		
		for (int i=0;i < location.length; i++) {
			try {
        GumballMachineRemote machine = (GumballMachineRemote) Naming.lookup(location[i]);
        monitor[i] = new GumballMonitor(machine);
        System.out.println(monitor[i]);
      } catch (Exception e) {
        e.printStackTrace();
      }
		}
 
    // 각 기계로부터 보고서를 받음
		for(int i=0; i < monitor.length; i++) {
			monitor[i].report();
		}
	}
}
```

- 13 line은 원격 뽑기 기계 객체에 대한 프록시를 리턴한다.(찾을 수 없다면 예외를 던짐)
  - Naming.lookup() 메소드는 RMI 패키지에 있는 정적 메소드로, 위치와 서비스 이름을 받아서 그 위치에 있는 RMI 레지스트리에 대해서 룩업 작업을 수행한다.
- 14 line은 뽑기 기게의 프록시를 가져오고 난 뒤 GumballMonitor를 생성한다. 이 때 생성자에 모니터링할 뽑기 기계의 프록시를 넘겨준다.

## 구현 및 테스트 코드

- https://github.com/PaengE/HeadFirst_DesignPatterns/tree/main/src/headfirst/designpatterns/proxy/gumball

