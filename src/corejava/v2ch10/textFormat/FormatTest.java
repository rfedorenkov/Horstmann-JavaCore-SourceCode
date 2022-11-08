package corejava.v2ch10.textFormat;

import javax.swing.*;

/**
 * Программа для проверки форматированных текстовых полей
 * @version 1.04 2016-05-10
 * @author Cay Horstmann
 */
public class FormatTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new FormatTestFrame();
            frame.setTitle("FormatTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}