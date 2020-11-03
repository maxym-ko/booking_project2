package com.maxym.booking.web.command;

import com.maxym.booking.Path;
import com.maxym.booking.db.dao.impl.ApplicationDaoImpl;
import com.maxym.booking.db.dao.impl.BillDaoImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConfirmPaymentCommand implements Command {
    private static final Logger LOG = Logger.getLogger(ConfirmPaymentCommand.class);

    private static final long serialVersionUID = 8598075694610464412L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOG.debug("Command starts");

        long reservationId = Long.parseLong(request.getParameter("reservationId"));
        long billId = Long.parseLong(request.getParameter("billId"));
        String receiptId = request.getParameter("receiptId");

        new ApplicationDaoImpl().confirmApplicationPaymentById(reservationId);
        new BillDaoImpl().updateBillWithReceiptById(billId, receiptId);

        LOG.trace("Bill with id=" + billId + " was confirmed");

        LOG.debug("Command finished");
        return Path.REDIRECT_RESERVATIONS;
    }
}
