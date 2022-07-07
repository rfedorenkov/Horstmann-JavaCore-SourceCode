package corejava.v1ch05.arrays;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * В этой программе демонстрируется применение рефлексии
 * для манипулирования массивами
 * @version 1.2 2012-05-04
 * @author Cay Horstmann
 */
public class CopyOfTest {
    public static void main(String[] args) {
        int[] a = {1, 2, 3};
        a = (int[]) goodCopyOf(a, 10);
        System.out.println(Arrays.toString(a));

        String[] b = {"Tom", "Dick", "Harry"};
        b = (String[]) goodCopyOf(b, 10);
        System.out.println(Arrays.toString(b));

        System.out.println("Following call will generate an exception.");
        b = (String[]) badCopyOf(b, 10);
    }

    /**
     * В этом методе предпринимается попытка нарастить массив
     * путем выделения нового массива и копирования в него
     * всех прежних элементов
     * @param a Наращиваемый массив
     * @param newLength Новая длина массива
     * @return Возвращаемый наращенный массив, содержащий
     *         все элементы массива a, но он относится
     *         к типу Object[], а не к типу массива a
     */
    public static Object[] badCopyOf(Object[] a, int newLength) { // бесполезно
        Object[] newArray = new Object[newLength];
        System.arraycopy(a, 0, newArray, 0, Math.min(a.length, newLength));
        return newArray;
    }

    /**
     * Этот метод наращивает массив, выделяя новый массив
     * того же типа и копируя в него все прежние элементы
     * @param a Наращиваемый массив. Может быть массивом объектов
     *          или же массивом примитивных типов
     * @param newLength Новая длина массива
     * @return Возвращаемый наращенный массив, содержащий все
     *         элементы массива a
     */
    public static Object goodCopyOf(Object a, int newLength) {
        Class<?> cl = a.getClass();
        if (!cl.isArray()) return null;
        Class<?> componentType = cl.getComponentType();
        int length = Array.getLength(a);
        Object newArray = Array.newInstance(componentType, newLength);
        System.arraycopy(a, 0, newArray, 0, Math.min(length, newLength));
        return newArray;
    }
}