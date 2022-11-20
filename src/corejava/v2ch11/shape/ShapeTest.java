package corejava.v2ch11.shape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.*;
import java.util.Random;

/**
 * В этой программе демонстрируется построение
 * различных двумерных фигур
 * @version 1.03 2016-05-10
 * @author Cay Horstmann
 */
public class ShapeTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new ShapeTestFrame();
            frame.setTitle("ShapeTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

/**
 * Этот фрейм содержит комбинированный список для выбора фигур,
 * а также компонент для их рисования
 */
class ShapeTestFrame extends JFrame {
    public ShapeTestFrame() {
        final ShapeComponent comp = new ShapeComponent();
        add(comp, BorderLayout.CENTER);
        final JComboBox<ShapeMaker> comboBox = new JComboBox<>();
        comboBox.addItem(new LineMaker());
        comboBox.addItem(new RectangleMaker());
        comboBox.addItem(new RoundRectangleMaker());
        comboBox.addItem(new EllipseMaker());
        comboBox.addItem(new ArcMaker());
        comboBox.addItem(new PolygonMaker());
        comboBox.addItem(new QuadCurveMaker());
        comboBox.addItem(new CubicCurveMaker());
        comboBox.addActionListener(event -> {
            ShapeMaker shapeMaker = comboBox.getItemAt(comboBox.getSelectedIndex());
            comp.setShapeMaker(shapeMaker);
        });
        add(comboBox, BorderLayout.NORTH);
        comp.setShapeMaker((ShapeMaker) comboBox.getItemAt(0));
        pack();
    }
}

/**
 * Этот компонент рисует фигуру и дает пользователю
 * возможность перемещать определяющие ее точки
 */
class ShapeComponent extends JComponent {
    private static final Dimension PREFERRED_SIZE = new Dimension(300, 200);
    private Point2D[] points;
    private static Random generator = new Random();
    private static int SIZE = 10;
    private int current;
    private ShapeMaker shapeMaker;

    public ShapeComponent() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                Point p = event.getPoint();
                for (int i = 0; i < points.length; i++) {
                    double x = points[i].getX() - SIZE / 2;
                    double y = points[i].getY() - SIZE / 2;
                    Rectangle2D r = new Rectangle2D.Double(x, y, SIZE, SIZE);
                    if (r.contains(p)) {
                        current = i;
                        return;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent event) {
                current = -1;
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent event) {
                if (current == -1) return;
                points[current] = event.getPoint();
                repaint();
            }
        });
        current = -1;
    }

    /**
     * Устанавливает построитель фигур и инициализирует
     * его произвольным рядом точек
     * @param aShapeMaker Построитель фигур, определяющий фигуру
     *                    по ряду исходных точек
     */
    public void setShapeMaker(ShapeMaker aShapeMaker) {
        shapeMaker = aShapeMaker;
        int n = shapeMaker.getPointCount();
        points = new Point2D[n];
        for (int i = 0; i < n; i++) {
            double x = generator.nextDouble() * getWidth();
            double y = generator.nextDouble() * getHeight();
            points[i] = new Point2D.Double(x, y);
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (points == null) return;
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i < points.length; i++) {
            double x = points[i].getX() - SIZE / 2;
            double y = points[i].getY() - SIZE / 2;
            g2.fill(new Rectangle2D.Double(x, y, SIZE, SIZE));
        }

        g2.draw(shapeMaker.makeShape(points));
    }

    @Override
    public Dimension getPreferredSize() {
        return PREFERRED_SIZE;
    }
}

/**
 * Построитель фигур по ряду исходных точек. Конкретные
 * подклассы должны возвращать форму из метода makeShape()
 */
abstract class ShapeMaker {
    private int pointCount;

    /**
     * Конструирует построитель фигур
     * @param pointCount Количество исходных точек, требующихся
     *                   для определения данной фигуры
     */
    public ShapeMaker(int pointCount) {
        this.pointCount = pointCount;
    }

    /**
     * Получает количество исходных точек, требующихся
     * для определения данной фигуры
     * @return Возвращает количество исходных точек
     */
    public int getPointCount() {
        return pointCount;
    }

    public abstract Shape makeShape(Point2D[] p);

    @Override
    public String toString() {
        return getClass().getName();
    }
}

/**
 * Проводит линию, соединяющую две заданные точки
 */
class LineMaker extends ShapeMaker {

    public LineMaker() {
        super(2);
    }

    @Override
    public Shape makeShape(Point2D[] p) {
        return new Line2D.Double(p[0], p[1]);
    }
}

/**
 * Строит прямоугольник, соединяющий две заданные угловые точки
 */
class RectangleMaker extends ShapeMaker {

    public RectangleMaker() {
        super(2);
    }

