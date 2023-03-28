package com.oro.oromodel2.repository;

import com.oro.oromodel2.model.Client;
import com.oro.oromodel2.model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("SELECT s FROM Screening s INNER JOIN s.reservations r WHERE r.client.id = ?1")
    List<Screening> findAllScreeningsByClientId(long clientId);

    @Query("SELECT s FROM Screening s INNER JOIN s.reservations r WHERE r.client.login = ?1")
    List<Screening> findAllScreeningsByClientLogin(String clientLogin);
}
