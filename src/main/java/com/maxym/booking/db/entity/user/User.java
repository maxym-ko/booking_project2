package com.maxym.booking.db.entity.user;

import lombok.*;

import java.io.Serializable;

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
}
