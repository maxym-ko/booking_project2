package com.maxym.booking.web.command;

import com.maxym.booking.Path;
import com.maxym.booking.db.dao.impl.RoomDaoImpl;
import com.maxym.booking.db.entity.room.Room;
import com.maxym.booking.db.entity.room.RoomStatus;
import com.maxym.booking.db.entity.room.RoomType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeRoomCommand implements Command {
    private static final long serialVersionUID = -3826320076424928998L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
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
