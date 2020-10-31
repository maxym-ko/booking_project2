package com.maxym.booking.web.command;

import com.maxym.booking.Path;
import com.maxym.booking.db.dao.impl.RoomDaoImpl;
import com.maxym.booking.db.entity.room.Room;
import com.maxym.booking.db.entity.room.RoomStatus;
import com.maxym.booking.db.entity.room.RoomType;
import com.maxym.booking.db.entity.user.Role;
import com.maxym.booking.db.entity.user.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeRoomCommand implements Command {
    private static final long serialVersionUID = -3826320076424928998L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || user.getRole() != Role.ADMIN) return Path.REDIRECT_FORBIDDEN_COMMAND;

        long roomId = Long.parseLong(request.getParameter("id"));
        int capacity = Integer.parseInt(request.getParameter("capacity"));
        RoomType type = RoomType.valueOf(request.getParameter("type"));
        double price = Double.parseDouble(request.getParameter("price"));

        Room room = Room.builder()
                .id(roomId)
                .capacity(capacity)
                .type(type)
                .price(price)
                .status(RoomStatus.VACANT).build();
//        saveImgToRoom(file, room);

        new RoomDaoImpl().updateRoom(room);

        return Path.REDIRECT_HOME;
    }
}
