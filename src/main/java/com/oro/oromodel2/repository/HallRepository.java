package com.oro.oromodel2.repository;

import com.oro.oromodel2.model.Hall;
import com.oro.oromodel2.model.Movie;
import com.oro.oromodel2.model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HallRepository extends JpaRepository<Hall, Long> {
    @Query("SELECT DISTINCT s.movie FROM Screening s WHERE s.hall.id = ?1")
    List<Movie> findAllMoviesInHall(long hallId);

    @Query("SELECT DISTINCT s FROM Screening s WHERE s.hall.id = ?1")
    List<Screening> findAllScreeningsInHall(long hallId);


}