    @Override
    public Shape makeShape(Point2D[] p) {
        Rectangle2D s = new Rectangle2D.Double();
        s.setFrameFromDiagonal(p[0], p[1]);
        return s;
    }
}

/**
 * Строит прямоугольник со скругленными углами,
 * соединяющий две заданные угловые точки
 */
class RoundRectangleMaker extends ShapeMaker {

    public RoundRectangleMaker() {
        super(2);
    }

    @Override
    public Shape makeShape(Point2D[] p) {
        RoundRectangle2D s = new RoundRectangle2D.Double(0, 0, 0, 0, 20, 20);
        s.setFrameFromDiagonal(p[0], p[1]);
        return s;
    }
}

/**
 * Строит эллипс, вписанный в ограничивающий прямоугольник
 * с двумя заданными угловыми точками
 */
class EllipseMaker extends ShapeMaker {

    public EllipseMaker() {
        super(2);
    }

    @Override
    public Shape makeShape(Point2D[] p) {
        Ellipse2D s = new Ellipse2D.Double();
        s.setFrameFromDiagonal(p[0], p[1]);
        return s;
    }
}

/**
 * Строит дугу, вписанную в ограничивающий прямоугольник
 * с двумя заданными угловыми точками. Начальный и конечный
 * углы дуги задают линии, проведенные из центра ограничивающего
 * прямоугольника в две заданные точки. Для демонстрации
 * правильности расчета углов возвращаемая фигура состоит
 * из дуги, ограничивающего прямоугольника и линий,
 * образующих эти углы
 */
class ArcMaker extends ShapeMaker {

    public ArcMaker() {
        super(4);
    }

    @Override
    public Shape makeShape(Point2D[] p) {
        double centerX = (p[0].getX() + p[1].getX()) / 2;
        double centerY = (p[0].getY() + p[1].getY()) / 2;
        double width = Math.abs(p[1].getX() - p[0].getX());
        double height = Math.abs(p[1].getY() - p[0].getY());

        double skewedStartAngle = Math.toDegrees(Math.atan2(-(p[2].getY() - centerY) * width,
                (p[2].getX() - centerX) * height));
        double skewedEndAngle = Math.toDegrees(Math.atan2(-(p[3].getY() - centerY) * width,
                (p[3].getX() - centerX) * height));
        double skewedAngleDifference = skewedEndAngle - skewedStartAngle;
        if (skewedStartAngle < 0) skewedStartAngle += 360;
        if (skewedAngleDifference < 0) skewedAngleDifference += 360;

        Arc2D s = new Arc2D.Double(0, 0, 0, 0, skewedStartAngle, skewedAngleDifference, Arc2D.OPEN);
        s.setFrameFromDiagonal(p[0], p[1]);

        GeneralPath g = new GeneralPath();
        g.append(s, false);
        Rectangle2D r = new Rectangle2D.Double();
        r.setFrameFromDiagonal(p[0], p[1]);
        g.append(r, false);
        Point2D center = new Point2D.Double(centerX, centerY);
        g.append(new Line2D.Double(center, p[2]), false);
        g.append(new Line2D.Double(center, p[3]), false);
        return g;
    }
}

/**
 * Строит многоугольник, определяемый шестью угловыми точками
 */
class PolygonMaker extends ShapeMaker {

    public PolygonMaker() {
        super(6);
    }

    @Override
    public Shape makeShape(Point2D[] p) {
        GeneralPath s = new GeneralPath();
        s.moveTo((float) p[0].getX(), (float) p[0].getY());
        for (int i = 1; i < p.length; i++)
            s.lineTo((float) p[i].getX(), (float) p[i].getY());
        s.closePath();
        return s;
    }
}

/**
 * Строит кривую второго порядка, определяемую двумя
 * конечными точками и одной управляющей точкой
 */
class QuadCurveMaker extends ShapeMaker {

    public QuadCurveMaker() {
        super(3);
    }

    @Override
    public Shape makeShape(Point2D[] p) {
        return new QuadCurve2D.Double(
                p[0].getX(), p[0].getY(),
                p[1].getX(), p[1].getY(),
                p[2].getX(), p[2].getY());
    }
}

/**
 * Строит кривую третьего порядка, определяемую двумя
 * конечными точками и двумя управляющими точками
 */
class CubicCurveMaker extends ShapeMaker {

    public CubicCurveMaker() {
        super(4);
    }

    @Override
    public Shape makeShape(Point2D[] p) {
        return new CubicCurve2D.Double(
                p[0].getX(), p[0].getY(),
                p[1].getX(), p[1].getY(),
                p[2].getX(), p[2].getY(),
                p[3].getX(), p[3].getY());
    }
}