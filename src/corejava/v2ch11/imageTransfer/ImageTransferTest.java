package corejava.v2ch11.imageTransfer;

import javax.swing.*;

/**
 * Эта программа демонстрирует передачу изображений
 * между приложением Java и системным буфером обмена.
 * @version 1.23 2016-05-10
 * @author Cay Horstmann
 */
public class ImageTransferTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new ImageTransferFrame();
            frame.setTitle("ImageTransferTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}