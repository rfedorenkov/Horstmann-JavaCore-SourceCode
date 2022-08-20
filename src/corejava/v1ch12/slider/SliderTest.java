package corejava.v1ch12.slider;

import javax.swing.*;

/**
 * @version 1.15 2015-06-12
 * @author Cay Horstmann
 */
public class SliderTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new SliderFrame();
            frame.setTitle("SliderTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}