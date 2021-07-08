## 정규 표현식(Regular Expression)

> 위키피디아에서는 `정규표현식`을 `특정한 규칙을 가진 문장려의 집합을 표현하는데 사용하는 형식 언어`라고 정의하고 있다.
>
> 자바에서는 다음과 같은 곳에서 `정규표현식 regex`을 사용한다.

### 자바의 정규표현식

정규표현식에는 여러 종류가 있지만 가장 대표적으로 두 개의 정규표현식이 있다.

- UNIX 계열의 표준 정규표현식인 `POSIX 정규표현식`
- POSIX 정규표현식에서 확장된 `Perl 방식의 정규표현식`

자바에서는 Perl 방식과 유사한 방식을 선택하고 있지만, Perl 방식과 완전히 똑같은 것은 아니다.

그 차이점은 oracle docs에 수록되어 있다.

## 내장 메소드의 파라미터

> 자바의 `String` 클래스의  내장 메소드를 살펴보면 인자로 `String regex` 같은 것을 볼 수 있다.
>
> ![RE-1](C:\Users\82102\OneDrive\티스토리\Java\image\RE-1.png)
>
> 여기서 `regex`가 `정규표현식`을 의미하며, 이를 인자로 받아 처리를 하는 메소드이다.
>
> 다음과 같은 것들이 있다. `String` 객체에 대하여 사용할 수 있다.

- `boolean matches(String regex)`: 인자로 주어진 정규식과 매치되는 값이 있는지 확인하여 true 혹은 false를 반환한다. 이 함수를 이용하여 간단하게 정규 표현식을 test할 수 있다.
- `String replaceAll(String regex, String replacement)` : 문자열 내에 인자로 주어진 정규식과 매치되는 모든 substring을 replacement로 바꾼다.
- `String[] split(String regex)` : 인자로 주어진 정규식과 매치되는 문자열을 구분자로 하여, 구분자를 기준으로 나뉜 문자열들을 문자열의 배열로 반환한다.

## 정규 표현식을 다루는 클래스

> 자바에서는 `java.util.regex` 에서 `Pattern`과 `Matcher` 클래스를 지원한다.
>
> Pattern 객체를 생성하여 compile 을 실행한다면, 그 정규표현식을 검색하고 싶을 때 다시 컴파일하지 않아도 되기에 속도를 높일 수 있다.
>
> 따라서 재사용이 많을 시에는 compile 을 하는 것이 더 효율적이고, 아니라면 compile 을 하지 말고 Pattern 클래스의 matches() 메소드를 이용하는 것이 효율적이다.

### Pattern 클래스

> 정규표현식의 단일 사용이 목적이라면, `java.util.regex.Pattern` 클래스의 `matches()` 메소드를 사용하는 것이 좋다. `matches()`과 `compile()` 메소드는 static method 이다.

- `Pattern.matches(String regex, CharSequence input)`: input이 regex과 일치한다면 true, 아니라면 false를 리턴한다.
- `Pattern.compile(String regex)`: 주어진 regex 로부터 패턴을 만든다.
- `String pattern()`: 컴파일된 정규표현식을 String 형태로 리턴한다.
- `boolean matcher(CharSequence input)`: 컴파일된 정규표현식 패턴에 input을 적용시킨 `Matcher`객체를 리턴한다. (Matcher 객체는 Matcher의 메소드를 이용하여 여러 정보를 얻을 수 있다.)

```java
String regex1 = "^[0-9]*$"; // 문자열이 숫자로만 구성
String s1 = "123456789";
String s2 = "qwerty123@gmail.com";

System.out.println(Pattern.matches(regex1, s1));
System.out.println(Pattern.matches(regex1, s2));

Pattern p = Pattern.compile(regex1);
System.out.println("p.pattern() = " + p.pattern());

/* 출력
		true
    false
    p.pattern() = ^[0-9]*$
*/
```

