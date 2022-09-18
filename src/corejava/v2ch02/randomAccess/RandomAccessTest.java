package corejava.v2ch02.randomAccess;

import java.io.*;
import java.time.LocalDate;

/**
 * @version 1.13 2016-07-11
 * @author Cay Horstmann
 */
public class RandomAccessTest {
    public static void main(String[] args) throws IOException {
        Employee[] staff = new Employee[3];

        staff[0] = new Employee("Carl Cracker", 75000, 1987, 12, 15);
        staff[1] = new Employee("Harry Hacker", 50000, 1989, 10, 1);
        staff[2] = new Employee("Tony Tester", 40000, 1990, 3, 15);

        try (DataOutputStream out = new DataOutputStream(
                new FileOutputStream("src/corejava/v2ch02/randomAccess/employee.dat"))) {
            // сохранить все записи о сотрудниках в файле employee.dat
            for (Employee e : staff) {
                writeData(out, e);
            }
        }

        try (RandomAccessFile in = new RandomAccessFile("src/corejava/v2ch02/randomAccess/employee.dat", "r")) {
            // извлечь все записи в новый массив

            // определить размер массива
            int n = (int) in.length() / Employee.RECORD_SIZE;
            Employee[] newStaff = new Employee[n];

            // прочитать записи о сотрудниках в обратном порядке
            for (int i = n - 1; i >= 0; i--) {
                newStaff[i] = new Employee();
                in.seek(i * Employee.RECORD_SIZE);
                newStaff[i] = readData(in);
            }

            // вывести вновь прочитанные записи о сотрудниках
            for (Employee e : newStaff)
                System.out.println(e);
        }
    }

    /**
     * Записывает сведения о сотрудниках в поток вывода данных
     * @param out Поток вывода данных
     * @param e Сотрудник
     */
    public static void writeData(DataOutputStream out, Employee e) throws IOException {
        DataIO.writeFixedString(e.getName(), Employee.NAME_SIZE, out);
        out.writeDouble(e.getSalary());

        LocalDate hireDay = e.getHireDay();
        out.writeInt(hireDay.getYear());
        out.writeInt(hireDay.getMonthValue());
        out.writeInt(hireDay.getDayOfMonth());
    }

    /**
     * Читает сведения о сотрудниках из потока ввода данных
     * @param in Поток ввода данных
     * @return Возвращает сотрудника
     */
    public static Employee readData(DataInput in) throws IOException {
        String name = DataIO.readFixedString(Employee.NAME_SIZE, in);
        double salary = in.readDouble();
        int y = in.readInt();
        int m = in.readInt();
        int d = in.readInt();
        return new Employee(name, salary, y, m - 1, d);
    }
}