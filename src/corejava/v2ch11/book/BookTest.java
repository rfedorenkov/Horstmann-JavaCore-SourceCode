package corejava.v2ch11.book;

import javax.swing.*;

/**
 * Эта программа демонстрирует печать многостраничной книги. Он печатает "баннер",
 * расширяя текстовую строку, чтобы заполнить всю страницу по вертикали.
 * Программа также содержит общий диалог предварительного просмотра печати
 * @version 1.13 2016-05-10
 * @author Cay Horstmann
 */
public class BookTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new BookTestFrame();
            frame.setTitle("BookTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}