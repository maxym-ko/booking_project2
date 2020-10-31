package com.maxym.booking.web.command;

import com.maxym.booking.Path;
import com.maxym.booking.db.dao.impl.ApplicationDaoImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RejectApplicationCommand implements Command {
    private static final long serialVersionUID = 136980203387616225L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        long applicationId = Long.parseLong(request.getParameter("id"));
        new ApplicationDaoImpl().rejectApplicationById(applicationId);

        return Path.REDIRECT_APPLICATIONS;
    }
}
