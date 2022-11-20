package corejava.v2ch11.print;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.*;
import java.awt.*;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

/**
 * В этом фрейме отображается панель с двумерной графикой и
 * кнопками для печати графики и установки формата страницы
 */
public class PrintTestFrame extends JFrame {
    private PrintComponent canvas;
    private PrintRequestAttributeSet attributes;

    public PrintTestFrame() {
        canvas = new PrintComponent();
        add(canvas, BorderLayout.CENTER);

        attributes = new HashPrintRequestAttributeSet();

        JPanel buttonPanel = new JPanel();
        JButton printButton = new JButton("Print");
        buttonPanel.add(printButton);
        printButton.addActionListener(event -> {
            try {
                PrinterJob job = PrinterJob.getPrinterJob();
                job.setPrintable(canvas);
                if (job.printDialog(attributes)) job.print(attributes);
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(PrintTestFrame.this, ex);
            }
        });

        JButton pageSetupButton = new JButton("Page setup");
        buttonPanel.add(pageSetupButton);
        pageSetupButton.addActionListener(event -> {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.pageDialog(attributes);
        });

        add(buttonPanel, BorderLayout.NORTH);
        pack();
    }
}