package corejava.v1ch14.bounceThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * В этой программе демонстрируется анимация скачущего мяча
 * @version 1.34 2015-06-21
 * @author Cay Horstmann
 */
public class BounceThread {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new BounceFrame();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setTitle("BounceThread");
            frame.setVisible(true);
        });
    }
}

/**
 * Фрейм с компонентом мяча и кнопками
 */
class BounceFrame extends JFrame {
    private BallComponent comp;
    public static final int STEPS = 1000;
    public static final int DELAY = 3;

    /**
     * Конструирует фрейм с компонентом, отображающим
     * скачущий мяч, а так же кнопки Start и Close
     */
    public BounceFrame() {
        setTitle("Bounce");
        comp = new BallComponent();
        add(comp, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        addButton(buttonPanel, "Start", event -> addBall());
        addButton(buttonPanel, "Close", event -> System.exit(0));
        add(buttonPanel, BorderLayout.SOUTH);
        pack();
    }


    /**
     * Вводит кнопку в контейнер
     * @param c Контейнер
     * @param title Надпись на кнопке
     * @param listener Приемник действий кнопки
     */
    public void addButton(Container c, String title, ActionListener listener) {
        JButton button = new JButton(title);
        c.add(button);
        button.addActionListener(listener);
    }

    /**
     * Вводит скачущий мяч на панели и производит 1000 его отскоков
     */
    public void addBall() {
        Ball ball = new Ball();
        comp.add(ball);
        Runnable r = () -> {
            try {
                for (int i = 1; i <= STEPS; i++) {
                    ball.move(comp.getBounds());
                    comp.paint(comp.getGraphics());
                    Thread.sleep(DELAY);
                }
            } catch (InterruptedException ignored) {}
        };
        Thread t = new Thread(r);
        t.start();
    }
}