package corejava.v1ch10.font;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

/**
 * @version 1.34 2015-05-12
 * @author Cay Horstmann
 */
public class FontTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new FontFrame();
            frame.setTitle("FontTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

/**
 * Фрейм с компонентом текстового сообщения
 */
class FontFrame extends JFrame {
    public FontFrame() {
        add(new FontComponent());
        pack();
    }
}

/**
 * Компонент, отображающий текстовое сообщение,
 * выровненное по центру в прямоугольной рамке
 */
class FontComponent extends JComponent {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        String message = "Hello, World!";

        Font f = new Font("Serif", Font.BOLD, 36);
        g2.setFont(f);

        // определить размеры текстового сообщения

        FontRenderContext context = g2.getFontRenderContext();
        Rectangle2D bounds = f.getStringBounds(message, context);

        // определить координаты (x,y) верхнего левого угла текста

        double x = (getWidth() - bounds.getWidth()) / 2;
        double y = (getHeight() - bounds.getHeight()) / 2;

        // сложить подъем с координатой y, чтобы достичь базовой линии

        double ascent = -bounds.getY();
        double baseY = y + ascent;

        // воспроизвести текстовое сообщение

        g2.drawString(message, (int) x, (int) baseY);

        g2.setColor(Color.LIGHT_GRAY);

        // нарисовать базовую линию

        g2.draw(new Line2D.Double(x, baseY, x + bounds.getWidth(), baseY));

        // нарисовать ограничивающий прямоугольник

        Rectangle2D rect = new Rectangle2D.Double(x, y, bounds.getWidth(), bounds.getHeight());
        g2.draw(rect);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
}