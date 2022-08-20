package corejava.v1ch12.dialog;

import javax.swing.*;

/**
 * Фрейм со строкой меню, при выборе команды File->About
 * из которого появляется диалоговое окно About
 */
public class DialogFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;
    private AboutDialog dialog;

    public DialogFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        // сконструировать меню File

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        // ввести в меню пункты About и Exit

        // при выборе пункта меню About открывается
        // одноименное диалоговое окно

        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> {
            if (dialog == null) // первый раз
                dialog = new AboutDialog(DialogFrame.this);
            dialog.setVisible(true); // показать диалоговое окно
        });
        fileMenu.add(aboutItem);

        // при выборе пункта меню Exit происходит выход из программы

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
    }
}