## 디자인 패턴: 이터레이터 패턴(Iterator Pattern)

> 이 포스팅은 `Head First: Design Patterns` 책을 보고, 개인적으로 정리한 포스팅입니다.

## Iterator Pattern 이란?

> `이터레이터 패턴(Iterator Pattern: 반복자 패턴)`은 컬렉션 구현 방법을 노출시키지 않으면서도 그 집합체 안에 들어있는 모든 항목에 접근할 수 있게 해주는 방법을 제공해준다.
>
> 이 패턴을 이용하면 집합체 내에서 어떤 식으로 일이 처리되는지에 대해서 전혀 모르는 상태에서 그 안에 들어있는 모든 항목들에 대해 반복작업을 수행할 수 있다.
>
> 또한, 이터레이터 패턴을 사용하면 모든 항목에 일일이 접근하는 작업을 컬렉션 객체가 아닌 반복자 객체에서 맡게된다는 것이 중요한 점인데, 이는 집합체의 인터페이스 및 구현이 간단해질 뿐 아니라, 집합체에서는 반복작업에서 손을 떼고 원래 자신이 할 일(객체 컬렉션 관리)에만 전념할 수 있게 한다.

![Iterator-1](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\Iterator-1.png)

- `ConcreteAggregate`에는 객체 컬렉션이 들어있으며, 그 안에 들어있는 컬렉션에 대한 Iterator를 리턴하는 메소드를 구현한다.
- 모든 `ConcreteAggregate`는 그 안에 있는 객체 컬렉션에 대해 돌아가면서 반복 작업을 처리할 수 있게 해주는 `ConcreteIterator`의 인스턴스를 만들 수 있어야 한다.
- `ConcreteIterator`에서는 반복작업 중에 현재 위치를 관리한다.
- `Iterator`인터페이스에서는 모든 반복자에서 구현해야 하는 인터페이스를 제공하며, 컬렉션에 들어있는 원소들에 돌아가면서 접근할 수 있게 해주는 메소드들을 제공한다.

## 팬케이크 가게와 식당의 합병

```java
public class PancakeMenu{	// 팬케이크 가게의 메뉴 구현 방식
	// ...
  public ArrayList getMenuItems(){
    return menuItems;
  }
  // ...
}
```

```java
public class DinerMenu{	// 식당의 메뉴 구현 방식
	// ...
  public MenuItem[] getMenuItems(){
    return menuItems;
  }
  // ...
}
```

```java
public class Waitress {	// 웨이트리스는 서로다른 순환문을 만들어야 한다.
	// ...
  for (int i = 0; i < breakfastItems.size(); i++){
    MenuItem menuItem = (MenuItem) breakfastItems.get(i);
    //...
  }
  
  for (int i = 0; i < lunchItems.length; i++){
    MenuItem menuItem = lunchItem[i];
    // ...
  }
  // ...
}
```



> 두 가게가 합쳐지면서 메뉴 구현 방식 통합이 필요해졌다.
>
> 팬케이크 가게에서는 ArrayList를, 식당에서는 배열을 사용하여 메뉴를 구현하고 있다.
>
> 메뉴 항목이 서로 다른 식으로 구현되어 있기에, 두 개의 서로 다른 순환문을 만들어야 한다.

위와 같은 문제를 `객체 컬렉션에 대한 반복자 인터페이스`를 구현함으로써 해결할 수 있다.

다음과 같은 `Iterator` 인터페이스를 사용할 수 있겠다. 여기서의 `Iterator`는 Java 내장 Iterator 인터페이스는 아니다.

![Iterator-2](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\Iterator-2.png)

```java
public interface Iterator {
	boolean hasNext();
	MenuItem next();
}
```

```java
public class DinerMenuIterator implements Iterator {
	MenuItem[] items;
	int position = 0;
 
	public DinerMenuIterator(MenuItem[] items) {
		this.items = items;
	}
 
	public MenuItem next() {
		MenuItem menuItem = items[position];
		position = position + 1;
		return menuItem;
	}
 
	public boolean hasNext() {
		if (position >= items.length || items[position] == null) {
			return false;
		} else {
			return true;
		}
	}
}
```

```java
public class DinerMenu{
  // ...
  
  // 더 이상 필요없다. 내부 구조를 다 드러내는 단점이 있기 때문이다.
  //public MenuItem[] getMenuItems(){
  //  return menuItems;
  //}
  
  public Iterator createIterator(){
    return new DinerMenuIterator(menuItems);
  }
}
```

