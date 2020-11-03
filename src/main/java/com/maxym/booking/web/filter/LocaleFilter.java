package com.maxym.booking.web.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class LocaleFilter implements Filter {
    private static final Logger LOG = Logger.getLogger(LocaleFilter.class);

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        LOG.debug("LocaleFilter starts");

        HttpServletRequest req = (HttpServletRequest) request;

        if (req.getParameter("lang") != null) {
            req.getSession().setAttribute("lang", req.getParameter("lang"));
        }

        LOG.debug("LocaleFilter finished");
        chain.doFilter(request, response);
    }
}
