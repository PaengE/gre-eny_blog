## 디자인 패턴: 스트래티지 패턴(Strategy Pattern)

> 이 포스팅은 `Head First: Design Patterns` 책을 보고, 개인적으로 정리한 포스팅입니다.

## Strategy Pattern 이란?

> `스트래티지 패턴(Strategy Pattern)`에서는 알고리즘군을 정의하고 각각을 캡슐화하여 교환해서 사용할 수 있도록 만든다.
> 스트래티지 패턴을 활용하면 알고리즘을 사용하는 클라이언트와는 독립적으로 알고리즘을 변경할 수 있다.

## 간단한 SimUDuck 애플리케이션

> 이 애플리케이션에서는 헤엄을 치거나, 꽥꽥거리는 소리도 내는 매우 다양한 오리 종류를 보여준다.
>
> 처음 이 시스템을 디자인한 사람들은 표준적인 객체지향 기법을 사용하여 Duck이라는 슈퍼클래스를 만든 다음, 그 클래스를 확장하여 다른 모든 종류의 오리를 만들었다.

모든 오리들이 꽥꽥 소리를 낼 수 있고, 헤엄을 칠 수 있기 때문에 super class 에서 quack() 과 swim() 을 간단한 코드로 구현해 놓았고, 각 오리별로 생김새가 다르기 때문에 display() 는 추상 메소드로 구현해 놓았다.

![strategy-1](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\strategy-1.png)

- 하지만, 시간이 지남에 따라 날아다니는 오리도 처리해야하며, 그로 인해 날 수 있는 오리와 날 수 없는 오리가 구분되었고, 또한 '삑삑'소리를 낸다거나 소리를 내지 못하는 오리들도 생겼다.
- 따라서 클래스 디자인을 변경해야한다.

> 여기서는 스트래티지 패턴(Strategy Pattern) 을 이용하여 애플리케이션을 수정해 나간다.

## 상속(Inheritance)을 사용하면 어떨까

> 기존의 클래스 디자인에서 display() 메소드처럼 추상메소드로 정의하고 각 서브 클래스에서 재정의(override)하여 사용할 수 있는 방법이 있다.

상속을 이용한 개선은 지속적인 업데이트가 있다는 가정 하에서 다음과 같은 문제점을 가진다.

1. 서브클래스에서 코드가 중복된다.
2. 실행시에 특징을 바꾸기 힘들다.
3. 모든 오리의 행동을 알기 힘들다.
4. 코드를 변경했을 때 다른 오리들한테 원치 않은 영향을 끼칠 수 있다.

## 인터페이스(Interface)는 어떨까

> 슈퍼클래스의 fly() 와 quack() 을 빼내어 따로 각각 Flyable, Quackable 인터페이스를 만들어 관리하는 방법이 있다.

![strategy-2](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\strategy-2.png)

인터페이스를 이용한 개선은 일부 문제점(RubberDuck이 날아다니는 것과 같이 이상한 일이 일어나지 않도록)을 해결해주어 꽤 괜찮아 보일 수는 있지만, 그러한 행동에 대한 `코드 재사용`을 전혀 기대할 수 없게 되므로(Flyable이나 Quackable 인터페이스를 상속받는 서브클래스에서 fly()나 quack()을 다시 override 해줘야 하므로) `코드 관리` 면에 있어서 다른 커다란 문제점을 낳게 된다.

## 문제를 명확하게 파악하기

상속과 Flyable, Quackable 인터페이스를 사용하는 것은 성공적이지 못하다는 것을 알았다.

이런 상황에 적용할 만한 `디자인 원칙`을 생각해보면, 어울릴만한 디자인 원칙이 있다.

> [ 디자인 원칙 - 1 ]
>
> - 애플리케이션에서 달라지는 부분을 찾아내고, 달라지는 부분으로부터 분리시킨다.
>
> 위의 원칙은 다음과 같은 식으로 생각할 수도 있다.
>
> - 바뀌는 부분은 따로 뽑아서 캡슐화시킨다. 그렇게 하면 나중에 바뀌지 않는 부분에는 영향을 미치지 않은 채로 그 부분만 고치거나 확장할 수 있다.

