package com.maxym.booking.web.command;

import com.maxym.booking.Path;
import com.maxym.booking.db.dao.impl.ApplicationDaoImpl;
import com.maxym.booking.db.entity.user.Role;
import com.maxym.booking.db.entity.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RemoveApplicationCommand implements Command {
    private static final long serialVersionUID = 2529151502748212725L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || user.getRole() != Role.USER) return Path.REDIRECT_FORBIDDEN_COMMAND;

        long applicationId = Long.parseLong(request.getParameter("id"));
        new ApplicationDaoImpl().deleteApplicationById(applicationId);

        return Path.REDIRECT_APPLICATIONS;
    }
}
