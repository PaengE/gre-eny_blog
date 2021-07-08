package TistoryContents.RegularExpression210503;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherEx {
    public static void main(String[] args) {
        String regex1 = "\\w+@(gmail.com)"; // 문자열이 숫자로 구성
        String s1 = "qwe123@gmail.com|asd234@naver.com|zxc135@gmail.com";

        Pattern p = Pattern.compile(regex1);
        Matcher m = p.matcher(s1);

        System.out.println(m.matches());

        while (m.find()) {
            System.out.println(m.group() + ": " + m.start() + " ~ " + m.end());
        }

        System.out.println(m.groupCount());
    }
}
