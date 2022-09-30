package corejava.v2ch03.xpath;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.StringJoiner;

/**
 * В этой программе вычисляются выражения XPath
 * @version 1.02 2016-05-10
 * @author Cay Horstmann
 */
public class XPathTester {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new XPathFrame();
            frame.setTitle("XPathTester");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

/**
 * В этом фрейме отображается XML-документ, панель для ввода
 * выражений XPath, а также текстовое поле для вывода результата
 */
class XPathFrame extends JFrame {
    private DocumentBuilder builder;
    private Document doc;
    private XPath path;
    private JTextField expression;
    private JTextField result;
    private JTextArea docText;
    private JComboBox<String> typeCombo;

    public XPathFrame() {
        JMenu fileMenu = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(event -> openFile());
        fileMenu.add(openItem);

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(event -> System.exit(0));
        fileMenu.add(exitItem);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        ActionListener listener = event -> evaluate();
        expression = new JTextField(20);
        expression.addActionListener(listener);
        JButton evaluateButton = new JButton("Evaluate");
        evaluateButton.addActionListener(listener);

        typeCombo = new JComboBox<>(new String[] {
                "STRING", "NODE", "NODESET", "NUMBER", "BOOLEAN"});
        typeCombo.setSelectedItem("STRING");

        JPanel panel = new JPanel();
        panel.add(expression);
        panel.add(typeCombo);
        panel.add(evaluateButton);
        docText = new JTextArea(10, 40);
        result = new JTextField();
        result.setBorder(new TitledBorder("Result"));

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(docText), BorderLayout.CENTER);
        add(result, BorderLayout.SOUTH);

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            JOptionPane.showMessageDialog(this, e);
        }

        XPathFactory xpfactory = XPathFactory.newInstance();
        path = xpfactory.newXPath();
        pack();
    }

    /**
     * Открыть файл и загрузить документ
     */
    public void openFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("xpath"));

        chooser.setFileFilter(new FileNameExtensionFilter("XML files", "xml"));
        int r = chooser.showOpenDialog(this);
        if (r != JFileChooser.APPROVE_OPTION) return;
        File file = chooser.getSelectedFile();
        try {
            docText.setText(new String(Files.readAllBytes(file.toPath())));
            doc = builder.parse(file);
        } catch (IOException | SAXException e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    public void evaluate() {
        try {
            String typeName = (String) typeCombo.getSelectedItem();
            QName returnType = (QName) XPathConstants.class.getField(typeName).get(null);
            Object evalResult = path.evaluate(expression.getText(), doc, returnType);
            if (typeName.equals("NODESET")) {
                NodeList list = (NodeList) evalResult;
                // Нельзя воспользоваться методом String.join()
                // т.к. объект типа NodeList не является итерируемым
                StringJoiner joiner = new StringJoiner(",", "{", "}");
                for (int i = 0; i < list.getLength(); i++)
                    joiner.add("" + list.item(i));
                result.setText("" + joiner);
            } else result.setText("" + evalResult);
        } catch (XPathExpressionException e) {
            result.setText("" + e);
        } catch (Exception e) { // исключение при рефлексии
            e.printStackTrace();
        }
    }
}