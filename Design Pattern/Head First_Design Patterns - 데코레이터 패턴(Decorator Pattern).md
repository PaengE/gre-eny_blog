## 디자인 패턴: 데코레이터 패턴(Decorator Pattern)

> 이 포스팅은 `Head First: Design Patterns` 책을 보고, 개인적으로 정리한 포스팅입니다.

## Decorator Pattern 이란?

> `데코레이터 패턴(Decorator Pattern)`에서는 객체에 추가적인 요건을 동적으로 첨가한다. 
>
> `데코레이터`는 서브클래스를 만드는 것을 통해서 기능을 유연하게 확장할 수 있는 방법을 제공한다.
>
> - 데코레이터의 슈퍼클래스는 자신이 장식하고 있는 객체의 슈퍼클래스와 같다.
> - 한 객체를 여러 개의 데코레이터로 감쌀 수 있다.
> - 데코레이터는 자신이 감싸고 있는 객체와 같은 슈퍼클래스를 가지고 있기 때문에 원래 객체(싸여져 있는 객체)가 들어갈 자리에 데코레이터 객체를 집어넣어도 상관없다.
> - <u>데코레이터는 자신이 장식하고 있는 객체에서 어떤 행동을 위임하는 것 외에 원하는 추가적인 작업을 수행할 수 있다.</u>
> - 객체는 언제든지 감쌀 수 있기 때문에 실행중에 필요한 데코레이터를 마음대로 적용할 수 있다.

![decorator-1](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\decorator-1.png)

위의 다이어그램으로 예시를 들겠다. 각 구성요소는 직접 쓰일 수도 있고 데코레이터로 감싸져서 쓰일 수도 있다.

- ConcreteComponent에 새로운 행동을 동적으로 추가하게 된다.
- 각 데코레이터 안에는 Component 객체가 들어있다. 즉, 데코레이터에는 구성요소에 대한 레퍼런스가 들어있는 인스턴스 변수가 있다.
- Decorator는 자신이 장식할 구성요소와 같은 인터페이스 또는 추상 클래스를 구현한다.
- Decorator는 Component의 상태를 확장할 수 있다.
- ConcreteDecorator에는 그 객체가 장식하고 있는 것(데코레이터가 감싸고 있는 Component 객체)을 위한 인스턴스 변수가 있다.
- 데코레이터에서 새로운 메소드를 추가할 수도 있다. 하지만 일반적으로 새로운 메소드를 추가하는 대신 Component에 원래 있던 메소드를 호출하기 전, 또는 후에 별도의 작업을 처리하는 방식으로 새로운 기능을 추가한다.

> [ 디자인 원칙 - 5 ]
>
> - OCP(Open-Closed Principle): 클래스는 확장에 대해서는 열려 있어야 하지만, 코드 변경에 대해서는 닫혀 있어야 한다.
>
> 기존 코드는 건드리지 않은 채로 확장을 통해서 새로운 행동을 간단하게 추가할 수 있도록 하는게 목표이며,
>
> 이 목표를 달성함으로써 새로운 기능을 추가하는 데에 있어 매우 유연하게 급변하는 주변 환경에 잘 적응할 수 있으면서도 강하고 튼튼한 디자인을 만드는 효과를 낼 수 있다.

## 스타버즈 커피의 초기 판매 시스템

> 처음에는 Beverage 라는 추상 클래스 밑에, 음료마다 각 서브클래스를 만들어 관리하는 구조였다.
>
> 이 방법은 음료가 추가될 때마다 서브클래스를 하나씩 만들어야 하므로 클래스의 개수가 비약적으로 증가해버린다.
>
> 그래서 인스턴스 변수와 슈퍼클래스 상속을 이용하여 추가 사항을 관리하도록 했다. 아래의 다이어그램처럼.

![decorator-2](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\decorator-2.png)

- 각 추가 요소에 해당하는 boolean 변수를 새로 만들었다.
- 이제 cost()를 추상 클래스로 하지 않고, 구현해 놓기로 하였다. 그래도 서브클래스에서는 cost()를 오버라이드 해야 한다.
- 슈퍼클래스에 있는 cost()에서는 추가된 각 항목의 가격을 계산하고, 서브클래스에서 cost() 메소드를 오버라이드 할 때에는 그 기능을 확장하여 특정 음료 형식의 가격을 더한다.
- 서브클래스의 각 cost() 메소드에서는 일단 음료의 가격을 계산한 다음 슈퍼클래스에서 구현한 cost()를 호출하여 첨가된 항목에 따른 추가 가격을 더한다.

