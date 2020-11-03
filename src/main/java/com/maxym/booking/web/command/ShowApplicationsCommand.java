package com.maxym.booking.web.command;

import com.maxym.booking.Path;
import com.maxym.booking.db.dao.ApplicationDao;
import com.maxym.booking.db.dao.impl.ApplicationDaoImpl;
import com.maxym.booking.db.entity.application.Application;
import com.maxym.booking.web.util.Pagination;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowApplicationsCommand implements Command {
    private static final Logger LOG = Logger.getLogger(ShowApplicationsCommand.class);

    private static final long serialVersionUID = 7562940731444998111L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOG.debug("Command starts");

        ApplicationDao applicationDao = new ApplicationDaoImpl();
        int recordsPerPage = 6;
        int currentPage = Pagination.paginateRequest(request, applicationDao.countApplications(), recordsPerPage);
        int[] scope = Pagination.getScope(currentPage, recordsPerPage);

        List<Application> applications = applicationDao.findApplicationsFromScope(scope[0], scope[1]);

        request.setAttribute("applications", applications);


        LOG.trace(applications.size() + " application was selected to show");

        LOG.debug("Command finished");
        return Path.PAGE_APPLICATIONS;
    }
}
