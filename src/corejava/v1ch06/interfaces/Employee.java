package corejava.v1ch06.interfaces;

public class Employee implements Comparable<Employee> {
    private String name;
    private double salary;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public void raiseSalary(double byPercent) {
        double raise = salary * byPercent / 100;
        salary += raise;
    }

    /**
    * Сравнивает работников по зарплате
    * @param other другой объект типа Employee
    * @return отрицательное значение, если зарплата данного
     *        работника меньше, чем у другого работника;
     *        нулевое значение, если их зарплаты одинаковы;
     *        а иначе - положительное значение
    */
    @Override
    public int compareTo(Employee other) {
        return Double.compare(salary, other.salary);
    }
}