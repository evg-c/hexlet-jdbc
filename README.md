### Общие принципы JDBC

Работа с JDBC

### Как работает

Устанавливается зависимость в gradle:  implementation("com.h2database:h2:2.2.220").
Подключается: throws SQLException.
Создается соединение с БД H2 в памяти: var connection = DriverManager.getConnection("jdbc:h2:mem:hexlet_test").
Создается стэйтсмент: var statement = connection.createstatement().
Выполняется запрос к БД: var resultSet = statement3.executeQuery(sql3).
Закрывается стэйтсмент.
