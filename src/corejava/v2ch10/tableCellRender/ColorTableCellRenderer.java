package corejava.v2ch10.tableCellRender;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Это средство воспроизведения отображает цвет
 * в виде панели с заданным цветом
 */
public class ColorTableCellRenderer extends JPanel implements TableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        setBackground((Color) value);
        if (hasFocus) setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
        else setBorder(null);
        return this;
    }
}