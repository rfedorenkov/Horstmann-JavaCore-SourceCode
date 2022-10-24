package corejava.v2ch08.buttons3;

import javax.swing.*;

/**
 * @version 1.01 2016-05-10
 * @author Cay Horstmann
 */
public class ButtonTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ButtonFrame frame = new ButtonFrame();
            frame.setTitle("ButtonTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}