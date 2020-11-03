package com.maxym.booking.web.command;

import com.maxym.booking.Path;
import com.maxym.booking.db.dao.impl.RoomDaoImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RemoveRoomCommand implements Command {
    private static final Logger LOG = Logger.getLogger(RemoveRoomCommand.class);

    private static final long serialVersionUID = 824212782743236076L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOG.debug("Command starts");

        long roomId = Long.parseLong(request.getParameter("id"));
        new RoomDaoImpl().deleteRoomById(roomId);

        LOG.trace("Room with id=" + roomId + " was removed from DB");

        LOG.debug("Command finished");
        return Path.REDIRECT_HOME;
    }
}
