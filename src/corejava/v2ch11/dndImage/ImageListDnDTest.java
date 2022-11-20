package corejava.v2ch11.dndImage;

import javax.swing.*;

/**
 * Эта программа демонстрирует перетаскивание в списке изображений
 * @version 1.02 2016-05-10
 * @author Cay Horstmann
 */
public class ImageListDnDTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new ImageListDnDFrame();
            frame.setTitle("ImageListDnDTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}