package corejava.v1ch12.dialog;

import javax.swing.*;

/**
 * @version 1.34 2012-06-12
 * @author Cay Horstmann
 */
public class DialogTest {
    public static void main(String[] args) {
        JFrame frame = new DialogFrame();
        frame.setTitle("DialogTest");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}