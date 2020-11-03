package com.maxym.booking.web.command;

import com.maxym.booking.Path;
import com.maxym.booking.db.dao.UserDao;
import com.maxym.booking.db.dao.impl.UserDaoImpl;
import com.maxym.booking.db.entity.user.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {
    private static final Logger LOG = Logger.getLogger(LoginCommand.class);

    private static final long serialVersionUID = -7356558627418433620L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOG.debug("Command starts");

        HttpSession session = request.getSession();

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDao userDao = new UserDaoImpl();
        User user = userDao.findUserByUsername(username);

        String forward = Path.REDIRECT_HOME;

        request.setAttribute("usernameNotFound", username);
        if (user == null) {
            LOG.error("There is no such a username --> [" + username + "]");

            request.setAttribute("usernameValid", "is-invalid");
            forward = Path.PAGE_LOGIN;
        } else if (!password.equals(user.getPassword())) {
            LOG.error("Incorrect password --> " + password + " for [" + username + "]");

            request.setAttribute("passwordValid", "is-invalid");
            forward = Path.PAGE_LOGIN;
        } else {
            LOG.trace("User [username --> '" + username + "', password --> '" + password + "'] successfully signed in");

            session.setAttribute("user", user);
        }

        LOG.debug("Command finished");

        return forward;
    }
}
