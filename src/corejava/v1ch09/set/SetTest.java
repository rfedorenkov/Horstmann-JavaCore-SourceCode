package corejava.v1ch09.set;

import java.util.*;

/**
 * В этой программе выводятся все неповторяющиеся слова,
 * введенные в множество из стандартного потока System.in
 * @version 1.12 2015-06-21
 * @author Cay Horstmann
 */
public class SetTest {
    public static void main(String[] args) {
        Set<String> words = new HashSet<>();
        // объект типа HashSet, реализующий хеш-множество
        long totalTime = 0;

        try (Scanner in = new Scanner(System.in)) {
            while (in.hasNext()) {
                String word = in.next();
                long callTime = System.currentTimeMillis();
                words.add(word);
                callTime = System.currentTimeMillis() - callTime;
                totalTime += callTime;
            }
        }

        Iterator<String> iter = words.iterator();
        for (int i = 1; i <= 20 && iter.hasNext(); i++)
            System.out.println(iter.next());
        System.out.println(". . .");
        System.out.println(words.size() + " distinct words. " + totalTime + " milliseconds.");
    }
}