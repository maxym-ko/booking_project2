package com.maxym.booking.db.dao;

import com.maxym.booking.db.entity.application.Application;
import com.maxym.booking.db.entity.room.Room;

import java.util.List;

public interface ApplicationDao {
    void saveApplication(Application application);
    void saveReservation(Application application);
    void confirmApplicationById(long id);
    void rejectApplicationById(long id);
    void updateApplication(Application application);
    void deleteApplicationById(long id);
    void confirmApplicationPaymentById(long id);
    List<Application> findAllApplications();
    List<Application> findApplicationsFromScope(int from, int to);
    List<Application> findAllReservations();
    List<Application> findReservationsFromScope(int from, int to);
    Application findApplicationById(long id);
    int countApplications();
    int countReservations();
}
