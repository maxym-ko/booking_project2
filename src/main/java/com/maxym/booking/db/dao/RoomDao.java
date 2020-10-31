package com.maxym.booking.db.dao;

import com.maxym.booking.db.entity.room.Room;

import java.util.List;

public interface RoomDao {
    void saveRoom(Room room);
    Room findRoomById(long id);
    List<Room> findAllRooms();
    List<Room> findRoomsFromScope(int from, int to);
    List<Room> findRoomsFromScopeOrderBy(String orderBy, int from, int to);
    void updateRoom(Room room);
    void deleteRoomById(long id);
    int getNumberOfRows();
}
