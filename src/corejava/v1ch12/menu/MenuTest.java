package corejava.v1ch12.menu;

import javax.swing.*;

/**
 * @version 1.24 2012-06-12
 * @author Cay Horstmann
 */
public class MenuTest {
    public static void main(String[] args) {
        JFrame frame = new MenuFrame();
        frame.setTitle("MenuTest");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}