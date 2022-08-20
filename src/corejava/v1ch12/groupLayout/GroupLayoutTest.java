package corejava.v1ch12.groupLayout;

import javax.swing.*;

/**
 * @version 1.01 2015-06-12
 * @author Cay Horstmann
 */
public class GroupLayoutTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new FontFrame();
            frame.setTitle("GroupLayoutTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}