package com.maxym.booking.db.dao.impl;

import com.maxym.booking.db.Fields;
import com.maxym.booking.db.dao.BillDao;
import com.maxym.booking.db.entity.application.Bill;
import com.maxym.booking.db.util.DBManager;
import org.apache.log4j.Logger;

import java.sql.*;

public class BillDaoImpl implements BillDao {
    private static final Logger LOG = Logger.getLogger(BillDaoImpl.class);

    public static final String SQL_INSERT_BILL = "INSERT INTO bill (created, receipt_id, total_price) VALUES (?, ?, ?)";
    private static final String SQL_FIND_BILL_BY_ID = "SELECT * FROM bill WHERE id=?";
    private static final String SQL_DELETE_BILL_BY_ID = "DELETE FROM bill WHERE id=?";
    public static final String SQL_UPDATE_BILL_WITH_RECEIPT_BY_ID = "UPDATE bill SET receipt_id=? WHERE id=?";

    @Override
    public long saveBill(Bill bill) {
        long id = -1;
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_BILL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setDate(1, bill.getCreated());
            preparedStatement.setString(2, bill.getReceiptId());
            preparedStatement.setDouble(3, bill.getTotalPrice());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) id = resultSet.getLong(1);

            connection.commit();
        } catch (SQLException ex) {
            LOG.error("Failed while saving bill", ex);
        }
        return id;
    }

    @Override
    public void deleteBillById(long id) {
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BILL_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

            connection.commit();
        } catch (SQLException ex) {
            LOG.error("Failed while deleting bill by id", ex);
        }
    }

    @Override
    public void updateBillWithReceiptById(long billId, String receiptId) {
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_BILL_WITH_RECEIPT_BY_ID)) {
            preparedStatement.setString(1, receiptId);
            preparedStatement.setLong(2, billId);
            preparedStatement.executeUpdate();

            connection.commit();
        } catch (SQLException ex) {
            LOG.error("Failed while updating bill by id", ex);
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
            LOG.error("Failed while finding bill by id", ex);
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
            bill.setReceiptId(resultSet.getString(Fields.BILL_RECEIPT_ID));
            bill.setTotalPrice(resultSet.getDouble(Fields.BILL_TOTAL_PRICE));
        }
        resultSet.close();
        return bill;
    }
}
