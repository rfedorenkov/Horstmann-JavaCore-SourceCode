package corejava.v2ch10.listRendering;

import javax.swing.*;

/**
 * Эта программа демонстрирует использование визуализаторов ячеек в списке.
 * @version 1.25 2016-05-10
 * @author Cay Horstmann
 */
public class ListRenderingTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new ListRenderingFrame();
            frame.setTitle("ListRenderingTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}