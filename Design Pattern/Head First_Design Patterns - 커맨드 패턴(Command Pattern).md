## 디자인 패턴: 커맨드 패턴(Command Pattern)

> 이 포스팅은 `Head First: Design Patterns` 책을 보고, 개인적으로 정리한 포스팅입니다.

## Command Pattern 이란?

> `커맨드 패턴(Command Pattern)`을 이용하면 요구 사항을 객체로 캡슐화 할 수 있으며, 매개변수를 써서 여러 가지 다른 요구사항을 집어넣을 수도 있다. 또한 요청 내역을 큐에 저장하거나 로그로 기록할 수 도 있고, 작업취소 기능도 지원 가능하다.
>
> 작업을 요청한 쪽과 그 작업을 처리하는 쪽을 분리시킬 수 있다는게 핵심이다.
>
> `커맨드 패턴`에서는 `커맨드 객체`라는 일련의 행동을 특정 리시버하고 연결시킴으로써 요구사항을 캡슐화한다. 이렇게 하기 위해서 행동과 리시버를 한 객체에 집어넣고, execute()라는 메소드 하나만 외부에 공개한다.
>
> execute() 메소드 호출에 의해 리시버에서 일련의 작업이 처리되며, 외부에서는 어떤 객체가 리시버 역할을 하는지, 그 리시버에서 실제로 어떤 일을 하는지 알 수 없다. 단지 execute() 메소드를 호출하면 작업이 처리된다는 사실만 알 수 있다.

![command-1](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\command-1.png)

- `Client`는 커맨드 객체 ConcreteCommand를 생성하고 Receiver를 설정한다.
- `ConcreteCommand`는 `커맨드 객체`로, 행동을 캡슐화하고 리시버에 있는 특정 행동을 처리하기 위한 ㄹexecute() 메소드 하나만 제공하며 특정 action과 receiver사이를 연결해 준다. Invoker에서 execute() 호출을 통해 요청을 하면 ConcreteCommand 객체에서 receiver에 있는 메소드를 호출함으로써 그 작업을 처리한다.
- `Invoker`에는 명령이 들어있으며, execute() 메소드를 호출함으로써 `커맨드 객체`에게 특정 작업을 수행해 달라는 요구를 하게 된다.
- `Command`는 모든 커맨드 객체에서 구현해야하는 인터페이스이다. 모든 명령은 execute() 메소드 호출을 통해 수행되며, 이 메소드에서는 Receiver에 특정 작업을 처리하라는 지시를 전달한다.
- `ConcreteCommand`는 `커맨드 객체`로, 특정 action과 receiver사이를 연결해 준다. Invoker에서 execute() 호출을 통해 요청을 하면 ConcreteCommand 객체에서 receiver에 있는 메소드를 호출함으로써 그 작업을 처리한다.

## 버튼이 하나 밖에 없는 리모컨

> 리모컨에 여러 장치들을 제어하는 기능이 있다고 가정하자. 일단 버튼이 하나인 리모컨부터 살펴보자.
>
> 커맨드 패턴을 사용하는 이유는, 리모컨이 여러 장치를 세세히 제어하는 것은 성능상의 이슈도 있고 그다지 좋지 않기 때문이다. 리모컨은 명령만 전달하고, 명령을 처리하는 것은 리시버인 장치에서 하도록 할 수 있다.

예를 들어, 전등 장치인  `Light`가 있다고 가정하자. `Light`는 `on()`과 `off()`메소드만 가지고 있다. 

전등을 키고 싶을 땐, 전등에게 리모컨을 통하여 불을 키라는 명령만 전달하면 될 것이다. 전등이 어떤 방법으로 불을 켜는지는 관심이 없다.

```java
public interface Command {	// 모든 커맨드 객체는 Command 인터페이스를 구현해야 한다.
	public void execute();
}
```

```java
public class LightOnCommand implements Command {	// 전등의 불을 키는 커맨드 객체이다.
	Light light;
  
  public LightOnCommand(Light light){
    this.light = light;
  }
  
  // 리시버 객체(여기서는 전등)의 on()메소드를 호출한다.
  public void execute(){
    light.on();
  }
}
```

