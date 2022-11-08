package corejava.v2ch10.tableSelection;

import javax.swing.*;

/**
 * Эта программа демонстрирует выделение, добавление и удаление строк и столбцов
 */
public class TableSelectionTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new TableSelectionFrame();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}