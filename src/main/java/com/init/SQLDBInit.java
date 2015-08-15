package com.init;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLDBInit {
    private static boolean isInit = false;
    private final static String dropDB = "DROP DATABASE IF EXISTS test;";
    private final static String createDB = "CREATE DATABASE test DEFAULT CHARACTER SET 'utf8';";
    private final static String useDB = "USE test;";

    // запрос для создания таблицы
    private final static String createTableDB =
            "CREATE TABLE `users` (" +
                    "`id` int(8) NOT NULL AUTO_INCREMENT,  " +
                    "`name` varchar(25) NOT NULL,  " +
                    "`age` int(1) NOT NULL, " +
                    "`isAdmin` bit(1) , " +
                    "`createdDate` TIMESTAMP NOT NULL , " +
                    "PRIMARY KEY (`id`) " +
                    ") ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;";

    // запросы для наполнения таблицы тестовыми данными
    private final static String headerAddUsersToDB = "insert into users (name, age, isAdmin, createdDate) values ";
    private final static String addUsersToDB[] = {
            headerAddUsersToDB +"('Ivan', '1', true, '1990-03-20'); ",
            headerAddUsersToDB +"('Sanjar', '2', false, '1990-03-20');" ,
            headerAddUsersToDB +"('Dmitro', '3', false, '1990-03-20');" ,
            headerAddUsersToDB +"('Mikita', '4', false, '1990-03-20');" ,
            headerAddUsersToDB +"('Altyn', '5', false, '1990-03-20');" ,
            headerAddUsersToDB +"('Sardor', '6', false, '1990-03-20');" ,
            headerAddUsersToDB +"('Balshabek', '7', false, '1990-03-20');" ,
            headerAddUsersToDB +"('Abdullo', '8', false, '1990-03-20');" ,
            headerAddUsersToDB +"('Adil', '9', false, '1990-03-20');" ,
            headerAddUsersToDB +"('Zurabi', '10', false, '1990-03-20');" ,
            headerAddUsersToDB +"('Andrius', '11', false, '1990-03-20');" ,
            headerAddUsersToDB +"('Gheorghe', '12', false, '1990-03-20');" ,
            headerAddUsersToDB +"('Kristal', '13', false, '1990-03-20');" ,
            headerAddUsersToDB +"('Merdan', '14', false, '1990-03-20');" ,
            headerAddUsersToDB +"('Mattias', '15', false, '1990-03-20');" };

    public SQLDBInit() {
        if (isInit) return;
        isInit = true;
        Connection connection = null;
        Statement statement = null;
        try {
            //Загружаем драйвер
            Class.forName("com.mysql.jdbc.Driver");

            // варим стэйтмент для отправки запросов нашей БД
            String url = "jdbc:mysql://localhost:3306/";
            connection = DriverManager.getConnection(url, "root", "root");
            statement = connection.createStatement();

            // прибьем старую базу
            statement.executeUpdate(dropDB);
            // создадим нову.
            statement.executeUpdate(createDB);
            // и будем ее использовать
            statement.executeUpdate(useDB);
            // создадим таблицу в этой базе
            statement.executeUpdate(createTableDB);
            // и заполним ее данными
            for (String querie : addUsersToDB) {
                statement.executeUpdate(querie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //позакрываем теперь все
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
