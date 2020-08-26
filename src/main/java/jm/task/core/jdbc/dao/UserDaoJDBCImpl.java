package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String createTable = "CREATE TABLE IF NOT EXISTS  user(\n" +
                "id int auto_increment primary key,\n" +
                "name varchar(45) NOT NULL,\n" +
                "lastName varchar(45) NOT NULL,\n" +
                "age int NOT NULL\n" +
                ");";
        executeStatement(createTable);
        System.out.println("Table is created");
    }

    public void dropUsersTable() {
        String dropTable = "DROP TABLE IF EXISTS user";
        executeStatement(dropTable);
        System.out.println("Table deleted");
    }

    public void saveUser(String name, String lastName, byte age) {
        String addUser = "INSERT INTO user" +
                "(name , lastName, age) " +
                " VALUES ('" + name + "', '" + lastName + "', '" + age + "')";
        executeStatement(addUser);
        System.out.println("User added " + name + " " + lastName + ", " + age + " years");
    }

    public void removeUserById(long id) {
        String deleteUserById = "DELETE FROM user WHERE id =" + id;
        executeStatement(deleteUserById);
        System.out.println("Remove user by id " + id);
    }

    public List<User> getAllUsers() {
        Util util = new Util();
        List<User> users = new ArrayList<>();
        try (Connection connection = util.connectToDataBase();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT *  FROM user")){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                byte age = resultSet.getByte(4);
                User user = new User(id, name, lastName, age);
                System.out.println(user.toString());
                users.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        String deleteAllUsers = "TRUNCATE TABLE user";
        executeStatement(deleteAllUsers);
        System.out.println("Delete all users");
    }

    private void executeStatement(String input) {
        Util util = new Util();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = util.connectToDataBase();
            statement = connection.createStatement();
            statement.execute(input);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            close(connection, statement);
        }
    }

    private void close(Connection connection, Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
