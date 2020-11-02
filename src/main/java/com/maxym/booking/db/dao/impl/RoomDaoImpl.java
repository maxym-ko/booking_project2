package com.maxym.booking.db.dao.impl;

import com.maxym.booking.db.Fields;
import com.maxym.booking.db.dao.RoomDao;
import com.maxym.booking.db.entity.room.Room;
import com.maxym.booking.db.entity.room.RoomStatus;
import com.maxym.booking.db.entity.room.RoomType;
import com.maxym.booking.db.util.DBManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RoomDaoImpl implements RoomDao {
    public static final String SQL_INSERT_ROOM = "INSERT INTO room (capacity, price, type, status, img_name) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_FIND_ROOM_BY_ID = "SELECT * FROM room WHERE id=?";
    private static final String SQL_FIND_ROOMS_FROM_SCOPE = "SELECT * FROM room LIMIT ?,?";
    private static final String SQL_FIND_ROOMS_FROM_SCOPE_ORDER_BY = "SELECT * FROM room ORDER BY %s LIMIT ?,?";
    private static final String SQL_FIND_ROOMS_FROM_SCOPE_EXCEPT = "SELECT * FROM room WHERE id not in (%s) LIMIT ?,?";
    private static final String SQL_FIND_ROOMS_FROM_SCOPE_EXCEPT_ORDER_BY = "SELECT * FROM room WHERE id not in (%s) ORDER BY %s LIMIT ?,?";
    private static final String SQL_DELETE_ROOM_BY_ID = "DELETE FROM room WHERE id=?";
    public static final String SQL_UPDATE_ROOM = "UPDATE room SET capacity=?, price=?, type=?, status=? WHERE id=?";
    public static final String SQL_COUNT_ROOMS = "SELECT COUNT(*) FROM room";

    @Override
    public void saveRoom(Room room) {
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_ROOM)) {
            preparedStatement.setInt(1, room.getCapacity());
            preparedStatement.setDouble(2, room.getPrice());
            preparedStatement.setString(3, room.getType().name());
            preparedStatement.setString(4, room.getStatus().name());
            preparedStatement.setString(5, room.getImgName());

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

            ResultSet resultSet = preparedStatement.executeQuery();
            room = getRoomFromResultSet(resultSet);
            resultSet.close();

            connection.commit();
        } catch (SQLException ex) {
//            TODO: Catch exception
        }
        return room;
    }

    private List<Room> findRoomsFromScope(int from, int to) {
        return findRoomsFromScopeBySql(SQL_FIND_ROOMS_FROM_SCOPE, from, to);
    }

    @Override
    public List<Room> findRoomsFromScopeOrderBy(String orderBy, int from, int to) {
        if (orderBy == null) return findRoomsFromScope(from, to);
        return findRoomsFromScopeBySql(String.format(SQL_FIND_ROOMS_FROM_SCOPE_ORDER_BY, orderBy), from, to);
    }

    private List<Room> findRoomsFromScopeExcept(int from, int to, Set<Long> roomIds) {
        String id = roomIds.toString();
        id = id.substring(1, id.length() - 1);
        String sql = id.length() == 0 ? SQL_FIND_ROOMS_FROM_SCOPE : SQL_FIND_ROOMS_FROM_SCOPE_EXCEPT;
        return findRoomsFromScopeBySql(String.format(sql, id), from, to);
    }

    @Override
    public List<Room> findRoomsFromScopeExceptOrderBy(String orderBy, int from, int to, Set<Long> roomIds) {
        if (orderBy == null) return findRoomsFromScopeExcept(from, to, roomIds);
        String id = roomIds.toString();
        id = id.substring(1, id.length() - 1);
        String sql = id.length() == 0 ? SQL_FIND_ROOMS_FROM_SCOPE_ORDER_BY : SQL_FIND_ROOMS_FROM_SCOPE_EXCEPT_ORDER_BY;
        return findRoomsFromScopeBySql(String.format(sql, id, orderBy), from, to);
    }

    private List<Room> findRoomsFromScopeBySql(String sql, int from, int to) {
        List<Room> rooms = new ArrayList<>();
        try (Connection connection = DBManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, from);
            preparedStatement.setInt(2, to - from);

            ResultSet resultSet = preparedStatement.executeQuery();
            rooms = getRoomsFromResultSet(resultSet);
            resultSet.close();

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
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

    @Override
    public int countRooms() {
        int res = -1;
        try (Connection connection = DBManager.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_COUNT_ROOMS)) {

            if (resultSet.next()) res = resultSet.getInt(1);

            connection.commit();
        } catch (SQLException ex) {
//            TODO: Catch exception
        }
        return res;
    }

    private List<Room> getRoomsFromResultSet(ResultSet resultSet) throws SQLException {
        List<Room> rooms = new ArrayList<>();
        while (resultSet.next()) {
            rooms.add(Room.builder()
                    .id(resultSet.getLong(Fields.ROOM_ID))
                    .capacity(resultSet.getInt(Fields.ROOM_CAPACITY))
                    .price(resultSet.getDouble(Fields.ROOM_PRICE))
                    .type(RoomType.valueOf(resultSet.getString(Fields.ROOM_TYPE)))
                    .status(RoomStatus.valueOf(resultSet.getString(Fields.ROOM_STATUS)))
                    .imgName(resultSet.getString(Fields.ROOM_IMG_NAME)).build());
        }
        return rooms;
    }

    private Room getRoomFromResultSet(ResultSet resultSet) throws SQLException {
        List<Room> rooms = getRoomsFromResultSet(resultSet);
        if (!rooms.isEmpty()) return rooms.get(0);
        return null;
    }
}
