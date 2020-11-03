package com.maxym.booking.web.command;

import com.maxym.booking.Path;
import com.maxym.booking.db.dao.impl.ApplicationDaoImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RejectApplicationCommand implements Command {
    private static final Logger LOG = Logger.getLogger(RejectApplicationCommand.class);

    private static final long serialVersionUID = 136980203387616225L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOG.debug("Command starts");

        long applicationId = Long.parseLong(request.getParameter("id"));
        new ApplicationDaoImpl().rejectApplicationById(applicationId);

        LOG.trace("Application with id=" + applicationId + " was rejected");

        LOG.debug("Command finished");
        return Path.REDIRECT_APPLICATIONS;
    }
}
