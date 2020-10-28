package com.maxym.booking.db.entity.user;

import com.maxym.booking.db.entity.application.Application;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class User implements Serializable {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final long serialVersionUID = -4930879145976782706L;
    private long id;
    private String username;
    private String password;
    private Role role;
    private List<Application> applications;
    private List<Application> reservations;
}
