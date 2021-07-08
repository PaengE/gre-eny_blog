## 디자인 패턴: 간단한 팩토리(Simple Factory)

> 이 포스팅은 `Head First: Design Patterns` 책을 보고, 개인적으로 정리한 포스팅입니다.

## 피자가게 판매 시스템

> 피자가게에서 다음과 같은 코드로 가게를 운영하고 있다고 하자.

```java
public class PizzaStore {
  public Pizza orderPizza(String type) {
    Pizza pizza;

    if (type.equals("cheese")) {
      pizza = new CheesePizza();
    } else if (type.equals("pepperoni")) {
      pizza = new PepperoniPizza();
    } else if (type.equals("clam")) {
      pizza = new ClamPizza();
    } else if (type.equals("veggie")) {
      pizza = new VeggiePizza();
    }

    pizza.prepare();
    pizza.bake();
    pizza.cut();
    pizza.box();

    return pizza;
  }
}
```

- 하지만, 피자 종류를 추가하거나 제외할 때마다 if 조건부의 코드를 고쳐야 한다. (if 조건부는 바뀌는 부분, 나머지 밑의 부분은 바뀌지 않는 부분)
- orderPizza() 메소드에서 가장 문제가 되는 부분은 인스턴스를 만들 구상클래스를 선택하는 부분이다.
  그 부분 때문에 상황 변화에 따라 코드를 변경할 수 밖에 없다. -> 캡슐화 필요

> 객체 생성 부분을 캡슐화 하자. (객체 생성 부분은 바뀌는 부분이므로)
>
> - 객체 생성을 처리하는 클래스를 `팩토리`라고 부른다. 
> - SimplePizzaFactory를 만들고 나면 orderPizza() 메소드는 새로 만든 객체의 클라이언트가 된다. 즉, 새로 만든 객체를 호출하는 것이다.
> - 이제 더이상 orderPizza() 메소드에서 어떤 피자를 만들어야 하는지 고민하지 않아도 된다. orderPizza() 메소드에서는 Pizza 인터페이스를 구현하는 피자를 받아 그 인터페이스에서 정의했던 prepare(), bake(), cut(), box() 메소드를 호출하기만 하면 된다.

## 간단판 피자 팩토리 만들기 & PizzaStore 클래스 수정

```java
// 이 클래스의 역할은 클라이언트를 위해 피자(객체) 인스턴스를 만들어 주는 것
public class SimplePizzaFactory {
  // 클라이언트에서 새로운 객체의 인스턴스를 만들 때 호출하는 메소드
	public Pizza createPizza(String type) {
		Pizza pizza = null;

		if (type.equals("cheese")) {
			pizza = new CheesePizza();
		} else if (type.equals("pepperoni")) {
			pizza = new PepperoniPizza();
		} else if (type.equals("clam")) {
			pizza = new ClamPizza();
		} else if (type.equals("veggie")) {
			pizza = new VeggiePizza();
		}
		return pizza;
	}
}
```

```java
public class PizzaStore {
	SimplePizzaFactory factory;
 
	public PizzaStore(SimplePizzaFactory factory) { 
		this.factory = factory;
	}
 
	public Pizza orderPizza(String type) {
		Pizza pizza;
 
		pizza = factory.createPizza(type);
 
		pizza.prepare();
		pizza.bake();
		pizza.cut();
		pizza.box();

		return pizza;
	}
}
```

수정 전과 별반 다를 바 없어 보이지만, 위와 같이 분리하여 캡슐화하면 다음과 같은 장점이 있다.

- 피자를 생성해야 하는 클라이언트(클래스)가 많다면, `캡슐화된 팩토리` 하나로 피자를 생성할 수 있다.
- 구현을 변경해야 하는 경우에 피자를 생성하던 클래스들을 하나하나 고칠 필요없이, 이 팩토리 클래스 하나만 수정하면 된다.

비슷한 식으로 메소드를 정적 메소드로 선언한 디자인이 있다. 그 디자인과의 차이는 다음과 같다.

- 간단한 팩토리를 정적 메소드로 정의하는 기법을 `정적 팩토리(static factory)`라고 부르기도 한다.
- 정적 팩토리를 사용하는 이유는, 객체를 생성하기 위한 메소드를 실행시키기 위해서 객체의 인스턴스를 만들지 않아도 되기 때문이다.
- 하지만 서브 클래스를 만들어서 객체 생성 메소드의 행동을 변경시킬 수 없다는 것이 단점이다.

## 간단한 팩토리(Simple Factory)란?

> 간단한 팩토리(Simple Factory)는 디자인 패턴이라고 할 수는 없다. 프로그래밍을 하는데 있어서 자주 쓰이는 관용구에 가깝다고 할 수 있다.
>
> 간단한 팩토리를 적용시킨 피자 가게 프로그램의 클래스 다이어그램은 다음과 같다.

![simple factory-1](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\simple factory-1.png)

- PizzaStore: 팩토리를 사용하는 클라이언트
- SimplePizzaFactory: 피자 객체를 생성하는 팩토리, 이런 메소드를 static 메소드로 선언하는 경우가 종종 있다.
- Pizza: 팩토리에서 만들어내는 상품인 피자. Pizza는 메소드를 오버라이드해서 쓸 수 있도록 추상클래스로 정의했다.
- 나머지: 팩토리에서 생산하는 제품에 해당하는 구상 클래스이다. 각 피자는 Pizza 인터페이스(추상클래스)를 구현해야 한다.

> 팩토리에 해당하는 다음 두가지 패턴에 대해서는 다음 포스팅에 다도록 하겠다.
>
> 1. 팩토리 메소드 패턴(Factory Method Pattern)
> 2. 추상 팩토리 패턴(Abstract Factory Pattern)

## 구현 및 테스트 코드

- https://github.com/PaengE/HeadFirst_DesignPatterns/tree/main/src/headfirst/designpatterns/factory/pizzas