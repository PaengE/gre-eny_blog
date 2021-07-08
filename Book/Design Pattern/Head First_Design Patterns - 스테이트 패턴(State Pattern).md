## 디자인 패턴: 스테이트 패턴(State Pattern)

> 이 포스팅은 `Head First: Design Patterns` 책을 보고, 개인적으로 정리한 포스팅입니다.

## State Pattern 이란?

> `스테이트(상태) 패턴(State Pattern)`을 이용하면 객체의 내부 상태가 바뀜에 따라서 객체의 행동을 바꿀 수 있다. 마치 객체의 클래스가 바뀌는 것과 같은 결과를 얻을 수 있다.
>
> 스테이트 패턴은 컨텍스트 객체에 수많은 조건문을 집어넣는 대신에 사용할 수 있는 패턴이라고 생각할 수 있다.

![state-1](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\state-1.png)

- `Context`라는 클래스에는 여러가지 내부 상태가 들어있을 수 있다. `Context`의 request() 메소드가 호출되면 그 작업은 상태 객체에게 맡겨진다.
- `State` 인터페이스에서는 모든 구상 상태 클래스에 대한 공통 인터페이스를 정의한다. 모든 상태 클래스에서 같은 인터페이스를 구현하기 때문에 바꿔가며 사용할 수 있다.
- `ConcreteState`에서는 `Context`로부터 전달된 요청을 처리한다. 각 `ConcreteState`에서 그 요청을 처리하는 방법을 구현한다. 이렇게 하면 `Context`에서 상태를 바꾸기만 하면 행동도 바뀌게 된다.
- 구상 상태 클래스는 얼마든지 많이 만들 수 있다.

## 뽑기 기계

> 뽑기 기계는 `동전 없음`, `동전 있음`, `알맹이 매진`, `알맹이 판매` 의 4가지 상태를 가질 수 있다.
>
> 그리고 `동전 투입`, `동전 반환`, `손잡이 돌리기`, `알맹이 배출` 의 4가지 행동을 가지고 있다.
>
> 같은 행동이라도 상태에 따라 다른 작업을 수행할 수 있다.

기본 틀은 다음과 같다.

```java
public class GumballMachine {
	final static int SOLD_OUT = 0;
	final static int NO_QUARTER = 1;
	final static int HAS_QUARTER = 2;
	final static int SOLD = 3;
 
	int state = SOLD_OUT;
	int count = 0;
  
	public GumballMachine(int count) {
		this.count = count;
		if (count > 0) {
			state = NO_QUARTER;
		}
	}
  
	public void insertQuarter() {
		if (state == HAS_QUARTER) {
			System.out.println("You can't insert another quarter");
		} else if (state == NO_QUARTER) {
			state = HAS_QUARTER;
			System.out.println("You inserted a quarter");
		} else if (state == SOLD_OUT) {
			System.out.println("You can't insert a quarter, the machine is sold out");
		} else if (state == SOLD) {
        	System.out.println("Please wait, we're already giving you a gumball");
		}
	}

	public void ejectQuarter() {
		if (state == HAS_QUARTER) {
			System.out.println("Quarter returned");
			state = NO_QUARTER;
		} else if (state == NO_QUARTER) {
			System.out.println("You haven't inserted a quarter");
		} else if (state == SOLD) {
			System.out.println("Sorry, you already turned the crank");
		} else if (state == SOLD_OUT) {
        	System.out.println("You can't eject, you haven't inserted a quarter yet");
		}
	}
 
	public void turnCrank() {
		if (state == SOLD) {
			System.out.println("Turning twice doesn't get you another gumball!");
		} else if (state == NO_QUARTER) {
			System.out.println("You turned but there's no quarter");
		} else if (state == SOLD_OUT) {
			System.out.println("You turned, but there are no gumballs");
		} else if (state == HAS_QUARTER) {
			System.out.println("You turned...");
			state = SOLD;
			dispense();
		}
	}
 
	private void dispense() {
		if (state == SOLD) {
			System.out.println("A gumball comes rolling out the slot");
			count = count - 1;
			if (count == 0) {
				System.out.println("Oops, out of gumballs!");
				state = SOLD_OUT;
			} else {
				state = NO_QUARTER;
			}
		} else if (state == NO_QUARTER) {
			System.out.println("You need to pay first");
		} else if (state == SOLD_OUT) {
			System.out.println("No gumball dispensed");
		} else if (state == HAS_QUARTER) {
			System.out.println("No gumball dispensed");
		}
	}
 
	public void refill(int numGumBalls) {
		this.count = numGumBalls;
		state = NO_QUARTER;
	}

	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("\nMighty Gumball, Inc.");
		result.append("\nJava-enabled Standing Gumball Model #2004\n");
		result.append("Inventory: " + count + " gumball");
		if (count != 1) {
			result.append("s");
		}
		result.append("\nMachine is ");
		if (state == SOLD_OUT) {
			result.append("sold out");
		} else if (state == NO_QUARTER) {
			result.append("waiting for quarter");
		} else if (state == HAS_QUARTER) {
			result.append("waiting for turn of crank");
		} else if (state == SOLD) {
			result.append("delivering a gumball");
		}
		result.append("\n");
		return result.toString();
	}
}
```

