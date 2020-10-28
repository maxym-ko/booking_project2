package com.maxym.booking.web.command;

import com.maxym.booking.Path;
import com.maxym.booking.db.dao.ApplicationDao;
import com.maxym.booking.db.dao.RoomDao;
import com.maxym.booking.db.dao.impl.ApplicationDaoImpl;
import com.maxym.booking.db.dao.impl.RoomDaoImpl;
import com.maxym.booking.db.entity.application.Application;
import com.maxym.booking.db.entity.room.Room;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowApplicationsCommand implements Command {
    private static final long serialVersionUID = 1225025417294416468L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        ApplicationDao applicationDao = new ApplicationDaoImpl();
        List<Application> applications = applicationDao.findAllApplications();
        request.getSession().setAttribute("applications", applications);

        return Path.PAGE_APPLICATIONS;
    }
}
