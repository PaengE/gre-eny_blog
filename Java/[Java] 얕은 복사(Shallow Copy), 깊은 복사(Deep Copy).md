## Java 얕은 복사(Shallow Copy), 깊은 복사(Deep Copy)

> 배열의 복사에는 `얕은 복사(Shallow Copy)`와 `깊은 복사(Deep Copy)`가 있다.

두 복사의 큰 차이는 다음과 같다. 복사되는 배열 a와 a를 복사한 배열 b가 있을 때, 

- `얕은 복사`는 하나의 배열의 주소를 a와 b가 모두 가리키고 있다. 같은 배열을 가리키고 있기에, 한쪽 배열에서 수정이 일어나면 나머지 다른쪽 배열에서도 수정이 반영된다.
- `깊은 복사`는 a와 b 모두 각각 다른 배열을 가리키고 있다. 따라서, 한쪽 배열에서 수정이 일어난다 하여도, 다른쪽 배열에 아무런 영향을 끼치지 못한다.

## 얕은 복사(Shallow Copy)

> 대입 연산자 `=`는 `얕은 복사`를 수행한다. 1차원 배열이든 다차원 배열이든 똑같다. 다음 예제를 살펴보자.

```java
int[] arr1 = new int[2];	// [0, 0]
int[] arr2 = arr1;				// [0, 0]

arr2[1] = 2;
System.out.println(arr1[0] + " " + arr1[1]);	// 0 1
System.out.println(arr2[0] + " " + arr2[1]);	// 0 1
```

### 메소드로 넘겨주는 인자에서의 배열

배열을 메소드의 인자로 넘겨주는 경우가 있다. 이럴 때에는 기본적으로 전부 `얕은 복사`가 일어난다. 배열의 주소값을 메소드로 넘겨주는 것이다.

이를 이용하여 배열을 초기화하거나, 배열의 값을 수정할 때에 따로 return 값을 명시하지 않아도 된다. 다음 예를 보자.

```java
int[] arr = new int[3];	// [0, 0, 0]

void modifyArray(){
	Arrays.fill(arr, 2);
}

System.out.println(arr[0] + " " + arr[1] + " " + arr[2]);	// 2 2 2
```

## 깊은 복사(Deep Copy)

> 자바에서는 기본적으로 2차원 배열의 깊은 복사를 완벽히 지원해주는 표준형 라이브러리가 없다.
>
> 예제를 살펴보자.

### 1차원 배열의 깊은 복사

- `기본 자료형 1차원 배열`의 경우, `clone()` 메소드를 활용하여 `깊은 복사`를 수행할 수 있다.
- 아니면 반복문을 돌려 직접 값을 대입하는 방법도 있다.
- `기본 자료형이 아닌 1차원 배열`의 경우(객체를 담고 있는 배열과 같은), `clone()`메소드로는 깊은 복사를 할 수 없다. (객체는 값이 아닌 주소를 가지고 있기 때문이다.)
- 객체를 담고있는 배열의 경우에는, 복사를 할 배열의 객체들의 내부 값들을 참조하여 직접 new 연산자로 객체를 생성하여 담아야 한다.

```java
int[] arr1 = new int[2];	// [0, 0]
int[] arr2 = arr1.clone()	// [0, 0]

arr2[1] = 2;
System.out.println(arr1[0] + " " + arr1[1]);	// 0 0
System.out.println(arr2[0] + " " + arr2[1]);	// 0 1

// 객체 배열의 경우
Point[] arr3 = new Point[2];
Point[] arr4 = new Point[arr3.length];
for (int i = 0; i < arr3.length; i++){
  arr4[i] = new Point(arr3.x, arr3.y);
}    
```

### 2차원 배열의 깊은 복사

- `기본 자료형 2차원 배열`의 경우, `System.arraycopy()` 메소드를 사용하여 깊은 복사를 수행할 수 있다.
- `System.arraycopy(int src, int srcPos, int dest, int destPos, int length)`
  - `arraycopy()`는 원하는 부분의 데이터만, 원하는 위치에 복사하기 위해 사용된다.
  - src = 복사를 할 배열
  - srcPos = 복사를 시작할 처음 위치
  - dest = 복사된 값을 저장할 배열
  - destPos = 복사된 값을 저장할 처음 위치
  - length = 복사할 배열 요소의 수
- 중요한 것은 2차원 배열을 특정 값으로 초기화 할 때처럼, 2차원 배열의 각 1차원 배열들에게 arraycopy() 메소드를 적용해야 한다는 것이다.

```java
int[][] src = new int[2][2];
int[][] dst = new int[2][2];

for (int[] tmp : src){
	System.arraycopy(src, 0, dst, 0, src.length);
}
```

- 아니면 이중 반복문으로 직접 값을 대입시키는 방법이 있다.
- `기본 자료형이 아닌 2차원 배열`의 경우(객체를 담은 2차원 배열), `arraycopy()` 메소드는 사용하지 못한다. 1차원 객체 배열처럼, 각 객체의 값을 참조하여 new 연산자로 객체 생성을 한 뒤, 직접 대입해줘야 한다.