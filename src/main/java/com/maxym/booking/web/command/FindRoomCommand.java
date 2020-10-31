package com.maxym.booking.web.command;

import com.maxym.booking.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FindRoomCommand extends ShowRoomsCommand {
    private static final long serialVersionUID = 5574093569700060989L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        execute0(request);

        return Path.PAGE_FIND_ROOM;
    }
}