### Matcher 클래스

> `Matcher` 클래스는 대상 문자열의 패턴을 해석하고 주어진 패턴과 일치하는지 판별할 때 주로 사용된다.
>
> `Pattern 객체`의 `matcher(CharSequence input)` 메소드를 이용하여 생성할 수 있다. `CharSequence` 라는 인터페이스를 사용함으로써 다양한 형태의 입력데이터로부터 문자 단위의 매칭 기능을 지원 받을 수 있다.

- `boolean matches()`: 대상 전체문자열과 패턴이 일치할 경우 true를, 아니라면 false를 리턴한다.
- `boolean find()`: 대상 문자열과 패턴이 일치하는 경우 true를 리턴하고, 그 위치로 이동한다.
- `boolean find(int start)`: start 위치 이후부터 `find()`를 수행한다.
- `int start()`: 패턴과 매칭되는 문자열의 시작위치를 리턴한다.
- `int start(int group)`: 지정된 그룹이 패턴과 매칭되는 시작위치를 리턴한다.
- `int end()`: 패턴과 매칭되는 문자열의 끝 다음위치를 리턴한다.
- `int end(int group)`: 지정된 그룹이 패턴과 매칭되는 끝 다음위치를 리턴한다.
- `String group()`: 패턴과 매칭된 부분을 리턴한다.
- `String group(int group)`: 패턴과 매칭된 부분 중 group번 그룹핑 매칭부분을 리턴한다.
- `int groupCount()`: 패턴 내 그룹핑한(괄호 지정) 전체 개수를 리턴한다.

```java
String regex1 = "\\w+@(gmail.com)"; // 이메일 형식 중 gmail.com으로 끝나는 것만
String s1 = "qwe123@gmail.com|asd234@naver.com|zxc135@gmail.com";

Pattern p = Pattern.compile(regex1);
Matcher m = p.matcher(s1);

System.out.println(m.matches());

while (m.find()) {
	System.out.println(m.group() + ": " + m.start() + " ~ " + m.end());
}

System.out.println(m.groupCount());

/* 출력
		false
    qwe123@gmail.com: 0 ~ 16
    zxc135@gmail.com: 34 ~ 50
    1
*/
```

여기서 그룹의 개수는 1개이다. (gmail.com) 

### 자주쓰이는 패턴

1. 숫자만 : ^[0-9]*$     (== ^\d$)
2. 영문자만 : ^[a-zA-Z]*$
3. 한글만 : ^[가-힣]*$
4. 영어 & 숫자만 : ^[a-zA-Z0-9]*$     (== ^\w$)
5. E-Mail : ^[a-zA-Z0-9]+@[a-zA-Z.0-9]+$
6. 휴대폰 : ^01(?:0|1|[6-9]) - (?:\d{3}|\d{4}) - \d{4}$
7. 일반전화 : ^\d{2.3} - \d{3,4} - \d{4}$
8. 주민등록번호 : \d{6} \- [1-4]\d{6}
9. IP 주소 : ([0-9]{1,3}) \. ([0-9]{1,3}) \. ([0-9]{1,3}) \. ([0-9]{1,3})

```java
String number = "010-1234-5678";
Pattern pa = Pattern.compile("^01(?:0|1|[6-9])-(\\d{3}|\\d{4})-\\d{4}$");
Matcher ma = pa.matcher(number);

System.out.println(ma.matches());
System.out.println(ma.groupCount());
System.out.println(ma.group(0) + " " + ma.group(1));

/* 출력
		true
    1
    010-1234-5678 1234
*/
```

- `(?:regex)`: 이 그룹은 캡쳐되지 않으며, 따라서 순차적으로 그룹화된 그룹에 포함되지 않는다.

## 정규표현식 문법

### 메타문자(metacharacter)

> 먼저 `정규표현식`의 문법을 살펴보기 전에, 정규표현식의 문법에 사용되는 `메타문자`에 대해 먼저 알아보자. 

