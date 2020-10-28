package com.maxym.booking;

public final class Path {
    private Path() {}

    public static final String PAGE_MAIN = "jsp/main.jsp";
    public static final String PAGE_ABOUT = "jsp/about.jsp";
    public static final String PAGE_LOGIN = "jsp/login.jsp";
    public static final String PAGE_REGISTRATION = "jsp/registration.jsp";

    public static final String REDIRECT_MAIN = "/controller?command=main";
    public static final String REDIRECT_SHOW_LOGIN = "/controller?command=show_login";
}
