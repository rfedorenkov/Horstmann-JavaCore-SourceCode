package corejava.v2ch10.textChange;

import javax.swing.*;

/**
 * @version 1.41 2016-05-10
 * @author Cay Horstmann
 */
public class ChangeTrackingTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new ColorFrame();
            frame.setTitle("ChangeTrackingTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}