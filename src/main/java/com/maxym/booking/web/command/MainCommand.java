package com.maxym.booking.web.command;

import com.maxym.booking.Path;
import com.maxym.booking.db.dao.RoomDao;
import com.maxym.booking.db.dao.impl.RoomDaoImpl;
import com.maxym.booking.db.entity.room.Room;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class MainCommand implements Command {

    private static final long serialVersionUID = -1833860940525215145L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        RoomDao roomDao = new RoomDaoImpl();
        List<Room> rooms;

        String sort = request.getParameter("sort");
        if (sort == null) {
            rooms = roomDao.findAllRooms();
        } else {
            rooms = roomDao.findRoomsOrderBy(sort);
        }
        request.setAttribute("rooms", rooms);

        request.getSession().setAttribute("rooms", rooms);

        return Path.PAGE_MAIN;
    }
}
