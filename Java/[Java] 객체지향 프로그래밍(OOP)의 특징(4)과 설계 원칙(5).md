## 객체지향 프로그래밍(OOP)

> `객체지향 프로그래밍(Object-Oriented Programming)`은 컴퓨터 프로그램을 명령어의 목록으로 보는 시각에서 벗어나, 여러 개의 독립된 단위(객체)들의 모임으로 파악하고자 하는 것이다.
>
> 각각의 객체는 `메시지`를 주고받고, 데이터를 처리할 수 있다.



객체지향 프로그래밍은 다음과 같은 장점을 가진다.

- 코드의 재사용성 증가
- 유지보수의 용이
- 간결한 코드



## OOP의 5가지 특성

> 관점에 따라 4가지 (추상화, 캡슐화, 다형성, 상속) 로 보기도 한다.



### 추상화, Abstraction

> `추상화`는 인터페이스로 클래스들의 공통적인 특성(변수, 메소드)들을 묶어 표현한다.

- `인터페이스`와 `구현`을 분리함으로써, 객체가 가진 특성 중 필수 속성만으로 객체를 묘사하고 유사성만을 표현하며 세부적인 상세 사항은 각 객체에 따라 다르게 구현되도록 할 수 있다.
- 자바에서는 `인터페이스`, `추상클래스`, `추상메소드` 정도로 활용할 수 있다. 



### 캡슐화, Encapsulation 

> `캡슐화`는 `데이터(속성)`와 `데이터를 처리하는 함수(메소드)`를 하나로 묶는다.

- 캡슐화된 객체의 세부 내용이 외부에 드러나지 않아서, 변경이 발생할 때 오류의 파급효과가 적다.
- 캡슐화된 객체들은 재사용이 용이하다.
- 객체들 간 메시지를 주고받을 때 각 객체의 세부 내용은 알 필요가 없으므로 인터페이스가 단순해지고, 객체 간 결합도가 낮아진다.



### 정보 은닉, Information hiding

> `정보 은닉` 다른 객체에게 자신의 정보를 숨기고, 자신의 연산만을 통하여 접근을 허용한다.

- 정보 은닉의 목적은 고려되지 않은 영향(Side Effect)들을 최소화하기 위함이다. 
- 외부 객체가 특정 객체의 변수와 메소드에 직접적으로 접근할 수 없게 만들어, 유지보수와 소프트웨어 확장 시 오류를 최소화할 수 있다.



### 상속, Inheritance

> `상속`은 기존 클래스를 수정하지 않으면서도 이미 정의되어 있는 내용을 확장해서 사용할 수 있는 방법을 제공한다.

- 상속을 받은 자식클래스는 부모클래스의 특성과 기능을 사용할 수 있다.
- 기능의 일부분을 변경하는 경우, 자식클래스에서 수정하여 사용할 수 있다.
- 캡슐화를 유지하므로, 클래스의 재사용을 용이하게 만들어준다.



### 다형성, Polymorphism

> `다형성`은 메시지에 의해 객체(클래스)가 연산을 수행하게 될 때, 하나의 메시지에 대해 각 객체(클래스)가 가지고 있는 고유한 방법으로 응답할수 있는 능력을 의미한다.

- `오버로딩(Overloading)`: 같은 이름의 메소드를 여러개 가지면서 매개변수의 유형과 개수가 다르도록 하는 기술
- `오버라이딩(Overriding)`: 상위 클래스가 가지고 있는 메소드를 하위 클래스에서 재정의 하는 기술



## OOP의 설계 5 원칙, SOLID

### 단일 책임 원칙, SRP(Single Responsiblity Principle)

> `"There should never be more than one reason for a class to change."`

- 하나의 클래스는 하나의 책임만 가져야 한다. 클래스는 그 책임을 완전히 캡슐화해야 함을 말한다.
- 어떤 변화에 의해 클래스를 변경해야 하는 이유는 오직 하나뿐이어야 함을 의미한다.



### 개방-폐쇄 원칙, OCP(Open-Closed Principle)

> `"All classes and similar units of source code should be open for extension but closed for modification."`

- 확장에는 열려있고, 변경에는 닫혀있다. 기존의 코드는 변경하지 않으면서(Closed), 기능을 추가할 수 있도록(Open) 설계해야 한다.
- OCP는 강한 응집도와 낮은 결합도를 유지시킨다.



### 리스코프 치환 원칙, LSP(Liskov Substitution Princple)

> `"Functions that use pointers of references to base classes must be able to use objects of derived classes without knowing it."`

- 객체는 프로그램의 정확성을 깨지 않으면서, 하위 타입의 인스턴스로 바꿀 수 있어야 한다.
- 이를 위해서 하위 타입의 객체는 상위 타입의 책임을 무시하거나 재정의 하지 않고 확장만 수행하도록 설계하여야 한다.



### 인터페이스 분리 원칙, ISP(Interface Segregation Principle)

> `"Clients should not be forced to depend upon interfaces that they do not use."`

- 하나의 범용적인 인터페이스보다 여러 개의 구체적인 인터페이스가 좋다는 원칙이다.



### 의존관계 역전 원칙, DIP(Dependency Inversion Principle)

> `"High level modules should not depend upon low level modules and abstractions should not depend upon details."`

- 의존 관계를 맺을 때 변화하기 쉬운 것, 자주 변화하는 것보다는 변화하기 어려운것, 거의 변화가 없는 것에 의존하라는 원칙이다.
- 즉, 구체적인 클래스(구체화)보다 인터페이스나 추상 클래스(추상화)에 의존하라는 뜻이다.



## Refer to

- http://www.blackwasp.co.uk/SOLID.aspx
- https://velog.io/@ygh7687/OOP%EC%9D%98-5%EC%9B%90%EC%B9%99%EA%B3%BC-4%EA%B0%80%EC%A7%80-%ED%8A%B9%EC%84%B1