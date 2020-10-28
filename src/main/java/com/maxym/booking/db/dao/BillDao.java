package com.maxym.booking.db.dao;

import com.maxym.booking.db.entity.application.Bill;

import java.util.List;

public interface BillDao {
    long saveBill(Bill bill);
    Bill findBillById(long id);
    void updateBillWithReceiptById(long billId, String receiptId);
    void deleteBillById(long id);
}
