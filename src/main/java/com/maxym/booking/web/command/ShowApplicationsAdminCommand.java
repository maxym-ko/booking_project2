package com.maxym.booking.web.command;

import com.maxym.booking.Path;
import com.maxym.booking.db.entity.user.Role;
import com.maxym.booking.db.entity.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowApplicationsAdminCommand extends ShowApplicationsCommand {
    private static final long serialVersionUID = 7562940731444998111L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || user.getRole() != Role.ADMIN) return Path.REDIRECT_FORBIDDEN_COMMAND;

        execute0(request);

        return Path.PAGE_APPLICATIONS_ADMIN;
    }
}
