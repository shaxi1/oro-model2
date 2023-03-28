package com.oro.oromodel2.repository;

import com.oro.oromodel2.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.screening.id = ?1")
    int countSeatsTakenForScreening(long screeningId);

    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.client.id = ?1 AND r.reservationDate BETWEEN ?2 AND ?3")
    int countSeatsBoughtByUserBetweenDates(long userId, LocalDateTime startDate, LocalDateTime endDate);
}
