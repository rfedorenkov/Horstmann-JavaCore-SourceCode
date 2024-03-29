package corejava.v2ch10.treeEdit;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;

/**
 * Фрейм с деревом и кнопками для его редактирования
 */
public class TreeEditFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 200;

    private DefaultTreeModel model;
    private JTree tree;

    public TreeEditFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        // построить дерево

        TreeNode root = makeSampleTree();
        model = new DefaultTreeModel(root);
        tree = new JTree(model);
        tree.setEditable(true);

        // ввести прокручиваемую панель с деревом

        JScrollPane scrollPane = new JScrollPane(tree);
        add(scrollPane, BorderLayout.CENTER);

        makeButtons();
    }

    public TreeNode makeSampleTree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("World");
        DefaultMutableTreeNode country = new DefaultMutableTreeNode("USA");
        root.add(country);
        DefaultMutableTreeNode state = new DefaultMutableTreeNode("California");
        country.add(state);
        DefaultMutableTreeNode city = new DefaultMutableTreeNode("San Jose");
        state.add(city);
        city = new DefaultMutableTreeNode("San Diego");
        state.add(city);
        state = new DefaultMutableTreeNode("Michigan");
        country.add(state);
        city = new DefaultMutableTreeNode("Ann Arbor");
        state.add(city);
        country = new DefaultMutableTreeNode("Germany");
        root.add(country);
        state = new DefaultMutableTreeNode("Schleswig-Holstein");
        country.add(state);
        city = new DefaultMutableTreeNode("Kiel");
        state.add(city);
        return root;
    }

    /**
     * Создает кнопки для ввода родственных,
     * дочерних узлов и их удаления
     */
    public void makeButtons() {
        JPanel panel = new JPanel();
        JButton addSiblingButton = new JButton("Add Sibling");
        addSiblingButton.addActionListener(event -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

            if (selectedNode == null) return;

            DefaultMutableTreeNode parent = (DefaultMutableTreeNode) selectedNode.getParent();

            if (parent == null) return;

            DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("New");

            int selectedIndex = parent.getIndex(selectedNode);
            model.insertNodeInto(newNode, parent, selectedIndex + 1);

            // отобразить теперь новый узел

            TreeNode[] nodes = model.getPathToRoot(newNode);
            TreePath path = new TreePath(nodes);
            tree.scrollPathToVisible(path);
        });
        panel.add(addSiblingButton);

        JButton addChildButton = new JButton("Add Child");
        addChildButton.addActionListener(event -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

            if (selectedNode == null) return;

            DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("New");
            model.insertNodeInto(newNode, selectedNode, selectedNode.getChildCount());

            // отобразить теперь новый узел
            TreeNode[] nodes = model.getPathToRoot(newNode);
            TreePath path = new TreePath(nodes);
            tree.scrollPathToVisible(path);
        });
        panel.add(addChildButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(event -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

            if (selectedNode != null && selectedNode.getParent() != null)
                model.removeNodeFromParent(selectedNode);
        });
        panel.add(deleteButton);
        add(panel, BorderLayout.SOUTH);
    }
}