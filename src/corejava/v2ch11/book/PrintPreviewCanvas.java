package corejava.v2ch11.book;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

/**
 * Конструирует канву для предварительного просмотра печати
 * The canvas for displaying the print preview.
 */
public class PrintPreviewCanvas extends JComponent {
    private Book book;
    private int currentPage;

    /**
     * Конструирует канву для предварительного просмотра печати
     * @param b Предварительно просматриваемая книга
     */
    public PrintPreviewCanvas(Book b) {
        book = b;
        currentPage = 0;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        PageFormat pageFormat = book.getPageFormat(currentPage);

        double xoff; // координата x смещения начала страницы в окне
        double yoff; // координата y смещения начала страницы в окне
        double scale; // масштабный коэффициент для подгонки
                      // просматриваемой страницы по размерам окна
        double px = pageFormat.getWidth();
        double py = pageFormat.getHeight();
        double sx = getWidth() - 1;
        double sy = getHeight() - 1;
        if (px / py < sx / sy) { // отцентрировать по горизонтали
            scale = sy / py;
            xoff = 0.5 * (sx - scale * px);
            yoff = 0;
        } else { // отцентрировать по вертикали
            scale = sx / px;
            xoff = 0;
            yoff = 0.5 * (sy - scale * py);
        }
        g2.translate((float) xoff, (float) yoff);
        g2.scale((float) scale, (float) scale);

        // нарисовать контуры страницы, игнорируя поля
        Rectangle2D page = new Rectangle2D.Double(0, 0, px, py);
        g2.setPaint(Color.white);
        g2.fill(page);
        g2.setPaint(Color.black);
        g2.draw(page);

        Printable printable = book.getPrintable(currentPage);
        try {
            printable.print(g2, pageFormat, currentPage);
        } catch (PrinterException e) {
            g2.draw(new Line2D.Double(0, 0, px, py));
            g2.draw(new Line2D.Double(px, 0, 0, py));
        }
    }

    /**
     * Листать книгу на заданное число страниц
     * @param by Число листаемых страниц. Отрицательные значения
     *          данного параметра обозначают листание книги назад
     */
    public void flipPage(int by) {
        int newPage = currentPage + by;
        if (0 <= newPage && newPage < book.getNumberOfPages()) {
            currentPage = newPage;
            repaint();
        }
    }
}