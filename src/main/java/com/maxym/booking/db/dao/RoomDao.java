package com.maxym.booking.db.dao;

import com.maxym.booking.db.entity.room.Room;

import java.util.List;
import java.util.Set;

public interface RoomDao {
    long saveRoom(Room room);
    Room findRoomById(long id);
    List<Room> findRoomsFromScopeOrderBy(String orderBy, int from, int to);
    List<Room> findRoomsFromScopeExceptOrderBy(String orderBy, int from, int to, Set<Long> roomIds);
    void updateRoom(Room room);
    void deleteRoomById(long id);
    int countRooms();
}
