package corejava.v1ch05.arrayList;

import java.util.ArrayList;

/**
 * В этой программе демонстрируется применение класса ArrayList
 * @version 1.11 2012-01-26
 * @author Cay Horstmann
 */
public class ArrayListTest {
    public static void main(String[] args) {
        // заполнить списочный массив staff тремя
        // объектами типа Employee
        ArrayList<Employee> staff = new ArrayList<>();

        staff.add(new Employee("Carl Cracker", 75000, 1987, 12, 15));
        staff.add(new Employee("Harry Hacker", 50000, 1989, 10, 1));
        staff.add(new Employee("Tony Tester", 40000, 1990, 3, 15));

        // поднять всем работникам зарплату на 5%
        for (Employee e : staff)
            e.raiseSalary(5);

        // вывести данные обо всех объектах типа Employee
        for (Employee e : staff)
            System.out.println("name=" + e.getName() + ", salary=" + e.getSalary() + ",hireDay=" + e.getHireDay());
    }
}