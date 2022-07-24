package corejava.v1ch09.linkedList;

import java.util.*;

/**
 * В этой программе демонстрируются операции со связными списками
 * @version 1.11 2012-01-26
 * @author Cay Horstmann
 */
public class LinkedListTest {

    public static void main(String[] args) {
        List<String> a = new LinkedList<>();
        a.add("Amy");
        a.add("Carl");
        a.add("Erica");

        List<String> b = new LinkedList<>();
        b.add("Bob");
        b.add("Doug");
        b.add("Frances");
        b.add("Gloria");

        // объединить слова из связных списков b и a
        ListIterator<String> aIter = a.listIterator();
        Iterator<String> bIter = b.iterator();

        while (bIter.hasNext()) {
            if (aIter.hasNext()) aIter.next();
            aIter.add(bIter.next());
        }

        System.out.println(a);

        // удалить каждое второе слово из связного списка b
        bIter = b.iterator();
        while (bIter.hasNext()) {
            bIter.next(); // пропустить один элемент
            if (bIter.hasNext()) {
                bIter.next(); // перейти к следующему элементу
                bIter.remove(); // удалить этот элемент
            }
        }

        System.out.println(b);

        // групповая операция удаления из связного списка a
        // всех слов, составляющих связный список b
        a.removeAll(b);

        System.out.println(a);
    }
}