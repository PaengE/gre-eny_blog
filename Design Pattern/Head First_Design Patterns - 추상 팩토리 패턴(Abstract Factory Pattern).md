## 디자인 패턴: 추상 팩토리 패턴(Abstract Factory Pattern)

> 이 포스팅은 `Head First: Design Patterns` 책을 보고, 개인적으로 정리한 포스팅입니다.
>
> 이전 포스팅 `간단한 팩토리`, `팩토리 메소드 패턴`과 연결되는 내용입니다.

## Abstract Factory Pattern 이란?

> `추상 팩토리 패턴(Abstract Factory Pattern)`에서는 인터페이스를 이용하여 서로 연관된, 또는 의존하는 객체를 구상 클래스를 지정하지 않고도 생성할 수 있다.
>
> 추상 팩토리 패턴을 사용하면 클라이언트에서 추상 인터페이스를 통해서 일련의 제품들을 공급받을 수 있다. 이 때, 실제로 어떤 제품이 생산되는지는 전혀 알 필요도 없다. 따라서 클라이언트와 팩토리에서 생산되는 제품을 분리할 수 있다.
>
> 클래스 다이어그램을 통해서 어떤 식으로 돌아가는지 살펴보자.

![abstarct factory-1](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\abstarct factory-1.png)

- AbstractFactory는 모든 구상 팩토리에서 구현해야하는 인터페이스이다. 제품을 생산하기 위한 일련의 메소드들이 정의되어 있다.
- 클라이언트를 만들 때는 추상 팩토리를 바탕으로 만든다. 실제 팩토리는 실행 시에 결정된다.
- AbstractProductA, B는 제품군으로, 각 구상 팩토리에서 필요한 제품들을 모두 만들 수 있다.
- ConcreteFactory1, 2는 구상팩토리로, 서로다른 제품군을 구현한다. 클라이언트에서 제품이 필요하면 이 팩토리 가운데 적당한 걸 골라서 쓰면 되기 때문에 제품 객체의 인스턴스를 직점 만들 필요가 없다.

## 원재료의 품질 관리 방법

> 지역마다 서로다른 종류의 재료들을 제공하기 위해 원재료군을 처리할 방법을 생각하자.
>
> 모든 객체마을 피자는 같은 구성요소로 이루어지지만, 지역마다 각 구성요소를 다른 방식으로 구현한다.

### 원재료 공장 만들기

> 이 공장에서는 원재료군에 들어있는 각각의 원재료를 생산한다. 우선 모든 원재료를 생산할 팩토리를 위한 인터페이스를 정의한다.

```java
public interface PizzaIngredientFactory {
	public Dough createDough();
	public Sauce createSauce();
	public Cheese createCheese();
	public Veggies[] createVeggies();
	public Pepperoni createPepperoni();
	public Clams createClam();
}
```

### 뉴욕 원재료 공장

> 이 팩토리에서는 전문적인 재료를 구현한다.

```java
public class NYPizzaIngredientFactory implements PizzaIngredientFactory {
 
	public Dough createDough() {
		return new ThinCrustDough();
	}
 
	public Sauce createSauce() {
		return new MarinaraSauce();
	}
 
	public Cheese createCheese() {
		return new ReggianoCheese();
	}
 
	public Veggies[] createVeggies() {
		Veggies veggies[] = { new Garlic(), new Onion(), new Mushroom(), new RedPepper() };
		return veggies;
	}
 
	public Pepperoni createPepperoni() {
		return new SlicedPepperoni();
	}

	public Clams createClam() {
		return new FreshClams();
	}
}
```

### 피자 클래스 변경

> Pizza 추상 클래스 준비가 끝났으니 뉴욕풍 피자와 시카고풍 피자를 만들어야 한다. 달라진 점은 원재료를 공장에서 바로 가져온다는 점밖엔 없다.
>
> 팩토리 메소드 패턴을 이용한 코드를 만들었을 때, NYCheesePizza와 ChicagoCheesePizza 두 클래스를 살펴보면, 지역별로 다른 재료를 사용한다는 것만 빼면 다른점이 없다. 야채피자나 조개피자도 마찬가지로 재료만 다를 뿐 준비 단계들은 똑같다.
>
> 따라서 피자마다 클래스를 지역별로 따로 만들 필요가 없다는 결론을 내릴 수 있다.

```java
public class CheesePizza extends Pizza {
	PizzaIngredientFactory ingredientFactory;
 
	public CheesePizza(PizzaIngredientFactory ingredientFactory) {
		this.ingredientFactory = ingredientFactory;
	}
 
	void prepare() {
		System.out.println("Preparing " + name);
		dough = ingredientFactory.createDough();
		sauce = ingredientFactory.createSauce();
		cheese = ingredientFactory.createCheese();
	}
}
```

