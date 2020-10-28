package com.maxym.booking.db.dao.impl;

import com.maxym.booking.db.Fields;
import com.maxym.booking.db.dao.BillDao;
import com.maxym.booking.db.entity.application.Bill;
import com.maxym.booking.db.util.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BillDaoImpl implements BillDao {
    public static final String SQL_INSERT_BILL = "INSERT INTO bill (created, receipt_id, total_price) VALUES (?, ?, ?)";
    private static final String SQL_FIND_BILL_BY_ID = "SELECT * FROM bill WHERE id=?";
    @Override
    public void createBill(Bill bill) {
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_BILL)) {
            preparedStatement.setDate(1, bill.getCreated());
            preparedStatement.setString(2, bill.getReceiptId());
            preparedStatement.setDouble(3, bill.getTotalPrice());

            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
//            TODO: Catch exception
        }
    }

    @Override
    public Bill findBillById(long id) {
        Bill bill = null;
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BILL_BY_ID)) {
            preparedStatement.setLong(1, id);

            bill = getBillFromPreparedStatement(preparedStatement);

            connection.commit();
        } catch (SQLException ex) {
//            TODO: Catch exception
        }
        return bill;
    }

    private Bill getBillFromPreparedStatement(PreparedStatement preparedStatement) throws SQLException {
        Bill bill = null;
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            bill = new Bill();
            bill.setId(resultSet.getLong(Fields.BILL_ID));
            bill.setCreated(resultSet.getDate(Fields.BILL_CREATED));
            bill.setReceiptId(resultSet.getString(Fields.APPLICATION_BILL_ID));
        }
        resultSet.close();
        return bill;
    }
}