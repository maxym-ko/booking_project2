package com.maxym.booking.web.command;

import com.maxym.booking.Path;
import com.maxym.booking.db.dao.impl.ApplicationDaoImpl;
import com.maxym.booking.db.dao.impl.BillDaoImpl;
import com.maxym.booking.db.entity.user.Role;
import com.maxym.booking.db.entity.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConfirmPaymentCommand implements Command {
    private static final long serialVersionUID = 8598075694610464412L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || user.getRole() != Role.USER) return Path.REDIRECT_FORBIDDEN_COMMAND;

        long reservationId = Long.parseLong(request.getParameter("reservationId"));
        long billId = Long.parseLong(request.getParameter("billId"));
        String receiptId = request.getParameter("receiptId");

        new ApplicationDaoImpl().updateApplicationToBookedById(reservationId);
        new BillDaoImpl().updateBillWithReceiptById(billId, receiptId);

        return Path.REDIRECT_RESERVATIONS;
    }
}
