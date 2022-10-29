package corejava.v2ch09.verifier;

import java.applet.Applet;
import java.awt.*;

/**
 * В этой прикладной программе демонстрируется верификатор
 * байт-кода виртуальной машины Java. Если воспользоваться
 * шестнадцатеричным редактором для видоизменения файла класса,
 * то виртуальная машина Java должна обнаружить злонамеренное
 * искажение содержимого файла класса
 */
public class VerifierTest extends Applet {
    public static void main(String[] args) {
        System.out.println("1 + 2 == " + fun());
    }

    /**
     * Функция, вычисляющая сумму чисел 1 + 2
     * @return Возвращает сумму 3, если код не нарушен
     */
    public static int fun() {
        int m;
        int n;
        m = 1;
        n = 2;
        // воспользоваться шестнадцатеричным редактором, чтобы
        // изменить значение переменной m на 2 в файле класса
        int r = m + n;
        return r;
    }

    @Override
    public void paint(Graphics g) {
        g.drawString("1 + 2 == " + fun(), 20, 20);
    }
}