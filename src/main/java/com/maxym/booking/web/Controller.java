package com.maxym.booking.web;


import com.maxym.booking.web.command.Command;
import com.maxym.booking.web.command.CommandContainer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

public class Controller extends HttpServlet {

    private static final long serialVersionUID = 4703884634960670326L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter("command");

        Command command = CommandContainer.get(commandName);

        String forward = command.execute(request, response);

        if (forward.contains("/controller")) {
            response.sendRedirect(forward);
        } else {
            request.getRequestDispatcher(forward).forward(request, response);
        }
    }
}
