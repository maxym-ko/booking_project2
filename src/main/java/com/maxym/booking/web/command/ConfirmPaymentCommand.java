package com.maxym.booking.web.command;

import com.maxym.booking.Path;
import com.maxym.booking.db.dao.impl.ApplicationDaoImpl;
import com.maxym.booking.db.dao.impl.BillDaoImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConfirmPaymentCommand implements Command {
    private static final long serialVersionUID = 8598075694610464412L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        long reservationId = Long.parseLong(request.getParameter("reservationId"));
        long billId = Long.parseLong(request.getParameter("billId"));
        String receiptId = request.getParameter("receiptId");

        new ApplicationDaoImpl().confirmApplicationPaymentById(reservationId);
        new BillDaoImpl().updateBillWithReceiptById(billId, receiptId);

        return Path.REDIRECT_RESERVATIONS;
    }
}
