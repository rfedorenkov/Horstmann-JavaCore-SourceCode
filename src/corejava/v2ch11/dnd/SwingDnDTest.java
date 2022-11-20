package corejava.v2ch11.dnd;

import javax.swing.*;

/**
 * В этой программе демонстрируется элементарная
 * поддержка операций перетаскивания и опускания
 * объектов в библиотеке Swing
 * @version 1.11 2016-05-10
 * @author Cay Horstmann
 */
public class SwingDnDTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new SwingDnDFrame();
            frame.setTitle("SwingDnDTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}