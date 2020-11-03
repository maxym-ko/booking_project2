package com.maxym.booking.db.dao;

import com.maxym.booking.db.entity.user.User;

public interface UserDao {
    long saveUser(User user);
    User findUserByUsername(String username);
    User findUserById(long id);
}
