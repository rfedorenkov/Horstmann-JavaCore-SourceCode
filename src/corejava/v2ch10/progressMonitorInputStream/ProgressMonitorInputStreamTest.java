package corejava.v2ch10.progressMonitorInputStream;

import javax.swing.*;

/**
 * Программа для тестирования входного потока монитора прогресса
 * @version 1.06 2016-05-10
 * @author Cay Horstmann
 */
public class ProgressMonitorInputStreamTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new TextFrame();
            frame.setTitle("ProgressMonitorInputStreamTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}