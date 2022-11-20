package corejava.v2ch11.print;

import javax.swing.*;

/**
 * Эта программа демонстрирует, как печатать 2D-графику
 * @version 1.13 2016-05-10
 * @author Cay Horstmann
 */
public class PrintTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new PrintTestFrame();
            frame.setTitle("PrintTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}