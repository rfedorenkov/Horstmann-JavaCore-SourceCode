package corejava.v1ch13.webstart;

import org.w3c.dom.ls.LSOutput;

import javax.jnlp.*;
import javax.swing.*;
import java.io.*;
import java.net.URL;

/**
 * Фрейм с панелью калькулятора и меню для загрузки и
 * сохранения предыстории калькуляций
 */
public class CalculatorFrame extends JFrame {
    private CalculatorPanel panel;

    public CalculatorFrame() {
        setTitle();
        panel = new CalculatorPanel();
        add(panel);

        JMenu fileMenu = new JMenu("File");
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        JMenuItem openItem = fileMenu.add("Open");
        openItem.addActionListener(e -> open());
        JMenuItem saveItem = fileMenu.add("Save");
        saveItem.addActionListener(e -> save());

        pack();
    }

    /**
     * Получает заголовок из постоянного хранилища или запрашивает
     * заголовок у пользователя, если он не был прежде сохранен
     */
    public void setTitle() {
        try {
            String title = null;

            BasicService basic = (BasicService) ServiceManager.lookup("javax.jnlp.BasicService");
            URL codeBase = basic.getCodeBase();

            PersistenceService service = (PersistenceService) ServiceManager.lookup("javax.jnlp.PersistenceService");
            URL key = new URL(codeBase, "title");

            try {
                FileContents contents = service.get(key);
                InputStream in = contents.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                title = reader.readLine();
            } catch (FileNotFoundException e) {
                title = JOptionPane.showInputDialog("Please supply a frame title:");
                if (title == null) return;

                service.create(key, 100);
                FileContents contents = service.get(key);
                OutputStream out = contents.getOutputStream(true);
                PrintStream printOut = new PrintStream(out);
                printOut.print(title);
            }
            setTitle(title);
        } catch (UnavailableServiceException | IOException e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    /**
     * Открывает файл предыстории и обновляет отображаемые данные
     */
    public void open() {
        try {
            FileOpenService service = (FileOpenService) ServiceManager.lookup("javax.jnlp.FileOpenService");
            FileContents contents = service.openFileDialog(".", new String[] { "txt" });

            JOptionPane.showMessageDialog(this, contents.getName());
            if (contents != null) {
                InputStream in = contents.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    panel.append(line);
                    panel.append("\n");
                }
            }
        } catch (IOException | UnavailableServiceException e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    /**
     * Открывает файл предыстории и обновляет отображаемые данные
     */
    public void save() {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PrintStream printOut = new PrintStream(out);
            printOut.print(panel.getText());
            InputStream data = new ByteArrayInputStream(out.toByteArray());
            FileSaveService service = (FileSaveService) ServiceManager.lookup("javax.jnlp.FileSaveService");
            service.saveFileDialog(".", new String[] { "txt" }, data, "calc.txt");
        } catch (IOException | UnavailableServiceException e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }
}