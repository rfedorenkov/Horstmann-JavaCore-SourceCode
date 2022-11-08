package corejava.v2ch10.list;

import javax.swing.*;
import java.awt.*;

/**
 * Этот фрейм содержит список слов и метку, отображающую
 * предложение, составляемое из слов, выбираемых из данного
 * списка. Чтобы выбрать сразу несколько слов из списка, можно
 * воспользоваться комбинациями <Ctrl+щелчок> <Shift+щелчок>
 */
public class ListFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 300;

    private JPanel listPanel;
    private JList<String> wordList;
    private JLabel label;
    private JPanel buttonPanel;
    private ButtonGroup group;
    private String prefix = "The ";
    private String suffix = "fox jumps over the lazy dog.";

    public ListFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        String[] words = { "quick", "brown", "hungry", "wild", "silent", "huge", "private",
                "abstract", "static",  "final" };

        wordList = new JList<>(words);
        wordList.setVisibleRowCount(4); // отобразить четыре элемента списка
        JScrollPane scrollPane = new JScrollPane(wordList);

        listPanel = new JPanel();
        listPanel.add(scrollPane);
        wordList.addListSelectionListener(event -> {
            StringBuilder text = new StringBuilder(prefix);
            for (String value : wordList.getSelectedValuesList()) {
                text.append(value);
                text.append(" ");
            }
            text.append(suffix);

            label.setText(text.toString());
        });

        buttonPanel = new JPanel();
        group = new ButtonGroup();
        makeButton("Vertical", JList.VERTICAL);
        makeButton("Vertical Wrap", JList.VERTICAL_WRAP);
        makeButton("Horizontal Wrap", JList.HORIZONTAL_WRAP);

        add(listPanel, BorderLayout.NORTH);
        label = new JLabel(prefix + suffix);
        add(label, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Создает кнопку-переключатель для выбора варианта
     * расположения элементов в списке
     * @param label Метка кнопки
     * @param orientation Вариант расположения элементов в списке
     */
    private void makeButton(String label, final int orientation) {
        JRadioButton button = new JRadioButton(label);
        buttonPanel.add(button);
        if (group.getButtonCount() == 0) button.setSelected(true);
        group.add(button);
        button.addActionListener(event -> {
            wordList.setLayoutOrientation(orientation);
            listPanel.revalidate();
        });
    }
}