위와 같은 코드는 많은 문제점이 존재한다.

먼저, 딱 보기에도 지저분하며, 임의의 상태의 작업이 추가/변경된다면 수정할 때에 많은 불편이 생길 것이다.

위에서 존재하는 `상태`를 별도의 개별 클래스에 정의하고, 그 `상태의 작업`을 해당 클래스 안에 위치하도록 변경해보자.

## State 인터페이스 및 클래스 정의

> 먼저 `상태` 라는 인터페이스를 정의하고, `동전 없음`, `동전 있음`, `알맹이 매진`, `알맹이 판매` 의 4가지 상태를 별도의 개별 클래스로 만들어보자.

![state-2](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\state-2.png)

```java
public class GumballMachine {
	State soldOutState;
	State noQuarterState;
	State hasQuarterState;
	State soldState;
 
	State state;
	int count = 0;
	public GumballMachine(int numberGumballs) {
		soldOutState = new SoldOutState(this);
		noQuarterState = new NoQuarterState(this);
		hasQuarterState = new HasQuarterState(this);
		soldState = new SoldState(this);

		this.count = numberGumballs;
 		if (numberGumballs > 0) {
			state = noQuarterState;
		} else {
			state = soldOutState;
		}
	}
 
	public void insertQuarter() {
		state.insertQuarter();
	}
 
	public void ejectQuarter() {
		state.ejectQuarter();
	}
 
	public void turnCrank() {
		state.turnCrank();
		state.dispense();
	}
  
  int getCount() {
		return count;
	}

	void setState(State state) {
		this.state = state;
	}
  
  // state getter
}
```

```java
public interface State {
	public void insertQuarter();
	public void ejectQuarter();
	public void turnCrank();
	public void dispense();
}
```

```java
public class SoldState implements State {
  GumballMachine gumballMachine;
  public SoldState(GumballMachine gumballMachine) {
    this.gumballMachine = gumballMachine;
  }
       
	public void insertQuarter() {
		System.out.println("Please wait, we're already giving you a gumball");
	}
 
	public void ejectQuarter() {
		System.out.println("Sorry, you already turned the crank");
	}
 
	public void turnCrank() {
		System.out.println("Turning twice doesn't get you another gumball!");
	}
 
	public void dispense() {
		gumballMachine.releaseBall();
		if (gumballMachine.getCount() > 0) {
			gumballMachine.setState(gumballMachine.getNoQuarterState());
		} else {
			System.out.println("Oops, out of gumballs!");
			gumballMachine.setState(gumballMachine.getSoldOutState());
		}
	}
}
```

```java
public class HasQuarterState implements State {
	GumballMachine gumballMachine;
 
	public HasQuarterState(GumballMachine gumballMachine) {
		this.gumballMachine = gumballMachine;
	}
  
	public void insertQuarter() {
		System.out.println("You can't insert another quarter");
	}
 
	public void ejectQuarter() {
		System.out.println("Quarter returned");
		gumballMachine.setState(gumballMachine.getNoQuarterState());
	}
 
	public void turnCrank() {
		System.out.println("You turned...");
		gumballMachine.setState(gumballMachine.getSoldState());
	}

  public void dispense() {
    System.out.println("No gumball dispensed");
  }
}
```

SoldOutState와 NoQuarterState 클래스도 비슷하게 구현될 것이다.

이렇게 하면, 나중에 뽑기 기계에 새로운 기능이 추가되어도 간단히 그 기능에 대한 `상태 클래스`만 만들면 될 것이다.

## 구현 및 테스트 코드

- https://github.com/PaengE/HeadFirst_DesignPatterns/tree/main/src/headfirst/designpatterns/state