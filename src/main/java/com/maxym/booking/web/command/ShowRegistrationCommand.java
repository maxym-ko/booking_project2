package com.maxym.booking.web.command;

import com.maxym.booking.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowRegistrationCommand implements Command {

    private static final long serialVersionUID = 7018532791640794784L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return Path.PAGE_REGISTRATION;
    }
}
