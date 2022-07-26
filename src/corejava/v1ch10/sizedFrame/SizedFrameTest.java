package corejava.v1ch10.sizedFrame;

import javax.swing.*;
import java.awt.*;

/**
 * @version 1.34 2015-06-16
 * @author Cay Horstmann
 */
public class SizedFrameTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new SizedFrame();
            frame.setTitle("SizedFrame");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

class SizedFrame extends JFrame {
    public SizedFrame() {
        // получить размеры экрана

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;

        // задать ширину и высоту фрейма, предоставив платформе
        // возможность самой выбрать местоположение фрейма

        setSize(screenWidth / 2, screenHeight / 2);
        setLocationByPlatform(true);

        // Задать пиктограмму для фрейма

        Image img = new ImageIcon("./src/corejava/v1ch10/icon.gif").getImage();
        setIconImage(img);
    }
}