> 현재는 5개의 클래스만을 사용하여 시스템을 운영하고 있지만, 이 접근법도 다음과 같은 문제점이 존재한다. 
>
> - 첨가물 가격이 바뀔 때마다 기존 코드를 수정해야 한다.
> - 첨가물의 종류가 많아지면 새로운 메소드를 추가해야 하고, 슈퍼클래스의 cost() 메소드도 고쳐야 한다.
> - 특정 첨가물이 들어가면 안되는 경우도 있을 것이고, 특정 첨가물이 중복으로 들어갈 경우도 있을 것이다.
>
> 따라서 `데코레이터 패턴`을 이용한 개선을 해보자.

![decorator-3](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\decorator-3.png)

- Beverage는 앞에 나왔던 구성요소를 나타내는 Component 추상 클래스 같은 개념이다.
- 커피 종류마다 구성요소를 나타내는 구상 클래스를 하나씩 만든다. (HoustBlend, DarkRoast, Decaf, Espresso)
- 각각의 첨가물을 나타내는 데코레이터.(Milk, Mocha, Soy, Whip)

## 추상 구성요소Beverage, 추상 데코레이터CondimentDecorator 추상클래스 구현

```java
public abstract class Beverage {
	String description = "Unknown Beverage";
  
	public String getDescription() {
		return description;
	}
 
	public abstract double cost();
}
```

```java
public abstract class CondimentDecorator extends Beverage {
	public abstract String getDescription();
}
```

- Beverage 객체가 들어갈 자리에 들어갈 수 있어야 하므로 Beverage 클래스를 확장한다.
- 모든 첨가물 데코레이터에서 getDescripiton() 메소드를 새로 구현하도록 하기 위해 추상메소드로 선언한다.

## 구성요소 구상클래스 구현

```java
public class HouseBlend extends Beverage {
	public HouseBlend() {
		description = "House Blend Coffee";
	}
 
	public double cost() {
		return .89;
	}
}
```

```java
public class DarkRoast extends Beverage {
	public DarkRoast() {
		description = "Dark Roast Coffee";
	}
 
	public double cost() {
		return .99;
	}
}
```

```java
public class Decaf extends Beverage {
	public Decaf() {
		description = "Decaf Coffee";
	}
 
	public double cost() {
		return 1.05;
	}
}
```

```java
public class Espresso extends Beverage {
	public Espresso() {
		description = "Espresso";
	}
  
	public double cost() {
		return 1.99;
	}
}
```

## 데코레이터 구상클래스 구현

```java
public class Milk extends CondimentDecorator {
	Beverage beverage;

	public Milk(Beverage beverage) {
		this.beverage = beverage;
	}

	public String getDescription() {
		return beverage.getDescription() + ", Milk";
	}

	public double cost() {
		return .10 + beverage.cost();
	}
}
```

```java
public class Mocha extends CondimentDecorator {
	Beverage beverage;
 
	public Mocha(Beverage beverage) {
		this.beverage = beverage;
	}
 
	public String getDescription() {
		return beverage.getDescription() + ", Mocha";
	}
 
	public double cost() {
		return .20 + beverage.cost();
	}
}
```

```java
public class Soy extends CondimentDecorator {
	Beverage beverage;

	public Soy(Beverage beverage) {
		this.beverage = beverage;
	}

	public String getDescription() {
		return beverage.getDescription() + ", Soy";
	}

	public double cost() {
		return .15 + beverage.cost();
	}
}
```

```java
public class Whip extends CondimentDecorator {
	Beverage beverage;
 
	public Whip(Beverage beverage) {
		this.beverage = beverage;
	}
 
	public String getDescription() {
		return beverage.getDescription() + ", Whip";
	}
 
	public double cost() {
		return .10 + beverage.cost();
	}
}
```

데코레이터는 다음과 같이 두가지가 필요하다.

- 감싸고자 하는 음료를 저장하기 위한 인스턴스
- 인스턴스 변수를 감싸고자 하는 객체로 설정하기 위한 생성자. (데코레이터의 생성자에 감싸고자 하는 음료 객체를 전달하는 방식을 사용)

## 커피 주문 테스트

