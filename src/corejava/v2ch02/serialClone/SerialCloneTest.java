package corejava.v2ch02.serialClone;

import java.io.*;
import java.time.LocalDate;

/**
 * @version 1.21 13 Jul 2016
 * @author Cay Horstmann
 */
public class SerialCloneTest {
    public static void main(String[] args) throws CloneNotSupportedException {
        Employee harry = new Employee("Harry Hacker", 35000, 1989, 10, 1);
        // клонировать объект harry
        Employee harry2 = (Employee) harry.clone();

        // видоизменить объект harry
        harry.raiseSalary(10);

        // теперь оригинал и клон объекта harry отличаются
        System.out.println(harry);
        System.out.println(harry2);
    }
}

/**
 * Класс, в методе клонирования которого
 * применяется сериализация
 */
class SerialCloneable implements Cloneable, Serializable {
    @Override
    public Object clone() throws CloneNotSupportedException {
        try {
            // сохранить объект в байтовом массиве
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            try (ObjectOutputStream out = new ObjectOutputStream(bout)) {
                out.writeObject(this);
            }

            // ввести клон объекта из байтового массива
            try (InputStream bin = new ByteArrayInputStream(bout.toByteArray())) {
                ObjectInputStream in = new ObjectInputStream(bin);
                return in.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            CloneNotSupportedException e2 = new CloneNotSupportedException();
            e2.initCause(e);
            throw e2;
        }
    }
}

/**
 * Класс Employee, переопределяемый для расширения
 * класса SerialCloneable
 */
class Employee extends SerialCloneable {
    private String name;
    private double salary;
    private LocalDate hireDay;

    public Employee(String name, double salary, int year, int month, int day) {
        this.name = name;
        this.salary = salary;
        this.hireDay = LocalDate.of(year, month, day);
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public LocalDate getHireDay() {
        return hireDay;
    }

    /**
     * Поднимает зарплату данному работнику
     * @param byPercent Процент повышения зарплаты
     */
    public void raiseSalary(double byPercent) {
        double raise = salary * byPercent / 100;
        salary += raise;
    }

    @Override
    public String toString() {
        return getClass().getName()
                + "[name=" + name
                + ",salary=" + salary
                + ",hireDay=" + hireDay
                + "]";
    }
}