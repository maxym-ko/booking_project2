package com.maxym.booking.web.command;

import com.maxym.booking.Path;
import com.maxym.booking.db.dao.ApplicationDao;
import com.maxym.booking.db.dao.impl.ApplicationDaoImpl;
import com.maxym.booking.db.entity.application.Application;
import com.maxym.booking.web.util.Pagination;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowApplicationsCommand implements Command {
    private static final long serialVersionUID = 7562940731444998111L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        ApplicationDao applicationDao = new ApplicationDaoImpl();
        int[] pagesInfo = Pagination.paginateRequest(request, applicationDao.countApplications(), 5);
        int[] scope = Pagination.getScope(pagesInfo[0], pagesInfo[1]);

        List<Application> applications = applicationDao.findApplicationsFromScope(scope[0], scope[1]);

        request.setAttribute("applications", applications);

        return Path.PAGE_APPLICATIONS;
    }
}
