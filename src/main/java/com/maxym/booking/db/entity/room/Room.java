package com.maxym.booking.db.entity.room;

import com.maxym.booking.db.entity.application.Application;
import lombok.*;

import java.io.Serializable;
import java.util.List;

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
    private List<Application> reservations;
}
