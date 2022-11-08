package corejava.v2ch10.tree;

import javax.swing.*;

/**
 * Эта программа показывает простое дерево
 * @version 1.03 2016-05-10
 * @author Cay Horstmann
 */
public class SimpleTree {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new SimpleTreeFrame();
            frame.setTitle("SimpleTree");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}