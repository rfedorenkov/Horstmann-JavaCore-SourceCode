package corejava.v2ch11.book;

import javax.swing.*;
import java.awt.*;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Printable;

/**
 * Этот класс реализует типичное окно предварительного
 * просмотра печати
 */
public class PrintPreviewDialog extends JDialog {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 300;

    private PrintPreviewCanvas canvas;

    /**
     * Конструирует диалоговое окно предварительного
     * просмотра печати
     * @param p Печатаемый объект типа Printable
     * @param pf Формат страницы
     * @param pages Количество страниц в печатаемом объекте p
     */
    public PrintPreviewDialog(Printable p, PageFormat pf, int pages) {
        Book book = new Book();
        book.append(p, pf, pages);
        layoutUI(book);
    }

    /**
     * Создает диалоговое окно предварительного просмотра печати
     * @param b Объект книги типа Book
     */
    PrintPreviewDialog(Book b) {
        layoutUI(b);
    }

    /**
     * Компонует ГПИ в диалоговом окне
     * @param book Предваритеьлно просматриваемая книга
     */
    public void layoutUI(Book book) {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        canvas = new PrintPreviewCanvas(book);
        add(canvas, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton nextButton = new JButton("Next");
        buttonPanel.add(nextButton);
        nextButton.addActionListener(event -> canvas.flipPage(1));

        JButton previousButton = new JButton("Previous");
        buttonPanel.add(previousButton);
        previousButton.addActionListener(event -> canvas.flipPage(-1));

        JButton closeButton = new JButton("Close");
        buttonPanel.add(closeButton);
        closeButton.addActionListener(event -> setVisible(false));

        add(buttonPanel, BorderLayout.SOUTH);
    }
}