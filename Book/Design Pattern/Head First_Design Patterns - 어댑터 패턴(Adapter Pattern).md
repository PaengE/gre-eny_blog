## 디자인 패턴: 어댑터 패턴(Adapter Pattern)

> 이 포스팅은 `Head First: Design Patterns` 책을 보고, 개인적으로 정리한 포스팅입니다.

## Adapter Pattern 이란?

> `어댑터 패턴(Adapter Pattern)`은 한 클래스의 인터페이스를 클라이언트에서 사용하고자 하는 다른 인터페이스로 변환한다. 
>
> 어댑터를 이용하면 인터페이스 호환성 문제 때문에 같이 쓸 수 없는 클래스들을 연결해서 쓸 수 있다.
>
> `어댑터`를 사용함으로써 클라이언트와 구현된 인터페이스를 분리시킬 수 있으며, 나중에 인터페이스가 바뀌더라도 그 변경 내역은 어댑터에 캡슐화되기 때문에 클라이언트는 바뀔 필요가 없다.

![adapter-1](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\adapter-1.png)

- 어댑티를 새로 바뀐 인터페이스로 감쌀 때는 객체 `구성(Composition)`을 사용한다. 이런 접근법을 쓰면 어댑티의 어떤 서브클래스에 대해서도 어댑터를 쓸 수 있다는 장점이 있다.
- 어댑터 패턴에서는 클라이언트를 특정 구현이 아닌 인터페이스에 연결시킨다. 각각 서로 다른 백엔드 클래스들로 변환시키는 여러 어댑터들을 사용할 수 도 있다. 타겟 인터페이스만 제대로 지킨다면, 나중에 다른 구현을 추가하는 것도 가능하다.

## 오리와 칠면조 인형

> 어떤 기업에서 칠면조 인형의 재고가 많이남아 오리 인형으로 둔갑시켜 수출하려고 한다.
>
> 오리 인형과 칠면조 인형은 각각 울음소리를 다르게 가지고 있고, 날 수 있다.
>
> 하지만 칠면조 인형도 오리 인형인 척 해줘야 한다.
>
> 여기서 칠면조를 오리로 둔갑시켜주는 것이 바로 `어댑터`이다. (여기서 어댑터는 오리 인형인 척 하는 둔갑프로그램이다.)

```java
public interface Duck {
  public void quack();
  public void fly();
}
```

```java
public class MallardDuck implements Duck {
	public void quack() {
		System.out.println("Quack!");
	}
	
	public void fly() {
		System.out.println("I'm flying!");
	}
}
```

```java
public interface Turkey {
	public void gobble();
	public void fly();
}
```

```java
public class WildTurkey implements Turkey {
	public void gobble() {
		System.out.println("Gobble gobble");
	}
 
	public void fly() {
		System.out.println("I'm flying a short distance");
	}
}
```

WildTurkey 를 Duck 으로 둔갑시켜보자. (어댑터를 만들어보자.)

울음 소리는 오리와 달라도 속일 수 있지만, 나는 것만큼은 오리만큼 날아야 한다. 그래서 칠면조를 총 5번정도 날게 해야한다.

```java
public class TurkeyAdapter implements Duck {
	Turkey turkey;
 
	public TurkeyAdapter(Turkey turkey) {
		this.turkey = turkey;
	}
    
	public void quack() {
		turkey.gobble();
	}
  
	public void fly() {
		for(int i=0; i < 5; i++) {
			turkey.fly();
		}
	}
}
```

간단히 테스트를 해보자.

```java
public class DuckTestDrive {
	public static void main(String[] args) {
		MallardDuck duck = new MallardDuck();

		WildTurkey turkey = new WildTurkey();
		Duck turkeyAdapter = new TurkeyAdapter(turkey);

		System.out.println("The Turkey says...");
		turkey.gobble();
		turkey.fly();

		System.out.println("\nThe Duck says...");
		testDuck(duck);

		System.out.println("\nThe TurkeyAdapter says...");
		testDuck(turkeyAdapter);
	}

	static void testDuck(Duck duck) {
		duck.quack();
		duck.fly();
	}
}

/** 출력
		The Turkey says...
    Gobble gobble
    I'm flying a short distance

    The Duck says...
    Quack
    I'm flying

    The TurkeyAdapter says...
    Gobble gobble
    I'm flying a short distance
    I'm flying a short distance
    I'm flying a short distance
    I'm flying a short distance
    I'm flying a short distance
*/
```

