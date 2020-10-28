package com.maxym.booking.db.dao;

import com.maxym.booking.db.entity.user.User;

public interface UserDao {
    void createUser(User user);
    User findUserByUsername(String username);
    User findUserById(long id);
}
