package corejava.v1ch10.simpleFrame;

import javax.swing.*;

/**
 * @version 1.33 2015-05-12
 * @author Cay Horstmann
 */
public class SimpleFrameTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimpleFrame frame = new SimpleFrame();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

class SimpleFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    public SimpleFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
}