package corejava.v2ch10.progressMonitor;

import javax.swing.*;

/**
 * Программа для тестирования диалогового окна мониторинга прогресса
 * @version 1.05 2016-05-10
 * @author Cay Horstmann
 */
public class ProgressMonitorTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new ProgressMonitorFrame();
            frame.setTitle("ProgressMonitorTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}