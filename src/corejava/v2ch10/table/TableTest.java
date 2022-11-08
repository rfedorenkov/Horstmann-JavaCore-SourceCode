package corejava.v2ch10.table;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PrinterException;

/**
 * В этой программе демонстрируется порядок
 * отображения простой таблицы
 * @version 1.13 2016-05-10
 * @author Cay Horstmann
 */
public class TableTest {
    public static void main(String[] args) throws Exception{
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new PlanetTableFrame();
            frame.setTitle("TableTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

/**
 * Этот фрейм содержит таблицу с данными о планетах
 */
class PlanetTableFrame extends JFrame {
    private String[] columnNames = { "Planet", "Radius", "Moons", "Gaseous", "Color" };
    private Object[][] cells = { { "Mercury", 2440.0, 0, false, Color.YELLOW },
            { "Venus", 6052.0, 0, false, Color.YELLOW }, { "Earth", 6378.0, 1, false, Color.BLUE },
            { "Mars", 3397.0, 2, false, Color.RED }, { "Jupiter", 71492.0, 16, true, Color.ORANGE },
            { "Saturn", 60268.0, 18, true, Color.ORANGE }, { "Uranus", 25559.0, 17, true, Color.BLUE },
            { "Neptune", 24766.0, 8, true, Color.BLUE }, { "Pluto", 1137.0, 1, false, Color.BLACK } };

    public PlanetTableFrame() {
        final JTable table = new JTable(cells, columnNames);
        table.setAutoCreateRowSorter(true);
        add(new JScrollPane(table), BorderLayout.CENTER);
        JButton printButton = new JButton("Print");
        printButton.addActionListener(event -> {
            try {
                table.print();
            } catch (SecurityException | PrinterException ex) {
                ex.printStackTrace();
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(printButton);
        add(buttonPanel, BorderLayout.SOUTH);
        pack();
    }
}