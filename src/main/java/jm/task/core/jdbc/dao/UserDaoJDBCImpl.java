package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection;

    public UserDaoJDBCImpl() {
        connection = Util.connectToDataBase();
    }

    public void createUsersTable() throws SQLException {
        String createTable = "CREATE TABLE IF NOT EXISTS  user(\n" +
                "id int auto_increment primary key,\n" +
                "name varchar(45) NOT NULL,\n" +
                "lastName varchar(45) NOT NULL,\n" +
                "age int NOT NULL\n" +
                ");";
        executeStatement(createTable);
        System.out.println("Table is created");
    }

    public void dropUsersTable() throws SQLException {
        String dropTable = "DROP TABLE IF EXISTS user";
        executeStatement(dropTable);
        System.out.println("Table deleted");
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        String addUser = "INSERT INTO user(name , lastName, age)  VALUES (?, ?, ?)";
        connection.setAutoCommit(false);
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(addUser, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("User added " + name + " " + lastName + ", " + age + " years");
        } catch (Exception ex) {
            ex.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void removeUserById(long id) throws SQLException {
        connection.setAutoCommit(false);
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM user WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            connection.commit();
            System.out.println("Remove user by id " + id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT *  FROM user");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                byte age = resultSet.getByte(4);
                User user = new User(id, name, lastName, age);
                System.out.println(user.toString());
                users.add(user);
            }
        } catch (Exception throwable) {
            throwable.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() throws SQLException {
        String deleteAllUsers = "TRUNCATE TABLE user";
        executeStatement(deleteAllUsers);
        System.out.println("Delete all users");
    }

    private void executeStatement(String input) throws SQLException {
        connection.setAutoCommit(false);
        try (PreparedStatement preparedStatement = connection.prepareStatement(input)) {
            preparedStatement.execute();
            connection.commit();
        } catch (Exception throwable) {
            throwable.printStackTrace();
            connection.rollback();
            System.out.println("Transaction failed.");
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
