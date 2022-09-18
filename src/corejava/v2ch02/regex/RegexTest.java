package corejava.v2ch02.regex;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * В этой программе производится проверка на совпадение
 * с регулярным выражением. Для этого следует ввести шаблон
 * и сопоставляемые с ним символьные строки, а для выхода
 * из программы - нажать "Отмена". Если шаблон содержит
 * группы, их границы отображаются при совпадении
 * @version 1.02 2012-06-02
 * @author Cay Horstmann
 */
public class RegexTest {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter pattern: ");
        String patternString = in.nextLine();

        Pattern pattern = Pattern.compile(patternString);

        while (true) {
            System.out.println("Enter string to match: ");
            String input = in.nextLine();
            if (input == null || input.equals("")) return;
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches()) {
                System.out.println("Match");
                int g = matcher.groupCount();
                if (g > 0) {
                    for (int i = 0; i < input.length(); i++) {
                        // ввести любые пустые группы
                        for (int j = 1; j <= g; j++)
                            if (i == matcher.start(j) && i == matcher.end(j))
                                System.out.print("()");
                        // вывести знак ( в начале не пустых групп
                        for (int j = 1; j <= g; j++)
                            if (i == matcher.start(j) && i != matcher.end(j))
                                System.out.print('(');
                        System.out.print(input.charAt(i));
                        // вывести знак ) в конце непустых групп
                        for (int j = 1; j <= g; j++)
                            if (i + 1 != matcher.start(j) && i + 1 == matcher.end(j))
                                System.out.print(')');
                    }
                    System.out.println();
                }
            } else
                System.out.println("No match");
        }
    }
}