## Problem : https://programmers.co.kr/learn/courses/30/lessons/17686

## Approach

2018 KAKAO BLIND RECRUITMENT 문제였다.

자바로 풀이하려면 Comparator를 활용하여 정렬의 기준을 override 해주면 된다.

> 우선 숫자가 나오기 전까지 문자열을 가지고 알파벳 정렬을 수행한 후, 
>
> 만약 같다면 숫자 부분을 가지고 숫자 정렬을 수행한다.

## Code

```java
import java.util.Arrays;
import java.util.Comparator;

public class FilenameSort {
    public static void main(String[] args) {
        String[] files = {"img12.png", "img10.png", "img02.png", "img1.png", "IMG01.GIF", "img2.JPG"};
        FilenameSort fs = new FilenameSort();

        String[] ans = fs.solution(files);
        for (int i = 0; i < ans.length; i++) {
            System.out.println(ans[i] + " ");
        }
    }

    public String[] solution(String[] files) {
        Arrays.sort(files, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String s1 = o1.split("[0-9]")[0];
                String s2 = o2.split("[0-9]")[0];

                int result = s1.toLowerCase().compareTo(s2.toLowerCase());

                if (result == 0) {
                    result = getNum(o1, s1) - getNum(o2, s2);
                }

                return result;
            }
        });
        return files;
    }

    static int getNum(String s, String h) {
        s = s.replace(h, "");
        String result = "";
        for(char c : s.toCharArray()) {
            if(Character.isDigit(c) && result.length() < 5 ) {
                result += c;
            }else
                break;
        }
        return Integer.valueOf(result);
    }
}

```

