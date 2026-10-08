package com.careernav.config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Singleton JDBC connection provider.
 * Settings come from environment variables first (DB_URL, DB_USER, DB_PASSWORD) - used on Render/Railway -
 * and fall back to db.properties for local development.
 */
public final class DBConnection {
    private static DBConnection instance;
    private final String url, user, password;

    private DBConnection() {
        Properties p = new Properties();
        try (InputStream in = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in != null) p.load(in);          // file is optional when env variables are set
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
        url = env("DB_URL", p.getProperty("db.url"));
        user = env("DB_USER", p.getProperty("db.user"));
        password = env("DB_PASSWORD", p.getProperty("db.password"));
        if (url == null) throw new ExceptionInInitializerError("No database settings found (DB_URL or db.properties)");
        try {
            Class.forName(env("DB_DRIVER", p.getProperty("db.driver", "com.mysql.cj.jdbc.Driver")));
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private static String env(String name, String fallback) {
        String v = System.getenv(name);
        return (v == null || v.isEmpty()) ? fallback : v;
    }

    public static synchronized DBConnection getInstance() {
        if (instance == null) instance = new DBConnection();
        return instance;
    }

    /** Returns a NEW connection each call - always use try-with-resources. */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}