package org.example;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
//import org.example.UserDAO;

public class Application {
    // Нужно указывать базовое исключение,
    // потому что выполнение запросов может привести к исключениям
    public static void main(String[] args) throws SQLException {
        // Создаем соединение с базой в памяти
        // База создается прямо во время выполнения этой строчки
        // Здесь hexlet_test — это имя базы данных
        try (var conn = DriverManager.getConnection("jdbc:h2:mem:hexlet_test")) {
            var sql = "CREATE TABLE users (id BIGINT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255), " +
                    "phone VARCHAR(255))";
            // Чтобы выполнить запрос, создадим объект statement
            try (var statement = conn.createStatement()) {
                statement.execute(sql);
            }
            // Закрывать statement не надо, так как try-with-resources

            // Выполняем insert в таблицу с помощью DAO
            UserDAO dao_User = new UserDAO(conn);
            dao_User.save(new User("Tommy", "33333333"));
            dao_User.save(new User("Maria", "44444444"));
            dao_User.save(new User("Semen", "55555555"));

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

            var sql4 = "DELETE FROM users WHERE username = ?";
            try (var preparedStatement = conn.prepareStatement(sql4)) {
                preparedStatement.setString(1, "Maria");
                preparedStatement.executeUpdate();
            }
            System.out.println("After Delete");
            var sql5 = "SELECT * FROM users";
            try (var statement5 = conn.createStatement()) {
                // Здесь вы видите указатель на набор данных в памяти СУБД
                var resultSet = statement5.executeQuery(sql5);
                // Набор данных — это итератор
                // Мы перемещаемся по нему с помощью next() и каждый раз получаем новые значения
                while (resultSet.next()) {
                    System.out.println(resultSet.getString("username"));
                    System.out.println(resultSet.getString("phone"));
                }
                // Закрывать statement не надо, так как try-with-resources
            }

            var sql6 = "SELECT * FROM users WHERE username = ?";
            long id_find = 0L;
            try (var preparedStatement6 = conn.prepareStatement(sql6)) {
                preparedStatement6.setString(1, "Semen");
                var resultSet6 = preparedStatement6.executeQuery();
                while (resultSet6.next()) {
                    System.out.println(resultSet6.getString("username"));
                    System.out.println(resultSet6.getString("phone"));
                    System.out.println(resultSet6.getString("id"));
                    id_find = Long.parseLong(resultSet6.getString("id"));
                }
            }
            // поиск пользователя с определенным id c помощью DAO
            Optional<User> user_find = dao_User.find(id_find);
            System.out.println(user_find);

        }

        // Закрывать соединение не надо, так как try-with-resources
    }
}