```java
public class SimpleRemoteControl {	// 버튼이 하나밖에 없는 리모컨일 때
	Command slot;
	
	public SimpleRemoteControl() {}
  
  public void setCommand(Command command) {
    slot = command;
  }
  
  public void buttonWasPressed() {
    slot.execute();
  }
}
```

## 버튼이 여러 개인 리모컨

> 제어할 수 있는 장치의 개수가 7개이고, 각각 on/off를 할 수 있는 버튼이 두개씩 있다.

### 리모컨 코드

```java
public class RemoteControl {
	Command[] onCommands;
	Command[] offCommands;
	Command undoCommand;
 
	public RemoteControl() {
		onCommands = new Command[7];
		offCommands = new Command[7];
 
		Command noCommand = new NoCommand();
		for(int i=0;i<7;i++) {
			onCommands[i] = noCommand;
			offCommands[i] = noCommand;
		}
		undoCommand = noCommand;
	}
  
  // 슬롯 번호와 그 슬롯에 저장할 on/off 명령을 인자로 받는다.
  // 각 커맨드 객체를 나중에 사용할 수 있도록 배열에 저장한다.
	public void setCommand(int slot, Command onCommand, Command offCommand) {
		onCommands[slot] = onCommand;
		offCommands[slot] = offCommand;
	}
 
	public void onButtonWasPushed(int slot) {
		onCommands[slot].execute();
		undoCommand = onCommands[slot];
	}
 
	public void offButtonWasPushed(int slot) {
		offCommands[slot].execute();
		undoCommand = offCommands[slot];
	}
  
  public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n------ Remote Control -------\n");
		for (int i = 0; i < onCommands.length; i++) {
			sb.append("[slot " + i + "] " + onCommands[i].getClass().getName()
				+ "    " + offCommands[i].getClass().getName() + "\n");
		}
		return stringBuff.toString();
	}
}
```

### 커맨드 클래스

커맨드 클래스는 위에서 사용한 LightOnCommand를 그대로 쓸 수 있다. 

LightOffCommand도 비슷하게 만들면 된다.

```java
public class LightOnCommand implements Command {	// 전등의 불을 키는 커맨드 객체이다.
	Light light;
  
  public LightOnCommand(Light light){
    this.light = light;
  }
  
  // 리시버 객체(여기서는 전등)의 on()메소드를 호출한다.
  public void execute(){
    light.on();
  }
}
```

```java
public class LightOffCommand implements Command {	// 전등의 불을 키는 커맨드 객체이다.
	Light light;
  
  public LightOnCommand(Light light){
    this.light = light;
  }
  
  // 리시버 객체(여기서는 전등)의 off()메소드를 호출한다.
  public void execute(){
    light.off();
  }
}
```

단지 켜고 끄는 것이 아니라 on/off 이후 처리할 작업이 있다면, execute() 메소드 안에 순서대로 작업을 요청하면 된다. 혹은 이후에 나올 `매크로 커맨드`를 사용해보자.

### 리모컨 테스트

> 커맨드 패턴을 적용하였으므로, 아래와 같은 순서로 동작하게 된다.
>
> 1. 리시버들을 생성한다. (여기서는 각 전등, 선풍기 팬, 창고 문 등)
> 2. 각 장치별 커맨드 객체를 생성한다. (여기서는 LightOnCommand, LightOffCommand 등)
> 3. 인보커에 커맨드 객체들을 저장한다. (여기서 인보커는 리모컨)
> 4. 리시버에게 명령을 전달한다. (커맨드 객체를 넘긴다.)

