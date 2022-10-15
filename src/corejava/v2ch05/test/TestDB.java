package corejava.v2ch05.test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

/**
 * В этой программе проверяется правильность конфигурирования
 * базы данных и драйвера JDBC
 * @version 1.02 2012-06-05
 * @author Cay Horstmann
 */
public class TestDB {
    public static void main(String[] args) throws IOException {
        try {
            runTest();
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
    }

    /**
     * Выполняет тест, создавая таблицу, вводя в нее значение,
     * отображая содержимое таблицы и, наконец, удаляя ее
     * Runs a test by creating a table, adding a value, showing the table contents, and removing
     * the table.
     */
    public static void runTest() throws SQLException, IOException {
        try (Connection connection = getConnection();
             Statement stat = connection.createStatement()) {
            stat.executeUpdate("CREATE TABLE Greetings (Message CHAR(20))");
            stat.executeUpdate("INSERT INTO Greetings VALUES ('Hello, World!')");

            try (ResultSet result = stat.executeQuery("SELECT * FROM Greetings")) {
                if (result.next())
                    System.out.println(result.getString(1));
            }
            stat.executeUpdate("DROP TABLE Greetings");
        }
    }

    /**
     * Получает соединение с базой данных из свойств,
     * определенных в файле database.properties
     * @return Возвращает соединение с базой данных
     */
    public static Connection getConnection() throws SQLException, IOException {
        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get("database.properties"))) {
            props.load(in);
        }
        String drivers = props.getProperty("jdbc.drivers");
        if (drivers != null) System.setProperty("jdbc.drivers", drivers);
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        return DriverManager.getConnection(url, username, password);
    }
}