package corejava.v2ch10.longList;

import javax.swing.*;

/**
 * Эта программа демонстрирует список, который динамически вычисляет элементы списка.
 * @version 1.24 2016-05-10
 * @author Cay Horstmann
 */
public class LongListTest {
    public static void main(String[] args) {
        JFrame frame = new LongListFrame();
        frame.setTitle("LongListTest");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}