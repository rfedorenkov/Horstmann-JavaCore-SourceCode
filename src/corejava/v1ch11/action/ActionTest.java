package corejava.v1ch11.action;

import javax.swing.*;

/**
 * @version 1.34 2015-06-12
 * @author Cay Horstmann
 */
public class ActionTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new ActionFrame();
            frame.setTitle("ActionTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}