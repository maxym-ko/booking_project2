package com.maxym.booking.web.command;

import com.maxym.booking.Path;
import com.maxym.booking.db.entity.user.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {
    private static final Logger LOG = Logger.getLogger(LogoutCommand.class);

    private static final long serialVersionUID = 9154184025241363277L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOG.debug("Command starts");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        LOG.info("User [username --> [" + user.getUsername() + "], [password --> " + user.getPassword() + "] successfully signed out");

        LOG.trace("Session invalidated");

        session.invalidate();

        LOG.debug("Command finished");

        return Path.REDIRECT_HOME;
    }
}
