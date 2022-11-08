package corejava.v2ch10.treeModel;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/**
 * Переменная с типом, именем и значением
 */
public class Variable {
    private Class<?> type;
    private String name;
    private Object value;
    private ArrayList<Field> fields;

    /**
     * Сконструировать переменную
     * @param aType Тип
     * @param aName Имя
     * @param aValue Значение
     */
    public Variable(Class<?> aType, String aName, Object aValue) {
        type = aType;
        name = aName;
        value = aValue;
        fields = new ArrayList<>();

        // найти все поля, если это тип класса, за исключением
        // символьных строк и пустых значений null

        if (!type.isPrimitive() && !type.isArray() && !type.equals(String.class) && value != null) {
            // получить поля из класса и всех его суперклассов
            for (Class<?> c = value.getClass(); c != null; c = c.getSuperclass()) {
                Field[] fs = c.getDeclaredFields();
                AccessibleObject.setAccessible(fs, true);

                // получить все нестатические поля
                for (Field f : fs)
                    if ((f.getModifiers() & Modifier.STATIC) == 0) fields.add(f);
            }
        }
    }

    /**
     * Получает значение данной переменной
     * @return Возвращает значение
     */
    public Object getValue() {
        return value;
    }

    /**
     * Получает все нестатические поля из данной переменной
     * @return Возвращает списочный массив переменных
     *         с описаниями полей
     */
    public ArrayList<Field> getFields() {
        return fields;
    }

    @Override
    public String toString() {
        String r = type + " " + name;
        if (type.isPrimitive()) r += "=" + value;
        else if (type.equals(String.class)) r += "=" + value;
        else if (value == null) r += "=null";
        return r;
    }
}