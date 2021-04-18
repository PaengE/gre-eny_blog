## 디자인 패턴: 팩토리 메소드 패턴(Factory Method Pattern)

> 이 포스팅은 `Head First: Design Patterns` 책을 보고, 개인적으로 정리한 포스팅입니다.
>
> 이전 포스팅 `간단한 팩토리`와 연결되는 내용입니다.

## Factory Method Pattern 이란?

> `팩토리 메소드 패턴(Factory Method Pattern)`에서는 객체를 생성하기 위한 인터페이스를 정의하는데, 어떤 클래스의 인스턴스를 만들지는 서브클래스에서 결정하게 만든다.
>
> 팩토리 메소드 패턴을 이용하면 클래스의 인스턴스를 만드는 일을 서브클래스에 맡긴다는 것이다.

![factory method-1](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\factory method-1.png)

- Creator에는 제품을 가지고 원하는 일을 하기 위한 모든 메소드들이 구현되어 있다. 하지만, 제품을 만들어 주는 팩토리 메소드는 추상 메소드로 정의되어 있을 뿐 구현되어 있진 않다. (Creator의 모든 서브클래스에서 factoryMethod() 추상 메소드를 구현해야 한다.)
- ConcreteCreator에서는 실제로 Product를 생산하는 factoryMethod()를 구현한다.
- Product 클래스에서는 모두 똑같은 인터페이스를 구현해야 한다. 그래야 그 제품을 사용할 클래스에서 구상클래스가 아닌 인터페이스에 대한 레퍼런스를 써서 객체를 참조할 수 있기 때문이다.
- 구상 클래스 인스턴스를 만들어내는 일은 ConcreteCreator가 책임진다. 여기에서 실제 제품을 만들어내는 방법을 아는 클래스는 이 클래스 뿐이다.

## 피자 프랜차이즈 사업

> 피자가게의 성공으로 프랜차이즈를 운영하게 되면서, 지금까지 써왔던 코드를 다른 지점에서도 쓸 수 있도록 만들기로 했다.
>
> 지역별로 조금씩 다른 차이점을 어떤 식으로 적용해야 할까?
>
> - 각 지점마다 그 지역의 특성과 입맛을 반영하여 다른 스타일의 피자(뉴욕 스타일, 시카고 스타일, 캘리포니아 스타일 등)를 만들어야 한다. -> 세가지 서로 다른 팩토리를 만들고, PizzaStore에서 적당한 팩토리를 사용하게 하는 것도 하나의 방법이다. 
>
> 하지만 위의 경우에는, 분점에서 독자적인 방법들(굽는 방식이 다르다던지, 피자를 자르지 않는다던지)이 생기면서, 작업의 통일화를 깨뜨려버린다. 그 말은 맛이 달라질 수 있다는 뜻이다.
>
> 그래서 결론은, `피자 가게와 피자 제작 과정 전체를 하나로 묶어주는 프레임워크`를 만들어야 되겠다는 것이다. (유연성은 챙기면서)

## 피자 가게 프레임워크

> 피자를 만드는 활동 자체는 전부 PizzaStore 클래스에 국한시키면서도 분점마다 고유의 스타일을 살릴 수 있도록 고쳐보자.

이제 createPizza() 메소드를 PizzaStore에 다시 집어 넣겠다. 하지만 이번에는 그 메소드를 `추상메소드`로 선언하고, 각 지역마다 고유의 스타일에 맞게 PizzaStore의 서브클래스를 만들도록 할 것이다.

일단 PizzaStore는 다음과 같이 달라진다.

```java
public abstract class PizzaStore {
	abstract Pizza createPizza(String item);

	public Pizza orderPizza(String type) {
		Pizza pizza = createPizza(type);

		pizza.prepare();
		pizza.bake();
		pizza.cut();
		pizza.box();
		return pizza;
	}
}
```

또한 각 분점을 위한 서브클래스가 필요하다. 각 지역별로 서브클래스(NYPizzaStore, ChicagoPizzaStore, CaliforniaPizzaStore)가 필요하다. 피자의 스타일은 각 서브클래스에서 결정한다.

## 서브클래스에서 결정되는 것