- `.`: 임의의 한 문자를 나타낸다. (알파벳, 숫자, 특수 문자 등)
- `^`: 문자열의 시작을 나타낸다. (대괄호([]) 안에서의 `^`는 not을 의미한다.)
- `$`: 문자열의 끝을 나타낸다.
- `\`: 메타문자의 성질을 없애고, 메타문자 자체를 검색하고 싶을 때 사용한다.
- `|`: or 연산자의 개념이다.
- `[]`: '[]' 안에는 `범위와 집합`의 개념이 사용되는데, `'[]' 자체는 하나의 문자를 가리킨다.`
  - `[abc]`: a, b, c 중 하나
  - `[^abc]`: a, b, c 를 제외한 문자 중 하나
  - `[a-zA-Z]`: 모든 알파벳 중 하나
  - `[a-d[m-p]]`: a~d 혹은 m~p 중 하나
  - `[a-z&&[def]]`: a~z 중에서 d, e, f 중 하나
  - `[a-z&&[^bc]]`: a~z 중에서 b, c 를 제외한 문자들 중 하나
  - `[a-z&&[^m-p]]`: a~z 중에서 m~p 를 제외한 문자들 중 하나
- `?`: 기호 바로 앞의 문자가 0번 또는 1번 반복된다.
- `+`: 기호 바로 앞의 문자가 1번 이상 반복된다.
- `*`: 기호 바로 앞의 문자가 0번 이상 반복된다.
- `{}`: '{}' 안에는 숫자(들)이 들어가며, '{}' 바로 앞의 문자의 개수를 나타낸다.
  - `{n}`: 기호 바로 앞의 문자가 정확히 `n번` 나온다.
  - `{n, m}`: 기호 바로 앞의 문자가 `n번 이상 m번 이하` 나온다.
  - `{n, }`: 기호 바로 앞의 문자가 `n번 이상` 나온다.
  - `{, m}`: 기호 바로 앞의 문자가 `m번 이하` 나온다.
- `()`: '()' 안의 문자열은 하나로 취급된다.

### 미리 정의된 문자(predefined character classes)

> oracle 에서는 아래의 내용을 `predefined character classes`라고 소개한다.

- `.`: 임의의 한 문자
- `\s`: 모든 공백문자 (`\t` - 매치 됨, `\n` - 매치 됨)
- `\S`: 모든 공백문자를 제외한 나머지 문자
- `\h`: 수평 공백문자 (`\t` - 매치 됨, `\n` - 매치 안 됨)
- `\H`: 수평 공백문자를 제외한 나머지 문자
- `\v`: 수직 공백문자 (`\t` - 매치 안 됨, `\n` - 매치 됨)
- `\V`: 수직 공백문자를 제외한 나머지 문자
- `\w`: 알파벳, 숫자, 언더라인(_) 중 한 문자 ( == `[a-zA-Z_0-9]`)
- `\W`: 알파벳, 숫자, 언더라인(_) 을 제외한 문자들 중 한 문자 ( == `[^a-zA-Z_0-9]`)
- `\d`: 숫자 ( == `[0-9]`)
- `\D`: 숫자를 제외한 모든 문자 ( == `[^0-9]`)
- `\b`: 단어의 경계
- `\B`: 단어가 아닌 것에 대한 경계

## Refer to

- https://docs.oracle.com/javase/tutorial/essential/regex/index.html
- https://ko.wikipedia.org/wiki/%EC%A0%95%EA%B7%9C_%ED%91%9C%ED%98%84%EC%8B%9D
- https://medium.com/depayse/java-%EC%A0%95%EA%B7%9C-%ED%91%9C%ED%98%84%EC%8B%9D-regular-expression-%EC%9D%98-%EC%9D%B4%ED%95%B4-31419561e4eb
- https://enterkey.tistory.com/353

