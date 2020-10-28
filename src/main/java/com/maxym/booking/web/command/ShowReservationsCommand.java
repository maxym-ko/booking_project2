package com.maxym.booking.web.command;

import com.maxym.booking.Path;
import com.maxym.booking.db.dao.ApplicationDao;
import com.maxym.booking.db.dao.impl.ApplicationDaoImpl;
import com.maxym.booking.db.entity.application.Application;
import com.maxym.booking.db.entity.room.Room;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowReservationsCommand implements Command {

    private static final long serialVersionUID = 193635295621833248L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        ApplicationDao applicationDao = new ApplicationDaoImpl();
        List<Application> reservations = applicationDao.findAllReservations();
        request.getSession().setAttribute("reservations", reservations);

        return Path.PAGE_RESERVATIONS;
    }
}
