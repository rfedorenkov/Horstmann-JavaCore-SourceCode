package corejava.v1ch06.clone;

/**
 * В этой программе демонстрируется клонирование объектов
 * @version 1.10 2002-07-01
 * @author Cay Horstmann
 */
public class CloneTest implements Cloneable {
    public static void main(String[] args) {
        try {
            Employee original = new Employee("John Q. Public", 50000);
            original.setHireDay(2000, 1, 1);
            Employee copy = original.clone();
            copy.raiseSalary(10);
            copy.setHireDay(2002, 12, 31);
            System.out.println("original=" + original);
            System.out.println("copy=" + copy);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}