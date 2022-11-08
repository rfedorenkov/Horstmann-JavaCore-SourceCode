package corejava.v2ch10.treeModel;

import javax.swing.*;
import java.awt.*;

/**
 * Этот фрейм содержит дерево объектов
 */
public class ObjectInspectorFrame extends JFrame {
    private JTree tree;
    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT =300;

    public ObjectInspectorFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        // в данном фрейме обследуются объекты

        Variable v = new Variable(getClass(), "this", this);
        ObjectTreeModel model = new ObjectTreeModel();
        model.setRoot(v);

        // построить и показать дерево

        tree = new JTree(model);
        add(new JScrollPane(tree), BorderLayout.CENTER);
    }
}