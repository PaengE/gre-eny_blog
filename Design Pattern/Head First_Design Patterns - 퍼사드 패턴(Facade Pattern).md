## 디자인 패턴: 퍼사드 패턴(Facade Pattern)

> 이 포스팅은 `Head First: Design Patterns` 책을 보고, 개인적으로 정리한 포스팅입니다.

## Facade Pattern 이란?

> `퍼사드 패턴(Facade Pattern)`은 어떤 서브시스템의 일련의 인터페이스에 대한 통합된 인터페이스를 제공한다. 
>
> 퍼사드에서 고수준의 인터페이스를 정의하기 때문에 서브시스템을 더 쉽게 사용할 수 있다는 장점이 있다. (복잡한 추상화 같은 것이 필요 없다.)
>
> 퍼사드 패턴을 사용하려면, 어떤 서브시스템에 속한 일련의 복잡한 클래스들을 단순화하고 통합한 클래스를 만들어야 한다.

![facade-1](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\facade-1.PNG)

- 위에서처럼 서브 시스템들을 통합한 인터페이스인 `퍼사드 클래스`를 사용한다.

## 홈 씨어터

> 영화를 보려면 Light를 키고, Projector를 키고, Amplifier를 키고, DvdPlayer를 키고 등의 작업을 수행하여야 한다.

```java
...
  
	Amplifier amp;
	DvdPlayer dvd;
	Projector projector;
	TheaterLights lights;
	Screen screen;

	lights.dim(10);
	screen.down();
	projector.on();
	...
    
...
```

- 기존에는 필요할 때마다 이렇게 위에서 언급한 모든 것들의 객체를 생성하고, 객체의 메소드를 호출하여야 한다. 
- 영화를 하나 보려면 너무 복잡한 과정을 거쳐야 한다.

### 하나의 통합된 인터페이스로 정의하는 퍼사드 클래스를 사용해보자.

> 홈 씨어터 퍼사드 객체를 생성하고, 영화 제목만 넘기면 알아서 영화를 틀도록 해보자.

```java
public class HomeTheaterFacade {
	Amplifier amp;
	Tuner tuner;
	DvdPlayer dvd;
	CdPlayer cd;
	Projector projector;
	TheaterLights lights;
	Screen screen;
	PopcornPopper popper;
 
	public HomeTheaterFacade(Amplifier amp, Tuner tuner, DvdPlayer dvd, CdPlayer cd, Projector projector, Screen screen, TheaterLights lights) {
 
		this.amp = amp;
		this.tuner = tuner;
		this.dvd = dvd;
		this.cd = cd;
		this.projector = projector;
		this.screen = screen;
		this.lights = lights;
		this.popper = popper;
	}
 
	public void watchMovie(String movie) {
		System.out.println("Get ready to watch a movie...");
		popper.on();
		popper.pop();
		lights.dim(10);
		screen.down();
		projector.on();
		projector.wideScreenMode();
		amp.on();
		amp.setDvd(dvd);
		amp.setSurroundSound();
		amp.setVolume(5);
		dvd.on();
		dvd.play(movie);
	}
 
 
	public void endMovie() {
		System.out.println("Shutting movie theater down...");
		popper.off();
		lights.on();
		screen.up();
		projector.off();
		amp.off();
		dvd.stop();
		dvd.eject();
		dvd.off();
	}
}
```

- 이렇게 `퍼사드 클래스`를 별도로 구성하면, `퍼사드 객체`를 생성한 뒤 원하는 메소드만 호출해주면 된다.

```java
public class HomeTheaterTestDrive {
	public static void main(String[] args) {
		Amplifier amp = new Amplifier("Top-O-Line Amplifier");
		Tuner tuner = new Tuner("Top-O-Line AM/FM Tuner", amp);
		DvdPlayer dvd = new DvdPlayer("Top-O-Line DVD Player", amp);
		CdPlayer cd = new CdPlayer("Top-O-Line CD Player", amp);
		Projector projector = new Projector("Top-O-Line Projector", dvd);
		TheaterLights lights = new TheaterLights("Theater Ceiling Lights");
		Screen screen = new Screen("Theater Screen");
		PopcornPopper popper = new PopcornPopper("Popcorn Popper");
 
		HomeTheaterFacade homeTheater = new HomeTheaterFacade(amp, tuner, dvd, cd, projector, screen, lights, popper);
 
		homeTheater.watchMovie("Raiders of the Lost Ark");
		homeTheater.endMovie();
	}
}
```

## 구현 및 테스트 코드

- https://github.com/PaengE/HeadFirst_DesignPatterns/tree/main/src/headfirst/designpatterns/facade/hometheater

