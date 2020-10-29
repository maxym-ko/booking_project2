package com.maxym.booking.db.dao.impl;

import com.maxym.booking.db.Fields;
import com.maxym.booking.db.dao.RoomDao;
import com.maxym.booking.db.entity.application.Bill;
import com.maxym.booking.db.entity.room.Room;
import com.maxym.booking.db.entity.room.RoomStatus;
import com.maxym.booking.db.entity.room.RoomType;
import com.maxym.booking.db.entity.user.Role;
import com.maxym.booking.db.entity.user.User;
import com.maxym.booking.db.util.DBManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDaoImpl implements RoomDao {
    public static final String SQL_INSERT_ROOM = "INSERT INTO room (capacity, price, type, status) VALUES (?, ?, ?, ?)";
    private static final String SQL_FIND_ROOM_BY_ID = "SELECT * FROM room WHERE id=?";
    private static final String SQL_FIND_ALL_ROOMS = "SELECT * FROM room";
    private static final String SQL_DELETE_ROOM_BY_ID = "DELETE FROM room WHERE id=?";
    public static final String SQL_UPDATE_ROOM = "UPDATE room SET capacity=?, price=?, type=?, status=? WHERE id=?";

    @Override
    public void saveRoom(Room room) {
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_ROOM)) {
            preparedStatement.setInt(1, room.getCapacity());
            preparedStatement.setDouble(2, room.getPrice());
            preparedStatement.setString(3, room.getType().name());
            preparedStatement.setString(4, room.getStatus().name());

            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
//            TODO: Catch exception
        }
    }

    @Override
    public Room findRoomById(long id) {
        Room room = null;
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ROOM_BY_ID)) {
            preparedStatement.setLong(1, id);

            room = getRoomFromPreparedStatement(preparedStatement);

            connection.commit();
        } catch (SQLException ex) {
//            TODO: Catch exception
        }
        return room;
    }

    @Override
    public List<Room> findAllRooms() {
        List<Room> rooms = new ArrayList<>();
        try (Connection connection = DBManager.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_ROOMS)) {

            while (resultSet.next()) {
                rooms.add(Room.builder()
                        .id(resultSet.getLong(Fields.ROOM_ID))
                        .capacity(resultSet.getInt(Fields.ROOM_CAPACITY))
                        .price(resultSet.getDouble(Fields.ROOM_PRICE))
                        .type(RoomType.valueOf(resultSet.getString(Fields.ROOM_TYPE)))
                        .status(RoomStatus.valueOf(resultSet.getString(Fields.ROOM_STATUS)))
                        .imgName(resultSet.getString(Fields.ROOM_IMG_NAME)).build());
            }

            connection.commit();
        } catch (SQLException ex) {
//            TODO: Catch exception
        }
        return rooms;
    }

    @Override
    public void updateRoom(Room room) {
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_ROOM)) {
            preparedStatement.setInt(1, room.getCapacity());
            preparedStatement.setDouble(2, room.getPrice());
            preparedStatement.setString(3, room.getType().name());
            preparedStatement.setString(4, room.getStatus().name());
            preparedStatement.setLong(5, room.getId());
            preparedStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
//            TODO: Catch exception
        }
    }

    @Override
    public void deleteRoomById(long id) {
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ROOM_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
//            TODO: Catch exception
        }
    }

    private Room getRoomFromPreparedStatement(PreparedStatement preparedStatement) throws SQLException {
        Room room = null;
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            room = Room.builder()
                    .id(resultSet.getLong(Fields.ROOM_ID))
                    .capacity(resultSet.getInt(Fields.ROOM_CAPACITY))
                    .price(resultSet.getDouble(Fields.ROOM_PRICE))
                    .type(RoomType.valueOf(resultSet.getString(Fields.ROOM_TYPE)))
                    .status(RoomStatus.valueOf(resultSet.getString(Fields.ROOM_STATUS)))
                    .imgName(resultSet.getString(Fields.ROOM_IMG_NAME)).build();
        }
        resultSet.close();
        return room;
    }
}
