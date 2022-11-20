package corejava.v2ch11.renderQuality;

import javax.swing.*;

/**
 * Эта программа демонстрирует эффект различных подсказок рендеринга
 * @version 1.11 2016-05-10
 * @author Cay Horstmann
 */
public class RenderQualityTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new RenderQualityTestFrame();
            frame.setTitle("RenderQualityTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}