package com.maxym.booking.web.filter;

import com.maxym.booking.Path;
import com.maxym.booking.db.entity.user.Role;
import com.maxym.booking.web.command.CommandContainer;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

public class AccessFilter implements Filter {
    private static final Logger LOG = Logger.getLogger(AccessFilter.class);

    private final EnumMap<Role, List<String>> accessMap = new EnumMap<>(Role.class);
    private List<String> commons = new ArrayList<>();
    private List<String> outOfControl = new ArrayList<>();

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LOG.debug("AccessFilter starts");
        String command = request.getParameter("command");

        if (!commandExist(command)) {
            LOG.debug("There is no such a command --> " + command + ", AccessFilter finished");

            request.setAttribute("message", "There is no such a command");
            request.getRequestDispatcher(Path.PAGE_ERROR).forward(request, response);
        } else if (!accessAllowed(request)) {
            LOG.debug("You don't have an access to this page --> " + command + ", AccessFilter finished");

            request.setAttribute("message", "You don't have an access to this page");
            request.getRequestDispatcher(Path.PAGE_ERROR).forward(request, response);
        } else {
            LOG.debug("AccessFilter finished");

            chain.doFilter(request, response);
        }
    }

    private boolean accessAllowed(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String commandName = request.getParameter("command");
        if (commandName == null || commandName.isEmpty()) return true;

        if (outOfControl.contains(commandName)) return true;

        HttpSession session = httpRequest.getSession();
        Role userRole = (Role) session.getAttribute("role");

        if (userRole == null) return accessMap.get(Role.GUEST).contains(commandName);

        return accessMap.get(userRole).contains(commandName) || commons.contains(commandName);
    }

    private boolean commandExist(String commandName) {
        if (commandName == null) {
            return true;
        }

        return CommandContainer.contains(commandName);
    }

    @Override
    public void init(FilterConfig fConfig) {
        accessMap.put(Role.ADMIN, Arrays.asList(fConfig.getInitParameter("admin").split("\\s")));
        accessMap.put(Role.USER, Arrays.asList(fConfig.getInitParameter("user").split("\\s")));
        accessMap.put(Role.GUEST, Arrays.asList(fConfig.getInitParameter("guest").split("\\s")));

        commons = Arrays.asList(fConfig.getInitParameter("common").split("\\s"));

        outOfControl = Arrays.asList(fConfig.getInitParameter("out-of-control").split("\\s"));
    }
}
