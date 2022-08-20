package corejava.v1ch12.circleLayout;

import javax.swing.*;

/**
 * @version 1.33 2015-06-12
 * @author Cay Horstmann
 */
public class CircleLayoutTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new CircleLayoutFrame();
            frame.setTitle("CircleLayoutTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}