package corejava.v1ch09.shuffle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * В этой программе демонстрируются алгоритмы
 * произвольной перетасовки и сортировки
 * @version 1.11 2012-01-26
 * @author Cay Horstmann
 */
public class ShuffleTest {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 49; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        List<Integer> winningCombination = numbers.subList(0, 6);
        System.out.println(winningCombination);
    }
}