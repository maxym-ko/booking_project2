package com.maxym.booking.web;


import com.maxym.booking.web.command.Command;
import com.maxym.booking.web.command.CommandContainer;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {
    private static final long serialVersionUID = 4703884634960670326L;
    private static final Logger LOG = Logger.getLogger(Controller.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("doGet() was called");
        try {
            process(request, response);
        } catch (ServletException | IOException e) {
            LOG.error("Cannot process GET request", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("doGet() was called");
        try {
            process(request, response);
        } catch (ServletException | IOException e) {
            LOG.error("Cannot process POST request", e);
        }
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("request processing started");

        String commandName = request.getParameter("command");
        LOG.trace("Request parameter: command --> " + commandName);

        Command command = CommandContainer.get(commandName);
        LOG.trace("Obtained command --> " + command.getClass().getSimpleName());

        String forward = command.execute(request, response);
        LOG.trace("Forward address --> " + forward);

        if (forward.contains("/controller")) {
            LOG.debug("Controller finished, now go to forward address with a redirect --> " + forward);
            response.sendRedirect(forward);
        } else {
            LOG.debug("Controller finished, now go to forward address --> " + forward);
            request.getRequestDispatcher(forward).forward(request, response);
        }
    }
}
