package ru.student.gallery.config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DbConfig {

    private static String url;
    private static String user;
    private static String password;

    static {
        try (InputStream in = DbConfig.class.getClassLoader()
                .getResourceAsStream("db.properties")) {
            if (in == null) throw new IllegalStateException("db.properties not found");

            Properties p = new Properties();
            p.load(in);
            url = p.getProperty("db.url");
            user = p.getProperty("db.user");
            password = p.getProperty("db.password");
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            throw new RuntimeException("Не удалось загрузить db.properties", e);
        }
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(url, user, password);
    }
}
