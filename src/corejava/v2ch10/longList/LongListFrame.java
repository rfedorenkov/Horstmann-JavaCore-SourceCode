package corejava.v2ch10.longList;

import javax.swing.*;
import java.awt.*;

/**
 * Этот фрейм содержит длинный список слов и метку, отображающую
 * предложение, составляемое из слов, выбираемых из данного списка
 * word.
 */
public class LongListFrame extends JFrame {
    private JList<String> wordList;
    private JLabel label;
    private String prefix = "The quick brown ";
    private String suffix = " jumps over the laze dog.";

    public LongListFrame() {
        wordList = new JList<>(new WordListModel(3));
        wordList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        wordList.setPrototypeCellValue("www");
        JScrollPane scrollPane = new JScrollPane(wordList);

        JPanel p = new JPanel();
        p.add(scrollPane);
        wordList.addListSelectionListener(event -> setSubject(wordList.getSelectedValue()));

        Container containerPane = getContentPane();
        containerPane.add(p, BorderLayout.NORTH);
        label = new JLabel(prefix + suffix);
        containerPane.add(label, BorderLayout.CENTER);
        setSubject("fox");
        pack();
    }

    /**
     * Устанавливает в метке новый субъект
     * @param word Новый субъект, перепрыгивающий
     *             через ленивую собаку
     */
    public void setSubject(String word) {
        StringBuilder text = new StringBuilder(prefix);
        text.append(word);
        text.append(suffix);
        label.setText(text.toString());
    }
}