> PizzaStore의 orderPizza() 메소드에 이미 주문 시스템이 잘 갖춰져있다. 이 주문 시스템 자체는 모든 분점에서 똑같이 진행되어야 한다.
>
> 각 분점마다 달라질 수 있는 것은 피자의 스타일 뿐이며, 이렇게 달라지는 점들은 createPizza() 메소드에 집어넣고 그 메소드에서 해당 스타일의 피자를 만드는 것을 모두 책임지도록 할 것이다.(PizzaStore의 서브클래스에서 createPizza() 메소드를 구현하도록 하면 된다.)

![factory method-2](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\factory method-2.png)

- 여기서 createPizza()가 `팩토리 메소드`가 된다.
- 팩토리 메소드는 객체 생성을 처리하며, 팩토리 메소드를 이용하면 객체를 생성하는 작업을 서브클래스에 캡슐화시킬 수 있다. 이렇게 하면 슈퍼클래스에 있는 클라이언트 코드와 서브클래스에 있는 객체 생성 코드를 분리시킬 수 있다.

## Pizza 클래스와 PizzaStore의 구상 서브클래스

```java
import java.util.ArrayList;

public abstract class Pizza {
	String name;
	String dough;
	String sauce;
	ArrayList<String> toppings = new ArrayList<String>();
 
	void prepare() {
		System.out.println("Prepare " + name);
		System.out.println("Tossing dough...");
		System.out.println("Adding sauce...");
		System.out.println("Adding toppings: ");
		for (String topping : toppings) {
			System.out.println("   " + topping);
		}
	}
  
	void bake() {
		System.out.println("Bake for 25 minutes at 350");
	}
 
	void cut() {
		System.out.println("Cut the pizza into diagonal slices");
	}
  
	void box() {
		System.out.println("Place pizza in official PizzaStore box");
	}
 
	public String getName() {
		return name;
	}

	public String toString() {
		StringBuffer display = new StringBuffer();
		display.append("---- " + name + " ----\n");
		display.append(dough + "\n");
		display.append(sauce + "\n");
		for (String topping : toppings) {
			display.append(topping + "\n");
		}
		return display.toString();
	}
}
```

```java
public class NYStyleCheesePizza extends Pizza {

	public NYStyleCheesePizza() { 
		name = "NY Style Sauce and Cheese Pizza";
		dough = "Thin Crust Dough";
		sauce = "Marinara Sauce";
 
		toppings.add("Grated Reggiano Cheese");
	}
}
```

```java
public class ChicagoStyleCheesePizza extends Pizza {

	public ChicagoStyleCheesePizza() { 
		name = "Chicago Style Deep Dish Cheese Pizza";
		dough = "Extra Thick Crust Dough";
		sauce = "Plum Tomato Sauce";
 
		toppings.add("Shredded Mozzarella Cheese");
	}
 
	void cut() {
		System.out.println("Cutting the pizza into square slices");
	}
}
```

## 피자가 주문에 따라 만들어지는 과정

1. 우선 NYPizzaStore가 필요하다.

   ```java
   PizzaStore nyPizzaStore = new NYPizzaStore();	// NYPizzaStore 인스턴스 생성
   ```

2. 피자 가게가 확보됐으니까 주문을 받는다.

   ```java
   nyPizzaStore.orderPizza("cheese");	
   ```

   - nyPizzaStore 인스턴스의 orderPizza() 메소드가 호출된다.(그러면 PizzaStore에 정의된 메소드가 호출된다.)

3. orderPizza() 메소드에서 createPizza() 메소드를 호출한다.

   ```java
   Pizza pizza = createPizza("cheese");
   ```

   - 팩토리 메소드인 createPizza() 메소드는 서브클래스에서 구현했다. 이 경우에는 뉴욕풍의 치즈피자가 리턴된다.

4. 아직 준비가 되지 않은 피자를 받았다. 이제 피자 만드는 작업을 마무리한다.

   ```java
   pizza.prepare();
   pizza.bake();
   pizza.cut();
   pizza.box();
   ```

   - orderPizza() 메소드에서 피자 객체를 받았다. 하지만 그 피자 객체가 어느 구상 클래스의 객체인지는 전혀 알지 못한다.
   - 이 메소드들은 모두 createPizza() 팩토리 메소드에서 리턴한 특정 피자 객체 내에 정의되어 있다. 그리고 createPizza() 메소드는 NYPizzaStore에 정의되어 있다.

## 팩토리 메소드 패턴을 적용시킨 피자 가게 다이어그램

### 생산자(Creator) 클래스

