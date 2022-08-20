package corejava.v1ch12.circleLayout;

import javax.swing.*;

/**
 * Фрейм, в котором демонстрируется расположение кнопок по кругу
 */
public class CircleLayoutFrame extends JFrame {
    public CircleLayoutFrame() {
        setLayout(new CircleLayout());
        add(new JButton("Yellow"));
        add(new JButton("Blue"));
        add(new JButton("Red"));
        add(new JButton("Green"));
        add(new JButton("Orange"));
        add(new JButton("Fuchsia"));
        add(new JButton("Indigo"));
        pack();
    }
}