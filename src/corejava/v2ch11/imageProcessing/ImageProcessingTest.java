package corejava.v2ch11.imageProcessing;

import javax.swing.*;

/**
 * Эта программа демонстрирует различные операции обработки изображений
 * @version 1.04 2016-05-10
 * @author Cay Horstmann
 */
public class ImageProcessingTest {
    public static void main(String[] args) {
        JFrame frame = new ImageProcessingFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}