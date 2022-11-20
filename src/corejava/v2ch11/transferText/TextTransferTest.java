package corejava.v2ch11.transferText;

import javax.swing.*;

/**
 * Эта программа демонстрирует передачу текста между
 * приложением Java и системным буфером обмена.
 * @version 1.14 2016-05-10
 * @author Cay Horstmann
 */
public class TextTransferTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new TextTransferFrame();
            frame.setTitle("TextTransferTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}