이 SimUDuck 애플리케이션에서 바뀌는 부분은 Duck 클래스에서 fly() 와 quack() 이다. 그러나 fly() 와 quack() 을 제외하면 Duck 클래스는 잘 작동하고 있으며, 나머지 부분은 자주 달라지거나 바꾸지 않는다. 그러므로 Duck 클래스는 그대로 두는게 좋다.

이러한 행동(fly() 와 quack())을 Duck 클래스로부터 갈라내기 위해 그 두 메소드를 모두 Duck 클래스로부터 끄집어내어 각 행동을 나타낼 `클래스 집합`을 새로 만들도록 한다.

## 오리의 행동 디자인

우리는 `나는 행동 fly()`와 `꽥꽥거리는 행동 quack()`이 달라지는 부분임을 인지하고, `클래스 집합`으로 분리하여 캡슐화하려고 한다. 

하지만 어떻게 `클래스 집합`을 디자인해야 할까? 다음과 같은 디자인 원칙을 살펴보자.

> [ 디자인 원칙 - 2 ]
>
> - 구현이 아닌 인터페이스에 맞춰서 프로그래밍한다.

각 행동은 인터페이스(FlyBehavior, QuackBehavior)로 표현하고 행동을 구현할 때 이런 인터페이스를 구현하도록 하겠다.

나는 행동과 꽥꽥거리는 행동은 이제 Duck 클래스에서 구현하지 않고, 대신 특정 행동("삑삑 소리 내기"와 같은 행동)만을 목적으로 하는 클래스의 집합을 만들도록 하겠다. 행동(behavior) 인터페이스는 Duck 클래스가 아닌, 방금  설명한 행동 클래스에서 구현한다. 그렇게 하면, Duck 클래스에서는 그 행동을 구체적으로 구현하는 방법에 대해서는 더 이상 알고 있을 필요가 없다.

![strategy-3](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\strategy-3.png)

위와 같이 디자인하면 다른 형식의 객체에서도 나는 행동과 꽥꽥거리는 행동을 재사용할 수 있다. 그런 행동이 더이상 Duck 클래스 안에 숨겨져 있지 않기 때문이다. (오리 클래스가 아닌 클래스에서 Quack 행동을 사용한다던지)

그리고 기존의 행동클래스를 수정하거나 날아다니는 행동을 사용하는 Duck 클래스를 전혀 건드리지 않고도 새로운 행동을 추가할 수 있다. (로켓 추진으로 나는 기능을 추가해야 한다던지)

## Duck 행동 통합하기

> 가장 중요한 점은 이제 Duck에서 나는 행동과 꽥꽥 소리를 내는 행동을 Duck 클래스(또는 그 서브클래스)에서 정의한 메소드를 써서 구현하지 않고, 다른 클래스(FlyBehavior, QuackBehavior)에 위임한다는 것이다.

1. 우선 Duck 클래스에 flyBehavior와 quackBehavior라는 `두 개의 인터페이스 형식의 인스턴스 변수를 추가`한다. (특정 구상 클래스 형식으로 선언하지 않는다.)

   - 각 오리 객체에서는 실행 시에 이 변수에 특정 행동 형식(FlyWithWings, Squeak 등)에 대한 레퍼런스를 다형적으로 설정한다.
   - 나는 행동과 꽥꽥거리는 행동은 FlyBehavior와 QuackBehavior 인터페이스로 옮겨놨기 때문에 Duck 클래스 및 모든 서브 클래스에서 fly()와 quack() 메소드를 제거하고, 대신에 performFly() 와 performQuack()이라는 메소드를 집어넣는다.

   ![strategy-4](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\strategy-4.png)

