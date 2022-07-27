package corejava.v1ch11.action;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Фрейм с панелью для демонстрации действий по изменению цвета
 */
public class ActionFrame extends JFrame {
    private JPanel buttonPanel;
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    public ActionFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        buttonPanel = new JPanel();

        // определить действия
        Action yellowAction = new ColorAction("Yellow",
                new ImageIcon("src/corejava/v1ch11/yellow-ball.gif"),
                Color.YELLOW);
        Action blueAction = new ColorAction("Blue",
                new ImageIcon("src/corejava/v1ch11/blue-ball.gif"),
                Color.BLUE);
        Action redAction = new ColorAction("Red",
                new ImageIcon("src/corejava/v1ch11/red-ball.gif"),
                Color.RED);

        // ввести экранные кнопки для этих действий
        buttonPanel.add(new JButton(yellowAction));
        buttonPanel.add(new JButton(blueAction));
        buttonPanel.add(new JButton(redAction));

        // добавить цвет по умолчанию
        buttonPanel.setBackground(Color.YELLOW);

        // ввести панель во фрейм
        add(buttonPanel);

        // привязать клавиши <Y>, <B>, <R> к надписям на кнопках
        InputMap imap = buttonPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        imap.put(KeyStroke.getKeyStroke("ctrl Y"), "panel.yellow");
        imap.put(KeyStroke.getKeyStroke("ctrl B"), "panel.blue");
        imap.put(KeyStroke.getKeyStroke("ctrl R"), "panel.red");

        // привязать надписи на кнопках панели к действиям
        ActionMap amap = buttonPanel.getActionMap();
        amap.put("panel.yellow", yellowAction);
        amap.put("panel.blue", blueAction);
        amap.put("panel.red", redAction);
    }

    public class ColorAction extends AbstractAction {

        /**
         * Конструирует действие по изменению цвета
         * @param name Надпись на экранной кнопке
         * @param icon Пиктограмма на экранной кнопке
         * @param c Цвет фона
         */
        public ColorAction(String name, Icon icon, Color c) {
            putValue(Action.NAME, name);
            putValue(Action.SMALL_ICON, icon);
            putValue(Action.SHORT_DESCRIPTION, "Set panel color to " + name.toLowerCase());
            putValue("color", c);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Color c = (Color) getValue("color");
            buttonPanel.setBackground(c);
        }
    }
}