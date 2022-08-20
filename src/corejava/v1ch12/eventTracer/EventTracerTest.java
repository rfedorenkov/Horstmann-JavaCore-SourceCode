package corejava.v1ch12.eventTracer;

import javax.swing.*;
import java.awt.*;

/**
 * @version 1.14 2015-08-20
 * @author Cay Horstmann
 */
public class EventTracerTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new EventTracerFrame();
            frame.setTitle("EventTracerTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

class EventTracerFrame extends JFrame {
    public EventTracerFrame() {
        // добавить ползунок и кнопку
        add(new JSlider(), BorderLayout.NORTH);
        add(new JButton("Test"), BorderLayout.SOUTH);

        // ловить все события компонентов внутри фрейма
        EventTracer tracer = new EventTracer();
        tracer.add(this);
        pack();
    }
}