![factory method-3](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\factory method-3.png)

- PizzaStore는 추상 생산자 클래스이다. 나중에 서브클래스에서 제품(객체)을 생산하기 위해 구현할 팩토리 메소드(추상 메소드)를 정의한다.
- 생산자 클래스에 추상 제품 클래스에 의존하는 코드가 들어있는 경우도 있다. 이 제품 클래스의 객체는 이 클래스의 서브클래스에 의해 만들어지고, 생산자 자체에서는 절대로 어떤 구상 제품 클래스가 만들어질지 미리 알 수 없다.
- NYPizzaStore, ChicagoPizzaStore은 구상 생산자(ConcreteCreator)이며, 제품을 생산하는 클래스이다.
- 여기에서는 createPizza() 메소드가 팩토리 메소드이다. 이 멤소드에서 제품(피자 객체)을 생산한다.

### 제품(Product) 클래스

![factory method-4](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\factory method-4.png)

- Pizza는 팩토리로써 제품을 생산한다. PizzaSotre에서는 Pizza를 만든다.
- 나머지 클래스들은 구상 클래스이며, 피자 가게에서 만들어지는 피자들이다.

## 병렬 클래스 계층 구조

> 생산자(Creator) 클래스와 제품(Product) 클래스 계층구조를 나란히 놓고 살펴보자.
>
> 두 클래스 모두 추상 클래스로 시작하고, 그 클래스를 확장하는 구상클래스들을 가지고 있다. 그리고 구체적인 구현은 모두 구상클래스들이 책임지고 있다.

NYPizzaStore에는 뉴욕풍 피자를 만드는 것에 대한 모든 지식이 캡슐화되어 있고,

ChicagoPizzaStore에는 시카고풍 피자를 만드는 것에 대한 모든 지식이 캡슐화되어 있다.

팩토리 메소드는 이러한 지식을 캡슐화시키는 데 있어서 가장 핵심적인 역할을 맡고 있다.

![factory method-5](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\factory method-5.png)

> [ 디자인 원칙 - 6 ]
>
> - 의존성 뒤집기 원칙(Dependency Inversion Principle): 추상화된 것에 의존하도록 만들어라. 구상 클래스에 의존하도록 만들지 않도록 한다.
>
> 이 원칙은 `고수준 구성요소`가 `저수준 구성요소`에 의존하면 안된다는 것이 중요하며, 항상 추상화에 의존하도록 만들어야 한다고 말한다.
>
> `고수준 구성요소`는 다른 `저수준 구성요소`에 의해 정의되는 행동이 들어있는 구성요소를 말한다. (이 포스팅에서는 PizzaStore가 고수준 구성요소, 피자 클래스들이 저수준 구성요소이다.)

### 원칙을 지키는 데 도움이 될만한 가이드라인...

> 다음과 같은 가이드라인을 따르면 `의존성 뒤집기 원칙`에 위배되는 객체지향 디자인을 피하는 데 도움이 된다.
>
> 그러나, 아래 가이드라인을 다 지키는 것은 불가능하며, 지킬 수 있는 한 지키는 것이 좋은 디자인을 만드는데 도움이 될 것이다.

1. 어떤 변수에도 구상 클래스에 대한 레퍼런스를 저장하지 말아라.
   - new 연산자를 사용하면 구상 클래스에 대한 레퍼런스를 사용하게 되는 것이다. 팩토리를 써서 구상 클래스에 대한 레퍼런스를 변수에 저장하는 일을 미연에 방지하도록 하자.
2. 구상 클래스에서 유도된 클래스를 만들지 말아라.
   - 구상 클래스에서 유도된 클래스를 만들면 특정 구상 클래스에 의존하게 된다. 인터페이스나 추상 클래스처럼 추상화된 것으로부터 클래스를 만들어야 한다.
3. 베이스 클래스에 이미 구현되어 있던 메소드를 오버라이드하지 말아라.
   - 이미 구현되어 있는 메소드를 오버라이드한다는 것은 애초부터 베이스 클래스가 제대로 추상화된 것이 아니었다고 볼 수있다. 베이스 클래스에서 메소드를 정의할 때는 모든 서브클래스에서 공유할 수 있는 것만 정의해야 한다.

## 구현 및 테스트 코드

- https://github.com/PaengE/HeadFirst_DesignPatterns/tree/main/src/headfirst/designpatterns/factory/pizzafm