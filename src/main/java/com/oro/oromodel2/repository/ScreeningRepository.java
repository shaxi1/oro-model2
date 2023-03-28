package com.oro.oromodel2.repository;

import com.oro.oromodel2.model.Reservation;
import com.oro.oromodel2.model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening, Long> {
    @Query("SELECT r FROM Reservation r WHERE r.screening.id = ?1")
    List<Reservation> findAllReservationsForScreeningById(long screeningId);


}