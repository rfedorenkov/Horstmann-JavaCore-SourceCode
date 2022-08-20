package corejava.v1ch12.toolBar;

import javax.swing.*;

/**
 * @version 1.14 2015-06-12
 * @author Cay Horstmann
 */
public class ToolBarTest {
    public static void main(String[] args) {
        ToolBarFrame frame = new ToolBarFrame();
        frame.setTitle("ToolBarTest");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}