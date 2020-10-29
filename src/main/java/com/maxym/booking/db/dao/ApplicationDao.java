package com.maxym.booking.db.dao;

import com.maxym.booking.db.entity.application.Application;

import java.util.List;

public interface ApplicationDao {
    void saveApplication(Application application);
    void saveReservation(Application application);
    List<Application> findAllApplications();
    List<Application> findAllReservations();
    Application findApplicationById(long id);
    void confirmApplicationPaymentById(long id);
    void confirmApplicationById(long id);
    void rejectApplicationById(long id);
    void updateApplication(Application application);
    void deleteApplicationById(long id);
}
