package corejava.v2ch10.internalFrame;

import javax.swing.*;

/**
 * Эта программа демонстрирует использование внутренних фреймов
 * @version 1.12 2016-05-10
 * @author Cay Horstmann
 */
public class InternalFrameTest {
    public static void main(String[] args) {
        JFrame frame = new DesktopFrame();
        frame.setTitle("InternalFrameTest");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}