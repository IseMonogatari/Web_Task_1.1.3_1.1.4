package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String hibernate_show_sql = "false";
    private static final String hibernate_hbm2ddl_auto = "update";//create
    private static final String DIALECT = "org.hibernate.dialect.PostgreSQLDialect";
    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "";
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";

    private static SessionFactory sessionFactory;

    public Util() {

    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Connection BAD");
            e.printStackTrace();
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = getPostgresConfiguration();
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                                .applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch (Exception e) {
                System.err.println("Ошибочка в Фабрике \n" + e);
            }
        }
        return sessionFactory;
    }

    private static Configuration getPostgresConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);

        configuration.setProperty("hibernate.dialect", DIALECT);
        configuration.setProperty("hibernate.connection.driver_class", DB_DRIVER);
        configuration.setProperty("hibernate.connection.url", URL);
        configuration.setProperty("hibernate.connection.username", USER_NAME);
        configuration.setProperty("hibernate.connection.password", PASSWORD);
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return configuration;
    }
}
