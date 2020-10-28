package com.maxym.booking.db.entity.application;

import com.maxym.booking.db.entity.room.Room;
import com.maxym.booking.db.entity.room.RoomType;
import com.maxym.booking.db.entity.user.User;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@Builder
@ToString
public class Application implements Serializable {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final long serialVersionUID = 6783297775521879987L;
    private long id;
    private int requirementCapacity;
    private RoomType requirementType;
    private double totalPrice;
    private ApplicationStatus status;
    private Date checkInDate;
    private Date checkOutDate;
    private Bill bill;
    private User owner;
    private Room room;
}
