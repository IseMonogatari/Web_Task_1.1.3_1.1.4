package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Connection connection = getConnection();
        String sql = "CREATE TABLE IF NOT EXISTS MyUser (" +
                "id SERIAL PRIMARY KEY," +
                "name VARCHAR(20) NOT NULL," +
                "lastName VARCHAR(20) NOT NULL," +
                "age SMALLINT" +
                ")";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица создана!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void dropUsersTable() {
        Connection connection = getConnection();
        String sql = "DROP TABLE IF EXISTS MyUser";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();  //выполнение запроса
            System.out.println("Таблица удалена");
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении таблицы:\n" + e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = getConnection();
        String sql = "INSERT INTO MyUser (name, lastName, age) VALUES(?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();  //выполнение запроса

            System.out.println("Пользователь с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void removeUserById(long id) {
        Connection connection = getConnection();
        String sql = "DELETE FROM MyUser WHERE id=" + id;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();  //выполнение запроса
        } catch (SQLException e) {
            System.out.println("Ошибка удаления:\n" + e);
        }
    }

    public List<User> getAllUsers() {
        Connection connection = getConnection();
        List<User> userList = new ArrayList<>();

        String sql = "SELECT * FROM MyUser";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return userList;
    }

    public void cleanUsersTable() {
        Connection connection = getConnection();
        String sql = "DELETE FROM MyUser";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();  //выполнение запроса

            System.out.println("Таблица пуста");
        } catch (SQLException e) {
            System.out.println("Ошибка при очистке таблицы:\n" + e);
        }
    }
}
