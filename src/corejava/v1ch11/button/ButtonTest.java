package corejava.v1ch11.button;

import javax.swing.*;

/**
 * @version 1.34 2015-06-12
 * @author Cay Horstmann
 */
public class ButtonTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new ButtonFrame();
            frame.setTitle("ButtonTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}