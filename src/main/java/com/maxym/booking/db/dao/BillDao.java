package com.maxym.booking.db.dao;

import com.maxym.booking.db.entity.application.Bill;

import java.util.List;

public interface BillDao {
    void createBill(Bill bill);
    Bill findBillById(long id);
}
