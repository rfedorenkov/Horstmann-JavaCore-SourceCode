package corejava.v2ch11.serialTransfer;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.Serializable;

/**
 * Этот фрейм содержит селектор цвета и кнопки
 * для копирования и вставки
 */
public class SerialTransferFrame extends JFrame {
    private JColorChooser chooser;

    public SerialTransferFrame() {
        chooser = new JColorChooser();
        add(chooser, BorderLayout.CENTER);
        JPanel panel = new JPanel();

        JButton copyButton = new JButton("Copy");
        panel.add(copyButton);
        copyButton.addActionListener(event -> copy());

        JButton pasteButton = new JButton("Paste");
        panel.add(pasteButton);
        pasteButton.addActionListener(event -> paste());

        add(panel, BorderLayout.SOUTH);
        pack();
    }

    /**
     * Копирует цвет селектора цвета в системный буфер
     */
    private void copy() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Color color = chooser.getColor();
        SerialTransferable selection = new SerialTransferable(color);
        clipboard.setContents(selection, null);
    }

    /**
     * Вставляет цвет из системного буфера обмена в селектор цвета
     */
    private void paste() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        try {
            DataFlavor flavor = new DataFlavor(
                    "application/x-java-serialized-object;class=java.awt.Color");
            if (clipboard.isDataFlavorAvailable(flavor)) {
                Color color = (Color) clipboard.getData(flavor);
                chooser.setColor(color);
            }
        } catch (ClassNotFoundException | UnsupportedFlavorException | IOException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }
}

/**
 * Этот класс служит оболочкой для передачи данных
 * в виде сериализируемых объектов
 */
class SerialTransferable implements Transferable {
    private Serializable obj;

    /**
     * Конструирует выбираемый объект
     * @param o Любой сериализируемый объект
     */
    SerialTransferable(Serializable o) {
        obj = o;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        DataFlavor[] flavors = new DataFlavor[2];
        Class<?> type = obj.getClass();
        String mimeType = "application/x-java-serialized-object;class=" + type.getName();
        try {
            flavors[0] = new DataFlavor(mimeType);
            flavors[1] = DataFlavor.stringFlavor;
            return flavors;
        } catch (ClassNotFoundException e) {
            return new DataFlavor[0];
        }
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return DataFlavor.stringFlavor.equals(flavor)
                || "application".equals(flavor.getPrimaryType())
                && "x-java-serialized-object".equals(flavor.getSubType())
                && flavor.getRepresentationClass().isAssignableFrom(obj.getClass());
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
        if (!isDataFlavorSupported(flavor)) throw new UnsupportedFlavorException(flavor);

        if (DataFlavor.stringFlavor.equals(flavor)) return obj.toString();

        return obj;
    }
}