package com.maxym.booking.web.command;

import com.maxym.booking.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowLoginCommand implements Command {

    private static final long serialVersionUID = -6435962538138519659L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return Path.PAGE_LOGIN;
    }
}
