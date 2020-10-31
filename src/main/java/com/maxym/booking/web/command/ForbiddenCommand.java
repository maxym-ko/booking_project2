package com.maxym.booking.web.command;

import com.maxym.booking.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForbiddenCommand implements Command {
    private static final long serialVersionUID = -7146627916885706150L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
//        request.setAttribute("message", "You don't have an access to this page");
        return Path.PAGE_ERROR;
    }
}
