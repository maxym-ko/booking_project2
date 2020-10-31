//package com.maxym.booking.web.filter;
//
//import com.maxym.booking.Path;
//import com.maxym.booking.db.entity.user.Role;
//import com.maxym.booking.db.entity.user.User;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//
//public class AccessFilter implements Filter {
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//
//        HttpServletRequest req = (HttpServletRequest) request;
//
//
//        User user = (User) req.getSession().getAttribute("user");
//        if (user == null || user.getRole() != Role.USER) return Path.REDIRECT_FORBIDDEN_COMMAND;
//        chain.doFilter(request, response);
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//}
