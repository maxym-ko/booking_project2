package com.maxym.booking.db.dao;

import com.maxym.booking.db.entity.user.User;

public interface UserDao {
    User createUser(User user);
    User findUser(String username);
    User findUser(long id);
}
