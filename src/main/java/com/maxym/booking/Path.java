package com.maxym.booking;

public final class Path {
    private Path() {}

    public static final String PAGE_ERROR = "jsp/error.jsp";
    public static final String PAGE_HOME = "jsp/home.jsp";
    public static final String PAGE_ABOUT = "jsp/about.jsp";
    public static final String PAGE_LOGIN = "jsp/login.jsp";
    public static final String PAGE_REGISTRATION = "jsp/registration.jsp";
    public static final String PAGE_APPLICATIONS = "jsp/applications.jsp";
    public static final String PAGE_RESERVATIONS = "jsp/reservations.jsp";
    public static final String PAGE_FIND_ROOM = "jsp/find_room.jsp";

    public static final String REDIRECT_SHOW_LOGIN = "/controller?command=show_login";
    public static final String REDIRECT_HOME = "/controller?command=home";
    public static final String REDIRECT_APPLICATIONS = "/controller?command=show_applications";
    public static final String REDIRECT_RESERVATIONS = "/controller?command=show_reservations";
}
