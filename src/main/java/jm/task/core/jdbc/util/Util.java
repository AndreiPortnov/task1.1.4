package jm.task.core.jdbc.util;

import com.mysql.jdbc.Driver;
import jm.task.core.jdbc.constants.StaticConstants;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static jm.task.core.jdbc.constants.StaticConstants.*;

public class Util {
    // реализуйте настройку соеденения с БД

    private static Connection connection = null;

    private String url = URL_KEY.getTitle();
    private String username = USERNAME_KEY.getTitle();
    private String password = PASSWORD_KEY.getTitle();

    public Util() {
        try {
            if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void connectionCommit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void connectionRollBack() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
