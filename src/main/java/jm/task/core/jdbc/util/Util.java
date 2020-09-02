package jm.task.core.jdbc.util;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;


public class Util {

    private static Connection connection = null;

    public static Connection connectToDataBase() {
        MysqlDataSource dataSource = new MysqlDataSource();
        try {
            dataSource.setURL("jdbc:mysql://127.0.0.1:3306/testbase?useSSL=false&" +
                    "useLegacyDatetimeCode=false&serverTimezone=UTC");
            dataSource.setUser("user");
            dataSource.setPassword("user");
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
