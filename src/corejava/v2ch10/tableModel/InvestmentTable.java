package corejava.v2ch10.tableModel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 * В этой программе демонстрируется построение таблицы по ее модели
 * @version 1.03 2006-05-10
 * @author Cay Horstmann
 */
public class InvestmentTable {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new InvestmentTableFrame();
            frame.setTitle("InvestmentTable");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

/**
 * Этот фрейм содержит таблицу капиталовложений
 */
class InvestmentTableFrame extends JFrame {
    public InvestmentTableFrame() {
        TableModel model = new InvestmentTableModel(30, 5, 10);
        JTable table = new JTable(model);
        add(new JScrollPane(table));
        pack();
    }
}

/**
 * В этой модели таблицы рассчитывается содержимое ячеек таблицы
 * всякий раз, когда оно запрашивается. В таблице представлен рост
 * капиталовложений в течении ряда лет при разных учетных ставках
 */
class InvestmentTableModel extends AbstractTableModel {
    private static double INITIAL_BALANCE = 100000.0;
    
    private int years;
    private int minRate;
    private int maxRate;

    /**
     * Конструирует модель таблицы капиталовложений
     * @param y Количество лет
     * @param r1 Наинизшая учетная ставка для составления таблицы
     * @param r2 Наивысшая учетная ставка для составления таблицы
     */
    public InvestmentTableModel(int y, int r1, int r2) {
        years = y;
        minRate = r1;
        maxRate = r2;
    }

    @Override
    public int getRowCount() {
        return years;
    }

    @Override
    public int getColumnCount() {
        return maxRate - minRate + 1;
    }

    @Override
    public Object getValueAt(int r, int c) {
        double rate = (c + minRate) / 100.0;
        int nperiods = r;
        double futureBalance = INITIAL_BALANCE * Math.pow(1 + rate, nperiods);
        return String.format("%.2f", futureBalance);
    }

    @Override
    public String getColumnName(int c) {
        return (c + minRate) + "%";
    }
}