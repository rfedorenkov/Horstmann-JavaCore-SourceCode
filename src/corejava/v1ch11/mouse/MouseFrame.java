package corejava.v1ch11.mouse;

import javax.swing.*;

/**
 * Фрейм, содержащий панель для проверки операций,
 * выполняемых мышью
 */
public class MouseFrame extends JFrame {
    public MouseFrame() {
        add(new MouseComponent());
        pack();
    }
}