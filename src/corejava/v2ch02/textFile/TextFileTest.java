package corejava.v2ch02.textFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * @version 1.14 2016-07-11
 * @author Cay Horstmann
 */
public class TextFileTest {
    public static void main(String[] args) throws IOException {
        Employee[] staff = new Employee[3];

        staff[0] = new Employee("Carl Cracker", 75000, 1987, 12, 15);
        staff[1] = new Employee("Harry Hacker", 50000, 1989, 10, 1);
        staff[2] = new Employee("Tony Tester", 40000, 1990, 3, 15);

        // сохранить записи обо всех сотрудниках в файле employee.dat
        try (PrintWriter out = new PrintWriter("src/corejava/v2ch02/textFile/employee.dat", StandardCharsets.UTF_8)) {
            writeData(staff, out);
        }

        // извлечь все записи в новый массив
        try (Scanner in = new Scanner(
                new FileInputStream("src/corejava/v2ch02/textFile/employee.dat"), StandardCharsets.UTF_8)) {
            Employee[] newStaff = readData(in);

            // вывести вновь прочитанные записи о сотрудниках
            for (Employee e : newStaff)
                System.out.println(e);
        }
    }

    /**
     * Записывает данные обо всех сотрудниках из
     * массива в поток записи выводимых данных
     * @param employees Массив записей о сотрудниках
     * @param out Поток записи выводимых данных
     */
    private static void writeData(Employee[] employees, PrintWriter out) {
        // записать количество сотрудников
        out.println(employees.length);

        for (Employee e : employees)
            writeEmployee(out, e);
    }

    /**
     * Читает записи о сотрудниках из потока сканирования в массив
     * @param in Поток сканирования вводимых данных
     * @return Массив записей о сотрудниках
     */
    private static Employee[] readData(Scanner in) {
        // извлечь размер массива
        int n = in.nextInt();
        in.nextLine(); // употребить символ новой строки

        Employee[] employees = new Employee[n];
        for (int i = 0; i < n; i++) {
            employees[i] = readEmployee(in);
        }
        return employees;
    }

    /**
     * Направляет данные о сотрудниках в поток
     * записи выводимых данных
     * @param out the print writer
     */
    public static void writeEmployee(PrintWriter out, Employee e) {
        out.println(e.getName() + "|" + e.getSalary() + "|" + e.getHireDay());
    }

    /**
     * Считывает данные о сотрудниках в поток
     * записи выводимых данных
     * @param in Поток чтения/сканирования вводимых данных
     */
    public static Employee readEmployee(Scanner in) {
        String line = in.nextLine();
        String[] tokens = line.split("\\|");
        String name = tokens[0];
        double salary = Double.parseDouble(tokens[1]);
        LocalDate hireDate = LocalDate.parse(tokens[2]);
        int year = hireDate.getYear();
        int month = hireDate.getMonthValue();
        int day = hireDate.getDayOfMonth();
        return new Employee(name, salary, year, month, day);
    }
}