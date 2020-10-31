package com.maxym.booking.web.command;

import com.maxym.booking.Path;
import com.maxym.booking.db.dao.impl.RoomDaoImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RemoveRoomCommand implements Command {
    private static final long serialVersionUID = 824212782743236076L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        long roomId = Long.parseLong(request.getParameter("id"));
        new RoomDaoImpl().deleteRoomById(roomId);

        return Path.REDIRECT_HOME;
    }
}
