package com.maxym.booking.web.command;

import com.maxym.booking.Path;
import com.maxym.booking.db.dao.UserDao;
import com.maxym.booking.db.dao.impl.UserDaoImpl;
import com.maxym.booking.db.entity.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {
    private static final long serialVersionUID = -7356558627418433620L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDao userDao = new UserDaoImpl();
        User user = userDao .findUserByUsername(username);

        request.setAttribute("usernameNotFound", username);
        if (user == null) {
            request.setAttribute("usernameValid", "is-invalid");
            return Path.PAGE_LOGIN;
        } else if (!password.equals(user.getPassword())) {
            request.setAttribute("passwordValid", "is-invalid");
            return Path.PAGE_LOGIN;
        }

        session.setAttribute("user", user);

        return Path.REDIRECT_HOME;
    }
}
