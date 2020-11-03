package com.maxym.booking.web.command;

import com.maxym.booking.Path;
import com.maxym.booking.db.dao.UserDao;
import com.maxym.booking.db.dao.impl.UserDaoImpl;
import com.maxym.booking.db.entity.user.Role;
import com.maxym.booking.db.entity.user.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationCommand implements Command {
    private static final Logger LOG = Logger.getLogger(RegistrationCommand.class);

    private static final long serialVersionUID = -7356558627418433620L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOG.debug("Command starts");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        User user = User.builder()
                .username(username)
                .password(password)
                .role(Role.valueOf(role)).build();

        UserDao userDao = new UserDaoImpl();
        long userId = userDao.saveUser(user);

        LOG.trace("User with id=" + userId + " was saved to the DB");

        LOG.info("User [username --> [" + username + "], [password --> " + password + "] successfully signed up");

        LOG.debug("Command finished");
        return Path.REDIRECT_SHOW_LOGIN;
    }
}
