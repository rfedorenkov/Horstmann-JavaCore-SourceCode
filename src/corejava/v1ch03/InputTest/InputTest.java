package corejava.v1ch03.InputTest;

import java.util.Scanner;

/**
 * Эта программа демонстрирует консольный ввод
 * @version 1.10 2004-02-10
 * @author Cay Horstmann
 */
public class InputTest {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // получить первую вводимую строку
        System.out.print("What is you name? ");
        String name = in.nextLine();

        // получить вторую вводимую строку
        System.out.print("How old are you? ");
        int age = in.nextInt();

        // вывести результат на консоль
        System.out.println("Hello, " + name + ". Next year, you'll be " + (age + 1));
        // Hello, Cay. Next year, you'll be 57
        // (Здравствуйте, Кей. В следующем году вам будет 57
        System.out.printf("Hello, %s. Next year, you'll be %d",name, (age + 1));
    }
}