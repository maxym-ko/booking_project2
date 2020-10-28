package com.maxym.booking.web.command;

import com.maxym.booking.Path;
import com.maxym.booking.db.dao.ApplicationDao;
import com.maxym.booking.db.dao.impl.ApplicationDaoImpl;
import com.maxym.booking.db.entity.application.Application;
import com.maxym.booking.db.entity.user.Role;
import com.maxym.booking.db.entity.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowApplicationsAdminCommand implements Command {
    private static final long serialVersionUID = 7562940731444998111L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || user.getRole() != Role.ADMIN) return Path.REDIRECT_FORBIDDEN_COMMAND;

        ApplicationDao applicationDao = new ApplicationDaoImpl();
        List<Application> applications = applicationDao.findAllApplications();
        request.getSession().setAttribute("applications", applications);

        return Path.PAGE_APPLICATIONS_ADMIN;
    }
}
