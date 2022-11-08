package corejava.v2ch10.tabbedPane;

import javax.swing.*;

/**
 * Эта программа демонстрирует организатор компонентов панели с вкладками
 * @version 1.04 2016-05-10
 * @author Cay Horstmann
 */
public class TabbedPaneTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new TabbedPaneFrame();
            frame.setTitle("TabbedPaneTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}