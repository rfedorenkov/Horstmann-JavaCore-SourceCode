package corejava.v1ch06.staticInnerClass;

/**
 * В этой программе демонстрируется применение
 * статического внутреннего класса
 * @version 1.02 2015-05-12
 * @author Cay Horstmann
 */
public class StaticInnerClassTest {
    public static void main(String[] args) {
        double[] d = new double[100];
        for (int i = 0; i < d.length; i++)
            d[i] = 100 * Math.random();
        ArrayAlg.Pair p = ArrayAlg.Pair.minmax(d);
        System.out.println("min = " + p.getFirst());
        System.out.println("max = " + p.getSecond());
    }
}

class ArrayAlg {

    /**
     * Пара чисел плавающей точкой
     */
    public static class Pair {
        private double first;
        private double second;

        /**
         * Составляет пару из двух чисел с плавающей точкой
         * @param f Первое число
         * @param s Второе число
         */
        public Pair(double f, double s) {
            first = f;
            second = s;
        }

        /**
         * Возвращает первое число из пары
         * @return Возврат первого числа
         */
        public double getFirst() {
            return first;
        }

        /**
         * Возвращает второе число из пары
         * @return Возврат второго числа
         */
        public double getSecond() {
            return second;
        }

        /**
         * Определяет минимальное и максимальное числа в массиве
         * @param values Массив чисел с плавающей точкой
         * @return Пара, первым элементом которой является минимальное
         *         число, а вторым элементом - максимальное число
         */
        public static Pair minmax(double[] values) {
            double min = Double.POSITIVE_INFINITY;
            double max = Double.NEGATIVE_INFINITY;
            for (double v : values) {
                if (min > v) min = v;
                if (max < v) max = v;
            }
            return new Pair(min, max);
        }
    }
}