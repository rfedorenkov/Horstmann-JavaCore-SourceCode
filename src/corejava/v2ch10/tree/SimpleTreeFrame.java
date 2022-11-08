package corejava.v2ch10.tree;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Этот фрейм содержит простое дерево, отображающее
 * построенную вручную модель дерева
 */
public class SimpleTreeFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    public SimpleTreeFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        // подготовить данные для модели дерева

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("World");
        DefaultMutableTreeNode country = new DefaultMutableTreeNode("USA");
        root.add(country);
        DefaultMutableTreeNode state = new DefaultMutableTreeNode("California");
        country.add(state);
        DefaultMutableTreeNode city = new DefaultMutableTreeNode("San Jose");
        state.add(city);
        city = new DefaultMutableTreeNode("Cupertino");
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

        // построить дерево и разместить его
        // на прокручиваемой панели
        JTree tree = new JTree(root);
        add(new JScrollPane(tree));
    }
}