package com.maxym.booking.db.dao;

import com.maxym.booking.db.entity.application.Application;

import java.util.List;

public interface ApplicationDao {
    void saveApplication(Application application);
    void saveReservation(Application application);
    Application findApplicationById(long id);
    List<Application> findAllApplications();
    List<Application> findAllReservations();
}
