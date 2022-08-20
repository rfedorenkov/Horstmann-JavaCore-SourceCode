package corejava.v1ch12.fileChooser;

import javax.swing.*;

/**
 * @version 1.25 2015-06-12
 * @author Cay Horstmann
 */
public class FileChooserTest {
    public static void main(String[] args) {
        JFrame frame = new ImageViewerFrame();
        frame.setTitle("FileChooserTest");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}