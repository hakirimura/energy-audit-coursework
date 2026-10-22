package ua.poltava.energy.server.util;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/energy_audit?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static String PASSWORD;

    static {
        Properties props = new Properties();
        boolean loaded = false;

        try (InputStream input = Files.newInputStream(Paths.get("../db.properties"))) {
            props.load(input);
            PASSWORD = props.getProperty("db.password");
            loaded = true;
        } catch (Exception ignored) {}

        if (!loaded) {
            try (InputStream input = Files.newInputStream(Paths.get("db.properties"))) {
                props.load(input);
                PASSWORD = props.getProperty("db.password");
                loaded = true;
            } catch (Exception ignored) {}
        }

        if (!loaded) {
            PASSWORD = System.getenv("DB_PASSWORD");
            if (PASSWORD != null) {
                loaded = true;
            }
        }

        if (!loaded || PASSWORD == null) {
            System.err.println("УВАГА: Пароль до БД не знайдено. Створіть db.properties або задайте змінну середовища DB_PASSWORD.");
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}