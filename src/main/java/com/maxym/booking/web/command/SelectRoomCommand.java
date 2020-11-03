package com.maxym.booking.web.command;

import com.maxym.booking.Path;
import com.maxym.booking.db.dao.ApplicationDao;
import com.maxym.booking.db.dao.BillDao;
import com.maxym.booking.db.dao.RoomDao;
import com.maxym.booking.db.dao.impl.ApplicationDaoImpl;
import com.maxym.booking.db.dao.impl.BillDaoImpl;
import com.maxym.booking.db.dao.impl.RoomDaoImpl;
import com.maxym.booking.db.entity.application.Application;
import com.maxym.booking.db.entity.application.ApplicationStatus;
import com.maxym.booking.db.entity.application.Bill;
import com.maxym.booking.db.entity.room.Room;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SelectRoomCommand implements Command {
    private static final Logger LOG = Logger.getLogger(SelectRoomCommand.class);

    private static final long serialVersionUID = 3426893868634070322L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOG.debug("Command starts");

        long applicationId = Long.parseLong(request.getParameter("id"));
        long roomId = Long.parseLong(request.getParameter("roomId"));

        ApplicationDao applicationDao = new ApplicationDaoImpl();
        RoomDao roomDao = new RoomDaoImpl();
        BillDao billDao = new BillDaoImpl();

        Application application = applicationDao.findApplicationById(applicationId);
        Room room = roomDao.findRoomById(roomId);
        Bill bill = new Bill(application.getCheckInDate(), application.getCheckOutDate(), room.getPrice());
        bill.setId(billDao.saveBill(bill));

        application.setBill(bill);
        application.setRoom(room);
        application.setStatus(ApplicationStatus.ACCEPT_WAITING);
        application.setTotalPrice(bill.getTotalPrice());

        applicationDao.updateApplication(application);

        LOG.trace("Room with id=" + roomId + " was selected for the application with id=" + applicationId);

        LOG.debug("Command finished");
        return Path.REDIRECT_APPLICATIONS;
    }
}
