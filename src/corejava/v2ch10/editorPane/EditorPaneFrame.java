package corejava.v2ch10.editorPane;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Stack;

/**
 * Этот фрейм содержит панель редактирования, текстовое поле и
 * кнопку для ввода URL и загрузки документа, а также кнопку
 * для возврата к предыдущему загруженному документу
 */
public class EditorPaneFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 400;

    public EditorPaneFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        final Stack<String> urlStack = new Stack<>();
        final JEditorPane editorPane = new JEditorPane();
        final JTextField url = new JTextField(30);

        // установить приемник событий от гиперссылок

        editorPane.setEditable(false);
        editorPane.addHyperlinkListener(event -> {
            if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                try {
                    // запомнить URL для кнопки возврата
                    urlStack.push(event.getURL().toString());
                    // показать URL в текстовом поле
                    url.setText(event.getURL().toString());
                    editorPane.setPage(event.getURL());
                } catch (IOException e) {
                    editorPane.setText("Exception: " + e);
                }
            }
        });

        // настроить флажок для переключения режима редактирования

        final JCheckBox editable = new JCheckBox();
        editable.addActionListener(event ->
                editorPane.setEditable(editable.isSelected()));

        // настроить кнопку для загрузки документа по заданному URL

        ActionListener listener = event -> {
            try {
                // запомнить URL для кнопки возврата
                urlStack.push(url.getText());
                editorPane.setPage(url.getText());
            } catch (IOException e) {
                editorPane.setText("Exception: " + e);
            }
        };

        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(listener);
        url.addActionListener(listener);

        // настроить кнопку возврата и ее действие

        JButton backButton = new JButton("Back");
        backButton.addActionListener(event -> {
            if (urlStack.size() <= 1) return;
            try {
                // получить URL из кнопки возврата
                urlStack.pop();
                // показать URL в текстовом поле
                String urlString = urlStack.peek();
                url.setText(urlString);
                editorPane.setPage(urlString);
            } catch (IOException e) {
                editorPane.setText("Exception: " + e);
            }
        });

        add(new JScrollPane(editorPane), BorderLayout.CENTER);

        // разместить все элементы управления на панели

        JPanel panel = new JPanel();
        panel.add(new JLabel("URL"));
        panel.add(url);
        panel.add(loadButton);
        panel.add(backButton);
        panel.add(new JLabel("Editable"));
        panel.add(editable);

        add(panel, BorderLayout.SOUTH);
    }
}