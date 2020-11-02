package com.maxym.booking.web.command;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public interface Command extends Serializable {
    String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
