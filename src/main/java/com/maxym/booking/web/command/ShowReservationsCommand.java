package com.maxym.booking.web.command;

import com.maxym.booking.Path;
import com.maxym.booking.db.dao.ApplicationDao;
import com.maxym.booking.db.dao.impl.ApplicationDaoImpl;
import com.maxym.booking.db.entity.application.Application;
import com.maxym.booking.db.entity.room.Room;
import com.maxym.booking.db.entity.user.Role;
import com.maxym.booking.db.entity.user.User;
import com.maxym.booking.web.util.Pagination;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowReservationsCommand implements Command {

    private static final long serialVersionUID = 193635295621833248L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || user.getRole() != Role.USER) return Path.REDIRECT_FORBIDDEN_COMMAND;

        ApplicationDao applicationDao = new ApplicationDaoImpl();
        int[] pagesInfo = Pagination.paginateRequest(request, applicationDao.countReservations(), 5);
        int[] scope = Pagination.getScope(pagesInfo[0], pagesInfo[1]);

        List<Application> reservations = applicationDao.findReservationsFromScope(scope[0], scope[1]);
        request.setAttribute("reservations", reservations);

        return Path.PAGE_RESERVATIONS;
    }
}