```java
public class StarbuzzCoffee {
 
	public static void main(String args[]) {
		Beverage beverage = new Espresso();
		System.out.println(beverage.getDescription() 
				+ " $" + beverage.cost());
 
		Beverage beverage2 = new DarkRoast();
		beverage2 = new Mocha(beverage2);
		beverage2 = new Mocha(beverage2);
		beverage2 = new Whip(beverage2);
		System.out.println(beverage2.getDescription() 
				+ " $" + beverage2.cost());
 
		Beverage beverage3 = new HouseBlend();
		beverage3 = new Soy(beverage3);
		beverage3 = new Mocha(beverage3);
		beverage3 = new Whip(beverage3);
		System.out.println(beverage3.getDescription() 
				+ " $" + beverage3.cost());
	}
}

/* 출력
Espresso $1.99
Dark Roast Coffee, Mocha, Mocha, Whip $1.49
House Blend Coffee, Soy, Mocha, Whip $1.34
*/
```

## 음료에 새로운 특성이 생긴다면?

> 스타버즈에서 사이즈 개념을 도입하기로 했다. 이제 커피를 Tall, Grande, Venti 사이즈 중에서 골라서 주문할 수 있다. 스타버즈에서는 커피 클래스 전체에 영향을 미칠 것으로 간주하고 Beverage 클래스에 setSize()와 getSize()라는 두개의 메소드를 추가했다. 그리고 사이즈에 따라서 첨가물 가격도 다르게 받을 계획이다. 예를 들어, 두유 가격을 Tall에서는 10센트, Grande에서는 15센트, Venti에서는 20센트로 책정하기로 했다.
>
> 이런 변경 사항을 처리하려면 데코레이터 클래스를 어떤 식으로 고쳐야 할까?

1. 일단, Beverage 클래스에 size를 저장하는 인스턴스 변수를 만들고, getter/setter 메소드를 구현한다.
2. 첨가물이 해당 음료의 사이즈를 알아야하기 때문에, CondimentDecorator에 Beverage 인스턴스와 getSize() 메소드를 추가한다.
3. 각  첨가물(데코레이터)에서 size에 따른 cost를 처리하는 로직을 구성한다.

## 데코레이터가 적용된 예: 자바 I/O

![decorator-4](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\decorator-4.png)

BufferedInputStream과 LineNumberInputStream은 모두 FilterInputStream을 확장한 클래스이다.

FilterInputStream은 추상 데코레이터 클래스의 역할을 한다.

- LineNumberInputStream은 구상 데코레이터이다. 데이터를 읽을 때 행번호를 붙여주는 기능을 추가해준다.
- BufferedInputStream도 구상 데코레이터이다. 속도를 향상시키기 위해 입력된 내용을 버퍼에 저장한다. 그리고 문자로 입력된 내용을 한번에 한줄씩 읽어들이기 위한 readLine() 메소드가 들어있는 인터페이스를 구현한다.
- FileInputStream이 바로 데코레이터로 포장될 구성요소이다. 자바 I/O에서는 FileInputStream, StringBufferInputStream, ByteArrayInputStream과 같은 구성요소를 제공한다. 이것들은 모두 데이터를 읽어들일 수 있게 해주는 기본 구성요소 역할을 한다.

## java.io 클래스와 데코레이터 패턴

![decorator-5](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\decorator-5.png)

- 첫번째 레벨에 있는 InputStream은 추상 구성요소이다.
- 두번째 레벨에 있는 4개의 추상클래스들은 추상 데코레이터이다.
- 세번째 레벨에 있는 4개의 서브클래스들이 구상 데코레이터이다.

> 자바 I/O를 보면 데코레이터의 단점도 찾을 수 있다. 데코레이터 패턴을 이용해서 디자인을 하다 보면 잡다한 클래스들이 너무 많아진다는 문제가 생긴다. 이 문제는 개발자 입장에서는 까다로울 수 있다.
>
> 하지만, 데코레이터가 어떤 식으로 작동하는지 이해하고 나면, 다른 사람이 데코레이터 패턴을 활용해서 만든 API를 사용하더라도 클래스들이 어떤 식으로 구성되어 있는지 일단 파악하고 나면 클래스를 데코레이터로 감싸서 원하는 행동을 구현할 수 있다.

## 구현 및 테스트 코드

- https://github.com/PaengE/HeadFirst_DesignPatterns/tree/main/src/headfirst/designpatterns/decorator