package com.maxym.booking.web.listener;


import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;

@WebListener("application context listener")
public class ContextListener implements ServletContextListener {
    private static final Logger LOG = Logger.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext servletContext = event.getServletContext();
        initLog4J(servletContext);
        initCommandContainer();

        LOG.debug("Servlet context initialization finished");
    }

    private void initCommandContainer() {
        LOG.debug("Command container initialization started");

        try {
            Class.forName("com.maxym.booking.web.command.CommandContainer");
        } catch (ClassNotFoundException ex) {
            LOG.fatal("Cannot initialize commands container");
        }

        LOG.debug("Command container initialization finished");
    }

    private void initLog4J(ServletContext context) {
        String log4jConfigFile = context.getInitParameter("log4j-config-location");
        String fullPath = context.getRealPath("") + File.separator + log4jConfigFile;

        PropertyConfigurator.configure(fullPath);

        LOG.debug("Log4J initialization finished");
    }
}