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

        int recordsPerPage = 6;
        int noOfPages = (int) ((roomDao.getNumberOfRows() + 1.) / recordsPerPage);
        String tmp = request.getParameter("page");
        System.out.println(tmp);
        int currentPage;
        if (tmp != null && tmp.matches("^[0-9]*$")) {
            currentPage = Integer.parseInt(tmp);
        } else {
            currentPage = 1;
        }
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", currentPage);

        int from = (currentPage - 1) * recordsPerPage;
        int to = from + recordsPerPage;
        String sort = request.getParameter("sort");
        System.out.println(sort);
        if (sort == null) {
            rooms = roomDao.findRoomsFromTo(from, to);
        } else {
            rooms = roomDao.findRoomsFromToOrderBy(sort, from, to);
        }
        request.setAttribute("rooms", rooms);

        return Path.PAGE_MAIN;
    }
}
