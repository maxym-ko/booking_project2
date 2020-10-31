package com.maxym.booking.web.command;

import com.maxym.booking.db.dao.ApplicationDao;
import com.maxym.booking.db.dao.impl.ApplicationDaoImpl;
import com.maxym.booking.db.entity.application.Application;
import com.maxym.booking.web.util.Pagination;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public abstract class ShowApplicationsCommand implements Command {
    private static final long serialVersionUID = 1225025417294416468L;

    protected void execute0(HttpServletRequest request) {
        ApplicationDao applicationDao = new ApplicationDaoImpl();
        int[] pagesInfo = Pagination.paginateRequest(request, applicationDao.countApplications(), 5);
        int[] scope = Pagination.getScope(pagesInfo[0], pagesInfo[1]);

        List<Application> applications = applicationDao.findApplicationsFromScope(scope[0], scope[1]);

        request.setAttribute("applications", applications);
    }
}
