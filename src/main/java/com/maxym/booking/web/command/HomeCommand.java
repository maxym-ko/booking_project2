package com.maxym.booking.web.command;

import com.maxym.booking.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeCommand extends ShowRoomsCommand {
    private static final long serialVersionUID = -1833860940525215145L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        execute0(request);

        return Path.PAGE_MAIN;
    }
}
