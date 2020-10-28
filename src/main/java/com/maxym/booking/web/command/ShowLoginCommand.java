package com.maxym.booking.web.command;

import com.maxym.booking.Path;
import com.maxym.booking.db.entity.user.Role;
import com.maxym.booking.db.entity.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowLoginCommand implements Command {

    private static final long serialVersionUID = -6435962538138519659L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession().getAttribute("user") != null) return Path.REDIRECT_FORBIDDEN_COMMAND;

        return Path.PAGE_LOGIN;
    }
}
