package com.maxym.booking.db.dao.impl;

import com.maxym.booking.db.Fields;
import com.maxym.booking.db.dao.ApplicationDao;
import com.maxym.booking.db.entity.application.Application;
import com.maxym.booking.db.entity.application.ApplicationStatus;
import com.maxym.booking.db.entity.application.Bill;
import com.maxym.booking.db.entity.room.Room;
import com.maxym.booking.db.entity.room.RoomType;
import com.maxym.booking.db.entity.user.User;
import com.maxym.booking.db.util.DBManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDaoImpl implements ApplicationDao {
    public static final String SQL_INSERT_APPLICATION = "INSERT INTO application " +
            "(check_in_date, check_out_date, requirement_capacity, requirement_type, status, user_id) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String SQL_INSERT_RESERVATION = "INSERT INTO application " +
            "(check_in_date, check_out_date, status, total_price, bill_id, user_id, room_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_FIND_APPLICATION_BY_ID = "SELECT * FROM application WHERE id=?";
    private static final String SQL_FIND_ALL_APPLICATIONS = "SELECT * FROM application WHERE status='LOOKING_FOR' or status='ACCEPT_WAITING' or status='OUT_OF_TIME'";
    private static final String SQL_FIND_ALL_RESERVATIONS = "SELECT * FROM application WHERE status='PAYMENT_WAITING' or status='BOOKED'";

    @Override
    public void saveApplication(Application application) {
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_APPLICATION)) {
            preparedStatement.setDate(1, application.getCheckInDate());
            preparedStatement.setDate(2, application.getCheckOutDate());
            preparedStatement.setInt(3, application.getRequirementCapacity());
            preparedStatement.setString(4, application.getRequirementType().name());
            preparedStatement.setString(5, application.getStatus().name());
            preparedStatement.setLong(6, application.getOwner().getId());

            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
//            TODO: Catch exception
        }
    }

    @Override
    public void saveReservation(Application application) {
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_RESERVATION)) {
            preparedStatement.setDate(1, application.getCheckInDate());
            preparedStatement.setDate(2, application.getCheckOutDate());
            preparedStatement.setString(3, application.getStatus().name());
            preparedStatement.setDouble(4, application.getBill().getTotalPrice());
            preparedStatement.setLong(5, application.getBill().getId());
            preparedStatement.setLong(6, application.getOwner().getId());
            preparedStatement.setLong(7, application.getRoom().getId());

            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
//            TODO: Catch exception
        }
    }

    @Override
    public Application findApplicationById(long id) {
        Application application = null;
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_APPLICATION_BY_ID)) {
            preparedStatement.setLong(1, id);

            application = getApplicationFromPreparedStatement(preparedStatement);

            connection.commit();
        } catch (SQLException ex) {
//            TODO: Catch exception
        }
        return application;
    }

    @Override
    public List<Application> findAllApplications() {
        return findAllApplicationsBySqlStatement(SQL_FIND_ALL_APPLICATIONS, false);
    }

    @Override
    public List<Application> findAllReservations() {
        return findAllApplicationsBySqlStatement(SQL_FIND_ALL_RESERVATIONS, true);
    }

    private List<Application> findAllApplicationsBySqlStatement(String sqlStatement, boolean isReservation) {
        List<Application> applications = new ArrayList<>();
        try (Connection connection = DBManager.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlStatement)) {

            while (resultSet.next()) {
                applications.add(getApplicationFromResultSet(resultSet, isReservation));
            }

            connection.commit();
        } catch (SQLException ex) {
//            TODO: Catch exception
        }
        return applications;
    }

    private Application getApplicationFromPreparedStatement(PreparedStatement preparedStatement) throws SQLException {
        Application application = null;
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            application = getApplicationFromResultSet(resultSet, false);
        }
        resultSet.close();
        return application;
    }

    private Application getApplicationFromResultSet(ResultSet resultSet, boolean isReservation) throws SQLException {
        Bill bill = new BillDaoImpl().findBillById(resultSet.getLong(Fields.APPLICATION_BILL_ID));
        User owner = new UserDaoImpl().findUserById(resultSet.getLong(Fields.APPLICATION_USER_ID));
        Room room = new RoomDaoImpl().findRoomById(resultSet.getLong(Fields.APPLICATION_ROOM_ID));
        return Application.builder()
                .id(resultSet.getLong(Fields.APPLICATION_ID))
                .checkInDate(resultSet.getDate(Fields.APPLICATION_CHECK_IN_DATE))
                .checkOutDate(resultSet.getDate(Fields.APPLICATION_CHECK_OUT_DATE))
                .requirementCapacity(isReservation ? room.getCapacity() : resultSet.getInt(Fields.APPLICATION_REQUIREMENT_CAPACITY))
                .requirementType(isReservation ? room.getType() : RoomType.valueOf(resultSet.getString(Fields.APPLICATION_REQUIREMENT_TYPE)))
                .status(ApplicationStatus.valueOf(resultSet.getString(Fields.APPLICATION_STATUS)))
                .totalPrice(resultSet.getDouble(Fields.APPLICATION_TOTAL_PRICE))
                .bill(bill).owner(owner).room(room).build();
    }
}
