## 디자인 패턴: 프록시 패턴(Proxy Pattern)

> 이 포스팅은 `Head First: Design Patterns` 책을 보고, 개인적으로 정리한 포스팅입니다.

## Proxy Pattern 이란?

> `프록시 패턴(Proxy Pattern)` 은 어떤 객체에 대한 접근을 제어하기 위한 용도로, 대리인이나 대변인에 해당하는 객체를 제공하는 패턴이다.
>
> 프록시 패턴을 사용하면 원격 객체라든가 생성하기 힘든 객체, 보안이 중요한 객체와 같은 다른 객체에 대한 접근을 제어하는 대변자 객체를 만들 수 있다.
>
> 먼저 Proxy Pattern에서 사용되는 용어와 방법들을 알아보고, 예제를 들겠다.

### 프록시에서 접근을 제어하는 방법

- `원격 프록시(remote proxy)`를 써서 원격 객체에 대한 접근을 제어할 수 있다.
- `가상 프록시(virtual proxy)`를 써서 생성하기 힘든 자원에 대한 접근을 제어할 수 있다.
- `보호 프록시(protection proxy)`를 써서 접근 권한이 필요한 자원에 대한 접근을 제어할 수 있다.

![proxy-1](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\proxy-1.png)

## 가상 프록시(Virtual Proxy)

> `가상 프록시`는 생성하는 데에 많은 비용이 드는 객체를 대신하는 역할을 맡는다.

![proxy-4](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\proxy-4.png)

실제로 진짜 객체가 필요하게 되기 전까지 객체의 생성을 미루게 해주는 기능을 제공하기도 하며, 객체 생성 전이나 생성 도중에 객체를 대신하기도 한다. 

객체 생성이 완료되면 그냥 RealSubject에 요청을 직접 전달해 준다.

## CD 앨범 커버 뷰어

> CD 앨범 커버를 보여주는 뷰어를 만드려고 한다.
>
> 가상 프록시를 이용하여 아이콘 대신 백그라운드에서 네트워크를 통하여 이미지를 불러오는 작업을 처리하고, 이미지를 완전히 가져오기 전까지는 별도 메시지를 출력하게 하려고 한다. 일단 이미지 로딩이 끝나고 나면 프록시에서는 아이콘 객체한테 모든 작업을 맡긴다.

### CD 앨범 커머 가상 프록시 설계

> `가상 프록시`는 네트워크를 통해 연결되어 다른 위치에 있는 객체를 대신하는 용도로 쓰이는 것이 아니라, 생성하는 데 많은 비용이 필요한 객체(아이콘용 데이터를 네트워크를 통해서 가져와야 하기 때문에 시간이 많이 걸릴 수 있다.)를 숨기기 위한 용도이다.

![proxy-5](C:\Users\82102\OneDrive\티스토리\Design Pattern\image\proxy-5.png)

- 사용자 인터페이스에 이미지를 표시해주는 Swing의 Icon 인터페이스
- 이미지를 화면에 표시해주는 javax.swing.ImageIcon 클래스
- `ImageProxy`는 처음에 화면에 간단한 메시지를 표시해 주다가, 이미지 로딩이 끝나고 나면 디스플레이 작업을 ImageIcon에 위임해서 이미지를 표시하는 기능을 맡고 있는 `프록시`이다.

### ImageProxy 작동 방법

1. ImageProxy에서는 우선 ImageIcon을 생성하고 네트워크 URL로부터 이미지를 불러온다.
2. 이미지를 가져오는 동안에는 "~~~" 라는 메시지를 화면에 표시한다.
3. 이미지 로딩이 끝나면 paintIcon(), getWidth(), getHeight()를 비롯한 모든 메소드 호출을 ImageIcon 객체한테 넘긴다.
4. 사용자가 새로운 이미지를 요청하면 프록시를 새로만들고 위의 과정을 새로 진행한다.

### ImageProxy 코드

```java
import java.net.*;
import java.awt.*;
import javax.swing.*;

class ImageProxy implements Icon {
	volatile ImageIcon imageIcon;
	final URL imageURL;
	Thread retrievalThread;
	boolean retrieving = false;
     
	public ImageProxy(URL url) { imageURL = url; }
     
	public int getIconWidth() {
		if (imageIcon != null) {
            return imageIcon.getIconWidth();
        } else {
			return 800;
		}
	}
 
	public int getIconHeight() {
		if (imageIcon != null) {
            return imageIcon.getIconHeight();
        } else {
			return 600;
		}
	}
	
	synchronized void setImageIcon(ImageIcon imageIcon) {
		this.imageIcon = imageIcon;
	}
     
	public void paintIcon(final Component c, Graphics  g, int x,  int y) {
		if (imageIcon != null) {
			imageIcon.paintIcon(c, g, x, y);
		} else {
			g.drawString("Loading CD cover, please wait...", x+300, y+190);
			if (!retrieving) {
				retrieving = true;

				retrievalThread = new Thread(new Runnable() {
					public void run() {
						try {
							setImageIcon(new ImageIcon(imageURL, "CD Cover"));
							c.repaint();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				retrievalThread.start();
			}
		}
	}
}
```

- `imageIcon`은 이미지 로딩이 끝났을 때 실제 이미지를 화면에 표시하기 위한 진짜 아이콘 객체이다.
- 이미지 URL 을 생성자에 전달함으로써, 로딩이 끝나면 이 URL에 있는 이미지가 화면에 표시된다.
- `paintIcon()`은 아이콘을 화면에 표시할 때 호출된다. 
- `retrivalThread` 부분은 진짜 아이콘 이미지를 로딩하는 부분이며, IconImage를 이용하여 이미지를 로딩하는 과정은 동기화되어 진행된다. 이미지 로딩이 끝나기 전까지는 ImageIcon 생성자에서 아무 것도 리턴하지 않는다. 따라서, 화면을 갱신할 수 없고, 아무 메시지도 출력할 수 없기 때문에, `비동기 방식`으로 작업을 처리해야 한다.
  - 사용자 인터페이스가 죽지 않도록 별도의 쓰레드에서 이미지를 가져온다. (repaint() 메소드는 한 쓰레드에서만 호출하기 때문에, thread-safe 하다.)
  - 이 쓰레드 내에서 Icon 객체의 인스턴스를 생성했다. 이미지가 완전히 로딩되어야만 생서앚에서 객체를 리턴한다.
  - 이미지가 확보되고 나면, repaint() 메소드를 호출하여 화면을 갱신한다.

## 프록시와 데코레이터의 용도

- `프록시`: 객체에 대한 접근을 제어한다. 그리고 그 객체를 대변해 준다.
- `데코레이터`: 객체에 행동을 추가한다.

## 구현 및 테스트 코드

- https://github.com/PaengE/HeadFirst_DesignPatterns/tree/main/src/headfirst/designpatterns/proxy/virtualproxy

