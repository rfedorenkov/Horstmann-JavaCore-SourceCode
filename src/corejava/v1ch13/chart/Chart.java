package corejava.v1ch13.chart;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;

/**
 * @version 1.34 2015-06-12
 * @author Cay Horstmann
 */
public class Chart extends JApplet {
    @Override
    public void init() {
        SwingUtilities.invokeLater(() -> {
            String v = getParameter("values");
            if (v == null) return;
            int n = Integer.parseInt(v);
            double[] values = new double[n];
            String[] names = new String[n];
            for (int i = 0; i < n; i++) {
                values[i] = Double.parseDouble(getParameter("value." + (i + 1)));
                names[i] = getParameter("name." + (i + 1));
            }

            add(new ChartComponent(values, names, getParameter("title")));
        });
    }
}

/**
 * Компонент, строящий гистограмму
 */
class ChartComponent extends JComponent {
    private double[] values;
    private String[] names;
    private String title;

    /**
     * Конструирует объект типа ChartComponent
     * @param v Массив значений для построения гистрограммы
     * @param n Массив меток гистограммы
     * @param t Заголовок гистограммы
     */
    public ChartComponent(double[] v, String[] n, String t) {
        values = v;
        names = n;
        title = t;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // вычислить минимальное и максимальное значения
        if (values == null) return;
        double minValue = 0;
        double maxValue = 0;
        for (double v : values) {
            if (minValue > v) minValue = v;
            if (maxValue < v) maxValue = v;
        }
        if (maxValue == minValue) return;

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        Font titleFont = new Font("SansSerif", Font.BOLD, 20);
        Font labelFont = new Font("SansSerif", Font.PLAIN, 10);

        // вычислить протяженность заголовка
        FontRenderContext context = g2.getFontRenderContext();
        Rectangle2D titleBounds = titleFont.getStringBounds(title, context);
        double titleWidth = titleBounds.getWidth();
        double top = titleBounds.getHeight();

        // воспроизвести заголовок
        double y = -titleBounds.getY(); // ascent
        double x = (panelWidth - titleWidth) / 2;
        g2.setFont(titleFont);
        g2.drawString(title, (float) x, (float) y);

        // вычислить протяженность метод гистограммы
        LineMetrics labelMetrics = labelFont.getLineMetrics("", context);
        double bottom = labelMetrics.getHeight();

        y = panelHeight - labelMetrics.getDescent();
        g2.setFont(labelFont);

        // получить масштабный коэффициент и
        // ширину столбиков гистограммы
        double scale = (panelHeight - top - bottom) / (maxValue - minValue);
        int barWidth = panelWidth / values.length;

        // нарисовать столбики гистограммы
        for (int i = 0; i < values.length; i++) {
            // получить координаты прямоугольного столбика гистограммы
            double x1 = i * barWidth + 1;
            double y1 = top;
            double height = values[i] * scale;
            if (values[i] >= 0)
                y1 += (maxValue - values[i]) * scale;
            else {
                y1 += maxValue * scale;
                height = -height;
            }

            // заполнить столбик гистограммы и обвести его контуром
            Rectangle2D rect = new Rectangle2D.Double(x1, y1, barWidth - 2, height);
            g2.setPaint(Color.RED);
            g2.fill(rect);
            g2.setPaint(Color.BLACK);
            g2.draw(rect);

            // воспроизвести отцентрованную метку
            // под столбиком гистограммы
            Rectangle2D labelBounds = labelFont.getStringBounds(names[i], context);

            double labelWidth = labelBounds.getWidth();
            x = x1 + (barWidth - labelWidth) / 2;
            g2.drawString(names[i], (float) x, (float) y);
        }
    }
}