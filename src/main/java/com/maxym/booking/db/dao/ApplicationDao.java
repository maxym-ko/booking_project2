package com.maxym.booking.db.dao;

import com.maxym.booking.db.entity.application.Application;
import com.maxym.booking.db.entity.user.User;

import java.util.List;

public interface ApplicationDao {
    void createApplication(Application application);
    void createReservation(Application application);
    Application findApplicationById(long id);
    List<Application> findAllApplications();
    List<Application> findAllReservations();
}
