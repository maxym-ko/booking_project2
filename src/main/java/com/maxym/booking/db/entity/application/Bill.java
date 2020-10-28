package com.maxym.booking.db.entity.application;

import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class Bill implements Serializable {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final long serialVersionUID = -8609599927493700074L;
    private long id;
    private double totalPrice;
    private Date created;
    private String receiptId;

    public Bill() {}

    public Bill(LocalDate start, LocalDate end, double price) {
        created = new Date(System.currentTimeMillis());

        long days = Duration.between(start.atStartOfDay(), end.atStartOfDay()).toDays();

        this.totalPrice = price * days;
    }
}
