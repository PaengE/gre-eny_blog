## 디자인 패턴: 옵저버 패턴(Observer Pattern)

> 이 포스팅은 `Head First: Design Patterns` 책을 보고, 개인적으로 정리한 포스팅입니다.

## Observer Pattern 이란?

> `옵저버 패턴(Observer Pattern)`에서는 한 객체의 상태가 바뀌면 그 객체에 의존하는 다른 객체들한테 연락이 가고 자동으로 내용이 갱신되는 방식으로 일대다(one-to-many) 의존성을 정의한다.
>
> 일대다 관계는 주제(Subject)와 옵저버(Observer)에 의해 정의된다.
>
> 옵저버 패턴을 구현하는 방법에는 여러가지가 있지만, 대부분 주제(Subject) 인터페이스와 옵저버(Observer) 인터페이스가 들어있는 클래스 디자인을 바탕으로 한다.

![observer-1](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\observer-1.png)

- 일대다 관계
  - 옵저버 패턴에서 상태를 저장하고 지배하는 것은 주제 객체이다. 따라서 상태가 들어있는 객체는 하나만 있을 수 있다.
  - 하지만 옵저버는 사용하긴 하지만 반드시 상태를 가지고 있어야 하는 것은 아니므로 옵저버는 여러개가 있을 수 있고, 주제 객체에서 상태가 바뀌었다는 것을 알려주기를 기다리는, 주제에 의존적인 성질을 가지게 된다.
  - 따라서 하나의 주제와 여러개의 옵저버가 연관된, 일대다(one-to-many) 관계가 성립된다.
- 의존성
  - 데이터의 주인은 주제이며, 옵저버는 데이터가 변경되었을 때 주제에서 갱신해 주기를 기다리는 입장이기 때문에 의존성을 가진다고 할 수 있다.

## 느슨한 결합(Loose Coupling)

> 두 객체가 느슨하게 결합되어 있다는 것은, 그 둘이 상호작용을 하긴 하지만 서로에 대해 잘 모른다는 것을 의미한다.
>
> 옵저버 패턴에서는 주제와 옵저버가 느슨하게 결합되어 있는 객체 디자인을 제공한다.

그 이유는 다음과 같다. 아래 내용들은 `디자인 원칙`과도 관련이 있다.

1. 주제가 옵저버에 대해서 아는것은 옵저버가 특정 인터페이스(Observer 인터페이스)를 구현한다는 것 뿐이다.
   - 옵저버의 구상클래스가 무엇인지, 옵저버가 무엇을 하는지 등에 대해서는 알 필요가 없다.
2. 옵저버는 언제든지 새로 추가할 수 있다.
   - 주제는 Observer 인터페이스를 구현하는 객체의 목록에만 의존하기 때문에 언제든지 새로운 옵저버를 추가하거나, 기존 옵저버를 제거하거나 등의 작업을 할 수 있다.
3. 새로운 형식의 옵저버를 추가하려고 할 때도 주제를 전혀 변경할 필요가 없다.
   - 새로운 클래스에서 Observer 인터페이스를 구현하고 옵저버로 등록하기만 하면 된다.
4. 주제와 옵저버는 서로 독립적으로 재사용할 수 있다.
   - 주제와 옵저버가 서로 단단하게 결합되어 있지 않기 때문에 다른 용도로 손쉽게 재사용이 가능하다.
5. 주제나 옵저버가 바뀌더라도 서로한테 영향을 미치지 않는다.
   - 둘이 서로 느슨하게 결합되어 있기 때문이다.

> [ 디자인 원칙 - 4 ]
>
> - 서로 상호작용을 하는 객체 사이에서는 가능하면 느슨하게 결합하는 디자인을 사용해야 한다.
>
> -> 느슨하게 결합하는 디자인을 사용하면 변경사항이 생겨도 무난히 처리할 수 있는 유여한 객체지향 시스템을 구축할 수 있다. 객체 사이의 상호의존성을 최소화할 수 있기 때문이다.

## 기상 모니터링 애플리케이션 개요

