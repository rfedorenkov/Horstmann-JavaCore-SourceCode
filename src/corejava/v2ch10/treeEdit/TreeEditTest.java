package corejava.v2ch10.treeEdit;

import javax.swing.*;

/**
 * Эта программа демонстрирует редактирование дерева
 * @version 1.04 2016-05-10
 * @author Cay Horstmann
 */
public class TreeEditTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new TreeEditFrame();
            frame.setTitle("TreeEditTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}