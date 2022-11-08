package corejava.v2ch10.tableRowColumn;

import javax.swing.*;

/**
 * Эта программа демонстрирует, как работать со строками и столбцами в таблице.
 * @version 1.22 2016-05-10
 * @author Cay Horstmann
 */
public class TableRowColumnTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new PlanetTableFrame();
            frame.setTitle("TableRowColumnTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}