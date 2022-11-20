package corejava.v2ch11.desktopApp;

import javax.swing.*;

/**
 * Эта программа демонстрирует API настольного приложения.
 * @version 1.01 2016-05-10
 * @author Cay Horstmann
 */
public class DesktopAppTest {
    public static void main(String[] args) {
        JFrame frame = new DesktopAppFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}