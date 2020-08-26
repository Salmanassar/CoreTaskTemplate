package jm.task.core.jdbc.util;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;


public class Util {
    public Connection connectToDataBase() {
        Connection connection = null;
        MysqlDataSource dataSource = new MysqlDataSource();
        try {
            dataSource.setURL("jdbc:mysql://127.0.0.1:3306/testbase?useSSL=false&" +
                    "useLegacyDatetimeCode=false&serverTimezone=UTC");
            dataSource.setUser("user");
            dataSource.setPassword("user");
            connection = dataSource.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
