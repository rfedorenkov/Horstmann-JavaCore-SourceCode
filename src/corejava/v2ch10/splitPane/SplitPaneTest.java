package corejava.v2ch10.splitPane;

import javax.swing.*;

/**
 * Эта программа демонстрирует организатор компонентов с разделенной панелью
 * @version 1.04 2016-05-10
 * @author Cay Horstmann
 */
public class SplitPaneTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new SplitPaneFrame();
            frame.setTitle("SplitPaneTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}