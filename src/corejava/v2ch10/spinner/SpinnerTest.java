package corejava.v2ch10.spinner;

import javax.swing.*;

/**
 * Программа для тестирования спиннеров
 * @version 1.03 2016-05-10
 * @author Cay Horstmann
 */
public class SpinnerTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new SpinnerFrame();
            frame.setTitle("SpinnerTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}