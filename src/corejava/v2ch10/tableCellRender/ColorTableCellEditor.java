package corejava.v2ch10.tableCellRender;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.EventHandler;
import java.util.EventObject;

/**
 * Этот редактор открывает диалоговое окно селектора цвета
 * для редактирования значения цвета в выбранной ячейке таблицы
 */
public class ColorTableCellEditor extends AbstractCellEditor implements TableCellEditor {
    private JColorChooser colorChooser;
    private JDialog colorDialog;
    private JPanel panel;

    public ColorTableCellEditor() {
        panel = new JPanel();
        // подготовить диалоговое окно выбора цвета

        colorChooser = new JColorChooser();
        colorDialog = JColorChooser.createDialog(null, "Planet Color", false, colorChooser,
                EventHandler.create(ActionListener.class, this, "stopCellEditing"),
                EventHandler.create(ActionListener.class, this, "cancelCellEditing"));
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
                                                 int row, int column) {
        // именно здесь получается текущее значение цвета,
        // сохраняемое в диалоговом окне на тот случай,
        // если пользователь начнет редактирование
        colorChooser.setColor((Color) value);
        return panel;
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        // начать редактирование
        colorDialog.setVisible(true);

        // уведомить вызывающую часть программы о том, что
        // эту ячейку разрешается выбрать
        return true;
    }

    @Override
    public void cancelCellEditing() {
        // редактирование отменено - скрыть диалоговое окно
        colorDialog.setVisible(false);
        super.cancelCellEditing();
    }

    @Override
    public boolean stopCellEditing() {
        // редактирование завершено - скрыть диалоговое окно
        colorDialog.setVisible(false);
        super.stopCellEditing();

        // уведомить вызывающую часть программы о том, что
        // данное значение цвета разрешается использовать
        return true;
    }

    @Override
    public Object getCellEditorValue() {
        return colorChooser.getColor();
    }
}