> 이 시스템은 기상 스테이션(실제 기상 정보를 수집하는 장비)과 WeatherData 객체(기상 스테이션으로부터 오는 데이터를 추적하는 객체), 그리고 사용자에게 현재 기상 조건을 보여주는 디스플레이, 이렇게 세 요소로 이루어진다.

![observer-2](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\observer-2.png)

WeatherData 객체에서는 기상 스테이션 장비 자체로부터 데이터를 가져올 수 있다. 데이터를 가져온 후에는 디스플레이 장비에 세 가지 항목을 표시할 수 있다. 첫번째는 현재조건(온도, 습도, 압력)이고, 두번째는 기상 통계, 세번째는 간단한 기상 예보이다.

-> WeatherData 객체를 사용하여 현재 조건, 기상 통계, 기상 예측, 이렇게 세 항목을 디스플레이 장비에서 갱신해 가면서 보여주는 애플리케이션을 만들어야 한다. (확장 가능하게)

## 기상 스테이션 설계

![observer-3](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\observer-3.png)

꽤나 복잡한 모양의 다이어그램이 나온다.

## 기상 스테이션 구현

> 자바에서도 자체적으로 옵저버 패턴을 일부 지원하지만 여기서는 일단 구현해보고, 뒤에 자바의 옵저버 패턴을 다루겠다.

```java
public interface Subject {
	public void registerObserver(Observer o);
	public void removeObserver(Observer o);
	public void notifyObservers();	// 주제 객체의 상태가 변경되었을 때 모든 옵저버들에게
																	// 알리기위해 호출되는 메소드
}
```

```java
// Observer 인터페이스는 모든 옵저버 클래스에서 구현해야 하며,
// 따라서 모든 옵저버들은 update 메소드를 override 해야한다.
// temp, humidity, pressure 파라미터는 기상 정보가 변경되었을 때 옵저버한테 전달되는 상태값이다.
public interface Observer {
	public void update(float temp, float humidity, float pressure);
}
```

```java
// DisplayElement 인터페이스에는 display()라는 메소드밖에 없다.
// 디스플레이 항목을 화면에 표시해야 하는 경우에 그 메소드를 호출하면 된다.
public interface DisplayElement {
	public void display();
}
```

## WeatherData에서 Subject 인터페이스 구현하기

```java
import java.util.*;

public class WeatherData implements Subject {
	private ArrayList<Observer> observers;
	private float temperature;
	private float humidity;
	private float pressure;
	
  // Observer 객체들을 저장하기 위해 ArrayList를 생성자에서 생성한다.
	public WeatherData() {
		observers = new ArrayList<Observer>();
	}
	
	public void registerObserver(Observer o) {
		observers.add(o);
	}
	
	public void removeObserver(Observer o) {
		int i = observers.indexOf(o);
		if (i >= 0) {
			observers.remove(i);
		}
	}
	
  // 모든 옵저버들이 가지고 있는 update() 메소드를 이용하여 옵저버들에게 상태를 갱신시킨다.
	public void notifyObservers() {
		for (Observer observer : observers) {
			observer.update(temperature, humidity, pressure);
		}
	}
	
  // 기상 스테이션으로부터 갱신된 측정치를 받으면 옵저버들한테 알리는 notifyObservers를 호출한다.
	public void measurementsChanged() {
		notifyObservers();
	}
	// 기상 스테이션으로부터 갱신된 측정치를 받아 갱신 후, measurementsChanged를 호출한다.
	public void setMeasurements(float temperature, float humidity, float pressure) {
		this.temperature = temperature;
		this.humidity = humidity;
		this.pressure = pressure;
		measurementsChanged();
	}

	public float getTemperature() {
		return temperature;
	}
	
	public float getHumidity() {
		return humidity;
	}
	
	public float getPressure() {
		return pressure;
	}
}
```

## Display 항목 구현

여러 디스플레이 항목 중 CurrentConditonsDisplay를 예로 들겠다.

