package corejava.v2ch10.tableCellRender;

import javax.swing.*;

/**
 * Эта программа демонстрирует рендеринг и редактирование ячеек в таблице
 * @version 1.04 2016-05-10
 * @author Cay Horstmann
 */
public class TableCellRenderTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new TableCellRenderFrame();
            frame.setTitle("TableCellRenderTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}