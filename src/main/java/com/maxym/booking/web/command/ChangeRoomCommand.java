package com.maxym.booking.web.command;

import com.maxym.booking.Path;
import com.maxym.booking.db.dao.impl.RoomDaoImpl;
import com.maxym.booking.db.entity.room.Room;
import com.maxym.booking.db.entity.room.RoomStatus;
import com.maxym.booking.db.entity.room.RoomType;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeRoomCommand implements Command {
    private static final Logger LOG = Logger.getLogger(ChangeRoomCommand.class);

    private static final long serialVersionUID = -3826320076424928998L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOG.debug("Command starts");

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

        new RoomDaoImpl().updateRoom(room);

        LOG.trace("Room with id=" + roomId + " was changed");

        LOG.debug("Command finished");
        return Path.REDIRECT_HOME;
    }
}
