package corejava.v2ch10.editorPane;

import javax.swing.*;

/**
 * Эта программа демонстрирует, как отображать HTML-документы в панели редактора
 * @version 1.05 2016-05-10
 * @author Cay Horstmann
 */
public class EditorPaneTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new EditorPaneFrame();
            frame.setTitle("EditorPaneTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}