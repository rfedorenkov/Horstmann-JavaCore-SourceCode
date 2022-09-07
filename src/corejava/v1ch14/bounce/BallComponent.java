package corejava.v1ch14.bounce;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Компонент, рисующий скачущий мяч
 * @version 1.34 2012-01-26
 * @author Cay Horstmann
 */
public class BallComponent extends JPanel {
    private static final int DEFAULT_WIDTH = 450;
    private static final int DEFAULT_HEIGHT = 350;

    private List<Ball> balls = new ArrayList<>();

    /**
     * Вводит мяч в компонент
     * @param ball Вводимый мяч
     */
    public void add(Ball ball) {
        balls.add(ball);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // очистить фон
        Graphics2D g2 = (Graphics2D) g;
        for (Ball b : balls) {
            g2.fill(b.getShape());
        }

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
}