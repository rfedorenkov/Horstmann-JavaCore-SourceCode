package corejava.v1ch13.resource;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @version 1.41 2015-06-12
 * @author Cay Horstmann
 */
public class ResourceTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new ResourceTestFrame();
            frame.setTitle("ResourceTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

/**
 * Фрейм, в котором загружаются ресурсы изображений и текста
 */
class ResourceTestFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 300;

    public ResourceTestFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        URL aboutURL = getClass().getResource("about.gif");
        Image img = new ImageIcon(aboutURL).getImage();
        setIconImage(img);

        JTextArea textArea = new JTextArea();
        InputStream stream = getClass().getResourceAsStream("about.txt");
        try (Scanner in = new Scanner(stream, StandardCharsets.UTF_8)) {
            while (in.hasNext())
                textArea.append(in.nextLine() + "\n");
        }
        add(textArea);
    }
}