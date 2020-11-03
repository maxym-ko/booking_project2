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
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ApplicationDaoImpl implements ApplicationDao {
    private static final Logger LOG = Logger.getLogger(ApplicationDaoImpl.class);

    public static final String SQL_INSERT_APPLICATION = "INSERT INTO application " +
            "(check_in_date, check_out_date, requirement_capacity, requirement_type, status, user_id) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String SQL_INSERT_RESERVATION = "INSERT INTO application " +
            "(check_in_date, check_out_date, status, total_price, bill_id, user_id, room_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_FIND_APPLICATION_BY_ID = "SELECT * FROM application WHERE id=?";
    private static final String SQL_FIND_APPLICATIONS_BOOKED_ON_DATE = "SELECT check_in_date, check_out_date, room_id " +
            "FROM application " +
            "WHERE status != 'LOOKING_FOR'" +
            "    and check_in_date BETWEEN ? AND ?" +
            "   OR check_out_date BETWEEN ? AND ?" +
            "   OR ? BETWEEN check_in_date AND check_out_date " +
            "ORDER BY -id";
    private static final String SQL_FIND_ALL_APPLICATIONS = "SELECT * FROM application WHERE status='LOOKING_FOR' or status='ACCEPT_WAITING' or status='OUT_OF_TIME' ORDER BY id DESC";
    private static final String SQL_FIND_APPLICATIONS_FROM_TO = "SELECT * FROM application WHERE status='LOOKING_FOR' or status='ACCEPT_WAITING' or status='OUT_OF_TIME' ORDER BY id DESC LIMIT %d,%d";
    private static final String SQL_FIND_ALL_RESERVATIONS = "SELECT * FROM application WHERE status='PAYMENT_WAITING' or status='BOOKED' ORDER BY id DESC";
    private static final String SQL_FIND_RESERVATIONS_FROM_TO = "SELECT * FROM application WHERE status='PAYMENT_WAITING' or status='BOOKED' ORDER BY id DESC LIMIT %d,%d";
    private static final String SQL_DELETE_APPLICATION_BY_ID = "DELETE FROM application WHERE id=?";
    private static final String SQL_CONFIRM_APPLICATION_BY_ID = "UPDATE application SET status = 'PAYMENT_WAITING' WHERE id=?";
    private static final String SQL_REJECT_APPLICATION_BY_ID = "UPDATE application SET status = 'LOOKING_FOR', room_id=null WHERE id=?";
    private static final String SQL_CONFIRM_APPLICATION_PAYMENT_BY_ID = "UPDATE application SET status = 'BOOKED' WHERE id=?";
    private static final String SQL_UPDATE_APPLICATION = "UPDATE application SET " +
            "check_in_date=?, check_out_date=?, status=?, total_price=?, bill_id=?, room_id=? " +
            "WHERE id=?";
    public static final String SQL_COUNT_APPLICATIONS = "SELECT COUNT(*) FROM application WHERE status='LOOKING_FOR' or status='ACCEPT_WAITING' or status='OUT_OF_TIME'";
    public static final String SQL_COUNT_RESERVATIONS = "SELECT COUNT(*) FROM application WHERE status='PAYMENT_WAITING' or status='BOOKED'";
    public static final String SQL_CREATE_EVENT_FOR_APPLICATION_TO_DELETE_AFTER_2_DAYS = "CREATE EVENT application_delete_%d " +
            "ON SCHEDULE AT CURRENT_TIMESTAMP + INTERVAL 2 DAY " +
            "DO DELETE FROM application WHERE id=?";

    @Override
    public long saveApplication(Application application) {
        long id = -1;
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_APPLICATION, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setDate(1, application.getCheckInDate());
            preparedStatement.setDate(2, application.getCheckOutDate());
            preparedStatement.setInt(3, application.getRequirementCapacity());
            preparedStatement.setString(4, application.getRequirementType().name());
            preparedStatement.setString(5, application.getStatus().name());
            preparedStatement.setLong(6, application.getOwner().getId());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getLong(1);
                application.setId(id);
            }

            connection.commit();
        } catch (SQLException ex) {
            LOG.error("Failed while saving application", ex);
        }
        return id;
    }

    @Override
    public long saveReservation(Application application) {
        long id = -1;
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_RESERVATION, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setDate(1, application.getCheckInDate());
            preparedStatement.setDate(2, application.getCheckOutDate());
            preparedStatement.setString(3, application.getStatus().name());
            preparedStatement.setDouble(4, application.getBill().getTotalPrice());
            preparedStatement.setLong(5, application.getBill().getId());
            preparedStatement.setLong(6, application.getOwner().getId());
            preparedStatement.setLong(7, application.getRoom().getId());

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getLong(1);
                application.setId(id);
            }

            connection.commit();

            startEventToDeleteApplicationById(connection, application.getId());
        } catch (SQLException ex) {
            LOG.error("Failed while saving reservation", ex);
        }
        return id;
    }

    @Override
    public void confirmApplicationById(long id) {
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_CONFIRM_APPLICATION_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

            connection.commit();

            startEventToDeleteApplicationById(connection, id);
        } catch (SQLException ex) {
            LOG.error("Failed while confirming application by id", ex);
        }
    }

    @Override
    public void rejectApplicationById(long id) {
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_REJECT_APPLICATION_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

            connection.commit();
        } catch (SQLException ex) {
            LOG.error("Failed while rejecting application by id", ex);
        }
    }

    @Override
    public void updateApplication(Application application) {
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_APPLICATION)) {
            preparedStatement.setDate(1, application.getCheckInDate());
            preparedStatement.setDate(2, application.getCheckOutDate());
            preparedStatement.setString(3, application.getStatus().name());
            preparedStatement.setDouble(4, application.getTotalPrice());
            Bill bill = application.getBill();
            preparedStatement.setLong(5, bill == null ? -1 : bill.getId());
            Room room = application.getRoom();
            preparedStatement.setLong(6, room == null ? -1 : room.getId());
            preparedStatement.setLong(7, application.getId());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            LOG.error("Failed while updating application", ex);
        }
    }

    @Override
    public void deleteApplicationById(long id) {
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_APPLICATION_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

            connection.commit();
        } catch (SQLException ex) {
            LOG.error("Failed while deleting application by id", ex);
        }
    }

    @Override
    public void confirmApplicationPaymentById(long id) {
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_CONFIRM_APPLICATION_PAYMENT_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

            connection.commit();
        } catch (SQLException ex) {
            LOG.error("Failed while confirming application payment by id", ex);
        }
    }

    @Override
    public Set<Long> findApplicationsBookedOnDate(Date checkIn, Date checkOut) {
        Set<Long> roomIdSet = new HashSet<>();
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_APPLICATIONS_BOOKED_ON_DATE)) {
            preparedStatement.setDate(1, checkIn);
            preparedStatement.setDate(2, checkOut);
            preparedStatement.setDate(3, checkIn);
            preparedStatement.setDate(4, checkOut);
            preparedStatement.setDate(5, checkIn);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                roomIdSet.add(resultSet.getLong(Fields.APPLICATION_ROOM_ID));
            }
            resultSet.close();

            connection.commit();
        } catch (SQLException ex) {
            LOG.error("Failed while finding application booked on the specific date", ex);
        }
        return roomIdSet;
    }

    @Override
    public Application findApplicationById(long id) {
        Application application = null;
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_APPLICATION_BY_ID)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                application = mapApplication(resultSet, false);
            }
            resultSet.close();

            connection.commit();
        } catch (SQLException ex) {
            LOG.error("Failed while finding application by id", ex);
        }
        return application;
    }

    @Override
    public List<Application> findAllApplications() {
        return findApplicationsBySql(SQL_FIND_ALL_APPLICATIONS, false);
    }

    @Override
    public List<Application> findApplicationsFromScope(int from, int to) {
        return findApplicationsBySql(String.format(SQL_FIND_APPLICATIONS_FROM_TO, from, to - from), false);
    }

    @Override
    public List<Application> findAllReservations() {
        return findApplicationsBySql(SQL_FIND_ALL_RESERVATIONS, true);
    }

    @Override
    public List<Application> findReservationsFromScope(int from, int to) {
        return findApplicationsBySql(String.format(SQL_FIND_RESERVATIONS_FROM_TO, from, to - from), true);
    }

    @Override
    public int countApplications() {
        return countBySql(SQL_COUNT_APPLICATIONS);
    }

    @Override
    public int countReservations() {
        return countBySql(SQL_COUNT_RESERVATIONS);
    }

    private int countBySql(String sql) {
        int res = -1;
        try (Connection connection = DBManager.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            if (resultSet.next()) res = resultSet.getInt(1);

            connection.commit();
        } catch (SQLException ex) {
            LOG.error("Failed while counting a number of rows", ex);
        }
        return res;
    }

    private List<Application> findApplicationsBySql(String sql, boolean isReservation) {
        List<Application> applications = new ArrayList<>();
        try (Connection connection = DBManager.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                applications.add(mapApplication(resultSet, isReservation));
            }

            connection.commit();
        } catch (SQLException ex) {
            LOG.error("Failed while finding application by given sql statement", ex);
        }
        return applications;
    }

    private Application mapApplication(ResultSet resultSet, boolean isReservation) throws SQLException {
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

    private void startEventToDeleteApplicationById(Connection connection, long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(String.format(SQL_CREATE_EVENT_FOR_APPLICATION_TO_DELETE_AFTER_2_DAYS, id))) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

            connection.commit();
        } catch (SQLException ex) {
            LOG.error("Failed while starting a event to delete bill after 2 days without confirmation", ex);
        }
    }
}
