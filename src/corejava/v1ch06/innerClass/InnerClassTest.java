package corejava.v1ch06.innerClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * В этой программе демонстрируется применение внутренних классов
 * @version 1.11 2015-05-12
 * @author Cay Horstmann
 */
public class InnerClassTest {
    public static void main(String[] args) {
        TalkingClock clock = new TalkingClock(1000, true);
        clock.start();

        // выполнять программу до тех пор, пока пользователь
        // не щелкнет на кнопке "Ok"
        JOptionPane.showMessageDialog(null, "Quit program?");
        System.exit(0);
    }
}

/**
 * Часы, выводящие время через регулярные промежутки
 */
class TalkingClock {
    private int interval;
    private boolean beep;

    /**
    * Конструирует "говорящие часы"
    * @param interval Интервал между сообщениями (в миллисекундах)
    * @param beep Истинно, если часы должны издавать звуковой сигнал
    */
    public TalkingClock(int interval, boolean beep) {
        this.interval = interval;
        this.beep = beep;
    }

    /**
     * Запускает часы
     */
    public void start() {
        ActionListener listener = new TimePrinter();
        Timer t = new Timer(interval, listener);
        t.start();
    }

    public class TimePrinter implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("At the tone, the time is " + new Date());
            if (beep) Toolkit.getDefaultToolkit().beep();
        }
    }
}