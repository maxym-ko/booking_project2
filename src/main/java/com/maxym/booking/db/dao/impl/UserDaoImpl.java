package com.maxym.booking.db.dao.impl;

import com.maxym.booking.db.Fields;
import com.maxym.booking.db.dao.UserDao;
import com.maxym.booking.db.entity.user.Role;
import com.maxym.booking.db.util.DBManager;
import com.maxym.booking.db.entity.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    public static final String SQL_INSERT_USER = "INSERT INTO user (username, password, role) VALUES (?, ?, ?)";
    private static final String SQL_FIND_USER_BY_USERNAME = "SELECT * FROM user WHERE username=?";

    @Override
    public void createUser(User user) {
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_USER)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole().name());

            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
//            TODO: Catch exception
        }
    }

    @Override
    public User findUserByUsername(String username) {
        User user = null;
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_USERNAME)) {
            preparedStatement.setString(1, username);

            user = getUserFromPreparedStatement(preparedStatement);

            connection.commit();
        } catch (SQLException ex) {
//            TODO: Catch exception
        }
        return user;
    }

    @Override
    public User findUserById(long id) {
        User user = null;
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_USERNAME)) {
            preparedStatement.setLong(1, id);

            user = getUserFromPreparedStatement(preparedStatement);

            connection.commit();
        } catch (SQLException ex) {
//            TODO: Catch exception
        }
        return user;
    }

    private User getUserFromPreparedStatement(PreparedStatement preparedStatement) throws SQLException {
        User user = null;
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            user = User.builder()
                    .id(resultSet.getLong(Fields.USER_ID))
                    .username(resultSet.getString(Fields.USER_USERNAME))
                    .password(resultSet.getString(Fields.USER_PASSWORD))
                    .role(Role.valueOf(resultSet.getString(Fields.USER_ROLE))).build();
        }
        resultSet.close();
        return user;
    }
}