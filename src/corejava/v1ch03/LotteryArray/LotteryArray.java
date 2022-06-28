package corejava.v1ch03.LotteryArray;

/**
 * В этой программе демонстрируется применение треугольного массива
 * @version 1.10 2004-02-10
 * @author Cay Horstmann
 */
public class LotteryArray {
    public static void main(String[] args) {
        final int NMAX = 10;

        // выделить память под треугольный массив
        int[][] odds = new int[NMAX + 1][];
        for (int n = 0; n < NMAX + 1; n++)
            odds[n] = new int[n + 1];

        // заполнить треугольный массив
        for (int n = 0; n < odds.length; n++)
            for (int k = 0; k < odds[n].length; k++) {
                /*
                 * вычислить биномиальный коэффициент по формуле:
                 * n*(n-1)*(n-2)*...*(n-k+1)/(1*2*3*...*k)
                 */
                int lotteryOdds = 1;
                for (int i = 1; i <= k; i++)
                    lotteryOdds = lotteryOdds * (n - i + 1) / i;

                odds[n][k] = lotteryOdds;
            }

        // вывести треугольный массив
        for (int[] row : odds) {
            for (int odd : row) {
                System.out.printf("%4d", odd);
            }
            System.out.println();
        }
    }
}