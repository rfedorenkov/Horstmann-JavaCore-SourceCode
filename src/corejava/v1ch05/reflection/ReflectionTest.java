package corejava.v1ch05.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Scanner;

/**
 * В этой программе рефлексия применяется для вывода
 * всех компонентов класса
 * @version 1.1 2004-02-21
 * @author Cay Horstmann
 */
public class ReflectionTest {
    public static void main(String[] args) {
        // извлечь имя класса из аргументов командной строки или
        // введенных пользователем данных
        String name;
        if (args.length > 0) name = args[0];
        else {
            Scanner in = new Scanner(System.in);
            System.out.print("Enter class name (e.g. java.util.Date): ");
            name = in.next();
        }
        System.out.println();

        try {
            // вывести имя класса и суперкласса (if != Object)
            Class<?> cl = Class.forName(name);
            Class<?> superclass = cl.getSuperclass();
            String modifiers = Modifier.toString(cl.getModifiers());
            if (modifiers.length() > 0) System.out.print(modifiers + " ");
            System.out.print("class " + name);
            if (superclass != null && superclass != Object.class)
                System.out.print(" extends " + superclass.getName());
            System.out.print("\n{\n");
            printConstructors(cl);
            System.out.println();
            printMethods(cl);
            System.out.println();
            printFields(cl);
            System.out.println("}");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    /**
     * Выводит всех конструкторы класса
     * @param cl a class
     */
    public static void printConstructors(Class<?> cl) {
        Constructor<?>[] constructors = cl.getDeclaredConstructors();

        for (Constructor<?> c : constructors) {
            String name = c.getName();
            System.out.print("   ");
            String modifiers = Modifier.toString(c.getModifiers());
            if (modifiers.length() > 0) System.out.print(modifiers + " ");
            System.out.print(name + "(");

            // вывести типы параметров
            Class<?>[] paramTypes = c.getParameterTypes();
            for (int j = 0; j < paramTypes.length; j++) {
                if (j > 0) System.out.print(", ");
                System.out.print(paramTypes[j].getName());
            }
            System.out.println(");");
        }
    }

    /**
     * Выводит все методы класса
     * @param cl a class
     */
    public static void printMethods(Class<?> cl) {
        Method[] methods = cl.getDeclaredMethods();
        for (Method m : methods) {
            Class<?> returnType = m.getReturnType();
            String name = m.getName();

            System.out.print("   ");
            // вывести модификаторы, возвращаемый тип и имя метода
            String modifiers = Modifier.toString(m.getModifiers());
            if (modifiers.length() > 0) System.out.print(modifiers + " ");
            System.out.print(returnType.getName() + " " + name + "(");

            // вывести типы параметров
            Class<?>[] paramTypes = m.getParameterTypes();
            for (int j = 0; j < paramTypes.length; j++) {
                if (j > 0) System.out.print(", ");
                System.out.print(paramTypes[j].getName());
            }
            System.out.println(");");
        }
    }

    /**
     * Выводит все поля класса
     * @param cl a class
     */
    public static void printFields(Class<?> cl) {
        Field[] fields = cl.getDeclaredFields();

        for (Field f : fields) {
            Class<?> type = f.getType();
            String name = f.getName();
            System.out.print("   ");
            String modifiers = Modifier.toString(f.getModifiers());
            if (modifiers.length() > 0) System.out.print(modifiers + " ");
            System.out.println(type.getName() + " " + name + ";");
        }
    }
}