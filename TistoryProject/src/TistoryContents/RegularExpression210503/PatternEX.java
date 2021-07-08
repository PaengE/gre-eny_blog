package TistoryContents.RegularExpression210503;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternEX {
    public static void main(String[] args) {
        String regex1 = "^\\d*$"; // 문자열이 숫자로만 구성
        String s1 = "123456789";
        String s2 = "qwerty123@gmail.com";

        System.out.println(Pattern.matches(regex1, s1));
        System.out.println(Pattern.matches(regex1, s2));

        Pattern p = Pattern.compile(regex1);
        System.out.println("p.pattern() = " + p.pattern());


        String number = "010-1234-5678";
        Pattern pa = Pattern.compile("^01(?:0|1|[6-9])-(\\d{3}|\\d{4})-\\d{4}$");
        Matcher ma = pa.matcher(number);

        System.out.println(ma.matches());
        System.out.println(ma.groupCount());
        System.out.println(ma.group(0) + " " + ma.group(1));


    }
}
