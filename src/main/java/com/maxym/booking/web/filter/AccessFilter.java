package com.maxym.booking.web.filter;

import com.maxym.booking.Path;
import com.maxym.booking.db.entity.user.Role;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

public class AccessFilter implements Filter {
    // commands access
    private final EnumMap<Role, List<String>> accessMap = new EnumMap<>(Role.class);
    private List<String> commons = new ArrayList<>();
    private List<String> outOfControl = new ArrayList<>();

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!accessAllowed(request)) {
            request.setAttribute("message", "You don't have an access to this page");
            request.getRequestDispatcher(Path.PAGE_ERROR).forward(request, response);
        } else {
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

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        // roles
        accessMap.put(Role.ADMIN, Arrays.asList(fConfig.getInitParameter("admin").split("\\s")));
        accessMap.put(Role.USER, Arrays.asList(fConfig.getInitParameter("user").split("\\s")));
        accessMap.put(Role.GUEST, Arrays.asList(fConfig.getInitParameter("guest").split("\\s")));

        // commons
        commons = Arrays.asList(fConfig.getInitParameter("common").split("\\s"));

        // out of control
        outOfControl = Arrays.asList(fConfig.getInitParameter("out-of-control").split("\\s"));
    }
}
