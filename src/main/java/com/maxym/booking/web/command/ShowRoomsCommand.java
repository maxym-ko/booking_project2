package com.maxym.booking.web.command;

import com.maxym.booking.Path;
import com.maxym.booking.db.dao.RoomDao;
import com.maxym.booking.db.dao.impl.RoomDaoImpl;
import com.maxym.booking.db.entity.room.Room;
import com.maxym.booking.web.util.Pagination;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public abstract class ShowRoomsCommand implements Command {
    private static final long serialVersionUID = -1833860940525215145L;

    protected void execute0(HttpServletRequest request) {
        RoomDao roomDao = new RoomDaoImpl();
        int[] pagesInfo = Pagination.paginateRequest(request, roomDao.getNumberOfRows(), 6);
        int[] scope = Pagination.getScope(pagesInfo[0], pagesInfo[1]);

        List<Room> rooms;
        String sort = request.getParameter("sort");
        if (sort == null) {
            rooms = roomDao.findRoomsFromScope(scope[0], scope[1]);
        } else {
            rooms = roomDao.findRoomsFromScopeOrderBy(sort, scope[0], scope[1]);
        }
        request.setAttribute("rooms", rooms);
    }
}
