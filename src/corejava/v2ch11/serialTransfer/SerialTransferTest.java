package corejava.v2ch11.serialTransfer;

import javax.swing.*;

/**
 * Эта программа демонстрирует передачу
 * сериализованных объектов между виртуальными машинами.
 * @version 1.03 2016-05-10
 * @author Cay Horstmann
 */
public class SerialTransferTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new SerialTransferFrame();
            frame.setTitle("SerialTransferTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}