package corejava.v1ch12.toolBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Фрейм с панелью инструментов и строкой меню для выбора цвета
 */
public class ToolBarFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;
    private JPanel panel;

    public ToolBarFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        // ввести панель инструментов для выбора цвета

        panel = new JPanel();
        add(panel, BorderLayout.CENTER);

        // задать действия

        Action blueAction = new ColorAction("Blue",
                new ImageIcon("src/corejava/v1ch12/blue-ball.gif"),
                Color.BLUE);
        Action yellowAction = new ColorAction("Yellow",
                new ImageIcon("src/corejava/v1ch12/yellow-ball.gif"),
                Color.YELLOW);
        Action redAction = new ColorAction("Red",
                new ImageIcon("src/corejava/v1ch12/red-ball.gif"),
                Color.RED);

        Action exitAction = new AbstractAction("Exit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };
        exitAction.putValue(Action.SHORT_DESCRIPTION, "Exit");

        // заполнить панели инструментов

        JToolBar bar = new JToolBar();
        bar.add(blueAction);
        bar.add(yellowAction);
        bar.add(redAction);
        bar.addSeparator();
        bar.add(exitAction);
        add(bar, BorderLayout.NORTH);

        // заполнить меню

        JMenu menu = new JMenu("Color");
        menu.add(blueAction);
        menu.add(yellowAction);
        menu.add(redAction);
        menu.add(exitAction);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    /**
     * Действие изменения цвета, задающее выбранный цвет фона фрейма
     */
    class ColorAction extends AbstractAction {
        public ColorAction(String name, Icon icon, Color c) {
            putValue(Action.NAME, name);
            putValue(Action.SMALL_ICON, icon);
            putValue(Action.SHORT_DESCRIPTION, name + " background");
            putValue("Color", c);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Color c = (Color) getValue("Color");
            panel.setBackground(c);
        }
    }
}