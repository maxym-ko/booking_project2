package com.maxym.booking.web.command;

import com.maxym.booking.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchRoomCommand extends ShowRoomsCommand {
    private static final Logger LOG = Logger.getLogger(SearchRoomCommand.class);

    private static final long serialVersionUID = -8467555172013196674L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOG.debug("Command starts");

        request.setAttribute("isBookAvailable", true);
        request.setAttribute("checkInDate", request.getParameter("checkInDate"));
        request.setAttribute("checkOutDate", request.getParameter("checkOutDate"));
        execute0(request, true);

        LOG.debug("Command finished");
        return Path.PAGE_HOME;
    }
}