### 지역별 재료 공장에 대한 레퍼런스 전달하기

```java
public class NYPizzaStore extends PizzaStore {
 
	protected Pizza createPizza(String item) {
		Pizza pizza = null;
		PizzaIngredientFactory ingredientFactory = 
			new NYPizzaIngredientFactory();
 
		if (item.equals("cheese")) {
  
			pizza = new CheesePizza(ingredientFactory);
			pizza.setName("New York Style Cheese Pizza");
  
		} else if (item.equals("veggie")) {
 
			pizza = new VeggiePizza(ingredientFactory);
			pizza.setName("New York Style Veggie Pizza");
 
		} else if (item.equals("clam")) {
 
			pizza = new ClamPizza(ingredientFactory);
			pizza.setName("New York Style Clam Pizza");
 
		} else if (item.equals("pepperoni")) {

			pizza = new PepperoniPizza(ingredientFactory);
			pizza.setName("New York Style Pepperoni Pizza");
 
		} 
		return pizza;
	}
}
```

> `추상 팩토리`라고 부르는 새로운 형식의 팩토리를 도입해서 서로 다른 피자에서 필요로 하는 원재료군을 생산하기 위한 방법을 구축했다.
>
> 추상 팩토리를 통해서 제품군을 생성하기 위한 인터페이스를 제공할 수 있다. 이 인터페이스를 이용하는 코드를 만들면 코드를 제품을 생산하는 실제 팩토리와 분리시킬 수 있다. 이렇게 함으로써 서로 다른 상황별로 적당한 제품을 생산할 수 있는 다양한 팩토리를 구현할 수 있게 된다.
>
> 코드가 실제 제품하고 분리되어 있으므로, 다른 공장을 사용하기만 하면 다른 결과를 얻을 수 있다.

## 피자가 주문에 따라 만들어지는 과정

1. 우선 뉴욕 피자가게(NYPizzaStore)가 필요하다.

   ```java
   PizzaStore nyPizzaStore = new NYPizzaStore();	// NYPizzaStore 인스턴스 생성
   ```

2. 피자 주문하기

   ```java
   nyPizzaStore.orderPizza("cheese");
   ```

   - nyPizzaStore 인스턴스의 orderPizza() 메소드가 호출된다.

3. orderPizza() 메소드에서는 우선 createPizza() 메소드를 호출한다.

   ```java
   Pizza pizza = createPizza("cheese");
   ```

4. createPizza() 메소드가 호출되면 원재료 공장이 돌아가기 시작한다.

   ```java
   Pizza pizza = new CheesePizza(nyIngredientFactory);
   ```

   - PizzaStore에서 원재료 공장을 선택하고 그 인스턴스를 만든다. 원재료 공장은 각 피자의 생성자엥 전달된다.
   - 뉴욕 원재료 공장을 사용하는 Pizza 인스턴스를 만든다.

5. 이제 피자를 준비해야 한다. prepare() 메소드가 호출되면 팩토리에 원재료 주문이 들어간다.

   ```java
   void prepare(){
   	dough = factory.createDough();
   	sauce = factory.createSauce();
   	chesse = factory.createCheese();
   }
   ```

6. 피자 준비가 끝나고, orderPizza() 메소드에서는 피자를 굽고, 자르고, 포장한다.

## 추상 팩토리 패턴을 적용시킨 피자 가게 다이어그램

![abstarct factory-2](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\abstarct factory-2.png)

> 추상 팩토리 패턴 뒤에는 팩토리 메소드 패턴이 숨어있는가?
>
> - 추상 팩토리 패턴에서 메소드가 팩토리 메소드로 구현되는 경우도 종종 있다. 당연히 추상 팩토리가 원래 일련의 제품들을 생성하는 데 쓰일 인터페이스를 정의하기 위해 만들어진 것이기 때문이다. 그 인터페이스에 있는 각 메소드는 구상 제품을 생산하는 일을 맡고 있고, 추상 팩토리의 서브클래스를 만들어서 각 메서드의 구현을 제공한다. 따라서 추상 팩토리 패턴에서 제품을 생산하기 위한 메소드를 구현하는 데 있어서 팩토리 메소드를 사용하는 것은 매우 자연스러운 일이다.

## 구현 및 테스트 코드

- https://github.com/PaengE/HeadFirst_DesignPatterns/tree/main/src/headfirst/designpatterns/factory/pizzaaf