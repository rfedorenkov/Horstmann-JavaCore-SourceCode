package corejava.v2ch11.rasterImage;

import javax.swing.*;

/**
 * Эта программа демонстрирует, как создать изображение из отдельных пикселей.
 * @version 1.14 2016-05-10
 * @author Cay Horstmann
 */
public class RasterImageTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new RasterImageFrame();
            frame.setTitle("RasterImageTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}