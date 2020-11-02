package com.maxym.booking.web.command;

import com.maxym.booking.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchRoomCommand extends ShowRoomsCommand {
    private static final long serialVersionUID = -8467555172013196674L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("isBookAvailable", true);
        request.setAttribute("checkInDate", request.getParameter("check_in_date"));
        request.setAttribute("checkOutDate", request.getParameter("check_out_date"));
        execute0(request, true);

        return Path.PAGE_HOME;
    }
}
