package corejava.v2ch10.treeRender;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.lang.reflect.Modifier;

/**
 * Этот класс воспроизводит имя класса, выделяя его
 * простым шрифтов или курсивом. Абстрактные классы
 * выделяются только курсивом
 */
public class ClassNameTreeCellRenderer extends DefaultTreeCellRenderer {
    private Font plainFont = null;
    private Font italicFont = null;

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected,
                                                  boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
        // получить пользовательский объект
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        Class<?> c = (Class<?>) node.getUserObject();

        // сначала сделать простой шрифт наклонным
        if (plainFont == null) {
            plainFont = getFont();
            // средство воспроизведения ячеек дерева иногда
            // вызывается с меткой, имеющей пустой шрифт
            if (plainFont != null) italicFont = plainFont.deriveFont(Font.ITALIC);
        }

        // установить наклонный шрифт, если класс является
        // абстрактным, а иначе - простой шрифт
        if ((c.getModifiers() & Modifier.ABSTRACT) == 0)
            setFont(plainFont);
        else setFont(italicFont);
        return this;
    }
}