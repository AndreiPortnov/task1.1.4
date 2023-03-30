package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.constants.StaticConstants;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Util util = new Util();

    public UserDaoJDBCImpl() {

    }


    public void createUsersTable() {
        String queryCreateTable = """
                       CREATE TABLE IF NOT EXISTS user(
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(64),
                       last_name VARCHAR(64),
                       age SMALLINT)
                """;
        try {
            PreparedStatement preparedStatement = util.getConnection().prepareStatement(queryCreateTable);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String queryDropTable = """
                DROP TABLE IF EXISTS user
                """;
        try {
            PreparedStatement preparedStatement = util.getConnection().prepareStatement(queryDropTable);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String querySave = """
                INSERT INTO user(name, last_name, age) VALUES (?, ?, ?)
                """;
        try {
            PreparedStatement preparedStatement = util.getConnection().prepareStatement(querySave, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            int checkValue = preparedStatement.executeUpdate();
            if (checkValue != 0) {
                util.connectionCommit();
                System.out.println("User с именем – " + name + " добавлен в базу данных");
            } else {
                util.connectionRollBack();
                throw new RuntimeException(StaticConstants.USER_ADDING_ERROR.getTitle());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void removeUserById(long id) {
        String queryRemove = """
                DELETE FROM user
                WHERE id =?
                """;
        try {
            PreparedStatement preparedStatement = util.getConnection().prepareStatement(queryRemove);
            preparedStatement.setLong(1, id);

            int checkValue = preparedStatement.executeUpdate();
            if (checkValue != 0) {
                util.connectionCommit();
            } else {
                util.connectionRollBack();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        String queryRemove = """
                SELECT * FROM user
                """;
        List<User> listFromTable = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = util.getConnection().prepareStatement(queryRemove);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(StaticConstants.TABLE_COLUMN_ID.getTitle()));
                user.setName(resultSet.getString(StaticConstants.TABLE_COLUMN_NAME.getTitle()));
                user.setLastName(resultSet.getString(StaticConstants.TABLE_COLUMN_LASTNAME.getTitle()));
                user.setAge(resultSet.getByte(StaticConstants.TABLE_COLUMN_AGE.getTitle()));
                listFromTable.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(listFromTable);
        return listFromTable;
    }


    public void cleanUsersTable() {
        String queryClean = """
                DELETE FROM user
                """;
        try {
            PreparedStatement preparedStatement = util.getConnection().prepareStatement(queryClean);

            int checkValue = preparedStatement.executeUpdate();
            if (checkValue != 0) {
                util.connectionCommit();
            } else {
                util.connectionRollBack();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
