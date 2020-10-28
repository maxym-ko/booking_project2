package com.maxym.booking.db.entity.room;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
public class Room implements Serializable {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final long serialVersionUID = -2525057169013511071L;
    private long id;
    private int capacity;
    private double price;
    private RoomType type;
    private RoomStatus status;
    private String imgName;
}
