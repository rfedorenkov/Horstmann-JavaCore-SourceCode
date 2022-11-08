package corejava.v2ch10.treeModel;

import javax.swing.*;

/**
 * Эта программа демонстрирует, как использовать
 * пользовательскую модель дерева. Он отображает поля объекта.
 * @version 1.05 2016-05-10
 * @author Cay Horstmann
 */
public class ObjectInspectorTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new ObjectInspectorFrame();
            frame.setTitle("ObjectInspectorTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}