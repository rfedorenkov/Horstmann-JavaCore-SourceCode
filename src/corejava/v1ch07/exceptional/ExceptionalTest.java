package corejava.v1ch07.exceptional;

import java.util.Date;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 * @version 1.11 2012-01-26
 * @author Cay Horstmann
 */
public class ExceptionalTest {
    public static void main(String[] args) {
        int i = 0;
        int ntry = 10_000_000;
        Stack<String> s = new Stack<>();
        long s1;
        long s2;

        // проверка стека на пустоту n раз
        System.out.println("Testing for empty stack");
        s1 = new Date().getTime();
        for (i = 0; i <= ntry; i++)
            if (!s.empty()) s.pop();
        s2 = new Date().getTime();
        System.out.println((s2 - s1) + " milliseconds");

        // вытолкнуть пустой стек n раз и перехватить полученное исключение
        System.out.println("Catching EmptyStackException");
        s1 = new Date().getTime();
        for (i = 0; i <= ntry; i++) {
            try {
                s.pop();
            } catch (EmptyStackException ignored) {
            }
        }
        s2 = new Date().getTime();
        System.out.println((s2 - s1) + " milliseconds");
    }
}