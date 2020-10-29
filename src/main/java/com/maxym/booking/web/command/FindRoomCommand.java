package com.maxym.booking.web.command;

import com.maxym.booking.Path;
import com.maxym.booking.db.dao.RoomDao;
import com.maxym.booking.db.dao.impl.RoomDaoImpl;
import com.maxym.booking.db.entity.room.Room;
import com.maxym.booking.db.entity.user.Role;
import com.maxym.booking.db.entity.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class FindRoomCommand implements Command {
    private static final long serialVersionUID = 5574093569700060989L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || user.getRole() != Role.ADMIN) return Path.REDIRECT_FORBIDDEN_COMMAND;

        RoomDao roomDao = new RoomDaoImpl();
        List<Room> rooms = roomDao.findAllRooms();
        request.getSession().setAttribute("rooms", rooms);

        return Path.PAGE_FIND_ROOM;
    }
}