### 클라이언트에서 어댑터를 사용하는 방법

1. 클라이언트(고객)에서 타겟 인터페이스(오리 인형)를 사용하여 메소드를 호출함으로써 어댑터(둔갑프로그램)에 요청을 한다.
2. 어댑터(둔갑프로그램)에서는 어댑티 인터페이스(칠면조 인형)를 사용하여 그 요청을 어댑티에 대한 (하나 이상의) 메소드 호출로 변환한다.
3. 클라이언트에서는 호출 결과를 받긴 하지만 중간에 어댑터가 껴 있는지는 전혀 알지 못한다.

## 객체 어댑터와 클래스 어댑터

> `어댑터`에는 두 종류가 있다. 위에서 언급했던 어댑터는 `객체 어댑터`이다.
>
> 다른 하나는 `클래스 어댑터`인데, 자바에서는 클래스의 다중 상속이 불가능하기 때문에 클래스 어댑터는 사용할 수 없다.
>
> 그래도 간단하게나마 클래스 다이어그램을 살펴보자.

![adapter-2](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\adapter-2.png)

클래스 어댑터에서는 어댑터를 만들 때 타겟과 어댑티 모두의 서브클래스로 만들고, 객체 어댑터에서는 구성을 통해서 어댑티에 요청을 전달한다는 점을 제외하면 객체 어댑터와 큰 차이는 없다.

## EnumerationToIterator 어댑터 예제

> 자바에는 `Enumeration`이라는 인터페이스가 있다. 이를 이용하면 컬렉션 내에서 각 항목이 관리되는 방식에는 신경 쓸 필요없이 컬렉션의 모든 항목들에 접근할 수 있다.
>
> Enumeration 과 유사한 기능을 하는 `Iterator` 인터페이스가 있다. 마찬가지로 컬렉션에 있는 일련의 항목들에 접근할 수 있게 해주면서, 추가로 항목을 제거할 수도 있게 해주는 remove() 메소드를 제공한다.
>
> 여기에서는 Enumeration을 사용하는 코드도 있지만, 새로 만드는 코드는 Iterator만 사용하려고 한다.

`Enumeration` 과 `Iterator` 인터페이스는 다음 구조를 가지고 있다.

`Iterator`가 타겟 인터페이스, `Enumeration`이 어댑티 인터페이스이다.

![adapter-3](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\adapter-3.png)

### 어댑터 디자인

> 우선 타겟 인터페이스를 구현하고, 어댑티 객체로 구성된 어댑터가 필요하다.
>
> hasNext() 는 hasMoreElements() 와, next() 는 nextElement() 와 대응되지만, remove() 는 Enumeration 에서 기능을 제공하지 않으므로, Exception을 날리도록 한다. (이럴 때 사용하는 UnsupportedOperationException을 예외를 자바에서 제공한다.)
>
> 다음 클래스 다이어그램의 EnumerationToIterator 가 어댑터이다.

![adapter-4](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\adapter-4.png)

```java
public class EnumerationIterator implements Iterator<Object> {
	Enumeration<?> enumeration;
 
	public EnumerationIterator(Enumeration<?> enumeration) {
		this.enumeration = enumeration;
	}
 
	public boolean hasNext() {
		return enumeration.hasMoreElements();
	}
 
	public Object next() {
		return enumeration.nextElement();
	}
 
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
```

## 각 패턴과 그 용도

- `데코레이터 패턴`: 인터페이스는 바꾸지 않고 책임(기능)만 추가
- `어댑터 패턴`: 한 인터페이스를 다른 인터페이스로 변환
- `퍼사드 패턴`: 인터페이스를 간단하게 바꿈

## 구현 및 테스트 코드

- https://github.com/PaengE/HeadFirst_DesignPatterns/tree/main/src/headfirst/designpatterns/adapter

