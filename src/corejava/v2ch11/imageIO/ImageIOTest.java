package corejava.v2ch11.imageIO;

import javax.swing.*;

/**
 * Эта программа позволяет читать и записывать файлы изображений в форматах,
 * поддерживаемых JDK. Поддерживаются многофайловые изображения.
 * @version 1.04 2016-05-10
 * @author Cay Horstmann
 */
public class ImageIOTest {
    public static void main(String[] args) {
        JFrame frame = new ImageIOFrame();
        frame.setTitle("ImageIOTest");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}