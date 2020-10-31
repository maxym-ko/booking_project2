package com.maxym.booking.web.command;

import com.maxym.booking.Path;
import com.maxym.booking.db.dao.impl.ApplicationDaoImpl;
import com.maxym.booking.db.entity.application.Application;
import com.maxym.booking.db.entity.application.ApplicationStatus;
import com.maxym.booking.db.entity.room.RoomType;
import com.maxym.booking.db.entity.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

public class CreateApplicationCommand implements Command {
    private static final long serialVersionUID = -1764181022043725126L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        int capacity = Integer.parseInt(request.getParameter("capacity"));
        RoomType type = RoomType.valueOf(request.getParameter("type"));
        Date checkInDate = Date.valueOf(request.getParameter("checkInDate"));
        Date checkOutDate = Date.valueOf(request.getParameter("checkOutDate"));

        Application application = Application.builder()
                .owner(user)
                .requirementCapacity(capacity)
                .requirementType(type)
                .checkInDate(checkInDate)
                .checkOutDate(checkOutDate)
                .status(ApplicationStatus.LOOKING_FOR).build();
        new ApplicationDaoImpl().saveApplication(application);

        return Path.REDIRECT_APPLICATIONS;
    }
}
