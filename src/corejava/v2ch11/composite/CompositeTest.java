package corejava.v2ch11.composite;

import javax.swing.*;

/**
 * Эта программа демонстрирует правила композиции Портера-Даффа
 * @version 1.04 2016-05-10
 * @author Cay Horstmann
 */
public class CompositeTest {
    public static void main(String[] args) {
        JFrame frame = new CompositeTestFrame();
        frame.setTitle("CompositeTest");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}