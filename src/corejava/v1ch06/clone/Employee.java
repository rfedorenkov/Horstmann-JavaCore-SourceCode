package corejava.v1ch06.clone;

import java.util.Date;
import java.util.GregorianCalendar;

public class Employee implements Cloneable {
    private String name;
    private double salary;
    private Date hireDay;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
        hireDay = new Date();
    }

    @Override
    protected Employee clone() throws CloneNotSupportedException {
        // вызвать метод Object.clone()
        Employee cloned = (Employee) super.clone();

        // клонировать изменяемые поля
        cloned.hireDay = (Date) hireDay.clone();
        return cloned;
    }

    /**
    * Устанавливает заданную дату приема на работу
    * @param year Год приема на работу
    * @param month Месяц приема на работу
    * @param day День приема на работу
    */
    public void setHireDay(int year, int month, int day) {
        Date newHireDay = new GregorianCalendar(year, month - 1, day).getTime();

        // Пример изменения поля экземпляра
        hireDay.setTime(newHireDay.getTime());
    }

    public void raiseSalary(double byPercent) {
        double raise = salary * byPercent / 100;
        salary += raise;
    }

    @Override
    public String toString() {
        return "Employee[name=" + name + ",salary=" + salary + ",hireDay=" + hireDay + "]";
    }
}