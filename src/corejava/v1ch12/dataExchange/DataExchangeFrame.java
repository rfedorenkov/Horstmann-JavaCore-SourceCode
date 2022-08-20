package corejava.v1ch12.dataExchange;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Фрейм со строкой меню, при выборе команды File -> Connect
 * из которого появляется диалоговое окно для ввода пароля
 */
public class DataExchangeFrame extends JFrame {
    public static final int TEXT_ROWS = 20;
    public static final int TEXT_COLUMNS = 40;
    private PasswordChooser dialog = null;
    private JTextArea textArea;

    public DataExchangeFrame() {
        // сконструировать меню File

        JMenuBar mBar = new JMenuBar();
        setJMenuBar(mBar);
        JMenu fileMenu = new JMenu("File");
        mBar.add(fileMenu);

        // ввести в меню пункты Connect и Exit

        JMenuItem connectItem = new JMenuItem("Connect");
        connectItem.addActionListener(new ConnectAction());
        fileMenu.add(connectItem);

        // При выборе пункта меню Exit происходит выход из программы

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        textArea = new JTextArea(TEXT_ROWS, TEXT_COLUMNS);
        add(new JScrollPane(textArea));
        pack();
    }

    /**
     * При выполнении команды Connect появляется
     * диалоговое окно для ввода пароля
     */
    class ConnectAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // при первом обращении конструируется диалоговое окно

            if (dialog == null) dialog = new PasswordChooser();

            // установить значения по умолчанию
            dialog.setUser(new User("yourname", null));

            // показать диалоговое окно
            if (dialog.showDialog(DataExchangeFrame.this, "Connect")) {
                // Если пользователь подтвердил введенные данные,
                // извлечь их для последующей обработки
                User u = dialog.getUser();
                textArea.append("user name = " + u.getName() + ", password = "
                        + (new String(u.getPassword())) + "\n");
            }
        }
    }
}