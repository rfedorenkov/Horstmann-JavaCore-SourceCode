package corejava.v2ch09.jaas;

import javax.swing.*;

/**
 * В этой программе сначала производится аутентификация
 * пользователя через специальную регистрацию, а затем
 * поиск системного свойства с назначенными для
 * пользователя привилегиями
 * @version 1.02 2016-05-10
 * @author Cay Horstmann
 */
public class JAASTest {
    public static void main(String[] args) {
        System.setSecurityManager(new SecurityManager());
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JAASFrame();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setTitle("JAASTest");
            frame.setVisible(true);
        });
    }
}