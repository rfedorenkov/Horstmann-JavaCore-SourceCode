package corejava.v2ch03.read;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * В этой программе демонстрируется применение XML-документа
 * для описания сеточно-контейнерной компоновки
 * @version 1.12 2016-04-27
 * @author Cay Horstmann
 */
public class GridBagTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFileChooser chooser = new JFileChooser(".");
            chooser.showOpenDialog(null);
            File file = chooser.getSelectedFile();
            JFrame frame = new FontFrame(file);
            frame.setTitle("GridBagTest");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

/**
 * Этот фрейм содержит диалоговое окно для выбора шрифтов,
 * описываемое в XML-файле
 */
class FontFrame extends JFrame {
    private GridBagPane gridbad;
    private JComboBox<String> face;
    private JComboBox<String> size;
    private JCheckBox bold;
    private JCheckBox italic;

    /**
     * @param file Файл, содержащий компоненты пользовательского интерфейса,
     *             располагаемые в диалоговом окне
     */
    @SuppressWarnings("unchecked")
    public FontFrame(File file) {
        gridbad = new GridBagPane(file);
        add(gridbad);

        face = (JComboBox<String>) gridbad.get("face");
        size = (JComboBox<String>) gridbad.get("size");
        bold = (JCheckBox) gridbad.get("bold");
        italic = (JCheckBox) gridbad.get("italic");

        face.setModel(new DefaultComboBoxModel<>(new String[] { "Serif",
                "SansSerif", "Monospaced", "Dialog", "DialogInput" }));

        size.setModel(new DefaultComboBoxModel<>(new String[] { "8",
                "10", "12", "15", "18", "24", "36", "48"}));

        ActionListener listener = event -> setSample();

        face.addActionListener(listener);
        size.addActionListener(listener);
        bold.addActionListener(listener);
        italic.addActionListener(listener);

        setSample();
        pack();
    }

    /**
     * Этот метод выделяет образец текста выбранным шрифтом
     */
    public void setSample() {
        String fontFace = face.getItemAt(face.getSelectedIndex());
        int fontSize = Integer.parseInt(size.getItemAt(size.getSelectedIndex()));
        JTextArea sample = (JTextArea) gridbad.get("sample");
        int fontStyle = (bold.isSelected() ? Font.BOLD : 0)
                + (italic.isSelected() ? Font.ITALIC : 0);

        sample.setFont(new Font(fontFace, fontStyle, fontSize));
        sample.repaint();
    }
}