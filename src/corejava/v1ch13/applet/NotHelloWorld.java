package corejava.v1ch13.applet;

import javax.swing.*;

/**
 * @version 1.24 2015-06-12
 * @author Cay Horstmann
 */
public class NotHelloWorld extends JApplet {

    @Override
    public void init() {
        SwingUtilities.invokeLater(() -> {
            JLabel label = new JLabel("Not a Hello, World applet", SwingConstants.CENTER);
            add(label);
        });
    }
}