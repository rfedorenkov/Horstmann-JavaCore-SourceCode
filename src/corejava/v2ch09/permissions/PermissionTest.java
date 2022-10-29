package corejava.v2ch09.permissions;

import javax.swing.*;
import java.awt.*;

/**
 * Этот класс демонстрирует применение специальных полномочий
 * типа WordCheckPermission
 * @version 1.04 2016-05-10
 * @author Cay Horstmann
 */
public class PermissionTest {
    public static void main(String[] args) {
        System.setProperty("java.security.policy", "permissions/PermissionTest.policy");
        System.setSecurityManager(new SecurityManager());

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new PermissionTestFrame();
            frame.setTitle("PermissionTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

/**
 * Этот фрейм содержит текстовое поле для ввода слов в текстовую
 * область, защищенную от вставки непристойных слов
 */
class PermissionTestFrame extends JFrame {
    private JTextField textField;
    private WordCheckTextArea textArea;
    private static final int TEXT_ROWS = 20;
    private static final int TEXT_COLUMNS = 60;

    public PermissionTestFrame() {
        textField = new JTextField(20);
        JPanel panel = new JPanel();
        panel.add(textField);
        JButton openButton = new JButton("Insert");
        panel.add(openButton);
        openButton.addActionListener(event -> insertWords(textField.getText()));

        add(panel, BorderLayout.NORTH);

        textArea = new WordCheckTextArea();
        textArea.setRows(TEXT_ROWS);
        textArea.setColumns(TEXT_COLUMNS);
        add(new JScrollPane(textArea), BorderLayout.CENTER);
        pack();
    }

    /**
     * Пытается вставить слова в текстовую область.
     * Отображает диалоговое окно, если попытка окажется неудачной
     * @param words Вставляемые слова
     */
    public void insertWords(String words) {
        try {
            textArea.append(words + "\n");
        } catch (SecurityException ex) {
            JOptionPane.showMessageDialog(this, "I am sorry, but I cannot do that.");
            ex.printStackTrace();
        }
    }
}

/**
 * Текстовая область, в которой метод ввода текста
 * проверяет в целях безопасности, чтобы в нее не были
 * вставлены непристойные слова
 */
class WordCheckTextArea extends JTextArea {
    @Override
    public void append(String text) {
        WordCheckPermission p = new WordCheckPermission(text, "insert");
        SecurityManager manager = System.getSecurityManager();
        if (manager != null) manager.checkPermission(p);
        super.append(text);
    }
}