```java
public class RemoteLoader {
 
	public static void main(String[] args) {
		RemoteControl remoteControl = new RemoteControl();
 
    // 리시버 생성
		Light livingRoomLight = new Light("Living Room");
		Light kitchenLight = new Light("Kitchen");
		CeilingFan ceilingFan= new CeilingFan("Living Room");
		GarageDoor garageDoor = new GarageDoor("");
		Stereo stereo = new Stereo("Living Room");
  
    // 각 장치별 커맨드 객체 생성
		LightOnCommand livingRoomLightOn = new LightOnCommand(livingRoomLight);
		LightOffCommand livingRoomLightOff = new LightOffCommand(livingRoomLight);
		LightOnCommand kitchenLightOn = new LightOnCommand(kitchenLight);
		LightOffCommand kitchenLightOff = new LightOffCommand(kitchenLight);
  
		CeilingFanOnCommand ceilingFanOn = new CeilingFanOnCommand(ceilingFan);
		CeilingFanOffCommand ceilingFanOff = new CeilingFanOffCommand(ceilingFan);
 
		GarageDoorUpCommand garageDoorUp = new GarageDoorUpCommand(garageDoor);
		GarageDoorDownCommand garageDoorDown = new GarageDoorDownCommand(garageDoor);
 
		StereoOnWithCDCommand stereoOnWithCD = new StereoOnWithCDCommand(stereo);
		StereoOffCommand  stereoOff = new StereoOffCommand(stereo);
 
    // 인보커에 커맨드 객체를 로드
		remoteControl.setCommand(0, livingRoomLightOn, livingRoomLightOff);
		remoteControl.setCommand(1, kitchenLightOn, kitchenLightOff);
		remoteControl.setCommand(2, ceilingFanOn, ceilingFanOff);
		remoteControl.setCommand(3, stereoOnWithCD, stereoOff);
  
		System.out.println(remoteControl);
 
    // 인보커를 통해 리시버에게 커맨드 객체를 전달
		remoteControl.onButtonWasPushed(0);
		remoteControl.offButtonWasPushed(0);
		remoteControl.onButtonWasPushed(1);
		remoteControl.offButtonWasPushed(1);
		remoteControl.onButtonWasPushed(2);
		remoteControl.offButtonWasPushed(2);
		remoteControl.onButtonWasPushed(3);
		remoteControl.offButtonWasPushed(3);
	}
}

/** 출력문
    ------ Remote Control -------
    [slot 0] headfirst.designpatterns.command.remote.LightOnCommand    headfirst.designpatterns.command.remote.LightOffCommand
    [slot 1] headfirst.designpatterns.command.remote.LightOnCommand    headfirst.designpatterns.command.remote.LightOffCommand
    [slot 2] headfirst.designpatterns.command.remote.CeilingFanOnCommand    headfirst.designpatterns.command.remote.CeilingFanOffCommand
    [slot 3] headfirst.designpatterns.command.remote.StereoOnWithCDCommand    headfirst.designpatterns.command.remote.StereoOffCommand
    [slot 4] headfirst.designpatterns.command.remote.NoCommand    headfirst.designpatterns.command.remote.NoCommand
    [slot 5] headfirst.designpatterns.command.remote.NoCommand    headfirst.designpatterns.command.remote.NoCommand
    [slot 6] headfirst.designpatterns.command.remote.NoCommand    headfirst.designpatterns.command.remote.NoCommand

    Living Room light is on
    Living Room light is off
    Kitchen light is on
    Kitchen light is off
    Living Room ceiling fan is on high
    Living Room ceiling fan is off
    Living Room stereo is on
    Living Room stereo is set for CD input
    Living Room Stereo volume set to 11
    Living Room stereo is off
*/
```

### NoCommand 클래스

위에서 나온 NoCommand 객체는 일종의 널 객체(Null Object)이다. 리턴할 객체는 없지만 클라이언트 쪽에서 null을 처리하지 않도록 하고 싶을 때 널 객체를 사용하면 좋다.

특정 슬롯을 사용하려고 할 때 사용 중인 슬롯인지를 체크하는 것 보다, 빈 슬롯임을 나타낼 수 있는 NoCommand 와 같은 객체로 초기화 해두면 된다.

### 각 리시버들에게 모두 적용되는 기능을 추가할 때

Command 인터페이스에 적용될 메소드를 추가하여, 모든 커맨드 객체가 이를 구현하도록 만들면 된다.

예를 들어, 지금은 execute() 메소드 밖에 없지만 직전 작업을 취소하는 undo() 메소드 등을 추가할 수도 있다.

### 매크로 커맨드

> 매크로 커맨드는 여러 개의 커맨드를 한 데 묶어놓은 커맨드이다.

위에서 여러 작업을 순서대로 진행하려면 execute() 안에 코드를 추가하여야 했다.

