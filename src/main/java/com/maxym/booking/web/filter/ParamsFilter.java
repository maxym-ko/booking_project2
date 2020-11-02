package com.maxym.booking.web.filter;

import com.maxym.booking.Path;

import javax.servlet.*;
import java.io.IOException;
import java.util.*;

public class ParamsFilter implements Filter {
    private static final Map<String, String[]> commandParamMap = new HashMap<>();

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!validate(request)) {
            request.setAttribute("message", "Some of the params are missed");
            request.getRequestDispatcher(Path.PAGE_ERROR).forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    private boolean validate(ServletRequest request) {
        String commandName = request.getParameter("command");
        if (commandName == null) return true;

        String[] paramArray = commandParamMap.get(commandName);
        if (paramArray == null) return true;

        Enumeration<String> actualParams = request.getParameterNames();
        List<String> requiredParams = Arrays.asList(paramArray);
        while (actualParams.hasMoreElements()) {
            String param = actualParams.nextElement();
            if (requiredParams.contains(param)) return true;
        }

        return false;
    }

    @Override
    public void init(FilterConfig fConfig) {
        commandParamMap.put("login", new String[] {"username", "password"});
        commandParamMap.put("registration", new String[] {"username", "password", "role"});
//        commandParamMap.put("add_room", new String[] {"capacity", "type", "price", "file"});
        commandParamMap.put("change_room", new String[] {"id", "capacity", "type", "price"});
        commandParamMap.put("remove_room", new String[] {"id"});
        commandParamMap.put("search_rooms", new String[] {"check_in_date", "check_out_date"});
        commandParamMap.put("book_room", new String[] {"id", "check_in_date", "check_out_date"});
        commandParamMap.put("select_room", new String[] {"id", "room_id"});
        commandParamMap.put("find_room", new String[] {"sort", "check_in_date", "check_out_date"});
        commandParamMap.put("create_application", new String[] {"capacity", "type", "check_in_date", "check_out_date"});
        commandParamMap.put("accept_application", new String[] {"id"});
        commandParamMap.put("reject_application", new String[] {"id"});
        commandParamMap.put("remove_application", new String[] {"id"});
        commandParamMap.put("confirm_payment", new String[] {"reservationId", "billId", "receiptId"});
    }
}
