package corejava.v2ch10.tableSelection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.util.ArrayList;

/**
 * Этот фрейм показывает таблицу умножения и имеет меню
 * для установки режимов выбора строки/столбца/ячейки,
 * а также для добавления и удаления строк и столбцов.
 */
public class TableSelectionFrame extends JFrame {
    private DefaultTableModel model;
    private JTable table;
    private ArrayList<TableColumn> removedColumns;

    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 300;

    public TableSelectionFrame() {
        setTitle("TableSelectionTest");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        // составить таблицу умножения

        model = new DefaultTableModel(10, 10);

        for (int i = 0; i < model.getRowCount(); i++)
            for (int j = 0; j < model.getColumnCount(); j++)
                model.setValueAt((i + 1) * (j + 1), i, j);

        table = new JTable(model);

        add(new JScrollPane(table), "Center");

        removedColumns = new ArrayList<>();

        // создаем меню

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu selectionMenu = new JMenu("Selection");
        menuBar.add(selectionMenu);

        final JCheckBoxMenuItem rowsItem = new JCheckBoxMenuItem("Rows");
        final JCheckBoxMenuItem columnsItem = new JCheckBoxMenuItem("Columns");
        final JCheckBoxMenuItem cellsItem = new JCheckBoxMenuItem("Cells");

        rowsItem.setSelected(table.getRowSelectionAllowed());
        columnsItem.setSelected(table.getColumnSelectionAllowed());
        cellsItem.setSelected(table.getCellSelectionEnabled());

        rowsItem.addActionListener(event -> {
            table.clearSelection();
            table.setRowSelectionAllowed(rowsItem.isSelected());
            cellsItem.setSelected(table.getCellSelectionEnabled());
        });
        selectionMenu.add(rowsItem);

        columnsItem.addActionListener(event -> {
            table.clearSelection();
            table.setColumnSelectionAllowed(columnsItem.isSelected());
            cellsItem.setSelected(table.getCellSelectionEnabled());
        });
        selectionMenu.add(columnsItem);

        cellsItem.addActionListener(event -> {
            table.clearSelection();
            table.setCellSelectionEnabled(cellsItem.isSelected());
            rowsItem.setSelected(table.getRowSelectionAllowed());
            columnsItem.setSelected(table.getColumnSelectionAllowed());
        });
        selectionMenu.add(cellsItem);

        JMenu tableMenu = new JMenu("Edit");
        menuBar.add(tableMenu);

        JMenuItem hideColumnsItem = new JMenuItem("Hide Columns");
        hideColumnsItem.addActionListener(event -> {
            int[] selected = table.getSelectedColumns();
            TableColumnModel columnModel = table.getColumnModel();

            // удалить столбцы из представления, начиная с последнего
            // индекса, чтобы номера столбцов не затрагивались

            for (int i = selected.length - 1; i >= 0; i--) {
                TableColumn column = columnModel.getColumn(selected[i]);
                table.removeColumn(column);

                // сохранить удаленные столбцы для команды Show columns

                removedColumns.add(column);
            }
        });
        tableMenu.add(hideColumnsItem);

        JMenuItem showColumnsItem = new JMenuItem("Show Columns");
        showColumnsItem.addActionListener(event -> {
            // восстановить все удаленные столбцы
            for (TableColumn tc : removedColumns)
                table.addColumn(tc);
            removedColumns.clear();
        });
        tableMenu.add(showColumnsItem);

        JMenuItem addRowItem = new JMenuItem("Add Row");
        addRowItem.addActionListener(event -> {
            // добавить новую строку в таблицу умножения
            // в модели

            Integer[] newCells = new Integer[model.getColumnCount()];
            for (int i = 0; i < newCells.length; i++)
                newCells[i] = (i + 1) * (model.getRowCount() + 1);
            model.addRow(newCells);
        });
        tableMenu.add(addRowItem);

        JMenuItem removeRowsItem = new JMenuItem("Remove Rows");
        removeRowsItem.addActionListener(event -> {
            int[] selected = table.getSelectedRows();

            for (int i = selected.length - 1; i >= 0; i--)
                model.removeRow(selected[i]);
        });
        tableMenu.add(removeRowsItem);

        JMenuItem clearCellsItem = new JMenuItem("Clear Cells");
        clearCellsItem.addActionListener(event -> {
            for (int i = 0; i < table.getRowCount(); i++)
                for (int j = 0; j < table.getColumnCount(); j++)
                    if (table.isCellSelected(i, j)) table.setValueAt(0, i, j);
        });
        tableMenu.add(clearCellsItem);
    }
}