그보다는 매크로 커맨드를 사용하는게 편리할 것이다.

매크로 커맨드는 커맨드들을 원하는 순서대로 나열하여 한 곳에 저장한다. 리시버에게 전달되면, 리시버는 배열을 참조하여 순서대로 작업을 수행해 나간다.

```java
public class MacroCommand implements Command {
	Command[] commands;
 
	public MacroCommand(Command[] commands) {
		this.commands = commands;
	}
 
	public void execute() {
		for (int i = 0; i < commands.length; i++) {
			commands[i].execute();
		}
	}
 
	public void undo() {
		for (int i = commands.length -1; i >= 0; i--) {
			commands[i].undo();
		}
	}
}
```

위에서 보는 것처럼 커맨드 배열을 받아 순서대로 작업을 수행하는 것을 볼 수 있다.

```java
public class RemoteLoader {

	public static void main(String[] args) {

    // 인보커를 생성한다.
		RemoteControl remoteControl = new RemoteControl();

    // 리시버들을 생성한다.
		Light light = new Light("Living Room");
		TV tv = new TV("Living Room");
		Stereo stereo = new Stereo("Living Room");
		Hottub hottub = new Hottub();
 
    // 커맨드 객체들을 생성한다.
		LightOnCommand lightOn = new LightOnCommand(light);
		StereoOnCommand stereoOn = new StereoOnCommand(stereo);
		TVOnCommand tvOn = new TVOnCommand(tv);
		HottubOnCommand hottubOn = new HottubOnCommand(hottub);
		LightOffCommand lightOff = new LightOffCommand(light);
		StereoOffCommand stereoOff = new StereoOffCommand(stereo);
		TVOffCommand tvOff = new TVOffCommand(tv);
		HottubOffCommand hottubOff = new HottubOffCommand(hottub);

    // 커맨드 객체들을 작업 순서대로 하나의 커맨드 배열로 묶는다.
		Command[] partyOn = { lightOn, stereoOn, tvOn, hottubOn};
		Command[] partyOff = { lightOff, stereoOff, tvOff, hottubOff};
  
    // 커맨드 배열을 매크로 커맨드로 생성한다.
		MacroCommand partyOnMacro = new MacroCommand(partyOn);
		MacroCommand partyOffMacro = new MacroCommand(partyOff);
 
    // 인보커에 매크로 커맨드를 로드한다.
		remoteControl.setCommand(0, partyOnMacro, partyOffMacro);
  
		System.out.println(remoteControl);
		System.out.println("--- Pushing Macro On---");
		remoteControl.onButtonWasPushed(0);
		System.out.println("--- Pushing Macro Off---");
		remoteControl.offButtonWasPushed(0);
	}
}

/** 출력문
			------ Remote Control -------
      [slot 0] headfirst.designpatterns.command.party.MacroCommand    headfirst.designpatterns.command.party.MacroCommand
      [slot 1] headfirst.designpatterns.command.party.NoCommand    headfirst.designpatterns.command.party.NoCommand
      [slot 2] headfirst.designpatterns.command.party.NoCommand    headfirst.designpatterns.command.party.NoCommand
      [slot 3] headfirst.designpatterns.command.party.NoCommand    headfirst.designpatterns.command.party.NoCommand
      [slot 4] headfirst.designpatterns.command.party.NoCommand    headfirst.designpatterns.command.party.NoCommand
      [slot 5] headfirst.designpatterns.command.party.NoCommand    headfirst.designpatterns.command.party.NoCommand
      [slot 6] headfirst.designpatterns.command.party.NoCommand    headfirst.designpatterns.command.party.NoCommand
      [undo] headfirst.designpatterns.command.party.NoCommand

      --- Pushing Macro On---
      Light is on
      Living Room stereo is on
      Living Room TV is on
      Living Room TV channel is set for DVD
      Hottub is heating to a steaming 104 degrees
      Hottub is bubbling!
      --- Pushing Macro Off---
      Light is off
      Living Room stereo is off
      Living Room TV is off
      Hottub is cooling to 98 degrees
*/
```

## 구현 및 테스트 코드

- https://github.com/PaengE/HeadFirst_DesignPatterns/tree/main/src/headfirst/designpatterns/command