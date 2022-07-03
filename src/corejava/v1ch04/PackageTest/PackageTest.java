package corejava.v1ch04.PackageTest;

import corejava.v1ch04.PackageTest.com.horstmann.corejava.Employee;
// В этом пакете определен класс Employee

import static java.lang.System.out;

/**
 * В этой программе демонстрируется применение пакетов
 * @version 1.11 2004-02-19
 * @author Cay Horstmann
 */
public class PackageTest {
    public static void main(String[] args) {
        // здесь не нужно указывать полное имя
        // класса com.hortstmann.corejava.Employee
        // поскольку используется оператор import
        Employee harry = new Employee("Harry Hacker", 50000, 1989, 10, 1);

        harry.raiseSalary(5);

        // здесь не нужно указывать полное имя System.out
        // поскольку используется оператор static import
        out.println("name=" + harry.getName() + ",salary=" + harry.getSalary());
    }
}