```java
// 모든 옵저버 클래스들은 Observer 인터페이스를 구현해야하며,
// Display가 필요한 클래스들은 DisplayElement 인터페이스 또한 구현해야한다.
public class CurrentConditionsDisplay implements Observer, DisplayElement {
	private float temperature;
	private float humidity;
	private Subject weatherData;
	
  // 생성자에 weatherData라는 주제 객체가 전달되며,그 객체를 써서 디스플레이를 옵저버로 등록한다.
	public CurrentConditionsDisplay(Subject weatherData) {
		this.weatherData = weatherData;
		weatherData.registerObserver(this);
	}
	
  // update()가 호출되면 기온과 습도를 저장하고 display()를 호출한다.
	public void update(float temperature, float humidity, float pressure) {
		this.temperature = temperature;
		this.humidity = humidity;
		display();
	}
	
	public void display() {
		System.out.println("Current conditions: " + temperature 
			+ "F degrees and " + humidity + "% humidity");
	}
}
```

이제 테스트용 클래스를 만들어 시연해보면 된다.(여기서는 WeatherStation.java)

테스트 및 코드는 포스팅의 맨 마지막에 링크를 달아두었다.

## 자바 내장 옵저버 패턴 사용하기

![observer-4](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\observer-4.png)

- Observable 클래스에서 모든 옵저버들을 관리하고 여러분 대신 연락을 해준다.
- Observable 은 인터페이스가 아니라 클래스이기 때문에 WeatherData에서는 Observable을 `확장`해야 한다.
- WeatherData 클래스는 더 이상 옵저버 등록, 삭제, 갱신을 구현하지 않고 슈퍼클래스에서 상속받아 사용한다.
- DisplayElement 인터페이스는 생략됐지만, 디스플레이 항목에서 그 인터페이스를 구현하는 것은 동일하다.

## 자바 내장 옵저버 패턴 작동 방식

> 자바에 내장된 옵저버 패턴과 앞에서 직접 구현한 옵저버 패턴의 큰 차이점은, WeatherData(주제 객체)에서 Observable 클래스를 확장하면서 addObserver, deleteObserver, notifyObservers 같은 메소드를 상속받는다는 것이다.
>
> 자바 버전은 다음과 같은 방법으로 사용하면 된다.

1. 객체가 옵저버가 되는 방법
   - 앞에서 했던 것과 비슷하게 Observer 인터페이스(이번에는 java.util.Observer 인터페이스이다)를 구현하고 Observable 객체의 addObserver() 메소드를 호출하면 된다. 옵저버 목록에서 탈퇴하고 싶을 때는 deleteObserver()를 호출하면 된다.
2. Observable에서 연락을 돌리는 방법
   - java.util.Observable 슈퍼클래스를 확장하여 Observable 클래스를 만들고 아래 두 단계를 더 거친다.
     1. setChanged() 메소드를 호출해서 객체의 상태가 바뀌었다는 것을 알린다.
     2. notifyObservers() 또는 notifyObservers(Object arg) 메소드를 호출한다.
3. 옵저버가 연락을 받는 방법
   - update(Observable o, Object arg) 메소드를 구현한다.
   - 연락을 보내는 주재 객체가 Observable o 로 전달되고, notifyObservers() 메소드에서 인자로 전달된 데이터 객체가 Object arg로 전달된다. (데이터 객체가 지정되지 않은 경우에는 null이 된다.)

> 데이터를 옵저버들한테 보낼 때는 (Push 방식으로 사용할 때에) 데이터를 notifyObservers(arg) 메소드의 인자로 전달하는 데이터 객체 형태로 전달해야 한다.
>
> 아니면 옵저버 쪽에서 전달받은 Observable 객체로부터 원하는 데이터를 가져가는 방식(Pull 방식)을 사용해야 한다. Pull 방식은 getter() 메소드를 활용한다. notifyObserver()를 호출할 때 데이터 객체를 보내지 않는다면 Pull 모델을 사용하고 있다는 뜻이다.

- 참고로 setChanged() 메소드는 상태가 바뀌었다는 것을 밝히기 위한 용도로 사용된다. 상태가 매우 짧은 주기로 계속 바뀔 경우, 이 메소드를 활용하여 상태의 변함정도를 어느정도 제어할 수 있다. 상태가 변했음을 나타내기 위해 changed flag를 사용한다.(상태가 변경되었으면 true)
- setChanged가 자주 사용된다면 clearChanged() 메소드도 함께 활용하는 것이 좋을 것이다. 이 메소드는 changed flag를 거짓으로 돌려놓는 역할을 한다.
- hasChanged()라는 메소드는, changed flag의 현재 상태를 알려준다.

