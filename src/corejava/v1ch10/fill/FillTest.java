package corejava.v1ch10.fill;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * @version 1.34 2015-05-12
 * @author Cay Horstmann
 */
public class FillTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new FillFrame();
            frame.setTitle("FillTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

/**
 * Фрейм, содержащий панель с нарисованными двумерными формами
 */
class FillFrame extends JFrame {
    public FillFrame() {
        add(new FillComponent());
        pack();
    }
}

/**
 * Компонент, отображающий закрашенные прямоугольники и эллипсы
 */
class FillComponent extends JComponent {
    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 400;

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // нарисовать прямоугольник

        double leftX = 100;
        double topY = 100;
        double width = 200;
        double height = 150;

        Rectangle2D rect = new Rectangle2D.Double(leftX, topY, width, height);
        g2.setPaint(Color.BLACK);
        g2.draw(rect);
        g2.setPaint(Color.RED);
        g2.fill(rect); // обратите внимание, что правый и нижний край не закрашиваются

        // нарисовать вписанный эллипс
        Ellipse2D ellipse = new Ellipse2D.Double();
        ellipse.setFrame(rect);
        g2.setPaint(new Color(0, 128, 128)); // скучный сине-зеленый цвет
        g2.fill(ellipse);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
}