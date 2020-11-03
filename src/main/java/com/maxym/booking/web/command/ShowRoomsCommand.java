package com.maxym.booking.web.command;

import com.maxym.booking.db.dao.ApplicationDao;
import com.maxym.booking.db.dao.RoomDao;
import com.maxym.booking.db.dao.impl.ApplicationDaoImpl;
import com.maxym.booking.db.dao.impl.RoomDaoImpl;
import com.maxym.booking.db.entity.room.Room;
import com.maxym.booking.web.util.Pagination;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.List;
import java.util.Set;

public abstract class ShowRoomsCommand implements Command {
    private static final Logger LOG = Logger.getLogger(ShowRoomsCommand.class);

    private static final long serialVersionUID = -1833860940525215145L;

    protected void execute0(HttpServletRequest request, boolean showVacantOnly) {
        ApplicationDao applicationDao = new ApplicationDaoImpl();

        RoomDao roomDao = new RoomDaoImpl();
        int recordsPerPage = 6;
        int currentPage = Pagination.paginateRequest(request, roomDao.countRooms(), recordsPerPage);
        int[] scope = Pagination.getScope(currentPage, recordsPerPage);

        List<Room> rooms;
        String sort = request.getParameter("sort");
        if (showVacantOnly) {
            Date checkIn = Date.valueOf(request.getParameter("checkInDate"));
            Date checkOut = Date.valueOf(request.getParameter("checkOutDate"));
            Set<Long> roomIds = applicationDao.findApplicationsBookedOnDate(checkIn, checkOut);
            rooms = roomDao.findRoomsFromScopeExceptOrderBy(sort, scope[0], scope[1], roomIds);

            LOG.trace(rooms.size() + " rooms from the scope [" + scope[0] + "; " + scope[1] + "], sorted by " + sort + " was selected to show");
        } else {
            rooms = roomDao.findRoomsFromScopeOrderBy(sort, scope[0], scope[1]);

            LOG.trace(rooms.size() + " rooms from the scope [" + scope[0] + "; " + scope[1] + "] was selected to show");
        }
        request.setAttribute("rooms", rooms);
    }
}
