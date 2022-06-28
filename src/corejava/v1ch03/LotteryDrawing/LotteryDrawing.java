package corejava.v1ch03.LotteryDrawing;

import java.util.Arrays;
import java.util.Scanner;

/**
 * В этой программе демонстрируется обращение с массивами
 * @version 1.10 2004-02-10
 * @author Cay Horstmann
 */
public class LotteryDrawing {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.print("How many numbers do you need to draw? ");
        int k = in.nextInt();

        System.out.print("What is the highest number you can draw? ");
        int n = in.nextInt();

        // заполнить массив числами 1 2 3 . . . n
        int[] numbers = new int[n];
        for (int i = 0; i < numbers.length; i++)
            numbers[i] = i + 1;

        // выбрать k номеров и вывести их во второй массив
        int[] result = new int[k];
        for (int i = 0; i < result.length; i++) {
            // получить случайный индекс в пределах от 0 до n - 1
            int r = (int) (Math.random() * n);

            // выбрать элемент из произвольного места
            result[i] = numbers[r];

            // переместить последний элемент в произвольное место
            numbers[r] = numbers[n - 1];
            n--;
        }

        // вывести отсортированный массив
        Arrays.sort(result);
        // попробуйте следующую комбинацию, чтобы разбогатеть!
        System.out.println("Bet the following combination. It'll make you rich!");
        for (int r : result)
            System.out.println(r);

    }
}
