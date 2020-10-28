package com.maxym.booking.web.command;

import com.maxym.booking.Path;
import com.maxym.booking.db.dao.UserDao;
import com.maxym.booking.db.dao.UserDaoImpl;
import com.maxym.booking.db.entity.user.Role;
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

        // TODO: error handler
        UserDao userDao = new UserDaoImpl();
        User user = userDao .findUserByUsername(username);

        if (user == null || !password.equals(user.getPassword())) {
            return Path.PAGE_LOGIN;
        }
//            Role userRole = Role.getRole(user);
//
//            if (userRole == Role.ADMIN)
//                forward = Path.COMMAND__LIST_ORDERS;
//
//            if (userRole == Role.CLIENT)
//                forward = Path.COMMAND__LIST_MENU;

        session.setAttribute("user", user);

        return Path.REDIRECT_MAIN;
    }
}
