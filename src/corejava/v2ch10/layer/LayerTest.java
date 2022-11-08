package corejava.v2ch10.layer;

import javax.swing.*;

/**
 * Эта программа демонстрирует, как слой может украшать компонент Swing
 * @version 1.01 2016-05-10
 * @author Cay Horstmann
 */
public class LayerTest {
    public static void main(String[] args) {
        JFrame frame = new ColorFrame();
        frame.setTitle("LayerTest");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}