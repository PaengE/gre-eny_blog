## 디자인 패턴: 템플릿 메소드 패턴(Template Method Pattern)

> 이 포스팅은 `Head First: Design Patterns` 책을 보고, 개인적으로 정리한 포스팅입니다.

## Template Method Pattern 이란?

> `템플릿 메소드 패턴(Template Method Pattern)`에서는 메소드에서 알고리즘의 골격을 정의한다.
>
> 알고리즘의 여러 단계 중 일부는 서브클래스에서 구현할 수 있다.
>
> 템플릿 메소드를 이용하면 알고리즘의 구조는 그대로 유지하면서 서브클래스에서 특정 단계를 재정의할 수 있다.
>
> `템플릿(Template)`이란 일련의 단계들로 알고리즘을 정의한 메소드이다. 여러 단계 가운데 하나 이상이 추상 메소드로 정의되며, 그 추상 메소드는 서브클래스에서 구현된다. 이렇게 함으로써 서브클래스에서 일부분을 구현할 수 있도록 하면서도 알고리즘의 구조는 바꾸지 않게 할 수 있다.

![template method-1](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\template method-1.png)

- AbstractClass에는 템플릿 메소드가 들어있다. abstract 메소드로 선언된 단계(메소드)들이 템플릿 메소드에서 활용된다.
- 템플릿 메소드에서는 알고리즘을 구현할 때, 추상 메소드 primitiveOperation 1과 2를 활용한다. 템플릿 메소드는 서브클래스에서 알고리즘의 각 단계를 마음대로 건드리는 것을 방지하기 위해 final로 선언한다. 알고리즘 자체는 이 단계들의 구체적인 구현으로부터 분리되어 있다.
- ConcreteClass는 여러 개가 있을 수 있으며, 각 클래스에서는 템플릿 메소드에서 요구하는 모든 단계들을 제공해야 한다.
- abstract로 선언되었던 단계들은 ConcreteClass에서 구현한다. 템플릿 메소드에서는 이런 메소드들을 호출해서 작업을 처리한다. concreteOperation() 은 구상 메소드이다.

```java
abstract class AbstractClass{
  final void templateMethod(){
    primitiveOperation1();
    primitiveOperation2();
    concreteOperation();
  }
  
  abstract void primitiveOperation1();
  abstract void primitiveOperation2();
  
  void concreteOperation(){
    // concreteOperation() 메소드 코드
  }
}
```

## 커피 및 홍차 만들기 

> 커피 클래스는 커피를 만들 때 `boilWater` - `brewCoffeeGrinds` - `pourInCup` - `addSugarAndMilk` 과정이 필요하다.
>
> 홍차 클래스는 홍차를 만들 때 `boilWater` - `steepTeaBag` - `pourInCup` - `addLemon` 과정이 필요하다.
>
> 커피 클래스와 홍차 클래스는 다음과 같다. 여기서 `prepareRecipe()`가 템플릿 메소드이다.

![template method-2](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\template method-2.png)

`boilWater` 과 `pourInCup` 과정은 중복되므로, 공통적인 부분을 추상화시켜서 베이스 클래스로 만들 수 있다.

![template method-3](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\template method-3.png)

`brewCoffeeGrinds`와 `steepTeaBag`은 `뭔가를 우린다`라는 공통점을 가지고 있다.

마찬가지로 `addSugarAndMilk`와 `addLemon` 또한 `뭔가를 추가한다`라는 공통점을 가지고 있다.

따라서 위에 언급한 부분을 추상화할 수 있을 것이다.

![template method-4](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\template method-4.png)

```java
public abstract class CaffeineBeverage{
	final void prepareRecipe(){
    boilWater();
    brew();
    pourInCup();
    addCondiments();
  }
  abstract void brew();
  abstract void addCondiments();
  
  void boilWater(){
    System.out.println("물 끓이는 중");
  }
  void pourInCup(){
    System.out.println("컵에 따르는 중");
  }
}
```

```java
public class Tea extends CaffeineBeverage{
  public void brew(){
    System.out.println("차를 우려내는 중");
  }
  public void addCondiments(){
    System.out.println("레몬을 추가하는 중");
  }
}
```

```java
public class Coffee extends CaffeineBeverage{
  public void brew(){
    System.out.println("필터로 커피를 우려내는 중");
  }
  public void addCondiments(){
    System.out.println("설탕과 커피를 추가하는 중");
  }
}
```

## 템플릿 메소드와 후크

> `후크(Hook)`는 추상 클래스에서 선언되는 메소드이긴 하지만, 기본적인 내용만 구현되어 있거나 아무 코드도 들어있지 않은 메소드이다.
>
> `후크`를 사용하면, 서브클래스 입장에서는 다양한 위치에서 알고리즘에 끼어들 수 있다. (그냥 무시할 수도 있다.)

예를 들어, 고객이 요청에 응했을 때만 첨가물을 넣는 경우가 있다. 고객이 요청에 응하지 않았으면 첨가물을 집어넣지 않는다. 이런 경우를 디자인 해보자.

```java
public abstract class CaffeineBeverageWithHook{
	final void prepareRecipe(){
    boilWater();
    brew();
    pourInCup();
    if (customerWantsCondiments()){	// 고객이 요청에 응했을 때만 수행된다.
	    addCondiments();
    }
  }
  abstract void brew();
  abstract void addCondiments();
  
  void boilWater(){
    System.out.println("물 끓이는 중");
  }
  void pourInCup(){
    System.out.println("컵에 따르는 중");
  }
  
  boolean customerWantsCondiments(){	// 고객이 요청에 응하지 않았을 때는 기본값이 false
    return false;
  }
}
```

- `customerWantsCondiments()` 메소드는 서브클래스에서 필요에 따라 오버라이드할 수 있는 메소드로 `후크`이다.

```java
public class CoffeeWithHook extends CaffeineBeverageWithHook {
 
	public void brew() {
		System.out.println("필터로 커피를 우려내는 중");
	}
 
	public void addCondiments() {
		System.out.println("우유와 설탕을 추가하는 중");
	}
 
	public boolean customerWantsCondiments() {

		String answer = getUserInput();

		if (answer.toLowerCase().startsWith("y")) {
			return true;
		} else {
			return false;
		}
	}
 
	private String getUserInput() {
		String answer = null;

		System.out.print("커피에 우유와 설탕을 넣어 드릴까요? (y/n)? ");

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try {
			answer = in.readLine();
		} catch (IOException ioe) {
			System.err.println("IO 오류");
		}
		if (answer == null) {
			return "no";
		}
		return answer;
	}
}
```

- 고객이 요청에 응할건지 말건지를 물어본 뒤에 그 결과에 따라 첨가물을 넣을지 말지를 판단한다.

## 할리우드 원칙

> `할리우드 원칙` 을 사용하면, 저수준 구성요소에서 시스템에 접속을 할 수는 있지만, 언제 어떤 식으로 그 구성요소들을 사용할지는 고수준 구성요소에서 결정하게 된다.
>
> 이렇게 되면 `의존석 부패(Dependency rot)`를 방지할 수 있다.

### 의존성 부패(Dependecy rot) 이란

- 어떤 고수준 구성요소가 저수준 구성요소에 의존하고, 그 저수준 구성요소는 다시 고수준 구성요소에 의존하고, 그 다른 고수준 구성요소는 다시 또 다른 구성요소에 의존하고, ... 식으로 의존성이 복잡하게 꼬여있는 것을 `의존성 부패`라고 한다.