```java
public class Waitress{
  // ...
  private void printMenu(Iterator iterator){
    while (iterator.hasNext()){
    	MenuItem menuItem = (MenuItem) iterator.next();  
    }
  }
  // ...
}
```

- `createIterator()` 메소드는 menuItems 배열을 가지고 DinerMenuIterator를 생성한 다음 클라이언트한테 리턴한다.

위와 같이 코드를 수정하면 다음과 같은 클래스 다이어그램이 된다.

![Iterator-3](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\Iterator-3.png)

- `PancakeMenu`와 `DinerMenu`에서는 `createIterator()` 메소드를 구현한다. 이 메소드에서는 각 메뉴 항목에 대한 반복자를 만들어주는 역할을 한다.
- `PancakeMenuIterator`와 `DinerMenuIterator`는 `Iterator`인터페이스를 구현한다.
- `Iterator`덕분에 `Waitress` 클래스가 실제로 구현된 구상 클래스로부터 분리될 수 있다. `Iterator`를 받아서 컬렉션에 들어있는 모든 객체를 사용하기 때문에 Menu가 무엇으로 구성되었는지는 알 필요가 없다.
- 반복자를 이용하면, 컬렉션 입장에서는 그 안에 들어있는 모든 항목에 접근할 수 있게 하기 위해서 여러 메소드를 외부에 노출시키지 않으면서도, 컬렉션에 들어있는 모든 객체에 접근할 수 있다.
  이는 반복자를 구현한 코드를 컬렉션 밖으로 끄집어낼 수 있다는 장점이 있다. (반복작업을 캡슐화함)
- `PancakeMenu`와 `DinerMenu` 두 메뉴에서는 똑같은 메소드들을 제공하지만 아직 같은 인터페이스를 구현하고 있진 않는다. 

## 인터페이스를 개선해보자

> 위에서는 자바 내장 인터페이스 Iterator 를 사용한 것이 아니다.
>
> 반복자를 어떤 식으로 만드는지 알았으니 이번엔 자바에 있는 Iterator 인터페이스를 사용해보자.
>
> 자바의 Iterator 인터페이스는 `hasNext()`, `next()`, `remove()` 메소드를 가지고 있으므로 이들을 모두 구현해주어야 한다.

ArrayList 를 사용하는 PancakeMenu 는 별도의 Iterator가 필요하지 않다.

ArrayList 에 반복자를 리턴해주는 `iterator()`라는 메소드가 있기 때문이다.

하지만 배열을 사용하는 DinerMenu 는 자바의 Iterator 인터페이스를 사용한다 할지라도 별도의 Iterator가 필요하다.

```java
public class PancakeMenu {	
	// ...
  
  // ArrayList의 iterator() 를 활용, 더 이상 별도의 Iterator 클래스가 필요없다.
  public Iterator createIterator(){
    return menuItems.iterator();
  }
  // ...
}
```

```java
public class DinerMenuIterator implements Iterator {
	// ...
	public MenuItem next() {
		// ...
	}
	public boolean hasNext() {
		// ...
	}
  public boolean remove(){
    // Iterator 인터페이스의 remove를 오버라이드 해줘야한다.
  }
}
```

## 메뉴 추가는 주방장이, 웨이트리스는 그냥 메뉴만,

> Waitress 클래스에서 각 메뉴 객체를 참조할 때는 구상 클래스 대신 인터페이스를 이용하도록 하자.
>
> 이렇게 하면 `특정 구현이 아닌 인터페이스에 맞춰서 프로그래밍한다`는 원칙을 따르게 되므로 Waitress와 구상 클래스 사이의 의존성을 줄일 수 있다.

```java
import java.util.Iterator;

public interface Menu {
	public Iterator<MenuItem> createIterator();
}
```

최종 클래스 다이어그램을 살펴보자.

![Iterator-4](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\Iterator-4.png)

- `Waitress` 클래스는 `Menu`와 `Iterator`만 신경쓰면 된다.
- `DinerMenu`와 `PancakeMenu`는 `Menu`인터페이스를 구현하면 되고, 각 이터레이터 클래스들은 메뉴 항목에 대한 반복자를 생성하기 위해 `Iterator`인터페이스를 구현하면 된다.
- 여기서는 java.util 에서 제공하는 ArrayList.iterator() 반복자를 사용하므로 `PancakeMenuIterator`클래스는 더이상 필요가 없다.
- 하지만 배열에 대한 반복자는 자바에서 제공하지 않기 때문에, `DinerMenuIterator`클래스는 여전히 필요하다.

## 구현 및 테스트 코드

- https://github.com/PaengE/HeadFirst_DesignPatterns/tree/main/src/headfirst/designpatterns/iterator