package com.maxym.booking.web.command;

import com.maxym.booking.Path;
import com.maxym.booking.db.dao.impl.ApplicationDaoImpl;
import com.maxym.booking.db.dao.impl.RoomDaoImpl;
import com.maxym.booking.db.entity.user.Role;
import com.maxym.booking.db.entity.user.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveRoomCommand implements Command {
    private static final long serialVersionUID = 824212782743236076L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || user.getRole() != Role.ADMIN) return Path.REDIRECT_FORBIDDEN_COMMAND;

        long roomId = Long.parseLong(request.getParameter("id"));
        new RoomDaoImpl().deleteRoomById(roomId);

        return Path.REDIRECT_MAIN;
    }
}
