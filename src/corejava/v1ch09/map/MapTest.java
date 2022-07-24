package corejava.v1ch09.map;

import java.util.HashMap;
import java.util.Map;

/**
 * This program demonstrates the use of a map with key type String and value type Employee.
 * В этой программе демонстрируется применение отображения
 * с ключами типа String и значениями типа Employee
 * @version 1.12 2015-06-21
 * @author Cay Horstmann
 */
public class MapTest {
    public static void main(String[] args) {
        Map<String, Employee> staff = new HashMap<>();
        staff.put("144-25-5464", new Employee("Amy Lee"));
        staff.put("567-24-2546", new Employee("Harry Hacker"));
        staff.put("157-62-7935", new Employee("Gary Cooper"));
        staff.put("456-62-5527", new Employee("Francesca Cruz"));

        // вывести все элементы из отображения
        System.out.println(staff);

        // удалить элемент из отображения
        staff.remove("567-24-2546");

        // заменить элемент из отображения
        staff.put("456-62-5527", new Employee("Francesca Miller"));

        // найти значение
        System.out.println(staff.get("157-62-7935"));

        // перебрать все элементы в отображении
        for (Map.Entry<String, Employee> entry : staff.entrySet()) {
            String key = entry.getKey();
            Employee value = entry.getValue();
            System.out.println("key=" + key + ", value=" + value);
        }
    }
}