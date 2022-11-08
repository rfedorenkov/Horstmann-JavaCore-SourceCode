package corejava.v2ch10.list;

import javax.swing.*;

/**
 * Эта программа демонстрирует простой фиксированный список строк.
 * @version 1.25 2016-05-10
 * @author Cay Horstmann
 */
public class ListTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new ListFrame();
            frame.setTitle("ListTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}