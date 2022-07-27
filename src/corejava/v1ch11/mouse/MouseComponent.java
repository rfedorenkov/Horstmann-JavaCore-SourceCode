package corejava.v1ch11.mouse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Компонент для операций с мышью по добавлению и удалению квадратов
 */
public class MouseComponent extends JComponent {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    private static final int SIDE_LENGTH = 10;
    private ArrayList<Rectangle2D> squares;
    private Rectangle2D current; // квадрат, содержащий курсор мыши

    public MouseComponent() {
        squares = new ArrayList<>();
        current = null;

        addMouseListener(new MouseHandler());
        addMouseMotionListener(new MouseMotionHandler());
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // нарисовать все квадраты
        for (Rectangle2D r : squares)
            g2.draw(r);
    }

    /**
    * Обнаруживает первый квадрат, содержащий заданную точку
    * @param p Точка
    * @return Первый квадрат, содержащий точку p
    */
    public Rectangle2D find(Point2D p) {
        for (Rectangle2D r : squares) {
            if (r.contains(p)) return r;
        }
        return null;
    }

    /**
    * Вводит квадрат в коллекцию
    * @param p Центр квадрата
    */
    public void add(Point2D p) {
        double x = p.getX();
        double y = p.getY();

        current = new Rectangle2D.Double(x - SIDE_LENGTH / 2, y - SIDE_LENGTH / 2, SIDE_LENGTH, SIDE_LENGTH);
        squares.add(current);
        repaint();
    }

    /**
     * Удаляет квадрат из коллекции
     * @param s Удаляемый квадрат
     */
    public void remove(Rectangle2D s) {
        if (s == null) return;
        if (s == current) current = null;
        squares.remove(s);
        repaint();
    }

    private class MouseHandler extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            // добавить новый квадрат, если курсор
            // находится за пределами квадрата
            current = find(e.getPoint());
            if (current == null) add(e.getPoint());
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            // удалить текущий квадрат, если на нем
            // произведен двойной щелчок
            current = find(e.getPoint());
            if (current != null && e.getClickCount() >= 2)
                remove(current);
        }
    }

    private class MouseMotionHandler implements MouseMotionListener {

        @Override
        public void mouseMoved(MouseEvent e) {
            // задать курсор в виде перекрестья,
            // если он находится внутри квадрата
            if (find(e.getPoint()) == null)
                setCursor(Cursor.getDefaultCursor());
            else
                setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (current != null) {
                int x = e.getX();
                int y = e.getY();

                // перетащить текущий квадрат, чтобы отцентровать
                // его в точке с координатами (x,y)
                current.setFrame(x - SIDE_LENGTH / 2, y - SIDE_LENGTH / 2, SIDE_LENGTH, SIDE_LENGTH);
                repaint();
            }
        }
    }
}