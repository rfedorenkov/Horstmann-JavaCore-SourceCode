package corejava.v1ch12.border;

import javax.swing.*;

/**
 * @version 1.34 2015-06-13
 * @author Cay Horstmann
 */
public class BorderTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new BorderFrame();
            frame.setTitle("BorderTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}