2. performQuack(), performFly() 구현

   - ```java
     public abstract class Duck {
       // 모든 Duck에는 QuackBehavior와 FlyBehavior 인터페이스를
     	// 구현하는 것에 대한 레퍼런스가 있다.
     	FlyBehavior flyBehavior;
     	QuackBehavior quackBehavior;
     
       // 기타코드..
       
       // 행동을 직접 처리하는 대신, Behavior로 참조되는 객체에 그 행동을 위임한다.
     	public void performFly() {
     		flyBehavior.fly();
     	}
     	public void performQuack() {
     		quackBehavior.quack();
     	}
     }
     ```

3. flyBehavior와 quackBehavior 인스턴스 변수를 설정하는 법

   - ```java
     public class MallardDuck extends Duck {
     
     	public MallardDuck() {
     		quackBehavior = new Quack();
     		flyBehavior = new FlyWithWings();
     	}
     
     	public void display() {
     		System.out.println("I'm a real Mallard duck");
     	}
     }
     ```

     위의 코드처럼 생성자에서 해당 오리의 행동을 지정해준다.(여기서 이 MallardDuck은 꽥꽥거리며, 날개를 이용하여 나는 오리이다.)

> 여기서는 앞에 있는 생성자에서 Quack이라는, 구현되어 있는 구상클래스의 인스턴스를 만듦으로써 `특정 구현에 맞춘 프로그래밍`을 하였습니다.
>
> 보통의 경우 `특정 구현에 맞춘 프로그래밍`은 좋지 못한 방법이다. 하지만 여기에서도 보면, 행동을 구상 클래스로 설정하고 있긴 하지만, 실행시에 쉽게 변경할 수가 있기 때문에, 상당히 유연하다고 할 수 있다.

## 동적으로 행동을 지정하는 방법

> 오리의 행동 형식을 생성자에서 인스턴스를 만드는 방법이 아닌 Duck의 서브클래스에서 Setter 메소드를 호출하는 방법으로 설정할 수 있게한다.

Duck 클래스에 메소드 두개를 새로 추가한다.

- ```java
  public void setFlyBehavior(FlyBehavior fb) {
    flyBehavior = fb;
  }
  
  public void setQuackBehavior(QuackBehavior qb) {
    quackBehavior = qb;
  }
  ```

- 오리의 행동을 즉석에서 바꾸고 싶으면 이 두 메소드를 호출하여 바꿀 수 있다.

## 새로 구성한 SimUDuck 클래스 구조

> 오리들은 모두 Duck을 확장해서 만들고, 나는 행동은 FlyBehavior를, 꽥꽥 소리를 내는 행동은 QuackBehavior를 구현해서 만들었다.![strategy-5](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\strategy-5.png)

## "A는 B이다"보다 "A에는 B가 있다"가 나을 수 있다.

> "A에는 B가 있다" 관계에 대해, 각 오리에는 FlyBehavior와 QuackBehavior가 있으며, 각각 행동과 꽥꽥거리는 행동을 위임 받는다.
>
> 두 클래스를 이런식으로 합치는 것을 구성(Composition)을 이용하는 것이라 부르며, 여기에 나와있는 오리클래스에서는 행동을 상속받는 대신, 올바른 행동 객체로 구성됨으로써 행동을 부여받게 된다.

이 테크닉은 매우 중요한 테크닉이며, 세 번째 디자인 원칙이기도 하다.

> [ 디자인 원칙 - 3 ]
>
> - 상속보다는 구성을 활용한다.
>
> 구성을 이용하여 시스템을 만들면 유연성을 크게 향상시킬 수 있다.
>
> 단순히 알고리즘군을 별도의 클래스 집합으로 캡슐화할 수 있도록 만들어주는 것 뿐 아니라, 구성요소로 사용하는 객체에서 올바른 행동 인터페이스를 구현하기만 하면 실행시에 행동을 바꿀 수도 있게 해준다.

## 구현 및 테스트 코드

- https://github.com/PaengE/HeadFirst_DesignPatterns/tree/main/src/headfirst/designpatterns/strategy