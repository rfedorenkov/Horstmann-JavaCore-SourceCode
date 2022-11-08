package corejava.v2ch10.splitPane;

import javax.swing.*;
import java.awt.*;

/**
 * Этот фрейм состоит из двух вложенных разделяемых панелей для
 * показа изображения и краткого описания выбранной планеты
 */
public class SplitPaneFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 300;

    private Planet[] planets = { new Planet("Mercury", 2440, 0), new Planet("Venus", 6052, 0),
            new Planet("Earth", 6378, 1), new Planet("Mars", 3397, 2),
            new Planet("Jupiter", 71492, 16), new Planet("Saturn", 60268, 18),
            new Planet("Uranus", 25559, 17), new Planet("Neptune", 24766,8),
            new Planet("Pluto", 1137, 1) };

    public SplitPaneFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        // установить компоненты для названий, изображений
        // и кратких описаний планет

        final JList<Planet> planetList = new JList<>(planets);
        final JLabel planetImage = new JLabel();
        final JTextArea planetDescription = new JTextArea();

        planetList.addListSelectionListener(event -> {
            Planet value = (Planet) planetList.getSelectedValue();

            // обновить изображение и краткое описание планеты

            planetImage.setIcon(value.getImage());
            planetDescription.setText(value.getDescription());
        });

        // установить разделяемые панели

        JSplitPane innerPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, planetList, planetImage);

        innerPane.setContinuousLayout(true);
        innerPane.setOneTouchExpandable(true);

        JSplitPane outerPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, innerPane,
                planetDescription);

        add(outerPane, BorderLayout.CENTER);
    }
}