## 자바 내장 기능을 활용한 기상 스테이션 구현

```java
import java.util.Observable;
	
public class WeatherData extends Observable {
	private float temperature;
	private float humidity;
	private float pressure;
	
	public WeatherData() { }
	
	public void measurementsChanged() {
		setChanged();
		notifyObservers();
	}
	
	public void setMeasurements(float temperature, float humidity, float pressure) {
		this.temperature = temperature;
		this.humidity = humidity;
		this.pressure = pressure;
		measurementsChanged();
	}
	
  // pull 방식을 사용하고 있기때문에 getter() 메소드들은 계속 남아있는다.
	public float getTemperature() {
		return temperature;
	}
	
	public float getHumidity() {
		return humidity;
	}
	
	public float getPressure() {
		return pressure;
	}
}
```

```java
import java.util.Observable;
import java.util.Observer;
	
public class CurrentConditionsDisplay implements Observer, DisplayElement {
	Observable observable;
	private float temperature;
	private float humidity;
	
	public CurrentConditionsDisplay(Observable observable) {
		this.observable = observable;
		observable.addObserver(this);
	}
	
  // Observable과 추가 데이터인자를 모두 받아들이도록 한다.
  // update() 메소드에서는 우선 Observable이 WeatherData형식인지 확인한 다음
  // Getter 메소드를 써서 기온과 습도를 가져온 후, display() 를 호출한다.
	public void update(Observable obs, Object arg) {
		if (obs instanceof WeatherData) {
			WeatherData weatherData = (WeatherData)obs;
			this.temperature = weatherData.getTemperature();
			this.humidity = weatherData.getHumidity();
			display();
		}
	}
	
	public void display() {
		System.out.println("Current conditions: " + temperature 
			+ "F degrees and " + humidity + "% humidity");
	}
}
```

테스트 시에 텍스트가 출력되는 순서가 달라지는 것은 이상한 일이 아니다.

오히려 옵저버한테 연락이 가는 순서에 의존하면(순서를 고려하고 프로그래밍하면) 절대 안된다.

특정 연락 순서에 의존하도록 만들었다면, Observable/Observer 구현을 변경했을 때, 연락 순서가 바뀌면 다른 결과가 나올 수 있고, 순서가 바뀐다고 하여 다른 결과가 나온다면, 그것은 `느슨한 결합`이라고 할 수 없기 때문이다.

## java.util.Observable의 단점

> Observable은 인터페이스가 아닌 클래스인 데다가, 어떤 인터페이스를 구현하는 것도 아니다. java.util.Observable 구현에는 활용도와 재사용성에 있어서 제약조건으로 작용하는 몇가지 문제점이 있다.

- Observable은 클래스이기 때문에, 서브클래스를 만들어야 한다는 문제가 있다. 이미 다른 슈퍼클래스를 확장하고 있는 클래스에 Observable의 기능을 추가할 수 없기 때문이다.(재사용성에 제약이 생김)
- Observable 인터페이스라는 것이 없기에 자바에 내장된 Observer API하고 잘 맞는 클래스를 직접 구현하는 것이 불가능하다. java.util 구현을 다른 구현으로 바꾸는 것도 불가능하다.
- Observable 클래스의 핵심 메소드를 외부에서 호출할 수 없다. Observable API를 살펴보면, setChanged() 메소드가 protected로 선언되어 있어 Observable의 서브클래스에서만 setChanged()를 호출할 수 있다.
  결국 직접 어떤 클래스를 만들고, Observable의 서브클래스를 인스턴스 변수로 사용하는 방법도 써먹을 수가 없다. 이런 디자인은 `상속보다는 구성을 사용한다`는 디자인 원칙에도 위배된다.

## 구현 및 테스트 코드

- https://github.com/PaengE/HeadFirst_DesignPatterns/tree/main/src/headfirst/designpatterns/observer