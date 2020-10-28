package com.maxym.booking.db.dao;

import com.maxym.booking.db.EntityMapper;
import com.maxym.booking.db.Fields;
import com.maxym.booking.db.entity.user.Role;
import com.maxym.booking.db.util.DBManager;
import com.maxym.booking.db.entity.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    private static final String SQL_FIND_USER_BY_USERNAME =
            "SELECT * FROM user WHERE username=?";

    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public User findUser(String username) {
        User user = null;
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_USERNAME)) {
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            UserMapper userMapper = new UserMapper();
            if (resultSet.next()) {
                user = userMapper.mapRow(resultSet);
            }
            resultSet.close();
            connection.commit();
        } catch (SQLException ex) {
//            TODO: Catch exception
        }
        return user;
    }

    @Override
    public User findUser(long id) {
        return null;
    }

    private static class UserMapper implements EntityMapper<User> {

        @Override
        public User mapRow(ResultSet resultSet) {
            try {
                User user = new User();
                user.setId(resultSet.getLong(Fields.USER_ID));
                user.setUsername(resultSet.getString(Fields.USER_USERNAME));
                user.setPassword(resultSet.getString(Fields.USER_PASSWORD));
                user.setRole(Role.valueOf(resultSet.getString(Fields.USER_ROLE)));
                return user;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
