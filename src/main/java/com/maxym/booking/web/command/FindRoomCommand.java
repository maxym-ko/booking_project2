package com.maxym.booking.web.command;

import com.maxym.booking.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FindRoomCommand extends ShowRoomsCommand {
    private static final Logger LOG = Logger.getLogger(FindRoomCommand.class);

    private static final long serialVersionUID = 5574093569700060989L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOG.debug("Command starts");

        execute0(request, true);

        LOG.debug("Command finished");
        return Path.PAGE_FIND_ROOM;
    }
}
