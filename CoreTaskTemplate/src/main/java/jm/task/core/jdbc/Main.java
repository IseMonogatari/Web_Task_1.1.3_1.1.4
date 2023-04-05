package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;


import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        User user1 = new User("Тит", "Меркулиус", (byte) 21);
        User user2 = new User("Пиотр", "Кучинский", (byte) 30);
        User user3 = new User("Сьёр", "Тот", (byte) 27);
        User user4 = new User("Саня", "Белый", (byte) 31);

        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        userService.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        userService.saveUser(user4.getName(), user4.getLastName(), user4.getAge());

        List<User> userList = userService.getAllUsers();
        for (User user : userList) {
            System.out.println(user.toString());
        }

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
