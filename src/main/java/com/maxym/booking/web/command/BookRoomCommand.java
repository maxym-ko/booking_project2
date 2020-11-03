package com.maxym.booking.web.command;

import com.maxym.booking.Path;
import com.maxym.booking.db.dao.RoomDao;
import com.maxym.booking.db.dao.impl.ApplicationDaoImpl;
import com.maxym.booking.db.dao.impl.BillDaoImpl;
import com.maxym.booking.db.dao.impl.RoomDaoImpl;
import com.maxym.booking.db.entity.application.Application;
import com.maxym.booking.db.entity.application.ApplicationStatus;
import com.maxym.booking.db.entity.application.Bill;
import com.maxym.booking.db.entity.room.Room;
import com.maxym.booking.db.entity.room.RoomStatus;
import com.maxym.booking.db.entity.user.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

public class BookRoomCommand implements Command {
    private static final Logger LOG = Logger.getLogger(BookRoomCommand.class);

    private static final long serialVersionUID = 7376792463530848535L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOG.debug("Command starts");

        User user = (User) request.getSession().getAttribute("user");

        long roomId = Long.parseLong(request.getParameter("id"));
        RoomDao roomDao = new RoomDaoImpl();
        Room room = roomDao.findRoomById(roomId);
        room.setStatus(RoomStatus.BOOKED);

        Date checkInDate = Date.valueOf(request.getParameter("checkInDate"));
        Date checkOutDate = Date.valueOf(request.getParameter("checkOutDate"));
        Bill bill = new Bill(checkInDate, checkOutDate, room.getPrice());
        bill.setId(new BillDaoImpl().saveBill(bill));

        Application reservation = Application.builder().checkInDate(checkInDate).checkOutDate(checkOutDate).build();
        reservation.setBill(bill);
        reservation.setOwner(user);
        reservation.setRoom(room);
        reservation.setTotalPrice(bill.getTotalPrice());
        reservation.setStatus(ApplicationStatus.PAYMENT_WAITING);

        long reservationId = new ApplicationDaoImpl().saveReservation(reservation);

        LOG.trace("Room with id=" + roomId + " was booked, the application id=" + reservationId);

        LOG.debug("Command finished");
        return Path.REDIRECT_RESERVATIONS;
    }
}
