package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser(null, "Kowalski", (byte) 23);
        userService.saveUser("Petr", "Petrov", (byte) 41);
        userService.saveUser("Mary", "Schmidt", (byte) 31);
        userService.saveUser("Levy", "Kohen", (byte) 53);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
