package corejava.v1ch12.colorChooser;

import javax.swing.*;

/**
 * Фрейм с панелью выбора цвета
 */
public class ColorChooserFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    public ColorChooserFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        // добавляем панель выбора цвета во фрейм

        ColorChooserPanel panel = new ColorChooserPanel();
        add(panel);
    }
}