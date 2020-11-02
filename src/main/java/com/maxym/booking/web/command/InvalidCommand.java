package com.maxym.booking.web.command;

import com.maxym.booking.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InvalidCommand implements Command {
    private static final long serialVersionUID = 326001214834967384L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
//        request.setAttribute("message", "There is no such a command");

        return Path.PAGE_ERROR;
    }
}
