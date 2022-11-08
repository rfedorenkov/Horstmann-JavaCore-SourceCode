package corejava.v2ch10.progressBar;

import javax.swing.*;

/**
 * Эта программа демонстрирует использование индикатора
 * выполнения для отслеживания хода выполнения потока.
 * @version 1.05 2012-05-10
 * @author Cay Horstmann
 */
public class ProgressBarTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new ProgressBarFrame();
            frame.setTitle("ProgressBarTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}