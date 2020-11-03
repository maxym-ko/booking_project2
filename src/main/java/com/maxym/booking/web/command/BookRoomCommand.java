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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

public class BookRoomCommand implements Command {
    private static final long serialVersionUID = 7376792463530848535L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");

        long roomId = Long.parseLong(request.getParameter("id"));
        RoomDao roomDao = new RoomDaoImpl();
        Room room = roomDao.findRoomById(roomId);
        room.setStatus(RoomStatus.BOOKED);

        Date checkInDate = Date.valueOf(request.getParameter("check_in_date"));
        Date checkOutDate = Date.valueOf(request.getParameter("check_out_date"));
        Bill bill = new Bill(checkInDate, checkOutDate, room.getPrice());
        bill.setId(new BillDaoImpl().saveBill(bill));

        Application reservation = Application.builder().checkInDate(checkInDate).checkOutDate(checkOutDate).build();
        reservation.setBill(bill);
        reservation.setOwner(user);
        reservation.setRoom(room);
        reservation.setTotalPrice(bill.getTotalPrice());
        reservation.setStatus(ApplicationStatus.PAYMENT_WAITING);

        new ApplicationDaoImpl().saveReservation(reservation);

        return Path.REDIRECT_RESERVATIONS;
    }
}
