package com.maxym.booking.web.command;

import com.maxym.booking.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeCommand extends ShowRoomsCommand {
    private static final Logger LOG = Logger.getLogger(HomeCommand.class);

    private static final long serialVersionUID = -1833860940525215145L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOG.debug("Command starts");

        execute0(request, false);

        LOG.debug("Command finished");
        return Path.PAGE_HOME;
    }
}
