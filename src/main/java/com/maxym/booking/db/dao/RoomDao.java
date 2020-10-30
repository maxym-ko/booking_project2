package com.maxym.booking.db.dao;

import com.maxym.booking.db.entity.room.Room;

import java.util.List;

public interface RoomDao {
    void saveRoom(Room room);
    Room findRoomById(long id);
    List<Room> findAllRooms();
    List<Room> findRoomsOrderBy(String orderBy);
    void updateRoom(Room room);
    void deleteRoomById(long id);
}
