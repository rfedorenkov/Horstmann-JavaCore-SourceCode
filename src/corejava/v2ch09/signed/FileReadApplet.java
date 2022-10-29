package corejava.v2ch09.signed;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Этот аплет можно выполнять вне "песочницы", чтобы читать
 * локальные файлы, как только он наделяется нужными полномочиями
 * @version 1.13 2016-05-10
 * @author Cay Horstmann
 */
public class FileReadApplet extends JApplet {
    private JTextField fileNameField;
    private JTextArea fileText;

    @Override
    public void init() {
        SwingUtilities.invokeLater(() -> {
            fileNameField = new JTextField(20);
            JPanel panel = new JPanel();
            panel.add(new JLabel("File name:"));
            panel.add(fileNameField);
            JButton openButton = new JButton("Open");
            panel.add(openButton);
            ActionListener listener = event -> loadFile(fileNameField.getText());
            fileNameField.addActionListener(listener);
            openButton.addActionListener(listener);
            add(panel, "North");
            fileText = new JTextArea();
            add(new JScrollPane(fileText), "Center");
        });
    }

    /**
     * Загружает содержимое файла в текстовую область
     * @param filename Имя файла
     */
    public void loadFile(String filename) {
        fileText.setText("");
        try {
            fileText.append(new String(Files.readAllBytes(Paths.get(filename))));
        } catch (IOException ex) {
            fileText.append(ex + "\n");
        } catch (SecurityException ex) {
            fileText.append("I am sorry, but I cannot do that.\n");
            fileText.append(ex + "\n");
            ex.printStackTrace();
        }
    }
}