package corejava.v2ch11.stroke;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * В этой программе демонстрируются различные виды обводки
 * @version 1.04 2016-05-10
 * @author Cay Horstmann
 */
public class StrokeTest {
    public static void main(String[] args) {
        JFrame frame = new StrokeTestFrame();
        frame.setTitle("StrokeTest");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

/**
 * В этом фрейме пользователь может выбрать стиль окончания
 * и соединения линий обводки, а также увидеть получающуюся
 * в итоге линию обводки
 */
class StrokeTestFrame extends JFrame {
    private StrokeComponent canvas;
    private JPanel buttonPanel;

    public StrokeTestFrame() {
        canvas = new StrokeComponent();
        add(canvas, BorderLayout.CENTER);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 3));
        add(buttonPanel, BorderLayout.NORTH);

        ButtonGroup group1 = new ButtonGroup();
        makeCapButton("Butt Cap", BasicStroke.CAP_BUTT, group1);
        makeCapButton("Round Cap", BasicStroke.CAP_ROUND, group1);
        makeCapButton("Square Cap", BasicStroke.CAP_SQUARE, group1);

        ButtonGroup group2 = new ButtonGroup();
        makeJoinButton("Miter Join", BasicStroke.JOIN_MITER, group2);
        makeJoinButton("Bevel Join", BasicStroke.JOIN_BEVEL, group2);
        makeJoinButton("Round Join", BasicStroke.JOIN_ROUND, group2);

        ButtonGroup group3 = new ButtonGroup();
        makeDashButton("Solid Line", false, group3);
        makeDashButton("Dashed Line", true, group3);
    }

    /**
     * Создает кнопку-переключатель для выбора стиля
     * окончания линии
     * @param label Метка кнопки-переключателя
     * @param style Стиль окончания линии
     * @param group Группа кнопок-переключателей
     */
    private void makeCapButton(String label, final int style, ButtonGroup group) {
        // выбрать первую кнопку-переключатель в группе
        boolean selected = group.getButtonCount() == 0;
        JRadioButton button = new JRadioButton(label, selected);
        buttonPanel.add(button);
        group.add(button);
        button.addActionListener(event -> canvas.setCap(style));
        pack();
    }

    /**
     * Создает кнопку-переключатель для выбора стиля
     * соединения линий
     * @param label Метка кнопки-переключателя
     * @param style Стиль соединения линий
     * @param group Группа кнопок-переключателей
     */
    private void makeJoinButton(String label, final int style, ButtonGroup group) {
        // выбрать первую кнопку-переключатель в группе
        boolean selected = group.getButtonCount() == 0;
        JRadioButton button = new JRadioButton(label, selected);
        buttonPanel.add(button);
        group.add(button);
        button.addActionListener(event -> canvas.setJoin(style));
    }

    /**
     * Создает кнопку-переключатель для выбора сплошных или
     * пунктирных линий обводки
     * @param label Метка кнопки-переключателя
     * @param style Принимает логическое значение false,
     *              если линия сплошная, или логическое
     *              значение true, если линия пунктирная
     * @param group Группа кнопок-переключателей
     */
    private void makeDashButton(String label, final boolean style, ButtonGroup group) {
        // выбрать первую кнопку-переключатель в группе
        boolean selected = group.getButtonCount() == 0;
        JRadioButton button = new JRadioButton(label, selected);
        buttonPanel.add(button);
        group.add(button);
        button.addActionListener(event -> canvas.setDash(style));
    }
}

/**
 * Этот компонент рисует две соединенные линии, используя
 * разные объекты обводки и давая пользователю возможность
 * перетаскивать три точки, определяющие линии обводки
 */
class StrokeComponent extends JComponent {
    private static final Dimension PREFERRED_SIZE = new Dimension(400, 400);
    private static int SIZE = 10;

    private Point2D[] points;
    private int current;
    private float width;
    private int cap;
    private int join;
    private boolean dash;

    public StrokeComponent() {
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

        points = new Point2D[3];
        points[0] = new Point2D.Double(200, 100);
        points[1] = new Point2D.Double(100, 200);
        points[2] = new Point2D.Double(200, 200);
        current = -1;
        width = 8.0F;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        GeneralPath path = new GeneralPath();
        path.moveTo((float) points[0].getX(), (float) points[0].getY());
        for (int i = 1; i < points.length; i++)
            path.lineTo((float) points[i].getX(), (float) points[i].getY());
        BasicStroke stroke;
        if (dash) {
            float miterLimit = 10.0F;
            float[] dashPattern = { 10F, 10F, 10F, 10F, 10F, 10F, 30F, 10F, 30F, 10F, 30F, 10F, 10F,
                    10F, 10F, 10F, 10F, 30F };
            float dashPhase = 0;
            stroke = new BasicStroke(width, cap, join, miterLimit, dashPattern, dashPhase);
        } else stroke = new BasicStroke(width, cap, join);
        g2.setStroke(stroke);
        g2.draw(path);
    }

    /**
     * Устанавливает стиль соединения линий
     * @param j Стиль соединения линий
     */
    public void setJoin(int j) {
        join = j;
        repaint();
    }

    /**
     * Устанавливает стиль окончания линий
     * @param c Стиль окончания линий
     */
    public void setCap(int c) {
        cap = c;
        repaint();
    }

    /**
     * Устанавливает сплошные или пунктирные линии
     * @param d Принимает логическое значение false,
     *          если линии сплошные, или логическое
     *          значение true, если линии пунктирные
     */
    public void setDash(boolean d) {
        dash = d;
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return PREFERRED_SIZE;
    }
}