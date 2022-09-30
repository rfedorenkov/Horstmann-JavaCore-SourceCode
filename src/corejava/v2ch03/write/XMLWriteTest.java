package corejava.v2ch03.write;

import javax.swing.*;

/**
 * В этой программе демонстрируется запись XML-документа в файл.
 * Сохраняемый файл описывает модернистский рисунок в формате SVG
 * @version 1.12 2016-04-27
 * @author Cay Horstmann
 */
public class XMLWriteTest {
    public static void main(String[] args) {
        JFrame frame = new XMLWriteFrame();
        frame.setTitle("XMLWriteTest");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}