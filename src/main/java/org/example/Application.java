package org.example;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Application {
    // Нужно указывать базовое исключение,
    // потому что выполнение запросов может привести к исключениям
    public static void main(String[] args) throws SQLException {
        // Создаем соединение с базой в памяти
        // База создается прямо во время выполнения этой строчки
        // Здесь hexlet_test — это имя базы данных
        try (var conn = DriverManager.getConnection("jdbc:h2:mem:hexlet_test")) {
            var sql = "CREATE TABLE users (id BIGINT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255), phone VARCHAR(255))";
            // Чтобы выполнить запрос, создадим объект statement
            try (var statement = conn.createStatement()) {
                statement.execute(sql);
            }
            // Закрывать statement не надо, так как try-with-resources

            var sql2 = "INSERT INTO users (username, phone) VALUES ('tommy', '123456789')";
            try (var statement2 = conn.createStatement()) {
                statement2.executeUpdate(sql2);
            }
            // Закрывать statement не надо, так как try-with-resources

            var sql3 = "SELECT * FROM users";
            try (var statement3 = conn.createStatement()) {
                // Здесь вы видите указатель на набор данных в памяти СУБД
                var resultSet = statement3.executeQuery(sql3);
                // Набор данных — это итератор
                // Мы перемещаемся по нему с помощью next() и каждый раз получаем новые значения
                while (resultSet.next()) {
                    System.out.println(resultSet.getString("username"));
                    System.out.println(resultSet.getString("phone"));
                }
                // Закрывать statement не надо, так как try-with-resources
            }
        }

        // Закрывать соединение не надо, так как try-with-resources
    }
}