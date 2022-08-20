package corejava.v1ch12.dialog;

import javax.swing.*;
import java.awt.*;

/**
 * Образец модального диалогового окна, в котором выводится сообщение
 * и ожидается до тех пор, пока пользователь не щелкнет на кнопке Ok
 */
public class AboutDialog extends JDialog {
    public AboutDialog(JFrame owner) {
        super(owner, "About DialogTest", true);

        // ввести HTML-метку по центру окна

        add(new JLabel("<html><h1><i>Core Java</i></h1><hr>By Cay Horstmann</html>"), BorderLayout.CENTER);

        // при выборе кнопки Ok диалоговое окно закрывается

        JButton ok = new JButton("Ok");
        ok.addActionListener(e -> setVisible(false));

        // ввести кнопку Ok в нижней части окна у южной его границы

        JPanel panel = new JPanel();
        panel.add(ok);
        add(panel, BorderLayout.SOUTH);

        pack();
    }
}